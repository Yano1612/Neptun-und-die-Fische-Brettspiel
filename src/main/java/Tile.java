import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class Tile extends Canvas{
    private int state; // 0 = Leer; 1 = Weiß; 2 = Schwarz
    int index = 0;
    int row = 0;
    int num = 0;
    public int getState(){
        return this.state;
    }
    public void setState(int val){
        this.state = val;
    }
    public Tile(Composite parent, int style,int row, int num, int counter) {
        super(parent, style);
        this.row = row;
        this.num = num;


        if(counter==4|counter==12|counter==20) {
            this.state = 2;
        } else {
            this.state = 1;
        }
    }
    public String toString(){
        return ("State: " + this.state + "  Coords: (" +this.row+"|"+this.num+")" );
    }

}
