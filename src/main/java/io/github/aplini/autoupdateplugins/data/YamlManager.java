package io.github.aplini.autoupdateplugins.data;

import com.google.common.io.ByteStreams;
import lombok.Getter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 *
 * @param <T> 务必传参的时候包装成javaBean, 我也不知道为什么会报错
 */
public abstract class YamlManager<T> implements ManagerInterface {
    private final Yaml yaml;
    private final File file;
    @Getter
    private T instance;

    public YamlManager(File dataFolder) {
        file = new File(dataFolder, getFileName());
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        Type type = parameterizedType.getActualTypeArguments()[0];
        Class<T> clazz;
        if (type instanceof Class<?>) {
            clazz = (Class<T>) type;
        } else if (type instanceof ParameterizedType) {
            clazz = (Class<T>) ((ParameterizedType) type).getRawType();
        } else {
            throw new IllegalArgumentException("Type parameter is neither a Class nor a ParameterizedType");
        }
        yaml = new Yaml(new Constructor(
                clazz,
                new LoaderOptions()
        ), new Representer(new DumperOptions()) {{
            getPropertyUtils().setSkipMissingProperties(true);
        }}, new DumperOptions() {{
            setIndent(2);
            setPrettyFlow(true);
            setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            isAllowUnicode();
        }});

    }

    @Override
    public void load() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(getResourcePath())) {
                ByteStreams.copy(Objects.requireNonNull(is), new FileOutputStream(file));
            }
        }
        try (FileReader reader = new FileReader(file)) {
            this.instance = yaml.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(yaml.dumpAs(instance, Tag.MAP, DumperOptions.FlowStyle.BLOCK));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reload() {
        try (FileReader reader = new FileReader(file)) {
            this.instance = yaml.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract String getFileName();

    public abstract String getResourcePath();
}
