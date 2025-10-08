package uk.firedev.emfpinata.pinata;

import com.oheers.fish.api.reward.Reward;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.config.ConfigBase;
import uk.firedev.emfpinata.pinata.config.RewardsEntityConfig;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class Pinata extends ConfigBase {

    private static final Logger logger = EMFPinata.getInstance().getLogger();

    private final PinataFactory factory;
    private final List<Reward> rewards;

    public Pinata(@NotNull File file) throws InvalidConfigurationException {
        super(file, EMFPinata.getInstance(), false);
        performRequiredConfigChecks();
        this.factory = new PinataFactory(getConfig(), getId());
        this.rewards = getConfig().getStringList("rewards").stream()
            .map(Reward::new)
            // Filter out Piñata rewards to avoid infinite loops.
            .filter(reward -> !(reward.getRewardType() instanceof PinataRewardType))
            .toList();;
    }

    // Current required config: id
    private void performRequiredConfigChecks() throws InvalidConfigurationException {
        if (getConfig().getString("id") == null) {
            logger.warning("Pinata invalid: 'id' missing in " + getFileName());
            throw new InvalidConfigurationException("An ID has not been found in " + getFileName() + ". Please correct this.");
        }
    }

    // Config Getters

    public @NotNull String getId() {
        return Objects.requireNonNull(getConfig().getString("id"));
    }

    public boolean isDisabled() {
        return getConfig().getBoolean("disabled");
    }

    public @NotNull PinataFactory getFactory() {
        return factory;
    }

    public @NotNull List<Reward> getRewards() {
        return rewards;
    }

}
