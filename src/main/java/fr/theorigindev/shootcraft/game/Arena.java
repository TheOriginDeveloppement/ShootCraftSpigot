package fr.theorigindev.shootcraft.game;

import fr.theorigindev.shootcraft.Loader;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class Arena {
    private Location min;
    private Location max;
    private final World world;
    private final List<Location> positions;

    public Arena(Location min, Location max, World world, List<Location> positions){
        this.min = min;
        this.max = max;
        this.world = world;
        this.positions = positions;
    }

    public void teleportPlayers(List<Player> players){

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Location position = positions.get(i);

            player.teleport(position);
        }
    }

    public void randomTeleport(Player player) {
        Random rand = new Random();
        Location randomPos = positions.get(rand.nextInt(positions.size()));

        player.teleport(randomPos);
    }

    public boolean isInArena(Location loc) {
        if (loc.getWorld() != world) {
            return false;
        }

        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();

        return x >= min.getX() && x <= max.getX() &&
                y >= min.getY() && y <= max.getY() &&
                z >= min.getZ() && z <= max.getZ();
    }

}
