package top.fifthlight.touchcontoller.gradle

/// Represents a minecraft version. Don't include snapshots or pre-releases.
data class MinecraftVersion(
    /// Major version, always 1.
    val major: Int,
    /// Minor version
    val minor: Int,
    /// Patch version, may be empty
    val patch: Int?,
) : Comparable<MinecraftVersion> {
    init {
        require(major == 1) { "Bad major version: $major" }
    }

    override fun compareTo(other: MinecraftVersion): Int = when {
        this.minor != other.minor -> this.minor - other.minor
        this.patch != other.patch -> (this.patch ?: 0) - (other.patch ?: 0)
        else -> 0
    }

    companion object {
        private val pattern = Regex("^1\\.(?<minor>\\d+)(\\.(?<patch>\\d+)|)$")

        fun parseOrNull(version: String): MinecraftVersion? = pattern.matchEntire(version)?.let { result ->
            val minor = (result.groups["minor"] ?: return@let null).value.toInt()
            val patch = result.groups["patch"]?.value?.toInt()
            MinecraftVersion(
                major = 1,
                minor = minor,
                patch = patch,
            )
        }
    }
}

fun MinecraftVersion(version: String) =
    MinecraftVersion.parseOrNull(version) ?: throw IllegalArgumentException("Bad minecraft version: $version")