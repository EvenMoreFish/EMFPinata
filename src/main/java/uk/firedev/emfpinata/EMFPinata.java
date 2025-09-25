package uk.firedev.emfpinata;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import uk.firedev.emfpinata.command.MainCommand;
import uk.firedev.emfpinata.config.MessageConfig;
import uk.firedev.emfpinata.pinata.PinataListener;
import uk.firedev.emfpinata.pinata.PinataManager;

public final class EMFPinata extends JavaPlugin {

    private static EMFPinata instance;

    private Metrics metrics = null;

    @Override
    public void onLoad() {
        CommandAPIBukkitConfig config = new CommandAPIBukkitConfig(this)
            .shouldHookPaperReload(true)
            .missingExecutorImplementationMessage("You are not able to use this command!");
        CommandAPI.onLoad(config);
    }

    @Override
    public void onEnable() {
        instance = this;
        CommandAPI.onEnable();

        MessageConfig.getInstance().reload();
        PinataManager.getInstance().load();
        getServer().getPluginManager().registerEvents(new PinataListener(), this);

        registerCommands();

        metrics = loadMetrics();

        new UpdateChecker(this).checkUpdate().thenAccept(success -> {
            if (success) {
                getLogger().info("A new update is available! Download it from https://modrinth.com/plugin/emfpinata");
            }
        });
    }

    @Override
    public void onDisable() {
        PinataManager.getInstance().unload();
        CommandAPI.onDisable();
    }

    public static EMFPinata getInstance() { return instance; }

    public void reload() {
        MessageConfig.getInstance().reload();
        PinataManager.getInstance().reload();
    }

    private void registerCommands() {
        MainCommand.getCommand().register(this);
    }

    private Metrics loadMetrics() {
        return new Metrics(this, 22866);
    }

}
