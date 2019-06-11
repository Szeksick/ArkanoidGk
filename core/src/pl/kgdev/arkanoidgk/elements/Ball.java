package pl.kgdev.arkanoidgk.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;


public class Ball extends Image {
    private float SPEED = 150;
    Texture texture = new Texture("ball.png");
    private final int SIZE = 16;
    private float x, y,startx, starty;
    double vx, vy;
    private CollisionRect rect;
    private float delta = Gdx.graphics.getDeltaTime();
    private float rev_timer;
    private float REV_WAIT_TIMER = 0.27f;
    private boolean canIrev = true;

    public Ball(float x, float y){
        //konstruktor
        this.x = x;
        this.y = y;
        this.startx = x;
        this.starty=y;
        this.x = 50*Math.round(x/50);
        this.y = 50*Math.round(y/50);
        this.rect = new CollisionRect(this.x,this.y,SIZE, SIZE);
        this.vx = -0.5;
        this.vy = -1;
        rev_timer = 0;
    }
    public Ball(float x, float y, int speed){
        this.x = x;
        this.y = y;
        this.startx = x;
        this.starty=y;
        this.SPEED = speed;
        this.x = 50*Math.round(x/50);
        this.y = 50*Math.round(y/50);
        this.rect = new CollisionRect(this.x,this.y,SIZE, SIZE);
        this.vx = -0.5;
        this.vy = -1;
        rev_timer = 0;
    }
    public void move() {
        update();
        if (x <= SIZE/2) {
            reverseXdir();
        }

        if (x >= ArkanoidGK.WIDTH - SIZE+2) {
            reverseXdir();
        }

        if (y >= ArkanoidGK.HEIGHT-40) {
            reverseYdir();
        }

        x += vx * SPEED * delta;
        y += vy * SPEED * delta;
        this.rect.move(this.x, this.y);
    }
    public void resetState(){
        x = startx;
        y = starty;
    }
    public float getY(){
        return y;
    }

    public void reverseXdir(){
        if(canIrev) {
            vx = -vx;
        }
    }

    public void reverseYdir(){
        if(canIrev) {
            vy = -vy;
        }
    }

    public void render(SpriteBatch batch) {

        batch.draw(texture,x,y,SIZE,SIZE);
    }
    private void update(){
        if(rev_timer >= REV_WAIT_TIMER){
            canIrev=false;
        }
        rev_timer += delta;
        canIrev = true;
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }
}