package vip.creeper.mcserverplugins.magiccreeperskullplusitem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static void sendMsgWithPrefix(Player player, String msg) {
        player.sendMessage("§a[MagicCreeperHeadPlus] §b" + ChatColor.translateAlternateColorCodes('&', msg));
    }
}
