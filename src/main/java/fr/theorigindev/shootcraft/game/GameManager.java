package fr.theorigindev.shootcraft.game;

import fr.theorigindev.shootcraft.Loader;
import fr.theorigindev.shootcraft.utils.GameConfig;
import fr.theorigindev.shootcraft.utils.MessageUtils;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private Arena arena;
    private PlayerManager playerManager;
    private GameState gameState;

    public GameManager(){

        arena = new Arena(GameConfig.getInstance().getMinArea(), GameConfig.getInstance().getMaxArea(), GameConfig.getInstance().getGameWorld(), GameConfig.getInstance().getTeleportLocations());
        gameState = GameState.WAITING;
        playerManager = new PlayerManager();

    }

    public void startGame(){
        gameState = GameState.RUNNING;

        arena.teleportPlayers(playerManager.getPlayers());
        playerManager.giveAllPlayersKits();
        MessageUtils.broadcast("La partie commence ! Utilisez votre bâton pour tirer ! ");
    }

    public boolean checkGameEnd(){
        return playerManager.getPlayers().size() < 2;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Arena getArena() {
        return arena;
    }

    public void endGame(){
        gameState = GameState.FINISHED;
        //aff le classement
        playerManager.finish();
    }
}
