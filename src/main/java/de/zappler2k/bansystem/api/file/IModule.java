package de.zappler2k.bansystem.api.file;

import java.io.File;

public interface IModule {

    File getFile();

    String getDefaultConfig();

    String toJson();

    IModule fromJson(String content);
}
