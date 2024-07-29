package io.github.aplini.autoupdateplugins.data.update;

import io.github.aplini.autoupdateplugins.beans.UpdateItem;
import io.github.aplini.autoupdateplugins.data.YamlManager;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.TypeDescription;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("message/" + language + ".yml");
            if(is != null)
                return "update/" + language + ".yml";
        } catch (Exception ignored){}
        return "update/zh-CN.yml";
    }
    @Setter
    @Getter
    public static class Instance {
        LinkedList<UpdateItem> list = new LinkedList<>();
    }
}
