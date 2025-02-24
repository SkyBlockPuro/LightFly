package lf.jaime.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lf.jaime.LightFly;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerTimeManager {

    private static final LightFly plugin = LightFly.getInstance();
    private final ConcurrentHashMap<String, Integer> playerTimers = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File dataFile = new File(LightFly.getInstance().getDataFolder(), "playerTimes.yml");
    private BukkitTask timerTask;

    public PlayerTimeManager() {
        loadData();
        startTimerTask();
    }

    private void startTimerTask() {
        timerTask = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (String playerName : playerTimers.keySet()) {
                int timeLeft = playerTimers.get(playerName);
                Player player = Bukkit.getPlayer(playerName);
                if (player == null) return;
                try {
                    playerTimers.put(playerName, timeLeft - 1);
                    player.setAllowFlight(true);
                    if (timeLeft <= 1) {
                        playerTimers.remove(playerName);
                        player.setAllowFlight(false);
                    }
                } catch (NullPointerException e) {
                    playerTimers.remove(playerName);
                }
            }
        }, 0, 20L);
    }

    public void addPlayer(String name, int seconds) {
        playerTimers.put(name, seconds);
    }

    public int getRemainingTime(String playername) {
        if (playerTimers.containsKey(playername)) {
            return playerTimers.get(playername);
        }
        return 0;
    }

    public void shutdown() {
        timerTask.cancel();
        saveData();
    }

    private void saveData() {
        try {
            objectMapper.writeValue(dataFile, playerTimers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        if (dataFile.exists()) {
            try {
                playerTimers.putAll(objectMapper.readValue(dataFile, ConcurrentHashMap.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
