package uk.firedev.emfpinata.pinata;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.Utils;
import uk.firedev.emfpinata.api.EntityLoader;
import uk.firedev.emfpinata.config.ConfigUtils;
import uk.firedev.emfpinata.pinata.config.AwareEntityConfig;
import uk.firedev.emfpinata.pinata.config.DisplayNameEntityConfig;
import uk.firedev.emfpinata.pinata.config.GlowColorEntityConfig;
import uk.firedev.emfpinata.pinata.config.GlowingEntityConfig;
import uk.firedev.emfpinata.pinata.config.HealthEntityConfig;
import uk.firedev.emfpinata.pinata.config.SilentEntityConfig;
import uk.firedev.emfpinata.pinata.loader.VanillaEntityLoader;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.Consumer;

public class PinataFactory {

    private final @NotNull Section config;
    private boolean rawEntity = false;
    private Consumer<Entity> finalChanges = null;
    private final @NotNull EntityLoader entityLoader;

    private final AwareEntityConfig awareness;
    private final DisplayNameEntityConfig displayName;
    private final GlowColorEntityConfig glowColor;
    private final GlowingEntityConfig glowing;
    private final HealthEntityConfig health;
    private final SilentEntityConfig silent;

    private PinataFactory(@NotNull Section initialSection, @Nullable String configLocation) {
        if (configLocation == null) {
            this.config = initialSection;
        } else {
            this.config = ConfigUtils.getOrCreateSection(initialSection, configLocation);
        }

        this.awareness = new AwareEntityConfig(this.config);
        this.displayName = new DisplayNameEntityConfig(this.config);
        this.glowColor = new GlowColorEntityConfig(this.config);
        this.glowing = new GlowingEntityConfig(this.config);
        this.health = new HealthEntityConfig(this.config);
        this.silent = new SilentEntityConfig(this.config);

        this.entityLoader = fetchEntityLoader();
    }

    public static PinataFactory pinataFactory(@NotNull Section configuration) {
        return new PinataFactory(configuration, null);
    }

    public static PinataFactory pinataFactory(@NotNull Section configuration, @Nullable String configLocation) {
        return new PinataFactory(configuration, configLocation);
    }

    public void spawnEntity(@NotNull Location location) {
        spawnEntity(location, null);
    }

    public void spawnEntity(@NotNull Location location, @Nullable Replacer replacements) {
        Entity entity = entityLoader.spawn(location);

        if (!rawEntity) {
            awareness.apply(entity, replacements);
            displayName.apply(entity, replacements);
            glowColor.apply(entity, replacements);
            glowing.apply(entity, replacements);
            health.apply(entity, replacements);
            silent.apply(entity, replacements);

            if (finalChanges != null) {
                finalChanges.accept(entity);
            }
        }
    }

    public @NotNull AwareEntityConfig getAwareness() {
        return awareness;
    }

    public @NotNull DisplayNameEntityConfig getDisplayName() {
        return displayName;
    }

    public @NotNull GlowColorEntityConfig getGlowColor() {
        return glowColor;
    }

    public @NotNull GlowingEntityConfig getGlowing() {
        return glowing;
    }

    public @NotNull HealthEntityConfig getHealth() {
        return health;
    }

    public @NotNull SilentEntityConfig getSilent() {
        return silent;
    }

    public boolean isRawEntity() {
        return rawEntity;
    }

    public void setRawEntity(boolean rawEntity) {
        this.rawEntity = rawEntity;
    }

    public void setFinalChanges(@Nullable Consumer<Entity> finalChanges) {
        this.finalChanges = finalChanges;
    }

    private EntityLoader fetchEntityLoader() {
        EntityLoader vanilla = getVanillaEntityLoader();
        if (vanilla != null) {
            return vanilla;
        }
        // Add other entity loaders here as needed

        // Default to LLAMA if no loader is found
        return new VanillaEntityLoader(EntityType.LLAMA);
    }

    // Entity Loader Methods

    // Vanilla
    public @Nullable EntityLoader getVanillaEntityLoader() {
        String rawValue = config.getString("entity-type");
        if (rawValue == null || rawValue.isEmpty()) {
            return null;
        }
        EntityType entityType = Utils.getEnumValue(EntityType.class, rawValue);
        if (entityType == null) {
            return null;
        }
        return new VanillaEntityLoader(entityType);
    }

}
