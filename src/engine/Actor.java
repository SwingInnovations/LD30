package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.Graphics;
import java.util.ArrayList;

public class Actor implements Entity{
    private Vector2f position;
    private Vector2f dimension;
    private int direction;
    private int currentIndex;
    private int currentFrame;
    private int totalFrame;
    private Polygon poly;
    private Image masterImage;
    private SpriteSheet spriteSheet;
    private static float SPEED = 0.25f;
    private int health;
    private float time;
    private float timeSinceLastChange;
    private boolean onGround = true; 
    private boolean isFalling = false;
    private boolean debugRender = false;
    private Foot foot;
    
    private float jumpSpeed;
    private float GRAVITY = 0.0098f;
    private float TERM_VELOCITY = 0.2f;
    
    public Actor(Vector2f pos, Vector2f dim, int dir){
        this.SetPos(pos);
        this.SetDim(dim);
        this.SetDirection(dir);
        this.InitPoly();
        foot = new Foot(new Vector2f(pos.x, pos.y + dim.y), new Vector2f(dim.x, 4));
        jumpSpeed = 0.0f;
    }
    
    public void Update(GameContainer gc, int delta, Polygon bounds, ArrayList<Block> groundTiles){
        Input in = gc.getInput();
        
        //check if on ground
        if(this.HasIntersected(foot)){
            SetOnGround(true);
        }
        
        if(position.y + dimension.y > foot.GetPos().y){
            position.y = foot.GetPos().y - dimension.y;
            poly.setY(position.y);
        }
        
        //TODO - refine in bounds control
        if(in.isKeyDown(Input.KEY_D) || in.isKeyDown(Input.KEY_RIGHT)){
            if(bounds.contains(poly)){
                position.x += SPEED * delta;
                poly.setX(position.x);
                foot.SetX(position.x);
                SetDirection(0);
            }else{
                position.x -= SPEED * delta;
                position.x = position.x + 1;
                poly.setX(position.x);
                foot.SetX(position.x);
            }
        }
        
        if(in.isKeyDown(Input.KEY_A) || in.isKeyDown(Input.KEY_LEFT)){
            if(bounds.contains(poly)){
                position.x -= SPEED * delta;
                poly.setX(position.x);
                foot.SetX(position.x);
                SetDirection(2);
            }else{
                position.x += SPEED * delta;
                position.x = position.x - 1;
                poly.setX(position.x);
                foot.SetX(position.x);
            }
        }
        
        if(in.isKeyDown(Input.KEY_S) || in.isKeyDown(Input.KEY_DOWN)){
            if(bounds.contains(poly)){
                position.y += SPEED * delta;
                poly.setY(position.y);
                foot.SetY(position.y + dimension.y);
                SetDirection(1);
            }else{
                position.y -= SPEED * delta;
                position.y = position.y - 1;
                poly.setY(position.y);
                foot.SetY(position.y + dimension.y);
            }
        }
        
        if(in.isKeyDown(Input.KEY_W) || in.isKeyDown(Input.KEY_UP)){
            if(bounds.contains(poly)){
                position.y -= SPEED * delta;
                poly.setY(position.y);
                foot.SetY(position.y + dimension.y);
                SetDirection(3);
            }else{
                position.y += SPEED * delta;
                position.y = position.y - 1;
                poly.setY(position.y);
                foot.SetY(position.y + dimension.y);
            }
        }
        
        if(in.isKeyDown(Input.KEY_SPACE)){
            SetOnGround(false);
            isFalling = false;
        }
        
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
                if(jumpSpeed <= 0){
                    jumpSpeed = SPEED;
                }
                position.y += jumpSpeed * delta;
                poly.setY(position.y);
                if(poly.intersects(foot.GetPoly())){
                    jumpSpeed = 0.0f;
                    SetOnGround(true);
                }
            }
        }
        
    }
    
    public void Render(Graphics g){
        if(debugRender){
            g.setColor(Color.green);
            g.fill(poly);
            g.draw(poly);
            foot.Render(g);
        }
        //g.drawImage(masterImage, position.x, position.y);
    }

    public void InitSpriteSheet(SpriteSheet sprite){
        int d = currentIndex;
        spriteSheet = sprite;
        currentFrame = 0;
        totalFrame = spriteSheet.getHorizontalCount();
        masterImage = spriteSheet.getSprite(currentFrame, d);
    }
    
    private void UpdateSpriteSheet(int delta){
        time += (float)delta/1000;
        if(time > timeSinceLastChange + 0.1f){
            timeSinceLastChange = time;
            NextFrame();
        }
    }
    
    private void NextFrame(){
        currentFrame++;
        if(currentFrame > totalFrame-1){
            currentFrame = 0;
        }
        int d = currentIndex;
        masterImage = spriteSheet.getSprite(currentFrame, d);
    }
    
    private void SetOnGround(boolean val){
        this.onGround = val;
    }
    
    @Override
    public void SetPos(Vector2f pos) {
        this.position = pos;
    }
    
    @Override
    public void SetDim(Vector2f dim) {
        this.dimension = dim;
    }
    
    public void DebugRender(boolean val){
        debugRender = val;
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

    @Override
    public void SetDirection(int d) {
        this.direction = d;
    }
    
    public void SetHealth(int h){
        this.health = h;
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

    public Foot GetFoot(){
        return foot;
    }
    
    @Override
    public int GetDirection() {
        return direction;
    }
    
    public int GetHealth(){
        return health;
    }
    
    public boolean OnGround(){
        return onGround;
    }
    
    public boolean HasIntersected(Entity other){
        if(this.poly.intersects(other.GetPoly())){
            return true;
        }else{
            return false;
        }
    }
    
}
