package io.github.aplini.autoupdateplugins.data.message;

import io.github.aplini.autoupdateplugins.data.YamlManager;
import org.yaml.snakeyaml.TypeDescription;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MessageManager extends YamlManager<MessageInstance> {
    private final String language;

    public MessageManager(File dataFolder, String language) throws IOException {
        super(dataFolder);
        this.language = language;
        this.load();
    }

    @Override
    public String getFileName() {
        return "message.yml";
    }

    @Override
    public String getResourcePath() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("message/" + language + ".yml");
            if(is != null)
                return "message/" + language + ".yml";
        } catch (Exception ignored){}
        return "message/zh-CN.yml";
    }
}
