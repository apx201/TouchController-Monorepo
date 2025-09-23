package top.fifthlight.blazerod.animation.context

import net.minecraft.client.option.Perspective
import net.minecraft.component.DataComponentTypes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import top.fifthlight.blazerod.model.animation.AnimationContext
import top.fifthlight.blazerod.model.animation.AnimationContext.Property.*
import top.fifthlight.blazerod.util.ObjectPool

open class PlayerEntityAnimationContext protected constructor() : LivingEntityAnimationContext(), AutoCloseable {
    companion object {
        private val POOL = ObjectPool(
            identifier = Identifier.of("blazerod", "player_entity_animation_context"),
            create = ::PlayerEntityAnimationContext,
            onReleased = {
                released = false
                realPlayerEntity = null
            },
            onClosed = { },
        )

        fun acquire(entity: PlayerEntity) = POOL.acquire().apply {
            realPlayerEntity = entity
        }

        fun <T> with(entity: PlayerEntity, block: (PlayerEntityAnimationContext) -> T) =
            acquire(entity).use { block(it) }

        @JvmStatic
        protected val propertyTypes = EntityAnimationContext.propertyTypes + listOf(
            PlayerHeadXRotation,
            PlayerHeadYRotation,
            PlayerIsFirstPerson,
            PlayerIsSpectator,
            PlayerIsSneaking,
            PlayerIsSprinting,
            PlayerIsSwimming,
            PlayerBodyXRotation,
            PlayerBodyYRotation,
            PlayerIsEating,
            PlayerIsUsingItem,
            PlayerLevel,
            PlayerIsJumping
        )
            @JvmName("getPlayerEntityPropertyTypes")
            get
    }

    protected var realPlayerEntity: PlayerEntity? = null
        set(value) {
            realEntity = value
            field = value
        }
    override val entity: PlayerEntity
        get() = realPlayerEntity ?: throw IllegalStateException("Entity is null")

    @Suppress("UNCHECKED_CAST")
    override fun <T> getProperty(type: AnimationContext.Property<T>): T? = when (type) {
        // FIXME
        PlayerHeadXRotation -> floatBuffer.apply { value = 0f }

        PlayerHeadYRotation -> floatBuffer.apply { value = entity.headYaw }

        PlayerIsFirstPerson -> booleanBuffer.apply {
            val isSelf = entity == client.player
            val isFirstPerson = client.options.perspective == Perspective.FIRST_PERSON
            value = isSelf && isFirstPerson
        }

        PlayerIsSpectator -> booleanBuffer.apply { value = entity.isSpectator }

        PlayerIsSneaking -> booleanBuffer.apply { value = entity.isSneaking }

        PlayerIsSprinting -> booleanBuffer.apply { value = entity.isSprinting }

        PlayerIsSwimming -> booleanBuffer.apply { value = entity.isSwimming }

        PlayerBodyXRotation -> floatBuffer.apply { value = entity.pitch }

        // FIXME
        PlayerBodyYRotation -> floatBuffer.apply { value = 0f }

        PlayerIsEating -> booleanBuffer.apply {
            val isUsingItem = entity.isUsingItem
            val usingItemHasConsumingComponent = entity.activeItem.components.get(DataComponentTypes.CONSUMABLE) != null
            value = isUsingItem && usingItemHasConsumingComponent
        }

        PlayerIsUsingItem -> booleanBuffer.apply { value = entity.isUsingItem }

        PlayerLevel -> intBuffer.apply { value = entity.experienceLevel }

        PlayerIsJumping -> booleanBuffer.apply { value = entity.isJumping }

        else -> super.getProperty(type)
    } as T?

    override fun getPropertyTypes() = propertyTypes
}