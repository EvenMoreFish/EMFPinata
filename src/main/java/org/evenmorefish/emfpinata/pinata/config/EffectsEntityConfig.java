package org.evenmorefish.emfpinata.pinata.config;

import com.oheers.fish.api.config.serializer.PotionEffectSerializer;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

public class EffectsEntityConfig extends EntityConfig<List<PotionEffect>> {

    public EffectsEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public List<PotionEffect> getConfiguredValue() {
        List<String> effects = section.getStringList("effects");
        if (effects == null || effects.isEmpty()) {
            return List.of();
        }
        return effects.stream()
            .map(PotionEffectSerializer.get()::deserialize)
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
