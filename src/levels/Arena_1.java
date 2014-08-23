package levels;

import engine.Block;
import engine.Actor;
import engine.Drop;
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
import org.newdawn.slick.Input;
import java.util.ArrayList;
import java.util.Random;

public class Arena_1 extends BasicGameState{
    private boolean isGameOver;
    private boolean isPause;
    private Polygon boundsArea;
    private int SCORE;
    private int XP;
    private ArrayList<Block> groundTiles;
    private Enemy enemy;
    private ArrayList<Enemy> minions;
    private ArrayList<Drop> upgrades;
    private Actor player;
    private Weapon playerFist;
    private Weapon enemyFist;
    
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
        playerFist = new Weapon(player, new Vector2f(32, 32), new Vector2f(16, -64), new Vector2f(96/2, 16));
        player.DebugRender(true);
        player.SetHealth(100);
        
        enemy = new Enemy(new Vector2f(800, 400), new Vector2f(96, 128), 2);
        enemy.DebugRender(true);
        enemyFist = new Weapon(enemy, new Vector2f(32, 32), new Vector2f(16, -64), new Vector2f(96/2, 16));
        
        minions = new ArrayList<>();
        upgrades = new ArrayList<>();
        groundTiles = new ArrayList<>();
        
        XP = 0;
        
        for(int i = 0; i < gc.getWidth(); i+=64){
            for(int j = 256; j < gc.getHeight(); j+=64){
                groundTiles.add(new Block(new Vector2f(i, j), new Vector2f(64, 72)));
            }
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta)throws SlickException{
        Input input = gc.getInput();
        
        if(!isGameOver){
            if(!isPause){
                player.Update(gc, delta, boundsArea, groundTiles);
                playerFist.UpdatePos(player);
                
                enemy.Update(gc, player, boundsArea, groundTiles, delta);
                
                if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
                    //primary attack
                    playerFist.Jab(player, 32, delta);
                    
                    if(playerFist.HasIntersected(enemy)){
                        int h = enemy.getHealth();
                        h = h - 15;
                        enemy.SetHealth(h);
                        SCORE += 10;
                    }
                    playerFist.SetFiring(false);
                }
                
                if(input.isMousePressed(Input.MOUSE_RIGHT_BUTTON)){
                    //slam on the ground
                    playerFist.Slam(player, delta);
                    for(int i = 0; i < groundTiles.size(); i++){
                        if(playerFist.HasIntersected(groundTiles.get(i)) && playerFist.IsFinalPosition() ){
                            groundTiles.get(i).SetVoid(true);
                            SCORE-=5;
                            playerFist.SetFiring(false);
                        }
                    }
                }
                
                for(int i = 0; i < groundTiles.size(); i++){
                    if(player.OnGround() && !player.GetFoot().HasIntersected(groundTiles.get(i)) && groundTiles.get(i).IsVoid()){
                        //player.SetHealth(0);
                    }
                    if(minions.size() > 0){
                        for(int j = 0; j < minions.size(); j++){
                            if(minions.get(j).IsOnGround() && !minions.get(i).GetFoot().HasIntersected(groundTiles.get(i)) && groundTiles.get(i).IsVoid()){
                                minions.get(j).SetHealth(0);
                                minions.remove(j);
                            }
                        }
                    }
                }
                
                if(player.GetHealth() <= 0){
                    isGameOver = true; // End the Game 
                }
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
            playerFist.Render(g);
            
            enemy.Render(g);
            enemyFist.Render(g);
            
            if(upgrades.size() > 0){
                for(int i = 0; i < upgrades.size(); i++){
                    upgrades.get(i).Render(g);
                }
            }
            
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
    
    private void GenerateDrops(){
        Random x1 = new Random();
        Random x2 = new Random();
        int pX = 0, pY = 0;
        if(x1.nextInt(960) < 64){
            pX = 64;
        }else{
            pY = x1.nextInt(960);
        }
        
        if(x2.nextInt(768) < 256){
            pY = 256;
        }else{
            pY = x2.nextInt(768);
        }
        
        Random r3 = new Random();
        int type = r3.nextInt(XP);
        
        upgrades.add(new Drop(new Vector2f(pX, pY), new Vector2f(32, 32), type));
    }
}
