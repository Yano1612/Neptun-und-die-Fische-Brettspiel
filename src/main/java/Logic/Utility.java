package Logic;
import java.util.ArrayList;
import java.util.List;


public class Utility {
    public static List<Tile> getAdjacentTiles(Tile tile, Tile[][] tiles) {
        // Creating a list of Adjacent tiles
        List<Tile> Adjacent = new ArrayList<>();
        if(!(tile.getRow()+1 >= tiles[1].length)) {
            Adjacent.add(tiles[tile.getRow() + 1][tile.getNum()]);
        } else {
            Adjacent.add(null);
        }
        if(!(tile.getRow()-1 < 0)){
            Adjacent.add(tiles[tile.getRow() - 1][tile.getNum()]);
        } else {
            Adjacent.add(null);
        }
        if(!(tile.getNum()+1 >= tiles[1].length)){
            Adjacent.add(tiles[tile.getRow()][tile.getNum() + 1]);
        } else {
            Adjacent.add(null);
        }
        if(!(tile.getNum()-1 < 0 )) {
            Adjacent.add(tiles[tile.getRow()][tile.getNum() - 1]);
        } else {
            Adjacent.add(null);
        }

        return Adjacent;
    }

    public static int checkWinner(Tile[][] tiles) {
        // Checking if white has won
        int won = 0;
        List<Tile> blackTiles = new ArrayList<>();
        for (int i = 0; i < tiles.length; i++) {
            for (int k = 0; k < tiles.length; k++) {
                if(tiles[i][k].getState() == 2) {
                    blackTiles.add(tiles[i][k]);
                }
            }
        }
        int countX = 0;
        int countY = 0;
        for(int i = 0; i<blackTiles.size();i++) {
            if(blackTiles.get(0).getRow() == blackTiles.get(i).getRow()){
                countY += 1;
            }
            if(blackTiles.get(0).getNum() == blackTiles.get(i).getNum()){
                countX += 1;
            }
        }
        if(countY == blackTiles.size() || countX == blackTiles.size()){
            won += 1;
        }
        // Checking if Black has won
        boolean possibleMoves = false;
        for (int k = 0; k < 3; k++) {
            List<Tile> Adj = getAdjacentTiles(blackTiles.get(k), tiles);
            for (int i = 0; i < 4; i++) {
                if (Adj.get(i) != null) {
                    if (Adj.get(i).getState() == 1) {
                        possibleMoves = true;
                    }
                }
            }
        }
        if (!possibleMoves) {
            won += 2;
        }
        if(won == 3){
            won = 1;
        }
        return won;
    }
}

