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


    /** Resumes the animation. This method is called when the activity is resumed. */
    @Override
    protected void onResume() {
        super.onResume();
        if(animationView.isMusic) {
            musicPlayerLoop.start();
            if (musicPlayerLoop.isPlaying() == false) {
                musicPlayerLoop.start();
            }
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
