package lf.jaime.commands;

import lf.jaime.LightFly;
import lf.jaime.files.MessagesManager;
import lf.jaime.utils.MessageUtils;
import lf.jaime.utils.PlayerTimeManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TempFly implements CommandExecutor {
    private final LightFly plugin;

    public TempFly(LightFly plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        MessagesManager messages = plugin.getMessagesManager();

        if (!sender.hasPermission("lf.admin") &&
                !sender.hasPermission("lf.fly.*") &&
                !sender.hasPermission("lf.fly.temp")) {

            sender.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getNoPermission()));
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getBadUsageTempfly()));
            return true;
        }
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getUnknownPlayer()
                    .replace("%player%", args[0])));
            return true;
        }

        boolean preventFlyInCreative = plugin.getConfigManager().isPreventInGamemode();
        if (player.getGameMode() == GameMode.CREATIVE && preventFlyInCreative) {
            player.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getGamemodeNotAllowed()));
            return true;
        }
        int seconds = 0;
        try {
            seconds = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getInvalidNumber()));
            return true;
        }
        PlayerTimeManager playerTimeManager = plugin.getPlayerTimeManager();
        playerTimeManager.addPlayer(player, seconds);
        sender.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getTempFlySuccess()
                .replace("%player%", player.getName()).replace("%time%", String.valueOf(seconds))));

        if (plugin.getConfigManager().isSendTempFlyMessage()) {
            player.sendMessage(MessageUtils.getMessage(messages.getPrefix() + messages.getTempFlyMessage()
                    .replace("%time%", String.valueOf(seconds))));
        }
        return true;
    }

}
