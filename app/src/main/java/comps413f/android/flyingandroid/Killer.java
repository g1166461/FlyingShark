package comps413f.android.flyingandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class Killer extends Sprite {
    private static final float INITIAL_DX = 3;  // Initial velocity in vertical direction
    private float dx;  // y velocity of the flying android object
    private Bitmap  killerPicture;
    private Drawable modifiedPicture;
    private Bitmap killerRotatedPicture;
    /** Constructor. */
    public Killer(Context context) {
        Matrix matrix = new Matrix();

        matrix.postRotate(270);
        killerPicture=BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship);
        int scaledWidth = (int) (killerPicture.getWidth() * (FlyingSharkView.arenaHeight / (float) killerPicture.getHeight()))/10;
        killerPicture = Bitmap.createScaledBitmap(killerPicture, scaledWidth, FlyingSharkView.arenaHeight/10, true);
        killerRotatedPicture=Bitmap.createBitmap(killerPicture,0,0,scaledWidth,FlyingSharkView.arenaHeight/10,matrix,true);
        modifiedPicture=new BitmapDrawable(context.getResources(),killerRotatedPicture);
        drawable = modifiedPicture;
        dx = Background.SpeedXMagnitude-2;
        setPosition(FlyingSharkView.arenaWidth,(float)(Math.random()*2)*(FlyingSharkView.arenaHeight/2));
    }
    public void increaseSpeed(){
        dx-=3;
    }
    /** Reset the x, y position of the flying android. */


    @Override
    /** Move the flying android. */
    public void move() {
        if (dx != 0) {
            // Add code here
            // Task 2: Move the flying android
            // i. Update the new y position of the flying android
            curPos.x += dx*3;

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
