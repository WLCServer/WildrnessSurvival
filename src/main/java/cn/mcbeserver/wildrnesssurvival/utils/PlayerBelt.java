package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class PlayerBelt {

    public static void setPlayerBeltsList(Player player, List<String> beltsList) throws IOException {
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
        PlayerInfo playerInfo = new PlayerInfo(player);
        List<String> playerBelts = playerInfo.getBeltsList();
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
