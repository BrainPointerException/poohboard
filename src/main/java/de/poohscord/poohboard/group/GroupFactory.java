package de.poohscord.poohboard.group;

import de.poohscord.poohboard.group.impl.FallbackGroupImpl;
import de.poohscord.poohboard.group.impl.LuckPermsGroupImpl;

public class GroupFactory {

    public IGroup makeGroup() {
        IGroup group;
        try {
            group = new LuckPermsGroupImpl();
        } catch (IllegalStateException e) {
            group = new FallbackGroupImpl();
        }
        return group;
    }

}
