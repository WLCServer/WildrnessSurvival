package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class BeltInfo {

    private final String materialName;
    private final String beltName;
    private final boolean enchant;
    private final List<String> lore;
    private final int health;
    private final int attack;
    private final int defense;
    private final int speed;

    public BeltInfo(String beltID) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder(), "belts.yml");
        FileConfiguration allBeltData = YamlConfiguration.loadConfiguration(dataFile);
        ConfigurationSection beltData = allBeltData.getConfigurationSection(beltID);
        this.materialName = beltData.getString("material");
        this.beltName = beltData.getString("name");
        this.enchant = beltData.getBoolean("enchant");
        this.lore = beltData.getStringList("lore");
        this.health = beltData.getInt("optionals.health");
        this.attack = beltData.getInt("optionals.attack");
        this.defense = beltData.getInt("optionals.defense");
        this.speed = beltData.getInt("optionals.speed");
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getBeltName() {
        return beltName;
    }

    public boolean isEnchant() {
        return enchant;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }
}
