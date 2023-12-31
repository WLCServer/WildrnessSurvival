package cn.mcwlc.wildrnesssurvival.util;

import cn.mcwlc.wildrnesssurvival.Message;
import cn.mcwlc.wildrnesssurvival.WildrnessSurvival;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;

/**
 * @author DongShaoNB
 */
public class AutoBackup {

    @Getter
    private static BukkitTask autoBackup;

    public static void start() {
        autoBackup = Bukkit.getScheduler().runTaskTimer(WildrnessSurvival.getInstance(), () -> {
            try {
                Bukkit.broadcastMessage(WildrnessSurvival.getPREFIX() + Message.getAutoBackupTip());
                WildrnessSurvival.backupAllResources();
                Bukkit.broadcastMessage(WildrnessSurvival.getPREFIX() + Message.getAutoBackupSuccess());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 0, WildrnessSurvival.getInstance().getConfig().getInt("auto-backup.time") * 20L);
    }

    public static void stop() {
        autoBackup.cancel();
    }

}
