package com.github.alexthe666.iceandfire.recipe;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;


public class DragonForgeRecipe implements Recipe<DragonForgeRecipe.Input> {
    private final Ingredient input;
    private final Ingredient blood;
    private final ItemStack result;
    private final String dragonType;
    private final int cookTime;

    public DragonForgeRecipe(Ingredient input, Ingredient blood, ItemStack result, String dragonType, int cookTime) {
        this.input = input;
        this.blood = blood;
        this.result = result;
        this.dragonType = dragonType;
        this.cookTime = cookTime;
    }

    public Ingredient getInput() {
        return input;
    }

    public Ingredient getBlood() {
        return blood;
    }

    public int getCookTime() {
        return cookTime;
    }

    public String getDragonType() {
        return dragonType;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(Input inv, @NotNull Level worldIn) {
        return this.input.test(inv.input()) && this.blood.test(inv.blood()) && this.dragonType.equals(inv.dragonType());
    }

    public boolean isValidInput(ItemStack stack) {
        return this.input.test(stack);
    }

    public boolean isValidBlood(ItemStack blood) {
        return this.blood.test(blood);
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider registries) {
        return result;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Input input, HolderLookup.@NotNull Provider registries) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return IafRecipeSerializers.DRAGONFORGE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return IafRecipeRegistry.DRAGON_FORGE_TYPE.get();
    }

    /**
     * Recipe input of the dragon forge: the item to smelt, the dragon blood and the forge's dragon type.
     */
    public record Input(ItemStack input, ItemStack blood, String dragonType) implements RecipeInput {
        @Override
        public @NotNull ItemStack getItem(int index) {
            return switch (index) {
                case 0 -> input;
                case 1 -> blood;
                default -> throw new IllegalArgumentException("No item for index " + index);
            };
        }

        @Override
        public int size() {
            return 2;
        }
    }

    public static class Serializer implements RecipeSerializer<DragonForgeRecipe> {
        private static final MapCodec<DragonForgeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("input").forGetter(DragonForgeRecipe::getInput),
            Ingredient.CODEC_NONEMPTY.fieldOf("blood").forGetter(DragonForgeRecipe::getBlood),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(DragonForgeRecipe::getResult),
            Codec.STRING.fieldOf("dragon_type").forGetter(DragonForgeRecipe::getDragonType),
            Codec.INT.fieldOf("cook_time").forGetter(DragonForgeRecipe::getCookTime)
        ).apply(instance, DragonForgeRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, DragonForgeRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, DragonForgeRecipe::getInput,
            Ingredient.CONTENTS_STREAM_CODEC, DragonForgeRecipe::getBlood,
            ItemStack.STREAM_CODEC, DragonForgeRecipe::getResult,
            ByteBufCodecs.STRING_UTF8, DragonForgeRecipe::getDragonType,
            ByteBufCodecs.VAR_INT, DragonForgeRecipe::getCookTime,
            DragonForgeRecipe::new);

        @Override
        public @NotNull MapCodec<DragonForgeRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, DragonForgeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }

}
