package com.ohdj.inventoryplugin;

import com.ohdj.inventoryplugin.executor.AwardExecutor;
import com.ohdj.inventoryplugin.executor.OpenExecutor;
import com.ohdj.inventoryplugin.listener.OnTakeAwardFromRemoteChestListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class InventoryPlugin extends JavaPlugin {

    /**
     * org.bukkit.inventory.Inventory：物品栏
     * <br>
     * org.bukkit.inventory.ItemStack：物品槽
     * <br>
     * org.bukkit.inventory.meta.ItemMeta：物品
     * <br>
     * org.bukkit.Material：物品类型
     */
    @Override
    public void onEnable() {
        // open指令：打开一个玩家独有的自定义物品栏
        getCommand("open").setExecutor(new OpenExecutor());
        // award指令：给玩家的物品栏添加特殊物品
        getCommand("award").setExecutor(new AwardExecutor());
        // 自定义事件：当玩家试图在自定义物品栏中拿取特殊物品时，会操作失败
        Bukkit.getPluginManager().registerEvents(new OnTakeAwardFromRemoteChestListener(), this);
    }
}
