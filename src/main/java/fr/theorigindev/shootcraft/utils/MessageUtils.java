package fr.theorigindev.shootcraft.utils;

import fr.theorigindev.shootcraft.Loader;

public class MessageUtils {

    private static final String FORMAT = "§6[ShootCraft] §e%s";

    public static void broadcast(String message){
        Loader.getInstance().getServer().broadcastMessage(String.format(FORMAT, message));
    }
}
