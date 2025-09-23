package uk.firedev.emfpinata.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.messagelib.config.PaperConfigLoader;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;

import static uk.firedev.messagelib.message.ComponentMessage.componentMessage;

public class MessageConfig extends ConfigBase {

    private static MessageConfig instance;

    private EMFPinataConfigLoader loader;

    private MessageConfig() {
        super("messages.yml", "messages.yml", EMFPinata.getInstance(), true);
        loader = new EMFPinataConfigLoader(getConfig());
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    private @NotNull ComponentMessage applyPrefix(@NotNull ComponentMessage component) {
        return component.replace("{prefix}", getPrefix());
    }

    // GENERAL

    public ComponentSingleMessage getPrefix() {
        return componentMessage(loader, "messages.prefix", "<gray>[EMFPinata] </gray>").toSingleMessage();
    }

    // MAIN COMMAND

    public ComponentMessage getReloadedMessage() {
        return componentMessage(loader, "messages.main-command.reloaded", "{prefix}<aqua>Successfully reloaded the plugin.");
    }

    // PINATA COMMAND

    public ComponentMessage getPinataNotValidMessage() {
        return componentMessage(loader, "messages.pinata-command.not-valid", "{prefix}<red>That piñata does not exist!");
    }

    public ComponentMessage getPinataSpawnedMessage() {
        return componentMessage(loader, "messages.pinata-command.spawned", "{prefix}<aqua>Successfully spawned a Piñata.");
    }

}
