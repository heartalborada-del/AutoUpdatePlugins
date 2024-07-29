package io.github.aplini.autoupdateplugins.data.config;

import io.github.aplini.autoupdateplugins.data.YamlManager;
import org.yaml.snakeyaml.TypeDescription;

import java.io.File;
import java.io.IOException;

public class ConfigManager extends YamlManager<ConfigInstance> {
    public ConfigManager(File dataFolder) throws IOException {
        super(dataFolder);
        load();
    }

    @Override
    public String getFileName() {
        return "config.yml";
    }

    @Override
    public String getResourcePath() {
        return "config.yml";
    }
}
