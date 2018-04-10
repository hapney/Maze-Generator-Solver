/*
 * Author: Sydney Norman
 * Project: Maze Generator and Solver
 * Date: October 25, 2017
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/*
 * Panel View for the Main View
 */
public class PanelView extends JPanel {

    // Border Outline Constant
    private static final Border BLACK_OUTLINE = BorderFactory.createLineBorder(Color.BLACK);

    // Label Constants
    private static final int FONT_SIZE = 30;

    // Size Constants
    private static final int PANEL_HEIGHT = 50;

    private static JLabel timeLabel;
    private static JLabel visitedLabel;

    /*
     * Constructor for the PanelView Class
     */
    public PanelView() {

        // Initialize Labels
        timeLabel = new JLabel("0");
        visitedLabel = new JLabel("0%");

        // Customize Labels
        Font labelFont = timeLabel.getFont();

        timeLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, FONT_SIZE));
        timeLabel.setPreferredSize(new Dimension(450, FONT_SIZE + 10));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        visitedLabel.setFont(new Font(labelFont.getName(), Font.PLAIN, FONT_SIZE));
        visitedLabel.setPreferredSize(new Dimension(450, FONT_SIZE + 10));
        visitedLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add Labels to Panel
        add(timeLabel);
        add(visitedLabel);

        // Customize Panel View
        setPreferredSize(new Dimension(950, PANEL_HEIGHT));
        setBorder(BLACK_OUTLINE);
    }

    /*
     * Sets the time label.
     *
     * @param   newTime     The new time label string
     */
    public void setTimeLabel(String newTime) {
        timeLabel.setText(newTime);
    }

    /*
     * Sets the visited label.
     *
     * @param   newVisited  The new visited label string
     */
    public void setVisitedLabel(String newVisited) {
        visitedLabel.setText(newVisited);
    }

    /*
     * Resets the time and visited labels.
     */
    public void reset() {
        timeLabel.setText("0");
        visitedLabel.setText("0%");
    }

}
