package de.zappler2k.bansystem.player;

import com.google.gson.GsonBuilder;
import de.zappler2k.bansystem.BanSystem;
import de.zappler2k.bansystem.api.mysql.MySQLConnnector;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerManager implements Listener {

    private List<BungeePlayer> players;

    private MySQLConnnector playerMySQL;

    public PlayerManager() {
        this.players = new ArrayList<>();
        playerMySQL = new MySQLConnnector("plugins/BungeeSystem/player", "mysqlconfig.json");
        playerMySQL.update("CREATE TABLE IF NOT EXISTS Players (uuid VARCHAR(36), info TEXT, PRIMARY KEY (uuid))");
        BanSystem.getInstance().getProxy().getPluginManager().registerListener(BanSystem.getInstance(), this);
    }

    public void addPlayer(BungeePlayer player, String uuid) {
        players.add(player);
        createPlayerMySQL(uuid);
        players.remove(player);
        player = getPlayerMySQL(uuid);
        player.setLastJoin(System.currentTimeMillis());
        players.add(player);
    }

    public void removePlayer(BungeePlayer player, String uuid) {
        getPlayer(uuid).setOnlineTime(System.currentTimeMillis() - player.getLastJoin() + player.getOnlineTime());
        setPlayerMySQL(uuid, player);
        players.remove(player);
    }

    public BungeePlayer getPlayer(String uuid) {
        for (BungeePlayer player : players) {
            if (player.getUuid().equals(uuid)) {
                return player;
            }
        }
        return null;
    }

    public boolean playerExists(String uuid) {
        for (BungeePlayer player : players) {
            if (player.getUuid().equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    public boolean playerExistsMySQL(String uuid) {
        try {
            ResultSet rs = playerMySQL.query("SELECT * FROM Players WHERE UUID=?", uuid);
            if (rs.next()) return rs.getString("UUID") != null;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void createPlayerMySQL(String uuid) {
        if (!playerExistsMySQL(uuid)) {
            String info = new GsonBuilder().create().toJson(getPlayer(uuid));
            playerMySQL.update("INSERT INTO Players(UUID, info) VALUES (?,?);", uuid, info);
        }
    }

    public void setPlayerMySQL(String uuid, BungeePlayer player) {
        if (playerExistsMySQL(uuid)) {
            String info = new GsonBuilder().create().toJson(getPlayer(uuid));
            playerMySQL.update("UPDATE Players SET info=? WHERE UUID=?", info, uuid);
        } else {
            createPlayerMySQL(uuid);
            setPlayerMySQL(uuid, player);
        }
    }

    public BungeePlayer getPlayerMySQL(String uuid) {
        BungeePlayer player = null;
        if (playerExistsMySQL(uuid)) {
            try {
                ResultSet rs = playerMySQL.query("SELECT * FROM Players WHERE UUID=?", uuid);
                if ((!rs.next()) || (String.valueOf(rs.getString("info")) == null)) ;

                player = new GsonBuilder().create().fromJson(rs.getString("info"), BungeePlayer.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            createPlayerMySQL(uuid);
            getPlayerMySQL(uuid);
        }
        return player;
    }

    @EventHandler
    public void onJoin(PostLoginEvent event) {
        BanSystem.getInstance().getPlayerManager().addPlayer(new BungeePlayer(event.getPlayer().getName(), event.getPlayer().getUniqueId().toString(), event.getPlayer().getAddress().getAddress().getHostAddress()), event.getPlayer().getUniqueId().toString());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event) {
        BanSystem.getInstance().getPlayerManager().removePlayer(BanSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId().toString()), event.getPlayer().getUniqueId().toString());
    }
}
