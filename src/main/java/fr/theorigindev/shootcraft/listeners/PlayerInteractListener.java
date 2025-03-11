package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.game.GameManager;
import org.bukkit.event.Listener;

public class PlayerInteractListener implements Listener {

    private GameManager gameManager;

    public PlayerInteractListener(GameManager gameMgr){

        this.gameManager = gameMgr;
    }
}
