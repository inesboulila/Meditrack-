package tn.meditrackplus.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etName = findViewById(R.id.etUserName);
        Button btnStart = findViewById(R.id.startButton);

        btnStart.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

                // --- PASSING DATA USING BUNDLE ---
                Bundle bundle = new Bundle();
                bundle.putString("USER_NAME_KEY", name);
                intent.putExtras(bundle);

                startActivity(intent);
                finish();
            }
        });
    }
}