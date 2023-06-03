package pl.pollub.android.app2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneViewModel extends AndroidViewModel {
    private PhoneRepository repository;
    private LiveData<List<Phone>> phoneList;

    public PhoneViewModel(@NonNull Application application){
        super(application);
        this.repository = new PhoneRepository(application);
        this.phoneList = this.repository.getPhoneList();
    }
    public LiveData<List<Phone>> getPhoneList(){
        return phoneList;
    }
    public void insert(Phone p){
        this.repository.insert(p);
    }
    public void update(Phone p){
        this.repository.update(p);
    }
    public void delete(Phone p){
        this.repository.delete(p);
    }
    public void deleteAll(){
        this.repository.deleteAll();
    }
}
