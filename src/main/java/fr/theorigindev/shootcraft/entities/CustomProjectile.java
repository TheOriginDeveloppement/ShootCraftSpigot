package fr.theorigindev.shootcraft.entities;
import fr.theorigindev.shootcraft.Loader;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class CustomProjectile {

    private final Player shooter;
    private EntityFireworks fireworkEntity;
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
        fireworkEntity = new EntityFireworks(world, startLoc.getX(), startLoc.getY(), startLoc.getZ(), null);

        fireworkEntity.setCustomName("ShootCraftProjectile");
        fireworkEntity.setCustomNameVisible(false);

        Vector direction = shooter.getEyeLocation().getDirection().normalize();
        double speedMultiplier = 3.0;
        fireworkEntity.motX = direction.getX() * speedMultiplier;
        fireworkEntity.motY = direction.getY() * speedMultiplier;
        fireworkEntity.motZ = direction.getZ() * speedMultiplier;

        world.addEntity(fireworkEntity);

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

                if (fireworkEntity.dead || fireworkEntity == null) {
                    stopTracking();
                    return;
                }

                Location loc = new Location(shooter.getWorld(),
                        fireworkEntity.locX, fireworkEntity.locY, fireworkEntity.locZ);


                if (loc.getBlock().getType() != Material.AIR &&
                        loc.getBlock().getType() != Material.WATER &&
                        loc.getBlock().getType() != Material.STATIONARY_WATER) {
                    fireworkEntity.die();
                    stopTracking();
                    return;
                }

                for (Entity nearby : loc.getWorld().getNearbyEntities(loc, 0.5, 0.5, 0.5)) {
                    if (nearby instanceof Player && nearby != shooter) {
                        Player victim = (Player) nearby;

                        shooter.getWorld().playEffect(loc, Effect.EXPLOSION_LARGE, 0);
                        shooter.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0F, false, false);

                        Loader.getInstance().getGameManager().getPlayerManager().killPlayer(victim, shooter);

                        fireworkEntity.die();
                        stopTracking();
                        return;
                    }
                }
            }
        };

        tracker.runTaskTimer(Loader.getInstance(), 0L, 1L);
        isRunning = true;
    }

    public void stopTracking() {
        if (tracker != null) {
            tracker.cancel();
            tracker = null;
        }
        isRunning = false;
    }

    public Player getShooter() {
        return shooter;
    }
}
