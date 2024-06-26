package com.ohdj.customcommand.executor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /**
         * [ 评论区 ]
         * plugin.yml里给commands.suicide设置的alias，就相当于多个指令绑定同一个CommandExecutor的操作。比如你的alias设置为 【s1, s2, s3】，那么最终就可以/s1 /s2 /s3触发同一条Executor对吧。
         * 而label存在的作用，就是为了显示你具体使用了哪个指令触发了当前的Executor。比如用/s1，label就是s1、用/suicide，label就是suicide，用/s3，label就是s3。
         * 不说我还没发现，当时是第一次做视频，弄得比较紧张。本来是打算做子指令，比如/god on /god off，这种的，这时应该是args【0】.equalsIgnoreCase("on")。
         * 然后稀里糊涂整成不带参数的输入，也没讲述label具体的作用，确实是我的问题。
         * [ 弹幕 ]
         * 草，这里我说错了。前面注册好指令后，输入alias中的指令或者是suicide，最终都会来到这里
         * 事实上一般做法是把参数args[index]进行小写，再与自己期望的内容进行比对
         * 大家完全可以忽略这里的第一条if判断（当时估计是脑抽了才整的这一出，以后的视频里就没这种操作了
         */
        if (label.equalsIgnoreCase("god")) {
            // 如果是玩家
            if (sender instanceof Player) {
                Player player = (Player) sender;
                // 切换玩家的 无伤状态
                player.setInvulnerable(!player.isInvulnerable());
                String tempMessage = (player.isInvulnerable() ? ChatColor.GREEN + "是" : ChatColor.RED + "不是");
                player.sendMessage(ChatColor.WHITE + "现在你就" + tempMessage + ChatColor.WHITE + "上帝模式了。");

                return true;
            } else {
                sender.sendMessage(ChatColor.YELLOW + "只有玩家才能使用该指令！");
                return true;
            }
        }
        return false;
    }
}
