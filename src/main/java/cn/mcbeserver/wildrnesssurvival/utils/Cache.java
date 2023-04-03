package cn.mcbeserver.wildrnesssurvival.utils;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;

import java.util.HashMap;

public class Cache {

    private static final HashMap<String, String> levelECList = new HashMap<>();

    public static void setupCache() {
        levelECList.put("collect", "采集");
        levelECList.put("make", "制作");
        levelECList.put("fight", "制作");
        WildrnessSurvival.getInstance().getLogger().info("§a缓存已加载!");
    }

    public static HashMap<String, String> getLevelECList() {
        return levelECList;
    }

    public static void addCache(String key, String value) {
        levelECList.put(key, value);
    }

}
