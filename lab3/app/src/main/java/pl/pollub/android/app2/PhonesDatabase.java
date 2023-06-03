package pl.pollub.android.app2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Phone.class}, version = 1, exportSchema = false)
public abstract class PhonesDatabase extends RoomDatabase
{
    //abstrakcyjna metoda zwracająca DAO
    public abstract PhoneDAO phoneDao();
    //implementacja singletona
    private static volatile PhonesDatabase INSTANCE;
    static PhonesDatabase getDatabase(final Context context) {
        //tworzymy nowy obiekt tylko gdy żaden nie istnieje
        if (INSTANCE == null) {
            synchronized (PhonesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), PhonesDatabase.class, "nazwa_bazy")
                    //ustawienie obiektu obsługującego
                    //zdarzenia związane z bazą
                            .addCallback(sRoomDatabaseCallback)
                    //najprostsza migracja – skasowanie
                    //i utworzenie bazy od nowa
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    //usługa wykonawcza do wykonywania zadań w osobnym wątku
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        //uruchamiane przy tworzeniu bazy (pierwsze
        //uruchomienie aplikacji, gdy baza nie istnieje)

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                PhoneDAO dao = INSTANCE.phoneDao();
                Phone[] phones = {new Phone("Huawei", "Thunder", "10.0.1", "huawei.com"), new Phone("Google", "Pixel", "11.0.1", "google.com"), new Phone("X", "Y", "1.1.1", "bing.com")};
                for(Phone p : phones){
                    dao.insert(p);
                }
            });
        }
    };
}
