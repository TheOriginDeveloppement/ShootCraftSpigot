package fr.theorigindev.shootcraft.utils;

import fr.theorigindev.shootcraft.Loader;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class MessageUtils {

    private static final String FORMAT = "§6[ShootCraft] §e%s";

    public static void broadcast(String message){
        Loader.getInstance().getServer().broadcastMessage(String.format(FORMAT, message));
    }

    public static String getFormatedTimer(int gameSeconds) {
        int minutes = gameSeconds / 60;
        int secondes = gameSeconds % 60;

        String formattedTime;

        if (minutes > 0) {
            formattedTime = String.format("%d minute" + ((minutes > 1) ? "s" : "") + " et %d seconde" + ((secondes > 1) ? "s" : ""), minutes, secondes);
        } else {
            formattedTime = String.format("%d seconde" + ((secondes > 1) ? "s" : ""), secondes);
        }

        return formattedTime;
    }

    public static ChatColor getColorByRank(int rank) {
        return switch (rank) {
            case 1 -> ChatColor.YELLOW;
            case 2 -> ChatColor.GOLD;
            case 3 -> ChatColor.DARK_GRAY;
            default -> ChatColor.GRAY;
        };
    }

    public static String getRankingMessage(List<Map.Entry<Player, Integer>> rankingData) {
        StringBuilder rankingMessage = new StringBuilder("§e§lVoici le top 10 du classement kills\n\n");
        for (int i = 0; i < rankingData.size(); i++) {

            Map.Entry<Player, Integer> playerData = rankingData.get(i);
            rankingMessage.append(getColorByRank(i+1)).append("- ").append(i).append(" - ").append(playerData.getKey().getName()).append(" : ").append(playerData.getValue()).append(" kill").append((playerData.getValue() > 1) ? "s" : "").append("\n");

        }

        return rankingMessage.toString();
    }
}
