package uk.firedev.emfpinata.pinata;

import com.oheers.fish.api.reward.Reward;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class PinataListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Pinata pinata = PinataManager.getInstance().getPinata(event.getEntity());
        if (pinata == null) {
            return;
        }
        event.setDroppedExp(0);
        event.getDrops().clear();
        Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }
        List<Reward> rewards = pinata.getRewards().getActualValue();
        if (rewards == null || rewards.isEmpty()) {
            return;
        }
        rewards.forEach(reward -> reward.rewardPlayer(player, event.getEntity().getLocation()));
    }

}
