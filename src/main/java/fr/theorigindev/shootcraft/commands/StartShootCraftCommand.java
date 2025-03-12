package fr.theorigindev.shootcraft.commands;

import fr.theorigindev.shootcraft.game.GameManager;
import fr.theorigindev.shootcraft.game.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartShootCraftCommand implements CommandExecutor {

    private final GameManager gameManager;

    public StartShootCraftCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCette commande est réservée aux joueurs !");
            return true;
        }

        Player player = (Player) sender;


        if (gameManager.getGameState() != GameState.WAITING) {
            player.sendMessage("§cUne partie est déjà en cours ou terminée !");
            return true;
        }

        int playerCount = gameManager.getPlayerManager().getPlayers().size();
        if (playerCount < 1) {
            player.sendMessage("§cIl faut au moins 2 joueurs pour lancer une partie !");
            return true;
        }

        gameManager.startGame();
        player.sendMessage("§aLa partie de ShootCraft a été lancée !");

        return true;
    }
}
