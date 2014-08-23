package levels;

import engine.Block;
import engine.Actor;
import engine.Enemy;
import engine.Weapon;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;
import java.util.ArrayList;

public class Arena_1 extends BasicGameState{
    private boolean isGameOver;
    private boolean isPause;
    private Polygon boundsArea;
    private ArrayList<Block> groundTiles;
    private ArrayList<Enemy> enemies;
    private Actor player;
    private Weapon playerFist;
    
    public Arena_1(int id){
    
    }
    
    public void init(GameContainer gc, StateBasedGame sbg)throws SlickException{
        isPause = false;
        isGameOver = false;
        boundsArea = new Polygon(new float[]{
            64, 256,
            960, 256,
            1024, 768,
            0, 768
        });
        
        player = new Actor(new Vector2f(200, 400), new Vector2f(96, 128), 0);
        player.DebugRender(true);
        
        groundTiles = new ArrayList<Block>();
        
        for(int i = 0; i < gc.getWidth(); i+=64){
            for(int j = 256; j < gc.getHeight(); j+=64){
                groundTiles.add(new Block(new Vector2f(i, j), new Vector2f(64, 72)));
            }
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException{
        if(!isGameOver){
            if(!isPause){
                player.Update(gc, delta, boundsArea, groundTiles);
            }else{
                /*-Display Pause Screen-*/
                
            }
        }else{
        /*-Display Game Over Screen-*/
        }
    }
    
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        if(!isGameOver){
            if(!isPause){
                //draw the bounds of the object
            g.draw(boundsArea);
            g.setColor(Color.orange);
            g.fill(boundsArea);

            for(int i = 0; i < groundTiles.size(); i++){
                groundTiles.get(i).Render(g);
            }

            player.Render(g);
            }else{
                /*-Draw Pause Screen-*/
                
            }
        }else{
            /*-Draw Game Over Screen -*/
            
        }
    }
    
    public int getID(){
        return 0;
    }
}
