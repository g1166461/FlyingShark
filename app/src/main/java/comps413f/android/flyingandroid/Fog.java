package comps413f.android.flyingandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Fog extends Sprite {
    private static final float INITIAL_DX = 1;  // Initial velocity in vertical direction
    private float dx;  // y velocity of the flying android object
    private Bitmap fogPicture;
    private Drawable modifiedPicture;
    /** Constructor. */
    public Fog(Context context) {

        fogPicture=BitmapFactory.decodeResource(context.getResources(), R.drawable.fog);
        int scaledWidth = (int) (fogPicture.getWidth() * (FlyingSharkView.arenaHeight / (float) fogPicture.getHeight()))/2;
        fogPicture = Bitmap.createScaledBitmap(fogPicture, scaledWidth, FlyingSharkView.arenaHeight/3, true);
        modifiedPicture=new BitmapDrawable(context.getResources(),fogPicture);
        drawable = modifiedPicture;
        dx = INITIAL_DX;
        setPosition(-300,(float)(Math.random()*2)*(FlyingSharkView.arenaHeight/2));
    }

    /** Reset the x, y position of the flying android. */


    @Override
    /** Move the flying android. */
    public void move() {
        if (dx != 0) {
            // Add code here
            // Task 2: Move the flying android
            // i. Update the new y position of the flying android
            curPos.x += dx+0.01;

            // ii. Update the boundary of the flying android drawable
            updateBounds();
        }
    }
    @Override
    /** Evaluate if the flying android is moving out of the arena, i.e., game end. */
    public boolean isOutOfArena() {
        if (curPos.y < 0 || curPos.y > FlyingSharkView.arenaHeight - getHeight())
            return true;
        return false;
    }
}