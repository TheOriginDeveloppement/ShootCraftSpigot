package fr.theorigindev.shootcraft.game;

import com.avaje.ebeaninternal.server.core.Message;
import fr.theorigindev.shootcraft.Loader;
import fr.theorigindev.shootcraft.utils.GameConfig;
import fr.theorigindev.shootcraft.utils.MessageUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class GameManager {

    private Arena arena;
    private PlayerManager playerManager;
    private GameState gameState;


    public GameManager() {

        arena = new Arena(GameConfig.getInstance().getMinArea(), GameConfig.getInstance().getMaxArea(), GameConfig.getInstance().getGameWorld(), GameConfig.getInstance().getTeleportLocations());
        gameState = GameState.WAITING;
        playerManager = new PlayerManager();
        new GameLoop(this);
    }




    public void startGame() {
        gameState = GameState.RUNNING;

        arena.teleportPlayers(playerManager.getPlayers());
        playerManager.giveAllPlayersKits();
        MessageUtils.broadcast("La partie commence ! Utilisez votre bâton pour tirer ! ");
    }




    public void setGameState(GameState gameState) {
        this.gameState = gameState;
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

    public void endGame() {
        gameState = GameState.FINISHED;
        playerManager.finish();
    }
}
