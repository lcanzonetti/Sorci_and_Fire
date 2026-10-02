package com.github.alexthe666.iceandfire.compat.jei;

import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * JEI category for one dragon forge type (fire, ice or lightning).
 */
public class DragonForgeCategory implements IRecipeCategory<DragonForgeRecipe> {

    private final RecipeType<DragonForgeRecipe> recipeType;
    private final Component title;
    private final DragonForgeDrawable drawable;

    public DragonForgeCategory(RecipeType<DragonForgeRecipe> recipeType, String dragonType) {
        this.recipeType = recipeType;
        this.title = Component.translatable("iceandfire." + dragonType + "_dragon_forge");
        this.drawable = new DragonForgeDrawable(dragonType);
    }

    @Override
    public @NotNull RecipeType<DragonForgeRecipe> getRecipeType() {
        return recipeType;
    }

    @Override
    public @NotNull Component getTitle() {
        return title;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return drawable;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull DragonForgeRecipe recipe, @NotNull IFocusGroup focuses) {
        // JEI 1.18 item slots were placed at the stack's top-left minus one pixel border
        builder.addSlot(RecipeIngredientRole.INPUT, 65, 30).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 83, 30).addIngredients(recipe.getBlood());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 145, 31).addItemStack(recipe.getResult());
    }
}
