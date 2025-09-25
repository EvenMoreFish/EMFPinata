package uk.firedev.emfpinata.pinata;

import com.oheers.fish.api.reward.RewardType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.messagelib.replacer.Replacer;

public class PinataRewardType extends RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, Location hookLocation) {
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

    @NotNull
    @Override
    public String getIdentifier() {
        return "PINATA";
    }

    @NotNull
    @Override
    public String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return EMFPinata.getInstance();
    }

}
