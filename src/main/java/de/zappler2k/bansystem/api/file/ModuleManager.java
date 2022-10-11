package de.zappler2k.bansystem.api.file;

import lombok.SneakyThrows;

import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private List<IModule> modules;

    public ModuleManager() {
        this.modules = new ArrayList<>();
    }

    @SneakyThrows
    public void register(IModule iModule) {
        if (!iModule.getFile().getParentFile().exists()) {
            iModule.getFile().getParentFile().mkdirs();
        }

        if (!iModule.getFile().exists()) {
            iModule.getFile().createNewFile();
            FileWriter writer = new FileWriter(iModule.getFile());
            writer.write(iModule.getDefaultConfig());
            writer.flush();
        }
        this.modules.add(iModule.fromJson(getContent(iModule)));
    }

    public void unregister(IModule iModule) {
        this.modules.remove(iModule);
    }

    public IModule getModule(Class<? extends IModule> module) {
        for (IModule iModule : modules) {
            if (iModule.getClass().getName().equalsIgnoreCase(module.getName())) {
                return iModule;
            }
        }
        return null;
    }

    public List<IModule> getModules() {
        return modules;
    }

    @SneakyThrows
    public void insert(IModule iModule, String data) {
        FileWriter fileWriter = new FileWriter(iModule.getFile());
        fileWriter.write(data);
        fileWriter.flush();
        fileWriter.close();
    }

    @SneakyThrows
    public String getContent(IModule iModule) {
        return new String(Files.readAllBytes(iModule.getFile().toPath()));
    }

}
