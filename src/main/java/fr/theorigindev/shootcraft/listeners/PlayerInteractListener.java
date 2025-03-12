package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.entities.CustomProjectile;
import fr.theorigindev.shootcraft.game.GameManager;
import fr.theorigindev.shootcraft.game.GameState;
import fr.theorigindev.shootcraft.game.QueueManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    private GameManager gameManager;

    public PlayerInteractListener(GameManager gameMgr) {

        this.gameManager = gameMgr;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) {
            return;
        }


        switch (item.getItemMeta().getDisplayName()) {

            case "§6Lanceur de Feu":
                if (gameManager.getGameState() != GameState.RUNNING) {
                    return;
                }

                CustomProjectile projectile = new CustomProjectile(player);
                projectile.launch();

                event.setCancelled(true);
                break;
            case "§2Rejoindre la file d'attente":
                QueueManager.joinQueue(player);
                event.setCancelled(true);

                break;
            case "§cQuitter la file d'attente":
                QueueManager.leaveQueue(player);
                event.setCancelled(true);

                break;
        }
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        switch (event.getItemInHand().getItemMeta().getDisplayName()) {
            case "§6Lanceur de Feu":
            case "§2Rejoindre la file d'attente":
            case "§cQuitter la file d'attente":
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        switch (event.getPlayer().getItemInHand().getItemMeta().getDisplayName()) {
            case "§6Lanceur de Feu":
            case "§2Rejoindre la file d'attente":
            case "§cQuitter la file d'attente":
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onPlayerGetDamage(EntityDamageEvent event) {
        switch (event.getCause()) {
            case FALL:
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }


}
