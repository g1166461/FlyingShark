package comps413f.android.flyingandroid;

import android.app.Activity;
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
            menus = res.getStringArray(R.array.mainmenu);
            boolean changePause=getIntent().getBooleanExtra("changePause",false);
            if(changePause){
                menus[0]="Resume";
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    menus);
            ListView listView = (ListView) findViewById(R.id.mainmenu_list);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                private final Class<?>[] classes = { MainActivity.class, SpinnerActivity.class
                };

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ResumeActivity.this, classes[position]);
                    if(position==0) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                        finish();
                    }
                    if(position==1){
                        startActivity(intent);
                    }
                }
            });
        }
    }
