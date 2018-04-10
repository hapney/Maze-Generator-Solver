/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import java.awt.*;

/*
 * Total View for the Maze Game.
 */
public class View extends JFrame {

    // Constant MazeView Dimensions
    private static final int DEFAULT_MAZE_VIEW_WIDTH = 650;
    private static final int DEFAULT_MAZE_VIEW_HEIGHT = 650;

    // Views
    private static MazeView mazeView;
    private static ConsoleView consoleView;
    private static PanelView panelView;

    /*
     * Constructor for the View class.
     *
     * @param   cells           The 2D list of cells in the maze
     * @param   defaultRows     The default number of rows in the maze
     * @param   defaultColumns  The default number of columns in the maze
     * @param   c               The controller used
     */
    public View(Cell[][] cells, int defaultRows, int defaultColumns, Controller C) {

        super("Maze Game");

        // Initialize Views
        mazeView = new MazeView(cells, defaultRows, defaultColumns);
        consoleView = new ConsoleView(defaultRows, defaultColumns, C);
        panelView = new PanelView();

        // Create Parent View of MazeView
        JPanel mazeViewParent = new JPanel();
        mazeViewParent.setPreferredSize(new Dimension(DEFAULT_MAZE_VIEW_WIDTH, DEFAULT_MAZE_VIEW_HEIGHT));
        mazeViewParent.add(mazeView, BorderLayout.CENTER);

        // Set Up Container
        Container c = getContentPane();

        c.setLayout(new FlowLayout());

        // Set Up Views
        c.add(mazeViewParent);
        c.add(consoleView);
        c.add(panelView);

        // Create Window
        setSize(1000, 775);
        setResizable(false);
        setVisible(true);

    }

    /*
     * Returns the maze view.
     *
     * @param   The mazeView
     */
    public MazeView getMazeView() {
        return mazeView;
    }

    /*
     * Returns the console view.
     *
     * @param   The consoleView
     */
    public ConsoleView getConsoleView() {
        return consoleView;
    }

    /*
     * Returns the panelView
     *
     * @param   The panelView
     */
    public PanelView getPanelView() {
        return panelView;
    }


}
