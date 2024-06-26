package com.ohdj.customcommand;

import com.ohdj.customcommand.executor.GodCommandExecutor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CustomCommand extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        /**
         * 1. 在插件的描述plugin.yml文件中声明Command指令
         * 2. 编写指令的CommandExecutor执行器
         * 3. 在当前插件获取指令，并绑定CommandExecutor执行器
         */
        this.getCommand("suicide").setExecutor(this);
        this.getCommand("god").setExecutor(new GodCommandExecutor());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * JavaPlugin类本身就已经重载了CommandExecutor接口的onCommand函数，只是没有任何代码实现而已
     * ctrl+h可以查看接口的继承实现关系
     * /suicide 指令执行器
     *
     * @param sender  Source of the command                 指令的发送者：可以是玩家、远程控制台、本地控制台、命令方块、其他实现了Permissible接口的实现类
     * @param command Command which was executed            指令：就是this.getCommand("suicide")的返回值，存储着在plugin.yml中声明的各种指令信息
     * @param label   Alias of the command which was used   指令的名称：如/suicide的指令label就是 “suicide”
     * @param args    Passed command arguments              指令的参数：加入输入的完整指令是/suicide 1 2 3，那么该args数组就是 [1, 2, 3]
     * @return
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        /**
         * 自杀指令：只有玩家能使用
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
        if (label.equalsIgnoreCase("suicide")) {
            if (sender instanceof Player) {
                // 输入指令的对象是Player玩家
                Player player = (Player) sender;

                // 通过玩家获得server服务器对象，然后用服务器对象的broadcastMessage广播消息函数向全部玩家发一段文字
                // 向全服玩家广播消息
                player.getServer().broadcastMessage(ChatColor.YELLOW + "再见了，世界！" +
                        "\n" + ChatColor.BLUE + player.getDisplayName() + ChatColor.YELLOW + "趋势了：/");
                player.setHealth(0);

                // return true是为了防止服务器给sender返回 指令的usage信息
                return true;
            } else {
                sender.sendMessage(ChatColor.YELLOW + "只有玩家能使用");
                return true;
            }
        }

        // usage属性会在指令执行器的onCommand函数返回false时被调用，用来提示玩家操作指令有误
        // 返回false：自动调用plugin.yml配置的commands.suicide.usage值并返回给CommandSender指令发送者
        return false;
    }
}
