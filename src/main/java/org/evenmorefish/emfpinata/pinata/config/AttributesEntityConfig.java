package org.evenmorefish.emfpinata.pinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.evenmorefish.emfpinata.EMFPinata;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.BiConsumer;

public class AttributesEntityConfig extends EntityConfig<List<AttributesEntityConfig.AttributeData>> {

    public AttributesEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public List<AttributesEntityConfig.AttributeData> getConfiguredValue() {
        List<String> attributes = section.getStringList("attributes");
        if (attributes == null || attributes.isEmpty()) {
            return List.of();
        }
        return attributes.stream()
            .map(this::parseAttribute)
            .filter(Objects::nonNull)
            .toList();
    }

    @Override
    protected BiConsumer<Entity, List<AttributesEntityConfig.AttributeData>> applyToEntity(@Nullable Replacer replacements) {
        return (ent, value) -> {
            if (!(ent instanceof LivingEntity entity)) {
                return;
            }
            value.forEach(data -> {
                AttributeInstance attribute = entity.getAttribute(data.attribute());
                if (attribute == null) {
                    return;
                }
                attribute.setBaseValue(data.value());
            });
        };
    }

    private @Nullable AttributeData parseAttribute(@NonNull String attributeString) {
        String[] split = attributeString.split(",");
        if (split.length != 2) {
            EMFPinata.getInstance().getLogger().warning("Invalid attribute config: " + attributeString);
            return null;
        }
        Attribute attribute;
        try {
            attribute = Attribute.valueOf(split[0].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            EMFPinata.getInstance().getLogger().warning("Invalid attribute: " + split[0]);
            return null;
        }
        double value;
        try {
            value = Double.parseDouble(split[1]);
        } catch (NumberFormatException exception) {
            EMFPinata.getInstance().getLogger().warning("Invalid attribute value: " + split[1]);
            return null;
        }
        return new AttributeData(attribute, value);
    }

    public record AttributeData(@NonNull Attribute attribute, double value) {}

}
