import java.util.ArrayList;
import java.util.List;


public class utility {
    public static List<Tile> getAdjacentTiles(Tile tile, Tile[][] tiles) {
        // Creating a list of Adjacent tiles
        List<Tile> Adjacent = new ArrayList<>();
        if(!(tile.row+1 >= tiles[1].length)) {
            Adjacent.add(tiles[tile.row + 1][tile.num]);
        } else {
            Adjacent.add(null);
        }
        if(!(tile.row-1 < 0)){
            Adjacent.add(tiles[tile.row - 1][tile.num]);
        } else {
            Adjacent.add(null);
        }
        if(!(tile.num+1 >= tiles[1].length)){
            Adjacent.add(tiles[tile.row][tile.num + 1]);
        } else {
            Adjacent.add(null);
        }
        if(!(tile.num-1 < 0 )) {
            Adjacent.add(tiles[tile.row][tile.num - 1]);
        } else {
            Adjacent.add(null);
        }

        return Adjacent;
    }

    public static int checkWinner(Tile[][] tiles) {
        // Checking if white has won
        int won = 0;
        int blacksVertical;
        int blacksHorizontal;
        List<Tile> blackTiles = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            blacksVertical = 0;
            blacksHorizontal = 0;
            for (int k = 0; k < tiles.length; k++) {
                if (tiles[i][k].getState() == 2) {
                    blacksHorizontal += 1;
                    blackTiles.add(tiles[i][k]);
                    if (blacksHorizontal == 3) {
                        won = 1;
                        break;
                    }
                }
                if (tiles[k][i].getState() == 2) {
                    blacksVertical += 1;
                    if (blacksVertical == 3) {
                        won = 1;
                        break;
                    }
                }
            }
        }
        // Checking if Black has won
        boolean possibleMoves = false;
        for (int k = 0; k < 3; k++) {
            List<Tile> Adj = getAdjacentTiles(blackTiles.get(k), tiles);
            for (int i = 0; i < 3; i++) {
                if (Adj.get(i) != null) {
                    if (Adj.get(i).getState() == 1) {
                        possibleMoves = true;
                    }
                }
            }
        }
        if (!possibleMoves) {
            won = 2;
        }
        return won;
    }
}

