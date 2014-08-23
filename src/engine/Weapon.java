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
    private boolean inFinalPosition;
    private boolean isFiring;
    
    public Weapon(Vector2f pos, Vector2f dim, int dir){
        this.SetPos(pos);
        this.SetDim(dim);
        this.InitPoly();
        this.SetDirection(dir);
        inFinalPosition = false;
    }
    
    public Weapon(Entity host, Vector2f dim, Vector2f hOffset, Vector2f vOffset){
        this.position = new Vector2f(0, 0);
        this.dimension = dim;
        this.hOffset = hOffset;
        this.vOffset = vOffset;
        this.SetDirection(host.GetDirection());
        switch(direction){
            case 0:
                position.x = (host.GetPos().x + host.GetDim().x + hOffset.x);
                position.y = (host.GetPos().y + host.GetDim().y / 2);
                break;
            case 1:
                position.x = (host.GetPos().x + host.GetDim().x / 2 );
                position.y = (host.GetPos().y + host.GetDim().y + hOffset.y);
                break;
            case 2:
                position.x = (host.GetPos().x - hOffset.x);
                position.y = (host.GetPos().y + host.GetDim().y / 2);
                break;
            case 3:
                position.x = (host.GetPos().x + host.GetDim().x / 2 );
                position.y = (host.GetPos().y - hOffset.y);
                break;
            default:
                break;
        };
        
        System.out.print(position);
        this.InitPoly();
        inFinalPosition = false;
        isFiring = false;
    }
    
    @SuppressWarnings("empty-statement")
    public void UpdatePos(Actor host){
        if(!isFiring){
            this.direction = host.GetDirection();
            switch(direction){
                case 0:
                    position.x = (host.GetPos().x + host.GetDim().x + hOffset.x);
                    position.y = (host.GetPos().y + host.GetDim().y / 2);
                    break;
                case 1:
                    position.x = (host.GetPos().x + host.GetDim().x / 2 );
                    position.y = (host.GetPos().y + host.GetDim().y + hOffset.y);
                    break;
                case 2:
                    position.x = (host.GetPos().x - hOffset.x - hOffset.x - 16);
                    position.y = (host.GetPos().y + host.GetDim().y / 2);
                    break;
                case 3:
                    position.x = (host.GetPos().x + host.GetDim().x / 2 );
                    position.y = (host.GetPos().y - hOffset.y);
                    break;
                default:
                    break;
            };
            poly.setX(position.x);
            poly.setY(position.y);
        }
    }
    
    public void UpdatePos(Enemy host){
        if(!IsFiring()){
            this.direction = host.GetDirection();
            switch(direction){
                case 0:
                    position.x = (host.GetPos().x + host.GetDim().x + hOffset.x);
                    position.y = (host.GetPos().y + host.GetDim().y / 2);
                    break;
                case 1:
                    position.x = (host.GetPos().x + host.GetDim().x / 2 );
                    position.y = (host.GetPos().y + host.GetDim().y + hOffset.y);
                    break;
                case 2:
                    position.x = (host.GetPos().x - hOffset.x - hOffset.x - 16);
                    position.y = (host.GetPos().y + host.GetDim().y / 2);
                    break;
                case 3:
                    position.x = (host.GetPos().x + host.GetDim().x / 2 );
                    position.y = (host.GetPos().y - hOffset.y);
                    break;
                default:
                    break;
            };
            poly.setX(position.x);
            poly.setY(position.y);
        }
    }
    
    public void Render(Graphics g){
        g.draw(poly);
    }
    
    public void Jab(Enemy host, int distance, int delta){
    
    }
    
    public void Jab(Actor host, int distance, int delta){
        isFiring = true;
        if(direction == 0){
            position.x += 0.45f * delta;
            if(position.x >= position.x + distance){
                position.x = position.x + distance;
            }
            poly.setX(position.x);
        }else if(direction == 1){
            //Shoot down
        }else if(direction == 2){
            position.x -= 0.45f * delta;
            if(position.x <= position.x - dimension.x - distance){
                position.x = position.x - dimension.x - distance;
            }
            poly.setX(position.x);
        }else{
            //shoot up
        }
    }
    
    public void Slam(Enemy host, int delta){
    
    }
    
    public void Slam(Actor host, int delta){
        if(direction == 0 || direction == 2){
            position.y += 0.25f * delta;
            poly.setY(position.y);
            if(position.y == host.GetFoot().GetPos().y){
                position.y += 0;
                poly.setY(position.y);
            }
        }
    }
    
    @Override
    public void SetPos(Vector2f pos) {
        this.position = pos;
    }

    @Override
    public void SetDim(Vector2f dim) {
        this.dimension = dim;
    }

    public void SetFiring(boolean val){
        isFiring = val;
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
    
    public void SetFinalPosition(boolean val){
        inFinalPosition = val;
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
    
    public boolean IsFinalPosition(){
        return inFinalPosition;
    }
    
    public boolean HasIntersected(Entity other){
        return poly.intersects(other.GetPoly());
    }
    
    public boolean IsFiring(){
        return isFiring;
    }
}
