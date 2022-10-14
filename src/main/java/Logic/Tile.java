package Logic;

public class Tile{
    private int state; // 0 = Empty; 1 = White; 2 = Black
    private final int row;
    private final int num;
    public int getState(){
        return this.state;
    }
    // TODO make private
    public void setState(int val){
        this.state = val;
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
        return ("Coords: (" +this.row+"|"+this.num+")");
    }

    public boolean isEqualTo(Tile tile) {
        if(tile.getRow() == this.row && tile.getNum() == this.num){
            return true;
        }
        return false;
    }
}
