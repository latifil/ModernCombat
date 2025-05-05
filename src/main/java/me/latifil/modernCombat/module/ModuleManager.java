package me.latifil.modernCombat.module;

import java.util.stream.Stream;

import me.latifil.modernCombat.ModernCombat;
import me.latifil.modernCombat.module.modules.AttackSpeedModule;
import me.latifil.modernCombat.module.modules.SwordBlockingModule;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ModuleManager {
    public ModuleManager() {
        registerModules();
    }

    public void registerModules() {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        Stream.of(
                new SwordBlockingModule(),
                new AttackSpeedModule()
        ).forEach(listener -> pluginManager.registerEvents(listener, ModernCombat.getInstance()));
    }
}
