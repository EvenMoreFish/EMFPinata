package org.evenmorefish.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.evenmorefish.emfpinata.ScoreboardHelper;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.BiConsumer;

public class GlowColorEntityConfig extends EntityConfig<String> {

    public GlowColorEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public String getConfiguredValue() {
        return section.getString("glow-color");
    }

    @Override
    protected BiConsumer<Entity, String> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (value == null || value.isEmpty()) {
                return;
            }
            ScoreboardHelper.addToTeam(entity, value);
        };
    }
}
