package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.game.GameManager;
import org.bukkit.event.Listener;

public class ProjectileHitListener implements Listener {

    private GameManager gameManager;

    public ProjectileHitListener(GameManager gameMgr){

        this.gameManager = gameMgr;
    }
}
