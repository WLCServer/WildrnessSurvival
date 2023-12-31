package cn.mcwlc.wildrnesssurvival.listener;

import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import cn.mcwlc.wildrnesssurvival.manager.BeltManager;
import cn.mcwlc.wildrnesssurvival.manager.PlayerManager;
import cn.mcwlc.wildrnesssurvival.em.Attribute;
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
    public static void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (event.getPlayer() instanceof Player player) {
            HashMap<Attribute, Double> oldPlayerAttributeList = PlayerManager.getPlayerAttribute(player);
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
                player.sendMessage(WildrnessSurvival.getPREFIX() + "§5你的饰品总属性: ");
                HashMap<Attribute, Double> newPlayerAttributeList = BeltManager.getBeltsAttribute(beltsList);
                for (Attribute attribute : newPlayerAttributeList.keySet()) {
                    if (oldPlayerAttributeList.containsKey(attribute)) {
                        if (oldPlayerAttributeList.get(attribute).equals(newPlayerAttributeList.get(attribute))) {
                            if (oldPlayerAttributeList.get(attribute) > newPlayerAttributeList.get(attribute)) {
                                player.sendMessage(WildrnessSurvival.getPREFIX() + "§5" + attribute.getAttributeName() + ": §6" + newPlayerAttributeList.get(attribute) + " §6(§c%number%§6)"
                                        .replace("%number%", "-" + (oldPlayerAttributeList.get(attribute) - newPlayerAttributeList.get(attribute))));
                            } else if (newPlayerAttributeList.get(attribute) > oldPlayerAttributeList.get(attribute)) {
                                player.sendMessage(WildrnessSurvival.getPREFIX() + "§5" + attribute.getAttributeName() + ": §6" + newPlayerAttributeList.get(attribute) + " §6(§a%number%§6)"
                                        .replace("%number%", "§a+" + (newPlayerAttributeList.get(attribute) - oldPlayerAttributeList.get(attribute))));
                            } else {
                                player.sendMessage(WildrnessSurvival.getPREFIX() + "§5" + attribute.getAttributeName() + ": §6" + newPlayerAttributeList.get(attribute) + " §6(§f%number%§6)"
                                        .replace("%number%", "+0"));
                            }
                        }
                    } else {
                        player.sendMessage(WildrnessSurvival.getPREFIX() + "§5" + attribute.getAttributeName() + ": §6" + newPlayerAttributeList.get(attribute) + " §6(§a%number%§6)"
                                .replace("%number%", "+" + newPlayerAttributeList.get(attribute)));
                    }
                }

                if (PlayerManager.getPlayerAttribute(player).get(Attribute.WALK_SPEED) > 0) {
                    player.setWalkSpeed(PlayerManager.getPlayerAttribute(player).get(Attribute.WALK_SPEED).floatValue());
                }

            }
        }
    }

}
