package AI;
import java.util.ArrayList;
import java.util.List;
import Logic.Tile;
import Logic.*;
import java.util.concurrent.ThreadLocalRandom;

public class AI {
    Tile startTile = null;
    Tile destinationTile = null;
    TurnManagement manager = null;

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
        Tile winner = null;

        if (turn == 2) {
            winner = checkWinnableMoves(blackTiles, turn);
            if(winner == null) {
                getTilesToMove(blackTiles, turn);
            } else {
                destinationTile = winner;
            }
        } else if (turn == 1) {
            winner = checkWinnableMoves(whiteTiles, turn);
            if(winner == null) {
                getTilesToMove(whiteTiles, turn);
            } else {
                destinationTile = winner;
            }

        }

        System.out.println("\n" + startTile);
        System.out.println(destinationTile);
        return manager.executeMove(startTile, destinationTile);
    }



    public void getTilesToMove(List<Tile> list, int color) {
        List<Tile> possibleDestinations;
        while (destinationTile == null) {
            startTile = list.get(ThreadLocalRandom.current().nextInt(list.size()));
            possibleDestinations = getPossibleDestinations(startTile, color);

            if(possibleDestinations.size() > 0){
                destinationTile = possibleDestinations.get(ThreadLocalRandom.current().nextInt(possibleDestinations.size()));
            }
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
        TurnManagement managerTest = new TurnManagement(manager.getTiles().length);
        managerTest.setTiles(manager.getTiles().clone());
        List<Tile> possibleMoves;

        Tile winner = null;

        for(int i = 0; i<tiles.size();i++){
            startTile = tiles.get(i);
            possibleMoves = getPossibleDestinations(startTile,turn);
            for(int n = 0; n < possibleMoves.size();n++) {
                managerTest.executeMove(startTile, possibleMoves.get(n));
                if(managerTest.getStop()){
                    winner = possibleMoves.get(n);
                } else {
                    managerTest.setTiles(manager.getTiles().clone());
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

}