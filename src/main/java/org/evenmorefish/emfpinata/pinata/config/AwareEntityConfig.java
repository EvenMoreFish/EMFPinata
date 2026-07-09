package org.evenmorefish.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.BiConsumer;

public class AwareEntityConfig extends EntityConfig<Boolean> {

    public AwareEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public Boolean getConfiguredValue() {
        return section.getBoolean("has-awareness");
    }

    @Override
    protected BiConsumer<Entity, Boolean> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (value == null) {
                return;
            }
            if (entity instanceof Mob mob) {
                mob.setAware(value);
            }
        };
    }
}
