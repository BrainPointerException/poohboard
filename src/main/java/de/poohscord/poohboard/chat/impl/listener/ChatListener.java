package de.poohscord.poohboard.chat.impl.listener;

import de.poohscord.poohboard.chat.IChatConfig;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ChatListener implements Listener, ChatRenderer {

    private final IChatConfig chatConfig;

    public ChatListener(IChatConfig chatConfig) {
        this.chatConfig = chatConfig;
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(this);
    }

    @Override
    public Component render(Player player, Component sourceDisplayName, Component message, Audience audience) {
        return this.chatConfig.getChatPrefix(player).append(message);
    }
}
