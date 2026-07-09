package org.evenmorefish.emfpinata.pinata.loader;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.evenmorefish.emfpinata.api.EntityLoader;
import org.jspecify.annotations.NonNull;

public class VanillaEntityLoader extends EntityLoader {

    private final EntityType type;

    public VanillaEntityLoader(@NonNull EntityType type) {
        super();
        this.type = type;
    }

    @Override
    public Entity spawn(@NonNull Location location) {
        return location.getWorld().spawnEntity(location, type);
    }

}
