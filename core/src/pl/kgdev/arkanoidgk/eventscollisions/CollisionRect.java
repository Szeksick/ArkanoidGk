package pl.kgdev.arkanoidgk.eventscollisions;

import com.badlogic.gdx.math.Rectangle;

public class CollisionRect extends Rectangle{
    private float x,y;
    private int width, height;



    public CollisionRect(float x, float y, int width, int height){
        this.x = x+2;
        this.y = y+2;
        this.width = width;
        this.height = height;

    }
    public void move(float x, float y){
        this.x = x;
        this.y = y;
        }

    public boolean collidesWith (CollisionRect rect) {
        return x < rect.x + rect.width && y < rect.y + rect.height && x + width > rect.x && y + height > rect.y;
    }

}
