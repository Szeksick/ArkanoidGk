package pl.kgdev.arkanoidgk.eventscollisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BlackHole {

    private final Texture texture;
    public float x,y;
    private static int WIDTH = 320;
    private static int HEIGHT = 240;
    public static final float SPEED_ANIMATION = 0.1f;
    public static final float BOOM_WAIT_TIMER = 0.7f;
    Sound explode = Gdx.audio.newSound(Gdx.files.internal("laser.wav"));

    Animation[] ticks;
    CollisionRect rect;

    public boolean remove = false;
    private float stateTime, boom_timer;

    public BlackHole(float x, float y){
        this.x = +x-this.WIDTH/2;
        this.y = +y-this.HEIGHT/2;
        this.rect = new CollisionRect(this.x,this.y,WIDTH, HEIGHT);
        boom_timer = 0;
        ticks = new Animation[1];
        texture = new Texture("blackhole.png");
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

