package org.example.bot.service;

import lombok.AllArgsConstructor;
import org.example.bot.commands.Commands;
import org.example.bot.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class MovieBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final Map<Long, Boolean> chatIdToAwaitingTitle = new HashMap<>();

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            Commands commands = new Commands(update);
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (Boolean.TRUE.equals(chatIdToAwaitingTitle.get(chatId))) {
                sendMessage(chatId, commands.findMovie(messageText));
                chatIdToAwaitingTitle.remove(chatId);
                return;
            }

            switch (messageText) {
                case "Знакомство" -> sendMessage(chatId, commands.startCommand());
                case "Найти фильм" -> {
                    sendMessage(chatId, "Введите название фильма");
                    chatIdToAwaitingTitle.put(chatId, true);
                }
                default -> sendMessage(chatId, commands.unknownCommand(messageText));
            }

        }

    }


    private void sendMessage(long chatId, String textToSend) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(textToSend);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Знакомство");
        row1.add("Найти фильм");

        keyboard.add(row1);
        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
