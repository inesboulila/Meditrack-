package tn.meditrackplus.meditrack;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class VitalsActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ArrayList<String> vitalsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vitals);

        dbHelper = new DatabaseHelper(this);
        EditText etBP = findViewById(R.id.etBP);
        EditText etSugar = findViewById(R.id.etSugar);
        Button btnSave = findViewById(R.id.btnSaveVital);
        ListView listView = findViewById(R.id.listViewVitals);
        Button btnBack = findViewById(R.id.btnBack);

        btnSave.setOnClickListener(v -> {
            String bp = etBP.getText().toString();
            String sugar = etSugar.getText().toString();

            if (bp.isEmpty() && sugar.isEmpty()) {
                Toast.makeText(this, "Enter at least one value", Toast.LENGTH_SHORT).show();
                return;
            }

            String data = "BP: " + (bp.isEmpty()?"-":bp) + " | Sugar: " + (sugar.isEmpty()?"-":sugar);
            String date = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault()).format(new Date());

            if (dbHelper.addVital(data, date)) {
                etBP.setText("");
                etSugar.setText("");
                loadVitals(listView);
            }
        });

        btnBack.setOnClickListener(v -> finish());
        loadVitals(listView);
    }

    private void loadVitals(ListView listView) {
        vitalsList = dbHelper.getAllVitals();
        adapter = new ArrayAdapter<String>(this, R.layout.item_vital, R.id.tvVitalData, vitalsList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                String[] parts = vitalsList.get(position).split("\\|");

                TextView dataView = view.findViewById(R.id.tvVitalData);
                TextView dateView = view.findViewById(R.id.tvVitalDate);

                if(parts.length >= 2) {
                    dataView.setText(parts[0]);
                    dateView.setText(parts[1]);
                }
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
}