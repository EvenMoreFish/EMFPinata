package org.evenmorefish.emfpinata.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

@ApiStatus.Internal
public abstract class EntityLoader {

    public abstract Entity spawn(@NonNull Location location);

}
