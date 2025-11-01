package uk.firedev.emfpinata.command.argument;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.papermc.paper.command.brigadier.MessageComponentSerializer;
import io.papermc.paper.command.brigadier.argument.CustomArgumentType;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.pinata.Pinata;
import uk.firedev.emfpinata.pinata.PinataManager;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnstableApiUsage")
public class PinataArgument implements CustomArgumentType.Converted<Pinata, String> {

    private static final DynamicCommandExceptionType UNKNOWN_PINATA = new DynamicCommandExceptionType(
        obj -> MessageComponentSerializer.message().serialize(Component.text("Unknown Piñata: " + obj))
    );

    @Override
    public Pinata convert(String nativeType) throws CommandSyntaxException {
        Pinata pinata = PinataManager.getInstance().getPinata(nativeType);
        if (pinata == null) {
            throw UNKNOWN_PINATA.create(nativeType);
        }
        return pinata;
    }

    @NotNull
    @Override
    public ArgumentType<String> getNativeType() {
        return StringArgumentType.string();
    }

    @NotNull
    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(@NotNull CommandContext<S> context, @NotNull SuggestionsBuilder builder) {
        PinataManager.getInstance().getPinataMap().values().stream()
            .map(Pinata::getId)
            .filter(name -> name.toLowerCase().startsWith(builder.getRemainingLowerCase()))
            .forEach(builder::suggest);
        return builder.buildFuture();
    }

}
