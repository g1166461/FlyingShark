package comps413f.android.flyingandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ResumeActivity extends Activity{
        private String[] menus;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.mainmenu);
            Resources res = getResources();
            menus = res.getStringArray(R.array.resumemenu);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    menus);
            ListView listView = (ListView) findViewById(R.id.mainmenu_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                private final Class<?>[] classes = { MainActivity.class,
                };

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(parent.getItemAtPosition(position).equals("About")){
                        AlertDialog.Builder builderInner=new AlertDialog.Builder(ResumeActivity.this);
                        builderInner.setTitle(R.string.about_title);
                        builderInner.setMessage(R.string.about_msg);
                        builderInner.setNeutralButton(android.R.string.ok, null);
                        builderInner.show();
                    }
                    Intent intent = new Intent(ResumeActivity.this, classes[position]);
                    if(position==0) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }
