package tn.meditrackplus.meditrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure your XML file is named "activity_dashboard.xml" in res/layout/
        setContentView(R.layout.activity_dashboard);

        // FIX 1: Changed ID to match the XML (tvWelcomeUser)
        TextView tvTitle = findViewById(R.id.tvWelcomeUser);

        Button btnMedications = findViewById(R.id.btnMedications);
        Button btnVitals = findViewById(R.id.btnVitals);
        Button btnBMI = findViewById(R.id.btnBMI);
        Button btnSOS = findViewById(R.id.btnSOS);

        // Retrieve User Name from Bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("USER_NAME_KEY");
            if (name != null) {
                tvTitle.setText("Welcome, " + name);
            }
        }

        btnMedications.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, MedicationActivity.class)));
        btnVitals.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, VitalsActivity.class)));

        // Go to BMI Input
        btnBMI.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, BMIInputActivity.class)));

        btnSOS.setOnClickListener(v -> {
            // ACTION_DIAL is safer than ACTION_CALL as it doesn't require runtime permissions
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:190"));
            startActivity(intent);
        });
    }
}