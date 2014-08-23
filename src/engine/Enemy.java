package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Enemy implements Entity {
    private Vector2f position;
    private Vector2f dimension;
    private int direction;
    private Polygon poly;
    private Image masterImage;
    private SpriteSheet spriteSheet;
    private static float SPEED = 0.25f;
    private int health;
    private int currentIndex;
    private int currentFrame;
    private int totalFrame;
    private float time;
    private float timeSinceLastChange;
    private Foot foot;
    private boolean debugRender = false;
    private boolean onGround = true;
    private boolean isFalling = false;
    private float jumpSpeed;
    private float GRAVITY = 0.0098f;
    private float TERM_VELOCITY = 0.2f;
    
    enum Behaviour{ STEAM, ALIEN, CYBER };
    
    public Enemy(Vector2f pos, Vector2f dim, int dir){
        this.SetPos(pos);
        this.SetDim(dim);
        this.InitPoly();
        this.SetDirection(dir);
        foot = new Foot(new Vector2f(pos.x, pos.y + dim.y), new Vector2f(dim.x, 4));
    }
    
    public void Update(GameContainer gc, int delta){
        
    }
    
    public void Render(Graphics g){
        if(debugRender){
            g.setColor(Color.red);
        }
        g.drawImage(masterImage, position.x, position.y);
    }
    
        public void InitSpriteSheet(SpriteSheet sprite){
        int d = currentIndex;
        spriteSheet = sprite;
        totalFrame = spriteSheet.getHorizontalCount();
        masterImage = spriteSheet.getSprite(currentFrame, d);
    }
    
    private void UpdateSpriteSheet(int delta){
        time+= (float)delta/1000;
        if(time > timeSinceLastChange + 0.1f){
            timeSinceLastChange = time;
            NextFrame();
        }
    }
    
    private void NextFrame(){
        currentFrame++;
        if(currentFrame > totalFrame - 1){
            currentFrame = 0;
        }
        int d = currentIndex;
        masterImage = spriteSheet.getSprite(currentFrame, d);
    }
    
    @Override
    public void SetPos(Vector2f pos) {
        this.position = pos;
    }

    @Override
    public void SetDim(Vector2f dim) {
       this.dimension = dim;
    }

    @Override
    public void InitPoly() {
        poly = new Polygon(new float[]{
            position.x, position.y,
            position.x, position.y + dimension.y,
            position.x + dimension.x, position.y + dimension.y,
            position.x + dimension.x, position.y
        });
    }
    
    public void SetHealth(int h){
        this.health = h;
    }
    
    private void SetOnGround(boolean val){
        onGround = val;
    }
    
    private void Jump(int delta){
        if(onGround){ onGround = false; isFalling = false; }
        if(!onGround){
            if(!isFalling){
                jumpSpeed += GRAVITY;
                position.y -= jumpSpeed * delta;
                poly.setY(position.y);
                if(jumpSpeed > TERM_VELOCITY){
                    jumpSpeed = TERM_VELOCITY;
                    isFalling = true;
                }
            }else{
                jumpSpeed -= GRAVITY;
                if(jumpSpeed < 0){
                    jumpSpeed = SPEED;
                }
                
                position.y += jumpSpeed * delta;
                poly.setY(position.y);
                if(poly.intersects(foot.GetPoly())){
                    jumpSpeed = 0.0f;
                }
            }
        }
    }

    @Override
    public void SetDirection(int d) {
        this.direction = d;
    }

    @Override
    public Vector2f GetPos() {
       return position;
    }

    @Override
    public Vector2f GetDim() {
        return dimension;
    }

    @Override
    public Polygon GetPoly() {
        return poly;
    }

    @Override
    public int GetDirection() {
        return direction;
    }
    
    public boolean HasIntersected(Entity other){
        if(this.poly.intersects(other.GetPoly())){
            return true;
        }else{
            return false;
        }
    }
}
