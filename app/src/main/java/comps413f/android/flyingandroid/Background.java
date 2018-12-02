package comps413f.android.flyingandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/** The scrolling background. */
public class Background {
    /** Scrolling speed magnitude of background. */
    static final int SpeedXMagnitude = -2;
    /** Scrolling speed of background. */
    private static int speedX = 0;
    /** Bitmap of the background. */
    private Bitmap background;
    /** Drawing position of the background. */
    private static final int bgY = 0; 
    private static int bgX = 0, bgX2;

    /** Constructor. */
    public Background(Context context) {
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        int scaledWidth = (int) (background.getWidth() * (FlyingSharkView.arenaHeight / (float) background.getHeight()));
        background = Bitmap.createScaledBitmap(background, scaledWidth, FlyingSharkView.arenaHeight, true);
    }

    /** Stop scrolling background. */
    public void stop(boolean stop) {
        if (stop)
            speedX = 0;
        else
            speedX = SpeedXMagnitude;
    }

    /** Draws the background. */
    public void drawOn(Canvas canvas) {
        if (background != null) {
            // Decrement the position of the first background bitmap
            bgX += speedX;

            // Calculate the position of the second background bitmap
            bgX2 = background.getWidth() + bgX;

            // If the second bitmap moving out from the view, reset the first one to start again
            if (bgX2 <= 0) {
                bgX = 0;
                // Only need to draw one bitmap (the first one)
                canvas.drawBitmap(background, bgX, bgY, null);
            } else {
                // Two bitmaps are needed to be drawn
                canvas.drawBitmap(background, bgX, bgY, null);
                canvas.drawBitmap(background, bgX2, bgY, null);
            }
        }
    }
}
