package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DongShaoNB
 */
public class BeltsManager {

    public static File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "belts.yml");
    public static FileConfiguration allBeltData = YamlConfiguration.loadConfiguration(dataFile);

    public static boolean getBeltExist(String beltId) {
        return allBeltData.getConfigurationSection(beltId) != null;
    }

    public static List<String> getAllBeltsId() {
        return new ArrayList<>(allBeltData.getKeys(false));
    }

}
