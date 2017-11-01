package vip.creeper.mcserverplugins.magiccreeperskullplusitem;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by July_ on 2017/10/29.
 */
public class MagicCreeperSkullPlusItem extends JavaPlugin implements Listener {
    private static MagicCreeperSkullPlusItem instance;
    private static List<String> lores = Arrays.asList( "§7- §f加成 §b> §f速度III", "§7- §f加成 §b> §f力量II", "§7- §f加成 §b> §f跳跃III", "§7- §f加成 §b> §f水下呼吸", "§7- §f加成 §b> §f七倍经验", "§7- §f技能 §b> §f迷之闪现", "§7- §f技能 §b> §f死神绽放");

    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("omchpi").setExecutor(new AdminCommand());
    }

    public static MagicCreeperSkullPlusItem getInstance() {
        return instance;
    }

    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        ItemMeta meta = item.getItemMeta();

        item.setDurability((short) 4);
        meta.setDisplayName("§b[S] §c魔力Creeper头 Plus");
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    public boolean isMagicCreeperHeadPlusItem(ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        List<String> comparedLores = meta.getLore();

        //去除认主lore
        if (comparedLores.size() == lores.size() + 2) {
            for (int i = 0; i < 2; i++) {
                comparedLores.remove(comparedLores.size());
            }
        }

        return lores.equals(comparedLores);
    }
}