package de.poohscord.poohboard;

import de.poohscord.poohboard.group.GroupFactory;
import de.poohscord.poohboard.group.IGroup;
import de.poohscord.poohboard.scoreboard.IScoreboard;
import de.poohscord.poohboard.scoreboard.IScoreboardConfig;
import de.poohscord.poohboard.scoreboard.impl.ScoreboardConfigImpl;
import de.poohscord.poohboard.scoreboard.impl.ScoreboardImpl;
import de.poohscord.poohboard.scoreboard.impl.ScoreboardListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PoohBoardPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        IGroup group = new GroupFactory().makeGroup();

        IScoreboardConfig config = new ScoreboardConfigImpl(this);
        IScoreboard board = new ScoreboardImpl(config, group);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ScoreboardListener(board), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            Bukkit.getOnlinePlayers().forEach(board::update);
        }, 0, 20*3);
    }

    @Override
    public void onDisable() {
    }
}