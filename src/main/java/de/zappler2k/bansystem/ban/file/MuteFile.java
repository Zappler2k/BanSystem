package de.zappler2k.bansystem.ban.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler2k.bansystem.BanSystem;
import de.zappler2k.bansystem.api.file.IModule;
import de.zappler2k.bansystem.ban.file.impl.Mute;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MuteFile implements IModule {

    private List<Mute> mutes;

    public MuteFile() {
        this.mutes = new ArrayList<>();
    }

    @Override
    public File getFile() {
        return new File("plugins/BungeeSystem/configs/Mutes.json");
    }

    @Override
    public String getDefaultConfig() {
        return new MuteFile().toJson();
    }

    @Override
    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    @Override
    public IModule fromJson(String content) {
        return new Gson().fromJson(content, MuteFile.class);
    }

    public void addMute(Mute mute) {
        this.mutes.add(mute);
        BanSystem.getInstance().getModuleManager().insert(this, this.toJson());
    }

    public void removeMute(String id) {
        this.mutes.removeIf(mute -> mute.getId().equals(id));
        BanSystem.getInstance().getModuleManager().insert(this, this.toJson());
    }

    public Mute getMute(String id) {
        for (Mute mute : this.mutes) {
            if (mute.getId().equals(id)) {
                return mute;
            }
        }
        return null;
    }
}
