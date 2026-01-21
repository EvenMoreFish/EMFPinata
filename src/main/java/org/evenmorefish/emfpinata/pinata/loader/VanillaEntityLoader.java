package org.evenmorefish.emfpinata.pinata.loader;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.evenmorefish.emfpinata.api.EntityLoader;
import org.jetbrains.annotations.NotNull;

public class VanillaEntityLoader extends EntityLoader {

    private final EntityType type;

    public VanillaEntityLoader(@NotNull EntityType type) {
        super();
        this.type = type;
    }

    @Override
    public Entity spawn(@NotNull Location location) {
        return location.getWorld().spawnEntity(location, type);
    }

}
