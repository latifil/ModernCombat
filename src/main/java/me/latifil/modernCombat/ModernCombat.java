package me.latifil.modernCombat;

import org.bukkit.plugin.java.JavaPlugin;

public final class ModernCombat extends JavaPlugin {
    private static ModernCombat instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ModernCombat getInstance() {
        return instance;
    }
}
