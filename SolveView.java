/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import java.awt.*;

/*
 * Solve View for the Panel View
 */
public class SolveView extends JPanel {

    // Constant Default Values
    private static final int DEFAULT_SPEED = 6;

    // Solve Buttons
    private static JButton solveButton, resetButton;

    // Solve Label
    private static JLabel speedLabel;

    // Solve Slider
    private static JSlider speedSlider;

    // Solve CheckBox
    private static JCheckBox showSolveBox;

    /*
     * Constructor for the SolveView.
     *
     * @param   C       Controller used
     */
    public SolveView(Controller C) {
        super();

        // Initialize Buttons
        solveButton = new JButton("Solve");
        resetButton = new JButton("Reset");

        // Initialize Labels
        speedLabel = new JLabel("Speed:");

        // Initialize Sliders
        speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 11, DEFAULT_SPEED);

        // Initialize CheckBox
        showSolveBox = new JCheckBox("Show Solution");
        showSolveBox.setHorizontalAlignment(SwingConstants.CENTER);

        // Set Up Label
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set Up Button
        solveButton.addActionListener(C);
        resetButton.addActionListener(C);

        // Set Up Slider
        speedSlider.addChangeListener(C);

        // Set Up CheckBox
        showSolveBox.setSelected(true);
        showSolveBox.addItemListener(C);

        // Set Layout
        setLayout(new GridLayout(9, 1, 0, 5));

        // Add Items to Panel
        add(speedLabel);
        add(speedSlider);

        for (int i = 0; i < 4; i++) {
            add(new JLabel());
        }

        add(showSolveBox);
        add(solveButton);
        add(resetButton);
    }

    /*
     * Resets the solveButton.
     */
    public void reset() {
        solveButton.setText("Solve");
    }

    // GETTERS

    /*
     * Gets the Show Solve Box
     *
     * @return      The Check Box
     */
    public JCheckBox getShowSolveBox() {
        return showSolveBox;
    }

    /*
     * Gets the solve button.
     *
     * @return  The solve button
     */
    public JButton getSolveButton() {
        return solveButton;
    }

    /*
     * Gets the reset button.
     *
     * @return  The reset button
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /*
     * Gets the speed slider.
     *
     * @return  The speed slider
     */
    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    /*
     * Gets the default speed.
     *
     * @return  The default speed
     */
    public int getDefaultSpeed() {
        return DEFAULT_SPEED;
    }



    // SETTERS
    /*
     * Sets the solve button text.
     *
     * @param   s       The new text
     */
    public void setSolveButtonText(String s) {
        solveButton.setText(s);
    }
}
