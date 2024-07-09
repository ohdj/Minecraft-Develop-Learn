package com.ohdj.menututorial;

import com.ohdj.menututorial.commands.Menu;
import org.bukkit.plugin.java.JavaPlugin;

public final class MenuTutorial extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("menu").setExecutor(new Menu(this));
    }
}
