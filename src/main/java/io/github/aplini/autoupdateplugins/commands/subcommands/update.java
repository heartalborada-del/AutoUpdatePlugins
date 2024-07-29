package io.github.aplini.autoupdateplugins.commands.subcommands;

import io.github.aplini.autoupdateplugins.AutoUpdatePlugin;
import io.github.aplini.autoupdateplugins.commands.SubCommand;
import org.bukkit.command.CommandSender;


import java.util.List;

public class update extends SubCommand {
    public update() {
        this.name = "update";
        this.permission = "aup.admin";
        this.usage = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getUsage().getUPDATE();
        this.description = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getDescription().getUPDATE();
    }
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        AutoUpdatePlugin.getUpdateInstance().run(sender);
    }

    @Override
    public List<String> onTab(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public void reloadMessage() {
        this.usage = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getUsage().getUPDATE();
        this.description = AutoUpdatePlugin.getMessageManager().getInstance().getCommands().getDescription().getUPDATE();
    }
}
