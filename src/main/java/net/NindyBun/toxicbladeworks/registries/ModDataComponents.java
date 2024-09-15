package net.NindyBun.toxicbladeworks.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.NindyBun.toxicbladeworks.ToxicBladeworks;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT = DeferredRegister.createDataComponents(ToxicBladeworks.MODID);

    public static final Supplier<DataComponentType<Potion_Data>> POTION_DATA = DATA_COMPONENT.register("potion_data",
            () -> DataComponentType.<Potion_Data>builder()
                    .persistent(Potion_Data.CODEC)
                    .networkSynchronized(Potion_Data.STREAM_CODEC)
                    .build());

    public static void register(IEventBus bus) {
        DATA_COMPONENT.register(bus);
    }

    public record Potion_Data(PotionContents potionContents, int volume) {
        public static final Codec<Potion_Data> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                PotionContents.CODEC.fieldOf("potion_content").forGetter(Potion_Data::potionContents),
                Codec.INT.fieldOf("potion_volume").forGetter(Potion_Data::volume)
        ).apply(instance, Potion_Data::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Potion_Data> STREAM_CODEC = StreamCodec.composite(
                PotionContents.STREAM_CODEC,
                Potion_Data::potionContents,
                ByteBufCodecs.INT,
                Potion_Data::volume,
                Potion_Data::new
        );
    }
}
