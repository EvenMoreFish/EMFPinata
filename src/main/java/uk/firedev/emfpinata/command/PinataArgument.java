package uk.firedev.emfpinata.command;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import uk.firedev.emfpinata.pinatas.PinataManager;
import uk.firedev.emfpinata.pinatas.PinataType;

import java.util.concurrent.CompletableFuture;

public class PinataArgument {

    public static Argument<PinataType> get() {
        return new CustomArgument<>(new StringArgument("pinata"), info -> {
            PinataType pinataType = PinataManager.getInstance().getPinataFromIdentifier(info.input());
            if (pinataType == null) {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                    new CustomArgument.MessageBuilder("This Piñata type does not exist: ").appendArgInput()
                );
            }
            return pinataType;
        }).includeSuggestions(
            ArgumentBuilder.getAsyncSuggestions(info ->
                PinataManager.getInstance().getPinataList().stream().map(PinataType::getIdentifier).toArray(String[]::new)
            )
        );
    }

}
