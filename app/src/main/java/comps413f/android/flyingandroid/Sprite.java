package comps413f.android.flyingandroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/** The sprite. */
abstract public class Sprite {
    /** Top-left position, x and y, of the sprite in world coordinates. */
    final protected PointF curPos = new PointF();
    /** Drawable for the sprite. */
    Drawable drawable;

    /** Returns the width of this sprite. */
    public int getWidth() {
        return drawable.getIntrinsicWidth();
    }

    /** Returns the height of this sprite. */
    public int getHeight() {
        return drawable.getIntrinsicHeight();
    }

    /** Returns the position of the sprite. */
    public PointF getCurPos() {
        return curPos; 
    }

    /** Returns the bounds of this sprite. */
    public Rect getBounds() {
        return drawable.getBounds();
    }

    /** Returns the bitmap of this sprite. */
    public Bitmap getBitmap() {
        return ((BitmapDrawable) drawable.getCurrent()).getBitmap();
    }

    /** Returns the drawable for this sprite. */
    public Drawable getDrawable() {
        return drawable;
    }

    /**
     * Sets the position of this sprite. Subclasses should use this method to
     * update the position so that bounds are updated accordingly.
     */
    public void setPosition(float x, float y) {
        curPos.set(x, y);
        updateBounds();
    }

    /** Updates the bounds of the drawable for drawing. */
    public void updateBounds() {
        if (drawable != null) {
            drawable.setBounds((int) curPos.x, (int) curPos.y, (int) curPos.x + getWidth(), (int) curPos.y + getHeight());
        }
    }

    /** Draws this sprite. */
    public void drawOn(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }

    /** Returns whether this sprite collides with another sprite. */
    public boolean collideWith(Sprite obstacle) {
        return collideWith(obstacle.getBounds(), obstacle.getBitmap());
    }

    /** Returns whether this sprite collides with another sprite, by bounds and bitmap. */
    public boolean collideWith(Rect bounds, Bitmap bitmap) {
        return CollisionDetection.collidePixel(getBounds(), bounds, getBitmap(), bitmap);
    }

    /** Move the sprite. */
    abstract public void move();
    /** Evaluate if the sprite has moved out from the arena. */
    abstract public boolean isOutOfArena();
}