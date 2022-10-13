package AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Logic.Tile;
import Logic.*;

public class AI {
    Tile startTile = null;
    Tile destinationTile = null;



    public int calculateNextMove(int turn, Game manager) {
            if(!manager.isFirstMove()) {

                List<List<Tile>> possibleMoves = getPossibleMoves(turn, copyTiles(manager.getTiles()), manager);
                int valueBest = -1000;
                List<Tile> bestMove = new ArrayList<>();
                for (int i = 0; i < possibleMoves.size(); i++) {
                    Game managerTest = new Game(manager.getTiles().length);
                    managerTest.setBoard(copyTiles(manager.getTiles()), turn);
                    managerTest.executeMove(possibleMoves.get(i).get(0), possibleMoves.get(i).get(0));
                    int valueMove = miniMax(managerTest, 10, turn, false, -1000, 1000);
                    if (valueMove > valueBest || i == 0) {
                        bestMove.clear();
                        valueBest = valueMove;
                        bestMove.add(possibleMoves.get(i).get(0));
                        bestMove.add(possibleMoves.get(i).get(1));
                        System.out.println("Found better move: " + bestMove + " with value " + valueBest);
                    }
                    if (valueMove == 3) {
                        break;
                    }
                }
                startTile = manager.getTiles()[bestMove.get(0).getRow()][bestMove.get(0).getNum()];
                destinationTile = manager.getTiles()[bestMove.get(1).getRow()][bestMove.get(1).getNum()];

            } else {
                startTile = manager.getTiles()[2][2];
                destinationTile = manager.getTiles()[2][3];
            }
            return manager.executeMove(startTile, destinationTile);
    }


    public List<Tile> getPossibleDestinationsTile(Tile tile, int color, Game manager) {
        List<Tile> possibleDestinations = new ArrayList<>();
        List<Tile> adj = Utility.getAdjacentTiles(tile, copyTiles(manager.getTiles()));
        for (int i = 0; i < adj.size(); i++) {
            if (adj.get(i) != null) {
                if (color == 1) {
                    if (adj.get(i).getState() == 0) {
                        possibleDestinations.add(adj.get(i));
                    }
                } else if (color == 2) {
                    if (adj.get(i).getState() == 1) {
                        possibleDestinations.add(adj.get(i));
                    }
                }
            }
        }
        return possibleDestinations;
    }


    private Tile[][] copyTiles(Tile[][] realTiles) {
        Tile[][] tilesCopy = new Tile[realTiles.length][realTiles.length];
        for (int i = 0; i < tilesCopy.length; i++) {
            for (int n = 0; n < tilesCopy.length; n++) {
                tilesCopy[i][n] = new Tile(i, n, realTiles[i][n].getState());
            }
        }

        return tilesCopy;
    }




    public int miniMax(Game node, int depth, int turn, boolean max, int alpha, int beta) {
        int winner = Utility.checkWinner(node.getTiles());
        if (winner == turn) {
            return 3;
        } else if (winner == 3 - turn) {
            return -3;
        }
        if (depth < 1) {
            return valOfPos(node, turn);
        }
        int value;
        List<List<Tile>> possibleMoves;
        if (max) {
            possibleMoves = getPossibleMoves(turn,node.getTiles(), node);
            value = -1000;
            for (int i = 0; i < possibleMoves.size(); i++) {

                Game child = new Game(node.getTiles().length);
                child.setBoard(copyTiles(node.getTiles()), turn);
                child.executeMove(possibleMoves.get(i).get(0), possibleMoves.get(i).get(1));
                value = Math.max(value, miniMax(child,depth-1,turn,false, alpha, beta));
                alpha = Math.max(alpha, value);
                if(value >= beta){
                    break;
                }
            }
        } else {
            possibleMoves = getPossibleMoves(-turn + 3,node.getTiles(), node);

            value = 1000;
            for (int i = 0; i < possibleMoves.size(); i++) {
                Game child = new Game(node.getTiles().length);
                child.setBoard(copyTiles(node.getTiles()), -turn+3);
                child.executeMove(possibleMoves.get(i).get(0), possibleMoves.get(i).get(1));

                value = Math.min(value, miniMax(child,depth-1,turn,true, alpha , beta));
                beta = Math.min(beta, value);
                if(value <= alpha){
                    break;
                }
            }
        }
        return value;

    }

    public List<List<Tile>> getPossibleMoves(int turn, Tile[][] tiles, Game manager) {
        List<Tile> availableTiles = new ArrayList<>();
        for (int r = 0; r < tiles[1].length; r++) {
            for (int n = 0; n < tiles[1].length; n++) {
                if (tiles[r][n].getState() == turn) {
                    availableTiles.add(tiles[r][n]);
                }
            }
        }
        List<Tile> possibleMovesTile;
        List<List<Tile>> possibleMoves = new ArrayList<>();
        Tile start;
        for (int i = 0; i < availableTiles.size(); i++) {
            start = availableTiles.get(i);
            possibleMovesTile = getPossibleDestinationsTile(start, turn, manager);
            for (int n = 0; n < possibleMovesTile.size(); n++) {
                if (manager.checkLegality(start, possibleMovesTile.get(n), turn)) {
                    List<Tile> pair = new ArrayList<>();
                    pair.add(start);
                    pair.add(possibleMovesTile.get(n));
                    possibleMoves.add(pair);
                }
            }
        }

        return possibleMoves;
    }
    public int valOfPos(Game node, int turn){

        int max = 0;
        int[] count = {0, 0};
        Tile[][] tiles = node.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j].getState() == 2) {
                    count[0]+=1;
                }
                if (tiles[j][i].getState() == 2) {
                    count[1]+=1;
                }
            }
            int[] values = new int[]{max, count[0], count[1]};
            Arrays.sort(values);
            max = values[2];
            count = new int[]{0, 0};
        }
        int val = (max-1) * (-2 * turn + 3);
        return val;

    }

}
