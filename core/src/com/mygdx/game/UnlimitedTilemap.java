package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Gyllion on 28-3-2015.
 */
public class UnlimitedTilemap
{
    ArrayList<Block> tileMap;
    float previousX = 0;

    public UnlimitedTilemap(Texture blockTexture, float x, float y)
    {
        tileMap = new ArrayList<Block>();

        // Fill the tilemap with blocks that fills the screen
        float initX = x - Gdx.graphics.getHeight();
        float totalWidth = 0;
        while(totalWidth <= (Gdx.graphics.getHeight() * 2))
        {
            if (tileMap.size() == 0)
            {
                tileMap.add(new Block(blockTexture, new Vector2(initX, y)));
            }
            else
            {
                tileMap.add(new Block(blockTexture, new Vector2(tileMap.get(tileMap.size() - 1).getBlockPosition().x + blockTexture.getWidth(), y)));
            }

            totalWidth += blockTexture.getWidth();
        }

        // Add one extra for the funzies
        tileMap.add(new Block(blockTexture, new Vector2(tileMap.get(tileMap.size() - 1).getBlockPosition().x + blockTexture.getWidth(), y)));

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    // Draw method that draws all the blocks in the tileMap
    public void draw(SpriteBatch batch)
    {
        for(Block block : tileMap)
        {
            block.draw(batch);
        }
    }

    public void scroll(float x)
    {
        for(int i = 0; i < tileMap.size(); i++)
        {
            // Check to see if player is moving left
            if (previousX > x)
            {
                // If the last block is out of bounds (right) switch this one to the left
                if (tileMap.get(i).getBlockPosition().x  >  x  + Gdx.graphics.getHeight())
                {
                    // Change position of last block
                    tileMap.get(i).setBlockPosition(new Vector2(tileMap.get(0).getBlockPosition().x - tileMap.get(i).getTexture().getWidth(), tileMap.get(i).getBlockPosition().y));

                    // Change rectangle position of last block
                    tileMap.get(i).setRectangle(new Rectangle(tileMap.get(i).getBlockPosition().x, tileMap.get(i).getBlockPosition().y, tileMap.get(i).getTexture().getWidth(), tileMap.get(i).getTexture().getHeight()));

                    // Rebuild the tilemap in the right order
                    for(int j = 0; j < tileMap.size(); j++)
                    {
                        // Check to prevent out of bounds exception
                        if (j != tileMap.size())
                        {
                            Collections.swap(tileMap, j, tileMap.size() - 1);        // Swapping the entries in the right order
                        }
                    }
                }
            }
            // Check to see if player is moving right
            else
            {
                // If the last block is out of bounds (left) switch this one to the right
                if (tileMap.get(i).getBlockPosition().x + tileMap.get(i).getTexture().getWidth() <  x - Gdx.graphics.getHeight())
                {
                    // Change position of first block
                    tileMap.get(i).setBlockPosition(new Vector2(tileMap.get(tileMap.size() - 1).getBlockPosition().x + tileMap.get(i).getTexture().getWidth(), tileMap.get(i).getBlockPosition().y));

                    // Change rectangle position of last block
                    tileMap.get(i).setRectangle(new Rectangle(tileMap.get(i).getBlockPosition().x, tileMap.get(i).getBlockPosition().y, tileMap.get(i).getTexture().getWidth(), tileMap.get(i).getTexture().getHeight()));

                    // Rebuild the tilemap in the right order
                    for(int j = tileMap.size() - 1; j > 0; j--)
                    {
                        Collections.swap(tileMap, 0, j);        // Swapping the entries in the right order
                    }
                }
            }
        }

        // Maintaining variable to check which direction the player is going (left or right)
        previousX = x;
    }

    public ArrayList<Block> getTileMap()
    {
        return tileMap;
    }
}
