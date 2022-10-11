package de.zappler2k.bansystem.player;

import de.zappler2k.bansystem.BanSystem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BungeePlayer {

    private String name;
    private String uuid;
    private String ip;
    private Long firstJoin;
    private Long lastJoin;
    private Long onlineTime;
    private Integer bans;
    private Integer kicks;
    private Integer warns;
    private Integer mutes;
    private boolean isBanned;
    private boolean isMuted;

    private boolean isPermBanned;

    private boolean isPermMuted;
    private Long lastBan;
    private Long lastMute;
    private Long lastKick;
    private Long lastWarn;
    private Long banduration;
    private Long muteduration;
    private String banreason;
    private String mutereason;

    public BungeePlayer(String name, String uuid, String ip) {
        this.uuid = uuid;
        this.name = name;
        this.ip = ip;
        if (!BanSystem.getInstance().getPlayerManager().playerExistsMySQL(uuid)) {
            this.firstJoin = System.currentTimeMillis();
            this.lastJoin = System.currentTimeMillis();
            this.onlineTime = 0L;
            this.bans = 0;
            this.kicks = 0;
            this.warns = 0;
            this.mutes = 0;
            this.isBanned = false;
            this.isMuted = false;
            this.isPermBanned = false;
            this.isPermMuted = false;
            this.lastBan = 0L;
            this.lastMute = 0L;
            this.lastKick = 0L;
            this.lastWarn = 0L;
            this.banduration = 0L;
            this.muteduration = 0L;
            this.banreason = "";
            this.mutereason = "";
        }
    }
}
