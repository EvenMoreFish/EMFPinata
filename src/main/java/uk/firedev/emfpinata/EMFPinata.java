package uk.firedev.emfpinata;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.emfpinata.config.ExampleConfig;
import uk.firedev.emfpinata.config.MessageConfig;
import uk.firedev.emfpinata.pinatas.PinataManager;

public final class EMFPinata extends JavaPlugin {

    private static EMFPinata instance;
    private static MiniMessage miniMessage;

    private Metrics metrics = null;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(
                new CommandAPIBukkitConfig(this)
                        .shouldHookPaperReload(true)
                        .usePluginNamespace()
        );
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();
        reload();
        registerCommands();

        metrics = loadMetrics();

        // Load the example config
        new ExampleConfig();
    }

    public static EMFPinata getInstance() { return instance; }

    public void reload() {
        MessageConfig.getInstance().reload();
        PinataManager.getInstance().reload();
    }

    private void registerCommands() {
        MainCommand.getInstance().register();
    }

    private Metrics loadMetrics() {
        return new Metrics(this, 22866);
    }

    public static MiniMessage getMiniMessage() {
        if (miniMessage == null) {
            miniMessage = MiniMessage.miniMessage();
        }
        return miniMessage;
    }

}
