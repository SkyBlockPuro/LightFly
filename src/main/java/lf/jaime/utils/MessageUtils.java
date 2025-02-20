package lf.jaime.utils;

import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtils {
    public static String getMessage(String text) {
        text = text.replaceAll("&#", "#");

        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(text);

        if (text.length() > 0) {
            while (matcher.find()) {
                String color = text.substring(matcher.start(), matcher.end());
                try {
                    text = text.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                } catch (NoSuchMethodError e) {
                    text = text.replace(color, "");
                }

                matcher = pattern.matcher(text);

            }

        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

}
