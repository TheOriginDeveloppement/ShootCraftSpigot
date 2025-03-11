package fr.theorigindev.shootcraft.game;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class Arena {
    private Location min;
    private Location max;
    private final World world;
    private final List<int[]> positions;

    public Arena(Location min, Location max, World world, List<int[]> positions){
        this.min = min;
        this.max = max;
        this.world = world;
        this.positions = positions;
    }

    public void teleportPlayers(List<Player> players){
        /*
            le nb de position == aux nb de players except si no
         */

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            int[] position = positions.get(i);
            player.teleport(new Location(world, position[0], position[1], position[2]));
        }
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
