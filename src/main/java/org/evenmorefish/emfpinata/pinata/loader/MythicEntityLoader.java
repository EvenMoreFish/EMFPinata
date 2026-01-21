package org.evenmorefish.emfpinata.pinata.loader;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.evenmorefish.emfpinata.api.EntityLoader;
import org.jetbrains.annotations.NotNull;

public class MythicEntityLoader extends EntityLoader {

    private final MythicMob mob;

    public MythicEntityLoader(@NotNull MythicMob mob) {
        super();
        this.mob = mob;
    }

    @Override
    public Entity spawn(@NotNull Location location) {
        return mob.spawn(BukkitAdapter.adapt(location), 1).getEntity().getBukkitEntity();
    }

}
