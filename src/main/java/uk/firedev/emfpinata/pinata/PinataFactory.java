package uk.firedev.emfpinata.pinata;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.Keys;
import uk.firedev.emfpinata.Utils;
import uk.firedev.emfpinata.api.EntityConfig;
import uk.firedev.emfpinata.api.EntityLoader;
import uk.firedev.emfpinata.config.ConfigBase;
import uk.firedev.emfpinata.pinata.loader.MythicEntityLoader;
import uk.firedev.emfpinata.pinata.loader.VanillaEntityLoader;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.Consumer;

public class PinataFactory extends ConfigBase {

    private final @NotNull Section config;
    private Consumer<Entity> finalChanges = null;
    private final @NotNull EntityLoader entityLoader;
    private final @NotNull String pinataId;

    protected PinataFactory(@NotNull Section section, @NotNull String pinataId) {
        this.config = section;
        this.pinataId = pinataId;
        this.entityLoader = fetchEntityLoader();
    }

    public void spawnEntity(@NotNull Location location) {
        spawnEntity(location, null);
    }

    public void spawnEntity(@NotNull Location location, @Nullable Replacer replacements) {
        Entity entity = entityLoader.spawn(location);

        // Step 1: Apply configs
        EntityConfig.getRegistry().values().stream()
            .map(function -> function.apply(config))
            .forEach(entityConfig -> entityConfig.apply(entity, replacements));

        // Step 2: Apply any final changes provided by external plugins
        if (finalChanges != null) {
            finalChanges.accept(entity);
        }

        // Step 3: Set the piñata ID in the entity's persistent data container
        entity.getPersistentDataContainer().set(Keys.PINATA_KEY, PersistentDataType.STRING, pinataId);
    }

    public void setFinalChanges(@Nullable Consumer<Entity> finalChanges) {
        this.finalChanges = finalChanges;
    }

    private EntityLoader fetchEntityLoader() {
        String rawValue = config.getString("entity-type");
        if (rawValue == null || rawValue.isEmpty()) {
            return new VanillaEntityLoader(EntityType.LLAMA);
        }

        VanillaEntityLoader vanilla = getVanillaEntityLoader(rawValue);
        if (vanilla != null) {
            return vanilla;
        }

        MythicEntityLoader mythic = getMythicEntityLoader(rawValue);
        if (mythic != null) {
            return mythic;
        }

        // Default to LLAMA if no loader is found
        return new VanillaEntityLoader(EntityType.LLAMA);
    }

    // Entity Loader Methods

    // Vanilla
    public @Nullable VanillaEntityLoader getVanillaEntityLoader(@NotNull String rawValue) {
        EntityType entityType = Utils.getEnumValue(EntityType.class, rawValue);
        if (entityType == null) {
            return null;
        }
        return new VanillaEntityLoader(entityType);
    }

    // MythicMobs
    public @Nullable MythicEntityLoader getMythicEntityLoader(@NotNull String rawValue) {
        String mobName;
        if (rawValue.startsWith("mythic:")) {
            mobName = rawValue.substring("mythic:".length());
        } else if (rawValue.startsWith("mythicmob:")) {
            mobName = rawValue.substring("mythicmob:".length());
        } else {
            return null;
        }
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobName).orElse(null);
        if (mob == null) {
            return null;
        }
        return new MythicEntityLoader(mob);
    }

}
