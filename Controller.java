/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

/*
 * Controller for the Maze
 */
public class Controller
        implements ChangeListener, ActionListener, ItemListener {

    // Constant Starting Coordinates
    private static final int START_R = 0;
    private static final int START_C = 0;

    // Simulation State Constants
    private static final int SIMULATION_RESET = 0;
    private static final int SIMULATION_PAUSED = 1;
    private static final int SIMULATION_IN_PROGRESS = 2;
    private static final int SIMULATION_COMPLETED = 3;

    // Neighbor Constants
    private static final int NEIGHBOR_R[] = {1, 0, -1, 0};
    private static final int NEIGHBOR_C[] = {0, 1, 0, -1};

    // Speed Constant
    private static final int SPEED_CONSTANT = 105;

    // Models
    private static MazeModel mazeModel;
    private static Cell cells[][];

    // Views
    private static MazeView mazeView;
    private static CellView cellViews[][];
    private static ConsoleView consoleView;
    private static View view;
    private static PanelView panelView;

    // Buttons
    private static GenerateView generateView;
    private static SolveView solveView;

    // Simulation Current Status
    private static int generationSimulationStatus = SIMULATION_RESET;
    private static int solutionSimulationStatus = SIMULATION_RESET;
    private static int generationSpeed;
    private static int solutionSpeed;
    private static int percentNumerator = 0;
    private static boolean showGeneration = true;
    private static boolean showSolution = true;

    // Timer
    private Timer timer;
    private int gameTime;

    /*
     * Constructor for the Controller class.
     */
    public Controller() {

        // Initialize the Game Time
        gameTime = 0;

        // Set Up the Timer
        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Increase the game time.
                gameTime++;

                // Update the Time Label
                panelView.setTimeLabel(Integer.toString((gameTime / 900)));

                // Update the Percentage Label
                int percent = ((percentNumerator * 100) / (generateView.getRowSlider().getValue() * generateView.getColumnSlider().getValue()));
                panelView.setVisitedLabel(Integer.toString(percent) + "%");

                // Control for Generate View
                if (consoleView.isGenerateView()) {

                    if (gameTime % (SPEED_CONSTANT - generationSpeed * 10) == 0) {
                        int wall[] = mazeModel.popMazeGenerationWall();
                        if (wall != null) {
                            mazeView.breakWall(wall[0], wall[1], wall[2], wall[3]);
                            percentNumerator++;
                        } else {
                            generationSimulationCompleted();
                        }
                    }
                }

                // Control for Solution View
                else if (consoleView.isSolveView()) {
                    if (gameTime % (SPEED_CONSTANT - solutionSpeed * 10) == 0) {
                        int mazeReturn = mazeView.popMazeSolution();
                        if (mazeReturn == -1) {
                            solutionSimulationCompleted();
                        }
                        else if (mazeReturn == 0) {
                            percentNumerator++;
                        }
                    }
                }
            }
        });
    }

    /*
     * Generate the maze instantly.
     */
    private void generateMazeInstantly() {

        // Generate the maze
        int wall[] = mazeModel.popMazeGenerationWall();
        while (wall != null) {
            mazeView.breakWall(wall[0], wall[1], wall[2], wall[3]);
            wall = mazeModel.popMazeGenerationWall();
        }

        // Complete the generation simulation
        generationSimulationCompleted();
    }

    /*
     * Solve the maze instantly.
     */
    private void solveMazeInstantly() {

        // Solve the maze
        int solutionReturn = mazeView.popMazeSolution();
        while (solutionReturn != -1) {
            // solutionReturn is 0 when a new cell is visited
            if (solutionReturn == 0) {
                percentNumerator++;
            }
            solutionReturn = mazeView.popMazeSolution();
        }

        // Update the percentage from panelView
        int percent = ((percentNumerator * 100) / (generateView.getRowSlider().getValue() * generateView.getColumnSlider().getValue()));
        panelView.setVisitedLabel(Integer.toString(percent) + "%");

        // Complete the solution simulation
        solutionSimulationCompleted();
    }

    /*
     * Set the maze for the controller to access.
     *
     * @param   m       The maze class
     */
    public void setMaze(MazeModel m) {

        // Update the data members
        mazeModel = m;
        cells = m.getCells();
    }

    /*
     * Sets the view for the controller to access.
     *
     * @param   v       The view class
     */
    public void setView(View v) {

        // Update the data members
        view = v;

        // Update the mazeView views
        mazeView = v.getMazeView();
        cellViews = mazeView.getCellViews();

        // Update the consoleView views
        consoleView = v.getConsoleView();
        generateView = view.getConsoleView().getGenerateView();
        solveView = view.getConsoleView().getSolveView();

        // Update the panelView view
        panelView = v.getPanelView();

        // Update the Speeds
        generationSpeed = generateView.getDefaultSpeed();
        solutionSpeed = solveView.getDefaultSpeed();

        // Generate the Maze
        generateMaze();
    }

    /*
    * Responds to the state change from the sliders.
    *
    * @param   event       The change event occur which called the function
    */
    public void stateChanged(ChangeEvent event) {
        JSlider curSlider = (JSlider)event.getSource();

        int value = curSlider.getValue();

        if (curSlider == generateView.getRowSlider()) {
            generateView.setRowLabel("Row: " + Integer.toString(value));
            mazeView.changeSize(cells, value, generateView.getColumnSlider().getValue());
            resetMazeGeneration();
        }
        else if (curSlider == generateView.getColumnSlider()) {
            generateView.setColumnLabel("Column: " + Integer.toString(value));
            mazeView.changeSize(cells, generateView.getRowSlider().getValue(), value);
            resetMazeGeneration();
        }
        else if (curSlider == generateView.getSpeedSlider()) {
            generationSpeed = generateView.getSpeedSlider().getValue();
        }
        else if (curSlider == solveView.getSpeedSlider()) {
            solutionSpeed = solveView.getSpeedSlider().getValue();
        }

    }

    /*
     * Responds to the console button presses.
     *
     * @param   event   The event occur which called the function
     */
    public void actionPerformed(ActionEvent event) {
        JButton curButton = (JButton)event.getSource();

        if (curButton == generateView.getGenerateButton()) {
            if (generationSimulationStatus == SIMULATION_RESET) {
                startGenerationSimulation();
            }
            else if (generationSimulationStatus == SIMULATION_PAUSED) {
                startGenerationSimulation();
            }
            else if (generationSimulationStatus == SIMULATION_IN_PROGRESS) {
                pauseGenerationSimulation();
            }
            else if (generationSimulationStatus == SIMULATION_COMPLETED) {
                resetGenerationSimulation();
            }
        }
        else if (curButton == generateView.getResetButton()) {
            resetGenerationSimulation();
        }
        else if (curButton == solveView.getSolveButton() && generationSimulationStatus == SIMULATION_COMPLETED) {

            if (solutionSimulationStatus == SIMULATION_RESET) {
                percentNumerator = 0;
                gameTime = 0;
                startSolutionSimulation();
            }
            else if (solutionSimulationStatus == SIMULATION_PAUSED) {
                startSolutionSimulation();
            }
            else if (solutionSimulationStatus == SIMULATION_IN_PROGRESS) {
                pauseSolutionSimulation();
            }
            else if (solutionSimulationStatus == SIMULATION_COMPLETED) {
                resetSolutionSimulation();
            }
        }
        else if (curButton == solveView.getResetButton() && generationSimulationStatus == SIMULATION_COMPLETED) {
            resetSolutionSimulation();
        }
    }

    /*
     * Responds to state changes for both the generation and solution
     * simulation boxes.
     *
     * @param   event       The event which triggered the function call
     */
    public void itemStateChanged(ItemEvent event) {
        JCheckBox curCheckBox = (JCheckBox)event.getSource();

        // Show Generation Simulation Box
        if (curCheckBox == generateView.getShowGenerationBox()) {
            if (curCheckBox.isSelected()) {
                showGeneration = true;
            }
            else {
                showGeneration = false;
            }
        }
        // Show Solution Simulation Box
        else if (curCheckBox == solveView.getShowSolveBox()) {
            if (curCheckBox.isSelected()) {
                showSolution = true;
            }
            else {
                showSolution = false;
            }
        }
    }

    /*
     * Pause the generation simulation.
     */
    private void pauseGenerationSimulation() {
        generationSimulationStatus = SIMULATION_PAUSED;

        // Update the View
        generateView.setGenerateButtonText("Start");

        // Start the Timer
        timer.stop();
    }

    /*
     * Start/Resume the generation simulation.
     */
    private void startGenerationSimulation() {
        generationSimulationStatus = SIMULATION_IN_PROGRESS;

        // Update the View
        generateView.setGenerateButtonText("Pause");
        generateView.disableSliders();

        if (showGeneration) {
            // Start the Timer if Show Solution is Selected
            timer.start();
        }
        else {
            // Solve Maze Instantly if the Show Solution is Unselected
            generateMazeInstantly();
        }
    }

    /*
     * Reset the generation simulation.
     */
    private void resetGenerationSimulation() {
        generationSimulationStatus = SIMULATION_RESET;

        // Reset the Timer
        timer.restart();
        timer.stop();
        gameTime = 0;

        // Update the View
        generateView.setGenerateButtonText("Generate");
        generateView.enableSliders();

        // Disable the Solution Pane
        consoleView.setSolveEnabled(false);

        // Reset Solve View
        solveView.reset();
        solutionSimulationStatus = SIMULATION_RESET;

        // Reset Maze
        resetMazeGeneration();

        // Reset Panel View
        percentNumerator = 0;
        panelView.reset();
    }

    /*
     * Wraps up the generation simulator once completed.
     */
    private void generationSimulationCompleted() {
        generationSimulationStatus = SIMULATION_COMPLETED;

        // Stop the Timer
        timer.stop();

        // Update the View
        generateView.setGenerateButtonText("Generation Completed. Reset?");
        panelView.setVisitedLabel("100%");

        // Enable the Solution Pane
        consoleView.setSolveEnabled(true);

        // Reset Solve View
        solveView.reset();
        solutionSimulationStatus = SIMULATION_RESET;

        // Solve the Maze
        solveMaze();

        // Repaint the Maze View
        mazeView.revalidate();
        mazeView.repaint();
    }

    /*
     * Wraps up the solution simulator once completed.
     */
    private void solutionSimulationCompleted() {
        solutionSimulationStatus = SIMULATION_COMPLETED;

        // Stop the Timer
        timer.stop();

        // Update the View
        solveView.setSolveButtonText("Solution Completed. Reset?");
        consoleView.setGenerateEnabled(true);

        // Repaint the Maze View
        mazeView.revalidate();
        mazeView.repaint();
    }

    /*
     * Pause the solution simulation.
     */
    private void pauseSolutionSimulation() {
        solutionSimulationStatus = SIMULATION_PAUSED;

        // Stop the Timer
        timer.stop();

        // Update the View
        solveView.setSolveButtonText("Start");
        consoleView.setGenerateEnabled(true);
    }

    /*
     * Start/Resume the solution simulation.
     */
    private void startSolutionSimulation() {
        solutionSimulationStatus = SIMULATION_IN_PROGRESS;

        if (showSolution) {
            // Start the Timer if Show Solution is Selected
            timer.start();
        }
        else {
            // Solve Maze Instantly if the Show Solution is Unselected
            solveMazeInstantly();
        }

        // Update the View
        solveView.setSolveButtonText("Pause");
        consoleView.setGenerateEnabled(false);
    }

    /*
     * Reset the solution simulation.
     */
    private void resetSolutionSimulation() {
        solutionSimulationStatus = SIMULATION_RESET;

        // Reset the Timer
        timer.restart();
        timer.stop();
        gameTime = 0;

        // Update the View
        solveView.setSolveButtonText("Solve");
        consoleView.setGenerateEnabled(true);

        // Reset Panel View
        percentNumerator = 0;
        panelView.reset();

        // Reset Maze
        resetMazeSolution();
    }

    /*
     * Reset the Maze Generation.
     */
    private void resetMazeGeneration() {
        // Reset Maze and Maze View
        mazeModel.reset();
        mazeView.reset(cells, generateView.getRowSlider().getValue(), generateView.getColumnSlider().getValue());

        // Reset Maze Generation
        generateMaze();

    }

    /*
     * Reset the Maze Solution.
     */
    private void resetMazeSolution() {
        // Reset Maze and Maze View
        mazeView.resetSolution(cells, generateView.getRowSlider().getValue(), generateView.getColumnSlider().getValue());

        solveMaze();
    }

    /*
     * Generate the maze.
     */
    private void generateMaze() {

        int rows = generateView.getRowSlider().getValue();
        int columns = generateView.getColumnSlider().getValue();

        LinkedList<Cell> adj = new LinkedList<>();

        Cell currentCell = cells[START_R][START_C];
        adj.add(currentCell);
        currentCell.visit();

        while(currentCell != null) {

            // Randomize Neighbor Order
            int randomNeighborR[] = new int[4];
            int randomNeighborC[] = new int[4];
            mazeModel.randomizeNeighbors(randomNeighborR, randomNeighborC);

            // Find Next Unvisited Neighbor
            int neighborIndex = 0;
            while ((neighborIndex < randomNeighborR.length)
                    && (currentCell.getR() + randomNeighborR[neighborIndex] < 0
                    || currentCell.getR() + randomNeighborR[neighborIndex] >= rows
                    || currentCell.getC() + randomNeighborC[neighborIndex] < 0
                    || currentCell.getC() + randomNeighborC[neighborIndex] >= columns
                    || cells[currentCell.getR() + randomNeighborR[neighborIndex]][currentCell.getC() + randomNeighborC[neighborIndex]].isVisited())) {
                neighborIndex++;
            }

            // Check if All Neighbors are Visited
            if (neighborIndex >= randomNeighborR.length) {
                if (adj.size() > 0) {
                    currentCell = adj.getFirst();
                    adj.removeFirst();
                } else {
                    currentCell = null;
                }
            } else {
                int tempWall[] = {currentCell.getR(), currentCell.getC(), currentCell.getR() + randomNeighborR[neighborIndex], currentCell.getC() + randomNeighborC[neighborIndex]};
                mazeModel.addToMazeGeneration(tempWall);
                currentCell = cells[currentCell.getR() + randomNeighborR[neighborIndex]][currentCell.getC() + randomNeighborC[neighborIndex]];
                adj.add(currentCell);
                currentCell.visit();
            }

        }
    }

    /*
     * Solve the maze.
     */
    private void solveMaze() {

        int rows = generateView.getRowSlider().getValue();
        int columns = generateView.getColumnSlider().getValue();

        mazeModel.unvisitCells();

        LinkedList<Cell> adj = new LinkedList<>();
        Cell currentCell = cells[START_R][START_C];

        while (currentCell.getR() != (rows - 1) || currentCell.getC() != (columns - 1)) {

            int i = 0;

            while ((i < NEIGHBOR_R.length)
                    && ((currentCell.getR() + NEIGHBOR_R[i]) < 0
                    || (currentCell.getR() + NEIGHBOR_R[i]) >= rows
                    || (currentCell.getC() + NEIGHBOR_C[i]) < 0
                    || (currentCell.getC() + NEIGHBOR_C[i]) >= columns
                    || mazeModel.hasWall(currentCell, cells[(currentCell.getR() + NEIGHBOR_R[i])][(currentCell.getC() + NEIGHBOR_C[i])])
                    || cells[(currentCell.getR() + NEIGHBOR_R[i])][(currentCell.getC() + NEIGHBOR_C[i])].isVisited())) {
                i++;
            }

            // Check if all neighbors are exhausted
            if (i >= NEIGHBOR_R.length) {
                currentCell.visit();
                mazeView.addToMazeSolution(currentCell.getR(), currentCell.getC(), Color.LIGHT_GRAY);
                currentCell = adj.removeLast();
            }
            else {
                adj.add(currentCell);
                currentCell.visit();
                mazeView.addToMazeSolution(currentCell.getR(), currentCell.getC(), Color.CYAN);
                currentCell = cells[(currentCell.getR() + NEIGHBOR_R[i])][(currentCell.getC() + NEIGHBOR_C[i])];
            }
        }
    }
}
