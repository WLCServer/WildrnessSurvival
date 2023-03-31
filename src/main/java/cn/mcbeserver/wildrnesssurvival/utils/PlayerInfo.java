package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class PlayerInfo {

    private final int collectLevel;
    private final int collectExp;
    private final int makeLevel;
    private final int makeExp;
    private final int fightLevel;
    private final int fightExp;
    private final List<String> beltsList;

    public PlayerInfo(Player player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        this.collectLevel = playerData.getInt("collect_level");
        this.collectExp = playerData.getInt("collect_exp");
        this.makeLevel = playerData.getInt("make_level");
        this.makeExp = playerData.getInt("make_exp");
        this.fightLevel = playerData.getInt("fight_level");
        this.fightExp = playerData.getInt("fight_exp");
        this.beltsList = playerData.getStringList("belts");
    }

    public int getCollectLevel() {
        return collectLevel;
    }

    public int getCollectExp() {
        return collectExp;
    }

    public int getMakeLevel() {
        return makeLevel;
    }

    public int getMakeExp() {
        return makeExp;
    }

    public int getFightLevel() {
        return fightLevel;
    }

    public int getFightExp() {
        return fightExp;
    }

    public List<String> getBeltsList() {
        return beltsList;
    }
}
