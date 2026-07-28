package com.example.addon;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.settings.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class Aura extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> range = sgGeneral.add(new DoubleSetting.Builder()
        .name("range")
        .description("Raio de alcance.")
        .defaultValue(4.0)
        .build());

    public Aura() {
        super(com.example.addon.ExampleAddon.CATEGORY, "aura", "Aura simples.");
    }

    @Override
    public void onTick() {
        for (var entity : mc.world.getEntities()) {
            if (entity instanceof PlayerEntity && entity != mc.player && mc.player.distanceTo(entity) <= range.get()) {
                if (mc.player.getAttackCooldownProgress(0.5f) >= 1) {
                    mc.interactionManager.attackEntity(mc.player, entity);
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}
