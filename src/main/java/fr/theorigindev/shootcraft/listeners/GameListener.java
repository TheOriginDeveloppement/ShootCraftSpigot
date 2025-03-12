package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.game.QueueManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        QueueManager.giveJoinItemsQueue(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        QueueManager.leaveQueue(event.getPlayer());

    }
}
