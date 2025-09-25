package uk.firedev.emfpinata.pinata.config;

import com.oheers.fish.api.reward.Reward;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.api.EntityConfig;
import uk.firedev.messagelib.replacer.Replacer;

import java.util.List;
import java.util.function.BiConsumer;

public class RewardsEntityConfig extends EntityConfig<List<Reward>> {

    public RewardsEntityConfig(@NotNull Section section) {
        super(section);
    }

    @Override
    public List<Reward> getConfiguredValue() {
        List<String> strings = section.getStringList("rewards");
        if (strings.isEmpty()) {
            return List.of();
        }
        return strings.stream().map(Reward::new).toList();
    }

    @Override
    protected BiConsumer<Entity, List<Reward>> applyToEntity(@Nullable Replacer replacements) {
        // Rewards are handled elsewhere, so we don't need to do anything here.
        return (entity, value) -> {};
    }

}
