package com.ohdj.inventoryplugin.holder;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局的箱子集合
 */
public class GlobalChestHolder {
    private static Map<String, Inventory> inventoryMap = new HashMap<>();

    public static boolean contains(String playerName) {
        return inventoryMap.containsKey(playerName);
    }

    public static Inventory getInventory(String playerName) {
        if (contains(playerName)) {
            return inventoryMap.get(playerName);
        } else {
            return null;
        }
    }

    // map的put()函数有返回值
    public static Inventory addInventory(String playerName, Inventory inventory) {
        return inventoryMap.put(playerName, inventory);
    }
}
