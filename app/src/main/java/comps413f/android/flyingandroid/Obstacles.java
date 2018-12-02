package comps413f.android.flyingandroid;

import android.content.Context;
import android.graphics.Canvas;

/** The obstacles. */
public class Obstacles {
    /** Pair of obstacles. */
    private Obstacle [] obstacles = new Obstacle[2];

    /** Constructor. */
    public Obstacles(Context context) {
        int x = FlyingAndroidView.arenaWidth;

        // Add code here
        // Task 1: Create a pair of obstacles
        // i. Randomize the distance between upper and lower obstacles
        int minDistance = (int) (0.4* FlyingAndroidView.arenaHeight);
        int maxDistance = (int) (0.6 * FlyingAndroidView.arenaHeight);
        int distance = minDistance + (int) (Math.random() * (maxDistance - minDistance));

        // ii. Randomize y position of the upper obstacle
        int minY = (int) (0.1 * FlyingAndroidView.arenaHeight);
        int maxY = (int) (0.9 * FlyingAndroidView.arenaHeight) - distance;
        int y = minY + (int) (Math.random() * (maxY - minY));

        // iii. Upper and lower obstacles creation
        obstacles[0] = new Obstacle(context, R.drawable.obstacle_upper);
        obstacles[0].setPosition(x, y - obstacles[0].getHeight());
        obstacles[1] = new Obstacle(context, R.drawable.obstacle_lower);
        obstacles[1].setPosition(x, y + distance);
    }
    public void increaseSpeed(){
        for (Obstacle obstacle:obstacles) {
            obstacle.increaseSpeed();
        }
    }
    /** Move the obstacles. */
    public void move() {
        for (Obstacle obstacle:obstacles) {
            obstacle.move();
        }
    }

    /** Draw the obstacles. */
    public void drawOn(Canvas canvas) {
        for (Obstacle obstacle:obstacles) {
            obstacle.drawOn(canvas);
        }
    }

    /** Evaluate if obstacles moved out from the arena. */
    public boolean isOutOfArena() {
        if (obstacles[0].isOutOfArena())
            return true;
        return false;
    }

    /** Evaluate if the input sprite collided with any obstacle. */
    public boolean collideWith(Sprite sprite) {
        for (Obstacle obstacle:obstacles) {
            if (obstacle.collideWith(sprite))
                return true;
        }
        return false;
    }

    /** Inner class, represents an individual obstacle, i.e., one pipe. */
    class Obstacle extends Sprite {
        /** Moving speed of obstacle, which is same as the one in scrolling background. */
        private float dx = Background.SpeedXMagnitude;

        /** Constructor. */
        public Obstacle(Context context, int resId) {
            drawable = context.getResources().getDrawable(resId);
        }
        @Override
        /** Move the obstacle. */
        public void move() {
            if (dx != 0) {
                // Update the new x position of the obstacle
                curPos.x += dx;

                // Update the boundary of the obstacle drawable
                updateBounds();
            }
        }
        public void increaseSpeed(){
            dx-=5;
        }

        @Override
        /** Evaluate if the obstacle has moved out from the arena. */
        public boolean isOutOfArena() {
            if (getCurPos().x + getWidth() < 0)
                return true;
            return false;
        }
    }
}