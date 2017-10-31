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
    private List<String> hattedPlayers = new ArrayList<>();
    private static List<String> lores = Arrays.asList( "§7- §f加成 §b> §f速度III", "§7- §f加成 §b> §f力量II", "§7- §f加成 §b> §f跳跃III", "§7- §f加成 §b> §f水下呼吸", "§7- §f加成 §b> §f八倍经验", "§7- §f技能 §b> §f忽隐忽现", "§7- §f技能 §b> §f死神绽放");

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("omchpi").setExecutor(this);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (hattedPlayers.contains(player.getName())) {
            removeEffects(player);
            giveEffects(player);
        }
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (hattedPlayers.contains(player.getName())) {
            Bukkit.getScheduler().runTask(this, () -> {
                removeEffects(player);
                giveEffects(player);
            });
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String playerName = player.getName();

        if (isMagicCreeperHeadPlusItem(player.getInventory().getHelmet())) {
            if (!hattedPlayers.contains(playerName)) {
                hattedPlayers.add(playerName);
                giveEffects(player);
                sendMsgWithPrefix(player, "&c您已获得 &e速度II &c加成.");
                sendMsgWithPrefix(player, "&c您已获得 &e力量II &c加成.");
                sendMsgWithPrefix(player, "&c您已获得 &e跳跃提升II &c加成.");
                sendMsgWithPrefix(player, "&c您已获得 &e水下呼吸 &c加成.");
                sendMsgWithPrefix(player, "&c您已获得 &e八倍经验 &c加成.");
                sendMsgWithPrefix(player, "&c您已获得 &e闪影 &c技能.");
                sendMsgWithPrefix(player, "&c您已获得 &e死神绽放 &c技能.");
            }

            return;
        }

        //脱下
        if (hattedPlayers.contains(playerName)) {
            hattedPlayers.remove(playerName);
            removeEffects(player);
            sendMsgWithPrefix(player, "&c您脱下了 §e魔力Creeper头 Plus§c.");
        }
    }

    private void removeEffects(Player player) {
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        player.removePotionEffect(PotionEffectType.JUMP);
        player.removePotionEffect(PotionEffectType.WATER_BREATHING);
    }

    private void giveEffects(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 2));
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 2));
    }

    private void sendMsgWithPrefix(Player player, String msg) {
        player.sendMessage("§a[MagicCreeperHeadPlus] §b" + ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        ItemMeta meta = item.getItemMeta();

        item.setDurability((short) 4);
        meta.setDisplayName("§b[S] §c魔力Creeper头 Plus");
        meta.setLore(lores);
        item.setItemMeta(meta);

        return item;
    }

    private boolean isMagicCreeperHeadPlusItem(ItemStack item) {
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

    public boolean onCommand(CommandSender cs, Command cmd, String lable, String[] args) {
        if (cs.hasPermission("MagicCreeperHeadPlusItem") && args.length == 2 && args[0].equalsIgnoreCase("give")) {
            Player player = Bukkit.getPlayer(args[1]);

            if (player == null || !player.isOnline()) {
                cs.sendMessage("目标玩家未在线.");
                return true;
            }

            PlayerInventory playerInv = player.getInventory();

            if (playerInv.firstEmpty() == -1) {
                cs.sendMessage("目标玩家背包空间不足.");
                return true;
            }

            playerInv.addItem(getItem());
        }
        return false;
    }
}