package comps413f.android.flyingandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Spinner spinner = (Spinner) findViewById(R.id.diffculty_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

        public void onItemSelected(AdapterView<?> parent, View view,int pos, long id) {
            String item = parent.getItemAtPosition(pos).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }


