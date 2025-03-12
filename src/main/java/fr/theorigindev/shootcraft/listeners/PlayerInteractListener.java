package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.entities.CustomProjectile;
import fr.theorigindev.shootcraft.game.GameManager;
import fr.theorigindev.shootcraft.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private GameManager gameManager;

    public PlayerInteractListener(GameManager gameMgr){

        this.gameManager = gameMgr;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || !item.getItemMeta().getDisplayName().equals("§6Lanceur de Feu")) {
            return;
        }

        if (gameManager.getGameState() != GameState.RUNNING) {
            return;
        }

        CustomProjectile projectile = new CustomProjectile(player);
        projectile.launch();


        event.setCancelled(true);
    }
}
