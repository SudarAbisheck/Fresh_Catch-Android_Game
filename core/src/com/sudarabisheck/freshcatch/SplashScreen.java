package com.sudarabisheck.freshcatch;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {

    private FreshCatchGame freshCatchGame;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public SplashScreen(FreshCatchGame FreshCatchGame) {
        this.freshCatchGame = FreshCatchGame;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Resources.splashTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch.begin();
        batch.draw(Resources.splashTexture,0,0);
        batch.end();


        freshCatchGame.setScreen(new MenuScreen(freshCatchGame));
        dispose();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {


    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
