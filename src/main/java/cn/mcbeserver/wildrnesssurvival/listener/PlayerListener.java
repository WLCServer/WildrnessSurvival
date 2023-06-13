package cn.mcbeserver.wildrnesssurvival.listener;

import cn.mcbeserver.wildrnesssurvival.WildrnessSurvival;
import cn.mcbeserver.wildrnesssurvival.api.PlayerManager;
import cn.mcbeserver.wildrnesssurvival.em.Attribute;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

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
                player.sendMessage(ChatColor.GOLD + "[荒野求生]" + ChatColor.GREEN + " 欢迎游玩未来城自制玩法-荒野求生，检测到您是第一次进入游戏，数据已生成完毕，可以正常生存!");
                WildrnessSurvival.getInstance().getLogger().info("§2玩家 " + playerName + " 首次进入服务器，数据已生成!");
            } else {
                player.sendMessage(ChatColor.GOLD + "[荒野求生]" + ChatColor.RED + " 你的数据无法正常生成，请不要继续游玩，将此截图发送给腐竹 东少 处理! (错误信息: 无法重命名文件)");
                WildrnessSurvival.getInstance().getLogger().warning("§e玩家 " + playerName + " 数据生成时出现问题! (错误信息: 无法重命名文件)");
            }
        }
    }

    @EventHandler
    public static void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (PlayerManager.getBeltsNumber(player) > 0) {
                if (PlayerManager.getPlayerAttribute(player).get(Attribute.ATTACK) > 0) {
                    event.setDamage(event.getDamage() + PlayerManager.getPlayerAttribute(player).get(Attribute.ATTACK));
                }
            }
        }
    }
}
