package cn.mcbeserver.wildrnesssurvival.support;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.utils.PlayerInfo;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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
        PlayerInfo playerInfo = new PlayerInfo((Player) player);

        if (params[0].equalsIgnoreCase("collect")) {
            switch (params[1]) {
                case "level":
                    return String.valueOf(playerInfo.getCollectLevel());
                case "exp":
                    return String.valueOf(playerInfo.getCollectExp());
                default:
                    return null;
            }
        }

        if (params[0].equalsIgnoreCase("make")) {
            switch (params[1]) {
                case "level":
                    return String.valueOf(playerInfo.getMakeLevel());
                case "exp":
                    return String.valueOf(playerInfo.getMakeExp());
                default:
                    return null;
            }
        }

        if (params[0].equalsIgnoreCase("fight")) {
            switch (params[1]) {
                case "level":
                    return String.valueOf(playerInfo.getFightLevel());
                case "exp":
                    return String.valueOf(playerInfo.getFightExp());
                default:
                    return null;
            }
        }


        return null;
    }

}
