package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.elements.Ball;
import pl.kgdev.arkanoidgk.elements.Bullet;
import pl.kgdev.arkanoidgk.elements.Paddle;
import pl.kgdev.arkanoidgk.eventscollisions.BlackHole;
import pl.kgdev.arkanoidgk.eventscollisions.Explosion;
import pl.kgdev.arkanoidgk.mobs.Boss;
import pl.kgdev.arkanoidgk.mobs.SpacePig;
import pl.kgdev.arkanoidgk.walls.Block;
import pl.kgdev.arkanoidgk.walls.BlocksArrayCreaor;

import java.util.ArrayList;

import static com.badlogic.gdx.Input.Keys.*;

public class BossFight implements Screen {

    private ArkanoidGK game;
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private BlocksArrayCreaor blocksArrayCreaor = new BlocksArrayCreaor();
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private ArrayList<Explosion> boomholder = new ArrayList<Explosion>();
    private ArrayList<SpacePig> pigs = new ArrayList<SpacePig>();
    private boolean allow_pigs = true;
    private boolean pigs_relased = false;

    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private Paddle pad;

    private Texture background = new Texture("boss_fight.jpg");
    private Sound music = Gdx.audio.newSound(Gdx.files.internal("stage1.wav"));
    private Sound hitpad = Gdx.audio.newSound(Gdx.files.internal("hit-1.wav"));
    private Sound hitblock = Gdx.audio.newSound(Gdx.files.internal("hit-4.wav"));
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("hover.wav"));
    private Sound restock = Gdx.audio.newSound(Gdx.files.internal("restock.wav"));
    private Sound pigs_spawn = Gdx.audio.newSound(Gdx.files.internal("pigs_spawn.wav"));
    private Kokpit kokpit = new Kokpit();
    private int points=0;
    private int stars=5;
    private int ammo;
    private BlackHole blackHole;
    private boolean win = false;
    private Boss boss;


    public BossFight(ArkanoidGK game, int points, int stars){
        this.game = game;
        this.points = points;
        this.stars += stars;
        this.boss = new Boss(ArkanoidGK.WIDTH/2, ArkanoidGK.WIDTH/2,450, balls, boomholder);
        pad = new Paddle(ArkanoidGK.WIDTH/2,50,450);
        pad.setSkin(2);

        music.loop(30);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
            //kolizje piłki
            for(Ball ball:balls){
                //z paletka
            if(ball.getCollisionRect().collidesWith(pad.getCollisionRect())){
                boomholder.add(new Explosion(ball.getX(),ball.getY()));
                stars--;
            }
            ball.move();
        }

        /*
        Sterowanie paletką i strzelanie
        */
//        if (Gdx.input.isKeyJustPressed(X) && stars >0){
//            balls.add(new Ball(ArkanoidGK.WIDTH/2, ArkanoidGK.HEIGHT/2, 250));
//            stars--;
//        }
        if (Gdx.input.isKeyPressed(LEFT)) {
            if(pad.getX() > 0){ pad.moveLeft();}
        } else if (Gdx.input.isKeyPressed(RIGHT)) {
            if(pad.getX()< (ArkanoidGK.WIDTH-pad.getWidth())){pad.moveRight();}
        }
        if (Gdx.input.isKeyJustPressed(R)){
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
            for(SpacePig pig:pigs){
                if(shot.getCollisionRect().collidesWith(pig.getCollisionRect())){
                    pig.gethit();
                    shot.gethit();
                }
            }
            if(shot.getCollisionRect().collidesWith(boss.getCollisionRect())){
                boss.gethit();
                shot.gethit();
                boomholder.add(new Explosion(boss.getX(),boss.getY()));
            }
            if(shot.remove) {
                bullets_toremove.add(shot);
            }
        }
//        bullets end
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
        /*
        * UWAGA swinie!
        * Jezeli na planszy pozostanie 6 blokow to uwolnione zostają kosmiczne swinie
        * Agresywan rasa ufo ktora latajac haotycznie sporbuje ukrasc pozostale na statku gwiazdy
        */
        if(boss.getY()<0){
            win = true;
            this.dispose();
            game.setScreen(new Menuscreen(game));
        }
        /*
         * Jezeli niema juz pilek  zapasie i na planszy
         * PRZEGRANA
         * */
        if(stars<=0 && !win){
            this.dispose();
            game.setScreen(new UlooseScreen(game, points, stars,4));
        }
        boss.update();
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
        boss.render(this.game.batch);
        for(Ball ball:balls) ball.render(this.game.batch);
        for(Bullet shot:bullets) shot.render(this.game.batch);
        for(Explosion boom: boomholder) boom.render(this.game.batch);
        if(pigs_relased && !blackHole.remove) blackHole.render(this.game.batch);
        kokpit.draw(this.game,5,points, stars);
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
