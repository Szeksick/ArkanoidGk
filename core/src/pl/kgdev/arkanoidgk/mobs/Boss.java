package pl.kgdev.arkanoidgk.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.elements.Ball;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;
import pl.kgdev.arkanoidgk.eventscollisions.Explosion;

import java.util.ArrayList;
import java.util.Random;

public class Boss extends Image {
    private static float SPEED = 150;
    private static final float SPEED_ANIMATION = 0.5f;
    Texture texture = new Texture("ufo.png");
    private final int WIDTH = texture.getWidth()/6;
    private final int HEIGHT = texture.getHeight()/6;
    private int move;
    private float x , y;
    private float stateTime;
    private boolean RIGHT_BLOCKED = false;
    private boolean LEFT_BLOCKED = false;
    private boolean UP_BLOCKED = false;
    private boolean DOWN_BLOCKED = false;
    CollisionRect rect;
    private float delta = Gdx.graphics.getDeltaTime();
    private int MOVE_TIMER = 1;
    private int moveDirection;
    private boolean remove = false;
    private int HIT_POINTS = 500;
    ArrayList<Ball> balls;
    ArrayList<Explosion> boomholder;
    public static final float BOOM_WAIT_TIMER = 0.9f;
    private float boom_timer;


    public Boss(float x, float y, int speed, ArrayList<Ball> balls, ArrayList<Explosion> boomholder){
        this.x = x;
        this.y = y;
        this.balls = balls;
        this.SPEED = speed;
        this.boomholder = boomholder;
        this.x = 50*Math.round(x/50);
        this.y = 50*Math.round(y/50);
        this.rect = new CollisionRect(this.x,this.y,WIDTH, HEIGHT);
        move = 1;
    }
    public Boss(float x, float y){
        this.x = x;
        this.y = y;
        this.x = 50*Math.round(x/50);;
        this.y = 50*Math.round(y/50);;
        this.rect = new CollisionRect(this.x,this.y,WIDTH, HEIGHT);
        move = 1;
    }
    public void direction(){
        MOVE_TIMER++;
        if(MOVE_TIMER >= 20) {
            moveDirection = new Random().nextInt(3)+1;
            MOVE_TIMER = 10;
        }
    }
    public void reverse(){
        if(moveDirection == 1) moveDirection=2;
        if(moveDirection == 2) moveDirection=1;
        if(moveDirection == 3) moveDirection=4;
        if(moveDirection == 4) moveDirection=3;
    }
    public void moveRight(){
        if(this.RIGHT_BLOCKED == false){
            this.x += SPEED * Gdx.graphics.getDeltaTime();
        }else{
            reverse();
        }
        move = 2;
        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveLeft(){
        if(this.LEFT_BLOCKED == false){
            this.x -= SPEED * Gdx.graphics.getDeltaTime();
        }else{
            reverse();
        }
        move = 1;
        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveUp(){
        if(this.UP_BLOCKED == false){
            this.y += SPEED * Gdx.graphics.getDeltaTime();
        }else{
            reverse();
        }
        move = 3;
        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveDown(){
        if(this.DOWN_BLOCKED == false){
            this.y -= SPEED * Gdx.graphics.getDeltaTime();
        }else{
            reverse();
        }
        move = 0;
        stateTime += delta / SPEED_ANIMATION;
    }

    private void ruch() {
        this.direction();
        if(moveDirection == 1 && this.x > WIDTH) this.moveLeft();
        if(moveDirection == 2 && this.x < ArkanoidGK.WIDTH-WIDTH) this.moveRight();
        if(moveDirection == 3 && this.y < ArkanoidGK.HEIGHT-HEIGHT){
            if(boom_timer >= BOOM_WAIT_TIMER){
                balls.add(new Ball(this.x+(this.WIDTH/2),this.y));
            }
            boom_timer += delta;
        }
    }

    public void render(SpriteBatch batch) {
//        batch.draw((TextureRegion) moves[move].getKeyFrame(stateTime,true),x,y,WIDTH+10,HEIGHT+10);
        batch.draw(texture,x,y,WIDTH,HEIGHT);
    }
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public int getWIDTH(){
        return WIDTH;
    }

    public float getWidth(){
        return WIDTH;
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }

    private void explode(ArrayList<Explosion> boomholder){
        boomholder.add(new Explosion(this.x+(this.WIDTH/2),this.y+(this.HEIGHT/2)));
    }
    private void dead() {
        moveDown();
        explode(this.boomholder);
    }
    public void gethit(){
        HIT_POINTS--;
    }

    public void update() {
        ruch();
        rect.move(this.x, this.y);
        RIGHT_BLOCKED = false;
        LEFT_BLOCKED = false;
        UP_BLOCKED = false;
        DOWN_BLOCKED = false;
        if(this.HIT_POINTS <=0 && this.y > -HEIGHT) dead();
    }
}


