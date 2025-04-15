package uk.firedev.emfpinata.pinatas.internal;

import com.oheers.fish.libs.boostedyaml.block.implementation.Section;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.pinatas.PinataType;

public class MythicMobsPinata extends PinataType {

    public MythicMobsPinata(@NotNull String identifier, @NotNull String entityTypeString, @NotNull Section section) {
        super(identifier, entityTypeString, section);
    }

    public @Nullable MythicMob getMythicMob() {
        return MythicBukkit.inst().getMobManager().getMythicMob(getEntityTypeString()).orElse(null);
    }

    @Override
    public void spawn(@NotNull Location location) {
        MythicMob mythicMob = getMythicMob();
        Entity entity;
        if (mythicMob == null) {
            EMFPinata.getInstance().getLogger().warning(getEntityTypeString() + " is not a valid MythicMob! Defaulting to LLAMA.");
            entity = location.getWorld().spawnEntity(location, EntityType.LLAMA);
        } else {
            entity = mythicMob.spawn(BukkitAdapter.adapt(location), 1).getEntity().getBukkitEntity();
        }
        applyCommonValues(entity);
    }

}
