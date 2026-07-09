package org.evenmorefish.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.BiConsumer;

public class SilentEntityConfig extends EntityConfig<Boolean> {

    public SilentEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public Boolean getConfiguredValue() {
        return section.getBoolean("silent");
    }

    @Override
    protected BiConsumer<Entity, Boolean> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (value == null) {
                return;
            }
            entity.setSilent(value);
        };
    }

}
