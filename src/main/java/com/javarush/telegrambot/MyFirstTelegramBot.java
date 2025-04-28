package com.javarush.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static com.javarush.telegrambot.TelegramBotContent.*;

public class MyFirstTelegramBot extends MultiSessionTelegramBot {

    Properties props = new Properties();

    public MyFirstTelegramBot(String name, String token) {
        super(name, token);
    }

    @Override
    public void onUpdateEventReceived(Update updateEvent) {
        // TODO: основной функционал бота будем писать здесь
        String text = getMessageText().toLowerCase(Locale.ROOT);
        String button = getCallbackQueryButtonKey();
        switch (text) {
            case "/start" -> start();
            default -> defaultResponse();
        }
    }

    public void start() {
        sendPhotoMessageAsync("step_1_pic");
        sendTextMessageAsync(STEP_1_TEXT, Map.of("Взломать холодильник!", "hack_holodilnik_btn"));
        setUserGlory(0);
    }

    public void defaultResponse() {
        String button = getCallbackQueryButtonKey();
        switch (button) {
            case "hack_holodilnik_btn" -> {
                addUserGlory(20);
                sendPhotoMessageAsync("step_2_pic");
                sendTextMessageAsync(STEP_2_TEXT,
                        Map.of("Взять сосиску! +20 славы", "sosiska_btn",
                                "Стащить рыбку! +30 славы", "fish_btn",
                                "Скинуть банку с огруцами! +50 славы", "cucumber_btn"));
            }
            case "sosiska_btn" -> {
                addUserGlory(20);
                sendPhotoMessageAsync("step_3_pic");
                sendTextMessageAsync(STEP_3_TEXT, Map.of("Взломать пылесос!", "hack_pilesos_btn"));
            }
            case "fish_btn" -> {
                addUserGlory(30);
                sendPhotoMessageAsync("step_3_pic");
                sendTextMessageAsync(STEP_3_TEXT, Map.of("Взломать пылесос!", "hack_pilesos_btn"));
            }
            case "cucumber_btn" -> {
                addUserGlory(50);
                sendPhotoMessageAsync("step_3_pic");
                sendTextMessageAsync(STEP_3_TEXT, Map.of("Взломать пылесос!", "hack_pilesos_btn"));
            }
            case "hack_pilesos_btn" -> {
                addUserGlory(30);
                sendPhotoMessageAsync("step_4_pic");
                sendTextMessageAsync(STEP_4_TEXT,
                        Map.of("Ходить по помытому! +30 славы", "win_pilesos_btn",
                                "Кататься сверху! +30 славы", "win_pilesos_btn",
                                "Сделать лужу на пути пылесоса! +30 славы", "win_pilesos_btn"));
            }
            case "win_pilesos_btn" -> {
                addUserGlory(30);
                sendPhotoMessageAsync("step_5_pic");
                sendTextMessageAsync(STEP_5_TEXT, Map.of("Нацепить камеру на хвост", "go_pro_btn"));
            }
            case "go_pro_btn" -> {
                addUserGlory(40);
                sendPhotoMessageAsync("step_6_pic");
                sendTextMessageAsync(STEP_6_TEXT,
                        Map.of("Гонять голубей! +40 славы", "cat_vlog_btn",
                                "Драться с соседским котом! +40 славы", "cat_vlog_btn",
                                "Дразнить собаку! +40 славы", "cat_vlog_btn"));
            }
            case "cat_vlog_btn" -> {
                addUserGlory(40);
                sendPhotoMessageAsync("step_7_pic");
                sendTextMessageAsync(STEP_7_TEXT, Map.of("Прыгнуть на клавиатуру!", "hack_comp"));
            }
            case "hack_comp" -> {
                addUserGlory(50);
                sendPhotoMessageAsync("step_8_pic");
                sendTextMessageAsync(STEP_8_TEXT,
                        Map.of("Спать на мониторе! +50 славы", "final_btn",
                                "Смотреть видео с мокрыми кисками! +50 славы", "final_btn",
                                "Перегрызть кабель! +50 славы", "final_btn"));
            }
            case "final_btn" -> {
                sendPhotoMessageAsync("final_pic");
                sendTextMessageAsync(FINAL_TEXT, Map.of("Завтра будет завтра...", "next_day_btn"));
            }
            case "next_day_btn" -> start();
        }
        sendTextMessageAsync("_Твоя слава:_ *" + getUserGlory() + "*");
    }

    public static void main(String[] args) throws TelegramApiException {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File("src/config/token.ini")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final String BOT_NAME = props.getProperty("BOT_NAME");
        final String BOT_TOKEN = props.getProperty("BOT_TOKEN");
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot(BOT_NAME, BOT_TOKEN));
    }
}
