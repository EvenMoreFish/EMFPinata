package uk.firedev.emfpinata.command;

import dev.jorel.commandapi.SuggestionInfo;
import dev.jorel.commandapi.arguments.Argument;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ArgumentBuilder {

    public static Argument<String> getAsyncStringsArgument(@NotNull String name, @NotNull Function<SuggestionInfo<CommandSender>, String[]> function) {
        return new StringArgument(name).includeSuggestions(getAsyncSuggestions(function));
    }

    public static ArgumentSuggestions<CommandSender> getAsyncSuggestions(@NotNull Function<SuggestionInfo<CommandSender>, String[]> function) {
        return ArgumentSuggestions.stringsAsync(
                info -> CompletableFuture.supplyAsync(() -> function.apply(info))
        );
    }

    public static <T> @Nullable T resolveArgument(@Nullable Object object, @NotNull Class<T> clazz) {
        if (clazz.isInstance(object)) {
            return clazz.cast(object);
        }
        return null;
    }

    public static <T> @NotNull T resolveArgumentOrThrow(@Nullable Object object, @NotNull Class<T> clazz) {
        T resolved = resolveArgument(object, clazz);
        if (resolved == null) {
            throw new IllegalArgumentException("Argument is invalid or null.");
        }
        return resolved;
    }

}
