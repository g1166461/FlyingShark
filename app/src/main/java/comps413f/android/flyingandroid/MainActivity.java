package comps413f.android.flyingandroid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.app.AlertDialog;

public class MainActivity extends Activity {
    /** The animation view. */
    private FlyingAndroidView animationView;
    MediaPlayer musicPlayerLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationView = new FlyingAndroidView(this);
        setContentView(animationView);

        musicPlayerLoop = MediaPlayer.create(getApplicationContext(), R.raw.background_music);

        musicPlayerLoop.setLooping(true);
        musicPlayerLoop.seekTo(0);
        musicPlayerLoop.setVolume(0.5f, 0.5f);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles the option menu selection. This method is called when an options
     * menu item is selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_restart:
            animationView.newGame(false);
            break;

            case R.id.about:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.about_title)
                        .setMessage(R.string.about_msg)
                        .setNeutralButton(android.R.string.ok, null)
                        .show();
                break;

        }
        return false;
    }

    /** Resumes the animation. This method is called when the activity is resumed. */
    @Override
    protected void onResume() {
        super.onResume();
        musicPlayerLoop.start();
        if(musicPlayerLoop.isPlaying() == false){
            musicPlayerLoop.start();
        }
        animationView.resume();
    }

    /** Pauses the animation. This method is called when the activity is paused. */
    @Override
    protected void onPause() {
        super.onPause();
        musicPlayerLoop.pause();
        animationView.pause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(musicPlayerLoop.isPlaying()){
            musicPlayerLoop.stop();
        }
        musicPlayerLoop.release();
        //animationView.release();
    }
}
