package Logic;

public class Tile{
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
    public int getRow(){
        return this.row;
    }
    public int getNum(){
        return this.num;
    }
    public Tile(int row, int num, int state) {
        this.row = row;
        this.num = num;
        this.state = state;


    }
    public String toString(){
        return ("Coords: (" +this.row+"|"+this.num+")" + this.state);
    }

}
