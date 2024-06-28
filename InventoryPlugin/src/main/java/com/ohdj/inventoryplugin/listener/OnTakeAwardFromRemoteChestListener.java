package com.ohdj.inventoryplugin.listener;

import com.ohdj.inventoryplugin.holder.GlobalChestHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OnTakeAwardFromRemoteChestListener implements Listener {
    /**
     * 当玩家试图在自定义物品栏中拿取特殊物品时，会操作失败
     * 被@EventHandler标注的方法必须是public void类型，参数只能有一个，并且参数必须是具体的某个Event类，如果该Event是抽象类，则会报错
     *
     * @param event
     */
    @EventHandler
    public void onTakeAwardFromRemoteChest(InventoryClickEvent event) {
        // 当前的物品栏
        Inventory currentInv = event.getInventory();
        // 点击者，只能是玩家
        HumanEntity player = event.getWhoClicked();

        // 判断是不是远程的自定义箱子
        Inventory remoteInv = GlobalChestHolder.getInventory(player.getName());
        if (remoteInv == null) {
            // 为null，肯定不是
            return;
        } else {
            // 非空，再判断
            if (remoteInv == currentInv) {
                // 两者是同一对象
                ItemStack clickerStack = event.getCurrentItem();
                // 获取stack槽的信息，比对
                if (clickerStack.getType() == Material.FEATHER &&
                        clickerStack.getItemMeta().getDisplayName().equals(ChatColor.RED + "阿米诺斯")) {
                    // 是同一物品，取消当前event
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.GREEN + "哈哈哈，放弃吧，你拿不到的");
                }
            }
        }
    }
}
