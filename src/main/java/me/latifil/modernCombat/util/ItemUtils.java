package me.latifil.modernCombat.util;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import org.jetbrains.annotations.NotNull;

public class ItemUtils {

    private static final Consumable CONSUMABLE_COMPONENT = Consumable.builder()
            .consumeSeconds(Float.MAX_VALUE)
            .animation(ItemUseAnimation.BLOCK)
            .build();

    public static void addBlockingComponent(@NotNull ItemStack itemStack) {
        if (itemStack.getComponents().has(DataComponents.CONSUMABLE)) return;

        itemStack.applyComponents(
                DataComponentPatch.builder()
                        .set(DataComponents.CONSUMABLE, CONSUMABLE_COMPONENT)
                        .build()
        );
    }

    public static void removeBlockingComponent(@NotNull ItemStack itemStack) {
        itemStack.applyComponents(
                DataComponentPatch.builder()
                        .remove(DataComponents.CONSUMABLE)
                        .build()
        );
    }
}
