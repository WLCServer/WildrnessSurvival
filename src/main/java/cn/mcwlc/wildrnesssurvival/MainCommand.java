package cn.mcwlc.wildrnesssurvival;

import cn.mcwlc.wildrnesssurvival.em.Skill;
import cn.mcwlc.wildrnesssurvival.inventory.BeltInventory;
import cn.mcwlc.wildrnesssurvival.manager.BeltManager;
import cn.mcwlc.wildrnesssurvival.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
                                                    if (player.hasPermission("ws.admin")) {
                                                        OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[3]);
                                                        if (PlayerManager.isExist(player1)) {
                                                            int playerLevel = PlayerManager.getLevel(player1, Skill.getSkill(skill));
                                                            player.sendMessage(
                                                                    WildrnessSurvival.getPREFIX() + Message.getCheckPlayerLevel()
                                                                            .replace("%player%", player1.getName())
                                                                            .replace("%skill%", Skill.getSkill(skill).getSkillName())
                                                                            .replace("%level%", String.valueOf(playerLevel))
                                                                            .replace("%nowexp%", String.valueOf(PlayerManager.getNowExp(player1, Skill.getSkill(skill))))
                                                                            .replace("%upallexp%", String.valueOf(PlayerManager.getUpAllExp(player1, Skill.getSkill(skill)))));
                                                        } else {
                                                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getUnknownPlayer());
                                                        }
                                                    } else {
                                                        player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getNoPermission());
                                                    }
                                                } else {
                                                    int playerLevel = PlayerManager.getLevel(player, Skill.getSkill(skill));
                                                    player.sendMessage(
                                                            WildrnessSurvival.getPREFIX() + Message.getCheckLevel()
                                                                    .replace("%player%", player.getName())
                                                                    .replace("%skill%", Skill.getSkill(skill).getSkillName())
                                                                    .replace("%level%", String.valueOf(playerLevel))
                                                                    .replace("%nowexp%", String.valueOf(PlayerManager.getNowExp(player, Skill.getSkill(skill))))
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
                                                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getUnknownPlayer());
                                                        }
                                                    }
                                                } else {
                                                    player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getNoPermission());
                                                }
                                                return false;
                                            }
                                            case "remove" -> {
                                                if (player.hasPermission("ws.admin")) {
                                                    if (args.length > 4) {
                                                        OfflinePlayer player1 = Bukkit.getOfflinePlayer(args[3]);
                                                        if (PlayerManager.isExist(player1)) {
                                                            int playerNowExp = PlayerManager.getExp(player1, Skill.getSkill(skill));
                                                            int removeExp = Integer.parseInt(args[4]);
                                                            if (playerNowExp - removeExp < 0) {
                                                                player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getRemoveExpFailMinus()
                                                                        .replace("%player%", player1.getName())
                                                                        .replace("%skill%", Skill.getSkill(skill).getSkillName())
                                                                );
                                                            } else {
                                                                PlayerManager.setExp(player1, Skill.getSkill(skill), playerNowExp - removeExp);
                                                                player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getRemoveExpSuccess()
                                                                        .replace("%player%", player1.getName())
                                                                        .replace("%skill%", Skill.getSkill(skill).getSkillName())
                                                                        .replace("%level%", String.valueOf(PlayerManager.getLevel(player1, Skill.getSkill(skill))))
                                                                        .replace("%nowexp%", String.valueOf(PlayerManager.getNowExp(player1, Skill.getSkill(skill))))
                                                                        .replace("%upallexp%", String.valueOf(PlayerManager.getUpAllExp(player1, Skill.getSkill(skill)))));
                                                            }
                                                        } else {
                                                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getUnknownPlayer());
                                                        }
                                                    }
                                                } else {
                                                    player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getNoPermission());
                                                }
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
                                                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getErrorParameters());
                                                        }
                                                    } else {
                                                        player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getUnknownPlayer());
                                                    }
                                                } else {
                                                    player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getNoPermission());
                                                }
                                                return false;
                                            }
                                        }
                                    } else {
                                        player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getAppointControl());
                                        return false;
                                    }
                                default:
                                    player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getSkillNotExist());
                                    return false;
                            }
                        } else {
                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getAppointSkill());
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
                                                if (BeltManager.isExist(beltId)) {
                                                    ItemStack itemStack = BeltManager.getItemStack(beltId);
                                                    player1.getInventory().addItem(itemStack);
                                                } else {
                                                    WildrnessSurvival.getInstance().getLogger().info("§c无法找到该饰品!");
                                                }
                                            } else {
                                                WildrnessSurvival.getInstance().getLogger().info("§c该玩家不在线!");
                                            }
                                        } else {
                                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getAppointBeltId());
                                        }
                                    } else {
                                        player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getNoPermission());
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
                                player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getBackupSuccess());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                    case "update" -> {
                        if (player.hasPermission("ws.admin")) {
                            try {
                                WildrnessSurvival.backupAllResources();
                                player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getBackupSuccess());
                                WildrnessSurvival.updateAllResources();
                                player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getUpdateSuccess());
                                WildrnessSurvival.reloadPlugin();
                                player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getReloadSuccess());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
                    case "reload" -> {
                        if (player.hasPermission("ws.admin")) {
                            WildrnessSurvival.reloadPlugin();
                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getReloadSuccess());
                        } else {
                            player.sendMessage(WildrnessSurvival.getPREFIX() + Message.getNoPermission());
                        }
                        return false;
                    }
                    default -> {
                        player.sendMessage(Message.getHelp());
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
                                            if (BeltManager.isExist(beltId)) {
                                                ItemStack itemStack = BeltManager.getItemStack(beltId);
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
                        WildrnessSurvival.getInstance().getLogger().info(Message.getReloadSuccess());
                    case "help":
                    default:
                        WildrnessSurvival.getInstance().getLogger().info(Message.getHelp());
                        return false;
                }
            }
        } else {
            if (commandSender instanceof Player player) {
                player.sendMessage(Message.getHelp());
            } else {
                WildrnessSurvival.getInstance().getLogger().info(Message.getHelp());
            }
            return false;
        }
    }
}
