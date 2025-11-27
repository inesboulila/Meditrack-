package tn.meditrackplus.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BMIInputActivity extends AppCompatActivity {

    private EditText etWeight, etHeight;
    private TextView tvResultReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_input); // Make sure this name matches the XML file name

        // These lines are where your error was.
        // Now that the XML has the IDs (etWeightInput, etc), these errors will go away.
        etWeight = findViewById(R.id.etWeightInput);
        etHeight = findViewById(R.id.etHeightInput);
        tvResultReceived = findViewById(R.id.tvResultReceived);
        Button btnCalculate = findViewById(R.id.btnCalculateBMI);
        Button btnBack = findViewById(R.id.btnBackBMI);

        // Clear text initially
        tvResultReceived.setText("");

        btnCalculate.setOnClickListener(v -> {
            String wStr = etWeight.getText().toString();
            String hStr = etHeight.getText().toString();

            if (!wStr.isEmpty() && !hStr.isEmpty()) {
                double weight = Double.parseDouble(wStr);
                double height = Double.parseDouble(hStr);

                // Start Activity 2 (Result) and wait for data back
                Intent intent = new Intent(BMIInputActivity.this, BMIResultActivity.class);
                Bundle myData = new Bundle();
                myData.putDouble("WEIGHT", weight);
                myData.putDouble("HEIGHT", height);
                intent.putExtras(myData);

                startActivityForResult(intent, 101);

            } else {
                Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if ((requestCode == 101) && (resultCode == RESULT_OK)) {
                Bundle myResults = data.getExtras();

                String statusResult = myResults.getString("BMI_STATUS");
                double bmiValue = myResults.getDouble("BMI_VALUE");

                // --- PROFESSIONAL TEXT UPDATE ---
                tvResultReceived.setText("Latest Health Analysis:\nYour BMI is " + String.format("%.1f", bmiValue) + "\nCategory: " + statusResult);
                tvResultReceived.setBackgroundColor(0xFFE8F5E9);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Calculation cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}