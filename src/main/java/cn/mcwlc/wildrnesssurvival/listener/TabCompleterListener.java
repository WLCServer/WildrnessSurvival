package cn.mcwlc.wildrnesssurvival.listener;

import cn.mcwlc.wildrnesssurvival.manager.BeltManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DongShaoNB
 */
public class TabCompleterListener implements TabCompleter {

    private final String[] commonSubCommand = {"help", "belt", "level"};
    private final String[] adminSubCommand = {"help", "belt", "level", "backup", "update", "reload"};
    private final String[] beltCommonSubCommand = {"gui"};
    private final String[] beltAdminSubCommand = {"gui", "give"};
    private final String[] allLevel = {"collect", "make", "fight"};
    private final String[] levelCommonSubCommand = {"check"};
    private final String[] levelAdminSubCommand = {"check", "add", "remove", "set"};
    private final String[] levelControlNumber = {"50", "100", "200", "500", "1000", "2000", "5000", "10000"};

    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {
        if (args.length == 1) {
            if (commandSender.hasPermission("ws.admin")) {
                return List.of(adminSubCommand);
            } else {
                return List.of(commonSubCommand);
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "belt" -> {
                    if (commandSender.hasPermission("ws.admin")) {
                        return List.of(beltAdminSubCommand);
                    } else {
                        return List.of(beltCommonSubCommand);
                    }
                }
                case "level" -> {
                    return List.of(allLevel);
                }
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "belt":
                    switch (args[1]) {
                        case "give":
                            if (commandSender.hasPermission("ws.admin")) {
                                List<String> playerList = new ArrayList<>();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    playerList.add(player.getName());
                                }
                                return playerList;
                            }
                    }
                case "level":
                    if (commandSender.hasPermission("ws.admin")) {
                        return List.of(levelAdminSubCommand);
                    } else {
                        return List.of(levelCommonSubCommand);
                    }
            }
            return List.of();
        } else if (args.length == 4) {
            switch (args[0]) {
                case "belt":
                    switch (args[1]) {
                        case "give":
                            if (commandSender.hasPermission("ws.admin")) {
                                return BeltManager.getAllBeltsId();
                            }
                    }
                case "level":
                    switch (args[2]) {
                        case "add", "remove", "set", "check" -> {
                            if (commandSender.hasPermission("ws.admin")) {
                                List<String> playerList = new ArrayList<>();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    playerList.add(player.getName());
                                }
                                return playerList;
                            } else {
                                return List.of();
                            }
                        }
                    }

            }
            return List.of();
        } else if (args.length == 5) {
            switch (args[2]) {
                case "add", "remove", "set" -> {
                    if (commandSender.hasPermission("ws.admin")) {
                        return List.of(levelControlNumber);
                    } else {
                        return List.of();
                    }
                }
            }
        }
        return List.of();
    }
}
