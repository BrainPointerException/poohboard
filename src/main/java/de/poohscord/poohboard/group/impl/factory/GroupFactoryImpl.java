package de.poohscord.poohboard.group.impl.factory;

import de.poohscord.poohboard.group.IGroup;
import de.poohscord.poohboard.group.IGroupFactory;
import de.poohscord.poohboard.group.impl.group.FallbackGroupImpl;
import de.poohscord.poohboard.group.impl.group.LuckPermsGroupImpl;

public class GroupFactoryImpl implements IGroupFactory {

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
