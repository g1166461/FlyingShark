package comps413f.android.flyingandroid;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class FlyingSharkPrefActivity extends PreferenceActivity {

    /** Preference activity for showing the preference screen */

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new EditorPreferenceFragment())
                    .commit();
        }

        /** Subclass of PreferenceFragment to add preferences from resource. */
        public static class EditorPreferenceFragment extends PreferenceFragment {
            @Override
            public void onCreate(final Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                addPreferencesFromResource(R.xml.game_prefs);
            }
        }
    }

