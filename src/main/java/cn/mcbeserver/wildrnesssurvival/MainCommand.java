package cn.mcbeserver.wildrnesssurvival;

import cn.mcbeserver.wildrnesssurvival.em.Skill;
import cn.mcbeserver.wildrnesssurvival.inventorys.BeltInventory;
import cn.mcbeserver.wildrnesssurvival.utils.BeltInfo;
import cn.mcbeserver.wildrnesssurvival.utils.BeltsManager;
import cn.mcbeserver.wildrnesssurvival.utils.PlayerManager;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author DongShaoNB
 */
public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length > 0) {
            if (commandSender instanceof Player player) {
                switch (args[0]) {
                    case "level" -> {
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "collect":
                                case "make":
                                case "fight":
                                    String skill = args[1];
                                    if (args.length > 2) {
                                        switch (args[2]) {
                                            case "check" -> {
                                                if (args.length > 3) {
                                                    OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[3]);
                                                    if (PlayerManager.isExist(player1)) {
                                                        int playerLevel = PlayerManager.getLevel(player1, Skill.getSkill(skill));
                                                        player.sendMessage(
                                                                WildrnessSurvival.getPrefix() + Message.checkPlayerLevel
                                                                        .replace("%player%", player1.getName())
                                                                        .replace("%skill%", Skill.getSkill(skill).getSkillName())
                                                                        .replace("%level%", String.valueOf(playerLevel))
                                                                        .replace("%nowexp%", String.valueOf(PlayerManager.getNowAlreadyExp(player1, Skill.getSkill(skill))))
                                                                        .replace("%upallexp%", String.valueOf(PlayerManager.getUpAllExp(player1, Skill.getSkill(skill)))));
                                                    } else {
                                                        player.sendMessage(WildrnessSurvival.getPrefix() + Message.unknownPlayer);
                                                    }
                                                } else {
                                                    int playerLevel = PlayerManager.getLevel(player, Skill.getSkill(skill));
                                                    player.sendMessage(
                                                            WildrnessSurvival.getPrefix() + Message.checkLevel
                                                                    .replace("%player%", player.getName())
                                                                    .replace("%skill%", Skill.getSkill(skill).getSkillName())
                                                                    .replace("%level%", String.valueOf(playerLevel))
                                                                    .replace("%nowexp%", String.valueOf(PlayerManager.getNowAlreadyExp(player, Skill.getSkill(skill))))
                                                                    .replace("%upallexp%", String.valueOf(PlayerManager.getUpAllExp(player, Skill.getSkill(skill)))));
                                                }
                                                return false;
                                            }
                                            case "add" -> {
                                                if (player.hasPermission("ws.admin")) {
                                                    if (args.length > 4) {
                                                        OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[3]);
                                                        if (PlayerManager.isExist(player1)) {
                                                            int playerNowExp = PlayerManager.getExp(player1, Skill.getSkill(skill));
                                                            int addExp = Integer.parseInt(args[4]);
                                                            PlayerManager.setExp(player1, Skill.getSkill(skill), playerNowExp + addExp);
                                                        } else {
                                                            player.sendMessage(WildrnessSurvival.getPrefix() + Message.unknownPlayer);
                                                        }
                                                    }
                                                } else {
                                                    player.sendMessage(WildrnessSurvival.getPrefix() + Message.noPermission);
                                                }
                                                return false;
                                            }
                                            case "remove" -> {
                                                return false;
                                            }
                                            case "set" -> {
                                                if (player.hasPermission("ws.admin")) {
                                                    if (args.length > 4) {
                                                        OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[3]);
                                                        if (PlayerManager.isExist(player1)) {
                                                            int setLevel = Integer.parseInt(args[4]);
                                                            PlayerManager.setLevel(player1, Skill.getSkill(skill), setLevel);
                                                        } else {
                                                            player.sendMessage(WildrnessSurvival.getPrefix() + Message.errorParameters);
                                                        }
                                                    } else {
                                                        player.sendMessage(WildrnessSurvival.getPrefix() + Message.unknownPlayer);
                                                    }
                                                } else {
                                                    player.sendMessage(WildrnessSurvival.getPrefix() + Message.noPermission);
                                                }
                                                return false;
                                            }
                                        }
                                    } else {
                                        player.sendMessage(WildrnessSurvival.getPrefix() + Message.appointControl);
                                        return false;
                                    }
                                default:
                                    player.sendMessage(WildrnessSurvival.getPrefix() + Message.skillNotExist);
                                    return false;
                            }
                        } else {
                            player.sendMessage(WildrnessSurvival.getPrefix() + Message.appointSkill);
                        }
                        return false;
                    }
                    case "belt" -> {
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "give" -> {
                                    if (player.hasPermission("ws.admin")) {
                                        if (args.length > 2) {
                                            Player player1 = Bukkit.getPlayer(args[2]);
                                            String beltId = args[3];
                                            if (player1 != null) {
                                                if (BeltsManager.getBeltExist(beltId)) {
                                                    BeltInfo beltInfo = new BeltInfo(beltId);
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
                                                    nbtItem.setString("beltID", beltId);
                                                    nbtItem.setBoolean("HideFlags", true);
                                                    player1.getInventory().addItem(itemStack);
                                                } else {
                                                    WildrnessSurvival.getInstance().getLogger().info("§c无法找到该饰品!");
                                                }
                                            } else {
                                                WildrnessSurvival.getInstance().getLogger().info("§c该玩家不在线!");
                                            }
                                        } else {
                                            player.sendMessage(WildrnessSurvival.getPrefix() + Message.appointBeltId);
                                        }
                                    } else {
                                        player.sendMessage(WildrnessSurvival.getPrefix() + Message.noPermission);
                                    }
                                }
                                case "gui" -> {
                                    BeltInventory.send(player);
                                }
                            }
                        } else {
                            BeltInventory.send(player);
                        }
                        return false;
                    }
                    case "backup" -> {
                        if (player.hasPermission("ws.admin")) {
                            try {
                                WildrnessSurvival.backupAllResources();
                                    player.sendMessage(WildrnessSurvival.getPrefix() + Message.backupSuccess);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return false;
                    }
                    case "update" -> {
                        if (player.hasPermission("ws.admin")) {
                            try {
                                WildrnessSurvival.backupAllResources();
                                player.sendMessage(WildrnessSurvival.getPrefix() + Message.backupSuccess);
                                WildrnessSurvival.updateAllResources();
                                player.sendMessage(WildrnessSurvival.getPrefix() + Message.updateSuccess);
                                WildrnessSurvival.reloadPlugin();
                                player.sendMessage(WildrnessSurvival.getPrefix() + Message.reloadSuccess);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return false;
                    }
                    case "reload" -> {
                        if (player.hasPermission("ws.admin")) {
                            WildrnessSurvival.reloadPlugin();
                            player.sendMessage(WildrnessSurvival.getPrefix() + Message.reloadSuccess);
                        } else {
                            player.sendMessage(WildrnessSurvival.getPrefix() + Message.noPermission);
                        }
                        return false;
                    }
                    default -> {
                        player.sendMessage(Message.help);
                        return false;
                    }
                }
            } else {
                switch (args[0]) {
                    case "belt":
                        if (args.length > 1) {
                            switch (args[1]) {
                                case "give" -> {
                                    if (args.length > 2) {
                                        Player player = Bukkit.getPlayer(args[2]);
                                        String beltId = args[3];
                                        if (player != null) {
                                            if (BeltsManager.getBeltExist(beltId)) {
                                                BeltInfo beltInfo = new BeltInfo(beltId);
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
                                                nbtItem.setString("beltID", beltId);
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
                                }
                                case "gui" -> {
                                    WildrnessSurvival.getInstance().getLogger().info("§c该命令只能在游戏中使用!");
                                    return false;
                                }
                            }
                        } else {
                            WildrnessSurvival.getInstance().getLogger().info("§c该命令只能在游戏中使用!");
                            return false;
                        }
                    case "reload":
                        WildrnessSurvival.getInstance().reloadConfig();
                        WildrnessSurvival.getInstance().getLogger().info(Message.reloadSuccess);
                    case "help":
                    default:
                        WildrnessSurvival.getInstance().getLogger().info(Message.help);
                        return false;
                }
            }
        } else {
            if (commandSender instanceof Player player) {
                player.sendMessage(Message.help);
            } else {
                WildrnessSurvival.getInstance().getLogger().info(Message.help);
            }
            return false;
        }
    }
}
