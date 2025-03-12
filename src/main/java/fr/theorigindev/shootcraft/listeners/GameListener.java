package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.game.GameManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GameListener implements Listener {

    private GameManager gameManager;

    public GameListener(GameManager gameMgr){

        this.gameManager = gameMgr;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        gameManager.getPlayerManager().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        gameManager.getPlayerManager().removePlayer(event.getPlayer());
    }
}
