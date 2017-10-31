package vip.creeper.mcserverplugins.magiccreeperskullplusitem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class MainListener {
    private MagicCreeperSkullPlusItem plugin;
    private List<String> hattedPlayers;

    public MainListener(MagicCreeperSkullPlusItem plugin) {
        this.plugin = plugin;
        this.hattedPlayers = new ArrayList<>();
    }
    
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //重新给予防止失效
        if (hattedPlayers.contains(player.getName())) {
            removeEffects(player);
            giveEffects(player);
        }
    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        //重新给予防止失效
        if (hattedPlayers.contains(player.getName())) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                removeEffects(player);
                giveEffects(player);
            });
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        String playerName = player.getName();

        if (plugin.isMagicCreeperHeadPlusItem(player.getInventory().getHelmet())) {
            //戴上
            if (!hattedPlayers.contains(playerName)) {
                hattedPlayers.add(playerName);
                giveEffects(player);
                Util.sendMsgWithPrefix(player, "&c您已获得 &e速度II &c加成.");
                Util.sendMsgWithPrefix(player, "&c您已获得 &e力量II &c加成.");
                Util.sendMsgWithPrefix(player, "&c您已获得 &e跳跃提升II &c加成.");
                Util.sendMsgWithPrefix(player, "&c您已获得 &e水下呼吸 &c加成.");
                Util.sendMsgWithPrefix(player, "&c您已获得 &e八倍经验 &c加成.");
                Util.sendMsgWithPrefix(player, "&c您已获得 &e闪影 &c技能.");
                Util.sendMsgWithPrefix(player, "&c您已获得 &e死神绽放 &c技能.");
            }

            return;
        }

        //脱下
        if (hattedPlayers.contains(playerName)) {
            hattedPlayers.remove(playerName);
            removeEffects(player);
            Util.sendMsgWithPrefix(player, "&c您脱下了 §e魔力Creeper头 Plus§c.");
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
}
