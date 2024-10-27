package de.poohscord.poohboard.group.impl.group;

import de.poohscord.poohboard.group.IGroup;
import org.bukkit.entity.Player;

public class FallbackGroupImpl implements IGroup {

    @Override
    public String getGroupName(Player player) {
        return "default";
    }
}
