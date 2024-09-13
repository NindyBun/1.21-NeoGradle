package net.NindyBun.toxicbladeworks.data;

import net.NindyBun.toxicbladeworks.registries.ModDataComponents;
import net.NindyBun.toxicbladeworks.registries.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class TippedSwordRecipe extends CustomRecipe {

    public TippedSwordRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput pInput, Level pLevel) {
        if (pInput.width() * pInput.height() >= 2 && pInput.ingredientCount() == 2) {
            ItemStack i0 = pInput.getItem(0);
            ItemStack i1 = pInput.getItem(1);

            if ( (i0.getItem() instanceof SwordItem && i1.is(Items.POTION))
            || (i1.getItem() instanceof SwordItem && i0.is(Items.POTION)) ) {
                return true;
            }

            return false;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        ItemStack sword = pInput.getItem(0).getItem() instanceof SwordItem ? pInput.getItem(0) : pInput.getItem(1);
        ItemStack potion = pInput.getItem(1).is(Items.POTION) ? pInput.getItem(1) : pInput.getItem(0);

        if (!potion.is(Items.POTION)) return ItemStack.EMPTY;
        if (!(sword.getItem() instanceof SwordItem)) return ItemStack.EMPTY;

        ItemStack swordCopy = sword.copy();
        swordCopy.set(ModDataComponents.POTION_DATA.get(), potion.get(DataComponents.POTION_CONTENTS));

        return swordCopy;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<TippedSwordRecipe> getSerializer() {
        return ModRecipes.TIPPED_SWORD.get();
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput pInput) {
        NonNullList<ItemStack> list = NonNullList.withSize(pInput.size(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            ItemStack itemstack = pInput.getItem(i);
            if (itemstack.is(Items.POTION)) {
                list.set(i, Items.GLASS_BOTTLE.getDefaultInstance());
            }
        }
        return list;
    }
}
