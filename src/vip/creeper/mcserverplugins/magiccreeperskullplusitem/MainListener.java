package vip.creeper.mcserverplugins.magiccreeperskullplusitem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainListener {
    private MagicCreeperSkullPlusItem plugin;
    private List<String> hattedPlayers;
    private HashMap<String, Long> cooldowns;

    public MainListener(MagicCreeperSkullPlusItem plugin) {
        this.plugin = plugin;
        this.hattedPlayers = new ArrayList<>();
        this.cooldowns = new HashMap<>();
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

    @EventHandler
    public void onPlayerExpChangeEvent(PlayerExpChangeEvent event) {
        if (hattedPlayers.contains(event.getPlayer().getName())) {
            event.setAmount(event.getAmount() * 7);
        }
    }

    @EventHandler
    public void onPlayerToggleSneakEvent(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        //50s
        if (hattedPlayers.contains(playerName)) {
            long cooldown = System.currentTimeMillis() - cooldowns.getOrDefault(playerName, 0L);

            if (cooldown < 50000L) {
                Util.sendMsgWithPrefix(player, "&c你还需 &e" + cooldown / 1000 + "秒 &c才能再次使用 &e死亡绽放 &c技能." );
                return;
            }

            List<Entity> entities = player.getNearbyEntities(8, 128, 8);

            //遍历实体，对玩家和怪物造成伤害
            for (Entity entity : entities) {
                if (entity instanceof Player) {
                    player.damage(5D);
                }

                if (entity instanceof Monster) {
                    player.damage(10D);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (damager instanceof Player && target instanceof Player) {
            Player playerDamager = (Player) damager;
            Player playerTarget = (Player) target;

            if (hattedPlayers.contains(playerDamager.getName())) {
                //40%几率
                if (Util.getRandomValue(1, 10) < 4) {
                    playerTarget.hidePlayer(playerTarget);
                    Util.sendMsgWithPrefix(playerTarget, "&d你触发了 &e迷之闪现 &d被动技能. ");
                }
            }
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
