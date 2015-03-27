package com.mygdx.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by Gyllion on 23-2-2015.
 */
public class Player
{

    // Animation properties
    Texture standTexture;
    Texture runTexture;
    CAnimation standAnimation;
    CAnimation runAnimation;
    boolean leftFlag = true;
    boolean rightFlag = true;
    int col = 12;
    int row = 1;

    // Drawing properties
    TextureRegion currentFrame;
    Sprite sprite;
    int scale = 130;

    // Position properties
    float gravity;
    Vector2 velocity;
    Vector2 position;

    // Collision properties
    Rectangle rectangle;
    ShapeRenderer sr;
    boolean isJumping = true;
    String collidedX = "";
    String collidedY = "";

    public Player(int startX, int startY)
    {
        standTexture = new Texture("AllyStandSheet.png");
        runTexture = new Texture("AllyRunSheet.png");
        standAnimation = new CAnimation(standTexture, 12, 1, 0.035f);
        runAnimation = new CAnimation(runTexture, 5, 1, 0.035f);

        sprite = new Sprite(standAnimation.getCurrentFrame());
        sprite.setCenter(startX, startY);

        position = new Vector2(startX, startY);
        velocity = new Vector2(0, 0);

        sr = new ShapeRenderer();
        rectangle = new Rectangle(position.x, position.y, standAnimation.getFrameWidth(), standAnimation.getFrameHeight());

        gravity = 0.2f;

        // Initializing flip
        if(Gdx.input.getAccelerometerY() * 2 < 0)
        {
            standAnimation.flip();
            runAnimation.flip();
        }

        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }


    // Main update method that will be called every game loop
    public void update(ArrayList<Block> blockList)
    {
        position = new Vector2(sprite.getX(), sprite.getY());

        // Rectangle updates
        rectangle.setPosition(position.x, position.y);

        // Animations depending on the accelerometer
        if (Gdx.input.getAccelerometerY() * 2 > 1)
        {
            currentFrame = runAnimation.getCurrentFrame();
            sprite.setScale((runAnimation.getFrameWidth() / scale) + 0.5f, runAnimation.getFrameHeight() / scale);
            collidedX = "left";
        }
        else if (Gdx.input.getAccelerometerY() * 2 < 1 && Gdx.input.getAccelerometerY() * 2 >= 0)
        {
            currentFrame = standAnimation.getCurrentFrame();
            sprite.setScale((standAnimation.getFrameWidth() / scale) + 0.3f, standAnimation.getFrameHeight() / scale);
            collidedX = "right";
        }
        else if(Gdx.input.getAccelerometerY() * 2 < -1)
        {
            currentFrame = runAnimation.getCurrentFrame();
            sprite.setScale((runAnimation.getFrameWidth() / scale) + 0.5f, runAnimation.getFrameHeight() / scale);
            collidedX = "right";
        }
        else
        {
            currentFrame = standAnimation.getCurrentFrame();
            sprite.setScale((standAnimation.getFrameWidth() / scale) + 0.3f, standAnimation.getFrameHeight() / scale);
            collidedX = "left";
        }

        // Flipping the animations
        if (Gdx.input.getAccelerometerY() * 2 < 0)
        {
            if (!leftFlag)
            {
                standAnimation.flip();
                runAnimation.flip();
            }

            leftFlag = true;
            rightFlag = false;
        }
        else
        {
            if (!rightFlag)
            {
                standAnimation.flip();
                runAnimation.flip();
            }

            rightFlag = true;
            leftFlag = false;
        }

        // Speed adjustments
        velocity.x = Gdx.input.getAccelerometerY() * 2;
        if (blockCollide(blockList))
        {
            velocity.y = 0f;
            isJumping = false;
        }
        else
        {
            isJumping = true;
        }

        // Check if the player is in the air
        // If so, apply gravity
        // If not, ignore gravity
        if (isJumping)
        {
            velocity.y -= 0.3f;
        }

        // Jumping input
        if (Gdx.input.isTouched() && !isJumping)
        {
            velocity.y = 13f;
            isJumping = true;
        }

        // Changing collided direction
        if (velocity.y >= 0)
        {
            collidedY = "up";
        }

        // Position update
        position.x += velocity.x;
        position.y += velocity.y;

        // Sprite updates
        sprite.setX(position.x);
        sprite.setY(position.y);
        sprite.setRegion(currentFrame);
    }


    public Sprite getSprite()
    {
        return sprite;
    }

    public void draw(SpriteBatch batch)
    {
        getSprite().draw(batch);
    }

    public void drawRectangle(Matrix4 matrix)
        {
            // Drawing the rectangle
            sr.setProjectionMatrix(matrix);
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.BLUE);
            sr.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            sr.end();
    }

    // Method to readjust in case a collision happens
    public void readjust(Rectangle rect)
    {
        // x Collision
        if (collidedX == "left")
        {
            position.x -= Gdx.input.getAccelerometerY() * 2;
        }
        else if(collidedX == "right")
        {
            position.x += Gdx.input.getAccelerometerY() * 2;
        }
    }

    // Loops through the entire map to check if a collision has occured
    public boolean blockCollide(ArrayList<Block> blockList)
    {
        for(Block block : blockList)
        {
            if (RectangleHelper.isTouchingTop(rectangle, block.getRectangle()))
            {
                return true;
            }
        }
        return false;
    }

    public Block getCollidedBlock(ArrayList<Block> blockList)
    {
        for(Block block : blockList)
        {
            if (RectangleHelper.isTouchingTop(rectangle, block.getRectangle()))
            {
                return block;
            }
        }
        return null;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.x;
    }
}
