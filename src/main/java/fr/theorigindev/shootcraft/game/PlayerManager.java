package fr.theorigindev.shootcraft.game;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerManager {

    private List<Player> players;
    private Map<Player, Integer> kills;

    public PlayerManager(){
        players = new ArrayList<>();
        kills = new HashMap<>();
    }

    public void addPlayer(Player player){
        if(!players.contains(player)){

            players.add(player);
            kills.putIfAbsent(player, 0);

        }else{
            /*
                TODO throw joueur déjà ajouté
             */
        }
    }

    public void removePlayer(Player player){

        players.remove(player);
        kills.remove(player);

    }

    public void giveAllPlayersKits() {

        for (Player player : players){

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


    public void finish() {
        for (Player player : players){
            // teleport spawn
        }
    }
}
