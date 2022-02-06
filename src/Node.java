import java.awt.Color;
import java.awt.Graphics;

public class Node {
    private int x;
    private int y;
    private int color;
    public Node parinte;
    private boolean hasParent=false;
    public boolean hasBelow,hasOver,hasLeft,hasRight;
    public int i=0,j=0;
    public boolean isVisited=false;

    public Node(){
        x=0;
        y=0;
        color=0;
        parinte=null;
    }
    public Node(int coordX, int coordY, int color) {
        this.x = coordX;
        this.y = coordY;
        this.color = color;
        parinte=new Node();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setNeighbours(int i,int j,int nrLinii,int nrColoane){
        if(i>0){
            hasOver=true;
        }else{
            hasOver=false;
        }
        if(i<nrLinii-1){
            hasBelow=true;
        }else{
            hasBelow=false;
        }
        if(j>0){
            hasLeft=true;
        }else{
            hasLeft=false;
        }
        if(j<nrColoane-1){
            hasRight=true;
        }else{
            hasRight=false;
        }
    }
    public void drawNode(Graphics g, int lungimeLatura,int color) {
        if(color == 0) {
            g.setColor(Color.BLACK);
        }else if(color == 1){
            g.setColor(Color.WHITE);
        }else if(color == 2){
            g.setColor(Color.RED);
        }
        else if(color == 3){
            g.setColor(Color.BLUE);
        }else if(color == 4){
            g.setColor(Color.GREEN);
        }else{
            System.out.println("Nu exista culoarea asta!");
        }
        g.fillRect(x,y,lungimeLatura,lungimeLatura);
        g.setColor(Color.PINK);
        g.drawRect(x, y, lungimeLatura, lungimeLatura);
    }
}
