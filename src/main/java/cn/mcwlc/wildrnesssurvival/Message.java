package cn.mcwlc.wildrnesssurvival;

import cn.mcwlc.wildrnesssurvival.config.MessageConfig;

/**
 * @author DongShaoNB
 */
public class Message {

    public static String getHelp() {
        return MessageConfig.getConfig().getString("help");
    }

    public static String getReloadSuccess() {
        return MessageConfig.getConfig().getString("reload-success");
    }

    public static String getAppointSkill() {
        return MessageConfig.getConfig().getString("appoint-skill");
    }

    public static String getSkillNotExist() {
        return MessageConfig.getConfig().getString("skill-not-exist");
    }

    public static String getAppointControl() {
        return MessageConfig.getConfig().getString("appoint-control");
    }

    public static String getRemoveExpSuccess() {
        return MessageConfig.getConfig().getString("remove-exp-success");
    }

    public static String getRemoveExpFailMinus() {
        return MessageConfig.getConfig().getString("remove-exp-fail-minus");
    }

    public static String getCheckLevel() {
        return MessageConfig.getConfig().getString("check-level");
    }

    public static String getCheckPlayerLevel() {
        return MessageConfig.getConfig().getString("check-player-level");
    }

    public static String getAppointBeltId() {
        return MessageConfig.getConfig().getString("appoint-beltid");
    }

    public static String getNoPermission() {
        return MessageConfig.getConfig().getString("no-permission");
    }

    public static String getErrorParameters() {
        return MessageConfig.getConfig().getString("error-parameters");
    }

    public static String getUnknownPlayer() {
        return MessageConfig.getConfig().getString("unknown-player");
    }

    public static String getBackupSuccess() {
        return MessageConfig.getConfig().getString("backup-success");
    }

    public static String getAutoBackupTip() {
        return MessageConfig.getConfig().getString("auto-backup-tip");
    }

    public static String getAutoBackupSuccess() {
        return MessageConfig.getConfig().getString("auto-backup-success");
    }

    public static String getUpdateSuccess() {
        return MessageConfig.getConfig().getString("update-success");
    }
}
