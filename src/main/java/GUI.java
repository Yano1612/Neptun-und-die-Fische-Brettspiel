import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class GUI {
    // Declaration of necessary variables


    Display display = new Display();
    Shell shell = new Shell(display);
    Label lTurn = new Label(shell, SWT.NONE);
    static Tile[][] tiles = new Tile[5][5];

    int counter = 0;
    Label lHints = new Label(shell, SWT.NONE);
    TurnManagement manager = new TurnManagement();
    int turn = 2;
    Tile Mover = null;
    boolean inactivePieceSelected = false;
    public void initGUI() {
        // Erstellen der GUI
        shell.setText("Board Game");
        shell.setSize(400, 600);
        shell.open();


        int lenRow = 4;
        // erstellen der Spielfelder
        Font font = new Font(shell.getDisplay(), "Arial", 14, SWT.BOLD);
        Font font2 = new Font(shell.getDisplay(), "Arial", 14, SWT.NONE);
        for (int n = 0; n <= lenRow; n++) {
            for (int i = 0; i <= lenRow; i++) {
                createTile(i * 70 + 10, n * 70 + 10, shell, n, i, font, font2);
            }
        }
        lTurn.setText("Currently Active: Black   ");
        lTurn.setBounds(10, 370, 100, 30);
        lTurn.pack();

        lHints.setText("                                                                                                       ");
        lHints.setBounds(10, 400, 100, 30);
        lHints.pack();

        Button bAI = new Button(shell, SWT.NONE);
        bAI.setText("Let AI do next Move");
        bAI.setBounds(10, 450, 100, 30);
        bAI.addMouseListener(new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseDown(MouseEvent mouseEvent) {
                manager.executeMove(tiles[0][4], tiles[1][4], tiles, turn, lHints, lTurn);
            }

            @Override
            public void mouseUp(MouseEvent mouseEvent) {
            }
        });
        bAI.pack();

        // Mainloop
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
        font.dispose();
        font2.dispose();

    }

    public void createTile(int x, int y, Shell shell, int row, int num, Font font, Font font2) {
        // Creating tiles
        Tile tile = new Tile(shell, SWT.NO_REDRAW_RESIZE, row, num, counter);
        tiles[row][num] = tile;
        tile.setBounds(x, y, 50, 50);
        tile.pack();
        counter += 1;
        tile.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                // Painting the Tiles
                e.gc.drawRectangle(0, 0, 50, 50);
                if (tile.getSelected()) {
                    e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_RED));
                    e.gc.fillRectangle(0, 0, 50, 50);
                    tile.setSelected(false);
                }
                if (tile.getState() == 1) {
                    e.gc.setFont(font2);
                    e.gc.drawText("W", 17, 15);

                } else if (tile.getState() == 2) {
                    e.gc.setFont(font);
                    e.gc.drawText("B", 17, 15);
                }


            }
        });
        tile.addMouseListener(new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
                // Selecting a piece by Right-Clicking
                tile.redraw();
                if (Mover != null) {
                    Mover.redraw();
                }
                if (e.button == 3 && tile.getState() == turn) {
                    tile.setSelected(true);
                    tile.redraw();
                    Mover = tile;
                    System.out.println(utility.getAdjacentTiles(Mover, tiles));

                } else if (Mover != null) {
                    turn = manager.executeMove(Mover, tile, tiles, turn, lHints, lTurn);
                } else {
                    if (!inactivePieceSelected) {
                        lHints.setText("No piece selected (Select piece by Right-Clicking it.)");
                        tile.setSelected(false);
                        tile.redraw();
                    } else {
                        lHints.setText("Selected piece is not active");
                        inactivePieceSelected = true;
                    }
                }
            }


            @Override
            public void mouseUp(MouseEvent mouseEvent) {
            }
        });
    }

}

