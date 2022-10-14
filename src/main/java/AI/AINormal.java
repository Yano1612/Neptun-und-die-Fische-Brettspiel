package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Logic.*;

import java.util.concurrent.ThreadLocalRandom;

public class AINormal {
    Tile startTile = null;
    Tile destinationTile = null;
    List<List<Tile>> NeutralMoves = new ArrayList<>();
    List<Tile> possibleMoves;
    List<List<Tile>> LosingMoves = new ArrayList<>();

    public int calculateNextMove(int turn, Game manager) {
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
        if (turn == 2) {
            winner = checkWinnableMoves(blackTiles, turn, manager);
        } else if (turn == 1) {
            winner = checkWinnableMoves(whiteTiles, turn, manager);
        }
        if (winner == null) {
            if (NeutralMoves.size() != 0) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    getTilesToMove(NeutralMoves, turn, manager);
                } else {
                    int x = ThreadLocalRandom.current().nextInt(NeutralMoves.size());
                    startTile = NeutralMoves.get(x).get(0);
                    destinationTile = NeutralMoves.get(x).get(1);
                }
            } else {
                startTile = LosingMoves.get(0).get(0);
                destinationTile = LosingMoves.get(0).get(1);
            }
        } else {
            destinationTile = winner;
        }

        return manager.executeMove(startTile, destinationTile);
    }


    public List<Tile> getPossibleDestinations(Tile tile, int color, Game manager) {
        List<Tile> possibleDestinations = new ArrayList<>();
        List<Tile> adj = Utility.getAdjacentTiles(tile, manager.getTiles());
        for (int i = 0; i < adj.size(); i++) {
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
        return possibleDestinations;
    }

    public Tile checkWinnableMoves(List<Tile> tiles, int turn, Game manager) {
        Tile[][] realTiles = manager.getTiles();
        Game managerTest = new Game(realTiles.length);
        managerTest.setBoard(Utility.copyTiles(realTiles), turn);
        Tile[][] testTiles;
        Tile winner = null;
        Tile start;
        for (int i = 0; i < tiles.size(); i++) {
            start = tiles.get(i);
            possibleMoves = getPossibleDestinations(start, turn, manager);
            for (int n = 0; n < possibleMoves.size(); n++) {
                testTiles = managerTest.getTiles();
                int checkWin = Utility.checkWinner(managerTest.getTiles());
                managerTest.executeMove(testTiles[start.getRow()][start.getNum()], testTiles[possibleMoves.get(n).getRow()][possibleMoves.get(n).getNum()]);
                if (checkWin == turn) {
                    winner = possibleMoves.get(n);
                    startTile = start;
                } else {
                    if (!managerTest.getIllegal()) {
                        if (!(checkWin == 3-turn)) {
                            List<Tile> pair = new ArrayList<>();
                            pair.add(start);
                            pair.add(possibleMoves.get(n));
                            NeutralMoves.add(pair);
                        } else {
                            List<Tile> pair = new ArrayList<>();
                            pair.add(start);
                            pair.add(possibleMoves.get(n));
                            LosingMoves.add(pair);
                        }
                    }
                    managerTest.setBoard(Utility.copyTiles(realTiles), turn);
                }
            }
        }
        return winner;
    }
    public void getTilesToMove(List<List<Tile>> list, int color, Game manager) {
        // TODO implement MiniMax algorithm
        List<Integer> scores = new ArrayList<>();
        int destScore;
        int startScore;
        for (int i = 0; i < list.size(); i++) {
            List<Tile> move = list.get(i);
            List<Tile> startADJ = Utility.getAdjacentTiles(move.get(0), manager.getTiles());
            List<Tile> destADJ = Utility.getAdjacentTiles(move.get(1), manager.getTiles());
            startScore = 2*(4-startADJ.size());
            destScore = 2*(4-destADJ.size());
            for (int n = 0; n < startADJ.size(); n++) {
                if (color == 2) {
                    scoreTile(startADJ,0,1);
                        //TODO fix index out of bounds
                    scoreTile(destADJ,0,1);
                } else if (color == 1) {
                    scoreTile(startADJ,2,1);
                    //TODO fix index out of bounds
                    scoreTile(destADJ,2,1);
                }
            }
            scores.add(destScore - startScore);
        }
        int bestScore = Collections.max(scores);
        List<Tile> bestMove = list.get(scores.indexOf(bestScore));
        startTile = bestMove.get(0);
        destinationTile = bestMove.get(1);
    }
    private int scoreTile(List<Tile> adj, int bestState, int midState) {
        int score = 0;
        for (int n = 0; n < adj.size(); n++) {
            int stateTile = adj.get(n).getState();
            if (stateTile == bestState) {
                score += 2;
            } else if (stateTile == midState) {
                score += 1;
            }
        }
        return score;
    }

}
