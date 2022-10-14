package Logic;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private int winner = 0;
    private boolean stop = false;
    private boolean illegalMove = false;
    private int turn = 2;
    private Tile[][] tiles;
    public Tile[][] getTiles(){
        return this.tiles;
    }
    private boolean firstMove = true;

    public boolean isFirstMove() {
        return firstMove;
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
                    tiles[i][n]  = new Tile(i, n, 2);
                } else {
                    tiles[i][n] = new Tile(i, n, 1);
                }
                counter += 1;
            }
        }

    }
    public int executeMove(Tile startTile, Tile destinationTile) {
        illegalMove = false;
        // Execution of the Move (also Checks the legality of the move)
        if (checkLegality(startTile, destinationTile, turn) && !stop) {
            destinationTile.setState(turn);
            startTile.setState(0);
            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
            firstMove = false;
        } else {
            illegalMove = true;
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
    public boolean getIllegal() {
        return this.illegalMove;
    }
}