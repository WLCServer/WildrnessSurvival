package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BeltsManager {

    public static File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "belts.yml");
    public static FileConfiguration allBeltData = YamlConfiguration.loadConfiguration(dataFile);

    public static boolean getBeltExist(String beltID) {
        return allBeltData.getConfigurationSection(beltID) != null;
    }

    public static List<String> getAllBeltsID() {
        return new ArrayList<>(allBeltData.getKeys(false));
    }

}
