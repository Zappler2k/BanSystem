package de.zappler2k.bansystem.ban;

import de.zappler2k.bansystem.BanSystem;
import de.zappler2k.bansystem.ban.file.BanFile;
import de.zappler2k.bansystem.ban.file.MuteFile;
import de.zappler2k.bansystem.ban.file.impl.Ban;
import de.zappler2k.bansystem.ban.file.impl.Mute;
import de.zappler2k.bansystem.player.BungeePlayer;
import lombok.Getter;

@Getter
public class BanManager {

    private BanFile banFile;
    private MuteFile muteFile;

    public BanManager() {
        BanSystem.getInstance().getModuleManager().register(new MuteFile());
        BanSystem.getInstance().getModuleManager().register(new BanFile());
        banFile = (BanFile) BanSystem.getInstance().getModuleManager().getModule(BanFile.class);
        muteFile = (MuteFile) BanSystem.getInstance().getModuleManager().getModule(MuteFile.class);
    }

    public void banPlayer(BungeePlayer bungeePlayer, String banid) {
        Ban ban = banFile.getBan(banid);
        bungeePlayer.setBanned(true);
        bungeePlayer.setBans(bungeePlayer.getBans() + 1);
        bungeePlayer.setBanreason(ban.getReason());
        bungeePlayer.setBanduration(0L);
    }
    public void adminBanPlayer(BungeePlayer bungeePlayer, String banid, String reason, Long duration) {
        bungeePlayer.setBanned(true);
        bungeePlayer.setBans(bungeePlayer.getBans() + 1);
        bungeePlayer.setBanreason(reason);
        bungeePlayer.setBanduration(duration);
    }
    public void unbanPlayer(BungeePlayer bungeePlayer) {
        bungeePlayer.setBanned(false);
        bungeePlayer.setBanreason("");
        bungeePlayer.setBanduration(0L);
        bungeePlayer.setPermBanned(false);
    }
    public void perBanPlayer(BungeePlayer bungeePlayer, String banid) {
        Ban ban = banFile.getBan(banid);
        bungeePlayer.setPermBanned(true);
        bungeePlayer.setBanreason(ban.getReason());
    }
    public void mutePlayer(BungeePlayer bungeePlayer, String muteid) {
        Mute mute = muteFile.getMute(muteid);
        bungeePlayer.setMuted(true);
        bungeePlayer.setMutes(bungeePlayer.getMutes() + 1);
        bungeePlayer.setMutereason(mute.getReason());
        bungeePlayer.setMuteduration(0L);
    }
    public void adminMutePlayer(BungeePlayer bungeePlayer, String muteid, String reason, Long duration) {
        bungeePlayer.setMuted(true);
        bungeePlayer.setMutes(bungeePlayer.getMutes() + 1);
        bungeePlayer.setMutereason(reason);
        bungeePlayer.setMuteduration(duration);
    }
    public void permMutePlayer(BungeePlayer bungeePlayer, String muteid) {
        Mute mute = muteFile.getMute(muteid);
        bungeePlayer.setMuted(true);
        bungeePlayer.setMutereason(mute.getReason());
    }
    public void unmutePlayer(BungeePlayer bungeePlayer) {
        bungeePlayer.setMuted(false);
        bungeePlayer.setMutereason("");
        bungeePlayer.setMuteduration(0L);
        bungeePlayer.setPermMuted(false);
    }
}