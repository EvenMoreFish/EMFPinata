package uk.firedev.emfpinata.pinatas;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.ScoreboardHelper;

import java.util.List;

public interface PinataType {

    String getIdentifier();

    String getDisplayName();

    void setDisplayName(@NotNull String displayName);

    int getHealth();

    void setHealth(int health);

    boolean isGlowing();

    void setGlowing(boolean glowing);

    boolean isAware();

    void setAware(boolean aware);

    /**
     * Hooks into EvenMoreFish's reward system to manage piñata rewards.
     * @return The list of reward strings.
     */
    List<String> getRewards();

    void setRewards(@NotNull List<String> rewards);

    void addReward(@NotNull String reward);

    void addRewards(@NotNull String... rewards);

    void addRewards(@NotNull List<String> rewards);

    boolean isSilent();

    void setSilent(boolean silent);

    String getGlowColor();

    void setGlowColor(@NotNull String glowColor);

    default boolean register() {
        return PinataManager.getInstance().registerPinata(this);
    }

    void spawn(@NotNull Location location);

    default void applyCommonValues(@NotNull Entity entity) {
        if (getDisplayName() != null) {
            entity.setCustomNameVisible(true);
            entity.customName(EMFPinata.getMiniMessage().deserialize(getDisplayName()));
        }
        entity.setGlowing(isGlowing());
        if (getGlowColor() != null && !getGlowColor().isEmpty()) {
            ScoreboardHelper.addToTeam(entity, getGlowColor());
        }
        if (getHealth() > 0 && entity instanceof LivingEntity livingEntity) {
            AttributeInstance attribute = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            if (attribute != null) {
                attribute.setBaseValue(getHealth());
                livingEntity.setHealth(getHealth());
            }
        }
        entity.setSilent(isSilent());
        if (entity instanceof Mob mob) {
            mob.setAware(isAware());
        }
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        pdc.set(PinataManager.getInstance().getPinataKey(), PersistentDataType.STRING, getIdentifier());
    }

}
