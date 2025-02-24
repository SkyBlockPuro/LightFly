package lf.jaime;

import lf.jaime.commands.Fly;
import lf.jaime.commands.MainCommand;
import lf.jaime.commands.TempFly;
import lf.jaime.files.ConfigManager;
import lf.jaime.files.MessagesManager;
import lf.jaime.listeners.PlayerHandler;
import lf.jaime.placeholders.LightFlyPlaceholderExpansion;
import lf.jaime.utils.MessageUtils;
import lf.jaime.utils.PlayerTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class LightFly extends JavaPlugin {
    private MessagesManager messagesManager;
    private ConfigManager configManager;
    private PlayerTimeManager playerTimeManager;
    private static LightFly instance;

    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(instance);
        messagesManager = new MessagesManager(instance, configManager.getLang() + ".yml");
        playerTimeManager = new PlayerTimeManager();
        registerCommands();
        registerListeners();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new LightFlyPlaceholderExpansion(this).register();
            Bukkit.getConsoleSender().sendMessage(MessageUtils.getMessage(messagesManager.getPrefix() + "&aRegistered placeholders!"));
        }

    }

    public void onDisable() {
        playerTimeManager.shutdown();
    }

    public static LightFly getInstance() {
        return instance;
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("fly")).setExecutor(new Fly(this));
        Objects.requireNonNull(getCommand("lightfly")).setExecutor(new MainCommand(this));
        Objects.requireNonNull(getCommand("tempfly")).setExecutor(new TempFly(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerHandler(this), this);
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerTimeManager getPlayerTimeManager() {
        return playerTimeManager;
    }
}
