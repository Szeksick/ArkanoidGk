package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;

public class Menuscreen implements Screen {
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
    private int padmoveY = -(ArkanoidGK.HEIGHT*10);
    Sound music = Gdx.audio.newSound(Gdx.files.internal("menu.wav"));
    Sound hover = Gdx.audio.newSound(Gdx.files.internal("hover1.wav"));
    Sound click = Gdx.audio.newSound(Gdx.files.internal("decide.mp3"));
    private static final int BUTTON_WIDTH = 264;
    private static final int BUTTON_HEIGHT= 66;
    private static final int PLAYBUTTON_Y= 250;
    private static final int EXITBUTTON_Y= 50;
    private static final int STATBUTTON_Y= 150;

    public Menuscreen(ArkanoidGK game){
        this.game = game;
        pad = new Texture("pad1.png");
        logo = new Texture("arkanoidlogo.png");
        playButonActive = new Texture("START.png");
        playButtonInacitve= new Texture("START-1.png");
        statsButtonActive = new Texture("Statystyki.png");
        statsButtonInactive = new Texture("Statystyki-1.png");
        exitButtonActive = new Texture("wyjscie.png");
        exitButtonInactive = new Texture("wyjscie-1.png");
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
        //rysowanie buttonow
        game.batch.draw(exitButtonInactive, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), EXITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(statsButtonInactive, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), STATBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(playButtonInacitve, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(logo, (ArkanoidGK.WIDTH/2-logo.getWidth()/2), ArkanoidGK.HEIGHT-200, logo.getWidth(), logo.getHeight());
        //wykrywanie klikniec w menu
        if(Gdx.input.getX() > (ArkanoidGK.WIDTH/2-BUTTON_WIDTH/2) && Gdx.input.getX() < (ArkanoidGK.WIDTH/2+BUTTON_WIDTH/2)){
            if(Gdx.input.getY() > ArkanoidGK.HEIGHT-EXITBUTTON_Y-BUTTON_HEIGHT&& Gdx.input.getY() < ArkanoidGK.HEIGHT-EXITBUTTON_Y ){
                game.batch.draw(exitButtonActive, (ArkanoidGK.WIDTH/2-BUTTON_WIDTH/2), EXITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.batch.draw(statsButtonInactive, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), STATBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.batch.draw(playButtonInacitve, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                if(Gdx.input.isTouched()){
                    click.play();
                    music.stop();
                    Gdx.app.exit();
                }
            }else if(Gdx.input.getY() > ArkanoidGK.HEIGHT-STATBUTTON_Y-BUTTON_HEIGHT && Gdx.input.getY() < ArkanoidGK.HEIGHT-STATBUTTON_Y){
                game.batch.draw(statsButtonActive, (ArkanoidGK.WIDTH/2-BUTTON_WIDTH/2), STATBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.batch.draw(exitButtonInactive, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), EXITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.batch.draw(playButtonInacitve, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                if(Gdx.input.isTouched()){
                    click.play();
                    this.dispose();
                    game.setScreen(new StatsScreen(game,0,0,999));
                    music.stop();
                }
            }else if(Gdx.input.getY() > ArkanoidGK.HEIGHT-PLAYBUTTON_Y-BUTTON_HEIGHT&& Gdx.input.getY() < ArkanoidGK.HEIGHT-PLAYBUTTON_Y){
                game.batch.draw(playButonActive, (ArkanoidGK.WIDTH/2-BUTTON_WIDTH/2), PLAYBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.batch.draw(exitButtonInactive, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), EXITBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.batch.draw(statsButtonInactive, (ArkanoidGK.WIDTH / 2 - BUTTON_WIDTH / 2), STATBUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
                if(Gdx.input.isTouched()){
                    click.play();
                    this.dispose();
                    game.setScreen(new UserManualScreen(game));
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
