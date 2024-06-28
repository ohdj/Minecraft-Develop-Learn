package com.ohdj.inventoryplugin.executor;

import com.ohdj.inventoryplugin.holder.GlobalChestHolder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class OpenExecutor implements CommandExecutor {
    /**
     * 打开一个玩家独有的自定义物品栏
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            Inventory remoteInv = GlobalChestHolder.getInventory(player.getName());
            if (remoteInv == null) {
                // 第一次输入/open指令，共享变量里还没有存储玩家与背包
                // 于是需要我们手动创建背包，并添加到共享变量里
                remoteInv = Bukkit.createInventory(player, 9, player.getName() + "的私人箱子");
                GlobalChestHolder.addInventory(player.getName(), remoteInv);
            }

            // 最后使用openInventory函数将共享变量存储的背包打开即可
            player.openInventory(remoteInv);
        }
        return false;
    }
}
