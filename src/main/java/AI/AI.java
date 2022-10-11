package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Logic.Tile;
import Logic.*;

import java.util.concurrent.ThreadLocalRandom;

public class AI {
    Tile startTile = null;
    Tile destinationTile = null;
    TurnManagement manager = null;
    List<List<Tile>> NeutralMoves = new ArrayList<>();

    public int calculateNextMove(int turn, TurnManagement manager) {
        this.manager = manager;
        Tile[][] tiles = manager.getTiles();

        List<Tile> whiteTiles = new ArrayList<>();
        List<Tile> blackTiles = new ArrayList<>();
        for (int r = 0; r < tiles[1].length; r++) {
            for (int n = 0; n < tiles[1].length; n++) {
                if (tiles[r][n].getState() == 1) {
                    whiteTiles.add(tiles[r][n]);
                } else if (tiles[r][n].getState() == 2) {
                    blackTiles.add(tiles[r][n]);
                }
            }
        }
        Tile winner = null;
        if(turn == 2) {
            winner = checkWinnableMoves(blackTiles, turn);
        } else if (turn == 1){
            winner = checkWinnableMoves(whiteTiles, turn);
        }
        if (winner == null) {
            getTilesToMove(NeutralMoves, turn);
        } else {
            destinationTile = winner;
        }
        System.out.println("\n" + startTile);
        System.out.println(destinationTile);
        return manager.executeMove(startTile, destinationTile);
    }


    public List<Tile> getPossibleDestinations(Tile tile, int color) {
        List<Tile> possibleDestinations = new ArrayList<>();
        List<Tile> adj = Utility.getAdjacentTiles(tile, manager.getTiles());
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

    public Tile checkWinnableMoves(List<Tile> tiles, int turn) {
        Tile[][] realTiles = manager.getTiles();
        TurnManagement managerTest = new TurnManagement(realTiles.length);
        managerTest.setBoard(copyTiles(realTiles), turn);
        List<Tile> possibleMoves;
        Tile[][] testTiles;
        Tile winner = null;
        Tile start;
        for (int i = 0; i < tiles.size(); i++) {
            start = tiles.get(i);
            possibleMoves = getPossibleDestinations(start, turn);
            for (int n = 0; n < possibleMoves.size(); n++) {
                testTiles = managerTest.getTiles();
                managerTest.executeMove(testTiles[start.getRow()][start.getNum()], testTiles[possibleMoves.get(n).getRow()][possibleMoves.get(n).getNum()]);
                if (Utility.checkWinner(managerTest.getTiles()) == turn) {
                    winner = possibleMoves.get(n);
                    startTile = start;
                } else {
                    if (!managerTest.getStop() && !managerTest.getIllegal()) {
                        List<Tile> pair = new ArrayList<>();
                        pair.add(start);
                        pair.add(possibleMoves.get(n));
                        NeutralMoves.add(pair);
                    }
                    managerTest.setBoard(copyTiles(realTiles), turn);
                }
            }
        }
        System.out.println("NEUTRAL" + NeutralMoves);
        return winner;
    }

    public List<Tile> returnMovedTiles() {
        List<Tile> movedTiles = new ArrayList<>();
        movedTiles.add(startTile);
        movedTiles.add(destinationTile);
        return movedTiles;
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

    public void getTilesToMove(List<List<Tile>> list, int color) {
        List<Integer> scores = new ArrayList<>();
        int destScore = 0;
        int startScore = 0;
        for (int i = 0; i < list.size(); i++) {
            List<Tile> move = list.get(i);
            List<Tile> startADJ = Utility.getAdjacentTiles(move.get(0), manager.getTiles());
            List<Tile> destADJ = Utility.getAdjacentTiles(move.get(1), manager.getTiles());
            startScore = 0;
            destScore = 0;
            for (int n = 0; n < startADJ.size(); n++) {
                if (color == 2) {
                    if (startADJ.get(n) != null && destADJ.get(n) != null) {
                        int stateAdj = startADJ.get(n).getState();
                        if (stateAdj == 0) {
                            startScore += 2;
                        } else if (stateAdj == 2) {
                            startScore += 1;
                        }
                        stateAdj = destADJ.get(n).getState();
                        if (stateAdj == 0) {
                            destScore += 2;
                        }else if (stateAdj == 2) {
                            destScore += 1;
                        }
                    } else {
                        if (startADJ.get(n) == null) {
                            startScore += 2;
                        } else if (destADJ.get(n) == null) {
                            destScore += 2;
                        }
                    }
                } else if (color == 1){
                    if (startADJ.get(n) != null && destADJ.get(n) != null) {
                        int stateAdj = startADJ.get(n).getState();
                        if (stateAdj == 1) {
                            startScore += 2;
                        } else if (stateAdj == 2) {
                            startScore += 3;
                        }
                        stateAdj = destADJ.get(n).getState();
                        if (stateAdj == 1) {
                            destScore += 1;
                        } else if (stateAdj == 2) {
                            destScore += 3;
                        }
                    }
                }

            }
            scores.add(destScore - startScore);
        }
        int bestScore = Collections.max(scores);
        List<Tile> bestMove = list.get(scores.indexOf(bestScore));
        System.out.println(bestMove);
        startTile = bestMove.get(0);
        destinationTile = bestMove.get(1);

    }
}