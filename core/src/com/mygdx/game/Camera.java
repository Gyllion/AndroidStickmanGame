package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by Gyllion on 17-3-2015.
 */
public class Camera
{
    private OrthographicCamera camera;

    public Camera(float startPosX, float startPosY)
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.translate(startPosX, startPosY);
    }

    public void moveCamera(float x, float y)
    {
        camera.position.set(x, y, 0);
        camera.update();
    }

    public Matrix4 getCombined()
    {
        return camera.combined;
    }
}
