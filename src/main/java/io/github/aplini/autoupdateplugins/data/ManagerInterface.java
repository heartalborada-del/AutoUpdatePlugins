package io.github.aplini.autoupdateplugins.data;

import java.io.IOException;

public interface ManagerInterface {
    void load() throws IOException;

    void reload();

    void save();
}
