package uk.firedev.emfpinata.config;

import uk.firedev.emfpinata.EMFPinata;

public class ExampleConfig extends ConfigBase {

    public ExampleConfig() {
        super("pinatas/examples.yml", "pinatas/examples.yml", EMFPinata.getInstance(), false);
        defaultFile();
    }

    private void defaultFile() {
        getConfig().getFile().delete();
        reload();
    }

}
