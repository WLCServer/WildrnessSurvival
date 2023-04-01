package cn.mcbeserver.wildrnesssurvival;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Language {
    private static final File languageFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "language.yml");
    private static final FileConfiguration languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    public static final String help = languageConfig.getString("help");
    public static final  String noPermission = WildrnessSurvival.getPrefix() + languageConfig.getString("no-permission");

}
