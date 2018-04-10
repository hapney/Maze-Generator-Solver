/*
 * Author: Sydney Norman
 * Class: CS 335-001
 * Project: Program 2
 * Date: October 25, 2017
 */

/*
 * Cell Model for the Maze
 */
public class Cell {

    private boolean hasRightWall, hasLeftWall, hasTopWall, hasBottomWall;

    private boolean isVisited;

    private int r, c;

    public Cell() {
        hasRightWall = true;
        hasLeftWall = true;
        hasTopWall = true;
        hasBottomWall = true;

        isVisited = false;
    }


    // GETTERS

    public boolean hasRightWall() {
        return this.hasRightWall;
    }

    public boolean hasLeftWall() {
        return this.hasLeftWall;
    }

    public boolean hasTopWall() {
        return this.hasTopWall;
    }

    public boolean hasBottomWall() {
        return this.hasBottomWall;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    // SETTERS

    public void setRightWall(boolean hasRightWall) {
        this.hasRightWall = hasRightWall;
    }

    public void setLeftWall(boolean hasLeftWall) {
        this.hasLeftWall = hasLeftWall;
    }

    public void setTopWall(boolean hasTopWall) {
        this.hasTopWall = hasTopWall;
    }

    public void setBottomWall(boolean hasBottomWall) {
        this.hasBottomWall = hasBottomWall;
    }

    public void visit() {
        isVisited = true;
    }

    public void unvisit() {
        isVisited = false;
    }

    public void setR(int r) {
        this.r = r;
    }

    public void setC(int c) {
        this.c = c;
    }

    public void reset() {
        hasRightWall = true;
        hasTopWall = true;
        hasBottomWall = true;
        hasLeftWall = true;

        isVisited = false;
    }
}
