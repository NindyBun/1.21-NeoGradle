package net.NindyBun.toxicbladeworks;

import net.NindyBun.toxicbladeworks.data.Generator;
import net.NindyBun.toxicbladeworks.decorators.PotionBarDecorator;
import net.NindyBun.toxicbladeworks.registries.ModCreativeModTabs;
import net.NindyBun.toxicbladeworks.registries.ModDataComponents;
import net.NindyBun.toxicbladeworks.registries.ModRecipes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import java.util.concurrent.atomic.AtomicInteger;

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
        ModRecipes.register(modEventBus);
        modEventBus.addListener(Generator::gatherData);
        NeoForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void itemTooltip(ItemTooltipEvent event) {
        ModDataComponents.Potion_Data potionData = event.getItemStack().get(ModDataComponents.POTION_DATA.get());
        if (potionData != null) {
            potionData.potionContents().addPotionTooltip(event.getToolTip()::add, 1.0F, event.getContext().tickRate());
            event.getToolTip().add(Component.translatable("tooltip."+ToxicBladeworks.MODID+".potion_volume", potionData.volume()).withStyle(ChatFormatting.AQUA));
        }
    }

    @SubscribeEvent
    public void hitEntity(AttackEntityEvent event) {
        if (!event.getTarget().level().isClientSide) {
            LivingEntity entity = (LivingEntity) event.getTarget();
            if (!entity.isAlive()) return;

            Player player = event.getEntity();

            ItemStack sword = player.getMainHandItem();
            if (!(sword.getItem() instanceof SwordItem)) return;

            ModDataComponents.Potion_Data potionContents = sword.get(ModDataComponents.POTION_DATA.get());
            if (potionContents == null) return;
            if (potionContents.volume() == 0) return;

            AtomicInteger volume = new AtomicInteger(potionContents.volume());
            potionContents.potionContents().forEachEffect(effect -> {
                if (effect.getEffect().value().isInstantenous()) {
                    effect.getEffect().value().applyInstantenousEffect(player, player, entity, effect.getAmplifier(), 1.0);
                } else {
                    entity.addEffect(effect);
                }
                volume.addAndGet(-5);
            });
            sword.set(ModDataComponents.POTION_DATA.get(), new ModDataComponents.Potion_Data(potionContents.potionContents(), volume.get()));
        }
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
