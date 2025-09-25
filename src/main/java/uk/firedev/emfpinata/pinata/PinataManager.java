package uk.firedev.emfpinata.pinata;

import com.oheers.fish.api.FileUtil;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.EMFPinata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;

public class PinataManager {

    private static final PinataManager instance = new PinataManager();

    private final Random random = new Random();
    private final Map<String, Pinata> pinataMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private PinataManager() {}

    public static PinataManager getInstance() {
        return instance;
    }

    public void load() {
        loadPinatas();
        logLoadedItems();
    }

    public void reload() {
        unload();
        load();
    }

    public void unload() {
        pinataMap.clear();
    }

    public @Nullable Pinata getPinata(@NotNull String name) {
        return pinataMap.get(name);
    }

    public @Nullable Pinata getRandomPinata() {
        if (pinataMap.isEmpty()) {
            EMFPinata.getInstance().getLogger().warning("There are no loaded Piñatas.");
            return null;
        }
        List<Pinata> values = new ArrayList<>(pinataMap.values());
        int randomIndex = random.nextInt(values.size());
        return values.get(randomIndex);
    }

    // Loading Things

    private void logLoadedItems() {
        EMFPinata.getInstance().getLogger().info("Loaded PinataManager with " + pinataMap.size() + " Pinatas.");
    }

    private void loadPinatas() {
        pinataMap.clear();

        File pinatasFolder = new File(EMFPinata.getInstance().getDataFolder(), "pinatas");
        if (!pinatasFolder.exists()) {
            loadDefaultFiles(pinatasFolder);
        }
        regenExampleFile(pinatasFolder);
        List<File> pinataFiles = FileUtil.getFilesInDirectory(pinatasFolder, true, true);

        if (pinataFiles.isEmpty()) {
            return;
        }

        pinataFiles.forEach(file -> {
            Pinata pinata;
            try {
                pinata = new Pinata(file);
            } catch (InvalidConfigurationException exception) {
                EMFPinata.getInstance().getLogger().warning("Could not load pinata file: " + file.getName() + ". See error below:");
                EMFPinata.getInstance().getLogger().log(Level.WARNING, exception.getMessage(), exception);
                return;
            }
            if (pinata.isDisabled()) {
                return;
            }
            String id = pinata.getId();
            if (pinataMap.containsKey(id)) {
                EMFPinata.getInstance().getLogger().warning("A pinata with the id: " + id + " already exists! Skipping.");
                return;
            }
            pinataMap.put(id, pinata);
        });
    }

    private void regenExampleFile(@NotNull File targetDirectory) {
        new File(targetDirectory, "_example.yml").delete();
        FileUtil.loadFileOrResource(targetDirectory, "_example.yml", "pinatas/_example.yml", EMFPinata.getInstance());
    }

    private void loadDefaultFiles(@NotNull File targetDirectory) {
        EMFPinata.getInstance().getLogger().info("Loading default pinata configs.");
        FileUtil.loadFileOrResource(targetDirectory, "default.yml", "pinatas/default.yml", EMFPinata.getInstance());
    }

}
