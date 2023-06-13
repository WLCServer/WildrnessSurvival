package cn.mcbeserver.wildrnesssurvival.api;

import cn.mcbeserver.wildrnesssurvival.em.Attribute;
import cn.mcbeserver.wildrnesssurvival.em.Skill;
import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.util.BeltInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;


/**
 * @author DongShaoNB
 */
public class PlayerManager {

    public static boolean isExist(OfflinePlayer player) {
        return new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml").exists();
    }

    public static int getExp(OfflinePlayer player, Skill skill) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt(skill.getSkillId() + "_exp");
    }

    public static void addExp(OfflinePlayer player, Skill skill, int exp) {
        setExp(player, skill, getExp(player, skill) + exp);
    }

    public static void setExp(OfflinePlayer player, Skill skill, int exp) {
        Bukkit.getScheduler().runTaskAsynchronously(WildrnessSurvival.getInstance(), () -> {
            File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
            playerData.set(skill.getSkillId() + "_exp", exp);
            try {
                playerData.save(dataFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static int getLevel(OfflinePlayer player, Skill skill) {
        int playerLevelExp = PlayerManager.getExp(player, skill);
        int upNeedExpMultiple = WildrnessSurvival.getInstance().getConfig().getInt("up-need-exp-multiple");
        int level = 0;
        int multiple = 0;
        while (playerLevelExp >= multiple * upNeedExpMultiple) {
            playerLevelExp -= multiple * upNeedExpMultiple;
            level++;
            multiple++;
        }
        return level;
    }

    public static void setLevel(OfflinePlayer player, Skill skill, int level) {
        int exp = 0;
        int upNeedExpMultiple = WildrnessSurvival.getInstance().getConfig().getInt("up-need-exp-multiple");
        while (level != 0) {
            exp += (level - 1) * upNeedExpMultiple;
            level--;
        }
        PlayerManager.setExp(player, skill, exp);
    }

    public static int getNowExp(OfflinePlayer player, Skill skill) {
        int playerLevelExp = PlayerManager.getExp(player, skill);
        int upNeedExpMultiple = WildrnessSurvival.getInstance().getConfig().getInt("up-need-exp-multiple");
        for (int i = 0; playerLevelExp >= upNeedExpMultiple * i; i++) {
            playerLevelExp -= upNeedExpMultiple * i;
        }
        return playerLevelExp;
    }

    public static int getUpAllExp(OfflinePlayer player, Skill skill) {
        int playerLevelExp = PlayerManager.getExp(player, skill);
        int upNeedExpMultiple = WildrnessSurvival.getInstance().getConfig().getInt("up-need-exp-multiple");
        int i = 0;
        while (playerLevelExp >= upNeedExpMultiple * i) {
            playerLevelExp -= upNeedExpMultiple * i;
            i++;
        }
        return upNeedExpMultiple * i;
    }

    public static double getExpPercent(OfflinePlayer player, Skill skill) {
        return PlayerManager.getNowExp(player, skill) / (double) PlayerManager.getUpAllExp(player, skill);
    }

    public static int getUpNeedExp(OfflinePlayer player, Skill skill) {
        return getUpAllExp(player, skill) - getNowExp(player, skill);
    }

    public static int getWeek(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getInt("week");
    }

    public static void setWeek(OfflinePlayer player, int pweek) {
        Bukkit.getScheduler().runTaskAsynchronously(WildrnessSurvival.getInstance(), () -> {
            File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
            playerData.set("week", pweek);
            try {
                playerData.save(dataFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static List<String> getBeltsList(OfflinePlayer player) {
        File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
        return playerData.getStringList("belts");
    }

    public static Integer getBeltsNumber(OfflinePlayer player) {
        return getBeltsList(player).size();
    }

    public static void setBeltsList(OfflinePlayer player, List<String> beltsList) {
        Bukkit.getScheduler().runTaskAsynchronously(WildrnessSurvival.getInstance(), () -> {
            File dataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/", player.getUniqueId() + ".yml");
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(dataFile);
            playerData.set("belts", beltsList);
            try {
                playerData.save(dataFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static HashMap<Attribute, Integer> getPlayerAttribute(Player player) {
        HashMap<Attribute, Integer> attributeHashMap = new HashMap<>();
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
        attributeHashMap.put(Attribute.HEALTH, health);
        attributeHashMap.put(Attribute.ATTACK, attack);
        attributeHashMap.put(Attribute.DEFENSE, defense);
        attributeHashMap.put(Attribute.SPEED, speed);
        return attributeHashMap;
    }

    public static HashMap<Attribute, Integer> getBeltsAttribute(List<String> beltList) {
        HashMap<Attribute, Integer> attributeHashMap = new HashMap<>();
        int health = 0;
        int attack = 0;
        int defense = 0;
        int speed = 0;
        for (String belt : beltList) {
            BeltInfo beltInfo = new BeltInfo(belt);
            health = health + beltInfo.getHealth();
            attack = attack + beltInfo.getAttack();
            defense = defense + beltInfo.getDefense();
            speed = speed + beltInfo.getSpeed();
        }
        attributeHashMap.put(Attribute.HEALTH, health);
        attributeHashMap.put(Attribute.ATTACK, attack);
        attributeHashMap.put(Attribute.DEFENSE, defense);
        attributeHashMap.put(Attribute.SPEED, speed);
        return attributeHashMap;
    }

}
