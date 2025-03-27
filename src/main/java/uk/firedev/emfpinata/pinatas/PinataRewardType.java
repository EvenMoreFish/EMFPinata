package uk.firedev.emfpinata.pinatas;

import com.oheers.fish.api.reward.RewardType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;

public class PinataRewardType extends RewardType {

    private static PinataRewardType instance;

    private PinataRewardType() {}

    public static PinataRewardType getInstance() {
        if (instance == null) {
            instance = new PinataRewardType();
        }
        return instance;
    }

    @Override
    public void doReward(@NotNull Player player, @NotNull String key, @NotNull String value, Location hookLocation) {
        if (hookLocation == null) {
            return;
        }
        if (value.equalsIgnoreCase("random")) {
            PinataManager.getInstance().spawnRandomPinata(hookLocation);
            return;
        }
        PinataType type = PinataManager.getInstance().getPinataFromIdentifier(value);
        if (type == null) {
            EMFPinata.getInstance().getLogger().warning("Invalid Pinata specified for RewardType " + getIdentifier() + ": " + value);
            return;
        }
        type.spawn(hookLocation);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "PINATA";
    }

    @Override
    public @NotNull String getAuthor() {
        return "FireML";
    }

    @Override
    public @NotNull JavaPlugin getPlugin() {
        return EMFPinata.getInstance();
    }

}
