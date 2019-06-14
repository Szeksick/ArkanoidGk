package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import pl.kgdev.arkanoidgk.ArkanoidGK;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
* Author: Konrad Gugała
*
*
* */
public class StatsScreen implements Screen {
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
    Sound music = Gdx.audio.newSound(Gdx.files.internal("menu.wav"));
    Sound hover = Gdx.audio.newSound(Gdx.files.internal("hover1.wav"));
    Sound click = Gdx.audio.newSound(Gdx.files.internal("decide.mp3"));
    private static final int BUTTON_WIDTH = 264;
    private static final int BUTTON_HEIGHT= 66;
    private static final int PLAYBUTTON_Y= 100;
    private static final int STATBUTTON_Y= 50;
    ArrayList<String> stats;

    public StatsScreen(ArkanoidGK game, int points, int stars, int endedstage){
        this.game = game;
        this.points = points;
        this.stars=stars;
        this.endedstage = endedstage;
        stats = new ArrayList<String>();
        pad = new Texture("pad1.png");
           playButonActive = new Texture("START.png");
        playButtonInacitve= new Texture("START-1.png");
        background = new Texture("menubg.jpeg");
        music.loop(10);
//        music.play();

    }


    @Override
    public void show() {
        try {
            read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() throws IOException {
        File file = new File("arkanoidstats.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        Scanner in = new Scanner(file);
        while(in.hasNext()){
            String line = in.nextLine();
            stats.add(line);
        }
    }

        public void save()throws FileNotFoundException{
            PrintWriter zapis = new PrintWriter("arkanoidstats.txt");
            zapis.println("---"+points+"---"+stars+"---"+endedstage);
            zapis.close();
        }

    @Override
    public void render(float delta) {
        try {
            save();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        bgmoveY++;
        padmoveY+=5;
        game.font.setColor(Color.WHITE);
        for(int i=0; i< stats.size();i++){
            game.font.draw(game.batch, stats.get(i),(ArkanoidGK.WIDTH/2)-100,(ArkanoidGK.HEIGHT-(i*10))-40, 200, 1, false);
        }
        if(bgmoveY == 0) bgmoveY=-(ArkanoidGK.HEIGHT*4);
        game.batch.draw(background, 0, bgmoveY, ArkanoidGK.WIDTH, ArkanoidGK.HEIGHT * 6);
        game.batch.draw(pad, 100, padmoveY, pad.getWidth(), pad.getHeight());
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
                    if(endedstage == 999){
                        game.setScreen(new Menuscreen(game));
                    }else {
                        game.setScreen(new WinScreen(game, 0, 0, endedstage));
                    }
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
