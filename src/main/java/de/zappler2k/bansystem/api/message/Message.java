package de.zappler2k.bansystem.api.message;

import de.zappler2k.bansystem.BanSystem;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
public class Message {

    private CommandSender sender;

    public Message(CommandSender sender) {
        this.sender = sender;
    }

    public void sendMessage(String message) {
        sender.sendMessage(new TextComponent(message
                                                    .replaceAll("&", "§")
                                                    .replaceAll("%prefix%", BanSystem.getInstance().getConfig().getPrefix().replaceAll("&", "§"))
                                                    .replaceAll("%nopermission%", BanSystem.getInstance().getConfig().getNopermission().replaceAll("&", "§")
                                                     )));
    }
}
