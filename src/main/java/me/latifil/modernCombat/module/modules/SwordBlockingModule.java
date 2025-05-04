package me.latifil.modernCombat.module.modules;

import me.latifil.modernCombat.ModernCombat;
import me.latifil.modernCombat.module.CombatModule;
import me.latifil.modernCombat.util.ItemUtils;
import me.latifil.modernCombat.util.combat.DamageUtils;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SwordBlockingModule extends CombatModule {
    private final ModernCombat plugin = ModernCombat.getInstance();

    private static void updateAllItems(@NotNull Inventory inventory, boolean add) {
        for (@Nullable ItemStack bukkitStack : inventory.getContents()) {
            if (bukkitStack == null || bukkitStack.getType().isAir()) continue;

            CraftItemStack craftItemStack;
            if (bukkitStack instanceof CraftItemStack) {
                craftItemStack = (CraftItemStack) bukkitStack;
            } else {
                craftItemStack = CraftItemStack.asCraftCopy(bukkitStack);
            }

            net.minecraft.world.item.ItemStack nmsStack = craftItemStack.handle;

            if (add) {
                ItemUtils.addBlockingComponent(nmsStack);
            } else {
                ItemUtils.removeBlockingComponent(nmsStack);
            }
        }
    }

    @EventHandler
    public void onCreativeInventory(@NotNull InventoryCreativeEvent event) {
        Player player = (Player) event.getWhoClicked();

        Bukkit.getScheduler().runTask(plugin, () -> {
            ItemStack stack = player.getInventory().getItem(event.getSlot());
            if (stack != null && stack.getType().toString().endsWith("_SWORD")) {
                CraftItemStack craftStack = (stack instanceof CraftItemStack)
                        ? (CraftItemStack) stack
                        : CraftItemStack.asCraftCopy(stack);

                ItemUtils.addBlockingComponent(craftStack.handle);
                player.getInventory().setItem(event.getSlot(), CraftItemStack.asBukkitCopy(craftStack.handle));
            }
        });
    }


    @EventHandler
    public void onDamagePlayer(@NotNull EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            event.setDamage(event.getDamage() * DamageUtils.blockingDamageReduction(((CraftPlayer) player).getHandle()));
        }
    }

    @EventHandler
    public void onItemChangeEvent(@NotNull PlayerItemHeldEvent event) {
        updateAllItems(event.getPlayer().getInventory(), true);
    }

    @EventHandler(ignoreCancelled = true)
    public void onJoin(@NotNull PlayerJoinEvent event) {
        updateAllItems(event.getPlayer().getInventory(), true);
    }

    @EventHandler
    public void onDisconnect(@NotNull PlayerQuitEvent event) {
        try {
            updateAllItems(event.getPlayer().getInventory(), false);
        } catch (Exception ignored) {}
    }

    @EventHandler
    public void onKick(@NotNull PlayerKickEvent event) {
        try {
            updateAllItems(event.getPlayer().getInventory(), false);
        } catch (Exception ignored) {}
    }
}
