package net.NindyBun.toxicbladeworks.registries;

import net.NindyBun.toxicbladeworks.ToxicBladeworks;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT = DeferredRegister.createDataComponents(ToxicBladeworks.MODID);

    public static final Supplier<DataComponentType<PotionContents>> POTION_DATA = DATA_COMPONENT.register("potion_data",
            () -> DataComponentType.<PotionContents>builder()
                    .persistent(PotionContents.CODEC)
                    .networkSynchronized(PotionContents.STREAM_CODEC)
                    .build());


    public static void register(IEventBus bus) {
        DATA_COMPONENT.register(bus);
    }
}
