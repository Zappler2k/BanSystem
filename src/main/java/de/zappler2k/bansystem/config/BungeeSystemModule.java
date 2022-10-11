package de.zappler2k.bansystem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler2k.bansystem.api.file.IModule;
import lombok.Getter;

import java.io.File;
@Getter
public class BungeeSystemModule implements IModule {

    private String prefix;
    private String nopermission;

    public BungeeSystemModule() {
        this.prefix = "§7[§bBungeeSystem§7] ";
        this.nopermission = "§cYou don't have permission to use this command.";
    }

    @Override
    public File getFile() {
        return new File("plugins/BungeeSystem/BungeeSystem.json");
    }

    @Override
    public String getDefaultConfig() {
        return new BungeeSystemModule().toJson();
    }

    @Override
    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    @Override
    public IModule fromJson(String content) {
        return new Gson().fromJson(content, BungeeSystemModule.class);
    }
}
