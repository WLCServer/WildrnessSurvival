package cn.mcwlc.wildrnesssurvival.util;

import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import cn.mcwlc.wildrnesssurvival.em.Attribute;
import cn.mcwlc.wildrnesssurvival.em.Quality;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * @author DongShaoNB
 */
public class BeltInfo {

    private final String materialName;
    private final String beltName;
    private final Quality quality;
    private final List<String> lore;
    private final HashMap<Attribute, Double> attributeHashMap = new HashMap<>();

    public BeltInfo(String beltId) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "belts.yml");
        FileConfiguration allBeltData = YamlConfiguration.loadConfiguration(dataFile);
        ConfigurationSection beltData = allBeltData.getConfigurationSection(beltId);
        this.materialName = beltData.getString("material");
        this.beltName = beltData.getString("name");
        this.quality = Quality.getQuality(beltData.getString("quality"));
        this.lore = beltData.getStringList("lore");
        ConfigurationSection attributeConfig = beltData.getConfigurationSection("optionals");
        for (String key: attributeConfig.getKeys(false)) {
            this.attributeHashMap.put(Attribute.getAttribute(key), attributeConfig.getDouble(key));
        }
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getBeltName() {
        return beltName;
    }

    public Quality getQuality() {
        return quality;
    }

    public List<String> getLore() {
        return lore;
    }

    public HashMap<Attribute, Double> getAttribute() {
        return attributeHashMap;
    }


}
