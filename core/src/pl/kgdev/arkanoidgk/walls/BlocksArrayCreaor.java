package pl.kgdev.arkanoidgk.walls;

import pl.kgdev.arkanoidgk.ArkanoidGK;
import pl.kgdev.arkanoidgk.walls.Block;
import java.util.ArrayList;

public class BlocksArrayCreaor {
    private ArrayList<Block> blocks = new ArrayList<Block>();

    public ArrayList<Block> create(){
        for(int i = 4;i <10;i++){
            for(int j = 0; j<12;j++){
                this.blocks.add(new Block(((ArkanoidGK.WIDTH/12)*j),(ArkanoidGK.HEIGHT-((ArkanoidGK.HEIGHT/24)*i))));
            }
        }
        return this.blocks;
    }
}
