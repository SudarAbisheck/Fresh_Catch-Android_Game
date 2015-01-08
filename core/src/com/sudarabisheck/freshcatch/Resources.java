package com.sudarabisheck.freshcatch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Resources {

    //Splash screen
    static Texture splashTexture;

    //Menu Screen
    static Texture menuScreenBg;

    //Rule Screen
    static Texture ruleScreenBg;

    //Game Screen
    static Texture gameScreenBg,fishnet,piranhaImage,xRayFish,crabImage;
    static Sound biteSound, popSound,crabCatchSound;
    static Music bgm;
    static BitmapFont font;

    //Points Screen
    static Texture pointsScreenBg;
    static Sound newHighScoreSound, lowScoreSound;



    public static void load(){
        splashTexture = new Texture(Gdx.files.internal("images/appchemy.png"));
        menuScreenBg = new Texture(Gdx.files.internal("images/MainScreen.png"));
        ruleScreenBg = new Texture(Gdx.files.internal("images/RuleScreen.png"));
        gameScreenBg = new Texture(Gdx.files.internal("images/GameScreen.png"));
        fishnet = new Texture(Gdx.files.internal("images/fishnet.png"));
        piranhaImage = new Texture(Gdx.files.internal("images/piranha.png"));
        xRayFish = new Texture(Gdx.files.internal("images/xray_fish.png"));
        crabImage = new Texture(Gdx.files.internal("images/crab.png"));
        biteSound = Gdx.audio.newSound(Gdx.files.internal("sounds/piranha_bite_sound.mp3"));
        popSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3"));
        crabCatchSound = Gdx.audio.newSound(Gdx.files.internal("sounds/crab_catch.ogg"));
        //gameOverSound = Gdx.audio.newSound(Gdx.files.internal("sounds/game_over.ogg"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/game_bgm.ogg"));
        bgm.setLooping(true);
        font = new BitmapFont(Gdx.files.internal("fonts/new.fnt"));
        pointsScreenBg = new Texture(Gdx.files.internal("images/PointsScreen.png"));
        newHighScoreSound = Gdx.audio.newSound(Gdx.files.internal("sounds/newHighScore.ogg"));
        lowScoreSound = Gdx.audio.newSound(Gdx.files.internal("sounds/lowScore.ogg"));
    }

    public static void dispose(){
        splashTexture.dispose();
        menuScreenBg.dispose();
        ruleScreenBg.dispose();
        gameScreenBg.dispose();
        fishnet.dispose();
        piranhaImage.dispose();
        xRayFish.dispose();
        crabImage.dispose();
        biteSound.dispose();
        popSound.dispose();
        crabCatchSound.dispose();
        //gameOverSound.dispose();
        bgm.dispose();
        font.dispose();
        pointsScreenBg.dispose();
        newHighScoreSound.dispose();
        lowScoreSound.dispose();
    }


}
