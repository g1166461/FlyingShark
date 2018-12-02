package comps413f.android.flyingshark;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;

public class FlyingSharkActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isShowing = prefs.getBoolean(res.getString(R.string.pref_splash_key),
                res.getBoolean(R.bool.pref_splash_default));
        if (!isShowing) {
            showMainMenu();
            return;
        }

        setContentView(R.layout.splash);
    }

    /** Called when touched. */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            showMainMenu();
        return true;
    }

    /** Shows the main menu and finishes this activity. */
    private void showMainMenu() {
        Intent intent = new Intent(FlyingSharkActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}

