package com.sudarabisheck.freshcatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PointsScreen implements Screen {

    private int gameScore;
    private FreshCatchGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private int highScore;

    public PointsScreen(FreshCatchGame game, int gameScore){
        this.game = game;
        this.gameScore = gameScore;
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 960);
        Preferences prefs;
        prefs = Gdx.app.getPreferences("FreshCatchData");
        highScore = prefs.getInteger("highScore");

        if(gameScore > highScore){
            highScore = gameScore;
            Resources.newHighScoreSound.play();
            prefs.putInteger("highScore",highScore);
            prefs.flush();
        }else
            Resources.lowScoreSound.play();

        Gdx.input.setCatchBackKey(true);

    }


    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0.2f,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Resources.pointsScreenBg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        camera.update();
        batch.setProjectionMatrix(camera.combined);


        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            this.game.setScreen(new MenuScreen(this.game));
        }

        if(Gdx.input.justTouched())
            game.setScreen(new GameScreen(game));

        batch.begin();
        batch.draw(Resources.pointsScreenBg,0,0);
        Resources.font.draw(batch, String.format("%d", gameScore), 1200, 600);
        Resources.font.draw(batch, String.format("%d", highScore), 1200, 430);
        batch.end();

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
