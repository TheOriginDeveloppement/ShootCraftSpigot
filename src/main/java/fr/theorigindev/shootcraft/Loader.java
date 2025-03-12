package fr.theorigindev.shootcraft;

import fr.theorigindev.shootcraft.commands.StartShootCraftCommand;
import fr.theorigindev.shootcraft.game.GameManager;
import fr.theorigindev.shootcraft.listeners.GameListener;
import fr.theorigindev.shootcraft.listeners.PlayerInteractListener;
import fr.theorigindev.shootcraft.listeners.ProjectileHitListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Loader extends JavaPlugin {

    private static Loader instance;
    private GameManager gameManager;


    @Override
    public void onEnable() {

        instance = this;

        gameManager = new GameManager();

        this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(gameManager), this);
        this.getServer().getPluginManager().registerEvents(new ProjectileHitListener(), this);
        this.getServer().getPluginManager().registerEvents(new GameListener(gameManager), this);

        getCommand("startshootcraft").setExecutor(new StartShootCraftCommand(gameManager));

        this.getServer().getLogger().info("Shootcraft Enabled");

    }

    public static Loader getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        this.getServer().getLogger().info("Shootcraft Disabled");
    }

    public GameManager getGameManager() {
        return gameManager;
    }


}
