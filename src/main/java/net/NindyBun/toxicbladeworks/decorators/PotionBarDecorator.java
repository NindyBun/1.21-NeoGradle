package net.NindyBun.toxicbladeworks.decorators;

import com.mojang.blaze3d.systems.RenderSystem;
import net.NindyBun.toxicbladeworks.registries.ModDataComponents;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.client.IItemDecorator;

import java.awt.*;

public class PotionBarDecorator implements IItemDecorator {
    public static final PotionBarDecorator INSTANCE = new PotionBarDecorator();

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        if (!stack.has(ModDataComponents.POTION_DATA.get())) return false;

        ModDataComponents.Potion_Data potionData = stack.get(ModDataComponents.POTION_DATA.get());
        PotionContents potionContents = potionData.potionContents();
        int potionVolume = potionData.volume();

        RenderSystem.disableBlend();
        int i = (int) Math.floor(((double) potionVolume /250) * 13);
        int x = xOffset + 2;
        int y = yOffset + 12;
        //guiGraphics.fill(x, y, x+13, y+1, 190, new Color(0f, 0f, 0.5f, 0.8f).getRGB());
        guiGraphics.fill(x, y, x+i, y+1, 190, potionContents.getColor());
        RenderSystem.enableBlend();

        return false;
    }
}
