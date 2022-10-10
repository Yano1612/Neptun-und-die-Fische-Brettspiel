import Logic.*;
import View.*;
public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();
        int lenRow = 5;
        TurnManagement manager = new TurnManagement(lenRow);
        manager.initGame(lenRow);
        gui.initGUI(manager);
    }












}