package cn.mcwlc.wildrnesssurvival.config;

import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

/**
 * @author DongShaoNB
 */
public class MessageConfig {

    private static YamlConfiguration messageConfig;

    public static void load() {
        File messageFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "message.yml");
        if (!messageFile.exists()) {
            WildrnessSurvival.getInstance().saveResource("message.yml", false);
        }
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
    }

    public static YamlConfiguration getConfig() {
        return messageConfig;
    }

    public static void reloadConfig() {
        File messageFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "message.yml");
        messageConfig = YamlConfiguration.loadConfiguration(messageFile);
    }

}
