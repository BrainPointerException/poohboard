package de.poohscord.poohboard.scoreboard;

import org.bukkit.entity.Player;

public interface IScoreboard {

    void create(final Player player);

    void update(final Player player);

    void delete(final Player player);

}
