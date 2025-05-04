package me.latifil.modernCombat.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements Listener {
    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta == null) return this;

        Component displayName = LegacyComponentSerializer.legacyAmpersand().deserialize(name);
        meta.displayName(displayName);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(String line) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta == null) return this;

        List<Component> lore = meta.lore();
        if (lore == null) lore = new ArrayList<>();

        Component loreLine = LegacyComponentSerializer.legacyAmpersand().deserialize(line);
        lore.add(loreLine);
        meta.lore(lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder clearLore() {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta != null) {
            meta.lore(new ArrayList<>());
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setDurability(int durability) {
        ItemMeta meta = this.itemStack.getItemMeta();
        if (meta instanceof Damageable damageable) {
            damageable.setDamage(durability);
            this.itemStack.setItemMeta(meta);
        }
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setEnchantment(Enchantment enchantment) {
        this.itemStack.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemBuilder setType(Material material) {
        ItemMeta oldMeta = this.itemStack.getItemMeta();
        int amount = this.itemStack.getAmount();

        this.itemStack = new ItemStack(material, amount);
        ItemMeta newMeta = this.itemStack.getItemMeta();

        if (oldMeta != null && newMeta != null) {
            newMeta.displayName(oldMeta.displayName());
            newMeta.lore(oldMeta.lore());
            this.itemStack.setItemMeta(newMeta);
        }
        return this;
    }

    public ItemBuilder clearEnchantments() {
        for (Enchantment enchantment : this.itemStack.getEnchantments().keySet()) {
            this.itemStack.removeEnchantment(enchantment);
        }
        return this;
    }

    public ItemBuilder addBlockingComponent() {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.itemStack);
        ItemUtils.addBlockingComponent(nmsStack);
        this.itemStack = CraftItemStack.asBukkitCopy(nmsStack);
        return this;
    }

    public ItemBuilder removeBlockingComponent() {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(this.itemStack);
        ItemUtils.removeBlockingComponent(nmsStack);
        this.itemStack = CraftItemStack.asBukkitCopy(nmsStack);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
