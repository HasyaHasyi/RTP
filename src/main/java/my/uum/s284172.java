package my.uum;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Telegram bot class that extends TelegramLongPollingBot for handling incoming messages and providing URL safety information.
 *
 * This class includes methods for bot username and token retrieval, message handling, and sending responses to users.
 */
public class s284172 extends TelegramLongPollingBot {
    /**
     * Declaring Bot Username, Token, and API Key
     */
    private static final String BOT_USERNAME = "s284172_bot";
    private static final String BOT_TOKEN = "6592549449:AAHOK02WE3GO6BNNhjlTDYpnZ_B8cdxdTPg";

    /**
     * Declaring Pattern for input recognition
     */
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?://)?(www\\.)?\\S+\\..{2,}.*$");

    /**
     * Returns the bot's username.
     *
     * @return The bot's username
     */
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    /**
     * Returns the bot's token.
     *
     * @return The bot's token
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Handles incoming updates from the Telegram bot.
     *
     * @param update The incoming update
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            System.out.println(message);
            long chatId = update.getMessage().getChatId();

            // If the message is "/start", send a welcome message
            if (message.equals("/start")) {
                sendMessage(chatId, "🌟 Welcome to SavySafey (Tech-Savvy Safety) Bot! 🌟\n\nI'm here to help you check the safety of any URL. Just send me the URL you want to look up, and I'll provide you with information about its security status. 🛡️🔍\n\nThis bot is part of Hasya's project! 💻");
            } else {
                // Check if the message is a valid URL
                Matcher matcher = URL_PATTERN.matcher(message);
                if (matcher.find()) {
                    try {
                        // Fetch data from the API and send the response to the user
                        String urlLookupResponse = UrlLookupAPI.fetchDataFromAPI(message);
                        System.out.println(urlLookupResponse);
                        String urlScamCheckerResponse = URLScamChecker.checkURL(message);
                        System.out.println(urlScamCheckerResponse);

                        // Combine both responses into a single message
                        String combinedResponse = "URL Lookup Info:\n" + urlLookupResponse + "\n\nScam Checker Info:\n" + urlScamCheckerResponse;

                        sendMessage(chatId, combinedResponse);
                    } catch (IOException e) {
                        // If there's an error fetching data from the API, send an error message to the user
                        sendMessage(chatId, "Error fetching data from API: " + e.getMessage());
                    }
                } else {
                    // If the message is not a valid URL, send a message asking the user to enter a valid URL
                    sendMessage(chatId, "Please enter a valid URL only!");
                }
            }
        }
    }

    /**
     * Private method to send a message to the user.
     *
     * @param chatId The chat ID to send the message to
     * @param text   The text of the message
     */
    private void sendMessage(long chatId, String text) {
        if (text == null || text.isEmpty()) {
            System.err.println("Error: Cannot send empty message");
            return;
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
