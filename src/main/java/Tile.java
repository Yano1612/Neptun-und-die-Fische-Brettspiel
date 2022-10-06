import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class Tile extends Canvas{
    private int state; // 0 = Empty; 1 = White; 2 = Black
    private boolean selected = false;
    int row;
    int num;
    public int getState(){
        return this.state;
    }
    public void setState(int val){
        this.state = val;
    }
    public boolean getSelected(){
        return this.selected;
    }
    public void setSelected(boolean val){
        this.selected = val;
    }
    public Tile(Composite parent, int style,int row, int num, int counter) {
        super(parent, style);
        this.row = row;
        this.num = num;


        if(counter==4||counter==12||counter==20) {
            this.state = 2;
        } else {
            this.state = 1;
        }
    }
    public String toString(){
        return ("Coords: (" +this.row+"|"+this.num+")" );
    }

}
