package cn.mcbeserver.wildrnesssurvival;

import cn.mcbeserver.wildrnesssurvival.config.MessageConfig;
import cn.mcbeserver.wildrnesssurvival.listener.BlockListener;
import cn.mcbeserver.wildrnesssurvival.listener.InventoryListener;
import cn.mcbeserver.wildrnesssurvival.listener.PlayerListener;
import cn.mcbeserver.wildrnesssurvival.listener.TabCompleterListener;
import cn.mcbeserver.wildrnesssurvival.support.PlaceholderSupport;
import cn.mcbeserver.wildrnesssurvival.util.AutoBackup;
import cn.mcbeserver.wildrnesssurvival.util.ZipUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * @author DongShaoNB
 */
public final class WildrnessSurvival extends JavaPlugin {

    private static WildrnessSurvival Plugin;
    private static final String PREFIX = "§b[荒野求生] ";

    @Override
    public void onEnable() {
        Plugin = this;
        saveResourceFile();
        registerEvents();
        registerCommands();
        hookSupportPlugins();
        loggerWlcServerFont();
        if (getConfig().getBoolean("autobackup.enable")) {
            AutoBackup.start();
        }
    }

    @Override
    public void onDisable() {
        if (getConfig().getBoolean("autobackup.enable")) {
            if (AutoBackup.getAutoBackup() != null) {
                AutoBackup.stop();
            }
        }
    }

    public void saveResourceFile() {
        saveDefaultConfig();
        MessageConfig.load();
        File backupFileFolder = new File(getDataFolder() + "/backup/");
        if (!backupFileFolder.exists()) {
            backupFileFolder.mkdirs();
        }
        File updateFileFolder = new File(getDataFolder() + "/update/");
        if (!updateFileFolder.exists()) {
            updateFileFolder.mkdirs();
        }
        File playerDataFolder = new File(getDataFolder() + "/playerData/");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }
        File beltsFile = new File(getDataFolder() + "/belts.yml");
        if (!beltsFile.exists()) {
            saveResource("belts.yml", false);
        }
    }

    public static void reloadPlugin() {
        getInstance().reloadConfig();
        MessageConfig.reloadConfig();
        Message.reload();
    }

    public void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
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

    public static void backupAllResources() throws IOException {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String rDate = formatter.format(date);
        String backupFile = getBackupFolder() + rDate + ".zip";
        String[] dIncludeFile = {".zip"};
        ZipUtils.zip(getInstance().getDataFolder().toString(), backupFile, dIncludeFile);
    }

    public static void updateAllResources() throws IOException {
        FileUtil.copy(new File(getInstance().getDataFolder() + "/config.yml"), new File(getUpdateFolder() + "/config.yml"));
        FileUtil.copy(new File(getInstance().getDataFolder() + "/message.yml"), new File(getUpdateFolder() + "/message.yml"));
        getInstance().saveResource("config.yml", true);
        getInstance().saveResource("message.yml", true);
        getInstance().saveResource("playerData/defaultPlayerData.yml", true);
        File oldConfigFile = new File(getUpdateFolder(),"/config.yml");
        File newConfigFile = new File(getInstance().getDataFolder(), "/config.yml");
        File oldMessageFile = new File(getUpdateFolder(), "/message.yml");
        File newMessageFile = new File(getInstance().getDataFolder(), "/message.yml");
        File playerDataFileFolder = new File(getInstance().getDataFolder() + "/playerData/");
        File newPlayerDataFile = new File(getInstance().getDataFolder(), "/playerData/defaultPlayerData.yml");
        YamlConfiguration oldConfig = YamlConfiguration.loadConfiguration(oldConfigFile);
        YamlConfiguration newConfig = YamlConfiguration.loadConfiguration(newConfigFile);
        YamlConfiguration oldMessage = YamlConfiguration.loadConfiguration(oldMessageFile);
        YamlConfiguration newMessage = YamlConfiguration.loadConfiguration(newMessageFile);
        YamlConfiguration newPlayerData = YamlConfiguration.loadConfiguration(newPlayerDataFile);
        for (String key: oldConfig.getKeys(true)) {
            newConfig.set(key, oldConfig.get(key));
        }
        newConfig.save(newConfigFile);
        for (String key: oldMessage.getKeys(true)) {
            newMessage.set(key, oldMessage.get(key));
        }
        newMessage.save(newMessageFile);
        for (File playerDataFile: Objects.requireNonNull(playerDataFileFolder.listFiles())) {
            YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
            for (String key: newPlayerData.getKeys(true)) {
                if (playerData.get(key) == null) {
                    playerData.set(key, newPlayerData.get(key));
                    playerData.save(playerDataFile);
                }
            }
        }
    }

    public void loggerWlcServerFont() {
        Bukkit.getLogger().info("§b __          ___      _____  _____                          ");
        Bukkit.getLogger().info("§b \\ \\        / / |    / ____|/ ____|                         ");
        Bukkit.getLogger().info("§b  \\ \\  /\\  / /| |   | |    | (___   ___ _ ____   _____ _ __ ");
        Bukkit.getLogger().info("§b   \\ \\/  \\/ / | |   | |     \\___ \\ / _ \\ '__\\ \\ / / _ \\ '__|");
        Bukkit.getLogger().info("§b    \\  /\\  /  | |___| |____ ____) |  __/ |   \\ V /  __/ |   ");
        Bukkit.getLogger().info("§b     \\/  \\/   |______\\_____|_____/ \\___|_|    \\_/ \\___|_|   ");
    }

    public static String getBackupFolder() {
        return getInstance().getDataFolder() + "/backup/";
    }

    public static String getUpdateFolder() {
        return getInstance().getDataFolder() + "/update/";
    }

    public static String getPrefix() {
        return PREFIX;
    }

    public static WildrnessSurvival getInstance() {
        return Plugin;
    }
}
