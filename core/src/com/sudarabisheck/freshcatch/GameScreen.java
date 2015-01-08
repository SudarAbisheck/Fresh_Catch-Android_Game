package com.sudarabisheck.freshcatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class GameScreen implements Screen {

    private FreshCatchGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private int fishCaught;
    private int highScore;
    private Preferences prefs;
    private Rectangle fishnetRec;
    private Array<Rectangle> fishArray;
    private Array<Rectangle> crabArray;
    private Array<Rectangle> piranhaArray;
    private long lastFishSpawnTime;
    private long lastPiranhaSpawnTime;
    private long lastCrabSpawnTime;
    private long fishSpeed;

    private boolean isFirst;


    public GameScreen(FreshCatchGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        fishCaught = 0;
        fishSpeed = 1100;
        lastPiranhaSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        lastCrabSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        prefs = Gdx.app.getPreferences("FreshCatchData");
        highScore = prefs.getInteger("highScore",0);
        fishnetRec = new Rectangle();
        fishnetRec.x = 1600/2 - 256/2;
        fishnetRec.y = -10;
        fishnetRec.width = 256;
        fishnetRec.height = 256;

        fishArray = new Array<Rectangle>();
        crabArray = new Array<Rectangle>();
        piranhaArray = new Array<Rectangle>();


        camera.setToOrtho(false,1600,960);
        Gdx.input.setCatchBackKey(true);

        isFirst = true;
        spawnFish();

    }
    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0,0,0.2f,1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);


        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Resources.gameScreenBg.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Resources.fishnet.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Resources.piranhaImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Resources.xRayFish.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Resources.crabImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        if(fishCaught > highScore)
            prefs.putInteger("highScore", fishCaught);



        if(Gdx.input.isKeyPressed(Input.Keys.BACK)) {
            this.game.setScreen(new MenuScreen(this.game));
        }

        if(Gdx.input.isTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(),0);
            camera.unproject(touchPos);
            fishnetRec.x = touchPos.x - 256/2;
        }


        if(fishnetRec.x < 0) fishnetRec.x = 0;
        if(fishnetRec.x > 1600-256) fishnetRec.x = 1600-256;



        Iterator<Rectangle> iter = fishArray.iterator();
        Iterator<Rectangle> crabIter = crabArray.iterator();
        Iterator<Rectangle> piranhaIter = piranhaArray.iterator();



        if(TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - lastFishSpawnTime > 1200)  spawnFish();
        while(iter.hasNext()){
            try{
                Rectangle fish = iter.next();
                fish.y -= fishSpeed * Gdx.graphics.getDeltaTime();
                if(fish.y + 256 <0) {
                    iter.remove();
                /*Resources.gameOverSound.play();
                Gdx.input.vibrate(200);
                game.setScreen(new PointsScreen(game, fishCaught)); */
                }

                if(fish.overlaps(fishnetRec)) {
                    Resources.popSound.play();
                    iter.remove();
                    fishCaught += 1;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Gdx.app.log("Error","Exception in iterating XRay Fish Array");
            }
        }



        if(TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - lastCrabSpawnTime > 7000)  spawnCrab();
        while(crabIter.hasNext()){
            try{
                Rectangle crab = crabIter.next();
                crab.y -= fishSpeed * Gdx.graphics.getDeltaTime();
                if(crab.y + 256 <0) {
                    crabIter.remove();
                }

                if(crab.overlaps(fishnetRec)) {
                    Resources.crabCatchSound.play();
                    crabIter.remove();
                    fishCaught += 10;
                }
            }catch (ArrayIndexOutOfBoundsException e){
                Gdx.app.log("Error","Exception in iterating Crab Array");
            }
        }



        if(TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - lastPiranhaSpawnTime > 4000)  spawnPiranha();
        while(piranhaIter.hasNext()){
            try{
                Rectangle piranha = piranhaIter.next();
                piranha.y -= fishSpeed * Gdx.graphics.getDeltaTime();
                if(piranha.y + 256 <0) piranhaIter.remove();
                if(piranha.overlaps(fishnetRec)) {
                    Resources.biteSound.play();
                    Gdx.input.vibrate(200);
                    game.setScreen(new PointsScreen(game, fishCaught));
                    piranhaIter.remove();
                }
            }catch (ArrayIndexOutOfBoundsException e){
                Gdx.app.log("Error","Exception in iterating Piranha Array");
                break;
            }

        }

        batch.begin();
        batch.draw(Resources.gameScreenBg,0,0);
        Resources.font.draw(batch,String.format("%d",fishCaught),20,930);
        batch.draw(Resources.fishnet,fishnetRec.x,fishnetRec.y);
        for(Rectangle fish : fishArray)
            batch.draw(Resources.xRayFish,fish.x, fish.y);
        for(Rectangle crab : crabArray)
            batch.draw(Resources.crabImage,crab.x, crab.y);
        for(Rectangle piranha : piranhaArray)
            batch.draw(Resources.piranhaImage,piranha.x, piranha.y);

        batch.end();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Resources.bgm.setVolume(0.75f);
        Resources.bgm.play();
    }

    @Override
    public void hide() {
        prefs.flush();
        Resources.bgm.pause();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        game.dispose();
        batch.dispose();

    }

    public void spawnFish(){
        Rectangle fish = new Rectangle();
        fish.x = MathUtils.random(0,1600-256);
        fish.y = 960;
        fish.width = 256;
        fish.height = 256;
        if(!isFirst)
            fishArray.add(fish);
        isFirst = false;

        lastFishSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        if(fishSpeed < 2000)
            fishSpeed += 20;
        else
            fishSpeed = 2000;
    }

    public void spawnCrab(){
        Rectangle crab = new Rectangle();
        crab.x = MathUtils.random(0,1600-256);
        crab.y = 960;
        crab.width =256;
        crab.height = 256;
        crabArray.add(crab);
        lastCrabSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
    }


    public void spawnPiranha(){
        Rectangle piranha = new Rectangle();
        piranha.x = MathUtils.random(0,1600-256);
        piranha.y = 960;
        piranha.width =256;
        piranha.height = 256;
        piranhaArray.add(piranha);
        lastPiranhaSpawnTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
    }

}
