package com.ohdj.eventlistener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import com.ohdj.eventlistener.event.MyEventHandler;

public final class EventListener extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        /**
         * 事件驱动：
         * 1. 针对特定的事件，准备Listener实现类
         * 2. 获取PluginManager
         * 3. 通过PM注册事件处理器
         */


        // 注册 事件处理器方法

        // 1
//        this.getServer().getPluginManager().registerEvents(new MyEventHandler(), this);

        // 2
        Bukkit.getPluginManager().registerEvents(new MyEventHandler(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
