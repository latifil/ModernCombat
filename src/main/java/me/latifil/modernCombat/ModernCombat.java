package me.latifil.modernCombat;

import me.latifil.modernCombat.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ModernCombat extends JavaPlugin {
    private static ModernCombat instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        ModuleManager moduleManager = new ModuleManager();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ModernCombat getInstance() {
        return instance;
    }
}
