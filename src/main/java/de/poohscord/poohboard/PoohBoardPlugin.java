package de.poohscord.poohboard;

import de.poohscord.poohboard.chat.IChatConfig;
import de.poohscord.poohboard.chat.impl.config.ChatYamlConfigImpl;
import de.poohscord.poohboard.chat.impl.listener.ChatListener;
import de.poohscord.poohboard.group.IGroupFactory;
import de.poohscord.poohboard.group.impl.factory.GroupFactoryImpl;
import de.poohscord.poohboard.group.IGroup;
import de.poohscord.poohboard.scoreboard.IScoreboard;
import de.poohscord.poohboard.scoreboard.IScoreboardConfig;
import de.poohscord.poohboard.scoreboard.impl.config.ScoreboardYamlConfigImpl;
import de.poohscord.poohboard.scoreboard.impl.board.ScoreboardImpl;
import de.poohscord.poohboard.scoreboard.impl.listener.ScoreboardListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PoohBoardPlugin extends JavaPlugin {

    private IScoreboard board;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        IGroupFactory groupFactory = new GroupFactoryImpl();
        IGroup group = groupFactory.makeGroup();

        IScoreboardConfig config = new ScoreboardYamlConfigImpl(this);
        this.board = new ScoreboardImpl(config, group);

        IChatConfig chatConfig = new ChatYamlConfigImpl(this, group);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ScoreboardListener(board), this);
        pm.registerEvents(new ChatListener(chatConfig), this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            Bukkit.getOnlinePlayers().forEach(board::update);
        }, 0, 20*3);
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(board::delete);
    }
}
