package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MainGame extends ApplicationAdapter implements GestureListener
{
	SpriteBatch batch;
	Player player;
    Camera camera;
    TileMap map;
    Background background;
	
	@Override
	public void create () {
        // Main initialize
		batch = new SpriteBatch();
		player = new Player(50, 350);
        camera = new Camera(player.getX(), player.getY());

        // Creating background
        ArrayList<Texture> landscapeList = new ArrayList<Texture>();
        landscapeList.add(new Texture("Landscape/layer-1.png"));
        landscapeList.add(new Texture("Landscape/layer-2.png"));
        landscapeList.add(new Texture("Landscape/layer-3.png"));
        landscapeList.add(new Texture("Landscape/layer-4.png"));
        background = new Background(landscapeList, new int[]{0, 0, 0, -50}, player.getX());

        // Creating the map
        Texture[] textArray = new Texture[3];
        textArray[1] = new Texture("GrassBlock.png");
        int[][] tmpMap = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        map = new TileMap(textArray, tmpMap, -1000, -250);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update methods
        player.update(map.getBlockArray());
        camera.moveCamera(player.getX() + 50, 200);
        background.scroll(player.getX(), 0);

        // Draw methods
        batch.setProjectionMatrix(camera.getCombined());
		batch.begin();
        background.draw(batch);
        player.draw(batch);
        map.draw(batch);
		batch.end();

        player.drawRectangle(camera.getCombined());
	}

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
