package pl.kgdev.arkanoidgk.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;

import java.util.Random;

public class SpacePig {
    public static float SPEED = 200;
    public static final float SPEED_ANIMATION = 0.5f;
    public static final int PIXEL_WIDTH = 31;
    public static final int PIXEL_HEIGHT = 29;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    private float delta = Gdx.graphics.getDeltaTime();
    private int MOVE_TIMER = 1;
    public boolean remove = false;
    public int HIT_POINTS = 10;
    public boolean RIGHT_BLOCKED = false;
    public boolean LEFT_BLOCKED = false;
    public boolean UP_BLOCKED = false;
    public boolean DOWN_BLOCKED = false;

    Animation[] moves;//deklaracja tablicy animacji

    public float x;
    public float y;
    CollisionRect rect;
    public int moveDirection;
    int move;
    float moveTimer;
    float stateTime;
    private float vx, vy;


    public SpacePig(float x, float y){
        this.x = x;
        this.y = y;
        this.rect = new CollisionRect(x,y,WIDTH, HEIGHT);
        move = 1;
        moveDirection =1;
        moveTimer = 0;
        moves = new Animation[4];//definicja tablicy animacji
        //siekam sprajta to nie fanta
        TextureRegion[][] moveSpriteSheet = TextureRegion.split(new Texture("chicken.png"), PIXEL_WIDTH, PIXEL_HEIGHT);
        //tablica animacji
        moves[0] = new Animation(SPEED_ANIMATION, moveSpriteSheet[0]);//w dol
        moves[1] = new Animation(SPEED_ANIMATION, moveSpriteSheet[1]);//w lewo
        moves[2] = new Animation(SPEED_ANIMATION, moveSpriteSheet[2]);//w prawo
        moves[3] = new Animation(SPEED_ANIMATION, moveSpriteSheet[3]);//w gore
    }

    public void direction(){
        MOVE_TIMER++;
        if(MOVE_TIMER >= 20) {
            moveDirection = new Random().nextInt(4)+1;
            MOVE_TIMER = 1;
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
        if(moveDirection == 1 && this.y < ArkanoidGK.HEIGHT-HEIGHT)this.moveUp();
        if(moveDirection == 2 && this.y > HEIGHT+200) this.moveDown();
        if(moveDirection == 3 && this.x > WIDTH) this.moveLeft();
        if(moveDirection == 4 && this.x < ArkanoidGK.WIDTH-WIDTH) this.moveRight();
    }
    public void render(SpriteBatch batch) {
        batch.draw((TextureRegion) moves[move].getKeyFrame(stateTime,true),x,y,WIDTH,HEIGHT);
    }

    private void dead() {
        remove = true;
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
        if(this.HIT_POINTS <=0) dead();
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }

}
