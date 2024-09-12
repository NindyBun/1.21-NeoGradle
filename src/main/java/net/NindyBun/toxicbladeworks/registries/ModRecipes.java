package net.NindyBun.toxicbladeworks.registries;

import net.NindyBun.toxicbladeworks.ToxicBladeworks;
import net.NindyBun.toxicbladeworks.data.TippedSwordRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE = DeferredRegister.create(Registries.RECIPE_SERIALIZER, ToxicBladeworks.MODID);

    public static final Supplier<RecipeSerializer<TippedSwordRecipe>> TIPPED_SWORD = RECIPE.register("tipped_sword", () -> new SimpleCraftingRecipeSerializer<>(TippedSwordRecipe::new));

    public static void register(IEventBus bus) {
        RECIPE.register(bus);
    }
}
