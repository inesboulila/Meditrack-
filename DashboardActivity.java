package tn.meditrackplus.meditrack;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnMedications, btnVitals, btnAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnMedications = findViewById(R.id.btnMedications);
        btnVitals = findViewById(R.id.btnVitals);
        btnAppointments = findViewById(R.id.btnAppointments);

        View.OnClickListener listener = v -> {
            String message = "";
            if (v.getId() == R.id.btnMedications) message = "Opening Medications...";
            else if (v.getId() == R.id.btnVitals) message = "Logging Vitals...";
            else if (v.getId() == R.id.btnAppointments) message = "Checking Appointments...";
            Toast.makeText(DashboardActivity.this, message, Toast.LENGTH_SHORT).show();
        };

        btnMedications.setOnClickListener(listener);
        btnVitals.setOnClickListener(listener);
        btnAppointments.setOnClickListener(listener);
    }
}
