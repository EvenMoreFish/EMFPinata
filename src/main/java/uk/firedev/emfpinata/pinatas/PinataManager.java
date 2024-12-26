package uk.firedev.emfpinata.pinatas;

import com.oheers.fish.api.reward.Reward;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.config.PinataConfig;

import java.util.*;

public class PinataManager {

    private static PinataManager instance;

    private List<PinataType> pinatas = new ArrayList<>();
    private Map<String, PinataType> pinatasMap = new HashMap<>();
    private int pinatasSize = -1;
    private final Random random = new Random();
    private boolean loaded = false;

    private PinataManager() {}

    public static PinataManager getInstance() {
        if (instance == null) {
            instance = new PinataManager();
        }
        return instance;
    }

    private void load() {
        PinataCommand.getInstance().register();
        Bukkit.getPluginManager().registerEvents(new PinataListener(), EMFPinata.getInstance());
        loaded = true;
    }

    public void reload() {
        if (!loaded) {
            load();
        }
        PinataConfig.getInstance().reload();
        PinataRewardType.getInstance().register();
    }

    /**
     * Gets a Piñata from its identifier.
     * @param identifier The piñata identifier
     * @return The PinataType, or null if it doesn't exist.
     */
    public @Nullable PinataType getPinataFromIdentifier(@Nullable String identifier) {
        if (identifier == null) {
            return null;
        }
        if (pinatasSize == -1 || pinatasSize != getEditablePinataList().size()) {
            pinatasSize = getEditablePinataList().size();
            Map<String, PinataType> map = new HashMap<>();
            for (PinataType type : getEditablePinataList()) {
                map.put(type.getIdentifier(), type);
            }
            pinatasMap = map;
        }
        if (pinatasMap.containsKey(identifier)) {
            return pinatasMap.get(identifier);
        }
        return null;
    }

    /**
     * Registers a Piñata
     * @param pinataType The piñata
     * @return true if it was registered, false if it's already registered.
     */
    public boolean registerPinata(@NotNull PinataType pinataType) {
        if (getPinataFromIdentifier(pinataType.getIdentifier()) != null) {
            EMFPinata.getInstance().getLogger().warning("Tried to register a Piñata that is already registered! (" + pinataType.getIdentifier() + ")");
            return false;
        }
        getEditablePinataList().add(pinataType);
        return true;
    }

    /**
     * Unregisters a Piñata
     * @param identifier The piñata identifier
     * @return true if it was unregistered, false if there's no piñata with that identifier.
     */
    public boolean unregisterPinata(@NotNull String identifier) {
        PinataType pinataType = getPinataFromIdentifier(identifier);
        if (pinataType == null) {
            return false;
        }
        getEditablePinataList().remove(pinataType);
        return true;
    }

    /**
     * Spawns a random Piñata
     * @param location The location to spawn the piñata at.
     */
    public void spawnRandomPinata(@NotNull Location location) {
        if (getEditablePinataList().isEmpty()) {
            EMFPinata.getInstance().getLogger().warning("There are no loaded Piñatas. Cannot spawn something that does not exist!");
            return;
        }
        int randomIndex = random.nextInt(getEditablePinataList().size());
        PinataType pinataType = getEditablePinataList().get(randomIndex);
        pinataType.spawn(location);
    }

    /**
     * Spawns a specific Piñata
     * @param identifier The piñata's identifier.
     * @param location The location to spawn the piñata at.
     */
    public void spawnPinata(@NotNull String identifier, @NotNull Location location) {
        if (getEditablePinataList().isEmpty()) {
            EMFPinata.getInstance().getLogger().warning("There are no loaded Piñatas. Cannot spawn something that does not exist!");
            return;
        }
        PinataType pinataType = getPinataFromIdentifier(identifier);
        if (pinataType == null) {
            EMFPinata.getInstance().getLogger().warning(identifier + " is not a valid PiñataType!");
            return;
        }
        pinataType.spawn(location);
    }

    private List<PinataType> getEditablePinataList() {
        if (pinatas == null) {
            pinatas = new ArrayList<>();
        }
        return pinatas;
    }

    /**
     * Gets the list of loaded Piñatas.
     * This list cannot be modified.
     * @return The unmodifiable list of loaded piñatas.
     */
    public List<PinataType> getPinataList() {
        if (pinatas == null) {
            pinatas = new ArrayList<>();
        }
        return List.copyOf(pinatas);
    }

    /**
     * Unregisters all piñatas.
     */
    public void clearLoadedPinatas() {
        pinatas.clear();
    }

    public NamespacedKey getPinataKey() {
        return new NamespacedKey(EMFPinata.getInstance(), "pinata");
    }

    public boolean isPinata(@NotNull Entity entity) {
        return entity.getPersistentDataContainer().has(getPinataKey());
    }

    public List<Reward> getRewards(@NotNull Entity entity) {
        if (!isPinata(entity)) {
            return List.of();
        }
        PinataType type = getPinataFromEntity(entity);
        if (type == null) {
            return List.of();
        }
        return type.getRewards();
    }

    public @Nullable PinataType getPinataFromEntity(@NotNull Entity entity) {
        String identifier = entity.getPersistentDataContainer().get(getPinataKey(), PersistentDataType.STRING);
        return getPinataFromIdentifier(identifier);
    }

}
