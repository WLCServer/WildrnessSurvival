package cn.mcbeserver.wildrnesssurvival.listeners;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.utils.PlayerManager;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryListener implements Listener {

    @EventHandler
    public static void InventoryClickEvent(InventoryClickEvent event) {
        InventoryAction inventoryAction = event.getAction();
        if (event.getWhoClicked().getOpenInventory().getTitle().equalsIgnoreCase("§a§l我的饰品")) {
            if (event.getClickedInventory() != null) {
                if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
                    if (event.getCurrentItem() != null) {
                        if (event.getCurrentItem().getType() == Material.BARRIER) {
                            event.setCancelled(true);
                        }
                    }
                    if (inventoryAction == InventoryAction.PLACE_ONE || inventoryAction == InventoryAction.PLACE_SOME || inventoryAction == InventoryAction.PLACE_ALL || inventoryAction == InventoryAction.SWAP_WITH_CURSOR) {
                        ItemStack itemStack = event.getCursor();
                        NBTItem nbtItem = new NBTItem(itemStack);
                        if (nbtItem.getString("beltID").isEmpty()) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public static void InventoryCloseEvent(InventoryCloseEvent event) throws IOException {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (event.getPlayer().getOpenInventory().getTitle().equalsIgnoreCase("§a§l我的饰品")) {
            List<String> beltsList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i) != null) {
                    if (inventory.getItem(i).getType() != Material.BARRIER && inventory.getItem(i).getType() != Material.AIR && inventory.getItem(i).getType() != null) {
                        ItemStack itemStack = inventory.getItem(i);
                        NBTItem nbtItem = new NBTItem(itemStack);
                        String beltID = nbtItem.getString("beltID");
                        beltsList.add(beltID);
                    } else {
                        break;
                    }
                }
            }
            PlayerManager.setBeltsList(player, beltsList);
            HashMap<String, Integer> playerAttributeList = PlayerManager.getPlayerAttribute(player);
            player.sendMessage(WildrnessSurvival.getPrefix() + "§a你的饰品总属性: ");
            player.sendMessage(WildrnessSurvival.getPrefix() + "§a生命上限 +" + playerAttributeList.get("health"));
            player.sendMessage(WildrnessSurvival.getPrefix() + "§a攻击力 +" + playerAttributeList.get("attack"));
            player.sendMessage(WildrnessSurvival.getPrefix() + "§a防御力 +" + playerAttributeList.get("defense"));
            player.sendMessage(WildrnessSurvival.getPrefix() + "§a速度 +" + playerAttributeList.get("speed"));
        }
    }

}
