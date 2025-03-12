package fr.theorigindev.shootcraft.game;

import fr.theorigindev.shootcraft.Loader;
import fr.theorigindev.shootcraft.utils.ItemBuilder;
import org.bukkit.entity.Player;

public class QueueManager {


    public static void joinQueue(Player player) {
        Loader.getInstance().getGameManager().getPlayerManager().addPlayer(player);
        giveLeaveItemsQueue(player);
    }

    public static void leaveQueue(Player player) {
        Loader.getInstance().getGameManager().getPlayerManager().removePlayer(player);
        giveJoinItemsQueue(player);

    }

    public static void giveJoinItemsQueue(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(4, ItemBuilder.createQueueJoinItem());
    }

    public static void giveLeaveItemsQueue(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(4, ItemBuilder.createQueueLeaveItem());
    }
}
