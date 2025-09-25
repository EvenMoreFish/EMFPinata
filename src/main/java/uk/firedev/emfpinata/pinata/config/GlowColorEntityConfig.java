package uk.firedev.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.ScoreboardHelper;
import uk.firedev.emfpinata.api.EntityConfig;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.BiConsumer;

public class GlowColorEntityConfig extends EntityConfig<String> {

    public GlowColorEntityConfig(@NotNull Section section) {
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
