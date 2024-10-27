package de.poohscord.poohboard.chat;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface IChatConfig {

    Component getChatPrefix(Player player);

}
