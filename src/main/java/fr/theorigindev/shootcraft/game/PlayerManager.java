package fr.theorigindev.shootcraft.game;

import fr.theorigindev.shootcraft.Loader;
import fr.theorigindev.shootcraft.utils.GameConfig;
import fr.theorigindev.shootcraft.utils.ItemBuilder;
import fr.theorigindev.shootcraft.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {

    private List<Player> players;
    private Map<Player, Integer> kills;
    private Map<Player, Integer> deaths;

    public PlayerManager(){
        players = new ArrayList<>();
        kills = new HashMap<>();
        deaths = new HashMap<>();

    }

    public void addPlayer(Player player){
        if(!players.contains(player)){


            players.add(player);
            kills.putIfAbsent(player, 0);
            deaths.putIfAbsent(player, 0);

            if (Loader.getInstance().getGameManager().getGameState() == GameState.WAITING) {
                MessageUtils.broadcast(player.getDisplayName() + " a rejoint la partie ");
            }
        }else{
            throw new IllegalStateException("Ce joueur a déjà ajouté à la liste");
        }
    }

    public void removePlayer(Player player){

        if (players.contains(player)) {


            players.remove(player);
            kills.remove(player);
            deaths.remove(player);

            if (Loader.getInstance().getGameManager().getGameState() == GameState.WAITING) {
                MessageUtils.broadcast(player.getDisplayName() + " a quitté la partie ");
            }
        }

    }

    public void respawn(Player player){
        Loader.getInstance().getGameManager().getArena().randomTeleport(player);
    }

    public void killPlayer(Player victim, Player shooter) {
        respawn(victim);

        MessageUtils.broadcast(victim.getDisplayName()+" a été tué par "+shooter.getDisplayName());
        deaths.put(victim, deaths.getOrDefault(victim, 0) + 1);
        kills.put(shooter, kills.getOrDefault(shooter, 0) + 1);
    }

    public void finish() {
        MessageUtils.broadcast(MessageUtils.getRankingMessage(getRanking()));

        for (Player player : GameConfig.getInstance().getGameWorld().getPlayers()){
            player.teleport(GameConfig.getInstance().getSpawnWorld().getSpawnLocation());
            removePlayer(player);
            QueueManager.giveJoinItemsQueue(player);
        }
    }

    public void giveAllPlayersKits() {

        ItemStack stick = ItemBuilder.createShootCraftStick();
        for (Player player : players){
            player.getInventory().clear();
            player.getInventory().addItem(stick);
        }
    }

    public List<Map.Entry<Player, Integer>> getRanking() {
        return kills.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .toList();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Map<Player, Integer> getKills() {
        return kills;
    }

    public Map<Player, Integer> getDeaths() {
        return deaths;
    }
}
