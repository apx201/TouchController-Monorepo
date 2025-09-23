package top.fifthlight.blazerod.model.animation

import org.joml.Vector3fc
import top.fifthlight.blazerod.model.util.BooleanWrapper
import top.fifthlight.blazerod.model.util.FloatWrapper
import top.fifthlight.blazerod.model.util.IntWrapper

interface AnimationContext {
    companion object {
        const val SECONDS_PER_TICK = 1f / 20f
    }

    interface Property<T> {
        object GameTick: Property<IntWrapper>
        object DeltaTick: Property<FloatWrapper>

        // Entity
        object EntityPosition : Property<Vector3fc>
        object EntityPositionDelta : Property<Vector3fc>
        object EntityHorizontalFacing : Property<IntWrapper>
        object EntityGroundSpeed : Property<FloatWrapper>
        object EntityVerticalSpeed : Property<FloatWrapper>
        object EntityHasRider : Property<BooleanWrapper>
        object EntityIsRiding : Property<BooleanWrapper>
        object EntityIsInWater : Property<BooleanWrapper>
        object EntityIsInFire : Property<BooleanWrapper>
        object EntityIsOnGround : Property<BooleanWrapper>

        // LivingEntity
        object LivingEntityHealth : Property<FloatWrapper>
        object LivingEntityMaxHealth : Property<FloatWrapper>
        object LivingEntityHurtTime : Property<IntWrapper>
        object LivingEntityIsDead : Property<BooleanWrapper>
        object LivingEntityEquipmentCount : Property<IntWrapper>

        // Player
        object PlayerHeadXRotation : Property<FloatWrapper>
        object PlayerHeadYRotation : Property<FloatWrapper>
        object PlayerIsFirstPerson : Property<BooleanWrapper>
        object PlayerIsSpectator : Property<BooleanWrapper>
        object PlayerIsSneaking : Property<BooleanWrapper>
        object PlayerIsSprinting : Property<BooleanWrapper>
        object PlayerIsSwimming : Property<BooleanWrapper>
        object PlayerBodyXRotation : Property<FloatWrapper>
        object PlayerBodyYRotation : Property<FloatWrapper>
        object PlayerIsEating : Property<BooleanWrapper>
        object PlayerIsUsingItem : Property<BooleanWrapper>
        object PlayerLevel : Property<IntWrapper>
        object PlayerIsJumping : Property<BooleanWrapper>

        // World
        object WorldMoonPhase : Property<IntWrapper>
    }

    // game ticks
    fun getGameTick(): Long
    // 0 ~ 1
    fun getDeltaTick(): Float

    fun <T> getProperty(type: Property<T>): T?

    fun getPropertyTypes(): Set<Property<*>>
}
