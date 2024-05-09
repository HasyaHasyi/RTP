package my.uum;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * This class represents the main entry point for the Telegram bot application.
 * It registers the s284172 bot with the Telegram Bots API and prints a success message.
 *
 */
public class Main {
    public static void main(String[] args) {
        try {
            // Create a new instance of the TelegramBotsApi
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Register the s284172 bot with the Telegram Bots API
            botsApi.registerBot(new s284172());

            // Print a message to the console indicating that the s284172 file has been successfully called
            System.out.println("S284172 file has successfully called");
        } catch (TelegramApiException e) {
            // If there's an exception during the bot registration process, print the stack trace
            e.printStackTrace();
        }
    }
}