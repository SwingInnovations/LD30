package engine;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Weapon implements Entity{

    private Vector2f position;
    private Vector2f dimension;
    private Vector2f hOffset, vOffset;
    private Image image;
    private Polygon poly;
    private int direction;
    
    public Weapon(Vector2f pos, Vector2f dim, int dir){
        this.SetPos(pos);
        this.SetDim(dim);
        this.InitPoly();
        this.SetDirection(dir);
    }
    
    public Weapon(Entity host, Vector2f dim, Vector2f hOffset, Vector2f vOffset){
        this.hOffset = hOffset;
        this.vOffset = vOffset;
        this.SetDirection(host.GetDirection());
        switch(direction){
            case 0:
                position.x = (host.GetPos().x + host.GetDim().x + hOffset.x);
                break;
            case 1:
                position.y = (host.GetPos().y + host.GetDim().y + hOffset.y);
                break;
            case 2:
                position.x = (host.GetPos().x - hOffset.x);
                break;
            case 3:
                position.y = (host.GetPos().y - hOffset.y);
                break;
            default:
                
        };
        this.InitPoly();
    }
    
    
    public void Render(Graphics g){
        
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
