import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;


public class TurnManagement {

    Tile Mover;

    boolean stop = false;
    int turn;

    public int executeMove(Tile Mover, Tile tile, Tile[][] tiles, int turn, Label lHints, Label lTurn) {
        this.turn = turn;
        this.Mover = Mover;
        ArrayList<Tile> Adjacent = utility.getAdjacentTiles(tile, tiles);
        // Execution of the Move (also Checks the legality of the move)
        if (checkLegality(Adjacent, tile) && !stop) {
            tile.setState(turn);
            tile.redraw(); // Updates the Paint-Function
            Mover.setState(0);
            Mover.redraw();

            if (turn == 1) {
                turn = 2;
            } else if (turn == 2) {
                turn = 1;
            }
        } else {
            lHints.setText("Illegal Move");
            Mover.setSelected(false);
            Mover.redraw();

        }

        if (turn == 2 && !stop) {
            lTurn.setText("Currently Active: Black   ");
        } else if (turn == 1 & !stop) {
            lTurn.setText("Currently Active: White   ");

        }

        // Checking, if a player has won
        int winner = utility.checkWinner(tiles);
        if (winner != 0) {
            stop = true;
            if (winner == 1) {
                lTurn.setText("Winner: White   ");
            } else if (winner == 2) {
                lTurn.setText("Winner: Black   ");
            }
        }
        return turn;


    }

    public boolean checkLegality(List<Tile> Adjacent, Tile tile) {
        // Checking the legality of a move
        boolean legal = false;
        for (int i = 0; i <= Adjacent.size() - 1; i++) {
            if (Adjacent.get(i) != null) {
                if (Adjacent.get(i) == this.Mover) {
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
}
