package de.poohscord.poohboard.group.impl.group;

import de.poohscord.poohboard.group.IGroup;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LuckPermsGroupImpl implements IGroup {

    private final LuckPerms api;

    public LuckPermsGroupImpl() throws IllegalStateException {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.api = provider.getProvider();
        } else {
            throw new IllegalStateException("LuckPerms not found!");
        }
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
}
