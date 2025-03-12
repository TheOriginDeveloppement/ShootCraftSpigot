package fr.theorigindev.shootcraft.game;

import fr.theorigindev.shootcraft.Loader;
import fr.theorigindev.shootcraft.utils.GameConfig;
import fr.theorigindev.shootcraft.utils.MessageUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class GameLoop {

    private int countdownSeconds;
    private int gameTimeSeconds;
    private final GameManager gameManager;

    public GameLoop(GameManager gameManager) {
        this.countdownSeconds = GameConfig.getInstance().getCountdown();
        this.gameTimeSeconds = GameConfig.getInstance().getTimer();
        this.gameManager = gameManager;

        startGameLoop();
    }

    private void startGameLoop() {
        BukkitRunnable gameLoop = new BukkitRunnable() {
            @Override
            public void run() {
                switch (gameManager.getGameState()) {
                    case WAITING:
                        handleWaitingState();
                        break;
                    case STARTING:
                        handleStartingState();
                        break;
                    case RUNNING:
                        handleRunningState();
                        break;
                    case FINISHED:
                        handleFinishedState();
                        break;
                }
            }
        };
        gameLoop.runTaskTimer(Loader.getInstance(), 0L, 20L);
    }


    private void handleWaitingState() {
        int playerCount = gameManager.getPlayerManager().getPlayers().size();
        if (playerCount >= GameConfig.getInstance().getMinPlayersAllowed() && playerCount <= GameConfig.getInstance().getMaxPlayersAllowed()) {
            gameManager.setGameState(GameState.STARTING);
            MessageUtils.broadcast("§aLe compte à rebours commence !");
        }
    }

    private void handleStartingState() {
        int playerCount = gameManager.getPlayerManager().getPlayers().size();
        if (playerCount < GameConfig.getInstance().getMinPlayersAllowed()) {
            gameManager.setGameState(GameState.WAITING);
            countdownSeconds = GameConfig.getInstance().getCountdown();
            return;
        }

        if (countdownSeconds > 0) {
            if (countdownSeconds <= 10 || countdownSeconds % 10 == 0) {
                MessageUtils.broadcast("§eLa partie commence dans " + countdownSeconds + " secondes !");
            }
            countdownSeconds--;
        } else {
            gameManager.startGame();
        }
    }

    private void handleRunningState() {
        int players = gameManager.getPlayerManager().getPlayers().size();
        if (players <= 1) {
            gameManager.endGame();
            return;
        }

        if (gameTimeSeconds > 0) {
            if (gameTimeSeconds <= 10 || gameTimeSeconds % 60 == 0) {
                MessageUtils.broadcast("§eTemps restant : " + MessageUtils.getFormatedTimer(gameTimeSeconds));
            }
            gameTimeSeconds--;
        } else {
            gameManager.endGame();
        }
    }

    private void handleFinishedState() {
        MessageUtils.broadcast("§aLa partie est terminée !");
        gameManager.setGameState(GameState.WAITING);
        countdownSeconds = GameConfig.getInstance().getCountdown();
        gameTimeSeconds = GameConfig.getInstance().getTimer();
    }
}
