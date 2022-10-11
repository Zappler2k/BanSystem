package de.zappler2k.bansystem.bungee.commands;

import de.zappler2k.bansystem.BanSystem;
import de.zappler2k.bansystem.api.message.Message;
import de.zappler2k.bansystem.ban.file.impl.Ban;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BanManagerCommand extends Command implements TabExecutor {


    public BanManagerCommand() {
        super("banmanager", "bungeesystem.banmanager", "bm");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Message message = new Message(sender);
        if(sender instanceof ProxiedPlayer) {
            if(sender.hasPermission("bungeesystem.banmanager")) {
                if(args.length == 0) {
                    sendHelp(message);
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("help")) {
                        sendHelp(message);
                    }  else {
                        sendHelp(message);
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("add")) {
                        if(!BanSystem.getInstance().getBanManager().getBanFile().getBans().contains(BanSystem.getInstance().getBanManager().getBanFile().getBan(args[1]))) {
                            BanSystem.getInstance().getBanManager().getBanFile().addBan(new Ban(args[1], "Reason", 0, 0, 0, 0, false,false));
                            message.sendMessage(BanSystem.getPrefix() + "§aBan successfully added! §8(§7Go to the Config to manage the Bans)");
                        } else {
                            message.sendMessage(BanSystem.getPrefix() + "§cBan already exists!");
                        }
                    } else if(args[0].equalsIgnoreCase("remove")) {
                        if(BanSystem.getInstance().getBanManager().getBanFile().getBans().contains(BanSystem.getInstance().getBanManager().getBanFile().getBan(args[1]))) {
                            BanSystem.getInstance().getBanManager().getBanFile().removeBan(args[1]);
                            message.sendMessage(BanSystem.getPrefix() + "§aBan successfully removed!");
                        } else {
                            message.sendMessage(BanSystem.getPrefix() + "§cBan does not exist!");
                        }
                    } else  if(args[0].equalsIgnoreCase("list")) {
                        int site = Integer.parseInt(args[1]);
                        message.sendMessage(BanSystem.getPrefix() + "§eSite §8● §7" + site);
                        message.sendMessage("");
                        List<Ban> outputbans = BanSystem.getInstance().getBanManager().getBanFile().getBans().stream().skip((site - 1) * 5).limit(5).collect(Collectors.toList());
                        for(Ban ban : outputbans) {
                            TextComponent textComponent = new TextComponent();
                            textComponent.setText(ban.getId());
                            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder("§eDays       §8● §7 "    + ban.getDays()
                                                            + "\n§eMinutes  §8● §7 " + ban.getMinutes()
                                                            +  "\n§eSeconds §8● §7 " + ban.getSeconds()
                                                            +  "\n§eReason  §8● §7 \n"  + ban.getReason()).create()));
                            ProxyServer.getInstance().getPlayer(sender.getName()).sendMessage(textComponent);
                        }
                    } else {
                        sendHelp(message);
                    }
                }
            } else {
                message.sendMessage("%nopermission%");
            }
        } else {
            message.sendMessage("%prefix% &cYou can't use this command from the console.");
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ArrayList<String> commands = new ArrayList<>();
        if(sender.hasPermission("bungeesystem.banmanager")) {
            if(args.length == 1) {
                commands.add("help");
                commands.add("list");
                commands.add("add");
                commands.add("remove");
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("add")) {
                    commands.add("id");
                } else if(args[0].equalsIgnoreCase("remove")) {
                    for(Ban ban : BanSystem.getInstance().getBanManager().getBanFile().getBans()) {
                        commands.add(ban.getId());
                    }
                } else if(args[0].equalsIgnoreCase("list")) {
                    commands.add("page");
                }
            }
        }
        return commands;
    }

    private void sendHelp(Message message) {
        message.sendMessage("");
        message.sendMessage("%prefix% §8| §7/BanManager §8<§ahelp§8> (§7Shows this Message§8)");
        message.sendMessage("%prefix% §8| §7/BanManager §8<§aadd§8> §8<§7id§8> (§7Add a ban to the banlist§8)");
        message.sendMessage("%prefix% §8| §7/BanManager §8<§aremove§8> §8<§7id§8> (§7Remove a ban from the banlist)");
        message.sendMessage("%prefix% §8| §7/BanManager §8<§alist§8> <site> (§7List all bans§8)");
    }
}
