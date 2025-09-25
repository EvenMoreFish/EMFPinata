package uk.firedev.emfpinata.command;

import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.CustomArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import uk.firedev.emfpinata.pinata.Pinata;
import uk.firedev.emfpinata.pinata.PinataManager;

public class PinataArgument {

    public static Argument<Pinata> get() {
        return new CustomArgument<>(new StringArgument("pinata"), info -> {
            Pinata pinata = PinataManager.getInstance().getPinata(info.input());
            if (pinata == null) {
                throw CustomArgument.CustomArgumentException.fromMessageBuilder(
                    new CustomArgument.MessageBuilder("This Piñata does not exist: ").appendArgInput()
                );
            }
            return pinata;
        }).includeSuggestions(
            ArgumentBuilder.getAsyncSuggestions(info ->
                PinataManager.getInstance().getPinataMap().values().stream().map(Pinata::getId).toArray(String[]::new)
            )
        );
    }

}
