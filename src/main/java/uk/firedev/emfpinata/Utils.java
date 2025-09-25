package uk.firedev.emfpinata;

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

}
