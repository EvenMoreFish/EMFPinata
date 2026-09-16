package org.evenmorefish.emfpinata.config;

import org.evenmorefish.emfpinata.EMFPinata;
import org.jspecify.annotations.NonNull;
import uk.firedev.daisylib.config.UpdatableConfig;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.messages.message.ComponentSingleMessage;

public class MessageConfig extends UpdatableConfig {

    private static MessageConfig instance;

    private MessageConfig() {
        super("messages.yml", "messages.yml", EMFPinata.getInstance());
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    // GENERAL

    public ComponentSingleMessage getPrefix() {
        return super.getComponentMessage("prefix", "<gray>[EMFPinata] </gray>").toSingleMessage();
    }

    // MAIN COMMAND

    public ComponentMessage<?, ?> getReloadedMessage() {
        return getComponentMessage("main-command.reloaded", "{prefix}<aqua>Successfully reloaded the plugin.");
    }

    // PINATA COMMAND

    public ComponentMessage<?, ?> getPinataSpawnedMessage() {
        return getComponentMessage("pinata-command.spawned", "{prefix}<aqua>Successfully spawned a Piñata.");
    }

    @Override
    public @NonNull Settings getUpdateSettings() {
        return new Settings();
    }

    @Override
    public @NonNull String versionKey() {
        return "version";
    }

    @Override
    public @NonNull ComponentMessage<?, ?> getComponentMessage(@NonNull String path, @NonNull Object def) {
        return super.getComponentMessage(path, def).replace("{prefix}", getPrefix());
    }

}
