package cn.mcbeserver.wildrnesssurvival.listeners;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.utils.PlayerManager;
import de.tr7zw.nbtapi.NBTItem;
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

/**
 * @author DongShaoNB
 */
public class InventoryListener implements Listener {

    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        InventoryAction inventoryAction = event.getAction();
        if ("§a§l我的饰品".equalsIgnoreCase(event.getWhoClicked().getOpenInventory().getTitle())) {
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
                } else {
                    if (event.getCurrentItem() != null) {
                        if (event.isShiftClick()) {
                            ItemStack itemStack = event.getCurrentItem();
                            NBTItem nbtItem = new NBTItem(itemStack);
                            if (nbtItem.getString("beltID").isEmpty()) {
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public static void onInventoryClose(InventoryCloseEvent event) throws IOException {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if ("§a§l我的饰品".equalsIgnoreCase(event.getPlayer().getOpenInventory().getTitle())) {
            List<String> beltsList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                if (inventory.getItem(i) != null) {
                    if (inventory.getItem(i).getType() != Material.BARRIER && inventory.getItem(i).getType() != Material.AIR && inventory.getItem(i).getType() != null) {
                        ItemStack itemStack = inventory.getItem(i);
                        NBTItem nbtItem = new NBTItem(itemStack);
                        String beltId = nbtItem.getString("beltID");
                        beltsList.add(beltId);
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
