package de.zappler2k.bansystem.api.mysql.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.zappler2k.bansystem.api.file.IModule;
import de.zappler2k.bansystem.api.file.ModuleManager;
import lombok.Getter;

import java.io.File;

@Getter
public class MySQLModule implements IModule {

    String HOST;
    Integer PORT;
    String DATABASE;
    String USER;
    String PASSWORD;
    String parantfile;
    String file;

    public MySQLModule(ModuleManager moduleManager, String parantfile, String file) {
        this.parantfile = parantfile;
        this.file = file;
        moduleManager.register(this);
    }

    public MySQLModule(String HOST, Integer PORT, String DATABASE, String USER, String PASSWORD) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.DATABASE = DATABASE;
        this.USER = USER;
        this.PASSWORD = PASSWORD;
    }

    @Override
    public File getFile() {
        return new File(parantfile, file);
    }

    @Override
    public String getDefaultConfig() {
        return new MySQLModule("localhost", 3306, "database", "user", "password").toJson();
    }

    @Override
    public IModule fromJson(String content) {
        return new Gson().fromJson(content, MySQLModule.class);
    }

    @Override
    public String toJson() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }


}
