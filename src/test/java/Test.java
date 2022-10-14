
import AI.AINormal;
import Logic.*;


class Test {
    @org.junit.jupiter.api.Test
    void winnableMoveTest() {
        Game manager = new Game(5);
        Tile[][] tiles = new Tile[5][5];
        tiles[0][0] = new Tile(0,0,2);
        tiles[0][3] = new Tile(0,3,2);
        tiles[3][1] = new Tile(3,1,2);
        tiles[3][0] = new Tile(3,0,1);
        tiles[4][1] = new Tile(4,1,1);
        tiles[2][1] = new Tile(2,1,1);
        tiles[2][2] = new Tile(2,2,1);
        tiles[4][2] = new Tile(4,2,1);

        for (int n = 0; n <= 4; n++) {
            for (int i = 0; i <= 4; i++) {
                if(tiles[n][i] == null){
                    tiles[n][i] = new Tile(n,i ,0);
                }
            }
        }
        manager.setBoard(tiles,2);
        AINormal ai = new AINormal();
        ai.calculateNextMove(2, manager);
        if(manager.getTiles()[3][0].getState() == 2){
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
    }
    @org.junit.jupiter.api.Test
    public void illegalMoveTest(){
        Game manager = new Game(5);
        manager.initGame(5);
        Tile[][] tiles = manager.getTiles();
        tiles[3][4].setState(0);
        manager.executeMove(tiles[2][2], tiles[3][3]);
        boolean illegDist = manager.getIllegal();
        manager.executeMove(tiles[1][1], tiles[1][0]);
        boolean illegState = manager.getIllegal();
        manager.executeMove(tiles[4][4], tiles[3][4]);
        boolean illegTurn = manager.getIllegal();
        manager.executeMove(tiles[2][2], tiles[2][3]);
        boolean legalMove = manager.getIllegal();

        if(illegDist && illegState && illegTurn && !legalMove){
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }
    }
    @org.junit.jupiter.api.Test
    public void winLogicTestWhite(){
        Game manager = new Game(5);
        Tile[][] tiles = new Tile[5][5];
        tiles[1][0] = new Tile(1,0,2);
        tiles[1][2] = new Tile(1,2,2);
        tiles[2][4] = new Tile(2,4,2);
        tiles[1][4] = new Tile(1,4,1);
        tiles[3][4] = new Tile(4,1,1);

        for (int n = 0; n <= 4; n++) {
            for (int i = 0; i <= 4; i++) {
                if(tiles[n][i] == null){
                    tiles[n][i] = new Tile(n,i ,0);
                }
            }
        }
        manager.setBoard(tiles, 2);
        manager.executeMove(tiles[2][4], tiles[1][4]);
        int x = Utility.checkWinner(tiles);
        if(x == 1){
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }

    }
    @org.junit.jupiter.api.Test
    public void winLogicTestBlack(){
        Game manager = new Game(5);
        Tile[][] tiles = new Tile[5][5];
        tiles[1][0] = new Tile(1,0,2);
        tiles[1][2] = new Tile(1,2,2);
        tiles[2][4] = new Tile(2,4,2);
        tiles[1][4] = new Tile(1,4,1);
        tiles[3][4] = new Tile(3,4,1);

        for (int n = 0; n <= 4; n++) {
            for (int i = 0; i <= 4; i++) {
                if(tiles[i][n] == null){
                    tiles[i][n] = new Tile(i,n ,0);
                }
            }
        }
        manager.setBoard(tiles, 2);
        manager.executeMove(tiles[2][4], tiles[3][4]);
        int x = Utility.checkWinner(manager.getTiles());
        if(x == 2){
            System.out.println("Passed");
        } else {
            System.out.println("Failed");
        }

    }
}


