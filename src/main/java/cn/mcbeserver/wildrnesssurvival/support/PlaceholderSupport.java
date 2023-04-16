package cn.mcbeserver.wildrnesssurvival.support;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.em.Skill;
import cn.mcbeserver.wildrnesssurvival.utils.PlayerManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * @author DongShaoNB
 */
public class PlaceholderSupport extends PlaceholderExpansion {

    public PlaceholderSupport(WildrnessSurvival wildrnessSurvival) {
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
        if ("collect".equalsIgnoreCase(params[0])) {
            switch (params[1]) {
                case "level" -> {
                    return String.valueOf(PlayerManager.getLevel(player, Skill.COLLECT));
                }
                case "exp" -> {
                    return String.valueOf(PlayerManager.getExp(player, Skill.COLLECT));
                }
                case "progress" -> {
                    if (params[2] != null) {
                        int perLong = Integer.parseInt(params[2]);
                        StringBuilder per = new StringBuilder();
                        int alreadyPer = (int) Math.round(PlayerManager.getExpPercent(player, Skill.COLLECT) * perLong);
                        per.append("§a|".repeat(Math.max(0, alreadyPer)));
                        per.append("§r|".repeat(Math.max(0, perLong - alreadyPer)));
                        return per.toString();
                    }
                }
            }
        }

        if ("make".equalsIgnoreCase(params[0])) {
            switch (params[1]) {
                case "level" -> {
                    return String.valueOf(PlayerManager.getLevel(player, Skill.MAKE));
                }
                case "exp" -> {
                    return String.valueOf(PlayerManager.getExp(player, Skill.MAKE));
                }
                case "progress" -> {
                    if (params[2] != null) {
                        int perLong = Integer.parseInt(params[2]);
                        StringBuilder per = new StringBuilder();
                        int alreadyPer = (int) Math.round(PlayerManager.getExpPercent(player, Skill.MAKE) * perLong);
                        per.append("§a|".repeat(Math.max(0, alreadyPer)));
                        per.append("§r|".repeat(Math.max(0, perLong - alreadyPer)));
                        return per.toString();
                    }
                }
            }
        }

        if ("fight".equalsIgnoreCase(params[0])) {
            switch (params[1]) {
                case "level" -> {
                    return String.valueOf(PlayerManager.getLevel(player, Skill.FIGHT));
                }
                case "exp" -> {
                    return String.valueOf(PlayerManager.getExp(player, Skill.FIGHT));
                }
                case "progress" -> {
                    if (params[2] != null) {
                        int perLong = Integer.parseInt(params[2]);
                        StringBuilder per = new StringBuilder();
                        int alreadyPer = (int) Math.round(PlayerManager.getExpPercent(player, Skill.FIGHT) * perLong);
                        per.append("§a|".repeat(Math.max(0, alreadyPer)));
                        per.append("§r|".repeat(Math.max(0, perLong - alreadyPer)));
                        return per.toString();
                    }
                }
            };
        }
        return null;
    }
}
