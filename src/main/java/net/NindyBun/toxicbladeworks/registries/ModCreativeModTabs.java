package net.NindyBun.toxicbladeworks.registries;

import net.NindyBun.toxicbladeworks.ToxicBladeworks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToxicBladeworks.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = CREATIVE_MODE_TABS.register("toxicbladeworks_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + ToxicBladeworks.MODID + ".creativeTab")) //The language key for the title of your CreativeModeTab
            .icon(() -> Items.GLASS_BOTTLE.getDefaultInstance())
            .displayItems((parameters, output) -> {
            }).build());

    public static void register(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }

}
