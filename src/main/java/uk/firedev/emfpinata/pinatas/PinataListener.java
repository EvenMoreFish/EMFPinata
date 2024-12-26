package uk.firedev.emfpinata.pinatas;

import com.oheers.fish.api.reward.Reward;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class PinataListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }
        if (PinataManager.getInstance().isPinata(entity)) {
            event.setDroppedExp(0);
            event.getDrops().clear();
            List<Reward> rewards = PinataManager.getInstance().getRewards(entity);
            rewards.forEach(reward -> reward.rewardPlayer(player, null));
        }
    }

}
