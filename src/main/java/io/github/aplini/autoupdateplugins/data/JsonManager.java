package io.github.aplini.autoupdateplugins.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @param <T> Must provide a default constructor
 */
public abstract class JsonManager<T> implements ManagerInterface {
    private final Gson gson;
    private final File file;
    @Getter
    private T instance;
    public JsonManager(File dataFolder) {
        file = new File(dataFolder, getFileName());
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void load() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileReader reader = new FileReader(file)) {
            instance = gson.fromJson(reader, new TypeToken<T>(){}.getType());
            if (instance == null)
                instance = createInstance(getType());
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reload() {
        try (FileReader reader = new FileReader(file)) {
            instance = gson.fromJson(reader, new TypeToken<T>(){}.getType());
            if (instance == null)
                instance = createInstance(getType());
        } catch (IOException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save() {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(instance, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private T createInstance(Type type) throws IllegalAccessException, InstantiationException {
        if (type instanceof Class<?>) {
            return ((Class<T>) type).newInstance();
        } else if (type instanceof ParameterizedType) {
            return (T) new Object();  // 此处为占位，需根据实际需求处理
        } else {
            throw new IllegalArgumentException("Type parameter is neither a Class nor a ParameterizedType");
        }
    }

    private Type getType() {
        ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        return parameterizedType.getActualTypeArguments()[0];
    }

    public abstract String getFileName();

    public abstract String getResourcePath();
}
