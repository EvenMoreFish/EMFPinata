package uk.firedev.emfpinata.api;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public abstract class EntityLoader {

    public abstract Entity spawn(@NotNull Location location);

}
