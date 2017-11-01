package vip.creeper.mcserverplugins.magiccreeperskullplusitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

class Util {
    static void sendMsgWithPrefix(Player player, String msg) {
        player.sendMessage("§a[MagicCreeperHeadPlus] §b" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    // 1 2
    static double getRandomValue(int min, int max) {
        return Math.random() * (max - min + 1) + min;
    }
}
