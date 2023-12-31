package cn.mcwlc.wildrnesssurvival.manager;

import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import cn.mcwlc.wildrnesssurvival.em.Attribute;
import cn.mcwlc.wildrnesssurvival.em.Quality;
import cn.mcwlc.wildrnesssurvival.util.BeltInfo;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author DongShaoNB
 */
public class BeltManager {

    public static File DATA_FILE = new File(WildrnessSurvival.getInstance().getDataFolder(), "belts.yml");
    public static FileConfiguration ALL_BELT_DATA = YamlConfiguration.loadConfiguration(DATA_FILE);

    public static void registerBelts() {
        int beltsNumber = ALL_BELT_DATA.getKeys(false).toArray().length;
        int startRegisterNumber = 1;
        for (String beltId : ALL_BELT_DATA.getKeys(false)) {
            WildrnessSurvival.getInstance().getLogger().info("§a注册饰品: §b" + beltId + " §6(%nowNumber%/%allNumber%)"
                    .replace("%nowNumber%", String.valueOf(startRegisterNumber))
                    .replace("%allNumber%", String.valueOf(beltsNumber)));
            startRegisterNumber++;
        }
        WildrnessSurvival.getInstance().getLogger().info("§a成功注册所有饰品!");
    }

    public static ItemStack getItemStack(String beltId) {
        BeltInfo beltInfo = new BeltInfo(beltId);
        ItemStack itemStack = new ItemStack(Material.getMaterial(beltInfo.getMaterialName()));
        ItemMeta itemMeta = itemStack.getItemMeta();
        Quality quality = beltInfo.getQuality();
        itemMeta.setDisplayName(quality.getQualityColor() + beltInfo.getBeltName());
        itemMeta.setLore(beltInfo.getLore());
        itemStack.setItemMeta(itemMeta);
        itemStack.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
        NBTItem nbtItem = new NBTItem(itemStack, true);
        nbtItem.setString("beltID", beltId);
        nbtItem.setBoolean("HideFlags", true);
        return itemStack;
    }

    public static HashMap<Attribute, Double> getBeltsAttribute(List<String> beltList) {
        HashMap<Attribute, Double> attributeHashMap = new HashMap<>();
        for (String beltId : beltList) {
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

    public static boolean isExist(String beltId) {
        return ALL_BELT_DATA.getConfigurationSection(beltId) != null;
    }

    public static List<String> getAllBeltsId() {
        return new ArrayList<>(ALL_BELT_DATA.getKeys(false));
    }

}
