package top.fifthlight.blazerod.util

import net.minecraft.util.math.Vec3d
import org.joml.Vector3f

internal infix fun Int.ceilDiv(other: Int) = if (this % other == 0) {
    this / other
} else {
    this / other + 1
}

internal infix fun Int.roundUpToMultiple(divisor: Int) = (this ceilDiv divisor) * divisor

internal tailrec fun gcd(a: Int, b: Int): Int = if (b == 0) {
    a
} else {
    gcd(b, a % b)
}

internal fun lcm(a: Int, b: Int): Int = a * (b / gcd(a, b))

fun Vector3f.set(vec3d: Vec3d) = apply {
    x = vec3d.x.toFloat()
    y = vec3d.y.toFloat()
    z = vec3d.z.toFloat()
}

fun Vec3d.sub(v: Vec3d, dest: Vector3f) = dest.apply {
    x = (this@sub.x - v.x).toFloat()
    y = (this@sub.y - v.y).toFloat()
    z = (this@sub.z - v.z).toFloat()
}
