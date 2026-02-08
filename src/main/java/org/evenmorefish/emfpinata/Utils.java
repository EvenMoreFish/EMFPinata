package org.evenmorefish.emfpinata;

import com.oheers.fish.api.Logging;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Utils {

    public static @NotNull <E extends Enum<E>> E getEnumValue(@NotNull Class<E> enumClass, @Nullable String value, @NotNull E def) {
        E enumValue = getEnumValue(enumClass, value);
        if (enumValue == null) {
            return def;
        }
        return enumValue;
    }

    public static @Nullable <E extends Enum<E>> E getEnumValue(@NotNull Class<E> enumClass, @Nullable String value) {
        if (value == null) {
            return null;
        }
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    public static @Nullable Integer getInteger(@NotNull String intString) {
        try {
            return Integer.parseInt(intString);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    public static @Nullable PotionEffect getPotionEffect(@NotNull String effectString) {
        String[] split = effectString.split(",");
        if (split.length != 3) {
            Logging.error("Potion effect string is formatted incorrectly. Use \"potion,amplifier,duration\".");
            return null;
        }
        NamespacedKey key = NamespacedKey.fromString(split[0]);
        if (key == null) {
            Logging.error("Potion effect type " + split[0] + " is not valid.");
            return null;
        }
        PotionEffectType type = Registry.POTION_EFFECT_TYPE.get(key);
        if (type == null) {
            Logging.error("Potion effect type " + key + " is not valid.");
            return null;
        }
        Integer amplifier = getInteger(split[1]);
        if (amplifier == null || amplifier < 1) {
            Logging.error("Potion effect amplifier " + split[1] + " is not valid.");
            return null;
        }
        Integer duration = getInteger(split[2]);
        if (duration == null || duration < 1) {
            Logging.error("Potion effect duration " + split[2] + " is not valid.");
            return null;
        }
        return new PotionEffect(
            type,
            duration * 20,
            amplifier - 1,
            false
        );
    }

}
