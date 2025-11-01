package uk.firedev.emfpinata.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.command.argument.PinataArgument;
import uk.firedev.emfpinata.config.MessageConfig;
import uk.firedev.emfpinata.pinata.Pinata;
import uk.firedev.messagelib.replacer.Replacer;

@SuppressWarnings("UnstableApiUsage")
public class MainCommand {

    public LiteralCommandNode<CommandSourceStack> get() {
        return Commands.literal("emfpinata")
            .requires(stack -> stack.getSender().hasPermission("emfpinata.command"))
            .then(reload())
            .then(pinata())
            .build();
    }

    private ArgumentBuilder<CommandSourceStack, ?> reload() {
        return Commands.literal("reload")
            .executes(context -> {
                EMFPinata.getInstance().reload();
                MessageConfig.getInstance().getReloadedMessage().send(context.getSource().getSender());
                return 1;
            });
    }

    private ArgumentBuilder<CommandSourceStack, ?> pinata() {
        return Commands.literal("pinata")
            .then(
                Commands.argument("pinata", new PinataArgument())
                    .executes(context -> {
                        Player player = CommandUtils.requirePlayer(context.getSource());
                        if (player == null) {
                            return 1;
                        }
                        Pinata pinata = context.getArgument("pinata", Pinata.class);
                        Replacer replacer = Replacer.replacer().addReplacement("{player}", player.name());

                        MessageConfig.getInstance().getPinataSpawnedMessage().send(player);
                        pinata.getFactory().spawnEntity(player.getLocation(), replacer);
                        return 1;
                    })
            );
    }

}
