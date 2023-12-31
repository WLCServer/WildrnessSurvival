package cn.mcwlc.wildrnesssurvival.listener;

import cn.mcwlc.wildrnesssurvival.em.Skill;
import cn.mcwlc.wildrnesssurvival.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author DongShaoNB
 */
public class BlockListener implements Listener {

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        PlayerManager.addExp(player, Skill.COLLECT, 1);
    }

}
