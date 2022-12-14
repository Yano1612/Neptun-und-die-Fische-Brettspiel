package View;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.*;

import java.util.List;
import Logic.*;
import AI.*;

public class GUI {
    // Declaration of necessary variables

    Display display = new Display();
    Shell shell = new Shell(display);
    Label lTurn = new Label(shell, SWT.NONE);


    Label lHints = new Label(shell, SWT.NONE);
    Game manager;
    int turn = 2;
    Tile mover = null;
    Canvas board = new Canvas(shell, SWT.NONE);
    Tile selectedTile = null;


    public void initGUI(Game TurnManager, int size) {
        this.manager = TurnManager;
        // Erstellen der GUI
        shell.setText("Board Game");
        shell.setSize(400, 600);
        shell.open();

        int lenRow = manager.getTiles().length;
        int width = lenRow * size;
        int height = lenRow * size;
        if (turn == 2) {
            lTurn.setText("Currently Active: Black   ");
        } else if (turn == 1) {
            lTurn.setText("Currently Active: White    ");
        }
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
                List<String> texts;
                AINormal ai = new AINormal();
                turn = ai.calculateNextMove(turn, manager);
                board.redraw();
                texts = manager.labelConfig();
                lTurn.setText(texts.get(0));
                lHints.setText(texts.get(1));
            }
            @Override
            public void mouseUp(MouseEvent mouseEvent) {
            }
        });
        bAI.pack();
        // creating the tiles
        board.setBounds(10, 10, width + 1, height + 1);
        board.addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent e) {
                Font font = new Font(shell.getDisplay(), "Arial", 14, SWT.BOLD);
                Font font2 = new Font(shell.getDisplay(), "Arial", 14, SWT.NONE);
                Tile[][] tiles = manager.getTiles();
                for (int n = 0; n < lenRow; n++) {
                    for (int i = 0; i < lenRow; i++) {
                        Tile tile = tiles[n][i];
                        e.gc.drawRectangle(n * size, i * size, size, size);
                        if(selectedTile != null) {
                            if (tile.isEqualTo(selectedTile)) {
                                e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_RED));
                                e.gc.fillRectangle(n * size, i * size, size, size);
                            }
                        }
                        if (tile.getState() == 1) {
                            e.gc.setFont(font2);
                            if(selectedTile != null) {
                                if (!(tile.isEqualTo(selectedTile))) {
                                    e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
                                }
                            }
                            e.gc.drawText("W", n * size + size / 2 - 7, i * size + size / 2 - 7);
                        } else if (tile.getState() == 2) {
                            e.gc.setFont(font);
                            if(selectedTile != null) {
                                if (!(tile.isEqualTo(selectedTile))) {
                                    e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
                                }
                            }
                            e.gc.drawText("B", n * size + 28, i * size + 28);
                        }
                    }

                }
            }
        });
        board.addMouseListener(new MouseListener() {
            @Override
            public void mouseDoubleClick(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseDown(MouseEvent e) {
                // Finding pos of click and redrawing GUI
                int tileX = (int) Math.ceil(e.x / (width / lenRow));
                int tileY = (int) Math.ceil(e.y / (width / lenRow));
                Tile tile = manager.getTiles()[tileX][tileY];
                System.out.println(tile);
                lHints.setText("                  ");
                board.redraw();
                if (mover != null) {
                    board.redraw();
                }

                if (e.button == 3) {
                    selectedTile = tile;
                    board.redraw();
                    // Selecting the starting Tile with a right click
                    mover = tile;
                } else if (mover != null) {
                    // Executing the Move on the Left-Clicked tile
                    turn = manager.executeMove(mover, tile);
                    mover = null;
                    selectedTile = null;
                    // Configurung Labels
                    List<String> texts;
                    texts = manager.labelConfig();
                    lTurn.setText(texts.get(0));
                    lHints.setText(texts.get(1));
                } else {
                    lHints.setText("No valid piece selected (Select piece by Right-Clicking it.)");
                    selectedTile = null;
                    board.redraw();
                }

            }

            @Override
            public void mouseUp(MouseEvent mouseEvent) {
            }
        });
        // Mainloop
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

}

