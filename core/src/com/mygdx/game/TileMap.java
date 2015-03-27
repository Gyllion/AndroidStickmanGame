package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Gyllion on 18-3-2015.
 */
public class TileMap
{
    ArrayList<Block> blockList;

    public TileMap(Texture[] textArray, int[][] map, int startX, int startY)
    {
        blockList = new ArrayList<Block>();

        // Build the map by putting the blocks in the list with their position
        // Loop to build from high to low on the Y-axis
        int countDown = 0;
        for(int i = 0; i < map.length; i++)
        {
            countDown++;
        }

        // Loop through the multidimensional array
        for(int i = 0; i < map.length; i++)
        {
            countDown--;
            for(int j = 0; j < map[i].length; j++)
            {
                if (textArray[map[i][j]] != null)
                {
                    Texture texture = textArray[map[i][j]];

                    float x = startX + (texture.getWidth() * j);
                    float y = startY + (texture.getHeight() * countDown);
                    blockList.add(new Block(texture, new Vector2(x , y)));
                    Gdx.app.log("Blokje" + i + j, "x: " + x + "    y: " + y);
                }
            }
        }
    }

    public void draw(SpriteBatch batch)
    {
        for(Block block : blockList)
        {
            block.draw(batch);
        }
    }

    public ArrayList<Block> getBlockArray()
    {
        return blockList;
    }
}
