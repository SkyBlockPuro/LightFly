package lf.jaime.utils;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerTimeManager {
    private final ConcurrentHashMap<Player, Integer> playerTimers = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public PlayerTimeManager() {
        startTimerTask();
    }

    private void startTimerTask() {
        scheduler.scheduleAtFixedRate(() -> {
            for (Player player : playerTimers.keySet()) {
                int timeLeft = playerTimers.get(player);
                try {
                    playerTimers.put(player, timeLeft - 1);
                    player.setAllowFlight(true);
                    if (timeLeft <= 1) {
                        playerTimers.remove(player);
                        player.setAllowFlight(false);
                    }
                } catch (NullPointerException e) {
                    playerTimers.remove(player);
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void addPlayer(Player player, int seconds) {
        playerTimers.put(player, seconds);
    }

    public int getRemainingTime(Player player) {
        if (playerTimers.containsKey(player)) {
            return playerTimers.get(player);
        }
        return 0;
    }

    public void shutdown() {
        scheduler.shutdown();
    }

    public ConcurrentHashMap<Player, Integer> getPlayerTimers() {
        return playerTimers;
    }
}
