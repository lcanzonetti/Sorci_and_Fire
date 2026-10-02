package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = IceAndFire.MODID, bus = EventBusSubscriber.Bus.MOD)
public class IafDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        event.getGenerator().addProvider(event.includeServer(), new ItemTags(output, lookup, event.getExistingFileHelper()));
    }

    /**
     * Since 1.21 enchantment applicability is tag driven. These tags reproduce the 1.18 class-based
     * enchantment categories for every Ice and Fire item.
     */
    private static class ItemTags extends ItemTagsProvider {

        ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup, ExistingFileHelper helper) {
            super(output, lookup, CompletableFuture.completedFuture(TagLookup.empty()), IceAndFire.MODID, helper);
        }

        @Override
        protected void addTags(@NotNull HolderLookup.Provider provider) {
            for (Item item : BuiltInRegistries.ITEM) {
                if (!IceAndFire.MODID.equals(BuiltInRegistries.ITEM.getKey(item).getNamespace())) {
                    continue;
                }
                if (item instanceof SwordItem) {
                    tag(net.minecraft.tags.ItemTags.SWORDS).add(item);
                }
                if (item instanceof AxeItem) {
                    tag(net.minecraft.tags.ItemTags.AXES).add(item);
                }
                if (item instanceof PickaxeItem) {
                    tag(net.minecraft.tags.ItemTags.PICKAXES).add(item);
                }
                if (item instanceof ShovelItem) {
                    tag(net.minecraft.tags.ItemTags.SHOVELS).add(item);
                }
                if (item instanceof HoeItem) {
                    tag(net.minecraft.tags.ItemTags.HOES).add(item);
                }
                if (item instanceof ArmorItem armor) {
                    switch (armor.getType()) {
                        case HELMET -> tag(net.minecraft.tags.ItemTags.HEAD_ARMOR).add(item);
                        case CHESTPLATE -> tag(net.minecraft.tags.ItemTags.CHEST_ARMOR).add(item);
                        case LEGGINGS -> tag(net.minecraft.tags.ItemTags.LEG_ARMOR).add(item);
                        case BOOTS -> tag(net.minecraft.tags.ItemTags.FOOT_ARMOR).add(item);
                        default -> {
                        }
                    }
                }
                if (item instanceof BowItem) {
                    tag(net.minecraft.tags.ItemTags.BOW_ENCHANTABLE).add(item);
                }
                if (item instanceof TridentItem) {
                    tag(net.minecraft.tags.ItemTags.TRIDENT_ENCHANTABLE).add(item);
                }
                // 1.18 BREAKABLE category: every damageable item could take Unbreaking and Mending
                if (item.components().has(net.minecraft.core.component.DataComponents.MAX_DAMAGE)) {
                    tag(net.minecraft.tags.ItemTags.DURABILITY_ENCHANTABLE).add(item);
                }
            }
        }
    }
}
