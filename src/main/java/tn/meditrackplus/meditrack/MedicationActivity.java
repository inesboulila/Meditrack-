package tn.meditrackplus.meditrack;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MedicationActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<Medication> medList;
    private ArrayAdapter<Medication> adapter;
    private String selectedTime = "";

    // Variables for Editing
    private boolean isEditMode = false;
    private int selectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        dbHelper = new DatabaseHelper(this);

        EditText etName = findViewById(R.id.etMedName);
        Button btnTime = findViewById(R.id.btnPickTime);
        Button btnAction = findViewById(R.id.btnAddMed);
        ListView listView = findViewById(R.id.listViewMeds);
        Button btnBack = findViewById(R.id.btnBack);

        // 1. Time Picker
        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                btnTime.setText("Time: " + selectedTime);
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });

        // 2. Add / Update Logic
        btnAction.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();

            if (name.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Enter name and pick time", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEditMode) {
                // UPDATE
                boolean success = dbHelper.updateMedication(selectedId, name, selectedTime);
                if (success) Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
                isEditMode = false;
                btnAction.setText("Save Schedule");
            } else {
                // INSERT
                boolean success = dbHelper.addMedication(name, selectedTime);
                if (success) Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            }

            etName.setText("");
            btnTime.setText("Pick Time ⏰");
            selectedTime = "";
            loadMeds(listView);
        });

        // 3. Single Click -> EDIT
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Medication clickedMed = medList.get(position);

            etName.setText(clickedMed.name);
            selectedTime = clickedMed.time;
            btnTime.setText("Time: " + selectedTime);

            isEditMode = true;
            selectedId = clickedMed.id;
            btnAction.setText("Update Medication");

            Toast.makeText(this, "Editing: " + clickedMed.name, Toast.LENGTH_SHORT).show();
        });

        // 4. Long Click -> DELETE
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Medication clickedMed = medList.get(position);

            new AlertDialog.Builder(this)
                    .setTitle("Delete Medication")
                    .setMessage("Delete " + clickedMed.name + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deleteMedication(clickedMed.id);
                        loadMeds(listView);
                        Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });

        btnBack.setOnClickListener(v -> finish());
        loadMeds(listView);
    }

    private void loadMeds(ListView listView) {
        medList = dbHelper.getAllMedications();

        adapter = new ArrayAdapter<Medication>(this, R.layout.item_medication, R.id.tvMedName, medList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                Medication m = medList.get(position);

                TextView nameView = view.findViewById(R.id.tvMedName);
                TextView timeView = view.findViewById(R.id.tvMedTime);

                nameView.setText(m.name);
                timeView.setText(m.time);
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
}