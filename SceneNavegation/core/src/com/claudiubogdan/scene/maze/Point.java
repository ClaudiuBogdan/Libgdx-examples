package com.claudiubogdan.scene.maze;


import java.util.Objects;

/**
 * Class that stores position/coordinates into the maze and keep track of the specific position.
 */
public class Point{

    private final int PATH_BLOCK_DIMENSION;
    private int horizontalPosition;
    private int verticalPosition;

    /**
     * Constructor. Create a point with specific coordinates.
     * @param mHorizontalPosition Horizontal position or X coord of the point
     * @param mVerticalPosition Vertical position or Y coord of the point
     */
    public Point(int mHorizontalPosition, int mVerticalPosition, int nextMoveDimension){
        this.horizontalPosition = mHorizontalPosition;
        this.verticalPosition = mVerticalPosition;
        PATH_BLOCK_DIMENSION = nextMoveDimension;


    }

    /**
     * Mutator. Move the current position one unit to Left
     */
    public void moveToLeft() {
        this.horizontalPosition = this.horizontalPosition - PATH_BLOCK_DIMENSION;
    }

    /**
     * Mutator. Move the current position one unit to Right
     */
    public void moveToRight() {
        this.horizontalPosition = this.horizontalPosition + PATH_BLOCK_DIMENSION;
    }

    /**
     * Mutator. Move the current position one unit Upward
     */
    public void moveUp() {
        this.verticalPosition = this.verticalPosition - PATH_BLOCK_DIMENSION;
    }

    /**
     * Mutator. Move the current position one unit to Downward
     */
    public void moveDown() {
        this.verticalPosition = this.verticalPosition + PATH_BLOCK_DIMENSION;
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public int getVerticalPosition() {
        return verticalPosition;
    }

    public int getPATH_BLOCK_DIMENSION() {
        return PATH_BLOCK_DIMENSION;
    }

    /**
     * Set new point position to specific coordinates.
     * @param aHorizontalPosition New horizontal position/ posX.
     * @param aVerticalPosition New vertical position/ posY.
     */
    public void setNewPosition(int aHorizontalPosition, int aVerticalPosition){
        this.horizontalPosition = aHorizontalPosition;
        this.verticalPosition = aVerticalPosition;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }
        if(!(obj instanceof Point)) {
            return false;
        }
        Point pointToCompare = (Point) obj;
        return  (this.horizontalPosition == pointToCompare.horizontalPosition &&
                this.verticalPosition == pointToCompare.verticalPosition &&
                this.PATH_BLOCK_DIMENSION == pointToCompare.PATH_BLOCK_DIMENSION) ;

    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + horizontalPosition;
        result = 31 * result + verticalPosition;
        result = 31 * result + PATH_BLOCK_DIMENSION;
        return result;
    }

    public String toString(){
        return new String("Coordinate: (" + horizontalPosition + "," + verticalPosition + ")");
    }

    /**
     * Give a copy of the point.
     * @return copy of the point (new object).
     */
    public Point copyOf(){
        return new Point(horizontalPosition, verticalPosition, getPATH_BLOCK_DIMENSION());
    }

}