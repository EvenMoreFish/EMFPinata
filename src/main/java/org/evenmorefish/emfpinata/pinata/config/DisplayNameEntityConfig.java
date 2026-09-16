package org.evenmorefish.emfpinata.pinata.config;

import org.evenmorefish.fish.libs.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.evenmorefish.emfpinata.api.EntityConfig;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import uk.firedev.daisylib.messages.message.ComponentMessage;
import uk.firedev.daisylib.messages.message.ComponentSingleMessage;
import uk.firedev.daisylib.messages.replacer.Replacer;

import java.util.function.BiConsumer;

public class DisplayNameEntityConfig extends EntityConfig<String> {

    public DisplayNameEntityConfig(@NonNull Section section) {
        super(section);
    }

    @Override
    public String getConfiguredValue() {
        return section.getString("display-name");
    }

    @Override
    protected BiConsumer<Entity, String> applyToEntity(@Nullable Replacer replacements) {
        return (entity, value) -> {
            if (value == null || value.isEmpty()) {
                return;
            }
            ComponentSingleMessage display = ComponentMessage.componentMessage(value).replace(replacements);
            entity.customName(display.get());
            entity.setCustomNameVisible(true);
        };
    }

}
