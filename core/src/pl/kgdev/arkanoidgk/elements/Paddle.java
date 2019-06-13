package pl.kgdev.arkanoidgk.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;

public class Paddle extends Image {
    private static float SPEED = 150;
    private static final float SPEED_ANIMATION = 0.5f;
    private static final int PIXEL_WIDTH = 64;
    private static final int PIXEL_HEIGHT = 64;
    Texture texture = new Texture("pad.png");
    private final int WIDTH = texture.getWidth();
    private final int HEIGHT = texture.getHeight();
    private int move;
//    private final int moveTimer;
    private float x , y;
    private float stateTime;
    private boolean RIGHT_BLOCKED = false;
    private boolean LEFT_BLOCKED = false;
    private boolean UP_BLOCKED = false;
    private boolean DOWN_BLOCKED = false;
    CollisionRect rect;
    private float delta = Gdx.graphics.getDeltaTime();
    private Animation[] moves;

    public Paddle(float x, float y, int speed){
        this.x = x;
        this.y = y;
        this.SPEED = speed;
        this.x = 50*Math.round(x/50);
        this.y = 50*Math.round(y/50);
        this.rect = new CollisionRect(this.x,this.y,WIDTH, HEIGHT);
        move = 1;
    }
    public Paddle(float x, float y){
        this.x = x;
        this.y = y;
        this.x = 50*Math.round(x/50);
        this.y = 50*Math.round(y/50);
        this.rect = new CollisionRect(this.x,this.y,WIDTH, HEIGHT);
        move = 1;
    }
    public void moveRight(){
        if(!this.RIGHT_BLOCKED) this.x += SPEED * Gdx.graphics.getDeltaTime();
        this.rect.move(this.x, this.y);
//        move = 3;
//        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveLeft(){
        if(!this.LEFT_BLOCKED) this.x -= SPEED * Gdx.graphics.getDeltaTime();
        this.rect.move(this.x, this.y);
//        move = 1;
//        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveUp(){
        if(!this.UP_BLOCKED) this.y += SPEED * Gdx.graphics.getDeltaTime();
        this.rect.move(this.x, this.y);
//        move = 0;
//        stateTime += delta / SPEED_ANIMATION;
    }
    public void moveDown(){
        if(!this.DOWN_BLOCKED) this.y -= SPEED * Gdx.graphics.getDeltaTime();
        this.rect.move(this.x, this.y);
//        move = 2;
//        stateTime += delta / SPEED_ANIMATION;
    }

    public void setSkin(int h){
        if(h==1){
            texture = new Texture("pad.png");
        }else{
            texture = new Texture("pad1.png");
        }
    }

    public void render(SpriteBatch batch) {
//        batch.draw((TextureRegion) moves[move].getKeyFrame(stateTime,true),x,y,WIDTH+10,HEIGHT+10);
        batch.draw(texture,x,y,texture.getWidth(),texture.getHeight());
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
}
