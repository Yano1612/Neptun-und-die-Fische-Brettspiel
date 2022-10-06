
import java.util.ArrayList;
import java.util.List;

public class AI {
    public static int calculateNextMove(int turn, TurnManagement manager){
        // Still Buggy
        /*
        Tile[][] tiles = manager.getTiles();
        Tile startTile = null;
        Tile destinationTile = null;
        List<Tile> whiteTiles = new ArrayList<>();
        List<Tile> blackTiles = new ArrayList<>();
        for(int r = 0; r <tiles[1].length;r++){
            for(int n = 0; n <tiles[1].length;n++){
                if(tiles[r][n].getState() == 1) {
                    whiteTiles.add(tiles[r][n]);
                } else {
                    blackTiles.add(tiles[r][n]);
                }
            }
        }
        boolean stop = false;
        List<Tile> adj = new ArrayList<>();
        if(turn == 2){
            while(!stop) {
                startTile = blackTiles.get((int) (Math.random() * blackTiles.size()));
                adj = utility.getAdjacentTiles(startTile, tiles);
                for (int i = 0; i <= 3; i++) {
                    if(adj.get(i) != null) {
                        if (adj.get(i).getState() != 1) {
                            adj.remove(i);
                        }
                    }
                }
                if(adj.size() > 0){
                    stop = true;
                    destinationTile = adj.get((int) (Math.random()*adj.size()));
                }
            }

        } else{
            while(!stop) {
                startTile = whiteTiles.get((int) (Math.random() * whiteTiles.size()));
                adj = utility.getAdjacentTiles(startTile, tiles);
                for (int i = 0; i <= 3; i++) {
                    if(adj.get(i) != null) {
                        if (adj.get(i).getState() != 0) {
                            adj.remove(i);
                        }
                    }
                }
                if(adj.size() > 0){
                    stop = true;
                    destinationTile = adj.get((int) (Math.random()*adj.size()));
                }
            }
        }
        System.out.println(startTile);


        System.out.println(destinationTile);
        return manager.executeMove(startTile, destinationTile); */
        return 1;
    }
}
