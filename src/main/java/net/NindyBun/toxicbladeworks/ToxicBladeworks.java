package net.NindyBun.toxicbladeworks;

import net.NindyBun.toxicbladeworks.decorators.PotionBarDecorator;
import net.NindyBun.toxicbladeworks.registries.ModCreativeModTabs;
import net.NindyBun.toxicbladeworks.registries.ModDataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ToxicBladeworks.MODID)
public class ToxicBladeworks
{
    public static final String MODID = "toxicbladeworks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ToxicBladeworks(IEventBus modEventBus, ModContainer modContainer)
    {

        ModDataComponents.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        NeoForge.EVENT_BUS.register(this);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void renderItemDecorators(RegisterItemDecorationsEvent event) {
            event.register(Items.WOODEN_SWORD, PotionBarDecorator.INSTANCE);
            event.register(Items.STONE_SWORD, PotionBarDecorator.INSTANCE);
            event.register(Items.GOLDEN_SWORD, PotionBarDecorator.INSTANCE);
            event.register(Items.DIAMOND_SWORD, PotionBarDecorator.INSTANCE);
            event.register(Items.NETHERITE_SWORD, PotionBarDecorator.INSTANCE);

        }
    }
}
