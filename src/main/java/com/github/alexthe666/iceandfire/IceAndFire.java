package com.github.alexthe666.iceandfire;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.ClientProxyFactory;
import com.github.alexthe666.iceandfire.config.ConfigHolder;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.IafVillagerRegistry;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.inventory.IafContainerRegistry;
import com.github.alexthe666.iceandfire.item.IafArmorMaterial;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.loot.IafLootRegistry;
import com.github.alexthe666.iceandfire.message.*;
import com.github.alexthe666.iceandfire.misc.IafCreativeTabs;
import com.github.alexthe666.iceandfire.misc.IafDataComponents;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import com.github.alexthe666.iceandfire.recipe.IafRecipeSerializers;
import com.github.alexthe666.iceandfire.world.IafBiomeModifiers;
import com.github.alexthe666.iceandfire.world.IafPlacementFilterRegistry;
import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(IceAndFire.MODID)
public class IceAndFire {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "iceandfire";
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    public static boolean DEBUG = true;
    public static String VERSION = "UNKNOWN";
    public static CommonProxy PROXY = FMLEnvironment.dist.isClient() ? ClientProxyFactory.create() : new CommonProxy();

    public IceAndFire(IEventBus modBus, ModContainer container) {
        VERSION = container.getModInfo().getVersion().toString();

        container.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        container.registerConfig(ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        PROXY.init();

        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
        NeoForge.EVENT_BUS.addListener((ServerAboutToStartEvent event) -> IafVillagerRegistry.addVillageHouses(event.getServer()));

        IafDataComponents.COMPONENTS.register(modBus);
        IafBlockRegistry.BLOCKS.register(modBus);
        IafItemRegistry.ITEMS.register(modBus);
        IafArmorMaterial.ARMOR_MATERIALS.register(modBus);
        IafCreativeTabs.TABS.register(modBus);
        IafEntityRegistry.ENTITIES.register(modBus);
        IafTileEntityRegistry.TYPES.register(modBus);
        IafPlacementFilterRegistry.PLACEMENT_MODIFIER_TYPES.register(modBus);
        IafWorldRegistry.FEATURES.register(modBus);
        IafWorldRegistry.STRUCTURE_TYPES.register(modBus);
        IafBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modBus);
        IafContainerRegistry.CONTAINERS.register(modBus);
        IafRecipeSerializers.SERIALIZERS.register(modBus);
        IafRecipeRegistry.RECIPE_TYPE.register(modBus);
        IafProcessors.PROCESSORS.register(modBus);
        IafLootRegistry.LOOT_FUNCTIONS.register(modBus);

        IafVillagerRegistry.POI_TYPES.register(modBus);
        IafVillagerRegistry.PROFESSIONS.register(modBus);

        modBus.addListener(this::setup);
        modBus.addListener(this::setupComplete);
        modBus.addListener(this::setupClient);
        modBus.addListener(IceAndFire::registerPayloads);
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        LOGGER.info(IafWorldRegistry.LOADED_FEATURES);
        LOGGER.info(IafEntityRegistry.LOADED_ENTITIES);
    }

    public static void sendMSGToServer(CustomPacketPayload message) {
        PacketDistributor.sendToServer(message);
    }

    public static void sendMSGToAll(CustomPacketPayload message) {
        PacketDistributor.sendToAllPlayers(message);
    }

    public static void sendMSGToPlayer(CustomPacketPayload message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }

    private static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        // All messages used to share one bidirectional SimpleChannel, so keep them bidirectional.
        final PayloadRegistrar registrar = event.registrar(MODID).versioned(PROTOCOL_VERSION);
        registrar.playBidirectional(MessageDaytime.TYPE, MessageDaytime.CODEC, MessageDaytime.Handler::handle);
        registrar.playBidirectional(MessageDeathWormHitbox.TYPE, MessageDeathWormHitbox.CODEC, MessageDeathWormHitbox.Handler::handle);
        registrar.playBidirectional(MessageDragonControl.TYPE, MessageDragonControl.CODEC, MessageDragonControl.Handler::handle);
        registrar.playBidirectional(MessageDragonSetBurnBlock.TYPE, MessageDragonSetBurnBlock.CODEC, MessageDragonSetBurnBlock.Handler::handle);
        registrar.playBidirectional(MessageDragonSyncFire.TYPE, MessageDragonSyncFire.CODEC, MessageDragonSyncFire.Handler::handle);
        registrar.playBidirectional(MessageGetMyrmexHive.TYPE, MessageGetMyrmexHive.CODEC, MessageGetMyrmexHive.Handler::handle);
        registrar.playBidirectional(MessageMyrmexSettings.TYPE, MessageMyrmexSettings.CODEC, MessageMyrmexSettings.Handler::handle);
        registrar.playBidirectional(MessageHippogryphArmor.TYPE, MessageHippogryphArmor.CODEC, MessageHippogryphArmor.Handler::handle);
        registrar.playBidirectional(MessageMultipartInteract.TYPE, MessageMultipartInteract.CODEC, MessageMultipartInteract.Handler::handle);
        registrar.playBidirectional(MessagePlayerHitMultipart.TYPE, MessagePlayerHitMultipart.CODEC, MessagePlayerHitMultipart.Handler::handle);
        registrar.playBidirectional(MessageSetMyrmexHiveNull.TYPE, MessageSetMyrmexHiveNull.CODEC, MessageSetMyrmexHiveNull.Handler::handle);
        registrar.playBidirectional(MessageSirenSong.TYPE, MessageSirenSong.CODEC, MessageSirenSong.Handler::handle);
        registrar.playBidirectional(MessageSpawnParticleAt.TYPE, MessageSpawnParticleAt.CODEC, MessageSpawnParticleAt.Handler::handle);
        registrar.playBidirectional(MessageStartRidingMob.TYPE, MessageStartRidingMob.CODEC, MessageStartRidingMob.Handler::handle);
        registrar.playBidirectional(MessageUpdatePixieHouse.TYPE, MessageUpdatePixieHouse.CODEC, MessageUpdatePixieHouse.Handler::handle);
        registrar.playBidirectional(MessageUpdatePixieHouseModel.TYPE, MessageUpdatePixieHouseModel.CODEC, MessageUpdatePixieHouseModel.Handler::handle);
        registrar.playBidirectional(MessageUpdatePixieJar.TYPE, MessageUpdatePixieJar.CODEC, MessageUpdatePixieJar.Handler::handle);
        registrar.playBidirectional(MessageUpdatePodium.TYPE, MessageUpdatePodium.CODEC, MessageUpdatePodium.Handler::handle);
        registrar.playBidirectional(MessageUpdateDragonforge.TYPE, MessageUpdateDragonforge.CODEC, MessageUpdateDragonforge.Handler::handle);
        registrar.playBidirectional(MessageUpdateLectern.TYPE, MessageUpdateLectern.CODEC, MessageUpdateLectern.Handler::handle);
        registrar.playBidirectional(MessageSyncPath.TYPE, MessageSyncPath.CODEC, (message, context) -> message.handle(context));
        registrar.playBidirectional(MessageSyncPathReached.TYPE, MessageSyncPathReached.CODEC, (message, context) -> message.handle(context));
        registrar.playBidirectional(MessageSwingArm.TYPE, MessageSwingArm.CODEC, MessageSwingArm.Handler::handle);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PROXY.setup();
            IafItemRegistry.setRepairMaterials();
            IafRecipeRegistry.registerDispenserBehaviors();
            IafVillagerRegistry.setup();
            IafLootRegistry.init();
        });
    }

    private void setupClient(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> PROXY.clientInit());
    }

    private void setupComplete(final FMLLoadCompleteEvent event) {
        PROXY.postInit();
    }

}
