package cn.mcwlc.wildrnesssurvival.listener;

import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import cn.mcwlc.wildrnesssurvival.manager.PlayerManager;
import cn.mcwlc.wildrnesssurvival.em.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.HashMap;
import java.util.Random;

/**
 * @author DongShaoNB
 */
public class PlayerListener implements Listener {

    @EventHandler
    public static void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        String playerUuid = String.valueOf(player.getUniqueId());
        File playerDataFile = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/" + playerUuid + ".yml");
        if (!playerDataFile.exists()) {
            WildrnessSurvival.getInstance().saveResource("playerData/defaultPlayerData.yml", false);
            File defaultPlayerData = new File(WildrnessSurvival.getInstance().getDataFolder() + "/playerData/defaultPlayerData.yml");
            if (defaultPlayerData.renameTo(playerDataFile)) {
                player.sendMessage("§6[荒野求生] §a欢迎游玩未来城自制玩法-荒野求生，检测到您是第一次进入游戏，数据已生成完毕，可以正常生存!");
                WildrnessSurvival.getInstance().getLogger().info("§a玩家 " + playerName + " 首次进入服务器，数据已生成!");
                if (WildrnessSurvival.getInstance().getConfig().getBoolean("plot.enable")) {
                    PlayerManager.playPlot(player);
                }
            } else {
                player.sendMessage("§6[荒野求生] §c你的数据无法正常生成，请不要继续游玩，将此截图发送给腐竹 东少 处理! (错误信息: 无法重命名文件)");
                WildrnessSurvival.getInstance().getLogger().warning("§e玩家 " + playerName + " 数据生成时出现问题! (错误信息: 无法重命名文件)");
            }
        }
    }

}

