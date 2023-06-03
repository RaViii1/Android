package pl.pollub.android.app4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private Button pobierzInfoBt;
    private static Button pobierzPlikBt;
    private ProgressBar postepPB;
    private TextView pobranoBajtowTv;
    private  TextView adresTv;
    private TextView rozmiarPlikuTv;
    private TextView typPlikuTv;
    private static boolean pobieranie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.pobierzInfoBt = findViewById(R.id.pobierz_info_bt);
        pobierzInfoBt.setOnClickListener(view -> pobierzInfo());
        this.pobierzPlikBt = findViewById(R.id.pobierz_plik_bt);
        this.adresTv = findViewById(R.id.adres_et);
        this.rozmiarPlikuTv = findViewById(R.id.rozmiar_pliku_et);
        this.typPlikuTv = findViewById(R.id.typ_pliku_et);
        pobierzPlikBt.setOnClickListener(view -> DownloadService.uruchomUsluge(this,this.adresTv.getText().toString()));
        if(pobieranie){
            this.pobierzPlikBt.setAlpha(0.5f);
            this.pobierzPlikBt.setClickable(false);
        }else{
            this.pobierzPlikBt.setAlpha(1f);
            this.pobierzPlikBt.setClickable(true);
        }
        this.postepPB = findViewById(R.id.postep_pb);
        this.pobranoBajtowTv = findViewById(R.id.pobrano_bajtow_et);
    }

    private void pobierzInfo() {
        //po  nowemu
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {

            FileInfo fileInfo;
            try {
                URL adres = new URL(adresTv.getText().toString());
                HttpURLConnection connection = (HttpURLConnection) adres.openConnection();
                connection.setRequestMethod("GET");
                fileInfo = new FileInfo();
                fileInfo.fileSize = connection.getContentLength();
                fileInfo.FileType = connection.getContentType();
                setFileInfo(fileInfo);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public class FileInfo{
        public int fileSize;
        public String FileType;
    }

    private void setFileInfo(FileInfo fileInfo) {
        TextView fileSizeEt = findViewById(R.id.rozmiar_pliku_et);
        TextView fileTypeEt = findViewById(R.id.typ_pliku_et);
        fileSizeEt.setText(Integer.toString(fileInfo.fileSize));
        fileTypeEt.setText(fileInfo.FileType);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ProgressInfo progressInfo = intent.getParcelableExtra(DownloadService.PROGRESS_INFO_KEY);
            MainActivity.this.postepPB.setProgress(progressInfo.progressValue());
            MainActivity.this.pobranoBajtowTv.setText(Integer.toString(progressInfo.progressValue()));
            MainActivity.this.rozmiarPlikuTv.setText(Integer.toString(progressInfo.getFilesize()));
            MainActivity.this.typPlikuTv.setText(progressInfo.getFileType());
        }
    };
    protected void onResume(){
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter(DownloadService.POWIADOMIENIE));
    }
    @Override
    protected void onPause(){
        LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver);
        super.onPause();
    }
    public static void disableDownload(){
        pobierzPlikBt.setAlpha(0.5f);
        pobierzPlikBt.setClickable(false);
        pobieranie = true;
    }
    public static void enableDownload(){
        pobierzPlikBt.setAlpha(1f);
        pobierzPlikBt.setClickable(true);
        pobieranie = false;
    }
}