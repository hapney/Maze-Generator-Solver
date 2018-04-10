/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import java.awt.*;

/*
 * Cell View for the Maze
 */
public class CellView extends JPanel {

    // The Default Cell Color
    private static final Color DEFAULT_COLOR = Color.WHITE;

    // The corresponding cell details
    private Cell cell;
    private int cellWidth;
    private int cellHeight;

    /*
     * Constructor for the CellView Class.
     *
     * @param   cell        The corresponding cell
     * @param   cellWidth   The width of the cell
     * @param   cellHeight  The height of the cell
     */
    public CellView(Cell cell, int cellWidth, int cellHeight) {
        super();

        this.cell = cell;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        clearCell();
    }

    /*
     * Clear the cell entirely.
     */
    public void clearCell() {

        // Remove all walls
        this.removeAll();

        // Remove any extra colors
        clearColor();
    }

    /*
     * Clear the cell color back to default.
     */
    public void clearColor() {
        setBackground(DEFAULT_COLOR);
        setOpaque(true);
    }

    /*
     * Draw the cell walls for a given cell.
     *
     * @param   cell        The corresponding cell to draw
     * @param   cellWidth   The width of the cell
     * @param   cellHeight  The height of the cell
     */
    public void drawCellWalls(Cell cell, int cellWidth, int cellHeight) {

        // Update the data members
        this.cell = cell;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        // Repaint the cell
        revalidate();
        repaint();
    }

    /*
     * Set the left wall of the cell.
     *
     * @param   g       Graphics to use
     */
    private void setLeftWall(Graphics g) {
        g.drawLine(0, 0, 0, cellHeight);
    }

    /*
     * Set the top wall of the cell.
     *
     * @param   g       Graphics to use
     */
    private void setTopWall(Graphics g) {
        g.drawLine(0, 0, cellWidth, 0);
    }

    /*
     * Set the right wall of the cell.
     *
     * @param   g       Graphics to use
     */
    private void setRightWall(Graphics g) {
        g.drawLine(cellWidth, 0, cellWidth, cellHeight);
    }

    /*
     * Set the bottom wall of the cell.
     *
     * @param   g       Graphics to use
     */
    private void setBottomWall(Graphics g) {
        g.drawLine(0, cellHeight, cellWidth, cellHeight);
    }

    /*
     * Remove the left wall of the cell
     */
    public void removeLeftWall() {
        cell.setLeftWall(false);
        repaint();
    }

    /*
     * Remove the top wall of the cell
     */
    public void removeTopWall() {
        cell.setTopWall(false);
        repaint();
    }

    /*
     * Remove the right wall of the cell
     */
    public void removeRightWall() {
        cell.setRightWall(false);
        repaint();
    }

    /*
     * Remove the bottom wall of the cell
     */
    public void removeBottomWall() {
        cell.setBottomWall(false);
        repaint();
    }

    /*
     * Default paint function to paint the cell.
     *
     * @param   g       Graphics to use
     */
    public void paintComponent(Graphics g) {

        // Set up the paint component
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Graphics2D picture = (Graphics2D)g;
        picture.setStroke(new BasicStroke(cellWidth / 5));

        // Color each wall of the cell
        if (cell.hasLeftWall()) {
            setLeftWall(g);
        }
        if (cell.hasTopWall()) {
            setTopWall(g);
        }
        if (cell.hasRightWall()) {
            setRightWall(g);
        }
        if (cell.hasBottomWall()) {
            setBottomWall(g);
        }
    }

    /*
     * Sets the cell color to the color provided.
     *
     * @param   c       The color to change to
     */
    public void setColor(Color c) {
        setBackground(c);
    }
}
