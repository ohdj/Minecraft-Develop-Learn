package com.ohdj.threadplugin;

import com.ohdj.threadplugin.executor.ThreadCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThreadPlugin extends JavaPlugin {

    // 插件单例
    private static ThreadPlugin instance = null;

    public static ThreadPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getCommand("stuck").setExecutor(new ThreadCommandExecutor());
    }
}
