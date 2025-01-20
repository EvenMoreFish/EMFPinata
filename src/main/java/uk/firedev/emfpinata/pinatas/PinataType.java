package uk.firedev.emfpinata.pinatas;

import com.oheers.fish.api.reward.Reward;
import com.oheers.fish.libs.boostedyaml.block.implementation.Section;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.emfpinata.EMFPinata;
import uk.firedev.emfpinata.ScoreboardHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class PinataType {

    private final String identifier;
    private String displayName;
    private int health;
    private boolean glowing;
    private List<String> rewards;
    private boolean silent;
    private String glowColor;
    private final String entityTypeString;
    private boolean hasAwareness;

    public PinataType(@NotNull String identifier, @NotNull String entityTypeString, @NotNull Section section) {
        this.identifier = identifier;
        this.entityTypeString = entityTypeString;
        this.displayName = section.getString("display-name");
        this.glowing = section.getBoolean("glowing", true);
        this.health = section.getInt("health", 120);
        this.silent = section.getBoolean("silent", true);
        this.glowColor = section.getString("glow-color", "aqua").toUpperCase();
        this.rewards = new ArrayList<>(section.getStringList("rewards"));
        this.hasAwareness = section.getBoolean("has-awareness", true);
    }

    protected @NotNull String getEntityTypeString() {
        return entityTypeString;
    }

    public @NotNull String getIdentifier() {
        return identifier;
    }

    public @Nullable String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(@Nullable String displayName) {
        this.displayName = displayName;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public void setGlowing(boolean glowing) {
        this.glowing = glowing;
    }

    public boolean isAware() {
        return hasAwareness;
    }

    public void setAware(boolean aware) {
        this.hasAwareness = aware;
    }

    /**
     * Hooks into EvenMoreFish's reward system to manage piñata rewards.
     * @return The list of reward strings.
     */
    public @NotNull List<Reward> getRewards() {
        return rewards.stream().map(Reward::new).toList();
    }

    public @NotNull List<String> getRewardStrings() {
        return rewards;
    }

    public void setRewards(@NotNull List<String> rewards) {
        this.rewards = new ArrayList<>(rewards);
    }

    public void addReward(@NotNull String reward) {
        this.rewards.add(reward);
    }

    public void addRewards(@NotNull String... rewards) {
        this.rewards.addAll(List.of(rewards));
    }

    public void addRewards(@NotNull List<String> rewards) {
        this.rewards.addAll(rewards);
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public String getGlowColor() {
        return glowColor;
    }

    public void setGlowColor(@NotNull String glowColor) {
        this.glowColor = glowColor;
    }

    public boolean register() {
        return PinataManager.getInstance().registerPinata(this);
    }

    public abstract void spawn(@NotNull Location location);

    public void applyCommonValues(@NotNull Entity entity) {
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
