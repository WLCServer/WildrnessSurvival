package cn.mcbeserver.wildrnesssurvival;

import cn.mcbeserver.wildrnesssurvival.config.MessageConfig;

/**
 * @author DongShaoNB
 */
public class Message {
    public static String help = MessageConfig.getConfig().getString("help");
    public static String reloadSuccess = MessageConfig.getConfig().getString("reload-success");
    public static String appointSkill = MessageConfig.getConfig().getString("appoint-skill");
    public static String skillNotExist = MessageConfig.getConfig().getString("skill-not-exist");
    public static String appointControl = MessageConfig.getConfig().getString("appoint-control");
    public static String removeExpSuccess = MessageConfig.getConfig().getString("remove-exp-success");
    public static String removeExpFailMinus = MessageConfig.getConfig().getString("remove-exp-fail-minus");
    public static String checkLevel = MessageConfig.getConfig().getString("check-level");
    public static String checkPlayerLevel = MessageConfig.getConfig().getString("check-player-level");
    public static String appointBeltId = MessageConfig.getConfig().getString("appoint-beltid");
    public static String noPermission = MessageConfig.getConfig().getString("no-permission");
    public static String errorParameters = MessageConfig.getConfig().getString("error-parameters");
    public static String unknownPlayer = MessageConfig.getConfig().getString("unknown-player");
    public static String backupSuccess = MessageConfig.getConfig().getString("backup-success");
    public static String autoBackupSuccess = MessageConfig.getConfig().getString("autobackup-success");
    public static String updateSuccess = MessageConfig.getConfig().getString("update-success");

    public static void reload() {
        help = MessageConfig.getConfig().getString("help");
        reloadSuccess = MessageConfig.getConfig().getString("reload-success");
        appointSkill = MessageConfig.getConfig().getString("appoint-skill");
        skillNotExist = MessageConfig.getConfig().getString("skill-not-exist");
        appointControl = MessageConfig.getConfig().getString("appoint-control");
        removeExpSuccess = MessageConfig.getConfig().getString("remove-exp-success");
        removeExpFailMinus = MessageConfig.getConfig().getString("remove-exp-fail-minus");
        checkLevel = MessageConfig.getConfig().getString("check-level");
        checkPlayerLevel = MessageConfig.getConfig().getString("check-player-level");
        appointBeltId = MessageConfig.getConfig().getString("appoint-beltid");
        noPermission = MessageConfig.getConfig().getString("no-permission");
        errorParameters = MessageConfig.getConfig().getString("error-parameters");
        unknownPlayer = MessageConfig.getConfig().getString("unknown-player");
        backupSuccess = MessageConfig.getConfig().getString("backup-success");
        autoBackupSuccess = MessageConfig.getConfig().getString("autobackup-success");
        updateSuccess = MessageConfig.getConfig().getString("update-success");
    }

}
