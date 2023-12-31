package cn.mcwlc.wildrnesssurvival.manager;

import cn.mcwlc.wildrnesssurvival.em.Attribute;
import cn.mcwlc.wildrnesssurvival.em.Quality;
import cn.mcwlc.wildrnesssurvival.em.Skill;
import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import cn.mcwlc.wildrnesssurvival.util.BeltInfo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


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
                e.printStackTrace();
            }
        });
    }

    public static HashMap<Attribute, Double> getPlayerAttribute(Player player) {
        HashMap<Attribute, Double> attributeHashMap = new HashMap<>();
        List<String> playerBelts = getBeltsList(player);
        for (String beltId : playerBelts) {
            BeltInfo beltInfo = new BeltInfo(beltId);
            HashMap<Attribute, Double> attributeHashMap2 = beltInfo.getAttribute();
            for (Attribute attribute: attributeHashMap2.keySet()) {
                if (attributeHashMap.containsKey(attribute)) {
                    attributeHashMap.replace(attribute, attributeHashMap.get(attribute) + attributeHashMap2.get(attribute));
                } else {
                    attributeHashMap.put(attribute, attributeHashMap2.get(attribute));
                }
            }
        }
        return attributeHashMap;
    }

    public static void playPlot(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(WildrnessSurvival.getInstance(), () -> {
            WildrnessSurvival.getInstance().getLogger().info("§a正在给玩家 " + player.getName() + " 播放背景故事!");
            for (String plotWord : WildrnessSurvival.getInstance().getConfig().getStringList("plot.words")) {
                int plotWordNumber = plotWord.length();
                player.sendTitle(plotWord, "", 10, plotWordNumber * 8, 20);
                Bukkit.getScheduler().runTask(WildrnessSurvival.getInstance(), () -> {
                    new PotionEffect(PotionEffectType.BLINDNESS, plotWordNumber * 15, 255, false, false).apply(player);

                });
                try {
                    Thread.sleep(plotWordNumber * 300L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (WildrnessSurvival.getInstance().getConfig().getBoolean("plot.teleport.enable")) {
                Bukkit.getScheduler().runTask(WildrnessSurvival.getInstance(), () -> {
                    Location location = new Location(Bukkit.getWorld(WildrnessSurvival.getInstance().getConfig().getString("plot.teleport.world")), WildrnessSurvival.getInstance().getConfig().getInt("plot.teleport.x"), WildrnessSurvival.getInstance().getConfig().getInt("plot.teleport.y"), WildrnessSurvival.getInstance().getConfig().getInt("plot.teleport.z"));
                    player.teleport(location);
                });
            }
            randomParentBelt(player);
        });
    }

    public static void randomParentBelt(Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(WildrnessSurvival.getInstance(), () -> {
           Random random =  new Random();
           int randomNumber = random.nextInt(4);
           String beltId;
           if (randomNumber == 0) {
               beltId = "parent_common";
           } else if (randomNumber == 1) {
               beltId = "parent_rare";
           } else if (randomNumber == 2) {
               beltId = "parent_epic";
           } else {
               beltId = "parent_legend";
           }
           BeltInfo beltInfo = new BeltInfo(beltId);
           Quality quality = beltInfo.getQuality();
           player.getInventory().addItem(BeltManager.getItemStack(beltId));
               WildrnessSurvival.getInstance().getLogger().info("§a玩家 " + player.getName() + " 随机获得了 %belt% §a!"
                       .replace("%belt%", quality.getQualityColor() + beltInfo.getBeltName()));
        });
    }

}
