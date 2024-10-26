package de.poohscord.poohboard.group.impl;

import de.poohscord.poohboard.group.IGroup;
import org.bukkit.entity.Player;

public class FallbackGroupImpl implements IGroup {

    @Override
    public String getGroup(Player player) {
        return "default";
    }
}
