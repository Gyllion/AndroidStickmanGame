package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gyllion on 14-3-2015.
 */
public class Block
{
    Texture texture;
    Vector2 position;
    Rectangle rectangle;

    public Block(Texture texture, Vector2 position)
    {
        this.texture = texture;
        this.position = position;
        rectangle = new Rectangle(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void update()
    {

        rectangle.set(position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public Rectangle getRectangle()
    {
        return rectangle;
    }

    public void setRectangle(Rectangle newRectangle)
    {
        this.rectangle = newRectangle;
    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(texture, position.x, position.y);
    }

    public Vector2 getBlockPosition()
    {
        return new Vector2(position.x, position.y);
    }

    public void setBlockPosition(Vector2 position)
    {
        this.position = position;
    }

    public int getTextureHeight()
    {
        return texture.getHeight();
    }

    public Texture getTexture()
    {
        return texture;
    }
}
