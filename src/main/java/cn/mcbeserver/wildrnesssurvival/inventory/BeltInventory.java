package cn.mcbeserver.wildrnesssurvival.inventory;

import cn.mcbeserver.wildrnesssurvival.em.Skill;
import cn.mcbeserver.wildrnesssurvival.util.BeltInfo;
import cn.mcbeserver.wildrnesssurvival.api.PlayerManager;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author DongShaoNB
 */
public class BeltInventory {

    public static void send(Player player) {

        Inventory inventory = Bukkit.createInventory(null, 9, "§a§l我的饰品");

            for (String belt : PlayerManager.getBeltsList(player)) {
                if (!belt.isEmpty()) {
                    BeltInfo beltInfo = new BeltInfo(belt);
                    ItemStack itemStack = new ItemStack(Material.getMaterial(beltInfo.getMaterialName()));
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    itemMeta.setDisplayName(beltInfo.getBeltName());
                    itemMeta.setLore(beltInfo.getLore());
                    itemStack.setItemMeta(itemMeta);
                    if (beltInfo.isEnchant()) {
                        itemStack.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                    }
                    NBTItem nbtItem = new NBTItem(itemStack, true);
                    nbtItem.setString("beltID", belt);
                    nbtItem.setBoolean("HideFlags", true);
                    inventory.addItem(itemStack);
                }
            }

        int playerFightLevel = PlayerManager.getLevel(player, Skill.FIGHT);
        ItemStack itemStack = new ItemStack(Material.BARRIER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        int forNumber;
        if (playerFightLevel < 25) {
            forNumber = 0;
        } else if (playerFightLevel < 50) {
            forNumber = 1;
        } else if (playerFightLevel < 75) {
            forNumber = 2;
        } else if (playerFightLevel < 100) {
            forNumber = 3;
        } else if (playerFightLevel < 125) {
            forNumber = 4;
        } else if (playerFightLevel < 150) {
            forNumber = 5;
        } else if (playerFightLevel < 175) {
            forNumber = 6;
        } else if (playerFightLevel < 200) {
            forNumber = 7;
        } else {
            forNumber = 8;
        }

        for (int i = 8; i != forNumber; i--) {
            itemMeta.setDisplayName("§c§l战斗 " + i * 25 + " 级解锁该槽位");
            itemStack.setItemMeta(itemMeta);
            inventory.setItem(i, itemStack);
        }

        player.openInventory(inventory);

    }

}
