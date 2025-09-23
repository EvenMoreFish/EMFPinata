package uk.firedev.emfpinata.command;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.LiteralArgument;
import org.bukkit.entity.Player;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.config.MessageConfig;
import uk.firedev.emfpinata.pinatas.PinataType;

import java.util.Objects;

public class MainCommand {

    public static CommandTree getCommand() {
        return new CommandTree("emfpinata")
            .withPermission("emfpinata.command")
            .withShortDescription("Manage the plugin")
            .withFullDescription("Manage the plugin")
            .then(getReload())
            .then(getPinata());
    }

    private static Argument<String> getReload() {
        return new LiteralArgument("reload")
            .executes(info -> {
                EMFPinata.getInstance().reload();
                MessageConfig.getInstance().getReloadedMessage().send(info.sender());
            });
    }

    private static Argument<String> getPinata() {
        return new LiteralArgument("pinata")
            .withPermission("emfpinata.command.pinata")
            .then(
                PinataArgument.get().executesPlayer(
                    info -> {
                        PinataType pinataType = Objects.requireNonNull(info.args().getUnchecked("pinata"));
                        Player player = info.sender();
                        MessageConfig.getInstance().getPinataSpawnedMessage().send(player);
                        pinataType.spawn(player.getLocation());
                    }
                )
            );
    }

}
