package lf.jaime.placeholders;

import lf.jaime.LightFly;
import lf.jaime.utils.PlayerTimeManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class LightFlyPlaceholderExpansion extends PlaceholderExpansion {
    private final LightFly plugin;

    public LightFlyPlaceholderExpansion(LightFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Jaime"; //
    }

    @Override
    public @NotNull String getIdentifier() {
        return "lightfly"; //
    }

    @Override
    public @NotNull String getVersion() {
        return Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("LightFly")).getDescription().getVersion(); //
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null;
        }

        if (params.equals("tempfly_time")) {
            PlayerTimeManager playerTimeManager = plugin.getPlayerTimeManager();
            return String.valueOf(playerTimeManager.getRemainingTime(player.getName()));
        }

        if (params.startsWith("tempfly_time_")) {
            String playerName = params.substring("tempfly_time_".length());
            PlayerTimeManager playerTimeManager = plugin.getPlayerTimeManager();
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                return null;
            }
            return String.valueOf(playerTimeManager.getRemainingTime(targetPlayer.getName()));
        }
        if (params.equals("isflying")) {
            return String.valueOf(player.isFlying());
        }
        if (params.startsWith("isflying_")) {
            String playerName = params.substring("isflying_".length());
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                return null;
            }
            return String.valueOf(targetPlayer.isFlying());
        }
        if (params.equals("flymode")) {
            return String.valueOf(player.getAllowFlight());
        }
        if (params.startsWith("flymode_")) {
            String playerName = params.substring("flymode_".length());
            Player targetPlayer = Bukkit.getPlayer(playerName);
            if (targetPlayer == null) {
                return null;
            }
            return String.valueOf(targetPlayer.getAllowFlight());
        }


        return null;

    }
}
