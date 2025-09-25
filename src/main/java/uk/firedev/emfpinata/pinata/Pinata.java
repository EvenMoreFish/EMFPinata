package uk.firedev.emfpinata.pinata;

import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.config.ConfigBase;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public class Pinata extends ConfigBase {

    private static final Logger logger = EMFPinata.getInstance().getLogger();

    private final PinataFactory factory;

    public Pinata(@NotNull File file) throws InvalidConfigurationException {
        super(file, EMFPinata.getInstance(), false);
        performRequiredConfigChecks();
        factory = new PinataFactory(getConfig(), getId());
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

}
