package com.ohdj.customrecipe;

import com.ohdj.customrecipe.executor.VillagerCommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomRecipe extends JavaPlugin {

    /**
     * 命名空间 {@link NamespacedKey}不可重复使用在不同配方里，
     * 不可重名，否则会抛出
     * {@link IllegalArgumentException}异常
     */
    @Override
    public void onEnable() {
        /**
         * 熔炉自定义
         */
        // 命名空间
        NamespacedKey furnaceKey = new NamespacedKey(this, "furnace_recipe");
        // 煅烧结果
        ItemStack bow = new ItemStack(Material.BOW);
        // 熔炉配方
        FurnaceRecipe furnaceRecipe = new FurnaceRecipe(furnaceKey, bow, Material.DIRT, 0f, 3 * 20);
        // 加入到服务器中
        Bukkit.addRecipe(furnaceRecipe);

        /**
         * 自定义工作台物品合成
         * Shaped       定形（有序合成）
         * Shapeless    不定形（无序合成）
         * rows
         * ["  P", "  S", "   "]
         * 关于这种定形配方
         * 我们声明的是这样的
         * "  P"
         * "  S"
         * "   "
         * 但是实际上在工作台横向水平翻转摆放
         * "P  "
         * "S  "
         * "   "
         * 比如这样
         * 会发现该自定义的配方也是有效的
         */
        // 命名空间
        NamespacedKey craftKey = new NamespacedKey(this, "craft_table");
        // 结果
        ShapedRecipe shapedRecipe = new ShapedRecipe(craftKey, bow);
        // 原材料的摆放方式
        shapedRecipe.shape(
                "  P",
                "  S",
                "   "
                /**
                 * 如果这里传入
                 * "P",
                 * "S"
                 * 则是烈焰粉在上，粘液球在下的时候，摆放在工作台任何一处都能触发
                 */
        );
        // 指定摆放方式中的字符所代表的原材料
        shapedRecipe.setIngredient('P', Material.BLAZE_POWDER);
        shapedRecipe.setIngredient('S', Material.SLIME_BALL);
        // 加入到服务器中
        Bukkit.addRecipe(shapedRecipe);

        /**
         * 村民的自定义交易
         */
        getCommand("villager").setExecutor(new VillagerCommandExecutor());
    }
}
