package pl.pollub.android.app2_2;


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
    {    public abstract PhoneDao phoneDao();
        private static volatile PhonesDatabase INSTANCE;
        static PhonesDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (PhonesDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PhonesDatabase.class,"phones_database")
                                .addCallback(sRoomDatabaseCallback)
                                .fallbackToDestructiveMigration()
                                .build();}}}  return INSTANCE;
        }
        private static final int NUMBER_OF_THREADS = 4;
        static final ExecutorService databaseWriteExecutor =  Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        private static RoomDatabase.Callback  sRoomDatabaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                databaseWriteExecutor.execute(() -> {
                    PhoneDao dao = INSTANCE.phoneDao();
                    Phone[] phones = {new Phone("google","Pixel 6a","13","google.com")};
                    for(Phone p : phones){
                        dao.insert(p);
                    }
                });
            }
        };
    }