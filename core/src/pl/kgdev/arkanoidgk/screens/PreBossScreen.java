package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import pl.kgdev.arkanoidgk.ArkanoidGK;

/*
* Author: Konrad Gugała
*
*
* */
public class PreBossScreen implements Screen {
    ArkanoidGK game;
    Texture playButonActive;
    Texture playButtonInacitve;
    Texture statsButtonActive;
    Texture statsButtonInactive;
    Texture exitButtonActive;
    Texture exitButtonInactive;
    Texture background;
    Texture logo;
    Texture pad;
    private int bgmoveY = -(ArkanoidGK.HEIGHT*4);
    private int padmoveY = -(ArkanoidGK.HEIGHT*3);
    private int points, stars, endedstage, displayed_points;
    Sound music = Gdx.audio.newSound(Gdx.files.internal("fail.wav"));
    Sound hover = Gdx.audio.newSound(Gdx.files.internal("hover1.wav"));
    Sound click = Gdx.audio.newSound(Gdx.files.internal("decide.mp3"));
    private static final int BUTTON_WIDTH = 264;
    private static final int BUTTON_HEIGHT= 66;
    private static final int PLAYBUTTON_Y= 100;
    private static final int STATBUTTON_Y= 50;

    public PreBossScreen(ArkanoidGK game, int points, int stars, int endedstage){
        this.game = game;
        this.points = points;
        this.stars=stars;
        this.endedstage = endedstage;
        pad = new Texture("pad1.png");
        logo = new Texture("Group 4.png");
        playButonActive = new Texture("START.png");
        playButtonInacitve= new Texture("START-1.png");
        background = new Texture("menubg.jpeg");
        music.loop(10);
//        music.play();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        bgmoveY++;
        padmoveY+=5;
        if(bgmoveY == 0) bgmoveY=-(ArkanoidGK.HEIGHT*4);
        game.batch.draw(background, 0, bgmoveY, ArkanoidGK.WIDTH, ArkanoidGK.HEIGHT * 6);
        game.batch.draw(pad, 100, padmoveY, pad.getWidth(), pad.getHeight());
        game.batch.draw(logo, (ArkanoidGK.WIDTH/2)-(logo.getWidth()/4), ArkanoidGK.HEIGHT-400, logo.getWidth()/2, logo.getHeight()/2);
        //rysowanie buttonow
        game.batch.draw(playButtonInacitve, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        //wykrywanie klikniec w menu
        if(Gdx.input.getX() > (ArkanoidGK.WIDTH/2-BUTTON_WIDTH/2) && Gdx.input.getX() < (ArkanoidGK.WIDTH/2+BUTTON_WIDTH/2)){
            if(Gdx.input.getY() > ArkanoidGK.HEIGHT-PLAYBUTTON_Y-BUTTON_HEIGHT&& Gdx.input.getY() < ArkanoidGK.HEIGHT-PLAYBUTTON_Y){
                game.batch.draw(playButonActive, (ArkanoidGK.WIDTH/2-BUTTON_WIDTH/2), PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                if(Gdx.input.isTouched()){
                    click.play();
                    this.dispose();
                    System.out.println(endedstage);
                    game.setScreen(new BossFight(game, 0, 0));

                    music.stop();
                }
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
