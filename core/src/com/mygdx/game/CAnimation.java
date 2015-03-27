package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

import sun.rmi.runtime.Log;

/**
 * Created by Gyllion on 7-3-2015.
 */
public class CAnimation
{
    Texture texture;
    int cols;
    int rows;

    TextureRegion[][] tmp;      // Temporary array holding the frames in a multidimensional array
    TextureRegion[] frames;     // Array holding the frames in sequence
    TextureRegion currentFrame;
    int frameNumber = 0;        // Number that contains the index of the current frame
    Animation animation;
    float statetime = 0;

    public CAnimation(Texture texture, int cols, int rows, float statetime)
    {
        this.texture = texture;
        this.cols = cols;
        this.rows = rows;

        tmp = TextureRegion.split(texture, texture.getWidth() / cols, texture.getHeight() / rows);
        frames = new TextureRegion[cols * rows];
        this.statetime = statetime;

        // Fill the frames array with the tmp multidimensional array
        int index = 0;
        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                frames[index] = tmp[i][j];
                index++;
            }
        }

        animation = new Animation(statetime, frames);
    }


    public TextureRegion getCurrentFrame()
    {
        statetime += Gdx.graphics.getDeltaTime();

        return animation.getKeyFrame(statetime, true);
    }


    public float getFrameWidth()
    {
        return texture.getWidth() / cols;
    }


    public float getFrameHeight()
    {
        return texture.getHeight() / rows;
    }


    public void flip()
    {
        for(TextureRegion frame : frames)
        {
            frame.flip(true, false);
        }
    }
}
