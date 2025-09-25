package uk.firedev.emfpinata.config;

import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

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

    // GENERAL

    public ComponentSingleMessage getPrefix() {
        return componentMessage(loader, "prefix", "<gray>[EMFPinata] </gray>").toSingleMessage();
    }

    public Replacer getPrefixReplacer() {
        return Replacer.replacer().addReplacement("{prefix}", getPrefix().get());
    }

    // MAIN COMMAND

    public ComponentMessage getReloadedMessage() {
        return componentMessage(loader, "main-command.reloaded", "{prefix}<aqua>Successfully reloaded the plugin.").replace(getPrefixReplacer());
    }

    // PINATA COMMAND

    public ComponentMessage getPinataNotValidMessage() {
        return componentMessage(loader, "pinata-command.not-valid", "{prefix}<red>That piñata does not exist!").replace(getPrefixReplacer());
    }

    public ComponentMessage getPinataSpawnedMessage() {
        return componentMessage(loader, "pinata-command.spawned", "{prefix}<aqua>Successfully spawned a Piñata.").replace(getPrefixReplacer());
    }

}
