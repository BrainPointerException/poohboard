package de.poohscord.poohboard.scoreboard.impl.config;

import de.poohscord.poohboard.scoreboard.IScoreboardConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class ScoreboardConfigImpl implements IScoreboardConfig {

    private final File file;
    private final YamlConfiguration config;

    public ScoreboardConfigImpl(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(),"scoreboard.yml");
        if (!this.file.exists()) {
            plugin.saveResource("scoreboard.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    @Override
    public String getTitle() {
        return this.config.getString("scoreboard.title");
    }

    @Override
    public List<String> getLines() {
        return this.config.getStringList("scoreboard.lines");
    }
}
