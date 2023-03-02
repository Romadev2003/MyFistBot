package org.example;

import org.apache.commons.io.FileUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

List<TelegramUser> users=new ArrayList<>();
    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        Message message=update.getMessage();
        TelegramUser user=saveUser(chatId);

        if(update.hasMessage()){
            String text = message.getText();
            if (message.hasText()){
                    if (message.getText().equals("/start")){
                        try {
                            SendMessage("Assalomu alaykum ,Mening birinchi botimga xush Kelibbsiz!!!!!\n",user.getChatId());
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            SendMessage sendMessage=new SendMessage();
                            sendMessage.setChatId(user.getChatId());
                            sendMessage.setText( "       Tanlang:");
                            InlineKeyboardMarkup inlineKeyboardMarkup=new InlineKeyboardMarkup();
                            List<InlineKeyboardButton> colums =new ArrayList<>();

                            InlineKeyboardButton inlineKeyboardButtonk=new InlineKeyboardButton();
                            inlineKeyboardButtonk.setText("kirish");
                            inlineKeyboardButtonk.setCallbackData("login");

                            InlineKeyboardButton inlineKeyboardButtonr=new InlineKeyboardButton();
                            inlineKeyboardButtonr.setText("Ro`yxatdan o`tish");
                            inlineKeyboardButtonr.setCallbackData("logup");

                            List<List<InlineKeyboardButton>> row =new ArrayList<>();

                            row.add(colums);

                            colums.add(inlineKeyboardButtonk);
                            colums.add(inlineKeyboardButtonr);
                            inlineKeyboardMarkup.setKeyboard(row);
                            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }


                    }
                }
        }
        if (update.hasCallbackQuery()) {
            String data=update.getCallbackQuery().getData();
            System.out.println(11);
            try {
                SendMessage("Okay",user.getChatId());
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            if (data.equals("login")){
                try {
                    SendMessage("---kIRISH---",user.getChatId());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            else  if (data.equals("logup")){
                try {
                    SendMessage("---Ro`yaxtdan o`tish---",user.getChatId());
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private TelegramUser saveUser(String chatId) {
        for (TelegramUser user : users) {
            if (user.getChatId().equals(chatId)){
                return user;
            }
        }
        TelegramUser user=new TelegramUser();
        user.setChatId(chatId);
        users.add(user);
        return user;
    }



    private void saveFileToFolder(String fileId,String fileName) throws Exception {
        GetFile getFile=new GetFile(fileId);
        File tgFile = execute(getFile);
        String fileUrl = tgFile.getFileUrl(getBotToken());
        System.out.println(fileUrl);
        URL url=new URL(fileUrl);
        InputStream inputStream = url.openStream();

        FileUtils.copyInputStreamToFile(inputStream, new java.io.File(fileName));
    }
    private   void SendMessage(String text,String chatId) throws TelegramApiException {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        execute(sendMessage);

    }
    @Override
    public String getBotUsername() {
        return "uzromabot";
    }
    @Override
    public String getBotToken() {
        return "6250480235:AAEY_YYwePc0Xcuw9hFoEePu0z0s3Sdl7M0";
    }
}
