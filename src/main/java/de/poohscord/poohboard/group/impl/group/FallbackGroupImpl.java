package de.poohscord.poohboard.group.impl.group;

import de.poohscord.poohboard.group.IGroup;
import org.bukkit.entity.Player;

import java.util.Map;

public class FallbackGroupImpl implements IGroup {

    @Override
    public String getGroupName(Player player) {
        return "default";
    }

    @Override
    public String getGroupPrefix(Player player) {
        return "default";
    }

    @Override
    public Map<String, String> getGroupPrefixesSortedByGroupWeight () {
        return Map.of("a", "default");
    }
}
