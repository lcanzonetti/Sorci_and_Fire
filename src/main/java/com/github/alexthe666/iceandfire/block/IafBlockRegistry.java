package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.common.EventBusSubscriber;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.item.BlockItemWithRender;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Optional;

@EventBusSubscriber(modid = IceAndFire.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IafBlockRegistry {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IceAndFire.MODID);

    public static final SoundType SOUND_TYPE_GOLD = new SoundType(1.0F, 1.0F, IafSoundRegistry.GOLD_PILE_BREAK, IafSoundRegistry.GOLD_PILE_STEP, IafSoundRegistry.GOLD_PILE_BREAK, IafSoundRegistry.GOLD_PILE_STEP, IafSoundRegistry.GOLD_PILE_STEP);

    public static final DeferredBlock<Block> LECTERN = BLOCKS.register("lectern", () -> new BlockLectern());
    public static final DeferredBlock<Block> PODIUM_OAK = BLOCKS.register("podium_oak", () -> new BlockPodium());
    public static final DeferredBlock<Block> PODIUM_BIRCH = BLOCKS.register("podium_birch", () -> new BlockPodium());
    public static final DeferredBlock<Block> PODIUM_SPRUCE = BLOCKS.register("podium_spruce", () -> new BlockPodium());
    public static final DeferredBlock<Block> PODIUM_JUNGLE = BLOCKS.register("podium_jungle", () -> new BlockPodium());
    public static final DeferredBlock<Block> PODIUM_DARK_OAK = BLOCKS.register("podium_dark_oak", () -> new BlockPodium());
    public static final DeferredBlock<Block> PODIUM_ACACIA = BLOCKS.register("podium_acacia", () -> new BlockPodium());
    public static final DeferredBlock<Block> FIRE_LILY = BLOCKS.register("fire_lily", () -> new BlockElementalFlower());
    public static final DeferredBlock<Block> FROST_LILY = BLOCKS.register("frost_lily", () -> new BlockElementalFlower());
    public static final DeferredBlock<Block> LIGHTNING_LILY = BLOCKS.register("lightning_lily", () -> new BlockElementalFlower());
    public static final DeferredBlock<Block> GOLD_PILE = BLOCKS.register("gold_pile", () -> new BlockGoldPile());
    public static final DeferredBlock<Block> SILVER_PILE = BLOCKS.register("silver_pile", () -> new BlockGoldPile());
    public static final DeferredBlock<Block> COPPER_PILE = BLOCKS.register("copper_pile", () -> new BlockGoldPile());
    public static final DeferredBlock<Block> SILVER_ORE = BLOCKS.register("silver_ore", () -> new BlockIafOre(2, 3.0F, 3.0F));
    public static final DeferredBlock<Block> SAPPHIRE_ORE = BLOCKS.register("sapphire_ore", () -> new BlockIafOre(2, 4.0F, 3.0F));
    public static final DeferredBlock<Block> COPPER_ORE = BLOCKS.register("copper_ore", () -> new BlockIafOre(0, 3.0F, 3.0F));
    public static final DeferredBlock<Block> AMYTHEST_ORE = BLOCKS.register("amythest_ore", () -> new BlockIafOre(2, 4.0F, 3.0F));
    public static final DeferredBlock<Block> SILVER_BLOCK = BLOCKS.register("silver_block", () -> new BlockGeneric(IafMaterial.METAL, 3.0F, 5.0F, SoundType.METAL));
    public static final DeferredBlock<Block> SAPPHIRE_BLOCK = BLOCKS.register("sapphire_block", () -> new BlockGeneric(IafMaterial.METAL, 3.0F, 6.0F, SoundType.METAL));
    public static final DeferredBlock<Block> COPPER_BLOCK = BLOCKS.register("copper_block", () -> new BlockGeneric(IafMaterial.METAL, 4.0F, 5.0F, SoundType.METAL));
    public static final DeferredBlock<Block> AMYTHEST_BLOCK = BLOCKS.register("amythest_block", () -> new BlockGeneric(IafMaterial.METAL, 5.0F, 6.0F, SoundType.METAL));
    public static final DeferredBlock<Block> CHARRED_DIRT = BLOCKS.register("chared_dirt", () -> new BlockReturningState(IafMaterial.DIRT, 0.5F, 0.0F, SoundType.GRAVEL, Blocks.DIRT.defaultBlockState()));
    public static final DeferredBlock<Block> CHARRED_GRASS = BLOCKS.register("chared_grass", () -> new BlockReturningState(IafMaterial.GRASS, 0.6F, 0.0F, SoundType.GRAVEL, Blocks.GRASS_BLOCK.defaultBlockState()));
    public static final DeferredBlock<Block> CHARRED_STONE = BLOCKS.register("chared_stone", () -> new BlockReturningState(IafMaterial.STONE, 1.5F, 10.0F, SoundType.STONE, Blocks.STONE.defaultBlockState()));
    public static final DeferredBlock<Block> CHARRED_COBBLESTONE = BLOCKS.register("chared_cobblestone", () -> new BlockReturningState(IafMaterial.STONE, 2F, 10.0F, SoundType.STONE, Blocks.COBBLESTONE.defaultBlockState()));
    public static final DeferredBlock<Block> CHARRED_GRAVEL = BLOCKS.register("chared_gravel", () -> new BlockFallingReturningState(IafMaterial.DIRT, 0.6F, 0F, SoundType.GRAVEL, Blocks.GRAVEL.defaultBlockState()));
    public static final DeferredBlock<Block> CHARRED_DIRT_PATH = BLOCKS.register(BlockCharedPath.getNameFromType(0), () -> new BlockCharedPath(0));
    public static final DeferredBlock<Block> ASH = BLOCKS.register("ash", () -> new BlockFallingGeneric(IafMaterial.SAND, 0.5F, 0F, SoundType.SAND));
    public static final DeferredBlock<Block> FROZEN_DIRT = BLOCKS.register("frozen_dirt", () -> new BlockReturningState(IafMaterial.DIRT, 0.5F, 0.0F, SoundType.GLASS, true, Blocks.DIRT.defaultBlockState()));
    public static final DeferredBlock<Block> FROZEN_GRASS = BLOCKS.register("frozen_grass", () -> new BlockReturningState(IafMaterial.GRASS, 0.6F, 0.0F, SoundType.GLASS, true, Blocks.GRASS_BLOCK.defaultBlockState()));
    public static final DeferredBlock<Block> FROZEN_STONE = BLOCKS.register("frozen_stone", () -> new BlockReturningState(IafMaterial.STONE, 1.5F, 1.0F, SoundType.GLASS, true, Blocks.STONE.defaultBlockState()));
    public static final DeferredBlock<Block> FROZEN_COBBLESTONE = BLOCKS.register("frozen_cobblestone", () -> new BlockReturningState(IafMaterial.STONE, 2F, 2.0F, SoundType.GLASS, true, Blocks.COBBLESTONE.defaultBlockState()));
    public static final DeferredBlock<Block> FROZEN_GRAVEL = BLOCKS.register("frozen_gravel", () -> new BlockFallingReturningState(IafMaterial.DIRT, 0.6F, 0F, SoundType.GLASS, true, Blocks.GRAVEL.defaultBlockState()));
    public static final DeferredBlock<Block> FROZEN_DIRT_PATH = BLOCKS.register(BlockCharedPath.getNameFromType(1), () -> new BlockCharedPath(1));
    public static final DeferredBlock<Block> FROZEN_SPLINTERS = BLOCKS.register("frozen_splinters", () -> new BlockGeneric(IafMaterial.WOOD, 2.0F, 1.0F, SoundType.GLASS, true));
    public static final DeferredBlock<Block> DRAGON_ICE = BLOCKS.register("dragon_ice", () -> new BlockGeneric(IafMaterial.ICE_SOLID, 0.5F, 0F, SoundType.GLASS, true));
    public static final DeferredBlock<Block> DRAGON_ICE_SPIKES = BLOCKS.register("dragon_ice_spikes", () -> new BlockIceSpikes());
    public static final DeferredBlock<Block> CRACKLED_DIRT = BLOCKS.register("crackled_dirt", () -> new BlockReturningState(IafMaterial.DIRT, 0.5F, 0.0F, SoundType.GRAVEL, Blocks.DIRT.defaultBlockState()));
    public static final DeferredBlock<Block> CRACKLED_GRASS = BLOCKS.register("crackled_grass", () -> new BlockReturningState(IafMaterial.GRASS, 0.6F, 0.0F, SoundType.GRAVEL, Blocks.GRASS_BLOCK.defaultBlockState()));
    public static final DeferredBlock<Block> CRACKLED_STONE = BLOCKS.register("crackled_stone", () -> new BlockReturningState(IafMaterial.STONE, 1.5F, 1.0F, SoundType.STONE, Blocks.STONE.defaultBlockState()));
    public static final DeferredBlock<Block> CRACKLED_COBBLESTONE = BLOCKS.register("crackled_cobblestone", () -> new BlockReturningState(IafMaterial.STONE, 2F, 2F, SoundType.STONE, Blocks.COBBLESTONE.defaultBlockState()));
    public static final DeferredBlock<Block> CRACKLED_GRAVEL = BLOCKS.register("crackled_gravel", () -> new BlockFallingReturningState(IafMaterial.DIRT, 0.6F, 0F, SoundType.GRAVEL, Blocks.GRAVEL.defaultBlockState()));
    public static final DeferredBlock<Block> CRACKLED_DIRT_PATH = BLOCKS.register(BlockCharedPath.getNameFromType(2), () -> new BlockCharedPath(2));

    public static final DeferredBlock<Block> NEST = BLOCKS.register("nest", () -> new BlockGeneric(IafMaterial.PLANT, 0.5F, 0F, SoundType.GRAVEL, false));

    public static final DeferredBlock<Block> DRAGON_SCALE_RED = BLOCKS.register("dragonscale_red", () -> new BlockDragonScales(EnumDragonEgg.RED));
    public static final DeferredBlock<Block> DRAGON_SCALE_GREEN = BLOCKS.register("dragonscale_green", () -> new BlockDragonScales(EnumDragonEgg.GREEN));
    public static final DeferredBlock<Block> DRAGON_SCALE_BRONZE = BLOCKS.register("dragonscale_bronze", () -> new BlockDragonScales(EnumDragonEgg.BRONZE));
    public static final DeferredBlock<Block> DRAGON_SCALE_GRAY = BLOCKS.register("dragonscale_gray", () -> new BlockDragonScales(EnumDragonEgg.GRAY));
    public static final DeferredBlock<Block> DRAGON_SCALE_BLUE = BLOCKS.register("dragonscale_blue", () -> new BlockDragonScales(EnumDragonEgg.BLUE));
    public static final DeferredBlock<Block> DRAGON_SCALE_WHITE = BLOCKS.register("dragonscale_white", () -> new BlockDragonScales(EnumDragonEgg.WHITE));
    public static final DeferredBlock<Block> DRAGON_SCALE_SAPPHIRE = BLOCKS.register("dragonscale_sapphire", () -> new BlockDragonScales(EnumDragonEgg.SAPPHIRE));
    public static final DeferredBlock<Block> DRAGON_SCALE_SILVER = BLOCKS.register("dragonscale_silver", () -> new BlockDragonScales(EnumDragonEgg.SILVER));
    public static final DeferredBlock<Block> DRAGON_SCALE_ELECTRIC = BLOCKS.register("dragonscale_electric", () -> new BlockDragonScales(EnumDragonEgg.ELECTRIC));
    public static final DeferredBlock<Block> DRAGON_SCALE_AMYTHEST = BLOCKS.register("dragonscale_amythest", () -> new BlockDragonScales(EnumDragonEgg.AMYTHEST));
    public static final DeferredBlock<Block> DRAGON_SCALE_COPPER = BLOCKS.register("dragonscale_copper", () -> new BlockDragonScales(EnumDragonEgg.COPPER));
    public static final DeferredBlock<Block> DRAGON_SCALE_BLACK = BLOCKS.register("dragonscale_black", () -> new BlockDragonScales(EnumDragonEgg.BLACK));

    public static final DeferredBlock<Block> DRAGON_BONE_BLOCK = BLOCKS.register("dragon_bone_block", () -> new BlockDragonBone());
    public static final DeferredBlock<Block> DRAGON_BONE_BLOCK_WALL = BLOCKS.register("dragon_bone_wall", () -> new BlockDragonBoneWall(BlockBehaviour.Properties.ofFullCopy(IafBlockRegistry.DRAGON_BONE_BLOCK.get())));
    public static final DeferredBlock<Block> DRAGONFORGE_FIRE_BRICK = BLOCKS.register(BlockDragonforgeBricks.name(0), () -> new BlockDragonforgeBricks(0));
    public static final DeferredBlock<Block> DRAGONFORGE_ICE_BRICK = BLOCKS.register(BlockDragonforgeBricks.name(1), () -> new BlockDragonforgeBricks(1));
    public static final DeferredBlock<Block> DRAGONFORGE_LIGHTNING_BRICK = BLOCKS.register(BlockDragonforgeBricks.name(2), () -> new BlockDragonforgeBricks(2));
    public static final DeferredBlock<Block> DRAGONFORGE_FIRE_INPUT = BLOCKS.register(BlockDragonforgeInput.name(0), () -> new BlockDragonforgeInput(0));
    public static final DeferredBlock<Block> DRAGONFORGE_ICE_INPUT = BLOCKS.register(BlockDragonforgeInput.name(1), () -> new BlockDragonforgeInput(1));
    public static final DeferredBlock<Block> DRAGONFORGE_LIGHTNING_INPUT = BLOCKS.register(BlockDragonforgeInput.name(2), () -> new BlockDragonforgeInput(2));
    public static final DeferredBlock<Block> DRAGONFORGE_FIRE_CORE = BLOCKS.register(BlockDragonforgeCore.name(0, true), () -> new BlockDragonforgeCore(0, true));
    public static final DeferredBlock<Block> DRAGONFORGE_ICE_CORE = BLOCKS.register(BlockDragonforgeCore.name(1, true), () -> new BlockDragonforgeCore(1, true));
    public static final DeferredBlock<Block> DRAGONFORGE_LIGHTNING_CORE = BLOCKS.register(BlockDragonforgeCore.name(2, true), () -> new BlockDragonforgeCore(2, true));
    public static final DeferredBlock<Block> DRAGONFORGE_FIRE_CORE_DISABLED = BLOCKS.register(BlockDragonforgeCore.name(0, false), () -> new BlockDragonforgeCore(0, false));
    public static final DeferredBlock<Block> DRAGONFORGE_ICE_CORE_DISABLED = BLOCKS.register(BlockDragonforgeCore.name(1, false), () -> new BlockDragonforgeCore(1, false));
    public static final DeferredBlock<Block> DRAGONFORGE_LIGHTNING_CORE_DISABLED = BLOCKS.register(BlockDragonforgeCore.name(2, false), () -> new BlockDragonforgeCore(2, false));
    public static final DeferredBlock<Block> EGG_IN_ICE = BLOCKS.register("egginice", () -> new BlockEggInIce());
    public static final DeferredBlock<Block> PIXIE_HOUSE_MUSHROOM_RED = BLOCKS.register(BlockPixieHouse.name("mushroom_red"), () -> new BlockPixieHouse());
    public static final DeferredBlock<Block> PIXIE_HOUSE_MUSHROOM_BROWN = BLOCKS.register(BlockPixieHouse.name("mushroom_brown"), () -> new BlockPixieHouse());
    public static final DeferredBlock<Block> PIXIE_HOUSE_OAK = BLOCKS.register(BlockPixieHouse.name("oak"), () -> new BlockPixieHouse());
    public static final DeferredBlock<Block> PIXIE_HOUSE_BIRCH = BLOCKS.register(BlockPixieHouse.name("birch"), () -> new BlockPixieHouse());
    public static final DeferredBlock<Block> PIXIE_HOUSE_SPRUCE = BLOCKS.register(BlockPixieHouse.name("spruce"), () -> new BlockPixieHouse());
    public static final DeferredBlock<Block> PIXIE_HOUSE_DARK_OAK = BLOCKS.register(BlockPixieHouse.name("dark_oak"), () -> new BlockPixieHouse());
    public static final DeferredBlock<Block> JAR_EMPTY = BLOCKS.register(BlockJar.name(-1), () -> new BlockJar(-1));
    public static final DeferredBlock<Block> JAR_PIXIE_0 = BLOCKS.register(BlockJar.name(0), () -> new BlockJar(0));
    public static final DeferredBlock<Block> JAR_PIXIE_1 = BLOCKS.register(BlockJar.name(1), () -> new BlockJar(1));
    public static final DeferredBlock<Block> JAR_PIXIE_2 = BLOCKS.register(BlockJar.name(2), () -> new BlockJar(2));
    public static final DeferredBlock<Block> JAR_PIXIE_3 = BLOCKS.register(BlockJar.name(3), () -> new BlockJar(3));
    public static final DeferredBlock<Block> JAR_PIXIE_4 = BLOCKS.register(BlockJar.name(4), () -> new BlockJar(4));
    public static final DeferredBlock<Block> MYRMEX_DESERT_RESIN = BLOCKS.register(BlockMyrmexResin.name(false, "desert"), () -> new BlockMyrmexResin(false));
    public static final DeferredBlock<Block> MYRMEX_DESERT_RESIN_STICKY = BLOCKS.register(BlockMyrmexResin.name(true, "desert"), () -> new BlockMyrmexResin(true));
    public static final DeferredBlock<Block> MYRMEX_JUNGLE_RESIN = BLOCKS.register(BlockMyrmexResin.name(false, "jungle"), () -> new BlockMyrmexResin(false));
    public static final DeferredBlock<Block> MYRMEX_JUNGLE_RESIN_STICKY = BLOCKS.register(BlockMyrmexResin.name(true, "jungle"), () -> new BlockMyrmexResin(true));
    public static final DeferredBlock<Block> DESERT_MYRMEX_COCOON = BLOCKS.register("desert_myrmex_cocoon", () -> new BlockMyrmexCocoon());
    public static final DeferredBlock<Block> JUNGLE_MYRMEX_COCOON = BLOCKS.register("jungle_myrmex_cocoon", () -> new BlockMyrmexCocoon());
    public static final DeferredBlock<Block> MYRMEX_DESERT_BIOLIGHT = BLOCKS.register("myrmex_desert_biolight", () -> new BlockMyrmexBiolight());
    public static final DeferredBlock<Block> MYRMEX_JUNGLE_BIOLIGHT = BLOCKS.register("myrmex_jungle_biolight", () -> new BlockMyrmexBiolight());
    public static final DeferredBlock<Block> MYRMEX_DESERT_RESIN_BLOCK = BLOCKS.register(BlockMyrmexConnectedResin.name(false, false), () -> new BlockMyrmexConnectedResin(false, false));
    public static final DeferredBlock<Block> MYRMEX_JUNGLE_RESIN_BLOCK = BLOCKS.register(BlockMyrmexConnectedResin.name(true, false), () -> new BlockMyrmexConnectedResin(true, false));
    public static final DeferredBlock<Block> MYRMEX_DESERT_RESIN_GLASS = BLOCKS.register(BlockMyrmexConnectedResin.name(false, true), () -> new BlockMyrmexConnectedResin(false, true));
    public static final DeferredBlock<Block> MYRMEX_JUNGLE_RESIN_GLASS = BLOCKS.register(BlockMyrmexConnectedResin.name(true, true), () -> new BlockMyrmexConnectedResin(true, true));
    public static final DeferredBlock<Block> DRAGONSTEEL_FIRE_BLOCK = BLOCKS.register("dragonsteel_fire_block", () -> new BlockGeneric(IafMaterial.METAL, 10.0F, 1000.0F, SoundType.METAL));
    public static final DeferredBlock<Block> DRAGONSTEEL_ICE_BLOCK = BLOCKS.register("dragonsteel_ice_block", () -> new BlockGeneric(IafMaterial.METAL, 10.0F, 1000.0F, SoundType.METAL));
    public static final DeferredBlock<Block> DRAGONSTEEL_LIGHTNING_BLOCK = BLOCKS.register("dragonsteel_lightning_block", () -> new BlockGeneric(IafMaterial.METAL, 10.0F, 1000.0F, SoundType.METAL));
    public static final DeferredBlock<BlockDreadBase> DREAD_STONE = BLOCKS.register("dread_stone", () -> new BlockDreadBase(IafMaterial.STONE, -1.0F, 100000.0F, SoundType.STONE));
    public static final DeferredBlock<BlockDreadBase> DREAD_STONE_BRICKS = BLOCKS.register("dread_stone_bricks", () -> new BlockDreadBase(IafMaterial.STONE, -1.0F, 100000.0F, SoundType.STONE));
    public static final DeferredBlock<BlockDreadBase> DREAD_STONE_BRICKS_CHISELED = BLOCKS.register("dread_stone_bricks_chiseled", () -> new BlockDreadBase(IafMaterial.STONE, -1.0F, 100000.0F, SoundType.STONE));
    public static final DeferredBlock<BlockDreadBase> DREAD_STONE_BRICKS_CRACKED = BLOCKS.register("dread_stone_bricks_cracked", () -> new BlockDreadBase(IafMaterial.STONE, -1.0F, 100000.0F, SoundType.STONE));
    public static final DeferredBlock<BlockDreadBase> DREAD_STONE_BRICKS_MOSSY = BLOCKS.register("dread_stone_bricks_mossy", () -> new BlockDreadBase(IafMaterial.STONE, -1.0F, 100000.0F, SoundType.STONE));
    public static final DeferredBlock<BlockDreadBase> DREAD_STONE_TILE = BLOCKS.register("dread_stone_tile", () -> new BlockDreadBase(IafMaterial.STONE, -1.0F, 100000.0F, SoundType.STONE));
    public static final DeferredBlock<Block> DREAD_STONE_FACE = BLOCKS.register("dread_stone_face", () -> new BlockDreadStoneFace());
    public static final DeferredBlock<Block> DREAD_TORCH = BLOCKS.register("dread_torch", () -> new BlockDreadTorch());
    public static final DeferredBlock<Block> DREAD_TORCH_WALL = BLOCKS.register("dread_torch_wall", () -> new BlockDreadTorchWall());
    public static final DeferredBlock<Block> DREAD_STONE_BRICKS_STAIRS = BLOCKS.register("dread_stone_stairs", () -> new BlockGenericStairs(DREAD_STONE_BRICKS.get().defaultBlockState()));
    public static final DeferredBlock<Block> DREAD_STONE_BRICKS_SLAB = BLOCKS.register("dread_stone_slab", () -> new SlabBlock(IafMaterial.STONE.properties().strength(10F, 10000F)));
    public static final DeferredBlock<Block> DREADWOOD_LOG = BLOCKS.register("dreadwood_log", () -> new BlockDreadWoodLog());
    public static final DeferredBlock<BlockDreadBase> DREADWOOD_PLANKS = BLOCKS.register("dreadwood_planks", () -> new BlockDreadBase(IafMaterial.WOOD, -1.0F, 100000.0F, SoundType.WOOD));
    public static final DeferredBlock<Block> DREADWOOD_PLANKS_LOCK = BLOCKS.register("dreadwood_planks_lock", () -> new BlockDreadWoodLock());
    public static final DeferredBlock<Block> DREAD_PORTAL = BLOCKS.register("dread_portal", () -> new BlockDreadPortal());
    public static final DeferredBlock<Block> DREAD_SPAWNER = BLOCKS.register("dread_spawner", () -> new BlockDreadSpawner());
    public static final DeferredBlock<Block> BURNT_TORCH = BLOCKS.register("burnt_torch", () -> new BlockBurntTorch());
    public static final DeferredBlock<Block> BURNT_TORCH_WALL = BLOCKS.register("burnt_torch_wall", () -> new BlockBurntTorchWall());
    public static final DeferredBlock<Block> GHOST_CHEST = BLOCKS.register("ghost_chest", () -> new BlockGhostChest());
    public static final DeferredBlock<Block> GRAVEYARD_SOIL = BLOCKS.register("graveyard_soil", () -> new BlockGraveyardSoil());

    @SubscribeEvent
    public static void registerBlockItems(RegisterEvent event) {
        event.register(Registries.ITEM, helper -> IafBlockRegistry.BLOCKS.getEntries().forEach(holder -> {
            Block block = holder.get();
            registerItemBlock(block).ifPresent(item -> helper.register(holder.getId(), item));
        }));
    }

    /**
     * Blocks whose item should show up in the blocks creative tab.
     */
    public static boolean isInTab(Block block) {
        return !(block instanceof INoTab) || ((INoTab) block).shouldBeInTab();
    }

    public static Optional<Item> registerItemBlock(Block block) {
        if (!(block instanceof WallTorchBlock)) {
            Item.Properties props = new Item.Properties();
            BlockItem itemBlock;
            if (block instanceof IWallBlock) {
                itemBlock = new StandingAndWallBlockItem(block, ((IWallBlock) block).wallBlock(), props, net.minecraft.core.Direction.DOWN);
            } else if (block instanceof BlockGhostChest || block instanceof BlockDreadPortal || block instanceof BlockPixieHouse) {
                itemBlock = new BlockItemWithRender(block, props);
            } else {
                itemBlock = new BlockItem(block, props);
            }
            return Optional.of(itemBlock);
        }
        return Optional.empty();
    }
}
