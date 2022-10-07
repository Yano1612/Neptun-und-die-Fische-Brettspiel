import static org.junit.jupiter.api.Assertions.*;

class AITest {

    @org.junit.jupiter.api.Test
    void calculateNextMove() {

        TurnManagement manager = new TurnManagement(5);
        manager.addToTiles(new Tile(0,0,2));
        manager.addToTiles(new Tile(0,3,2));
        manager.addToTiles(new Tile(3,1,2));
        manager.addToTiles(new Tile(3,0,1));
        manager.addToTiles(new Tile(4,1,1));
        manager.addToTiles(new Tile(2,1,1));
        manager.addToTiles(new Tile(2,2,1));
        manager.addToTiles(new Tile(4,2,1));
        Tile[][] tiles = manager.getTiles();
        for (int n = 0; n <= 4; n++) {
            for (int i = 0; i <= 4; i++) {
                if(tiles[n][i] == null){
                    manager.addToTiles(new Tile(n,i ,0));
                }
            }
        }

        GUI gui= new GUI();
        gui.initGUI(manager);
    }
}