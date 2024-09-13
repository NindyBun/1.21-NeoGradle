package net.NindyBun.toxicbladeworks;

import net.NindyBun.toxicbladeworks.data.Generator;
import net.NindyBun.toxicbladeworks.decorators.PotionBarDecorator;
import net.NindyBun.toxicbladeworks.registries.ModCreativeModTabs;
import net.NindyBun.toxicbladeworks.registries.ModDataComponents;
import net.NindyBun.toxicbladeworks.registries.ModRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.neoforged.neoforge.client.event.RegisterItemDecorationsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
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

import java.util.List;

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
        PotionContents potioncontents = event.getItemStack().get(ModDataComponents.POTION_DATA.get());
        if (potioncontents != null) {
            potioncontents.addPotionTooltip(event.getToolTip()::add, 1.0F, event.getContext().tickRate());
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

            PotionContents potionContents = sword.getOrDefault(ModDataComponents.POTION_DATA.get(), PotionContents.EMPTY);

            potionContents.forEachEffect(effect -> {
                if (effect.getEffect().value().isInstantenous()) {
                    effect.getEffect().value().applyInstantenousEffect(player, player, entity, effect.getAmplifier(), 1.0);
                } else {
                    entity.addEffect(effect);
                }
            });

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
