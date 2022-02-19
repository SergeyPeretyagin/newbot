package TimeRepeat;
import Bot.Bot;
import Service.PDFConverter;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Repeater {

   public static void startTimer() {
        Calendar startCalendar = Calendar.getInstance();
        long day = 86_400_000L;
        int sec = 10000;

//        if(Calendar.getInstance().getTime().getHours()<7){
       if(Calendar.getInstance().getTime().getHours()<=23 && Calendar.getInstance().getTime().getMinutes()<=57){
            startCalendar.set(Calendar.HOUR_OF_DAY,22);
            startCalendar.set(Calendar.MINUTE,59);
            startCalendar.set(Calendar.SECOND,00);
        }else {
            startCalendar.set(Calendar.DATE,Calendar.getInstance().getTime().getDate()+1);
            startCalendar.set(Calendar.HOUR_OF_DAY,17);
            startCalendar.set(Calendar.MINUTE,00);
            startCalendar.set(Calendar.SECOND,00);
        }
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            File file;
            @Override
            public void run() {
                System.out.println("TimerTask начал свое выполнение в:" + new Date());
                file = PDFConverter.createPDF();
                Bot bot = new Bot(new DefaultBotOptions());
                try {
                    bot.sendFile(file);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                System.out.println("TimerTask закончил свое выполнение в:" + new Date());
            }
        };
        timer.scheduleAtFixedRate(timerTask,startCalendar.getTime(),sec );
       try {
           Thread.currentThread().join();
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
    }

}
