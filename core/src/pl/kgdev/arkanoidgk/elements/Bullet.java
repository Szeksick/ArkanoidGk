package pl.kgdev.arkanoidgk.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;


public class Bullet extends Image {
    private final int WIDTH = 5;
    private final int HEIGTH = 30;
    private float x,y;
    private CollisionRect rect;
    private Texture texture;
    private float SPEED = 600;
    public boolean remove = false;
    Sound laser = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));


    public Bullet(float x, float y){
        this.x=x;
        this.y=y;
        this.texture = new Texture("bullet.png");
        this.rect = new CollisionRect(this.x, this.y, this.WIDTH, this.HEIGTH);
        laser.play();
    }


    public void render(SpriteBatch batch){
        batch.draw(texture, this.x, this.y, this.WIDTH, this.HEIGTH);
    }
    private void move(){
        this.y += SPEED * Gdx.graphics.getDeltaTime();
    }
    public void gethit(){
        this.remove = true;
    }
    public void update(){
        move();
        this.rect.move(this.x, this.y);

    }
    public CollisionRect getCollisionRect(){
        return rect;
    }
}
