package com.ohdj.configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public final class Configuration extends JavaPlugin {

    @Override
    public void onEnable() {
        /**
         * 默认插件配置文件的信息读取
         * 1. 先调用JavaPlugin父类的public函数getConfig得到config.yml中的信息。
         * 2. 使用YamlConfiguration对象的各种get方法，读取配置文件中的值。
         */
        // testGetConfigYaml();

        /**
         * config.yml文件的持久化。
         * 在当前案例中，虽然我们已经将config.yml事先定义在了resources文件夹中。
         * 但是当插件被加载完成，Spigot并不会在本地的为该插件创建config.yml文件。
         * 我们需要使用JavaPlugin的public函数 saveConfig()来实现完成持久化。
         */
        // testOnlySet();

        /**
         * saveConfig()与saveDefaultConfig()函数的区别。
         * saveConfig虽然会覆盖掉位于saveDefaultConfig()函数所生成的文件的值，
         * 但是之后我们仍然能从JavaPlugin所维护的config对象里得到源文件的信息。
         */
        // testSaveAndSaveDefaultConfig();

        /**
         * 自定义配置文件，活学活用{@link #getDataFolder()} 函数
         */
        // testCustomConfigYaml();
    }

    /**
     * <code>FileConfiguration config = getConfig();</code>
     * <br><br>
     * <i>这里的赋值方式是引用类型赋值，两个对象是相同的内存地址，所以针对config的修改会影响到JavaPlugin所维护的newConfig属性。</i>
     * <br><br>
     * {@link #getConfig()}方法返回了JavaPlugin内部维护的配置对象的引用，指向了与JavaPlugin内部配置相同的对象。因此，对config的任何修改都会直接影响到JavaPlugin内部的配置对象。
     * <br><br>
     * 如果你只想读取配置而不修改它，最好使用config.getXXX()方法而不是config.set()方法。
     */
    private void testGetConfigYaml() {
        Logger logger = getLogger();

        // 通过JavaPlugin#getConfig函数获取的配置文件只能是config.yml
        FileConfiguration config = getConfig();

        // 使用各种get函数来获取配置文件中的属性，get方法在编译期的返回值是Object类型，运行期时会得到正确的class类型

        /**
         * getList("key")
         * 获取list数组
         */
        List<?> list = config.getList("ohdj.list");
        System.out.println("==========ohdj.list===========" + list.size());
        for (int i = 0; i < list.size(); i++) {
            logger.info("Result：" + list.get(i) + "| 类型为：" + list.get(i).getClass().getName());
        }

        /**
         * getMapList("key")
         * 获取Map集合
         */
        List<Map<?, ?>> mapList = config.getMapList("ohdj.map");
        System.out.println("==========ohdj.mapList===========" + mapList.size());
        for (int i = 0; i < mapList.size(); i++) {
            StringBuilder sb = new StringBuilder();

            Set<?> keys = mapList.get(i).keySet();
            Collection<?> values = mapList.get(i).values();
            sb.append("，map的keys：");

            keys.forEach((e) -> {
                sb.append(e + " ");
            });
            sb.append("，map的values：");

            values.forEach((e) -> {
                sb.append(e + " ");
            });
            logger.info("当前map：" + i + sb);
        }

        /**
         * getInt("key")
         * 获取整型值
         */
        System.out.println("==========ohdj.age===========");
        System.out.println(config.getInt("ohdj.age"));

        /**
         * getBoolean("key")
         * 获取布尔值
         */
        System.out.println("==========ohdj.disabled===========");
        System.out.println(config.getBoolean("ohdj.disabled"));

        /**
         * getXXXX("key")
         */

        /**
         * 补充：
         * {@link YamlConfiguration#get()}虽然最终能获取到值的类型，但是在编译期还无法做到
         */
    }

    /**
     * 演示只set而不save的区别
     * <br><br>
     * 通过{@link #getConfig()}所得到的对象是FileConfiguration类型
     * <br>
     * 查看该类的继承和实现关系，会非常明显的看出所得到的对象是FileConfiguration类型类本质上只是内存片段
     * <br>
     * 我们针对内存对象的set操作是不会实时同步到本地配置文件里的
     */
    private void testOnlySet() {
        /**
         * 由于指针引用的特性，这里get得到的config对象在被修改后会同步影响JavaPlugin所维护的config。
         */
        FileConfiguration config = this.getConfig();
        config.set("ohdj.disabled", true);

        /**
         * 如果不进行save操作，就不会在本地持久化刚刚更新好的config.yml文件。
         * 使用saveConfig或saveDefaultConfig都会造成config.yml文件的持久化。
         */
        this.saveConfig();

    }

    /**
     * 测试 {@link #saveConfig()} 与 {@link #saveDefaultConfig()} 的区别
     */
    private void testSaveAndSaveDefaultConfig() {
        // config.yml
        FileConfiguration config = getConfig();

        // 更新JavaPlugin#newConfig对象的属性值
        config.set("ohdj.disabled", true);

        // 覆盖保存
        saveConfig();

        /**
         * saveConfig()后，针对对象的setter修改才能被持久化保存
         */

        config.set("ohdj.disabled", false);

        System.out.println(ChatColor.RED + "内存对象中的disabled： " + config.get("ohdj.disabled"));

        // 非覆盖保存，但是不会同步JavaPlugin#newConfig对象的状态
        /**
         * 非覆盖保存并不会涉及到对JavaPlugin#newConfig对象的持久化，只会将jar包中的默认config.yml给持久化到配置文件夹中。
         */
        // saveDefaultConfig();
    }

    /**
     * 自定义的配置文件，活学活用{@link #getDataFolder()}函数
     */
    private void testCustomConfigYaml() {
        YamlConfiguration myConfig = new YamlConfiguration();
        myConfig.set("ohdj.age", 13);
        myConfig.set("ohdj.status", "unknown");


        try {
            /**
             * 保存
             */
            myConfig.save(new File(getDataFolder(), "custom-config.yml"));
            /**
             * 读取
             */
            YamlConfiguration newConfig = new YamlConfiguration();
            newConfig.load(new File(getDataFolder(), "custom-config.yml"));
            System.out.println(ChatColor.GREEN + " " + newConfig.get("ohdj.age"));
            System.out.println(ChatColor.GREEN + " " + newConfig.get("ohdj.status"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
