package tn.meditrackplus.meditrack;

import android.content.Intent; // Added for potential future navigation
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper; // Import Looper for Handler
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button moreButton; // Declared the moreButton
    private ProgressBar loadingBar;
    private int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        startButton = findViewById(R.id.startButton);
        moreButton = findViewById(R.id.moreButton); // Initialized moreButton
        loadingBar = findViewById(R.id.loadingBar);

        // Set OnClickListener for the "Get Started" button
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the progress bar
                loadingBar.setVisibility(View.VISIBLE);
                // Reset progress and set it visually
                progress = 0;
                loadingBar.setProgress(progress);

                // Create a Handler associated with the main UI thread's Looper
                Handler handler = new Handler(Looper.getMainLooper());

                // Create and start a new background thread for progress simulation
                Thread worker = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progress < 100) {
                            try {
                                // Simulate work being done
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                // Handle thread interruption
                                e.printStackTrace();
                                Thread.currentThread().interrupt(); // Restore the interrupted status
                            }

                            // Post UI update to the main thread
                            handler.post(() -> {
                                progress++; // Increment progress
                                loadingBar.setProgress(progress); // Update the progress bar
                            });
                        }

                        // After progress reaches 100, post final UI updates to the main thread
                        handler.post(() -> {
                            // Show a success toast message
                            Toast toast = Toast.makeText(MainActivity.this,
                                    "Dashboard ready!",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0); // Center the toast
                            toast.show();

                            // Hide the progress bar
                            loadingBar.setVisibility(View.GONE);

                            // TODO: Add navigation to another activity here, e.g.:
                            // Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            // startActivity(intent);
                        });
                    }
                });
                worker.start(); // Start the background thread
            }
        });

        // Set OnClickListener for the "More Info" button
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Example: Show a simple toast for the More Info button
                Toast.makeText(MainActivity.this, "More information coming soon!", Toast.LENGTH_SHORT).show();
                // TODO: Add navigation to a More Info / About Us activity here, e.g.:
                // Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                // startActivity(intent);
            }
        });
    }
}