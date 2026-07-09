package org.evenmorefish.emfpinata.pinata;

import com.oheers.fish.api.reward.RewardType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.evenmorefish.emfpinata.EMFPinata;
import org.jspecify.annotations.NonNull;
import uk.firedev.messagelib.replacer.Replacer;

public class PinataRewardType extends RewardType {

    @Override
    public void doReward(@NonNull Player player, @NonNull String key, @NonNull String value, Location hookLocation) {
        Pinata pinata;
        if (value.equalsIgnoreCase("random")) {
            pinata = PinataManager.getInstance().getRandomPinata();
        } else {
            pinata = PinataManager.getInstance().getPinata(value);
        }
        if (pinata == null) {
            EMFPinata.getInstance().getLogger().warning("Tried to give a player a piñata reward, but the piñata " + value + " does not exist.");
            return;
        }
        final Location finalLocation = hookLocation == null ? player.getLocation() : hookLocation;
        final Replacer replacer = Replacer.replacer().addReplacement("{player}", player.name());
        pinata.getFactory().spawnEntity(finalLocation, replacer);
    }

    @NonNull
    @Override
    public String getIdentifier() {
        return "PINATA";
    }

    @NonNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

    @Override
    public @NonNull Plugin getPlugin() {
        return EMFPinata.getInstance();
    }

}
