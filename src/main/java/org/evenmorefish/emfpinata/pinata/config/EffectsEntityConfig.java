package org.evenmorefish.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.evenmorefish.emfpinata.Utils;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class EffectsEntityConfig extends EntityConfig<List<PotionEffect>> {

    public EffectsEntityConfig(@NotNull Section section) {
        super(section);
    }

    @Override
    public List<PotionEffect> getConfiguredValue() {
        List<String> effects = section.getStringList("effects");
        if (effects == null || effects.isEmpty()) {
            return List.of();
        }
        return effects.stream()
            .map(Utils::getPotionEffect)
            .filter(Objects::nonNull)
            .toList();
    }

    @Override
    protected BiConsumer<Entity, List<PotionEffect>> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (entity instanceof LivingEntity living) {
                living.addPotionEffects(value);
            }
        };
    }

}
