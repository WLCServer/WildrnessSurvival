package cn.mcbeserver.wildrnesssurvival.listeners;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

public class PlayerListener implements Listener {

    @EventHandler
    public static void PlayerJoinServer(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String playerUUID = String.valueOf(player.getUniqueId());
        File playerDataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/" + playerUUID + ".yml");
        if (!playerDataFile.exists()) {
            WildrnessSurvival.getInstance().saveResource("playerData/defaultPlayerData.yml", false);
            File defaultPlayerData = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/defaultPlayerData.yml");
            if (defaultPlayerData.renameTo(playerDataFile)) {
                player.sendMessage(ChatColor.GOLD + "[荒野求生]" + ChatColor.GREEN + " 欢迎游玩未来城自制玩法-荒野求生，检测到您是第一次进入游戏，数据已生成完毕，可以正常生存!");
                WildrnessSurvival.getInstance().getLogger().info("§2玩家 " + playerName + " 首次进入服务器，数据已生成!");
            } else {
                player.sendMessage(ChatColor.GOLD + "[荒野求生]" + ChatColor.RED + " 你的数据无法正常生成，请不要继续生存，将此截图发送给腐竹 东少 处理! (错误信息: 无法重命名文件)");
                WildrnessSurvival.getInstance().getLogger().warning("§e玩家 " + playerName + " 生成数据时出现问题! (错误信息: 无法重命名文件)");
            }
        }
    }
}

