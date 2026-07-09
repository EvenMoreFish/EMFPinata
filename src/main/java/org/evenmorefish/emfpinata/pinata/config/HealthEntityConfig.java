package org.evenmorefish.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.evenmorefish.emfpinata.EMFPinata;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.function.BiConsumer;

public class HealthEntityConfig extends EntityConfig<Integer> {

    public HealthEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public Integer getConfiguredValue() {
        return section.getInt("health");
    }

    @Override
    protected BiConsumer<Entity, Integer> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (value == null) {
                return;
            }
            // Health cannot be less than 1.
            value = Math.max(value, 1);
            if (!(entity instanceof LivingEntity livingEntity)) {
                return;
            }
            AttributeInstance attribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute != null) {
                try {
                    attribute.setBaseValue(value);
                    livingEntity.setHealth(value);
                } catch (IllegalArgumentException exception) {
                    EMFPinata.getInstance().getLogger().warning("Invalid health value: " + value + ". Defaulting to 1024.");
                    attribute.setBaseValue(1024);
                    livingEntity.setHealth(1024);
                }
            }
        };
    }

}
