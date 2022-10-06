
import java.util.ArrayList;
import java.util.List;


public class TurnManagement {
    private Tile startTile;
    private int winner = 0;
    private boolean stop = false;
    private boolean illegalMove = false;
    private int turn = 2;
    private Tile[][] tiles = new Tile[5][5];
    public void addToTiles(int row, int num, Tile tile){
        tiles[row][num] = tile;
    }
    public Tile[][] getTiles(){
        return this.tiles;
    }
    public int executeMove(Tile startTile, Tile destinationTile) {

        this.startTile = startTile;
        List<Tile> Adjacent = utility.getAdjacentTiles(destinationTile, tiles);
        // Execution of the Move (also Checks the legality of the move)
        if (checkLegality(Adjacent, destinationTile) && !stop) {
            destinationTile.setState(turn);
            startTile.setState(0);
            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
        } else {

            illegalMove = true;
            startTile.setSelected(false);
        }

        // Checking, if a player has won
        winner = utility.checkWinner(tiles);
        if (winner != 0) {
            stop = true;
        }
        return turn;
    }

    public boolean checkLegality(List<Tile> Adjacent, Tile tile) {
        // Checking the legality of a move
        boolean legal = false;
        for (int i = 0; i <= Adjacent.size() - 1; i++) {
            if (Adjacent.get(i) != null) {
                if (Adjacent.get(i) == this.startTile) {
                    legal = true;
                    break;
                }
            }
        }
        if (legal && turn == 2 && tile.getState() != 1) {
            legal = false;
        } else if (legal && turn == 1 && tile.getState() != 0) {
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
}
