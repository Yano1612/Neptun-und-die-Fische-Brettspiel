package Logic;

import java.util.ArrayList;
import java.util.List;


public class Game {
    Tile startTile;
    int winner = 0;
    boolean stop = false;
    boolean illegalMove = false;
    int turn = 2;
    Tile[][] tiles;
    public Tile[][] getTiles(){
        return this.tiles;
    }
    public void setBoard(Tile[][] tiles, int turn){
        this.tiles = tiles;
        this.turn = turn;
    }
    public void initGame(int lenRow){
        int counter = 0;

        for (int n = 0; n < lenRow; n++) {
            for (int i = 0; i < lenRow; i++) {
                if(counter==4||counter==12||counter==20) {
                    tiles[n][i]  = new Tile(n, i, 2);
                } else {
                    tiles[n][i] = new Tile(n, i, 1);
                }
                counter += 1;
            }
        }

    }
    public int executeMove(Tile startTile, Tile destinationTile) {
        illegalMove = false;
        this.startTile = startTile;
        // Execution of the Move (also Checks the legality of the move)
        if (checkLegality(startTile, destinationTile, turn) && !stop) {
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

    public boolean checkLegality(Tile startTile, Tile destinationTile, int turn) {
        // Checking the legality of a move
        boolean legal = false;
        if(Math.abs(startTile.getRow() - destinationTile.getRow()) + Math.abs(startTile.getNum() - destinationTile.getNum())==1&& startTile.getState()==turn){
            legal = true;
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
        } else {
            texts.add("             ");
        }
        return texts;
    }
    public Game(int lenRow){
        tiles = new Tile[lenRow][lenRow];
    }
}
