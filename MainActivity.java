package com.example.unitconverter; // Change this to match your actual package name

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCategory, spinnerFromUnit, spinnerToUnit;
    private EditText editTextInput;
    private Button buttonConvert;
    private TextView textViewResult;

    // Measurement Categories
    private final String[] categories = {"Length", "Weight", "Temperature"};

    // Unit Arrays
    private final String[] lengthUnits = {"Centimeters (cm)", "Meters (m)", "Kilometers (km)"};
    private final String[] weightUnits = {"Grams (g)", "Kilograms (kg)", "Pounds (lbs)"};
    private final String[] tempUnits = {"Celsius (°C)", "Fahrenheit (°F)"};

    @Override
    protected void bundle(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        editTextInput = findViewById(R.id.editTextInput);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        // Set up Category Spinner
        ArrayAdapter<String> catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(catAdapter);

        // Listen for category selection changes to update unit spinners dynamically
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnitSpinners(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Convert Button Click Listener
        buttonConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    // Dynamic dropdown updates based on category selection
    private void updateUnitSpinners(int categoryPosition) {
        String[] currentUnits;
        switch (categoryPosition) {
            case 0: // Length
                currentUnits = lengthUnits;
                break;
            case 1: // Weight
                currentUnits = weightUnits;
                break;
            case 2: // Temperature
                currentUnits = tempUnits;
                break;
            default:
                currentUnits = lengthUnits;
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentUnits);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        spinnerFromUnit.setAdapter(unitAdapter);
        spinnerToUnit.setAdapter(unitAdapter);
    }

    // Core calculation and validation method
    private void performConversion() {
        String inputText = editTextInput.getText().toString().trim();

        // 1. Input Validation Checklist Requirement
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a numeric value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            return;
        }

        String category = spinnerCategory.getSelectedItem().toString();
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();
        double result = 0.0;

        // 2. Conversion Logic Switchboards
        if (category.equals("Length")) {
            result = convertLength(inputValue, fromUnit, toUnit);
        } else if (category.equals("Weight")) {
            result = convertWeight(inputValue, fromUnit, toUnit);
        } else if (category.equals("Temperature")) {
            result = convertTemperature(inputValue, fromUnit, toUnit);
        }

        // Display results cleanly
        textViewResult.setText(String.format("Result: %.4f %s", result, toUnit));
    }

    // --- Conversion Helper Functions ---

    private double convertLength(double value, String from, String to) {
        // Base Unit: Meters
        double meters;
        if (from.contains("(cm)")) meters = value / 100.0;
        else if (from.contains("(km)")) meters = value * 1000.0;
        else meters = value; // meters

        if (to.contains("(cm)")) return meters * 100.0;
        if (to.contains("(km)")) return meters / 1000.0;
        return meters;
    }

    private double convertWeight(double value, String from, String to) {
        // Base Unit: Grams
        double grams;
        if (from.contains("(kg)")) grams = value * 1000.0;
        else if (from.contains("(lbs)")) grams = value * 453.592;
        else grams = value; // grams

        if (to.contains("(kg)")) return grams / 1000.0;
        if (to.contains("(lbs)")) return grams / 453.592;
        return grams;
    }

    private double convertTemperature(double value, String from, String to) {
        if (from.equals(to)) return value;
        
        if (from.contains("Celsius")) {
            return (value * 9/5) + 32; // To Fahrenheit
        } else {
            return (value - 32) * 5/9; // To Celsius
        }
    }
}
