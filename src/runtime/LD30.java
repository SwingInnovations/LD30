package runtime;

import levels.Arena_1;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class LD30 extends StateBasedGame{
    private final static String GAME_NAME = "Pound";
    private final int ARENA_1 = 0;
    public LD30(String name){
        super(name); 
        this.addState(new Arena_1(ARENA_1));
    }
    
    public void initStatesList(GameContainer gc)throws SlickException{
        this.getState(ARENA_1).init(gc, this);
        this.enterState(ARENA_1);
    }
    
    public static void main(String[] args){
        try{
            AppGameContainer app = new AppGameContainer(new LD30(GAME_NAME));
            app.setDisplayMode(1024, 768, false);
            app.setVSync(true);
            app.setShowFPS(false);
            app.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
