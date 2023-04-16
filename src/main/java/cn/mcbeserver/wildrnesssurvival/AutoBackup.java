package cn.mcbeserver.wildrnesssurvival;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;

/**
 * @author DongShaoNB
 */
public class AutoBackup {

    private static BukkitTask autoBackup;

    public static void start() {
        autoBackup = Bukkit.getScheduler().runTaskTimer(WildrnessSurvival.getInstance(), () -> {
            try {
                WildrnessSurvival.backupAllResources();
                Bukkit.broadcastMessage(WildrnessSurvival.getPrefix() + Message.autoBackupSuccess);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 0, WildrnessSurvival.getInstance().getConfig().getInt("autobackup.time") * 20L);
    }

    public static void stop() {
        autoBackup.cancel();
    }

    public static BukkitTask getAutoBackup() {
        return autoBackup;
    }
}
