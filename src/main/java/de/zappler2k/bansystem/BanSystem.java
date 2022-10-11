package de.zappler2k.bansystem;

import de.zappler2k.bansystem.api.file.ModuleManager;
import de.zappler2k.bansystem.ban.BanManager;
import de.zappler2k.bansystem.bungee.commands.BanManagerCommand;
import de.zappler2k.bansystem.config.BungeeSystemModule;
import de.zappler2k.bansystem.player.PlayerManager;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

@Getter
public final class BanSystem extends Plugin {

    @Getter
    private static BanSystem instance;

    private ModuleManager moduleManager;

    private PlayerManager playerManager;
    @Getter
    private BanManager banManager;
    @Getter
    private static String prefix;
    @Getter
    private static String nopermission;

    private BungeeSystemModule config;

    @Override
    public void onEnable() {
        instance = this;
        this.moduleManager = new ModuleManager();
        this.playerManager = new PlayerManager();
        this.banManager = new BanManager();

        moduleManager.register(new BungeeSystemModule());

        config = (BungeeSystemModule) moduleManager.getModule(BungeeSystemModule.class);

        prefix = config.getPrefix();

        nopermission = config.getNopermission();

        this.getProxy().getPluginManager().registerCommand(this, new BanManagerCommand());
    }

    @Override
    public void onDisable() {

    }
}
