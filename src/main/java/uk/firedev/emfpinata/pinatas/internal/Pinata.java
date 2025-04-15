package uk.firedev.emfpinata.pinatas.internal;

import com.oheers.fish.libs.boostedyaml.block.implementation.Section;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.pinatas.PinataType;

public class Pinata extends PinataType {

    public Pinata(@NotNull String identifier, @NotNull String entityTypeString, @NotNull Section section) {
        super(identifier, entityTypeString, section);
    }

    private EntityType getEntityType() {
        String entityTypeString = getEntityTypeString();
        @NotNull EntityType entityType;
        try {
            entityType = EntityType.valueOf(entityTypeString);
        } catch (IllegalArgumentException ex) {
            EMFPinata.getInstance().getLogger().warning(entityTypeString + " is not a valid entity type. Defaulting to LLAMA.");
            entityType = EntityType.LLAMA;
        }
        return entityType;
    }

    @Override
    public void spawn(@NotNull Location location) {
        Entity entity = location.getWorld().spawnEntity(location, getEntityType());
        applyCommonValues(entity);
    }

}
