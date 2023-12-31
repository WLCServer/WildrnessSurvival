package cn.mcwlc.wildrnesssurvival.listener;

import cn.mcwlc.wildrnesssurvival.em.Attribute;
import cn.mcwlc.wildrnesssurvival.manager.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.HashMap;
import java.util.Random;

/**
 * @author DongShaoNB
 */
public class EntityListener implements Listener {

    @EventHandler
    public static void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (PlayerManager.getBeltsNumber(player) > 0) {
                HashMap<Attribute, Double> playerAttributeList = PlayerManager.getPlayerAttribute(player);
                if (playerAttributeList.get(Attribute.ATTACK) > 0) {
                    if (playerAttributeList.get(Attribute.CRIT) > 0) {
                        Random random = new Random();
                        int probability = random.nextInt(100) + 1;
                        if (playerAttributeList.get(Attribute.CRIT) > probability) {
                            if (playerAttributeList.get(Attribute.CRIT_DAMAGE) > 0) {
                                event.setDamage((event.getDamage() + PlayerManager.getPlayerAttribute(player).get(Attribute.ATTACK)) * (1 + playerAttributeList.get(Attribute.CRIT_DAMAGE)));
                            }
                        }
                    } else {
                        event.setDamage(event.getDamage() + PlayerManager.getPlayerAttribute(player).get(Attribute.ATTACK));
                    }
                }
            }
        } else if (event.getEntity() instanceof Player player) {
            if (PlayerManager.getBeltsNumber(player) > 0) {
                if (PlayerManager.getPlayerAttribute(player).get(Attribute.DEFENSE) > 0) {
                    event.setDamage(event.getDamage() * (1 - PlayerManager.getPlayerAttribute(player).get(Attribute.DEFENSE) * 0.01));
                }
            }
        }
    }

    @EventHandler
    public static void onEntityDeathEvent(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = event.getEntity().getKiller();

        }
    }

}
