package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Block implements Entity{

    private Vector2f position;
    private Vector2f dimension;
    private Polygon poly;
    private Image image;
    private boolean isVoid;
    
    public Block(Vector2f pos, Vector2f dim){
        this.SetPos(pos);
        this.SetDim(dim);
        this.InitPoly();
        isVoid = false;
    }
    
    public void SetX(float _x){this.position.x = _x; this.poly.setX(_x);}
    public void SetY(float _y){this.position.y = _y; this.poly.setY(_y);}
    
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
    
    public void SetVoid(boolean val){
        this.isVoid = val;
    }
    
    public void Update(int delta){
        if(IsVoid() && position.y <= 2000){
            position.y += 0.25f * delta;
            poly.setY(position.y);
        }
    }
    
    public void Render(Graphics g){
        g.setColor(Color.cyan);
        g.draw(poly);
    }

    @Override
    public void SetDirection(int d) {
        
    }

    @Override
    public Vector2f GetPos() {
        return this.position;
    }

    @Override
    public Vector2f GetDim() {
        return this.dimension;
    }

    @Override
    public Polygon GetPoly() {
        return this.poly;
    }

    @Override
    public int GetDirection() {
        return 0;
    }
    
    public boolean HasIntersected(Entity other){
        if(this.poly.intersects(other.GetPoly())){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean IsVoid(){
        return isVoid;
    }
    
}
