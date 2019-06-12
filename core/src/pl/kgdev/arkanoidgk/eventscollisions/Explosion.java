package pl.kgdev.arkanoidgk.eventscollisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion {

    private final Texture texture;
    public float x,y;
    private static int WIDTH = 150;
    private static int HEIGHT = 150;
    public static final float SPEED_ANIMATION = 0.1f;
    public static final float BOOM_WAIT_TIMER = 0.5f;
    Sound explode = Gdx.audio.newSound(Gdx.files.internal("explosion.mp3"));

    Animation[] ticks;
    CollisionRect rect;

    public boolean remove = false;
    private float stateTime, boom_timer;

    public Explosion(float x, float y){
        this.x = +x-this.WIDTH/2;
        this.y = +y-this.HEIGHT/2;
        this.rect = new CollisionRect(this.x,this.y,WIDTH, HEIGHT);
        boom_timer = 0;
        ticks = new Animation[1];
        texture = new Texture("boom.png");
        explode.play();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture,this.x,this.y,this.WIDTH,this.HEIGHT);
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }

    public void update(float delta) {
        if(boom_timer >= BOOM_WAIT_TIMER){
            this.remove=true;
        }
        boom_timer += delta;
        this.rect.move(this.x, this.y);
        stateTime += delta / SPEED_ANIMATION;
    }

}

