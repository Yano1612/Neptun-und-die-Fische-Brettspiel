
import java.util.ArrayList;
import java.util.List;

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
                } else if(tiles[r][n].getState() == 2){
                    blackTiles.add(tiles[r][n]);
                }
            }
        }
        if (turn == 2) {
            getTilesToMove(blackTiles, 2);
        } else if (turn == 1) {
            getTilesToMove(whiteTiles, 1);
        }

        System.out.println("\n" + startTile);
        System.out.println(destinationTile);
        return manager.executeMove(startTile, destinationTile);
    }


    public List<Tile> returnMovedTiles() {
        List<Tile> movedTiles = new ArrayList<>();
        movedTiles.add(startTile);
        movedTiles.add(destinationTile);
        return movedTiles;
    }

    public void getTilesToMove(List<Tile> list, int color) {
        List<Tile> adj;
        while(destinationTile == null) {
            startTile = list.get(ThreadLocalRandom.current().nextInt(list.size()));
            adj = Utility.getAdjacentTiles(startTile, manager.getTiles());

            for (int i = 0; i < adj.size(); i++) {
                if (adj.get(i) != null) {
                    if (color == 1) {
                        if (adj.get(i).getState() == 0) {
                            destinationTile = adj.get(i);
                        }
                    } else if (color == 2) {
                        if (adj.get(i).getState() == 1) {
                            destinationTile = adj.get(i);
                        }
                    }
                }
            }
        }
    }
}
