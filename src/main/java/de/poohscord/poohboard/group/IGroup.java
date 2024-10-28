package de.poohscord.poohboard.group;

import org.bukkit.entity.Player;

import java.util.Map;

public interface IGroup {

    String getGroupName(Player player);

    String getGroupPrefix(Player player);

    // returns a map of group prefixes with their weight represented as a string in alphabetical order
    Map<String, String> getGroupPrefixesSortedByGroupWeight();

}
