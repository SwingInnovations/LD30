package engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.Image;
import org.newdawn.slick.Graphics;

public class Drop implements Entity{

    private Vector2f position;
    private Vector2f dimension;
    private Image image;
    private int direction;
    private int type; 
    private Polygon poly;
    private boolean DebugRender;
    
    public Drop(Vector2f pos, Vector2f dim, int type){
        this.SetPos(pos);
        this.SetDim(dim);
        this.InitPoly();
        this.SetType(type);
        DebugRender = false;
    }
    
    public void SetType(int t){
        type = t;
    }
    
    public void SetImage(Image image){
        this.image = image;
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
    
    public void SetDebugRender(boolean val){
        DebugRender = val;
    }
    
    public void Render(Graphics g){
        if(DebugRender){
            g.setColor(Color.magenta);
            g.fill(poly);
            g.draw(poly);
        }
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
    
}
