package comps413f.android.flyingandroid;

import android.content.Context;

public class Killer extends Sprite {
    private static final float INITIAL_DX = 3;  // Initial velocity in vertical direction
    private float dx;  // y velocity of the flying android object
    /** Constructor. */
    public Killer(Context context) {
        drawable = context.getResources().getDrawable(R.drawable.spaceship);
        dx = Background.SpeedXMagnitude;
        setPosition(FlyingAndroidView.arenaHeight/2,(float)(Math.random()*2)*(FlyingAndroidView.arenaHeight/2));
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
        if (curPos.y < 0 || curPos.y > FlyingAndroidView.arenaHeight - getHeight())
            return true;
        return false;
    }
}
