package me.latifil.modernCombat.module.modules;

import me.latifil.modernCombat.module.CombatModule;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.bukkit.attribute.Attribute;

public class AttackSpeedModule extends CombatModule {
    int MODERN_ATTACK_SPEED = 4; // Attack speed in post-1.9 combat
    int NEW_ATTACK_SPEED = 40; // Attack speed value to recreate pre-1.9 combat

    private void setAttackSpeed(@NotNull Player player, int attackSpeed) {
        AttributeInstance attribute = player.getAttribute(Attribute.ATTACK_SPEED);

        if (attribute == null) {
            return;
        }

        double value = attribute.getBaseValue();

        if (value != attackSpeed) {
            attribute.setBaseValue(attackSpeed);
            player.saveData();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent event) {
        setAttackSpeed(event.getPlayer(), NEW_ATTACK_SPEED);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        setAttackSpeed(e.getPlayer(), NEW_ATTACK_SPEED);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDisconnect(PlayerQuitEvent event) {
        setAttackSpeed(event.getPlayer(), MODERN_ATTACK_SPEED);
    }
}
