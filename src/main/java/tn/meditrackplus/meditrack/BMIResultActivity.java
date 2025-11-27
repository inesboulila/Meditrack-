package tn.meditrackplus.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BMIResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_result);

        TextView tvBMI = findViewById(R.id.tvBMIValue);
        TextView tvStatus = findViewById(R.id.tvBMIStatus);
        Button btnDone = findViewById(R.id.btnDone);

        // 1. Get the Intent and Bundle
        Intent myLocalIntent = getIntent();
        Bundle myBundle = myLocalIntent.getExtras();

        if (myBundle != null) {
            double weight = myBundle.getDouble("WEIGHT");
            double heightCm = myBundle.getDouble("HEIGHT");

            // 2. Do the Calculation (Operate on data)
            double heightM = heightCm / 100.0;
            double bmi = weight / (heightM * heightM);

            String status;
            int color;
            if (bmi < 18.5) { status = "Underweight"; color = 0xFF2196F3; }
            else if (bmi < 25) { status = "Healthy"; color = 0xFF4CAF50; }
            else if (bmi < 30) { status = "Overweight"; color = 0xFFFF9800; }
            else { status = "Obese"; color = 0xFFF44336; }

            // 3. Display data (Visual Confirmation)
            tvBMI.setText(String.format("BMI: %.1f", bmi));
            tvStatus.setText(status);
            tvStatus.setTextColor(color);

            // 4. Prepare result to send BACK
            myBundle.putDouble("BMI_VALUE", bmi);
            myBundle.putString("BMI_STATUS", status);

            // Attach updated bundle to intent
            myLocalIntent.putExtras(myBundle);

            // 5. Send OK signal
            setResult(RESULT_OK, myLocalIntent);
        }

        btnDone.setOnClickListener(v -> {
            // Close activity (Triggers sending the result back)
            finish();
        });
    }
}