
import java.util.ArrayList;
import java.util.List;


public class TurnManagement {
    private Tile startTile;
    private int winner = 0;
    boolean stop = false;
    private boolean illegalMove = false;
    private int turn = 2;
    private Tile[][] tiles;
    public void addToTiles(Tile tile){
        tiles[tile.row][tile.num] = tile;
    }
    public Tile[][] getTiles(){
        return this.tiles;
    }
    public void setTiles(Tile[][] tiles){
        this.tiles = tiles;
    }
    public int executeMove(Tile startTile, Tile destinationTile) {

        this.startTile = startTile;
        List<Tile> Adjacent = Utility.getAdjacentTiles(destinationTile, tiles);
        // Execution of the Move (also Checks the legality of the move)
        if (checkLegality(Adjacent, destinationTile) && !stop) {
            destinationTile.setState(turn);
            this.startTile.setState(0);
            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
        } else {
            illegalMove = true;
            this.startTile.setSelected(false);
        }

        // Checking, if a player has won
        winner = Utility.checkWinner(tiles);
        if (winner != 0) {
            stop = true;
        }
        return turn;
    }

    public boolean checkLegality(List<Tile> Adjacent, Tile destinationTile) {
        // Checking the legality of a move
        boolean legal = false;
        for (int i = 0; i <= Adjacent.size() - 1; i++) {
            if (Adjacent.get(i) != null) {
                if (Adjacent.get(i) == this.startTile && this.startTile.getState() == turn) {
                    legal = true;
                    break;
                }
            }
        }
        if (legal && turn == 2 && destinationTile.getState() != 1) {
            legal = false;
        } else if (legal && turn == 1 && destinationTile.getState() != 0) {
            legal = false;
        }
        return legal;
    }
    public List<String> labelConfig(){
        List<String> texts = new ArrayList<>();
        if (turn == 2 && !stop) {
            texts.add("Currently Active: Black   ");
        } else if (turn == 1 && !stop) {
            texts.add("Currently Active: White   ");
        } else if (winner == 1 && stop) {
            texts.add("Winner: White   ");
        } else if (winner == 2 && stop) {
            texts.add("Winner: Black   ");
        }
        if(illegalMove) {
            texts.add("Illegal Move");
            illegalMove = false;
        } else {
            texts.add("             ");
        }
        return texts;
    }
    public TurnManagement(int lenRow){
        tiles = new Tile[lenRow][lenRow];
    }
}
