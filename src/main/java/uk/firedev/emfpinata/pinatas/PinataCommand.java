package uk.firedev.emfpinata.pinatas;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import uk.firedev.emfpinata.config.MessageConfig;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class PinataCommand extends CommandAPICommand {

    private static PinataCommand instance;

    private PinataCommand() {
        super("pinata");
        setPermission(CommandPermission.fromString("emfpinata.command.pinata"));
        withShortDescription("Spawn Piñatas!");
        withFullDescription("Spawn Piñatas!");
        withArguments(getPinataArgument());
        executesPlayer((player, arguments) -> {
            // This should never throw an NPE, as the argument is not optional
            PinataType pinataType = PinataManager.getInstance().getPinataFromIdentifier((String) Objects.requireNonNull(arguments.get("pinata")));
            if (pinataType == null) {
                player.sendMessage(MessageConfig.getInstance().getPinataNotValidMessage());
                return;
            }
            player.sendMessage(MessageConfig.getInstance().getPinataSpawnedMessage());
            pinataType.spawn(player.getLocation());
        });
    }

    public static PinataCommand getInstance() {
        if (instance == null) {
            instance = new PinataCommand();
        }
        return instance;
    }

    private Argument<String> getPinataArgument() {
        return new StringArgument("pinata").includeSuggestions(ArgumentSuggestions.stringsAsync(info ->
                CompletableFuture.supplyAsync(() ->
                        PinataManager.getInstance().getPinataList().stream().map(PinataType::getIdentifier).toArray(String[]::new)
        )));
    }

}
