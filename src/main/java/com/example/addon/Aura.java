package com.example.addon;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.systems.friends.Friends;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.Hand;

public class Aura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    // Configurações
    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder().name("range").defaultValue(4.0).build());
    private final Setting<Double> delay = sgGeneral.add(new DoubleSetting.Builder().name("delay").defaultValue(0.5).min(0.0).max(20.0).build());
    private final Setting<Boolean> silent = sgGeneral.add(new BoolSetting.Builder().name("silent").defaultValue(true).build());
    private final Setting<Boolean> shieldBreaker = sgGeneral.add(new BoolSetting.Builder().name("shield-breaker").defaultValue(true).build());
    private final Setting<Boolean> rotate = sgGeneral.add(new BoolSetting.Builder().name("rotate").defaultValue(false).build());

    public Aura() {
        super(com.example.addon.ExampleAddon.CATEGORY, "aura", "Aura profissional completo.");
    }

    @Override
    public void onTick() {
        // Pause on Use
        if (mc.player.isUsingItem()) return;

        for (Entity entity : mc.world.getEntities()) {
            if (isValid(entity)) {
                // Shield Breaker
                if (shieldBreaker.get() && entity instanceof PlayerEntity && ((PlayerEntity) entity).isBlocking()) {
                    var axe = InvUtils.findInHotbar(itemStack -> itemStack.getItem() instanceof AxeItem);
                    if (axe.found()) {
                        InvUtils.swap(axe.slot(), true);
                        mc.interactionManager.attackEntity(mc.player, entity);
                        InvUtils.swapBack();
                        return;
                    }
                }

                // Attack logic
                if (mc.player.getAttackCooldownProgress(0.5f) >= 1) {
                    if (rotate.get()) Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity));
                    mc.interactionManager.attackEntity(mc.player, entity);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }

    private boolean isValid(Entity e) {
        if (e == mc.player) return false;
        if (mc.player.distanceTo(e) > range.get()) return false;
        if (e instanceof PlayerEntity && Friends.get().isFriend((PlayerEntity) e)) return false;
        return e.isAlive();
    }
}
