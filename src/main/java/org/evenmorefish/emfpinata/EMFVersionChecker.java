package org.evenmorefish.emfpinata;

import com.oheers.fish.api.plugin.EMFPlugin;
import org.apache.maven.artifact.versioning.ComparableVersion;

public class EMFVersionChecker {

    private static final String MINIMUM_EMF_VERSION = "2.1.8";

    @SuppressWarnings("UnstableApiUsage")
    private static String getEMFVersion() {
        return EMFPlugin.getInstance().getPluginMeta().getVersion();
    }

    public static void checkMinimumVersionInstalled() throws IllegalStateException {
        ComparableVersion emfVersion = new ComparableVersion(getEMFVersion());
        ComparableVersion minEmfVersion = new ComparableVersion(MINIMUM_EMF_VERSION);
        if (emfVersion.compareTo(minEmfVersion) < 0) {
            throw new IllegalStateException(
                "Installed EMF version " + emfVersion + " is below the required minimum version " + minEmfVersion + "."
            );
        };
    }

}
