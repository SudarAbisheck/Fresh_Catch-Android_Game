package com.sudarabisheck.freshcatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RuleScreen implements Screen {

    private FreshCatchGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    public RuleScreen(FreshCatchGame game){
        this.game = game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        camera.setToOrtho(false,1600,960);
        Gdx.input.setCatchBackKey(true);
    }
    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0.2f,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Resources.ruleScreenBg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch.begin();
        batch.draw(Resources.ruleScreenBg,0,0);
        batch.end();

        if(Gdx.input.justTouched())
            game.setScreen(new GameScreen(game));

        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            this.game.setScreen(new MenuScreen(this.game));
        }
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
        game.dispose();

    }
}
