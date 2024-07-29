package io.github.aplini.autoupdateplugins;

import io.github.aplini.autoupdateplugins.commands.CommandManager;
import io.github.aplini.autoupdateplugins.data.config.ConfigInstance;
import io.github.aplini.autoupdateplugins.data.config.ConfigManager;
import io.github.aplini.autoupdateplugins.data.message.MessageManager;
import io.github.aplini.autoupdateplugins.data.temp.TempDataManager;
import io.github.aplini.autoupdateplugins.data.update.UpdateDataManager;
import io.github.aplini.autoupdateplugins.update.UpdateInstance;
import io.github.aplini.autoupdateplugins.utils.Util;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;


public class AutoUpdatePlugin extends JavaPlugin {
    @Getter
    private static ConfigManager configManager;
    @Getter
    private static MessageManager messageManager;
    @Getter
    private static CommandManager commandManager;
    @Getter
    private static TempDataManager tempDataManager;
    @Getter
    private static UpdateDataManager updateDataManager;
    @Getter
    @Setter
    private static UpdateInstance updateInstance;
    @Getter
    private static Logger _logger;
    {
        _logger = getLogger();
        try {
            configManager = new ConfigManager(this.getDataFolder());
            messageManager = new MessageManager(this.getDataFolder(), configManager.getInstance().getLanguage());
            updateDataManager = new UpdateDataManager(this.getDataFolder(), configManager.getInstance().getLanguage());
            tempDataManager = new TempDataManager(getDataFolder());
            commandManager = new CommandManager();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Proxy proxy = Proxy.NO_PROXY;
        ConfigInstance.Proxy p = configManager.getInstance().getProxy();
        if(p.getType() != Proxy.Type.DIRECT) {
            proxy = new Proxy(p.getType(), new InetSocketAddress(p.getHost(), p.getPort()));
        }
        updateInstance = Util.getUpdateInstance(
                configManager.getInstance().getStartupDelay(),
                configManager.getInstance().getStartupCycle(),
                proxy,
                configManager.getInstance().getSetRequestProperty(),
                updateDataManager.getInstance().getList(),
                configManager.getInstance().getDownloadThreadCount(),
                configManager.getInstance().isSslVerify());
    }

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 20629);
        metrics.addCustomChart(new Metrics.SingleLineChart("Plugins", () -> ((List<?>) Objects.requireNonNull(getConfig().get("list"))).size()));
        Objects.requireNonNull(Bukkit.getPluginCommand("aup")).setExecutor(commandManager);
    }

    @Override
    public void onDisable() {
        configManager.save();
        tempDataManager.save();
        updateInstance.stop();
    }
}
