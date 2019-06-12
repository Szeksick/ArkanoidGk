package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.elements.Ball;
import pl.kgdev.arkanoidgk.elements.Paddle;
import pl.kgdev.arkanoidgk.eventscollisions.Explosion;
import pl.kgdev.arkanoidgk.walls.Block;
import pl.kgdev.arkanoidgk.walls.BlocksArrayCreaor;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;

public class Stage1 implements Screen {

    private ArkanoidGK game;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private BlocksArrayCreaor blocksArrayCreaor = new BlocksArrayCreaor();
    private ArrayList<Block> blocks = blocksArrayCreaor.create();
    private ArrayList<Explosion> boomholder = new ArrayList<Explosion>();
    private Paddle pad;
    private Texture background = new Texture("stars.jpg");

    private Sound music = Gdx.audio.newSound(Gdx.files.internal("stage1.wav"));
    private Sound hitpad = Gdx.audio.newSound(Gdx.files.internal("hit-1.wav"));
    private Sound hitblock = Gdx.audio.newSound(Gdx.files.internal("hit-4.wav"));
    private Kokpit kokpit = new Kokpit();
    private int points=0;
    private int stars=5;

    public Stage1 (ArkanoidGK game){
        this.game = game;
        pad = new Paddle(ArkanoidGK.WIDTH/2,50,450);
        balls.add(new Ball(ArkanoidGK.WIDTH/2, ArkanoidGK.HEIGHT/2, 250));
        music.loop(30);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
            //kolizje piłki
            for(Ball ball:balls){
            //z blokami
            for(Block block:blocks){
                 if(ball.getCollisionRect().collidesWith(block.getCollisionRect())) {
                    System.out.println("KOLIZJA PIŁKI Z BLOKIEM");
                    block.gethit();
                    points++;
                    if (ball.getY()+ball.getHeight() < block.getY() || ball.getY() > block.getY()+block.getHeight()) {
                         ball.reverseYdir();
                    }
                    hitblock.play();
                    if (ball.getX() < block.getX() || ball.getX() > block.getX()+block.getWidth()) {
                        ball.reverseXdir();
                    }
                }
            }
            //z paletka
            if(ball.getCollisionRect().collidesWith(pad.getCollisionRect())){
                    System.out.print("KOLIZJA PIŁKI Z PALETKĄ");
                if(ball.getY() > pad.getY()+pad.getHeight()){
                    ball.reverseYdir();
                }
                    hitpad.play();
                if(ball.getY() < pad.getY()+pad.getHeight()){
                    ball.reverseXdir();
                }
            }
            ball.move();
        }

        //
        //Sterowanie paletka
        if (Gdx.input.isKeyJustPressed(R) && stars >0){
            balls.add(new Ball(ArkanoidGK.WIDTH/2, ArkanoidGK.HEIGHT/2, 250));
            stars--;
        }
        if (Gdx.input.isKeyPressed(LEFT)) {
            if(pad.getX() > 0){ pad.moveLeft();}
        } else if (Gdx.input.isKeyPressed(RIGHT)) {
            if(pad.getX()< (ArkanoidGK.WIDTH-pad.getWidth())){pad.moveRight();}
        }
//       Usuwanie piłek
        ArrayList<Ball> balls_toremove = new ArrayList<Ball>();
        for(Ball ball:balls){
          if(ball.getY()<0){
                balls_toremove.add(ball);
            }
        }

        //update wybuchow
        ArrayList<Explosion> exploend = new ArrayList<Explosion>();
        for(Explosion boom: boomholder){
           for(Block block:blocks){
                if (boom.getCollisionRect().collidesWith(block.getCollisionRect())) {
                    block.gethit();
                    points++;
                }
            }
            boom.update(delta);
        //jezeli ekplozja zmienila flage remove na true to dodaje do listy bombydousuniecia
            if(boom.remove) {
                exploend.add(boom);
            }
        }

        //Usuwanie zniszczonych blokow
        ArrayList<Block> blocks_toremove = new ArrayList<Block>();
        for(Block block:blocks){
            block.update(boomholder);
            if(block.remove){
                blocks_toremove.add(block);
            }
        }
        if(blocks.size()== blocks_toremove.size()){
            this.dispose();
            game.setScreen(new Testscreen(game));
        }
        /*
        * Nie odkryłem dla czego ale w przydapdku blokow usuwana jest cała kolekcja
        * blocks.removeAll(blocks_toremove);
        */
        balls.removeAll(balls_toremove);
        boomholder.removeAll(exploend);
        //Czyszczenie ekranu
        Gdx.gl.glClearColor(130/255f, 130/255f, 130/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Rysowanie elementow
        game.batch.begin();
        game.batch.draw(background,0,0,ArkanoidGK.WIDTH, ArkanoidGK.HEIGHT);
        pad.render(this.game.batch);
        if(balls.size()==0){
            game.font.setColor(Color.WHITE);
            game.font.draw(game.batch,"Aby stworzyć nową gwiazdę wciśnij R",(ArkanoidGK.WIDTH/2)-100,ArkanoidGK.HEIGHT/2);
        }
        for(Ball ball:balls) ball.render(this.game.batch);
        for(Block block:blocks) block.render(this.game.batch);
        for(Explosion boom: boomholder) boom.render(this.game.batch);
        kokpit.draw(this.game,points, stars);
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
        music.dispose();

    }
}
