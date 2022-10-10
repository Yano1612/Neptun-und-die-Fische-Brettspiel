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
        // Still Buggy
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
        Tile winner;

        if (turn == 2) {
            winner = checkWinnableMoves(blackTiles, turn);
            if(winner == null) {
                getTilesToMove(NeutralMoves, turn);
            } else {
                destinationTile = winner;
            }
        } else if (turn == 1) {
            winner = checkWinnableMoves(whiteTiles, turn);
            if(winner == null) {
                getTilesToMove(NeutralMoves, turn);
            } else {
                destinationTile = winner;
            }

        }

        System.out.println("\n" + startTile);
        System.out.println(destinationTile);
        return manager.executeMove(startTile, destinationTile);
    }



    public void getTilesToMove(List<List<Tile>> list, int color) {
        if(color == 2) {
            List<Integer> scores = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                List<Tile> move = list.get(i);

                List<Tile> startADJ = Utility.getAdjacentTiles(move.get(0), manager.getTiles());
                List<Tile> destADJ = Utility.getAdjacentTiles(move.get(1), manager.getTiles());
                int startScore = 0;
                int destScore = 0;
                for(int n = 0; n <startADJ.size();n++) {
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
                        } else if (stateAdj == 2) {
                            destScore += 1;
                        }
                    } else {
                        if(startADJ.get(n) == null){
                            startScore += 2;
                        } else if(destADJ.get(n) == null){
                            destScore += 2;
                        }
                    }
                }
                scores.add(destScore - startScore);
            }
            int bestScore = Collections.max(scores);
            List<Tile> bestMove = list.get(scores.indexOf(bestScore));
            startTile = bestMove.get(0);
            destinationTile = bestMove.get(1);
        }
        else if (color == 1){
            // TODO write code for white bot
            startTile = list.get(ThreadLocalRandom.current().nextInt(list.size())).get(0);
            destinationTile = list.get(ThreadLocalRandom.current().nextInt(list.size())).get(1);
        }
    }

    public List<Tile> getPossibleDestinations(Tile tile, int color){
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
    public Tile checkWinnableMoves(List<Tile> tiles, int turn){
        Tile[][] realTiles = manager.getTiles();
        TurnManagement managerTest = new TurnManagement(realTiles.length);
        managerTest.setBoard(copyTiles(realTiles), turn);
        List<Tile> possibleMoves;
        Tile[][] testTiles;
        Tile winner = null;
        for(int i = 0; i<tiles.size();i++){
            startTile = tiles.get(i);
            possibleMoves = getPossibleDestinations(startTile,turn);
            for(int n = 0; n < possibleMoves.size();n++) {
                testTiles = managerTest.getTiles();
                managerTest.executeMove(testTiles[startTile.getRow()][startTile.getNum()], testTiles[possibleMoves.get(n).getRow()][possibleMoves.get(n).getNum()]);
                if(Utility.checkWinner(managerTest.getTiles()) == turn){
                    winner = possibleMoves.get(n);
                } else {
                    if(!managerTest.getStop()){
                        List<Tile> pair = new ArrayList<>();
                        pair.add(startTile);
                        pair.add(possibleMoves.get(n));
                        NeutralMoves.add(pair);
                    }
                    managerTest.setBoard(copyTiles(realTiles), turn);
                }
            }
        }
        return winner;
    }
    public List<Tile> returnMovedTiles() {
        List<Tile> movedTiles = new ArrayList<>();
        movedTiles.add(startTile);
        movedTiles.add(destinationTile);
        return movedTiles;
    }
    private Tile[][] copyTiles(Tile[][] realTiles){
        Tile[][] tilesCopy = new Tile[realTiles.length][realTiles.length];
        for(int i = 0; i<tilesCopy.length; i++){
            for(int n = 0; n<tilesCopy.length; n++){
                tilesCopy[i][n] = new Tile(i, n, realTiles[i][n].getState());
            }
        }

        return tilesCopy;
    }

}