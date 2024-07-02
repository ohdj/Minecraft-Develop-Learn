package com.ohdj.tabexecutorplugin;

import com.ohdj.tabexecutorplugin.executor.CommandTipExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class TabExecutorPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("info").setExecutor(new CommandTipExecutor());
    }
}
