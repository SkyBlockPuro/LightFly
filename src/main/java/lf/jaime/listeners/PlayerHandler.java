package lf.jaime.listeners;

import lf.jaime.LightFly;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerHandler implements Listener {

    private final LightFly plugin;

    public PlayerHandler(LightFly plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if (plugin.getPlayerTimeManager().getRemainingTime(player.getName()) > 0) {
            player.setAllowFlight(true);
            return;
        }
        if (player.hasPermission("lf.admin") ||
                player.hasPermission("lf.fly.*") ||
                player.hasPermission("lf.fly")) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (plugin.getPlayerTimeManager().getRemainingTime(player.getName()) > 0) {
            player.setAllowFlight(true);
            return;
        }
        if (player.hasPermission("lf.admin") ||
                player.hasPermission("lf.fly.*") ||
                player.hasPermission("lf.fly")) {
            player.setAllowFlight(true);
        }
    }

}
