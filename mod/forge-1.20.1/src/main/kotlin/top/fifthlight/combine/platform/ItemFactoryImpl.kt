package top.fifthlight.combine.platform

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import net.minecraft.network.chat.Component
import net.minecraft.world.item.*
import net.minecraftforge.registries.ForgeRegistries
import top.fifthlight.combine.data.Identifier
import top.fifthlight.combine.data.ItemFactory
import top.fifthlight.combine.data.ItemSubclass
import top.fifthlight.touchcontroller.config.ItemList
import top.fifthlight.combine.data.Item as CombineItem
import top.fifthlight.combine.data.ItemStack as CombineItemStack

object ItemFactoryImpl : ItemFactory {
    override fun createItem(id: Identifier): CombineItem? {
        val item = ForgeRegistries.ITEMS.getValue(id.toMinecraft()) ?: return null
        return ItemImpl(item)
    }

    override fun createItemStack(
        item: CombineItem,
        amount: Int
    ): CombineItemStack {
        val minecraftItem = (item as ItemImpl).inner
        val stack = ItemStack(minecraftItem, amount)
        return ItemStackImpl(stack)
    }

    override fun createItemStack(id: Identifier, amount: Int): CombineItemStack? {
        val item = ForgeRegistries.ITEMS.getValue(id.toMinecraft()) ?: return null
        val stack = ItemStack(item, amount)
        return ItemStackImpl(stack)
    }

    override val allItems: PersistentList<CombineItem> by lazy {
        ForgeRegistries.ITEMS.map(Item::toCombine).toPersistentList()
    }

    val rangedWeaponSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("Ranged weapon")),
        configId = "RangedWeaponItem",
        clazz = ProjectileWeaponItem::class.java
    )

    val armorSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("Armor")),
        configId = "ArmorItem",
        clazz = ArmorItem::class.java
    )

    val equipmentSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("Equipment")),
        configId = "Equipment",
        clazz = Equipable::class.java
    )

    val bundleSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("Bundle")),
        configId = "BundleItem",
        clazz = BundleItem::class.java
    )

    val bucketSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("Bucket")),
        configId = "BucketItem",
        clazz = BucketItem::class.java,
    )

    val boatSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("Boat")),
        configId = "BoatItem",
        clazz = BoatItem::class.java,
    )

    val placeableOnWaterSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("PlaceableOnWater")),
        configId = "PlaceableOnWaterItem",
        clazz = PlaceOnWaterBlockItem::class.java,
    )

    val spawnEggSubclass = ItemSubclassImpl(
        name = TextImpl(Component.literal("SpawnEgg")),
        configId = "SpawnEggItem",
        clazz = SpawnEggItem::class.java,
    )

    override val subclasses: PersistentList<ItemSubclass> = persistentListOf(
        rangedWeaponSubclass,
        armorSubclass,
        equipmentSubclass,
        bundleSubclass,
        bucketSubclass,
        boatSubclass,
        placeableOnWaterSubclass,
        spawnEggSubclass,
    )
}

fun Item.toCombine() = ItemImpl(this)
fun ItemStack.toCombine() = ItemStackImpl(this)
fun CombineItem.toVanilla() = (this as ItemImpl).inner
fun CombineItemStack.toVanilla() = (this as ItemStackImpl).inner
fun ItemList.contains(item: Item) = contains(item.toCombine())
