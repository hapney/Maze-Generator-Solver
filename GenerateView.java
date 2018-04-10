/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import java.awt.*;

/*
 * Generate View for the Panel View
 */
public class GenerateView extends JPanel {

    // Constant Default Values
    private static final int DEFAULT_SPEED = 6;

    // Constant Minimum and Maximum Row/Column Values
    private static final int MIN_ROW = 10;
    private static final int MAX_ROW = 50;
    private static final int MIN_COLUMN = 10;
    private static final int MAX_COLUMN = 50;

    // Generation Buttons
    private static JButton generateButton, resetButton;

    // Generation Labels
    private static JLabel speedLabel, rowLabel, columnLabel;

    // Generation Sliders
    private static JSlider speedSlider, rowSlider, columnSlider;

    // Generation CheckBox
    private static JCheckBox showGenerationBox;

    /*
     * Constructor for the GenerateView class.
     *
     * @param   defaultRows     The default number of rows for the maze
     * @param   defaultColumns  The default number of columns for the maze
     * @param   C               The controller for the maze
     */
    public GenerateView(int defaultRows, int defaultColumns, Controller C) {
        super();

        // Initialize Buttons
        generateButton = new JButton("Generate");
        resetButton = new JButton("Reset");

        // Initialize Labels
        speedLabel = new JLabel("Speed:");
        rowLabel = new JLabel("Row: " + Integer.toString(defaultRows));
        columnLabel = new JLabel("Column: " + Integer.toString(defaultColumns));

        // Initialize Sliders
        speedSlider = new JSlider(JSlider.HORIZONTAL, 2, 10, DEFAULT_SPEED);
        rowSlider = new JSlider(JSlider.HORIZONTAL, MIN_ROW, MAX_ROW, defaultRows);
        columnSlider = new JSlider(JSlider.HORIZONTAL, MIN_COLUMN, MAX_COLUMN, defaultColumns);

        // Initialize CheckBox
        showGenerationBox = new JCheckBox("Show Generation");
        showGenerationBox.setHorizontalAlignment(SwingConstants.CENTER);

        // Set Up Labels
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rowLabel.setHorizontalAlignment(SwingConstants.CENTER);
        columnLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set Up Sliders
        speedSlider.addChangeListener(C);
        rowSlider.addChangeListener(C);
        columnSlider.addChangeListener(C);

        // Set Up Buttons
        generateButton.addActionListener(C);
        resetButton.addActionListener(C);

        // Set Up CheckBox
        showGenerationBox.setSelected(true);
        showGenerationBox.addItemListener(C);

        // Set Layout
        setLayout(new GridLayout(9, 1, 0, 5));

        // Add Items to Panel
        add(speedLabel);
        add(speedSlider);
        add(rowLabel);
        add(rowSlider);
        add(columnLabel);
        add(columnSlider);
        add(showGenerationBox);
        add(generateButton);
        add(resetButton);

    }

    /*
     * Disables the sliders and check box.
     */
    public void disableSliders() {
        rowSlider.setEnabled(false);
        columnSlider.setEnabled(false);
        showGenerationBox.setEnabled(false);
    }

    /*
     * Enables the sliders and check box
     */
    public void enableSliders() {
        rowSlider.setEnabled(true);
        columnSlider.setEnabled(true);
        showGenerationBox.setEnabled(true);
    }


    // GETTERS

    /*
     * Returns the speed slider.
     *
     * @return      the speed slider
     */
    public JSlider getSpeedSlider() {
        return speedSlider;
    }

    /*
     * Returns the row slider.
     *
     * @return      the row slider
     */
    public JSlider getRowSlider() {
        return rowSlider;
    }

    /*
     * Returns the column slider.
     *
     * @return      the column slider
     */
    public JSlider getColumnSlider() {
        return columnSlider;
    }

    /*
     * Returns the generate button.
     *
     * @return      the generate button
     */
    public JButton getGenerateButton() {
        return generateButton;
    }


    /*
     * Returns the reset button
     *
     * @return      the reset button
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /*
     * Returns the show maze generation checkbox.
     *
     * @return      the maze generation checkbox
     */
    public JCheckBox getShowGenerationBox() {
        return showGenerationBox;
    }

    /*
     * Returns the default speed
     *
     * @return      the default speed
     */
    public int getDefaultSpeed() {
        return DEFAULT_SPEED;
    }



    // SETTERS

    /*
     * Sets the row label.
     *
     * @param   s       The new row label string
     */
    public void setRowLabel(String s) {
        rowLabel.setText(s);
    }

    /*
     * Sets the column label.
     *
     * @param   s       The new column label string
     */
    public void setColumnLabel(String s) {
        columnLabel.setText(s);
    }

    /*
     * Sets the generation button text.
     *
     * @param   s       The new generate button string
     */
    public void setGenerateButtonText(String s) {
        generateButton.setText(s);
    }
}
