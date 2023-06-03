package pl.pollub.android.app4;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends IntentService {

    private static final String PARAMETR1 = "ADRES";
    private static final int ROZMIAR_BLOKU = 32767;
    private static final String ID_KANALU = "KANAL";
    private static final int ID_POWIADOMIENIA = 1;
    public final static String POWIADOMIENIE = "com.example.intent_service.odbiornik";
    public static final String PROGRESS_INFO_KEY = "PROGRESS_INFO_KEY";
    private ProgressInfo progressInfo = new ProgressInfo();
    private NotificationManager mManagerPowiadomien;

    public DownloadService() {
        super("DownloadService");
    }

    public static void uruchomUsluge(Context context, String adres) {
        Intent zamiar = new Intent(context, DownloadService.class);
        zamiar.putExtra(PARAMETR1, adres);
        context.startService(zamiar);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        this.mManagerPowiadomien = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        przygotujKanalPowiadomien();
        //startForeground(ID_POWIADOMIENIA, utworzPowiadomienie());
        if (intent != null) {
            final String adres = intent.getStringExtra(PARAMETR1);
            MainActivity.disableDownload();
            wykonajZadanie(adres);
        } else {
            Log.e("intent_service", "nieznana akcja");
        }
        MainActivity.enableDownload();
        Log.d("intent_service", "usługa wykonała zadanie");
    }

    private void wykonajZadanie(String adres){
        FileOutputStream strumienDoPliku = null;
        HttpURLConnection polaczenie = null;
        try {
            URL url = new URL(adres);
            File plikRoboczy = new File(url.getFile());
            polaczenie =(HttpURLConnection) url.openConnection();
            this.progressInfo = new ProgressInfo();
            this.progressInfo.setFilesize(polaczenie.getContentLength());
            this.progressInfo.setFileType(polaczenie.getContentType());
            File plikWyjsciowy = new File(getBaseContext().getFilesDir().getPath() + File.separator + plikRoboczy.getName());
            if (plikWyjsciowy.exists()) {
                plikWyjsciowy.delete();
            }
            DataInputStream czytnik = new  DataInputStream(polaczenie.getInputStream());
            strumienDoPliku = new  FileOutputStream(plikWyjsciowy.getPath());
            byte bufor[] = new byte[ROZMIAR_BLOKU];
            int pobrano = czytnik.read(bufor, 0, ROZMIAR_BLOKU);
            while (pobrano != -1)
            {
                strumienDoPliku.write(bufor, 0, pobrano);
                this.progressInfo.increaseDownloadBytes(pobrano);
                this.mManagerPowiadomien.notify(ID_POWIADOMIENIA, utworzPowiadomienie());
                wyslijBroadcast();
                Log.d("DownloadService","pobrano bajtów: " +this.progressInfo.getDownloadBytes());
                pobrano = czytnik.read(bufor, 0, ROZMIAR_BLOKU);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
           if(strumienDoPliku != null){
               try {
                   strumienDoPliku.close();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
           if(polaczenie != null){
               polaczenie.disconnect();
           }
        }
    }
    private void przygotujKanalPowiadomien() {
        this.mManagerPowiadomien = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //Android 8/Oreo wymaga kanału powiadomień
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            //tworzymy kanał – nadajemy mu jakieś ID (stała typu String)
            NotificationChannel kanal = new NotificationChannel(ID_KANALU, name, NotificationManager.IMPORTANCE_LOW);
            //tworzymy kanał w menadżerze powiadomień
            this.mManagerPowiadomien.createNotificationChannel(kanal);
        }
    }
    private Notification utworzPowiadomienie() {
        Intent intencjaPowiadomienia = new Intent(this, MainActivity.class);
        //intencjaPowiadomienia.putExtra(//...
        TaskStackBuilder budowniczyStosu = TaskStackBuilder.create(this);

        budowniczyStosu.addParentStack(MainActivity.class);
        budowniczyStosu.addNextIntent(intencjaPowiadomienia);
        PendingIntent intencjaOczekujaca = budowniczyStosu.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder budowniczyPowiadomien = new Notification.Builder(this);

        budowniczyPowiadomien.setContentTitle(getString(R.string.powiadomienie_tytul))
                .setProgress(100, this.progressInfo.progressValue(), false)
                .setContentIntent(intencjaOczekujaca)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_HIGH);
        if(this.progressInfo.isDownloadComplete())
        {
            budowniczyPowiadomien.setOngoing(false);
        } else {
            budowniczyPowiadomien.setOngoing(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            budowniczyPowiadomien.setChannelId(ID_KANALU);
        }
        return budowniczyPowiadomien.build();
    }


    private void wyslijBroadcast() {
        Intent zamiar = new Intent(POWIADOMIENIE);
        zamiar.putExtra(PROGRESS_INFO_KEY, this.progressInfo);
        LocalBroadcastManager.getInstance(this).sendBroadcast(zamiar);
    }

}
