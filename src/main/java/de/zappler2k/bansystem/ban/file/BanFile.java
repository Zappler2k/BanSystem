package de.zappler2k.bansystem.ban.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler2k.bansystem.BanSystem;
import de.zappler2k.bansystem.api.file.IModule;
import de.zappler2k.bansystem.ban.file.impl.Ban;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BanFile implements IModule {

    private List<Ban> bans;

    public BanFile() {
        this.bans = new ArrayList<>();
    }

    @Override
    public File getFile() {
        return new File("plugins/BungeeSystem/configs/Bans.json");
    }

    @Override
    public String getDefaultConfig() {
        return new BanFile().toJson();
    }

    @Override
    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    @Override
    public IModule fromJson(String content) {
        return new Gson().fromJson(content, BanFile.class);
    }

    public void addBan(Ban ban) {
        this.bans.add(ban);
        BanSystem.getInstance().getModuleManager().insert(this, this.toJson());
    }

    public void removeBan(String id) {
        this.bans.removeIf(ban -> ban.getId().equals(id));
        BanSystem.getInstance().getModuleManager().insert(this, this.toJson());
    }

    public Ban getBan(String id) {
        for (Ban ban : this.bans) {
            if (ban.getId().equals(id)) {
                return ban;
            }
        }
        return null;
    }
}
