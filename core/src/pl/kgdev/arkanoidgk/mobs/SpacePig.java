package pl.kgdev.arkanoidgk.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;

import java.util.Random;

public class SpacePig {
    public static float SPEED = 40;
    public static final float SPEED_ANIMATION = 0.5f;
    public static final int PIXEL_WIDTH = 31;
    public static final int PIXEL_HEIGHT = 29;
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;
    private float delta = Gdx.graphics.getDeltaTime();
    private int MOVE_TIMER = 1;
    public int state;//1 odliczanie, 2 ruch, 3 śmierć
    public int HIT_POINTS;
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


    public SpacePig(float x, float y){
        this.x = x;//50*Math.round(x/50);
        this.y = y;//50*Math.round(y/50);
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

    public void kierunek(){
        MOVE_TIMER++;
        if(MOVE_TIMER >= 240) {
            moveDirection = new Random().nextInt(4)+1;
            MOVE_TIMER = 1;
        }
    }
    public void odwrot(){
        if(moveDirection == 1) moveDirection=2;
        if(moveDirection == 2) moveDirection=1;
        if(moveDirection == 3) moveDirection=4;
        if(moveDirection == 4) moveDirection=3;
    }
    public void moveRight(){
        if(this.RIGHT_BLOCKED == false) this.x += SPEED * Gdx.graphics.getDeltaTime();
        move = 2;
        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveLeft(){
        if(this.LEFT_BLOCKED == false) this.x -= SPEED * Gdx.graphics.getDeltaTime();
        move = 1;
        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveUp(){
        if(this.UP_BLOCKED == false) this.y += SPEED * Gdx.graphics.getDeltaTime();
        move = 3;
        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveDown(){
        if(this.DOWN_BLOCKED == false) this.y -= SPEED * Gdx.graphics.getDeltaTime();
        move = 0;
        stateTime += delta / SPEED_ANIMATION;
    }

    private void ruch() {
        kierunek();
        if(moveDirection == 1) this.moveUp();
        if(moveDirection == 2) this.moveDown();
        if(moveDirection == 3) this.moveLeft();
        if(moveDirection == 4) this.moveRight();
    }
    public void render(SpriteBatch batch) {
        batch.draw((TextureRegion) moves[move].getKeyFrame(stateTime,true),x,y,WIDTH,HEIGHT);
    }

    private void dead() {
        state=3;

    }

    public void update(float delta) {
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
