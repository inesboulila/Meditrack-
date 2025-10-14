package tn.meditrackplus.meditrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton, moreButton;
    private ProgressBar loadingBar;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        moreButton = findViewById(R.id.moreButton);
        loadingBar = findViewById(R.id.loadingBar);

        // When "Get Started" is clicked
        startButton.setOnClickListener(v -> {
            loadingBar.setVisibility(View.VISIBLE);
            progress = 0;
            loadingBar.setProgress(progress);

            Handler handler = new Handler(Looper.getMainLooper());
            Thread thread = new Thread(() -> {
                while (progress < 100) {
                    try {
                        Thread.sleep(25); // short animation
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progress++;
                    handler.post(() -> loadingBar.setProgress(progress));
                }

                // When finished → go to dashboard
                handler.post(() -> {
                    loadingBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(MainActivity.this,
                            "Welcome to your Dashboard!",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                });
            });
            thread.start();
        });

        // When "More Info" is clicked
        moreButton.setOnClickListener(v ->
                Toast.makeText(MainActivity.this, "More information coming soon!", Toast.LENGTH_SHORT).show());
    }
}
