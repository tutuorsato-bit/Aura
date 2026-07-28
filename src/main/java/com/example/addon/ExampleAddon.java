package com.example.addon;

import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleAddon extends MeteorAddon {
    public static final Logger LOG = LoggerFactory.getLogger("meu-aura-addon");
    public static final String CATEGORY = "AuraAddon";

    @Override
    public void onInitialize() {
        LOG.info("Inicializando Aura Addon!");
        Modules.get().add(new Aura());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }
}
