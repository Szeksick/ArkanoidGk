package pl.kgdev.arkanoidgk.walls;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.eventscollisions.CollisionRect;
import pl.kgdev.arkanoidgk.eventscollisions.Explosion;

import java.util.ArrayList;
import java.util.Random;

public class Block extends Rectangle {
    private static final int PIXEL_WIDTH = 40;
    private static final int PIXEL_HEIGHT = 20;
    private int colornumber;
    private float x,y;
    private int state;
    private static final int WALL_WIDTH = ArkanoidGK.WIDTH/12;
    private static final int WALL_HEIGHT = ArkanoidGK.HEIGHT/24;
    private CollisionRect rect;
    private TextureRegion[][] stateSpriteSheet;
    public boolean remove = false;
    private boolean exploded = false;


    public Block(float x, float y){
        this.x = x;
        this.y = y;
        this.colornumber = new Random().nextInt(4);
        this.state = 0;
        this.rect = new CollisionRect(this.x,this.y,WALL_WIDTH, WALL_HEIGHT);
        stateSpriteSheet = TextureRegion.split(new Texture("blocks.png"), PIXEL_WIDTH, PIXEL_HEIGHT);
    }

    public void render(SpriteBatch batch) {
        batch.draw(stateSpriteSheet[colornumber][this.state], this.x, this.y, WALL_WIDTH, WALL_HEIGHT);
    }

    public void gethit(){
        if(this.state < 3){
            this.state++;
        }
    }

    private void explode(ArrayList<Explosion> boomholder){
        boomholder.add(new Explosion(this.x+(this.WALL_WIDTH/2),this.y+(this.WALL_HEIGHT/2)));
    }

    public void update(ArrayList<Explosion> boomholder) {
        this.rect.move(this.x, this.y);
        if(state >= 3){
            this.remove = true;
            if(colornumber == 1 && exploded == false){
                explode(boomholder);
            }
            this.exploded = true;
            this.x = -100;
            this.y = -100;
        }
    }

    public CollisionRect getCollisionRect(){
        return rect;
    }
}

