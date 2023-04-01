package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PlayerManager {

    public static int getCollectLevel(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("collect_level");
    }

    public static int setCollectLevel(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("collect_level");
    }

    public static int getCollectExp(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("collect_exp");
    }

    public static int getMakeLevel(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("make_level");
    }

    public static int getMakeExp(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("make_exp");
    }

    public static int getFightLevel(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("fight_level");
    }

    public static int getFightExp(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("fight_exp");
    }

    public static int getPWeek(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("pweek");
    }

    public static void setPWeek(OfflinePlayer player, int pweek) throws IOException{
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        playerData.set("pweek", pweek);
        playerData.save(dataFile);
    }

    public static List<String> getBeltsList(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getStringList("belts");
    }

    public static void setBeltsList(OfflinePlayer player, List<String> beltsList) throws IOException {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        playerData.set("belts", beltsList);
        playerData.save(dataFile);
    }

    public static HashMap<String, Integer> getPlayerAttribute(Player player) {
        HashMap<String, Integer> attributeHashMap = new HashMap<>();
        int health = 0;
        int attack = 0;
        int defense = 0;
        int speed = 0;
        List<String> playerBelts = getBeltsList(player);
        for (String belt : playerBelts) {
            BeltInfo beltInfo = new BeltInfo(belt);
            health = health + beltInfo.getHealth();
            attack = attack + beltInfo.getAttack();
            defense = defense + beltInfo.getDefense();
            speed = speed + beltInfo.getSpeed();
        }
        attributeHashMap.put("health", health);
        attributeHashMap.put("attack", attack);
        attributeHashMap.put("defense", defense);
        attributeHashMap.put("speed", speed);
        return attributeHashMap;
    }

}
