package cn.mcwlc.wildrnesssurvival.inventory;

import cn.mcwlc.wildrnesssurvival.manager.BeltManager;
import cn.mcwlc.wildrnesssurvival.em.Skill;
import cn.mcwlc.wildrnesssurvival.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
            for (String beltId : PlayerManager.getBeltsList(player)) {
                if (!beltId.isEmpty()) {
                    ItemStack itemStack = BeltManager.getItemStack(beltId);
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
