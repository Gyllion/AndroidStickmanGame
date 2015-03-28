package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Gyllion on 23-3-2015.
 */
public class Background
{
    ArrayList<Texture> parallaxTextures;
    ArrayList<Sprite> spriteList;
    int[] yAxises;
    float previousX;

    public Background(ArrayList<Texture> textures, int[] yAxises, float initX)
    {
        parallaxTextures = textures;
        this.yAxises = yAxises;
        previousX = initX;

        // Add sprites to the spritelist dependent on the amount of textures in the texturelist
        int countSprites = 1;
        int countYAxises = 0;
        spriteList = new ArrayList<Sprite>();
        for(Texture texture : parallaxTextures)
        {
            // Skip the first texture since that is an unmoving background
            if (countYAxises > 0)
            {
                spriteList.add(new Sprite(texture));

                // Set start background position
                spriteList.get(countSprites).setPosition(0, yAxises[countYAxises]);

                // Create two copies background position
                spriteList.add(new Sprite(spriteList.get(countSprites).getTexture()));
                spriteList.get(countSprites + 1).setPosition(spriteList.get(countSprites).getX() - spriteList.get(countSprites).getTexture().getWidth(), spriteList.get(countSprites).getY());

                spriteList.add(new Sprite(spriteList.get(countSprites).getTexture()));
                spriteList.get(countSprites + 2).setPosition(spriteList.get(countSprites).getX() + spriteList.get(countSprites).getTexture().getWidth(), spriteList.get(countSprites).getY());

                countSprites += 3;
            }
            else
            {
                // Scale background lowest background on device
                spriteList.add(new Sprite(texture, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
            }
            countYAxises++;
        }

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    // Making the background scroll dependent on the x and y position of the player
    public void scroll(float x, float y)
    {
        // Update lowest background
        spriteList.get(0).setCenter(x + Gdx.graphics.getHeight() / 14, y + Gdx.graphics.getHeight() / 2);

        // Update every background with a factor
        for(int i = 1; i < spriteList.size(); i += 3)
        {
            // Move elements parallax
            if (i != spriteList.size() - 3)
            {
                spriteList.get(i).setX(spriteList.get(i).getX() + ((x - previousX) / 2));
                spriteList.get(i + 1).setX(spriteList.get(i + 1).getX() + ((x - previousX) / 2));
                spriteList.get(i + 2).setX(spriteList.get(i + 2).getX() + ((x - previousX) / 2));
            }

            // Shift background if it's out of bounds
            // Left side
            if(spriteList.get(i).getX() > x  + Gdx.graphics.getHeight())
            {
                spriteList.get(i).setX(spriteList.get(i + 2).getX() - spriteList.get(i).getTexture().getWidth());
            }

            if(spriteList.get(i + 1).getX() > x  + Gdx.graphics.getHeight())
            {
                spriteList.get(i + 1).setX(spriteList.get(i).getX() - spriteList.get(i).getTexture().getWidth());
            }

            if(spriteList.get(i + 2).getX() > x  + Gdx.graphics.getHeight())
            {
                spriteList.get(i + 2).setX(spriteList.get(i + 1).getX() - spriteList.get(i).getTexture().getWidth());
            }

            // Right side
            if(spriteList.get(i).getX() + spriteList.get(i).getTexture().getWidth() < x  - Gdx.graphics.getHeight())
            {
                spriteList.get(i).setX(spriteList.get(i + 1).getX() + spriteList.get(i).getTexture().getWidth());
            }

            if(spriteList.get(i + 1).getX() + spriteList.get(i).getTexture().getWidth() < x  - Gdx.graphics.getHeight())
            {
                spriteList.get(i + 1).setX(spriteList.get(i + 2).getX() + spriteList.get(i).getTexture().getWidth());
            }

            if(spriteList.get(i + 2).getX() + spriteList.get(i).getTexture().getWidth() < x  - Gdx.graphics.getHeight())
            {
                spriteList.get(i + 2).setX(spriteList.get(i).getX() + spriteList.get(i).getTexture().getWidth());
            }
        }

        // Updating the previous x
        previousX = x;
    }

    public void draw(SpriteBatch batch)
    {
        for(Sprite sprite : spriteList)
        {
            sprite.draw(batch);
        }
    }
}