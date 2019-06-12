package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.elements.Ball;
import pl.kgdev.arkanoidgk.elements.Bullet;
import pl.kgdev.arkanoidgk.elements.Paddle;
import pl.kgdev.arkanoidgk.eventscollisions.Explosion;
import pl.kgdev.arkanoidgk.mobs.SpacePig;
import pl.kgdev.arkanoidgk.walls.Block;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;

public class Testscreen implements Screen {

    ArkanoidGK game;
    ArrayList<Ball> balls = new ArrayList<Ball>();
    ArrayList<Block> blocks = new ArrayList<Block>();
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    ArrayList<SpacePig> pigs = new ArrayList<SpacePig>();
    ArrayList<Explosion> boomholder = new ArrayList<Explosion>();
    Paddle pad;
    private int to_win = 0;
    Texture background = new Texture("stars.jpg");
    Sound music = Gdx.audio.newSound(Gdx.files.internal("stage1.wav"));
    Sound boom = Gdx.audio.newSound(Gdx.files.internal("bomb.mp3"));
    Sound hitpad = Gdx.audio.newSound(Gdx.files.internal("hit-1.wav"));
    Sound hitblock = Gdx.audio.newSound(Gdx.files.internal("hit-4.wav"));
    Sound click = Gdx.audio.newSound(Gdx.files.internal("hover.wav"));
    Sound restock = Gdx.audio.newSound(Gdx.files.internal("restock.wav"));
    private int ammo = 0;


    public Testscreen(ArkanoidGK game){
        this.game = game;
        pad = new Paddle(ArkanoidGK.WIDTH/2,50,450);
//        balls.add(new Ball(ArkanoidGK.WIDTH/2, ArkanoidGK.HEIGHT/2, 250));
        music.loop(30);
    }

    @Override
    public void show() {
//        for(int i=4;i <10;i++){
//            for(int j = 0; j<12;j++){
//                to_win++;
//                blocks.add(new Block(((ArkanoidGK.WIDTH/12)*j),ArkanoidGK.HEIGHT-((ArkanoidGK.HEIGHT/24)*i)));
//            }
//        }
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
//                    if (ball.getY()+ball.getHeight() < block.getY() || ball.getY() > block.getY()+block.getHeight()) {
                         ball.reverseYdir();
//                    }
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

        //Sterowanie paletka
        if (Gdx.input.isKeyPressed(R)){
                   ammo += 10;
        }
        if (Gdx.input.isKeyPressed(UP)) {
            pad.moveUp();
        } else if (Gdx.input.isKeyPressed(DOWN)) {
            pad.moveDown();
        } else if (Gdx.input.isKeyPressed(LEFT)) {
            if(pad.getX() > 0){ pad.moveLeft();}
        } else if (Gdx.input.isKeyPressed(RIGHT)) {
            if(pad.getX()< (ArkanoidGK.WIDTH-pad.getWidth())){pad.moveRight();}
        }
//        pigs
        if (Gdx.input.isKeyPressed(P)){
                pigs.add(new SpacePig(ArkanoidGK.WIDTH/2, ArkanoidGK.HEIGHT/2));
        }
        ArrayList<SpacePig> pigs_toremove = new ArrayList<SpacePig>();
        for(SpacePig pig: pigs){
            pig.update();
            for(Block block:blocks){
                if(pig.getCollisionRect().collidesWith(block.getCollisionRect())){
                    pig.reverse();
                }
            }
            if(pig.remove) {
                pigs_toremove.add(pig);
            }
        }
//        pigs end
        //bullets
        if (Gdx.input.isKeyPressed(R)){
            ammo += 10;
            restock.play();
        }
        if (Gdx.input.isKeyJustPressed(SPACE)){
            if(ammo > 0) {
                bullets.add(new Bullet(pad.getX()+5, pad.getY()));
                bullets.add(new Bullet(pad.getX() + pad.getWidth() - 5, pad.getY()));
                ammo--;
            }else{
                click.play();
            }
        }
        ArrayList<Bullet> bullets_toremove = new ArrayList<Bullet>();
        for(Bullet shot: bullets){
            shot.update();
            for(Block block:blocks){
                if(shot.getCollisionRect().collidesWith(block.getCollisionRect())){
                    block.gethit();
                    shot.gethit();
                }
            }
            if(shot.remove) {
                bullets_toremove.add(shot);
            }
        }
//        bullets end
        //Usuwanie zniszczonych blokow
        ArrayList<Block> blocks_toremove = new ArrayList<Block>();
        for(Block block:blocks){
            block.update(boomholder);
//            if(block.remove){
//                blocks_toremove.add(block);
//            }
        }
        //update wybuchow
        ArrayList<Explosion> exploend = new ArrayList<Explosion>();
        for(Explosion boom: boomholder){
            boom.update(delta);
//            jezeli bomba zmienila flage remove na true to dodaje do listy bombydousuniecia
            if(boom.remove) {
                exploend.add(boom);
            }
        }
//        System.out.println("bloki do usuniecia: "+blocks_toremove.size());
//        System.out.println("Przed usunieciem:"+blocks.size());
//        blocks.removeAll(blocks_toremove);
        System.out.println("Swinie na ekranie:"+pigs.size());
        boomholder.removeAll(exploend);
        bullets.removeAll(bullets_toremove);
        pigs.removeAll(pigs_toremove);
        //Czyszczenie ekranu
        Gdx.gl.glClearColor(130/255f, 130/255f, 130/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Rysowanie elementow
        game.batch.begin();
        game.font.setColor(Color.GREEN);
        game.batch.draw(background,0,0,ArkanoidGK.WIDTH, ArkanoidGK.HEIGHT);
        game.font.draw(game.batch,"Punkty : ",150,25);
        game.font.draw(game.batch,"ZYCIE : ",300,25);
        game.font.setColor(Color.RED);
        game.font.draw(game.batch,Integer.toString(20),360,25);
        game.font.draw(game.batch,"POZIOM 1",360,ArkanoidGK.HEIGHT-50);
        pad.render(this.game.batch);
        for(Ball ball:balls) ball.render(this.game.batch);
        for(Block block:blocks) block.render(this.game.batch);
        for(Explosion boom: boomholder) boom.render(this.game.batch);
        for(Bullet shot:bullets) shot.render(this.game.batch);
        for(SpacePig pig:pigs) pig.render(this.game.batch);
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
