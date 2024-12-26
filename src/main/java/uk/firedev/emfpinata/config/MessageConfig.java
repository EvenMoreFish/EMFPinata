package uk.firedev.emfpinata.config;

import com.oheers.fish.config.ConfigBase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;

public class MessageConfig extends ConfigBase {

    private static MessageConfig instance;

    private MessageConfig() {
        super("messages.yml", "messages.yml", EMFPinata.getInstance(), true);
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    private @NotNull Component applyPrefix(@NotNull Component component) {
        TextReplacementConfig trc = TextReplacementConfig.builder().matchLiteral("{prefix}").replacement(getPrefix()).build();
        return component.replaceText(trc);
    }

    // GENERAL

    public Component getPrefix() {
        @NotNull String prefixString = getConfig().getString("messages.prefix", "<gray>[EMFPinata] </gray>");
        return EMFPinata.getMiniMessage().deserialize(prefixString);
    }

    // MAIN COMMAND

    public Component getReloadedMessage() {
        @NotNull String message = getConfig().getString("messages.main-command.reloaded", "{prefix}<aqua>Successfully reloaded the plugin.");
        return applyPrefix(EMFPinata.getMiniMessage().deserialize(message));
    }

    // PINATA COMMAND

    public Component getPinataNotValidMessage() {
        @NotNull String message = getConfig().getString("messages.pinata-command.not-valid", "{prefix}<red>That piñata does not exist!");
        return applyPrefix(EMFPinata.getMiniMessage().deserialize(message));
    }

    public Component getPinataSpawnedMessage() {
        @NotNull String message = getConfig().getString("messages.pinata-command.spawned", "{prefix}<aqua>Successfully spawned a Piñata.");
        return applyPrefix(EMFPinata.getMiniMessage().deserialize(message));
    }

}
