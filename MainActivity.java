
package pl.pollub.pierwszaapka;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import pl.pollub.pierwszaapka.databinding.ActivityMainBinding;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText nameEditText, surnameEditText, gradesEditText;
    private Button gradesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        gradesEditText = findViewById(R.id.gradesEditText);

        gradesButton = findViewById(R.id.gradesButton);
        gradesButton.setVisibility(View.INVISIBLE);

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && nameEditText.getText().toString().isEmpty()) {
                    nameEditText.setError("Pole nie może być puste");
                }
                checkFields();
            }
        });

        surnameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && surnameEditText.getText().toString().isEmpty()) {
                    surnameEditText.setError("Pole nie może być puste");
                }
                checkFields();
            }
        });

        gradesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    try {
                        int grades = Integer.parseInt(gradesEditText.getText().toString());
                        if (grades < 5 || grades > 15) {
                            gradesEditText.setError("Liczba ocen musi być z przedziału od 5 do 15");
                        }
                    } catch (NumberFormatException e) {
                        gradesEditText.setError("Nieprawidłowa wartość");
                    }
                }
                checkFields();
            }
        });
    }

    private void checkFields() {
        boolean validFields = true;
        if (nameEditText.getText().toString().isEmpty()) {
            validFields = false;
        }
        if (surnameEditText.getText().toString().isEmpty()) {
            validFields = false;
        }
        try {
            int grades = Integer.parseInt(gradesEditText.getText().toString());
            if (grades < 5 || grades > 15) {
                validFields = false;
            }
        } catch (NumberFormatException e) {
            validFields = false;
        }
        if (validFields) {
            gradesButton.setVisibility(View.VISIBLE);
        } else {
            gradesButton.setVisibility(View.INVISIBLE);
        }
    }

    public void showGrades(View view) {
        Toast.makeText(this, "Przycisk 'Oceny' został kliknięty", Toast.LENGTH_SHORT).show();
    }
}
