package Logic;
import java.util.ArrayList;
import java.util.List;


public class Utility {
    private static void addTile(List<Tile> list ,int row, int num, Tile[][] tiles){
        if(row >= 0 && row < tiles.length && num >=0&& num < tiles.length){
            list.add(tiles[row][num]);
        }
    }
    public static List<Tile> getAdjacentTiles(Tile tile, Tile[][] tiles) {
        // Creating a list of Adjacent tiles
        List<Tile> adjacent = new ArrayList<>();
        addTile(adjacent, tile.getRow()-1,tile.getNum(), tiles);
        addTile(adjacent, tile.getRow()+1,tile.getNum(), tiles);
        addTile(adjacent, tile.getRow(),tile.getNum()+1, tiles);
        addTile(adjacent, tile.getRow(),tile.getNum()-1, tiles);
        return adjacent;
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
        for(int i = 0; i < blackTiles.size(); i++) {
            if(blackTiles.get(i).getRow() == blackTiles.get(0).getRow()){
                countY += 1;
            }
            if(blackTiles.get(i).getNum() == blackTiles.get(0).getNum()){
                countX += 1;
            }
        }
        if(countY == blackTiles.size() || countX == blackTiles.size()){
            won += 1;
        }
        // Checking if Black has won
        boolean possibleMoves = false;
        for (int k = 0; k < blackTiles.size(); k++) {
            List<Tile> adj = getAdjacentTiles(blackTiles.get(k), tiles);
            for (int i = 0; i < adj.size(); i++) {
                if (adj.get(i).getState() == 1) {
                    possibleMoves = true;
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
    public static Tile[][] copyTiles(Tile[][] realTiles) {
        Tile[][] tilesCopy = new Tile[realTiles.length][realTiles.length];
        for (int i = 0; i < tilesCopy.length; i++) {
            for (int n = 0; n < tilesCopy.length; n++) {
                tilesCopy[i][n] = new Tile(i, n, realTiles[i][n].getState());
            }
        }
        return tilesCopy;
    }
    private Utility(){}

}

