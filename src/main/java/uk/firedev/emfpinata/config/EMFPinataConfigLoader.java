package uk.firedev.emfpinata.config;

import dev.dejvokep.boostedyaml.block.implementation.Section;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.messagelib.config.ConfigLoader;

import java.util.List;

public class EMFPinataConfigLoader implements ConfigLoader<Section> {

    private final Section section;

    public EMFPinataConfigLoader(@NotNull Section section) {
        this.section = section;
    }

    @Nullable
    @Override
    public Object getObject(String s) {
        return section.get(s);
    }

    @Nullable
    @Override
    public String getString(String s) {
        return section.getString(s);
    }

    @Override
    public @NotNull List<String> getStringList(String s) {
        return section.getStringList(s);
    }

    @NotNull
    @Override
    public Section getConfig() {
        return section;
    }

    @Nullable
    @Override
    public ConfigLoader<Section> getSection(@NotNull String s) {
        Section subsection = section.getSection(s);
        return subsection == null ? null : new EMFPinataConfigLoader(subsection);
    }

}
