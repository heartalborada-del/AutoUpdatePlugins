package io.github.aplini.autoupdateplugins.commands.subcommands;

import io.github.aplini.autoupdateplugins.AutoUpdatePlugin;
import io.github.aplini.autoupdateplugins.commands.SubCommand;
import io.github.aplini.autoupdateplugins.data.config.ConfigInstance;
import io.github.aplini.autoupdateplugins.utils.Util;
import org.bukkit.command.CommandSender;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;

import static io.github.aplini.autoupdateplugins.utils.Util.getUpdateInstance;

public class reload extends SubCommand {

    public reload() {
        this.name = "reload";
        this.permission = "aup.admin";
        this.usage = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getUsage().getRELOAD();
        this.description = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getDescription().getRELOAD();
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Util.Message(sender, AutoUpdatePlugin.getMessageManager().getInstance().getReloadMessage());
        AutoUpdatePlugin.getConfigManager().reload();
        AutoUpdatePlugin.getMessageManager().reload();
        AutoUpdatePlugin.getCommandManager().reload();
        AutoUpdatePlugin.getUpdateDataManager().reload();
        AutoUpdatePlugin.getTempDataManager().save();
        if(AutoUpdatePlugin.getUpdateInstance().stop()) {
            Proxy proxy = Proxy.NO_PROXY;
            ConfigInstance.Proxy p = AutoUpdatePlugin.getConfigManager().getInstance().getProxy();
            if(p.getType() != Proxy.Type.DIRECT) {
                proxy = new Proxy(p.getType(), new InetSocketAddress(p.getHost(), p.getPort()));
            }
            AutoUpdatePlugin.setUpdateInstance(getUpdateInstance(
                    AutoUpdatePlugin.getConfigManager().getInstance().getStartupDelay(),
                    AutoUpdatePlugin.getConfigManager().getInstance().getStartupCycle(),
                    proxy,
                    AutoUpdatePlugin.getConfigManager().getInstance().getSetRequestProperty(),
                    AutoUpdatePlugin.getUpdateDataManager().getInstance().getList(),
                    AutoUpdatePlugin.getConfigManager().getInstance().getDownloadThreadCount(),
                    AutoUpdatePlugin.getConfigManager().getInstance().isSslVerify()));
        }
        Util.Message(sender, AutoUpdatePlugin.getMessageManager().getInstance().getSuccessMessage());
    }

    @Override
    public List<String> onTab(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void reloadMessage() {
        this.usage = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getUsage().getRELOAD();
        this.description = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getDescription().getRELOAD();
    }
}
