package io.github.aplini.autoupdateplugins.data.update;

import io.github.aplini.autoupdateplugins.beans.UpdateItem;
import io.github.aplini.autoupdateplugins.data.YamlManager;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.TypeDescription;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class UpdateDataManager extends YamlManager<UpdateDataManager.Instance> {
    private final String language;

    public UpdateDataManager(File dataFolder, String language) throws IOException {
        super(dataFolder);
        this.language = language;
        this.load();
    }

    @Override
    public String getFileName() {
        return "update.yml";
    }

    @Override
    public String getResourcePath() {
        return "update/" + language + ".yml";
    }
    @Setter
    @Getter
    public static class Instance {
        LinkedList<UpdateItem> list = new LinkedList<>();
    }
}
