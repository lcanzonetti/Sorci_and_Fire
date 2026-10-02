package com.github.alexthe666.iceandfire.client;

import net.neoforged.fml.common.EventBusSubscriber;

import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.gui.IafGuiRegistry;
import com.github.alexthe666.iceandfire.client.model.*;
import com.github.alexthe666.iceandfire.client.model.animator.FireDragonTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.IceDragonTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.LightningTabulaDragonAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.SeaSerpentTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.*;
import com.github.alexthe666.iceandfire.client.render.entity.*;
import com.github.alexthe666.iceandfire.client.render.tile.*;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.github.alexthe666.iceandfire.item.ItemDragonHorn;
import com.github.alexthe666.iceandfire.item.ItemSummoningCrystal;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.TextureStitchEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD, modid = IceAndFire.MODID)
public class IafClientSetup {

    public static TabulaModel FIRE_DRAGON_BASE_MODEL;
    public static TabulaModel ICE_DRAGON_BASE_MODEL;
    public static TabulaModel SEA_SERPENT_BASE_MODEL;
    public static TabulaModel LIGHTNING_DRAGON_BASE_MODEL;
    private static ShaderInstance rendertypeDreadPortalShader;
    private static ShaderInstance rendertypeScalableTextureShader;
    public static final ResourceLocation GHOST_CHEST_LOCATION = ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "models/ghost/ghost_chest");
    public static final ResourceLocation GHOST_CHEST_LEFT_LOCATION = ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "models/ghost/ghost_chest_left");
    public static final ResourceLocation GHOST_CHEST_RIGHT_LOCATION = ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "models/ghost/ghost_chest_right");


    public static void clientInit() {
        EntityRenderers.register(IafEntityRegistry.FIRE_DRAGON.get(), x -> new RenderDragonBase(x, FIRE_DRAGON_BASE_MODEL, 0));
        EntityRenderers.register(IafEntityRegistry.ICE_DRAGON.get(), manager -> new RenderDragonBase(manager, ICE_DRAGON_BASE_MODEL, 1));
        EntityRenderers.register(IafEntityRegistry.LIGHTNING_DRAGON.get(), manager -> new RenderLightningDragon(manager, LIGHTNING_DRAGON_BASE_MODEL, 2));
        EntityRenderers.register(IafEntityRegistry.DRAGON_EGG.get(), () -> new RenderDragonEgg());
        EntityRenderers.register(IafEntityRegistry.DRAGON_ARROW.get(), () -> new RenderDragonArrow());
        EntityRenderers.register(IafEntityRegistry.DRAGON_SKULL.get(), manager -> new RenderDragonSkull(manager, FIRE_DRAGON_BASE_MODEL, ICE_DRAGON_BASE_MODEL, LIGHTNING_DRAGON_BASE_MODEL));
        EntityRenderers.register(IafEntityRegistry.FIRE_DRAGON_CHARGE.get(), manager -> new RenderDragonFireCharge(manager, true));
        EntityRenderers.register(IafEntityRegistry.ICE_DRAGON_CHARGE.get(), manager -> new RenderDragonFireCharge(manager, false));
        EntityRenderers.register(IafEntityRegistry.LIGHTNING_DRAGON_CHARGE.get(), () -> new RenderDragonLightningCharge());
        EntityRenderers.register(IafEntityRegistry.HIPPOGRYPH_EGG.get(), () -> new ThrownItemRenderer());
        EntityRenderers.register(IafEntityRegistry.HIPPOGRYPH.get(), () -> new RenderHippogryph());
        EntityRenderers.register(IafEntityRegistry.STONE_STATUE.get(), () -> new RenderStoneStatue());
        EntityRenderers.register(IafEntityRegistry.GORGON.get(), () -> new RenderGorgon());
        EntityRenderers.register(IafEntityRegistry.PIXIE.get(), () -> new RenderPixie());
        EntityRenderers.register(IafEntityRegistry.CYCLOPS.get(), () -> new RenderCyclops());
        EntityRenderers.register(IafEntityRegistry.SIREN.get(), () -> new RenderSiren());
        EntityRenderers.register(IafEntityRegistry.HIPPOCAMPUS.get(), () -> new RenderHippocampus());
        EntityRenderers.register(IafEntityRegistry.DEATH_WORM.get(), () -> new RenderDeathWorm());
        EntityRenderers.register(IafEntityRegistry.DEATH_WORM_EGG.get(), () -> new ThrownItemRenderer());
        EntityRenderers.register(IafEntityRegistry.COCKATRICE.get(), () -> new RenderCockatrice());
        EntityRenderers.register(IafEntityRegistry.COCKATRICE_EGG.get(), () -> new ThrownItemRenderer());
        EntityRenderers.register(IafEntityRegistry.STYMPHALIAN_BIRD.get(), () -> new RenderStymphalianBird());
        EntityRenderers.register(IafEntityRegistry.STYMPHALIAN_FEATHER.get(), () -> new RenderStymphalianFeather());
        EntityRenderers.register(IafEntityRegistry.STYMPHALIAN_ARROW.get(), () -> new RenderStymphalianArrow());
        EntityRenderers.register(IafEntityRegistry.TROLL.get(), () -> new RenderTroll());
        EntityRenderers.register(IafEntityRegistry.MYRMEX_WORKER.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexWorker(), 0.5F));
        EntityRenderers.register(IafEntityRegistry.MYRMEX_SOLDIER.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexSoldier(), 0.75F));
        EntityRenderers.register(IafEntityRegistry.MYRMEX_QUEEN.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexQueen(), 1.25F));
        EntityRenderers.register(IafEntityRegistry.MYRMEX_EGG.get(), () -> new RenderMyrmexEgg());
        EntityRenderers.register(IafEntityRegistry.MYRMEX_SENTINEL.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexSentinel(), 0.85F));
        EntityRenderers.register(IafEntityRegistry.MYRMEX_ROYAL.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexRoyal(), 0.75F));
        EntityRenderers.register(IafEntityRegistry.MYRMEX_SWARMER.get(), manager -> new RenderMyrmexBase(manager, new ModelMyrmexRoyal(), 0.25F));
        EntityRenderers.register(IafEntityRegistry.AMPHITHERE.get(), () -> new RenderAmphithere());
        EntityRenderers.register(IafEntityRegistry.AMPHITHERE_ARROW.get(), () -> new RenderAmphithereArrow());
        EntityRenderers.register(IafEntityRegistry.SEA_SERPENT.get(), manager -> new RenderSeaSerpent(manager, SEA_SERPENT_BASE_MODEL));
        EntityRenderers.register(IafEntityRegistry.SEA_SERPENT_BUBBLES.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.SEA_SERPENT_ARROW.get(), () -> new RenderSeaSerpentArrow());
        EntityRenderers.register(IafEntityRegistry.CHAIN_TIE.get(), () -> new RenderChainTie());
        EntityRenderers.register(IafEntityRegistry.PIXIE_CHARGE.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.TIDE_TRIDENT.get(), () -> new RenderTideTrident());
        EntityRenderers.register(IafEntityRegistry.MOB_SKULL.get(), manager -> new RenderMobSkull(manager, SEA_SERPENT_BASE_MODEL));
        EntityRenderers.register(IafEntityRegistry.DREAD_SCUTTLER.get(), () -> new RenderDreadScuttler());
        EntityRenderers.register(IafEntityRegistry.DREAD_GHOUL.get(), () -> new RenderDreadGhoul());
        EntityRenderers.register(IafEntityRegistry.DREAD_BEAST.get(), () -> new RenderDreadBeast());
        EntityRenderers.register(IafEntityRegistry.DREAD_SCUTTLER.get(), () -> new RenderDreadScuttler());
        EntityRenderers.register(IafEntityRegistry.DREAD_THRALL.get(), () -> new RenderDreadThrall());
        EntityRenderers.register(IafEntityRegistry.DREAD_LICH.get(), () -> new RenderDreadLich());
        EntityRenderers.register(IafEntityRegistry.DREAD_LICH_SKULL.get(), () -> new RenderDreadLichSkull());
        EntityRenderers.register(IafEntityRegistry.DREAD_KNIGHT.get(), () -> new RenderDreadKnight());
        EntityRenderers.register(IafEntityRegistry.DREAD_HORSE.get(), () -> new RenderDreadHorse());
        EntityRenderers.register(IafEntityRegistry.HYDRA.get(), () -> new RenderHydra());
        EntityRenderers.register(IafEntityRegistry.HYDRA_BREATH.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.HYDRA_ARROW.get(), () -> new RenderHydraArrow());
        EntityRenderers.register(IafEntityRegistry.SLOW_MULTIPART.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.DRAGON_MULTIPART.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.CYCLOPS_MULTIPART.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.HYDRA_MULTIPART.get(), () -> new RenderNothing());
        EntityRenderers.register(IafEntityRegistry.GHOST.get(), () -> new RenderGhost());
        EntityRenderers.register(IafEntityRegistry.GHOST_SWORD.get(), () -> new RenderGhostSword());

        BlockEntityRenderers.register(IafTileEntityRegistry.PODIUM.get(), () -> new RenderPodium());
        BlockEntityRenderers.register(IafTileEntityRegistry.IAF_LECTERN.get(), () -> new RenderLectern());
        BlockEntityRenderers.register(IafTileEntityRegistry.EGG_IN_ICE.get(), () -> new RenderEggInIce());
        BlockEntityRenderers.register(IafTileEntityRegistry.PIXIE_HOUSE.get(), () -> new RenderPixieHouse());
        BlockEntityRenderers.register(IafTileEntityRegistry.PIXIE_JAR.get(), () -> new RenderJar());
        BlockEntityRenderers.register(IafTileEntityRegistry.DREAD_PORTAL.get(), () -> new RenderDreadPortal());
        BlockEntityRenderers.register(IafTileEntityRegistry.DREAD_SPAWNER.get(), () -> new RenderDreadSpawner());
        BlockEntityRenderers.register(IafTileEntityRegistry.GHOST_CHEST.get(), () -> new RenderGhostChest());

        // TODO: Remove in future releases
        // This has been implemented because some mods don't know how to properly register things
        if (Sheets.getBannerMaterial(IafRecipeRegistry.PATTERN_DREAD) == null)
        {
            IceAndFire.LOGGER.error("Some mod(s) you're using incorrectly registers things! This WILL break other mods banner patterns. Ice and fire will attempt to fix things so the game doesn't crash");
            Sheets.BANNER_MATERIALS = Arrays.stream(BannerPattern.values()).collect(Collectors.toMap(Function.identity(), Sheets::createBannerMaterial));
            Sheets.SHIELD_MATERIALS = Arrays.stream(BannerPattern.values()).collect(Collectors.toMap(Function.identity(), Sheets::createShieldMaterial));
        }
    }

    @SubscribeEvent
    public static void setupShaders(RegisterShadersEvent event) throws IOException {
        ResourceManager manager = event.getResourceManager();
        event.registerShader(new ShaderInstance(manager, ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "rendertype_dread_portal"), DefaultVertexFormat.POSITION_COLOR), (p_172782_) -> {
            rendertypeDreadPortalShader = p_172782_;
        });
        event.registerShader(new ShaderInstance(manager, ResourceLocation.fromNamespaceAndPath(IceAndFire.MODID, "rendertype_scalable_texture"), DefaultVertexFormat.NEW_ENTITY), (p_172782_) -> {
            rendertypeScalableTextureShader = p_172782_;
        });
    }

    public static ShaderInstance getRendertypeDreadPortalShader() {
        return rendertypeDreadPortalShader;
    }

    public static ShaderInstance getRendertypeScalableTextureShader() {
        return rendertypeScalableTextureShader;
    }

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
            return;
        }
        event.addSprite(GHOST_CHEST_LOCATION);
        event.addSprite(GHOST_CHEST_RIGHT_LOCATION);
        event.addSprite(GHOST_CHEST_LEFT_LOCATION);
    }

    @SubscribeEvent
    public static void setupClient(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            IafGuiRegistry.register();
            EnumSeaSerpentAnimations.initializeSerpentModels();
            DragonAnimationsLibrary.register(EnumDragonPoses.values(), EnumDragonModelTypes.values());

            try {
                SEA_SERPENT_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/seaserpent/seaserpent"), new SeaSerpentTabulaModelAnimator());
                FIRE_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/firedragon/firedragon_Ground"), new FireDragonTabulaModelAnimator());
                ICE_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/icedragon/icedragon_Ground"), new IceDragonTabulaModelAnimator());
                LIGHTNING_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/lightningdragon/lightningdragon_Ground"), new LightningTabulaDragonAnimator());
            } catch (IOException e) {
                e.printStackTrace();
            }

        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.GOLD_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.SILVER_PILE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.LECTERN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PODIUM_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PODIUM_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PODIUM_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PODIUM_JUNGLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PODIUM_ACACIA.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PODIUM_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.FIRE_LILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.FROST_LILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.LIGHTNING_LILY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.DRAGON_ICE_SPIKES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.MYRMEX_DESERT_RESIN_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.MYRMEX_DESERT_RESIN_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.MYRMEX_JUNGLE_RESIN_BLOCK.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.MYRMEX_JUNGLE_RESIN_GLASS.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.MYRMEX_DESERT_BIOLIGHT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.MYRMEX_JUNGLE_BIOLIGHT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.DREAD_STONE_FACE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.DREAD_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.BURNT_TORCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.EGG_IN_ICE.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.JAR_EMPTY.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.JAR_PIXIE_0.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.JAR_PIXIE_1.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.JAR_PIXIE_2.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.JAR_PIXIE_3.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.JAR_PIXIE_4.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PIXIE_HOUSE_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PIXIE_HOUSE_BIRCH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PIXIE_HOUSE_SPRUCE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.PIXIE_HOUSE_DARK_OAK.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.DREAD_SPAWNER.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.DREAD_TORCH_WALL.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(IafBlockRegistry.BURNT_TORCH_WALL.get(), RenderType.cutout());
        ItemPropertyFunction pulling = ItemProperties.getProperty(Items.BOW, ResourceLocation.parse("pulling"));
        ItemPropertyFunction pull = (stack, worldIn, entity, p) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                ItemDragonBow item = ((ItemDragonBow) stack.getItem());
                return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F;
            }
        };

            ItemProperties.register(IafItemRegistry.DRAGON_BOW.get().asItem(), ResourceLocation.parse("pulling"), pulling);
            ItemProperties.register(IafItemRegistry.DRAGON_BOW.get().asItem(), ResourceLocation.parse("pull"), pull);
            ItemProperties.register(IafItemRegistry.DRAGON_HORN.get(), ResourceLocation.parse("iceorfire"), (stack, level, entity, p) -> {
                return ItemDragonHorn.getDragonType(stack) * 0.25F;
            });
            ItemProperties.register(IafItemRegistry.SUMMONING_CRYSTAL_FIRE.get(), ResourceLocation.parse("has_dragon"), (stack, level, entity, p) -> {
                return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
            });
            ItemProperties.register(IafItemRegistry.SUMMONING_CRYSTAL_ICE.get(), ResourceLocation.parse("has_dragon"), (stack, level, entity, p) -> {
                return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
            });
            ItemProperties.register(IafItemRegistry.SUMMONING_CRYSTAL_LIGHTNING.get(), ResourceLocation.parse("has_dragon"), (stack, level, entity, p) -> {
                return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
            });
            ItemProperties.register(IafItemRegistry.TIDE_TRIDENT.get(), ResourceLocation.parse("throwing"), (stack, level, entity, p) -> {
                return entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
            });
        });
    }

}
