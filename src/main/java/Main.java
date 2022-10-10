import Logic.*;
import View.*;
public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();
        int lenRow = 5;
        TurnManagement manager = new TurnManagement(lenRow);
        int counter = 0;
        Tile tile = null;
        for (int n = 0; n < lenRow; n++) {
            for (int i = 0; i < lenRow; i++) {
                if(counter==4||counter==12||counter==20) {
                    tile = new Tile(n, i, 2);
                } else {
                    tile = new Tile(n, i, 1);
                }
                manager.addToTiles(tile);
                counter += 1;
            }
        }
        gui.initGUI(manager);
    }












}