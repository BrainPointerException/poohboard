package de.poohscord.poohboard.scoreboard.impl.board;

import de.poohscord.poohboard.group.IGroup;
import de.poohscord.poohboard.scoreboard.IScoreboard;
import de.poohscord.poohboard.scoreboard.IScoreboardConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardImpl implements IScoreboard {

    private final IScoreboardConfig config;

    private final IGroup group;

    private final Map<String, Scoreboard> boards = new HashMap<>();

    public ScoreboardImpl(IScoreboardConfig config, IGroup group) {
        this.config = config;
        this.group = group;
    }

    @Override
    public void create(Player player) {
        if (this.boards.containsKey(player.getUniqueId().toString())) {
            return;
        }
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective(
                "board",
                Criteria.DUMMY,
                LegacyComponentSerializer.legacyAmpersand().deserialize(config.getTitle()),
                RenderType.INTEGER
                );
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setAutoUpdateDisplay(true);

        this.boards.put(player.getUniqueId().toString(), board);

        for (int i = 0; i < config.getLines().size(); i++) {

            String line = config.getLines().get(i);

            // Set simple lines without placeholders
            if (!line.contains("%")) {
                Score score = objective.getScore(line.replaceAll("&", "§"));
                score.setScore(config.getLines().size() - i);
                continue;
            }

            String formattedLine = line.replaceAll("&", "§");
            String teamEntry = "";

            for (Placeholder placeholder : Placeholder.values()) {
                if (!formattedLine.contains(placeholder.getPlaceholder())) {
                    continue;
                }
                // Set placeholder lines
                Team team = board.registerNewTeam(placeholder.getPlaceholder());
                teamEntry = placeholder.getTeamEntry();
                team.addEntry(teamEntry);

                setTeamPrefixWithPlaceholder(team, player, formattedLine, placeholder);
                break;
            }

            objective.getScore(teamEntry).setScore(config.getLines().size() - i);
        }

        player.setScoreboard(board);
    }

    @Override
    public void update(Player player) {
        if (!this.boards.containsKey(player.getUniqueId().toString())) {
            return;
        }

        List<String> lines = config.getLines();

        Scoreboard board = this.boards.get(player.getUniqueId().toString());
        for (Placeholder placeholder : Placeholder.values()) {
            Team team = board.getTeam(placeholder.getPlaceholder());
            if (team == null)
                continue;

            String teamLine = lines.stream().filter(l -> l.contains(placeholder.getPlaceholder())).findFirst().orElse(placeholder.getPlaceholder());
            setTeamPrefixWithPlaceholder(board.getTeam(placeholder.getPlaceholder()), player, teamLine.replaceAll("&", "§"), placeholder);
        }
    }

    @Override
    public void delete(Player player) {
        this.boards.remove(player.getUniqueId().toString());
    }

    private void setTeamPrefixWithPlaceholder(Team team, Player player, String formattedLine, Placeholder placeholder) {
        team.prefix(LegacyComponentSerializer.legacyAmpersand()
                .deserialize(
                        formattedLine.replace(placeholder.getPlaceholder(),
                                LegacyComponentSerializer.legacyAmpersand().serialize(getComponentTextByPlaceholder(placeholder, player)))));
    }

    private Component getComponentTextByPlaceholder(Placeholder placeholder, Player player) {
        Component component = null;
        switch (placeholder) {
            case PLAYER_NAME -> component = Component.text(player.getName());
            case GROUP_NAME -> component = Component.text(this.group.getGroupPrefix(player).split(" ")[0]);
            case ONLINE_PLAYERS -> component = Component.text(Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
            case HONIGTROPFEN -> component = Component.text(0);
            case HONIGKRISTALLE -> component = Component.text(0);
        }
        return component;
    }

    private enum Placeholder {
        PLAYER_NAME("%player_name%", "§0"),
        GROUP_NAME("%group_name%", "§1"),
        ONLINE_PLAYERS("%online_players%", "§2"),
        HONIGTROPFEN("%honigtropfen%", "§4"),
        HONIGKRISTALLE("%honigkristalle%", "§5");

        private final String placeholder, teamEntry;

        Placeholder(String placeholder, String teamEntry) {
            this.placeholder = placeholder;
            this.teamEntry = teamEntry;
        }

        public String getPlaceholder() {
            return placeholder;
        }

        public String getTeamEntry() {
            return teamEntry;
        }
    }
}
