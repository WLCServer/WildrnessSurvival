package cn.mcbeserver.wildrnesssurvival;

import cn.mcbeserver.wildrnesssurvival.inventorys.BeltInventory;
import cn.mcbeserver.wildrnesssurvival.utils.BeltInfo;
import cn.mcbeserver.wildrnesssurvival.utils.BeltsManager;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length > 0) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                switch (args[0]) {
                    case "level":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "collect":
                                case "make":
                                case "fight":
                                    String cLevel = args[1];
                                    if (args.length > 2) {
                                        switch (args[2]) {
                                            case "check":

                                            case "add":

                                            case "remove":

                                            case "set":
                                                if (player.hasPermission("ws.admin")) {

                                                } else {
                                                    player.sendMessage(Language.noPermission);
                                                }
                                        }
                                    } else {
                                        player.sendMessage("§c请指定要操作的等级!");
                                    }
                            }
                        }
                    case "belt":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "give":
                                    if (player.hasPermission("ws.admin")) {
                                        if (args.length > 2) {
                                            Player player1 = Bukkit.getPlayer(args[2]);
                                            String beltID = args[3];
                                            if (player1 != null) {
                                                if (BeltsManager.getBeltExist(beltID)) {
                                                    BeltInfo beltInfo = new BeltInfo(beltID);
                                                    Material material = Material.getMaterial(beltInfo.getMaterialName());
                                                    ItemStack itemStack = new ItemStack(material);
                                                    ItemMeta itemMeta = itemStack.getItemMeta();
                                                    itemMeta.setDisplayName(beltInfo.getBeltName());
                                                    itemMeta.setLore(beltInfo.getLore());
                                                    itemStack.setItemMeta(itemMeta);
                                                    if (beltInfo.isEnchant()) {
                                                        itemStack.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                                                    }
                                                    NBTItem nbtItem = new NBTItem(itemStack, true);
                                                    nbtItem.setString("beltID", beltID);
                                                    nbtItem.setBoolean("HideFlags", true);
                                                    player1.getInventory().addItem(itemStack);
                                                } else {
                                                    WildrnessSurvival.getInstance().getLogger().info("§c无法找到该饰品!");
                                                }
                                            } else {
                                                WildrnessSurvival.getInstance().getLogger().info("§c该玩家不在线!");
                                            }
                                        } else {
                                            player.sendMessage("§c请指定饰品的ID!");
                                        }
                                    } else {
                                        player.sendMessage(Language.noPermission);
                                    }
                                    return false;
                                case "gui":
                                    BeltInventory.send(player);
                                    return false;
                            }
                        } else {
                            BeltInventory.send(player);
                        }
                        return false;
                    case "help":
                    default:
                        player.sendMessage(Language.help);
                        return false;
                }
            } else {
                switch (args[0]) {
                    case "belt":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "give":
                                    if (args.length > 2) {
                                        Player player = Bukkit.getPlayer(args[2]);
                                        String beltID = args[3];
                                        if (player != null) {
                                            if (BeltsManager.getBeltExist(beltID)) {
                                                BeltInfo beltInfo = new BeltInfo(beltID);
                                                Material material = Material.getMaterial(beltInfo.getMaterialName());
                                                ItemStack itemStack = new ItemStack(material);
                                                ItemMeta itemMeta = itemStack.getItemMeta();
                                                itemMeta.setDisplayName(beltInfo.getBeltName());
                                                itemMeta.setLore(beltInfo.getLore());
                                                itemStack.setItemMeta(itemMeta);
                                                if (beltInfo.isEnchant()) {
                                                    itemStack.addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1);
                                                }
                                                NBTItem nbtItem = new NBTItem(itemStack, true);
                                                nbtItem.setString("beltID", beltID);
                                                nbtItem.setBoolean("HideFlags", true);
                                                player.getInventory().addItem(itemStack);
                                            } else {
                                                WildrnessSurvival.getInstance().getLogger().info("§c无法找到该饰品!");
                                            }
                                        } else {
                                            WildrnessSurvival.getInstance().getLogger().info("§c该玩家不在线!");
                                        }
                                    } else {
                                        WildrnessSurvival.getInstance().getLogger().info("§c请指定饰品的ID!");
                                    }
                                    return false;
                                case "gui":
                                    WildrnessSurvival.getInstance().getLogger().info("§c该命令只能在游戏中使用!");
                                    return false;
                            }
                        } else {
                            WildrnessSurvival.getInstance().getLogger().info("§c该命令只能在游戏中使用!");
                            return false;
                        }
                    case "help":
                    default:
                        WildrnessSurvival.getInstance().getLogger().info(Language.help);
                        return false;
                }
            }
        } else {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                player.sendMessage("§6要获取相关信息，请前往未来城官网Wiki查看");
                player.sendMessage("§ahttps://www.mcbeserver.cn/wiki/");
            } else {
                WildrnessSurvival.getInstance().getLogger().info(Language.help);
            }
            return false;
        }
    }
}
