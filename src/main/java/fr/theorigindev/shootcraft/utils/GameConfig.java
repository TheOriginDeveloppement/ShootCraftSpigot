package fr.theorigindev.shootcraft.utils;

import fr.theorigindev.shootcraft.Loader;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameConfig {
    private FileConfiguration config;

    private static GameConfig instance;

    private GameConfig(){
        Loader.getInstance().saveDefaultConfig();
        config = Loader.getInstance().getConfig();
    }

    public static GameConfig getInstance() {
        if(instance == null){
            instance = new GameConfig();
        }

        return instance;
    }

    public World getGameWorld(){
        String worldName = config.getString("world", "world");
        World world = Loader.getInstance().getServer().getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("Monde '" + worldName + "' non trouvé !");
        }

        return world;
    }

    public World getSpawnWorld(){
        String worldName = config.getString("spawn-name", "world");
        World world = Loader.getInstance().getServer().getWorld(worldName);
        if (world == null) {
            throw new IllegalStateException("Monde '" + worldName + "' non trouvé !");
        }

        return world;
    }

    public Location getMinArea(){
        return new Location(getGameWorld(), config.getDouble("arena.min.x"), config.getDouble("arena.min.y"), config.getDouble("arena.min.z"));
    }

    public Location getMaxArea(){
        return new Location(getGameWorld(), config.getDouble("arena.max.x"), config.getDouble("arena.max.y"), config.getDouble("arena.max.z"));
    }

    public int getMinPlayersAllowed(){
        return config.getInt("players.min", 2);
    }

    public int getCountdown() {
        return config.getInt("countdown", 30);
    }

    public int getTimer() {
        return config.getInt("timer", 300);
    }


    public int getMaxPlayersAllowed(){
        return config.getInt("players.max", 8);
    }

    public List<Location> getTeleportLocations(){
        List<Location> teleportLocations = new ArrayList<>();
        List<?> locs = config.getList("teleport-locations");
        if (locs == null || locs.size() != getMaxPlayersAllowed()) {
            throw new IllegalStateException("Le nombre de teleport-locations doit être égal à players.max (" + getMaxPlayersAllowed() + ") !");
        }
        for (Object obj : locs) {
            if (obj instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) obj;
                double x = toDouble(map.get("x"));
                double y = toDouble(map.get("y"));
                double z = toDouble(map.get("z"));
                teleportLocations.add(new Location(getGameWorld(), x, y, z));
            }
        }

        return teleportLocations;
    }

    private double toDouble(Object value) {
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        } else {
            throw new IllegalStateException("Valeur invalide dans teleport-locations : " + value);
        }
    }
}
