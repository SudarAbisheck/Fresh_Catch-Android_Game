package com.sudarabisheck.freshcatch;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;


public class FreshCatchGame extends Game {
    int counter=0;
    boolean alertBeingShown = false;


    private RequestHandler requestHandler;

    @Override
    public void create () {
        Resources.load();
        this.setScreen(new SplashScreen(this));

    }

    @Override
    public void render () {
        super.render();

        //Makes the Splash Screen pause for 2 seconds
        counter++;
        if(counter == 2)
            try{
                Thread.sleep(2000);
            }catch(Exception e){
                Gdx.app.log("Thread Fault","Exception in Splash Screen Thread Sleeping");
            }
    }

    @Override
    public void dispose(){
        Resources.dispose();
    }

    public FreshCatchGame(RequestHandler requestHandler)
    {
        this.requestHandler = requestHandler;
    }


    public void showConfirmDialog(){
        alertBeingShown = true;
        requestHandler.confirm(new ConfirmInterface(){
            @Override
            public void yes() {
              Gdx.app.exit();
                alertBeingShown = false;
            }


            @Override
            public void no() {
                alertBeingShown = false;

            }
        });
    }



}
