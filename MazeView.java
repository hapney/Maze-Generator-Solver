/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/*
 * Maze View for the Maze Game.
 */
public class MazeView extends JPanel {

    // Starting and Ending Cell Constants
    private static final int START_X = 0;
    private static final int START_Y = 0;
    // Note: Ending Cell Coordinates are current rows/columns - 1
    private static final Color START_COLOR = Color.GREEN;
    private static final Color END_COLOR = Color.RED;

    // Constant Max Values
    private static final int MAX_ROWS = 50;
    private static final int MAX_COLUMNS = 50;

    // Constant MazeView Dimensions
    private static final int DEFAULT_WIDTH = 645;
    private static final int DEFAULT_HEIGHT = 645;

    private static int currentWidth;
    private static int currentHeight;

    private static CellView cellViews[][];

    // Maze Solution Lists
    private LinkedList<CellView> mazeSolution;
    private LinkedList<Color> mazeSolutionColors;

    /*
     * The constructor for the MazeView.
     *
     * @param   cells       The cells to fill the maze with.
     */
    public MazeView(Cell[][] cells, int defaultRows, int defaultColumns) {
        super();

        // Initialize the solution lists
        mazeSolution = new LinkedList<>();
        mazeSolutionColors = new LinkedList<>();

        // Set the default widths
        currentWidth = DEFAULT_WIDTH;
        currentHeight = DEFAULT_HEIGHT;

        // Initialize the Cell Views
        cellViews = new CellView[MAX_ROWS][MAX_COLUMNS];
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cellViews[r][c] = new CellView(cells[r][c], DEFAULT_WIDTH / defaultColumns, DEFAULT_HEIGHT / defaultRows);
            }
        }

        // Set the JPanel Info
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setLayout(new GridLayout(defaultRows, defaultColumns, 0, 0));

        fillMazeView(cells, defaultRows, defaultColumns);

    }

    /*
     * Change the size of the mazeView
     *
     * @param   cells       The cells to fill the maze with
     * @param   rows        The number of rows of the maze
     * @param   columns     The number of columns of the maze
     */
    public void changeSize(Cell[][] cells, int rows, int columns) {

        // Check for Bounds Error
        if (rows > MAX_ROWS || rows < 0 || columns < 0 || columns > MAX_COLUMNS) {
            JOptionPane.showMessageDialog(MazeView.this, "Sorry, an error occurred! Please try again later.");
            System.out.println("Error: changeSize row and column out of bounds");
            System.exit(0);
        }

        this.removeAll();
        clearMaze();

        int windowWidth = DEFAULT_WIDTH;
        int windowHeight = DEFAULT_HEIGHT;

        // Change Maze View Size to Ensure Cell Shape As Square
        if (rows > columns) {
            windowWidth = (windowHeight / rows) * columns;
        }
        else if (columns > rows) {
            windowHeight = (windowWidth / columns) * rows;
        }

        currentWidth = windowWidth;
        currentHeight = windowHeight;

        // Color the Start and End Cells
        cellViews[START_X][START_Y].setColor(START_COLOR);
        cellViews[rows - 1][columns - 1].setColor(END_COLOR);

        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setLayout(new GridLayout(rows, columns, 0, 0));

        fillMazeView(cells, rows, columns);

        refreshView();

    }

    /*
     * Fill the maze view with cells.
     *
     * @param   cells       The cells to fill the maze with
     * @param   rows        The number of rows of the maze
     * @param   columns     The number of columns of the maze
     */
    private void fillMazeView(Cell[][] cells, int rows, int columns) {

        // Color the Start and End Cells
        cellViews[START_X][START_Y].setColor(START_COLOR);
        cellViews[rows - 1][columns - 1].setColor(END_COLOR);

        // Draw the cell walls and add the cell to the view
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                cellViews[r][c].drawCellWalls(cells[r][c], currentWidth / columns, currentHeight / rows);
                add(cellViews[r][c]);
            }
        }
    }

    /*
     * Refresh the view.
     */
    private void refreshView() {
        revalidate();
        repaint();
    }

    /*
     * Clear everything from all cells.
     */
    private void clearMaze() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cellViews[r][c].clearCell();
            }
        }
    }

    /*
     * Clear the colors of all the cells.
     */
    private void clearColors() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cellViews[r][c].clearColor();
            }
        }
    }

    /*
     * Returns the cell view 2D list.
     *
     * @returns     the cell view 2D list
     */
    public CellView[][] getCellViews() {
        return cellViews;
    }

    /*
     * Break the wall between two cells.
     *
     * @param   r1      The row for the first cell
     * @param   c1      The column for the first cell
     * @param   r2      The row for the second cell
     * @param   c2      The column for the second cell
     */
    public void breakWall(int r1, int c1, int r2, int c2) {

        if (r1 == r2) {
            // Cells are vertically aligned

            if (c1 < c2) {
                // Cell1 : Cell2
                cellViews[r1][c1].removeRightWall();
                cellViews[r2][c2].removeLeftWall();
            }
            else if (c2 < c1) {
                // Cell2 : Cell1
                cellViews[r2][c2].removeRightWall();
                cellViews[r1][c1].removeLeftWall();
            }
        }
        else if (c1 == c2) {
            // Cells are horizontally aligned

            if (r1 < r2) {
                // Cell1
                // Cell2
                cellViews[r1][c1].removeBottomWall();
                cellViews[r2][c2].removeTopWall();
            }
            else if (r2 < r1) {
                // Cell2
                // Cell1
                cellViews[r2][c2].removeBottomWall();
                cellViews[r1][c1].removeTopWall();
            }
        }

    }

    /*
     * Reset the solution list.
     *
     * @param   cells       The list of cells in the maze
     * @param   rows        The number of rows in the maze
     * @param   columns     The number of columns in the maze
     */
    public void reset(Cell cells[][], int rows, int columns) {
        this.removeAll();
        clearMaze();
        fillMazeView(cells, rows, columns);
        refreshView();

        mazeSolution.clear();
        mazeSolutionColors.clear();
    }

    /*
     * Reset the solution list, leaving the maze.
     *
     * @param   cells       The list of cells in the maze
     * @param   rows        The number of rows in the maze
     * @param   columns     The number of columns in the maze
     */
    public void resetSolution(Cell cells[][], int rows, int columns) {

        // Remove everything and refresh view
        this.removeAll();
        clearColors();
        fillMazeView(cells, rows, columns);
        refreshView();

        // Clear the solution list
        mazeSolution.clear();
        mazeSolutionColors.clear();
    }

    /*
     * Add new cell to maze solution list.
     *
     * @param   cellR       The cell row to add
     * @param   cellC       The cell column to add
     * @param   c           The color to make the cell
     */
    public void addToMazeSolution(int cellR, int cellC, Color c) {
        mazeSolution.add(cellViews[cellR][cellC]);
        if (!(cellR == START_X && cellC == START_Y)) {
            mazeSolutionColors.add(c);
        }
        else {
            mazeSolutionColors.add(START_COLOR);
        }
    }

    /*
     * Pop a single move from the solution list.
     *
     * @return      0 if newly visited cell
     *              1 if backtracked cell
     *              -1 if maze solution list is empty
     */
    public int popMazeSolution() {

        // Make sure the solution list isn't empty
        if (mazeSolution.size() > 0) {
            Color c = mazeSolutionColors.removeFirst();
            mazeSolution.removeFirst().setColor(c);
            if (c == Color.CYAN) {
                return 0;
            }
            else {
                return 1;
            }
        }
        return -1;
    }


}
