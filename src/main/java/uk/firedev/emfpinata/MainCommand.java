package uk.firedev.emfpinata;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import uk.firedev.emfpinata.config.MessageConfig;
import uk.firedev.emfpinata.pinatas.PinataCommand;

public class MainCommand extends CommandAPICommand {

    private static MainCommand instance;

    private MainCommand() {
        super("emfpinata");
        setPermission(CommandPermission.fromString("emfpinata.command"));
        withShortDescription("Manage the plugin");
        withFullDescription("Manage the plugin");
        withSubcommands(
                getReloadCommand(),
                PinataCommand.getInstance()
        );
    }

    public static MainCommand getInstance() {
        if (instance == null) {
            instance = new MainCommand();
        }
        return instance;
    }

    private CommandAPICommand getReloadCommand() {
        return new CommandAPICommand("reload")
                .executes((sender, arguments) -> {
                    EMFPinata.getInstance().reload();
                    sender.sendMessage(MessageConfig.getInstance().getReloadedMessage());
                });
    }

}
