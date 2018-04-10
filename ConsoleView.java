/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import java.awt.*;

/*
 * Console View for the Maze Game.
 */
public class ConsoleView extends JTabbedPane {

    // Constant Strings
    private static final String GENERATE = "Generate";
    private static final String SOLVE = "  Solve  ";

    // Generate View
    private GenerateView generateView;

    // Solve View
    private SolveView solveView;

    /*
     * Constructor for the ConsoleView class.
     *
     * @param   defaultRows     The default number of rows for the maze
     * @param   defaultColumns  The default number of columns for the maze
     * @param   C               The constructor for the maze
     */
    public ConsoleView(int defaultRows, int defaultColumns, Controller C) {

        super();

        // Set Up and Add Generate View Panel
        generateView = new GenerateView(defaultRows, defaultColumns, C);
        this.addTab(GENERATE, null, generateView, "First Panel");

        // Set Up and Add Solve View Panel
        solveView = new SolveView(C);
        this.addTab(SOLVE, null, solveView, "Second Panel");

        setSolveEnabled(false);

        setPreferredSize(new Dimension(300, 675));

    }

    /*
     * Returns the generateView.
     *
     * @return      The generateView
     */
    public GenerateView getGenerateView() {
        return generateView;
    }

    /*
     * Returns the solveView.
     *
     * @return      The solveView
     */
    public SolveView getSolveView() {
        return solveView;
    }

    /*
     * Checks if the tab open is the generateView.
     *
     * @return      Whether or not the tab is generateView
     */
    public boolean isGenerateView() {
        return (getSelectedComponent() == generateView);
    }

    /*
     * Checks if the tab open is the solveView.
     *
     * @return      Whether or not the tab is solveView
     */
    public boolean isSolveView() {
        return (getSelectedComponent() == solveView);
    }

    /*
     * Enables or Disables the solve tab.
     *
     * @param   enabled     Whether or not to enable the solve tab
     */
    public void setSolveEnabled(boolean enabled) {
        setEnabledAt(1, enabled);
    }

    /*
     * Enables or Disables the generate tab.
     *
     * @param   enabled     Whether or not to enable the generate tab
     */
    public void setGenerateEnabled(boolean enabled) {
        setEnabledAt(0, enabled);
    }
}
