package uk.firedev.emfpinata;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.emfpinata.command.MainCommand;
import uk.firedev.emfpinata.config.MessageConfig;
import uk.firedev.emfpinata.pinata.PinataListener;
import uk.firedev.emfpinata.pinata.PinataManager;

@SuppressWarnings("UnstableApiUsage")
public final class EMFPinata extends JavaPlugin {

    private static EMFPinata instance;

    private Metrics metrics = null;

    @Override
    public void onLoad() {
        registerCommands();
    }

    @Override
    public void onEnable() {
        instance = this;

        MessageConfig.getInstance().reload();
        PinataManager.getInstance().load();
        getServer().getPluginManager().registerEvents(new PinataListener(), this);

        registerCommands();

        metrics = loadMetrics();

        new UpdateChecker(this).checkUpdate().thenAccept(success -> {
            if (success) {
                getLogger().warning("A new update is available! Download it from https://modrinth.com/plugin/emfpinata");
            }
        });
    }

    @Override
    public void onDisable() {
        PinataManager.getInstance().unload();
    }

    public static EMFPinata getInstance() { return instance; }

    public void reload() {
        MessageConfig.getInstance().reload();
        PinataManager.getInstance().reload();
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(new MainCommand().get());
        });
    }

    private Metrics loadMetrics() {
        return new Metrics(this, 22866);
    }

}
