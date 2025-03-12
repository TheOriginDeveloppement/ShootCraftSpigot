package fr.theorigindev.shootcraft.listeners;

import fr.theorigindev.shootcraft.Loader;
import fr.theorigindev.shootcraft.entities.CustomProjectile;
import net.minecraft.server.v1_8_R3.EntityArrow;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) {
            return;
        }

        if (!(event.getDamager() instanceof CraftArrow arrow)) {
            return;
        }

        EntityArrow nmsArrow = arrow.getHandle();

        if (!nmsArrow.getCustomName().equals("ShootCraftProjectile")) {
            return;
        }

        Player shooter = CustomProjectile.getShooter(nmsArrow);

        if (shooter == null) {
            return;
        }

        Location loc = victim.getLocation();
        loc.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 0);
        loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0F, false, false);

        Loader.getInstance().getGameManager().getPlayerManager().killPlayer(victim, shooter);

        arrow.remove();

        event.setCancelled(true);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) {
            return;
        }

        EntityArrow nmsArrow = ((CraftArrow) arrow).getHandle();

        if (!nmsArrow.getCustomName().equals("ShootCraftProjectile")) {
            return;
        }

        arrow.remove();
    }
}
