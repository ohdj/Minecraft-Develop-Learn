package com.ohdj.customrecipe.executor;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.LinkedList;

/**
 * 生成一只特殊的村民
 */
public class VillagerCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();
            Location location = player.getLocation();

            Villager villager = (Villager) world.spawnEntity(location, EntityType.VILLAGER);
            // 修改村民的信息
            villager.setAI(false);
            villager.setCustomName("奸商YOHO");

            // 交易集合
            LinkedList<MerchantRecipe> recipes = new LinkedList<>();

            // 结果1
            ItemStack result1 = new ItemStack(Material.DIAMOND_BLOCK, 2);
            // 交易1
            MerchantRecipe recipe1 = new MerchantRecipe(result1, 3);
            // 交易需要的材料 集合
            LinkedList<ItemStack> materials1 = new LinkedList<>();
            ItemStack itemStack = new ItemStack(Material.DIRT, 3);
            materials1.add(itemStack);
            recipe1.setIngredients(materials1);
            // recipes加入配方1
            recipes.add(recipe1);

            // 结果2
            ItemStack result2 = new ItemStack(Material.FEATHER, 2);
            // 交易2
            MerchantRecipe recipe2 = new MerchantRecipe(result2, 1);
            // 交易需要的材料 集合
            LinkedList<ItemStack> materials2 = new LinkedList<>();
            ItemStack m1 = new ItemStack(Material.DIAMOND_BLOCK, 1);
            ItemStack m2 = new ItemStack(Material.DIRT, 2);
            materials2.add(m1);
            materials2.add(m2);
            recipe2.setIngredients(materials2);
            // recipes加入配方2
            recipes.add(recipe2);

            villager.setRecipes(recipes);
            return true;
        }
        return false;
    }
}
