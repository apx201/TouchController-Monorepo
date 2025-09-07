package top.fifthlight.armorstand.manage.scan

import kotlinx.coroutines.*
import org.slf4j.LoggerFactory
import top.fifthlight.armorstand.manage.database.DatabaseManager
import top.fifthlight.armorstand.util.ModelHash
import top.fifthlight.armorstand.util.calculateSha256
import top.fifthlight.armorstand.util.toHexString
import top.fifthlight.blazerod.model.ModelFileLoader
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes

class ModelScannerImpl(
    private val modelDir: Path,
    private val database: DatabaseManager,
) : ModelScanner {
    companion object {
        private val logger = LoggerFactory.getLogger(ModelScannerImpl::class.java)
    }

    // Actually this API is stable in newer coroutines library, so it is safe to use
    @OptIn(ExperimentalCoroutinesApi::class)
    private val ioDispatcher = Dispatchers.IO.limitedParallelism(4)

    override suspend fun scan(fileHandler: FileHandler) {
        database.transaction {
            coroutineScope {
                scanSessionRepository.open()
                Files.walkFileTree(modelDir, object : FileVisitor<Path> {
                    override fun preVisitDirectory(
                        dir: Path,
                        attrs: BasicFileAttributes,
                    ) = FileVisitResult.CONTINUE

                    override fun visitFileFailed(
                        file: Path,
                        exc: IOException?,
                    ) = FileVisitResult.CONTINUE

                    override fun postVisitDirectory(
                        dir: Path,
                        exc: IOException?,
                    ) = FileVisitResult.CONTINUE

                    override fun visitFile(
                        file: Path,
                        attrs: BasicFileAttributes,
                    ): FileVisitResult {
                        launch {
                            try {
                                val relativePath = modelDir.relativize(file).normalize().toString()
                                val name = file.fileName.toString()
                                val lastChanged = Files.getLastModifiedTime(file).toMillis()

                                logger.trace("Process model file {}", name)

                                // findSha256(path, lastChanged) will return null when lastChanged not match
                                val cachedSha = fileCacheRepository.findSha256(relativePath, lastChanged)?.also {
                                    logger.trace("Use cached hash {}", it.toHexString())
                                }
                                val sha256 = ModelHash(cachedSha ?: withContext(ioDispatcher) {
                                    file.calculateSha256().also {
                                        logger.trace("Calculated hash {}", it.toHexString())
                                        fileCacheRepository.upsertCache(relativePath, lastChanged, it)
                                    }
                                })
                                scanSessionRepository.markFileSha(sha256)

                                when {
                                    fileHandler.isModelFile(file) -> {
                                        scanSessionRepository.markModelPath(relativePath)

                                        if (modelRepository.exists(relativePath, sha256)) {
                                            logger.trace("Already scanned model, skip processing model.")
                                            scanSessionRepository.markThumbnailSha(sha256)
                                            return@launch
                                        }

                                        modelRepository.upsert(relativePath, name, lastChanged, sha256)

                                        if (fileHandler.canExtractEmbedThumbnail(file)) {
                                            if (
                                                !thumbRepository.existsEmbed(sha256) &&
                                                !scanSessionRepository.isThumbnailMarked(sha256)
                                            ) {
                                                try {
                                                    val result = withContext(ioDispatcher) {
                                                        fileHandler.extractEmbedThumbnail(
                                                            file,
                                                            file.toAbsolutePath().parent
                                                        )
                                                    }
                                                    logger.trace("Extracted thumbnail, result: {}", result)
                                                    if (result != null && result is ModelFileLoader.ThumbnailResult.Embed) {
                                                        val (offset, length, type) = result
                                                        thumbRepository.insertEmbed(
                                                            sha256 = sha256,
                                                            offset = offset,
                                                            length = length,
                                                            mimeType = type?.mimeType,
                                                        )
                                                    }
                                                } catch (ex: Exception) {
                                                    logger.warn("Failed to extract thumbnail: {}", file, ex)
                                                }
                                            }
                                            scanSessionRepository.markThumbnailSha(sha256)
                                        }
                                    }

                                    fileHandler.isAnimationFile(file) -> {
                                        animationRepository.upsert(relativePath, name, lastChanged, sha256)
                                        scanSessionRepository.markAnimationPath(relativePath)
                                    }

                                    else -> {}
                                }
                            } catch (ex: Exception) {
                                logger.warn("Failed to scan file: {}", file, ex)
                            }
                        }
                        return FileVisitResult.CONTINUE
                    }
                })
            }

            scanSessionRepository.cleanup()
            scanSessionRepository.close()
        }
    }
}
