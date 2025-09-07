package top.fifthlight.armorstand.manage.scan

import top.fifthlight.armorstand.util.ModelLoaders
import top.fifthlight.blazerod.model.ModelFileLoaders
import java.nio.file.Path
import kotlin.io.path.extension

object ModelLoaderFileHandler : FileHandler {
    override fun isModelFile(file: Path) = file.extension in ModelLoaders.modelExtensions
    override fun isAnimationFile(file: Path) = file.extension in ModelLoaders.animationExtensions
    override fun canExtractEmbedThumbnail(file: Path) = file.extension in ModelLoaders.embedThumbnailExtensions
    override fun extractEmbedThumbnail(file: Path, basePath: Path) = ModelFileLoaders.getEmbedThumbnail(file, basePath)
}