package pl.pollub.android.app2_2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView phoneListRv;
    private PhoneAdaper adaper;
    private PhoneViewModel phoneViewModel;
    private ActivityResultLauncher<Intent> launcher;

    private FloatingActionButton addPhoneFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.phoneListRv = findViewById(R.id.phone_list_rv);
        this.addPhoneFab = findViewById(R.id.add_phone_fab);
        this.addPhoneFab.setOnClickListener(view -> newPhone());
        this.adaper = new PhoneAdaper(this.getLayoutInflater());
        this.adaper.setOnPhoneClickListener(phone -> editSelectedPhone(phone));
        this.phoneListRv.setAdapter(this.adaper);
        this.phoneListRv.setLayoutManager(new LinearLayoutManager(this));
        this.phoneViewModel = new ViewModelProvider(this).get(PhoneViewModel.class);
        this.phoneViewModel.getPhoneList().observe(this, phones -> {
            this.adaper.setPhoneList(phones);
        });
        this.launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result ->  insertOrUpdatePhone(result));

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Phone phone = MainActivity.this.adaper.getPhoneList().get(viewHolder.getAdapterPosition());
                MainActivity.this.phoneViewModel.delete(phone);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(this.phoneListRv);
    }

    private void editSelectedPhone(Phone phone) {
         Bundle bundle = new Bundle();
         bundle.putString(PhoneActivity.MANUFACTURER_KEY,phone.getManufacturer());
         bundle.putString(PhoneActivity.MODEL_KEY,phone.getModel());
         bundle.putString(PhoneActivity.ANDROID_VERSION_KEY,phone.getAndroidVersion());
         bundle.putString(PhoneActivity.WEB_SITE_KEY,phone.getWebSite());
         bundle.putLong(PhoneActivity.PHONE_ID_KEY, phone.getId());
         Intent intent = new Intent(this, PhoneActivity.class);
         intent.putExtras(bundle);
         this.launcher.launch(intent);
    }

    private void newPhone() {
        Intent intent = new Intent(this, PhoneActivity.class);
        this.launcher.launch(intent);
    }

    private void insertOrUpdatePhone(ActivityResult result) {
        //if(result.getResultCode()==RESULT_OK){
            Bundle bundle = result.getData().getExtras();
            String manufactrer = bundle.getString(PhoneActivity.MANUFACTURER_KEY);
            String model = bundle.getString(PhoneActivity.MODEL_KEY);
            String androidVersion = bundle.getString(PhoneActivity.ANDROID_VERSION_KEY);
            String webSite = bundle.getString(PhoneActivity.WEB_SITE_KEY);
            Long idPhone = bundle.getLong(PhoneActivity.PHONE_ID_KEY);
            if(idPhone == 0){
                this.phoneViewModel.insert(new Phone(manufactrer,model,androidVersion,webSite));
            }
            else{
                this.phoneViewModel.update(new Phone(idPhone, manufactrer,model,androidVersion,webSite));
            }
        //}


    }
}