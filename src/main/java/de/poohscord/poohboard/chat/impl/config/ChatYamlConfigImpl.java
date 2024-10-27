package de.poohscord.poohboard.chat.impl.config;

import de.poohscord.poohboard.chat.IChatConfig;
import de.poohscord.poohboard.group.IGroup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ChatYamlConfigImpl implements IChatConfig {

    private final YamlConfiguration config;
    private final IGroup group;

    public ChatYamlConfigImpl(JavaPlugin plugin, IGroup group) {
        final File file = new File(plugin.getDataFolder(), "chat.yml");
        if (!file.exists()) {
            plugin.saveResource("chat.yml", false);
        }

        this.config = YamlConfiguration.loadConfiguration(file);
        this.group = group;
    }

    @Override
    public Component getChatPrefix(Player player) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(this.config.getString("chat.prefix")
                .replace("%group_prefix%", this.group.getGroupPrefix(player))
                .replace("%player_name%", player.getName()));
    }
}
