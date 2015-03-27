package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Gyllion on 23-3-2015.
 */
public class RectangleHelper
{
    public static boolean isTouchingTop(Rectangle r1, Rectangle r2)
    {
        int penetrationMargin = 10;

        float r1Bottom = r1.getY();
        float r1Left = r1.getX();
        float r1Right = r1.getX() + r1.getWidth();

        float r2Top = r2.getY() + r2.getHeight();
        float r2Left = r2.getX();
        float r2Right = r2.getX() + r2.getWidth();

        return (r1Bottom >= r2Top - penetrationMargin &&
                r1Bottom <= r2Top + 1 &&
                r1Right >= r2Left + 5 &&
                r1Left <= r2Right - 5);
    }
}
