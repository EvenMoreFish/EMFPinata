package org.evenmorefish.emfpinata.pinata;

import com.oheers.fish.api.config.ConfigBase;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataType;
import org.evenmorefish.emfpinata.Keys;
import org.evenmorefish.emfpinata.Utils;
import org.evenmorefish.emfpinata.api.EntityLoader;
import org.evenmorefish.emfpinata.pinata.config.AwareEntityConfig;
import org.evenmorefish.emfpinata.pinata.config.DisplayNameEntityConfig;
import org.evenmorefish.emfpinata.pinata.config.EffectsEntityConfig;
import org.evenmorefish.emfpinata.pinata.config.GlowColorEntityConfig;
import org.evenmorefish.emfpinata.pinata.config.GlowingEntityConfig;
import org.evenmorefish.emfpinata.pinata.config.HealthEntityConfig;
import org.evenmorefish.emfpinata.pinata.config.SilentEntityConfig;
import org.evenmorefish.emfpinata.pinata.loader.MythicEntityLoader;
import org.evenmorefish.emfpinata.pinata.loader.VanillaEntityLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.Consumer;

@SuppressWarnings("UnstableApiUsage")
public class PinataFactory extends ConfigBase {

    private final @NotNull Section config;
    private Consumer<Entity> finalChanges = null;
    private final @NotNull EntityLoader entityLoader;
    private final @NotNull String pinataId;

    private final AwareEntityConfig awareness;
    private final DisplayNameEntityConfig displayName;
    private final EffectsEntityConfig effects;
    private final GlowColorEntityConfig glowColor;
    private final GlowingEntityConfig glowing;
    private final HealthEntityConfig health;
    private final SilentEntityConfig silent;

    protected PinataFactory(@NotNull Section section, @NotNull String pinataId) {
        this.config = section;
        this.pinataId = pinataId;

        this.awareness = new AwareEntityConfig(this.config);
        this.displayName = new DisplayNameEntityConfig(this.config);
        this.effects = new EffectsEntityConfig(this.config);
        this.glowColor = new GlowColorEntityConfig(this.config);
        this.glowing = new GlowingEntityConfig(this.config);
        this.health = new HealthEntityConfig(this.config);
        this.silent = new SilentEntityConfig(this.config);

        this.entityLoader = fetchEntityLoader();
    }

    public void spawnEntity(@NotNull Location location) {
        spawnEntity(location, null);
    }

    public void spawnEntity(@NotNull Location location, @Nullable Replacer replacements) {
        Entity entity = entityLoader.spawn(location);

        // Step 1: Apply configs
        awareness.apply(entity, replacements);
        displayName.apply(entity, replacements);
        effects.apply(entity, replacements);
        glowColor.apply(entity, replacements);
        glowing.apply(entity, replacements);
        health.apply(entity, replacements);
        silent.apply(entity, replacements);

        // Step 2: Apply any final changes provided by external plugins
        if (finalChanges != null) {
            finalChanges.accept(entity);
        }

        // Step 3: Set the piñata ID in the entity's persistent data container
        entity.getPersistentDataContainer().set(Keys.PINATA_KEY, PersistentDataType.STRING, pinataId);
    }

    public @NotNull AwareEntityConfig getAwareness() {
        return awareness;
    }

    public @NotNull DisplayNameEntityConfig getDisplayName() {
        return displayName;
    }

    public @NotNull EffectsEntityConfig getEffects() {
        return effects;
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
