/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Main Maze
 */
public class Maze {

    // Constant Default Values
    private static final int DEFAULT_ROW = 30;
    private static final int DEFAULT_COLUMN = 30;

    // Model, View, and Controller
    private static Controller controller;
    private static View view;
    private static MazeModel mazeModel;

    /*
    * Creates the Maze Game.
    */
    public static void main(String arg[]) {

        // Initialize the Controller, Model, and View
        controller = new Controller();
        mazeModel = new MazeModel();
        view = new View(mazeModel.getCells(), DEFAULT_ROW, DEFAULT_COLUMN, controller);

        // Pass the Model and View to the Controller
        controller.setMaze(mazeModel);
        controller.setView(view);

        view.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
