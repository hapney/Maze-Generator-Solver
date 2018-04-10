/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import java.util.LinkedList;

/*
 * Maze Model used for the Maze Game.
 */
public class MazeModel {

    // Constant Neighboring Cell Coordinates
    private static final int NEIGHBOR_R[] = {0, 1, 0, -1};
    private static final int NEIGHBOR_C[] = {1, 0, -1, 0};

    // Constant Max Values
    private static final int MAX_ROWS = 50;
    private static final int MAX_COLUMNS = 50;

    // Maze 2D Array
    private static Cell cells[][];

    private LinkedList<int[]> mazeGeneration;

    /*
     * Constructor for the Maze Class
     *
     * @param   defaultRows     The default number of rows for the maze
     * @param   defaultColumns  The default number of columns for the maze
     */
    public MazeModel() {

        mazeGeneration = new LinkedList<>();

        cells = new Cell[MAX_ROWS][MAX_COLUMNS];

        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cells[r][c] = new Cell();
                cells[r][c].setR(r);
                cells[r][c].setC(c);
            }
        }
    }

    /*
     * Gets the cells from the maze.
     *
     * @return  The 2D list of cells
     */
    public Cell[][] getCells() {
        return cells;
    }

    /*
     * Randomizes neighbor list.
     *
     * @param   r       The list of r-coordinates
     * @param   c       The list of c-coordinates
     */
    public void randomizeNeighbors(int[] r, int[] c) {

        // Copy over the constant neighbor list
        for (int i = 0; i < r.length; i++) {
            r[i] = NEIGHBOR_R[i];
            c[i] = NEIGHBOR_C[i];
        }

        for (int i = 0; i < r.length; i++) {

            // Obtain random index
            int randomIndex = (int) (Math.random() * r.length);

            // Swap integers
            int tempR = r[i];
            int tempC = c[i];
            r[i] = r[randomIndex];
            c[i] = c[randomIndex];
            r[randomIndex] = tempR;
            c[randomIndex] = tempC;
        }
    }

    /*
     * Adds to the maze generation list.
     *
     * @param   wallToBreak     The list of the coordinates for the cells to break between
     */
    public void addToMazeGeneration(int[] wallToBreak) {
        mazeGeneration.add(wallToBreak);
    }

    /*
     * Pops a wall from the generation wall.
     *
     * @return      An integer array to represent the wall
     */
    public int[] popMazeGenerationWall() {
        if (mazeGeneration.size() > 0) {
            return mazeGeneration.removeFirst();
        }
        else {
            return null;
        }
    }

    /*
     * Resets the cells and maze generation list.
     */
    public void reset() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cells[r][c].reset();
            }
        }
        mazeGeneration.clear();
    }

    /*
     * Unvisits all the cells.
     */
    public void unvisitCells() {
        for (int r = 0; r < MAX_ROWS; r++) {
            for (int c = 0; c < MAX_COLUMNS; c++) {
                cells[r][c].unvisit();
            }
        }
    }

    /*
     * Determines if two cells has a wall in between.
     *
     * @return      Whether or not the two cells have walls between
     */
    public boolean hasWall(Cell c1, Cell c2) {
        if (c1.getR() == c2.getR()) {
            // Cells are vertically aligned

            if (c1.getC() < c2.getC()) {
                // Cell1 : Cell2
                return c1.hasRightWall();
            }
            else if (c2.getC() < c1.getC()) {
                // Cell2 : Cell1
                return c2.hasRightWall();
            }
        }
        else if (c1.getC() == c2.getC()) {
            // Cells are horizontally aligned

            if (c1.getR() < c2.getR()) {
                // Cell1
                // Cell2
                return c1.hasBottomWall();
            }
            else if (c2.getR() < c1.getR()) {
                // Cell2
                // Cell1
                return c2.hasBottomWall();
            }
        }
        return true;
    }
}
