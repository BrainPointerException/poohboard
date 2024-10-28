package de.poohscord.poohboard.group.impl.group;

import de.poohscord.poohboard.group.IGroup;
import de.poohscord.poohboard.group.impl.group.sort.GroupSort;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.Map;

public class LuckPermsGroupImpl implements IGroup {

    private final LuckPerms api;
    private final GroupSort groupSort;

    public LuckPermsGroupImpl(GroupSort groupSort) throws IllegalStateException {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.api = provider.getProvider();
        } else {
            throw new IllegalStateException("LuckPerms not found!");
        }
        this.api.getGroupManager().loadAllGroups();
        this.groupSort = groupSort;
    }

    @Override
    public String getGroupName(Player player) {
        return this.api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup();
    }

    @Override
    public String getGroupPrefix(Player player) {
        return this.api.getGroupManager().getGroup(this.api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup())
                .getCachedData().getMetaData().getPrefix();
    }

    @Override
    public Map<String, String> getGroupPrefixesSortedByGroupWeight() {
        return this.groupSort.sortGroupsByWeightAlphabetically(new ArrayList<>(this.api.getGroupManager().getLoadedGroups()));
    }
}
