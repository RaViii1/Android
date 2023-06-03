package pl.pollub.android.app2;

import android.app.Application;
import android.provider.Contacts;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneRepository {
    private PhoneDAO dao;
    LiveData<List<Phone>> phoneList;

    public PhoneRepository(Application application) {
        PhonesDatabase database = PhonesDatabase.getDatabase(application);
        this.dao = database.phoneDao();
        this.phoneList = dao.findAllPhones();
    }
    public LiveData<List<Phone>> getPhoneList(){
        return phoneList;
    }
    public void insert(Phone p){
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.dao.insert(p));
        //this.dao.insert(p);
    }
    public void update(Phone p){
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.dao.update(p));
        //this.dao.update(p);
    }
    public void delete(Phone p){
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.dao.delete(p));
    }
    public void deleteAll(){
        PhonesDatabase.databaseWriteExecutor.execute(() -> this.dao.deleteAll());
    }
}
