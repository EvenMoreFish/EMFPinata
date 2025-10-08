package uk.firedev.emfpinata.api;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

@ApiStatus.Internal
public abstract class EntityConfig<T> {

    private static final Map<String, Function<Section, EntityConfig<?>>> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static Map<String, Function<Section, EntityConfig<?>>> getRegistry() {
        return Map.copyOf(registry);
    }

    public static void unregisterAll() {
        registry.clear();
    }

    public static @Nullable EntityConfig<?> get(@NotNull String identifier, @NotNull Section section) {
        return registry.getOrDefault(identifier, section1 -> null).apply(section);
    }

    public static boolean register(@NotNull String identifier, @NotNull Function<Section, EntityConfig<?>> function, boolean force) {
        if (!force && registry.containsKey(identifier)) {
            return false;
        }
        registry.put(identifier, function);
        return true;
    }

    public static boolean register(@NotNull String identifier, @NotNull Function<Section, EntityConfig<?>> function) {
        return register(identifier, function, false);
    }

    public static boolean unregister(@NotNull String identifier) {
        if (!registry.containsKey(identifier)) {
            return false;
        }
        registry.remove(identifier);
        return true;
    }

    private T def;
    private T override;
    protected final Section section;
    protected boolean enabled = true;

    public EntityConfig(@NotNull Section section) {
        this.section = section;
    }

    public @Nullable T getActualValue() {
        if (override != null) {
            return override;
        }
        T configured = getConfiguredValue();
        if (configured == null) {
            return def;
        }
        return configured;
    }

    /**
     * Applies the actual value to the entity if this config is enabled.
     * @param entity The entity to apply the config to.
     */
    public void apply(@NotNull Entity entity, @Nullable Replacer replacements) {
        if (!enabled) {
            return;
        }
        T value = getActualValue();
        if (value != null) {
            applyToEntity(replacements).accept(entity, value);
        }
    }

    public abstract T getConfiguredValue();

    protected abstract BiConsumer<Entity, T> applyToEntity(@Nullable Replacer replacements);

    public void setDefault(@Nullable T def) {
        this.def = def;
    }

    public void setOverride(@Nullable T override) {
        this.override = override;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
