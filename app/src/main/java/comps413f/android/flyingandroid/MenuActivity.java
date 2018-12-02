package comps413f.android.flyingandroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MenuActivity extends Activity {
    private String[] menus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);
        Resources res = getResources();
        menus = res.getStringArray(R.array.mainmenu);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                menus);
        ListView listView = (ListView) findViewById(R.id.mainmenu_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private final Class<?>[] classes = { MainActivity.class, FlyingSharkPrefActivity.class,
                    AboutActivity.class,ClearActivity.class};

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("About")){
                    AlertDialog.Builder builderInner=new AlertDialog.Builder(MenuActivity.this);
                    builderInner.setTitle(R.string.about_title);
                    builderInner.setMessage(R.string.about_msg);
                    builderInner.setNeutralButton(android.R.string.ok, null);
                    builderInner.show();
                }
                if(parent.getItemAtPosition(position).equals("Clear Highest Time Record")){
                    SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(MenuActivity.this);
                    SharedPreferences.Editor editor = saved_values.edit();
                    editor.remove("time").apply();
                    AlertDialog.Builder builderInner=new AlertDialog.Builder(MenuActivity.this);
                    builderInner.setTitle(R.string.about_cleardata_title);
                    builderInner.setMessage(R.string.about_cleardata_msg);
                    builderInner.setNeutralButton(android.R.string.ok, null);
                    builderInner.show();
                }

                Intent intent = new Intent(MenuActivity.this, classes[position]);
                if(position==0) {
                    startActivity(intent);
                }
                if(position==1){
                    startActivity(intent);
                }
            }
        });
    }
}
