package cn.mcbeserver.wildrnesssurvival.api;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author DongShaoNB
 */
public class BeltManager {

    public static File DATA_FILE = new File(WildrnessSurvival.getInstance().getDataFolder(), "belts.yml");
    public static FileConfiguration ALL_BELT_DATA = YamlConfiguration.loadConfiguration(DATA_FILE);

    public static boolean isExist(String beltId) {
        return ALL_BELT_DATA.getConfigurationSection(beltId) != null;
    }

    public static List<String> getAllBeltsId() {
        return new ArrayList<>(ALL_BELT_DATA.getKeys(false));
    }

}
