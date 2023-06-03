package pl.pollub.android.app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneActivity extends AppCompatActivity {
    public static final String MANUFACTURER_KEY = "MANUFACTURER_KEY";
    public static final String MODEL_KEY = "MODEL_KEY";
    public static final String ANDROIDVER_KEY = "ANDROIDVER_KEY";
    public static final String WEBSITE_KEY = "WEBSITE_KEY";
    public static final String PHONE_ID_KEY = "PHONE_ID_KEY";
    private EditText manufacturer_et;
    private EditText model_et;
    private EditText androidVersion_et;
    private EditText website_et;
    private boolean isManufacturerEtOk;
    private boolean isModel_etOk;
    private boolean isAndroidVersion_etOk;
    private boolean isWebsite_etOk;
    private Button saveBt;
    private Button cancelBT;
    private Button websiteBT;
    private Long phoneId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        this.manufacturer_et = findViewById(R.id.manufacturer_et);
        this.manufacturer_et.setOnFocusChangeListener((view, b) -> {
            sprawdzSaveWidoczny();
            if(manufacturer_et.getText().toString().isEmpty()){
                manufacturer_et.setError("Fill this field!");
                isManufacturerEtOk = false;
            } else isManufacturerEtOk = true;
        });
        this.model_et = findViewById(R.id.model_et);
        this.model_et.setOnFocusChangeListener((view, b) -> {
            sprawdzSaveWidoczny();
            if(model_et.getText().toString().isEmpty()){
                model_et.setError("Fill this field!");
                isModel_etOk = false;
            } else isModel_etOk = true;
        });
        this.androidVersion_et = findViewById(R.id.android_et);
        this.androidVersion_et.setOnFocusChangeListener((view, b) -> {
            sprawdzSaveWidoczny();
            if(androidVersion_et.getText().toString().isEmpty()){
                androidVersion_et.setError("Fill this field!");
                isAndroidVersion_etOk = false;
            } else isAndroidVersion_etOk = true;
        });
        this.website_et = findViewById(R.id.website_et);
        this.website_et.setOnFocusChangeListener((view, b) -> {
            sprawdzSaveWidoczny();
            if(website_et.getText().toString().isEmpty()){
                website_et.setError("Fill this field!");
                isWebsite_etOk = false;
            } else isWebsite_etOk = true;
        });

        this.saveBt = findViewById(R.id.save_bt);
        this.saveBt.setOnClickListener(view -> saveNewPhone());
        this.cancelBT = findViewById(R.id.cancel_bt);
        this.cancelBT.setOnClickListener(view -> cancelAction());
        this.websiteBT = findViewById(R.id.website_bt);
        this.websiteBT.setOnClickListener(view -> openWebsite());
        saveBt.setBackgroundColor(ContextCompat.getColor(this, R.color.custom_color));
        cancelBT.setBackgroundColor(ContextCompat.getColor(this, R.color.custom_color));
        websiteBT.setBackgroundColor(ContextCompat.getColor(this, R.color.custom_color));
        saveBt.setTextColor(ContextCompat.getColor(this, R.color.black));
        cancelBT.setTextColor(ContextCompat.getColor(this, R.color.black));
        websiteBT.setTextColor(ContextCompat.getColor(this, R.color.black));
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                this.manufacturer_et.setText(bundle.getString(MANUFACTURER_KEY));
                isManufacturerEtOk = true;
                this.model_et.setText(bundle.getString(MODEL_KEY));
                isModel_etOk = true;
                this.androidVersion_et.setText(bundle.getString(ANDROIDVER_KEY));
                isAndroidVersion_etOk = true;
                this.website_et.setText(bundle.getString(WEBSITE_KEY));
                isWebsite_etOk = true;
                this.phoneId = bundle.getLong(PHONE_ID_KEY);
            }
            this.saveBt.setAlpha(1f);
            this.saveBt.setClickable(true);
        }
        sprawdzSaveWidoczny();
    }

    private void openWebsite() {
        String url = this.website_et.getText().toString();
        if(url.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Niepoprawny url", Toast.LENGTH_LONG).show();
        } else {
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://" + url;

            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            startActivity(browserIntent);
        }
    }

    private void cancelAction() {
        Intent intent = getIntent();
        setResult(RESULT_CANCELED, intent);
        this.finish();
    }

    private void saveNewPhone() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString(this.MANUFACTURER_KEY, this.manufacturer_et.getText().toString());
        bundle.putString(this.MODEL_KEY, this.model_et.getText().toString());
        bundle.putString(this.ANDROIDVER_KEY, this.androidVersion_et.getText().toString());
        bundle.putString(this.WEBSITE_KEY, this.website_et.getText().toString());
        bundle.putLong(this.PHONE_ID_KEY, this.phoneId);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        this.finish();
    }
    private void sprawdzSaveWidoczny(){
        boolean isOk = this.isManufacturerEtOk && this.isAndroidVersion_etOk && this.isModel_etOk && this.isWebsite_etOk;
        if(isOk) {
            this.saveBt.setAlpha(1f);
            this.saveBt.setClickable(true);
        }else{
            this.saveBt.setAlpha(.5f);
            this.saveBt.setClickable(false);
        }
    }
}