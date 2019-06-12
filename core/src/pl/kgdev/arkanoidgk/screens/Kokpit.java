package pl.kgdev.arkanoidgk.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import pl.kgdev.arkanoidgk.ArkanoidGK;

public class Kokpit {
    private Texture star = new Texture("ball_1.png");
    private Texture kokpit = new Texture("display.png");


    public void draw(ArkanoidGK game,int stage, int points, int stars){
        game.batch.draw(kokpit,(ArkanoidGK.WIDTH/2)-170, 0, 340, 40);
        game.font.setColor(Color.GREEN);
        game.font.draw(game.batch,"Punkty: ",(ArkanoidGK.WIDTH/2)+40,25);
        game.batch.draw(star,(ArkanoidGK.WIDTH/2)-30,15, 16, 12);
        game.font.setColor(Color.RED);
        game.font.draw(game.batch,Integer.toString(stars),(ArkanoidGK.WIDTH/2)-10,25);
        game.font.draw(game.batch,Integer.toString(points),(ArkanoidGK.WIDTH/2)+100,25);
        game.font.draw(game.batch,"POZIOM "+stage,(ArkanoidGK.WIDTH/2)-120,25);
    }
}
