package cn.mcbeserver.wildrnesssurvival;

import cn.mcbeserver.wildrnesssurvival.listeners.InventoryListener;
import cn.mcbeserver.wildrnesssurvival.listeners.PlayerListener;
import cn.mcbeserver.wildrnesssurvival.listeners.TabCompleterListener;
import cn.mcbeserver.wildrnesssurvival.support.PlaceholderSupport;
import cn.mcbeserver.wildrnesssurvival.utils.Cache;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class WildrnessSurvival extends JavaPlugin {

    private static WildrnessSurvival Plugin;
    private static final String Prefix = "§b[荒野求生] ";

    @Override
    public void onEnable() {
        Plugin = this;
        saveResourceFile();
        registerEvents();
        registerCommands();
        hookSupportPlugins();
        Cache.setupCache();
        File playerDataFolder = new File(getDataFolder() + "/playerData/");
        if (!playerDataFolder.exists()) {
            if (playerDataFolder.mkdirs()) {
                WildrnessSurvival.getInstance().getLogger().info("§2插件首次加载，已成功初始化!");
            }
        }
    }

    @Override
    public void onDisable() {

    }

    public void saveResourceFile() {
        saveDefaultConfig();
        File beltsFile = new File(getDataFolder() + "/belts.yml");
        if (!beltsFile.exists()) {
            saveResource("belts.yml", false);
        }
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }

    public void registerCommands() {
        getCommand("wildrnesssurvival").setExecutor(new MainCommand());
        getCommand("wildrnesssurvival").setTabCompleter(new TabCompleterListener());
    }

    public void hookSupportPlugins() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderSupport(this).register();
        }
    }

    public static String getPrefix() {
        return Prefix;
    }

    public static WildrnessSurvival getInstance() {
        return Plugin;
    }
}
