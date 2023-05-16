package pl.pollub.android.app2_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PhoneActivity extends AppCompatActivity {
    public static final String MANUFACTURER_KEY = "MANUFACTURER_KEY";
    public static final String MODEL_KEY = "MODEL_KEY";
    public static final String ANDROID_VERSION_KEY = "ANDROID_VERSION_KEY";
    public static final String WEB_SITE_KEY = "WEB_SITE_KEY";
    public static final String PHONE_ID_KEY = "PHONE_ID_KEY";

    private EditText manufacturerEt;
    private EditText modelEt;
    private EditText androidVersionEt;
    private EditText webSiteEt;
    private Button saveBt;
    private Button cancelBt;
    private Long phoneId = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        this.manufacturerEt = findViewById(R.id.manufacturer_et);
        this.modelEt = findViewById(R.id.model_et);
        this.androidVersionEt = findViewById(R.id.android_version_et);
        this.webSiteEt = findViewById(R.id.web_site_et);
        this.saveBt = findViewById(R.id.save_bt);
        this.saveBt.setOnClickListener(view -> saveNewPhone());
        this.cancelBt = findViewById(R.id.cancel_bt);
        this.cancelBt.setOnClickListener(view -> cancelAction());
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null){
                this.manufacturerEt.setText(bundle.getString(MANUFACTURER_KEY));
                this.modelEt.setText(bundle.getString(MODEL_KEY));
                this.androidVersionEt.setText(bundle.getString(ANDROID_VERSION_KEY));
                this.webSiteEt.setText(bundle.getString(WEB_SITE_KEY));
                this.phoneId = bundle.getLong(PHONE_ID_KEY);
            }
        }
    }

    private void cancelAction(){
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }

    private void saveNewPhone() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(this.MANUFACTURER_KEY,this.manufacturerEt.getText().toString());
        bundle.putString(this.MODEL_KEY,this.modelEt.getText().toString());
        bundle.putString(this.ANDROID_VERSION_KEY,this.androidVersionEt.getText().toString());
        bundle.putString(this.WEB_SITE_KEY,this.webSiteEt.getText().toString());
        bundle.putLong(this.PHONE_ID_KEY,this.phoneId);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}