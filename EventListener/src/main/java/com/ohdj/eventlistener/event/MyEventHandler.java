package com.ohdj.eventlistener.event;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class MyEventHandler implements Listener {

    /**
     * 玩家加入服务器 事件处理
     *
     * @param event
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // 当玩家进入服务器时，会在玩家身边播放音效
        Location location = player.getLocation();
        World world = location.getWorld();
        world.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, .5F, .5F);

        // 并且修改玩家进入服务器时的全局广播提示
        event.setJoinMessage(ChatColor.GREEN + "让我们欢迎玩家 " + player.getName() + " 加入了服务器，热烈欢迎~");
    }

    /**
     * 羊毛色彩变化 事件处理
     * 当玩家想要伤害羊羊君的时候羊毛色彩就会发生变化
     * [ idea快捷键 ]
     * event.getter().var   补齐getter拿到的类型
     * entity.cast          补齐向下转型（强制转换，Entity 是一个比较通用的类型，表示游戏中的任何实体）
     *
     * @param event
     */
    @EventHandler
    public void onPLayerHitSheep(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (damager != null && damager instanceof Player) {
            // 施害者是玩家类型
            if (event.getEntity() != null && event.getEntity() instanceof Sheep) {
                // 被害者是羊羊君
                Entity entity = event.getEntity();
                Sheep sheep = (Sheep) entity;

                // sheep.setColor(DyeColor color);
                // 可以看到这里需要输入DyeColor类
                // 通过枚举类的values()函数，获取枚举类的所有值，得到数组
                DyeColor[] colors = DyeColor.values();
                // Random.nextInt(n) 方法返回一个介于 0（包含）和 n（不包含）之间的随机整数
                int randomIndex = new Random().nextInt(colors.length);

                // 修改羊毛颜色，随机
                sheep.setColor(colors[randomIndex]);

                // 取消伤害事件发生
                event.setCancelled(true);
            }
        }
    }
}
