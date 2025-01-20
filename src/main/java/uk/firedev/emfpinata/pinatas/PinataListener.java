package uk.firedev.emfpinata.pinatas;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class PinataListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        PinataType pinata = PinataManager.getInstance().getPinataFromEntity(event.getEntity());
        if (pinata == null) {
            return;
        }
        event.setDroppedExp(0);
        event.getDrops().clear();
        Player player = event.getEntity().getKiller();
        if (player == null) {
            return;
        }
        pinata.getRewards().forEach(reward -> reward.rewardPlayer(player, null));
    }

}
