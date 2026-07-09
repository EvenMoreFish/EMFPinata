package org.evenmorefish.emfpinata.pinata;

import com.oheers.fish.api.config.ConfigBase;
import org.bukkit.configuration.InvalidConfigurationException;
import org.evenmorefish.emfpinata.EMFPinata;
import org.evenmorefish.emfpinata.pinata.config.RewardsEntityConfig;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("UnstableApiUsage")
public class Pinata extends ConfigBase {

    private static final Logger logger = EMFPinata.getInstance().getLogger();

    private final PinataFactory factory;
    private final RewardsEntityConfig rewards;

    public Pinata(@NonNull File file) throws InvalidConfigurationException {
        super(file, EMFPinata.getInstance(), false);
        performRequiredConfigChecks();
        this.factory = new PinataFactory(getConfig(), getId());
        this.rewards = new RewardsEntityConfig(getConfig());
    }

    // Current required config: id
    private void performRequiredConfigChecks() throws InvalidConfigurationException {
        if (getConfig().getString("id") == null) {
            logger.warning("Pinata invalid: 'id' missing in " + getFileName());
            throw new InvalidConfigurationException("An ID has not been found in " + getFileName() + ". Please correct this.");
        }
    }

    // Config Getters

    public @NonNull String getId() {
        return Objects.requireNonNull(getConfig().getString("id"));
    }

    public boolean isDisabled() {
        return getConfig().getBoolean("disabled");
    }

    public @NonNull PinataFactory getFactory() {
        return factory;
    }

    public @NonNull RewardsEntityConfig getRewards() {
        return rewards;
    }

}
