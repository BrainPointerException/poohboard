package de.poohscord.poohboard.scoreboard.impl.listener;

import de.poohscord.poohboard.scoreboard.IScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final IScoreboard board;

    public ScoreboardListener(IScoreboard board) {
        this.board = board;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerSetScoreboard(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        board.create(player);
    }

    @EventHandler
    public void onPlayerDeleteScoreboard(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        board.delete(player);
    }

}
