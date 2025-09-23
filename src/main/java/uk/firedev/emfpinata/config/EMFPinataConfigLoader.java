package uk.firedev.emfpinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.config.ConfigLoader;

import java.util.List;

public class EMFPinataConfigLoader implements ConfigLoader<Section> {

    private final Section config;

    public EMFPinataConfigLoader(@NotNull Section section) {
        this.config = section;
    }

    @Nullable
    @Override
    public Object getObject(String s) {
        return config.get(s);
    }

    @Nullable
    @Override
    public String getString(String s) {
        return config.getString(s);
    }

    @Override
    public @NotNull List<String> getStringList(String s) {
        return config.getStringList(s);
    }

    @NotNull
    @Override
    public Section getConfig() {
        return config;
    }

    @Nullable
    @Override
    public EMFPinataConfigLoader getSection(@NotNull String s) {
        Section section = config.getSection(s);
        return section == null ? null : new EMFPinataConfigLoader(section);
    }

}
