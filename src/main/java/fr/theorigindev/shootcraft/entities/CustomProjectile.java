package fr.theorigindev.shootcraft.entities;

import fr.theorigindev.shootcraft.Loader;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class CustomProjectile {

    private static final Map<EntityArrow, Player> projectileOwners = new HashMap<>();

    private final Player shooter;
    private EntityArrow entityArrow;
    private boolean isRunning;
    private BukkitRunnable tracker;

    public CustomProjectile(Player shooter) {
        this.shooter = shooter;
        this.isRunning = false;
    }

    public void launch() {
        if (isRunning) {
            return;
        }

        WorldServer world = ((CraftWorld) shooter.getWorld()).getHandle();
        Location startLoc = shooter.getEyeLocation();
        Vector startVec = startLoc.getDirection().normalize();
        startLoc.add(startVec.getX() * 1.5, startVec.getY() * 1.5, startVec.getZ() * 1.5);
        entityArrow = new EntityArrow(world, startLoc.getX(), startLoc.getY(), startLoc.getZ());

        entityArrow.setCustomName("ShootCraftProjectile");
        entityArrow.setCustomNameVisible(false);
        entityArrow.setInvisible(true);

        Vector direction = shooter.getEyeLocation().getDirection().normalize();
        double speedMultiplier = 3.0;
        entityArrow.motX = direction.getX() * speedMultiplier;
        entityArrow.motY = direction.getY() * speedMultiplier;
        entityArrow.motZ = direction.getZ() * speedMultiplier;


        world.addEntity(entityArrow);
        projectileOwners.put(entityArrow, shooter);

        startTracking();
    }

    private void startTracking() {
        if (isRunning) {
            stopTracking();
        }

        tracker = new BukkitRunnable() {
            @Override
            public void run() {
                if (!isRunning) {
                    return;
                }

                if (entityArrow.dead || entityArrow == null) {
                    stopTracking();
                    return;
                }
                Location loc = new Location(shooter.getWorld(),
                        entityArrow.locX, entityArrow.locY, entityArrow.locZ);

                shooter.getWorld().playEffect(loc, Effect.FIREWORKS_SPARK, 0);
            }
        };

        tracker.runTaskTimer(Loader.getInstance(), 0L, 1L);
        isRunning = true;
    }

    public void stopTracking() {
        if (tracker != null) {
            tracker.cancel();
            tracker = null;
            projectileOwners.remove(entityArrow);
        }
        isRunning = false;
    }

    public Player getShooter() {
        return shooter;
    }

    public static Player getShooter(EntityArrow arrow) {
        return projectileOwners.get(arrow);
    }
}