package Bot;

//import TimeRepeat.Repeater;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot{
    private static String BOT_TOKEN = "5145412598:AAHikpj4anWuxM4g4nPped7AOyuZQPEdyJo";
    private static String BOT_NAME = "@wodnyi_bot";
    //String name = "1399019417";
    private static List<String> chatIdList = new ArrayList<>();


    public Bot(DefaultBotOptions defaultBotOptions) {
    }

//    public static void main(String[] args) throws TelegramApiException {
    public static void start() throws TelegramApiException {
        Bot bot = new Bot(new DefaultBotOptions());
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
//        Repeater.startTimer();
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void createButtons(Message message){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Да, давай его сюда");
        inlineKeyboardButton1.setCallbackData("Yes");
        System.out.println(inlineKeyboardButton1.getCallbackData());
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Не, я подужду 17:30");
        inlineKeyboardButton2.setCallbackData("No");
        List<InlineKeyboardButton>buttons=new ArrayList<>();
        buttons.add(inlineKeyboardButton1);
        buttons.add(inlineKeyboardButton2);
        List<List<InlineKeyboardButton>>lists=new ArrayList<>();
        lists.add(buttons);
        inlineKeyboardMarkup.setKeyboard(lists);
        try{
            execute(SendMessage.builder().text("Хочешь получить отчет прямо сейчас").chatId(message.getChatId().toString())
                    .replyMarkup(InlineKeyboardMarkup.builder().keyboard(lists).build()).build());
        }catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasCallbackQuery()){
            try {
                handleCallback(update.getCallbackQuery());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        Message message = update.getMessage();
        if (message.getText().equals("/start")){
            helloMessage(message);
        }
        createButtons(message);
    }

    private void handleCallback(CallbackQuery callbackQuery) throws TelegramApiException {
        String chatId = callbackQuery.getMessage().getChatId().toString();
        if (callbackQuery.getData().equals("Yes")) {
            execute(SendMessage.builder().chatId(chatId).text("Сейчас получишь").build());
//            File file = PDFConverter.createPDF();
//            File file = new File("D:\\Example\\newPostgresDemo\\userManagementApplication\\TeLe\\newPDF.pdf");
            File file = new File("/opt/tomcat/apache-tomcat-9.0.58/webapps/telebot/WEB-INF/source/newPDF.pdf");
//            InputFile inputFile = new InputFile(file);
//            execute(SendDocument.builder().chatId(chatId).document(inputFile).build());
            if (file.isFile())
            sendFile(file);
        }
        if (callbackQuery.getData().equals("No")) {
            execute(SendMessage.builder().chatId(chatId).text("Правильно, лучше пусть само придет").build());
        }
    }

    public void helloMessage(Message message){
            try {
                execute(SendMessage.builder().chatId(message.getChatId().toString()).text("Добро пожаловать в красную команду").build());
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            if (!chatIdList.contains(message.getText())){
                chatIdList.add(message.getChatId().toString());
            }
    }

    public void sendFile(File file) throws TelegramApiException {
        InputFile inputFile = new InputFile(file);
        for (String str:
             chatIdList) {
            execute(SendDocument.builder().chatId(str).document(inputFile).build());
        }

    }
}

















//        List<List<InlineKeyboardButton>>buttons=new ArrayList<>();
//        buttons.add(Arrays.asList(InlineKeyboardButton.builder().text("Да, давай его сюда").callbackData("BBB").build(),
//        InlineKeyboardButton.builder().text("Не, я подужду 17:30").callbackData("DDD").build()));
//        try {
//            execute(SendMessage.builder().text("Хочешь получить отчет прямо сейчас").chatId(message.getChatId().toString())
//            .replyMarkup(InlineKeyboardMarkup.builder().keyboard(buttons).build()).build());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
