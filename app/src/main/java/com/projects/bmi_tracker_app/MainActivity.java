package com.projects.bmi_tracker_app;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.inappmessaging.model.Button;

public class MainActivity extends AppCompatActivity {

    private EditText heightInput, weightInput;
    private Spinner heightUnitSpinner, weightUnitSpinner;
    private View calculateButton;
    private TextView resultText;

    private String heightUnit = "cm"; // Default unit
    private String weightUnit = "kg"; // Default unit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        heightUnitSpinner = findViewById(R.id.heightUnitSpinner);
        weightUnitSpinner = findViewById(R.id.weightUnitSpinner);
        calculateButton = findViewById(R.id.calculateButton);
        resultText = findViewById(R.id.resultText);

        setupUnitSpinners();

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });
    }
    private void setupUnitSpinners() {
        // Setting up the height unit spinner
        ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter.createFromResource(this,
                R.array.height_units, android.R.layout.simple_spinner_item);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightUnitSpinner.setAdapter(heightAdapter);
        heightUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                heightUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Setting up the weight unit spinner
        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(this,
                R.array.weight_units, android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightUnitSpinner.setAdapter(weightAdapter);
        weightUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                weightUnit = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void calculateBMI() {
        String heightStr = heightInput.getText().toString();
        String weightStr = weightInput.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please enter both height and weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);

        // Convert the height and weight to meters and kilograms respectively
        double heightInMeters = UnitConverter.convertHeightToMeters(height, heightUnit);
        double weightInKg = UnitConverter.convertWeightToKg(weight, weightUnit);

        if (heightInMeters <= 0 || weightInKg <= 0) {
            Toast.makeText(this, "Invalid values for height or weight", Toast.LENGTH_SHORT).show();
            return;
        }

        double bmi = BMIUtil.calculateBMI(heightInMeters, weightInKg);
        resultText.setText(String.format("Your BMI is: %.2f", bmi));
    }
}