package vip.creeper.mcserverplugins.magiccreeperskullplusitem;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class AdminCommand implements CommandExecutor {

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

            playerInv.addItem(MagicCreeperSkullPlusItem.getInstance().getItem());
        }
        return false;
    }
}
