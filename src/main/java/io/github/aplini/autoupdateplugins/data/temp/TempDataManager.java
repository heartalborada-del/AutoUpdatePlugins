package io.github.aplini.autoupdateplugins.data.temp;

import io.github.aplini.autoupdateplugins.beans.TempData;
import io.github.aplini.autoupdateplugins.data.JsonManager;
import io.github.aplini.autoupdateplugins.data.YamlManager;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TempDataManager extends JsonManager<Map<String, TempData>> {
    public TempDataManager(File dataFolder) throws IOException {
        super(dataFolder);
        load();
    }

    @Override
    public String getFileName() {
        return "temp.json";
    }

    @Override
    public String getResourcePath() {
        return "temp.json";
    }
}
