package comps413f.android.flyingandroid;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/** View of flying android animation. */
public class FlyingAndroidView extends SurfaceView {
    /** Delay in each animation cycle, in ms. */
    private static final int CYCLE_DELAY = 30;
    /** Text size. */
    private static final int TEXT_SIZE = 24;

    /** Width and height of the arena. */
    public static int arenaWidth;
    public static int arenaHeight;
    private Rect r=new Rect();
    private Rect r2=new Rect();
    /** Animation object, the flying android. */
    private FlyingAndroid flyingAndroid;
    /** List of obstacles objects, i.e., pairs of pipes. */
    private Vector<Obstacles> obstacles = new Vector<Obstacles>();
    /** Scrolling background of the view. */
    Background background;

    /** Timer for the game loop. */
    private Timer timer = null;

    private Context context;
    private int count;
    /** Start time of the game. */
    private long startTime = 0;
    /** Pause time of the game. */
    private long pauseTime = 0;
    /** Total time elapsed of the game. */
    private float totalTime = 0;
    /** Obstacle creation time. */
    private float obstacleCreationTime;
    private float killerCreationTime;
    private float fogCreationTime;
    private Killer killer;
    private Fog fog;
    /** Whether the game is over. */
    private boolean gameOver;
    private Bitmap gameOverPicture;
    private Bitmap restartPicture;

    /**Pause Game*/
    private Bitmap pausePicture;


    /** Whether the game is paused and waiting for touching to start. */
    private boolean waitForTouch = true;

    /** Saving and handling of user input of touch events. */
    private class UserInput {
        /** Whether there is a user input present. */
        boolean present = false;
        int action;
        float x,y;

        /**
         * Sets the user input mouse event for later processing. This method is
         * called in event handlers, i.e., in the main UI thread.
         */
        synchronized void save(MotionEvent event) {
            present = true;
            action=event.getAction();
            x=event.getX();
            y=event.getY();
        }

        /**
         * Handles the user input to move the flying android upward. This method is
         * called in the thread of the game loop.
         */
        synchronized void handle() {
            if (present) {
                // Add code here
                // Task 4: Handling of user input
                // If (waitForTouch)
                // - Set waitForTouch to false
                // - Get current time and assign to startTime
                // - Start scrolling background
                // - Start animation of the flying android
                // Otherwise
                // - Move the flying android upward
                if (waitForTouch) {  // Start of the game
                    waitForTouch = false;
                    startTime = System.currentTimeMillis();
                    background.stop(false);
                    ((AnimationDrawable)(flyingAndroid.getDrawable())).start();



                }
                else if(gameOver){/*Restart Game*/
                    //Check if the x and y position of the touch is inside the bitmap
                    if( x > ((FlyingAndroidView.arenaWidth-restartPicture.getWidth()) / 2) && x < ((FlyingAndroidView.arenaWidth-restartPicture.getWidth())/2+150) && y > ((FlyingAndroidView.arenaHeight -restartPicture.getHeight())/2+getWidth() / 2) && y < ((FlyingAndroidView.arenaHeight -restartPicture.getHeight())/2 +getWidth() / 2+150)){
                        newGame(false);
                    }
                }
                else {  // Game active
                    flyingAndroid.fly();
                            //Pause Game
                            if(!gameOver) {
                                if (x > ((FlyingAndroidView.arenaWidth - pausePicture.getWidth()) / 20) && x < ((FlyingAndroidView.arenaHeight - pausePicture.getWidth()) / 20 + 100) && y > ((FlyingAndroidView.arenaHeight - pausePicture.getHeight()) / 20) && y < ((FlyingAndroidView.arenaHeight - pausePicture.getHeight()) / 20 + 100)) {
                                        Intent intent = new Intent(context, ResumeActivity.class);
                                        intent.putExtra("changePause", true);
                                        context.startActivity(intent);

                                }
                            }
                }

                present = false;
            }
        }
    }

    /** User input object of touch events. */
    private UserInput userInput = new UserInput();
    /** Task for the game loop. */
    private class AnimationTask extends TimerTask {
        @Override
        public void run() {
            userInput.handle();
            if (!gameOver && !waitForTouch) {
                // Add code here
                // Task 5: Game loop implementation

                // i. Create obstacles
                createObstacles();
                createKiller();
                createFog();
                // ii. Move the flying android
                flyingAndroid.move();

                // iii. If the flying android moved out from the arena, call method gameOver
                if (flyingAndroid.isOutOfArena()) {
                    gameOver();
                }
                else {
                    // iv. Obstacles manipulation
                    for (int i = 0; i < obstacles.size(); i++) {
                        // a. Move the obstacles
                        obstacles.get(i).move();

                        // b. Determine if the flying android collided with any obstacle
                        if (obstacles.get(i).collideWith(flyingAndroid)) {
                            gameOver();
                            break;
                        }

                        // c. Remove any obstacle that already moved out from the arena
                        if (obstacles.get(i).isOutOfArena())
                            obstacles.remove(i);
                    }
                    killer.move();
                    if (killer.collideWith(flyingAndroid)) {
                        gameOver();
                    }
                    fog.move();
                    if (fog.collideWith(flyingAndroid)) {
                        gameOver();
                    }
                }
            }

            // v. Draw the game objects
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                // a. Draw the scrolling background
                background.drawOn(canvas);

                // b. Draw the obstacles
                for (int i = 0; i < obstacles.size(); i++) {
                    obstacles.get(i).drawOn(canvas);
                }

                // c. Draw the flying android
                flyingAndroid.drawOn(canvas);
                killer.drawOn(canvas);
                fog.drawOn(canvas);
                // d. Draw game text
                drawGameText(canvas);

                getHolder().unlockCanvasAndPost(canvas);
            }
        }

        /** Paint object for painting text. */
        private Paint textPaint = new Paint();
        /** Draws text for the game. */
        private void drawGameText(Canvas canvas) {
            Resources res = getResources();
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(TEXT_SIZE);

            /**Pause*/
            if(!gameOver && !waitForTouch) {
                pausePicture = BitmapFactory.decodeResource(context.getResources(), R.drawable.pause);
                int scaledWidth3 = pausePicture.getWidth() / 8;
                int scaledHeight3 = pausePicture.getHeight() / 8;
                pausePicture = Bitmap.createScaledBitmap(pausePicture, scaledWidth3, scaledHeight3, true);
                r2.set((FlyingAndroidView.arenaWidth - pausePicture.getWidth()) / 20, (FlyingAndroidView.arenaHeight - pausePicture.getHeight()) / 20, (FlyingAndroidView.arenaHeight - pausePicture.getWidth()) / 20+100, (FlyingAndroidView.arenaHeight - pausePicture.getHeight()) / 20+100);
                canvas.drawBitmap(pausePicture,null,r2,textPaint);
            }

            // Add code here
            // Task 1: Draw game information
            // If game over
            // - Draw "Game Over" and the total time elapsed
            // Else if wait for touch
            // - Draw "Touch to Start!"
            // Else
            // - Draw the total time elapsed on the top left corner of the arena
            if (gameOver) {
                if (startTime > 0) {
                    totalTime += (System.currentTimeMillis() - startTime);
                    startTime = 0;
                }
                float gameTime = totalTime / 1000.0f;
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                gameOverPicture=BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover);
                int scaledWidth = gameOverPicture.getWidth()/4;
                int scaledHeight = gameOverPicture.getHeight()/4;
                gameOverPicture = Bitmap.createScaledBitmap(gameOverPicture, scaledWidth, scaledHeight, true);
                canvas.drawBitmap(gameOverPicture,(FlyingAndroidView.arenaWidth-gameOverPicture.getWidth()) / 2, (FlyingAndroidView.arenaHeight -gameOverPicture.getHeight())/ 2, textPaint);
                restartPicture=BitmapFactory.decodeResource(context.getResources(), R.drawable.restart);
                int scaledWidth2 = restartPicture.getWidth()/8;
                int scaledHeight2 = restartPicture.getHeight()/8;
                restartPicture= Bitmap.createScaledBitmap(restartPicture, scaledWidth2, scaledHeight2, true);
                r.set((FlyingAndroidView.arenaWidth-restartPicture.getWidth()) / 2, (FlyingAndroidView.arenaHeight -restartPicture.getHeight())/2+getWidth() / 2, (FlyingAndroidView.arenaWidth-restartPicture.getWidth())/2+150, (FlyingAndroidView.arenaHeight -restartPicture.getHeight())/2 + getWidth() / 2+150);
                canvas.drawBitmap(restartPicture, null, r, textPaint);
                canvas.drawText(res.getString(R.string.time_elapse, gameTime), getWidth() / 2, getHeight() / 2 + (scaledHeight/2), textPaint);


            }
            else if (waitForTouch) {
                textPaint.setTextSize(2 * TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(res.getString(R.string.start), getWidth() / 2, getHeight() / 2, textPaint);
            }
            else {
                textPaint.setTextSize(TEXT_SIZE);
                textPaint.setTextAlign(Paint.Align.LEFT);
                float gameTime = (System.currentTimeMillis() - startTime + totalTime) / 1000.0f;
                canvas.drawText(res.getString(R.string.time_elapse, gameTime), TEXT_SIZE, TEXT_SIZE, textPaint);
            }
        }
    }

    /** Create obstacles randomly. */
    public boolean createObstacles() {
        // Add code here
        // Task 2: Create one pair of pipes for every 15-25s randomly
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - obstacleCreationTime;
        if (obstacleCreationTime == -1 || timeDiff >  25000) {
            obstacleCreationTime = gameTime;
            Obstacles o = new Obstacles(context);
            obstacles.add(o);
        }
        return true;
    }
    public void createKiller() {
        // Add code here
        //  Create one killer for every 7-17s randomly
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - killerCreationTime;
        if (killerCreationTime == -1 || timeDiff > ((Math.random()*10000)+7000)) {
            killerCreationTime = gameTime;
            killer=new Killer(context);
        }
    }
    public void createFog() {
        // Add code here
        // Task 2: Create fog for every 25s randomly
        float gameTime = (System.currentTimeMillis() - startTime + totalTime);
        float timeDiff = gameTime - fogCreationTime;
        if (fogCreationTime == -1 || timeDiff > 25000) {
            System.out.println("hello");
            fogCreationTime = gameTime;
            fog=new Fog(context);
        }
    }

    /** Game over. */
    public void gameOver() {
        // Add code here
        // Task 3: Game over handling
        // i. Set gameOver to true
        // ii. Stop the animation of the flying andriod
        // iii. Stop the scrolling background

        /**Vibration*/
        Effect effect = new Effect();
        effect.vibrateOn = true;
        effect.soundOn = true;
        gameOver = true;

        effect.vibrate();
        effect.playSound(R.raw.beep);
        ((AnimationDrawable)(flyingAndroid.getDrawable())).stop();
        background.stop(true);
    }

    /** Resume or start the animation. */
    public void resume() {
        if (timer == null)
            timer = new Timer();
        timer.schedule(new AnimationTask(), 0, CYCLE_DELAY);
    }

    /** Pause or stop the animation. */
    public void pause() {
        totalTime += (System.currentTimeMillis() - startTime);
        waitForTouch = true;

        background.stop(true);
        ((AnimationDrawable) (flyingAndroid.getDrawable())).stop();

        timer.cancel();
        timer = null;
    }

    /**
     * Start a new game.
     */
    public void newGame(boolean newGame) {
        if (newGame) {
            arenaWidth = getWidth();
            arenaHeight = getHeight();
            background = new Background(context);
            flyingAndroid = new FlyingAndroid(this, context);
        }
        fog=new Fog(context);
        killer=new Killer(context);
        fogCreationTime=-1;
        gameOver = false;
        waitForTouch = true;
        totalTime = 0;
        startTime = -1;
        obstacleCreationTime = -1;
        killerCreationTime=-1;
        obstacles.clear();
        flyingAndroid.reset();

        ((AnimationDrawable)(flyingAndroid.getDrawable())).stop();
        background.stop(true);
    }

    /**
     * Constructs an animation view. This performs initialization including the
     * event handlers for key presses and touches.
     */
    public FlyingAndroidView(Context context) {
        super(context);
        this.context = context;

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                userInput.save(event);
                return true;
            }
        });

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                while (getWidth() == 0)
                    ; // Wait for layout
                newGame(true);
            }
        }, 0);
    }

    protected boolean verifyDrawable(Drawable who) {
        super.verifyDrawable(who);
        return who == flyingAndroid.getDrawable();
    }

    public void invalidateDrawable(Drawable drawable) {
    }

    private class Effect {

        private Vibrator vibrater = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        private long[] pattern = new long[]{0, 200, 200, 200};
        /** Whether to play sound or not. */
        boolean soundOn ;
        /** Whether to vibrate the device or not. */
        boolean vibrateOn ;
        private AsyncPlayer player = new AsyncPlayer("memory");
        void playSound(int resId) {
            if (soundOn) {
                Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + resId);
                player.play(context, uri, false, AudioManager.STREAM_MUSIC);
            }
        }

        /** Vibrates if vibrateOn is set. */
        void vibrate() {
            if (vibrateOn)
                vibrater.vibrate(pattern, -1);
        }
    }
}
