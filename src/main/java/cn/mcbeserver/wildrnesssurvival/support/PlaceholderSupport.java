package cn.mcbeserver.wildrnesssurvival.support;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.utils.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderSupport extends PlaceholderExpansion {

    private final WildrnessSurvival plugin;

    public PlaceholderSupport(WildrnessSurvival plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "DongShaoNB";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "WildrnessSurvival";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String parameters) {
        String[] params = parameters.split("_");

        if (params[0].equalsIgnoreCase("collect")) {
            switch (params[1]) {
                case "level":
                    return String.valueOf(PlayerManager.getCollectLevel(player));
                case "exp":
                    return String.valueOf(PlayerManager.getCollectExp(player));
                default:
                    return null;
            }
        }

        if (params[0].equalsIgnoreCase("make")) {
            switch (params[1]) {
                case "level":
                    return String.valueOf(PlayerManager.getMakeLevel(player));
                case "exp":
                    return String.valueOf(PlayerManager.getMakeExp(player));
                default:
                    return null;
            }
        }

        if (params[0].equalsIgnoreCase("fight")) {
            switch (params[1]) {
                case "level":
                    return String.valueOf(PlayerManager.getFightLevel(player));
                case "exp":
                    return String.valueOf(PlayerManager.getFightExp(player));
                default:
                    return null;
            }
        }


        return null;
    }

}
