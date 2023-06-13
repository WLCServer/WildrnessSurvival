package cn.mcbeserver.wildrnesssurvival.listener;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.api.PlayerManager;
import cn.mcbeserver.wildrnesssurvival.em.Attribute;
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
        if (event.getPlayer() instanceof Player player) {
            HashMap<Attribute, Integer> oldPlayerAttributeList = PlayerManager.getPlayerAttribute(player);
            if ("§a§l我的饰品".equalsIgnoreCase(event.getPlayer().getOpenInventory().getTitle())) {
                List<String> beltsList = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    if (inventory.getItem(i) != null) {
                        if (inventory.getItem(i).getType() != Material.BARRIER && inventory.getItem(i).getType() != Material.AIR && inventory.getItem(i).getType() != null) {
                            ItemStack itemStack = inventory.getItem(i);
                            NBTItem nbtItem = new NBTItem(itemStack);
                            String beltId = nbtItem.getString("beltID");
                            beltsList.add(beltId);
                        }
                        break;
                    }
                }
                PlayerManager.setBeltsList(player, beltsList);
                HashMap<Attribute, Integer> playerAttributeList = PlayerManager.getBeltsAttribute(beltsList);
                String healthChange = "";
                String attackChange = "";
                String defenseChange = "";
                String speedChange = "";
                if (playerAttributeList.get(Attribute.HEALTH) - oldPlayerAttributeList.get(Attribute.HEALTH) > 0) {
                    healthChange += "§a+" + (playerAttributeList.get(Attribute.HEALTH) - oldPlayerAttributeList.get(Attribute.HEALTH));
                } else if (playerAttributeList.get(Attribute.HEALTH) - oldPlayerAttributeList.get(Attribute.HEALTH) < 0) {
                    healthChange += "§c-" + (oldPlayerAttributeList.get(Attribute.HEALTH) - playerAttributeList.get(Attribute.HEALTH));
                } else {
                    healthChange += "§f0";
                }

                player.sendMessage(WildrnessSurvival.getPrefix() + "§a你的饰品总属性: ");
                player.sendMessage(WildrnessSurvival.getPrefix() + "§a生命: " + playerAttributeList.get(Attribute.HEALTH));
                player.sendMessage(WildrnessSurvival.getPrefix() + "§a攻击: " + playerAttributeList.get(Attribute.ATTACK));
                player.sendMessage(WildrnessSurvival.getPrefix() + "§a防御: " + playerAttributeList.get(Attribute.DEFENSE));
                player.sendMessage(WildrnessSurvival.getPrefix() + "§a速度: " + playerAttributeList.get(Attribute.SPEED));
            }
        }
    }

}
