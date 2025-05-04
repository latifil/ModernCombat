package me.latifil.modernCombat.util.combat;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class DamageUtils {
    public static float blockingDamageReduction(net.minecraft.world.entity.player.Player player) {
        return (isBlockingSword(player)) ? 0.5f : 1;
    }

    public static boolean isBlockingSword(@NotNull Player player) {
        return player.getUseItem().is(ItemTags.SWORDS);
    }
}
