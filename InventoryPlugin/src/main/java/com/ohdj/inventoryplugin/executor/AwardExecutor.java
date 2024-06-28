package com.ohdj.inventoryplugin.executor;

import com.ohdj.inventoryplugin.holder.GlobalChestHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

public class AwardExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 分两种情况，一种是args.length == 0 也就是 /award，此时只作用于自己，且自己必须是player类型
        if (sender instanceof Player && args.length == 0) {
            Player player = (Player) sender;
            // 先获取远程箱子
            Inventory remoteInv = GlobalChestHolder.getInventory(player.getName());
            if (remoteInv == null) {
                // 为null，还未绑定
                remoteInv = Bukkit.createInventory(null, 9, player.getName() + "的私人箱子");
                GlobalChestHolder.addInventory(player.getName(), remoteInv);
            }
            // 添加奖励物品
            if (award(player.getName(), remoteInv)) {
                sender.sendMessage(ChatColor.GREEN + "添加成功");
            } else {
                sender.sendMessage(ChatColor.RED + "添加失败");
            }
            return true;
        } else if (args.length == 1) {
            // 此时 /award xxx 只作用于xxx

            // 先获取远程箱子
            Inventory remoteInv = GlobalChestHolder.getInventory(args[0]);
            if (remoteInv == null) {
                // 为null，还未绑定
                remoteInv = Bukkit.createInventory(null, 9, args[0] + "的私人箱子");
                GlobalChestHolder.addInventory(args[0], remoteInv);
            }
            // 添加奖励物品
            if (award(args[0], remoteInv)) {
                sender.sendMessage(ChatColor.GREEN + "添加成功");
            } else {
                sender.sendMessage(ChatColor.RED + "添加失败");
            }
            return true;
        }
        return false;
    }

    /**
     * 向远程的自定义箱子中添加奖励物品
     *
     * @param name
     * @param remoteInv
     * @return 是否可以添加
     */
    private boolean award(String name, Inventory remoteInv) {
        int index = remoteInv.firstEmpty();
        if (index != -1) {
            // 找到空的槽位
            // ItemStack本身是个类，于是我们可以直接通过构造函数new出该对象
            ItemStack stack = new ItemStack(Material.FEATHER, 1);
            // 先有槽，然后有物品
            // 通过getItemMeta()函数获取ItemMeta实现类，用于修改物品的信息
            ItemMeta item = stack.getItemMeta();
            // 修改物品的展示名、lores、附魔
            item.setDisplayName(ChatColor.RED + "阿米诺斯");

            List<String> lores = new LinkedList<>();
            lores.add(ChatColor.GREEN + "这是第1行");
            lores.add(ChatColor.GREEN + "这是第2行");
            lores.add(ChatColor.GREEN + "这是第3行");
            item.setLore(lores);

            item.addEnchant(Enchantment.LUCK, 1, true);

            // 将物品于槽进行绑定
            stack.setItemMeta(item);
            remoteInv.setItem(index, stack);

            return true;
        }
        return false;
    }
}
