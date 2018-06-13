package com.claudiubogdan.scene.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Inner class that generate random path given the start position and direction.
 */
class MazePath{
    private final int PATH_ID;
    private Point mPosition;
    private int[][] mazeReference;
    private List<Directions> directions = new ArrayList<Directions>();
    private List<Point> path = new ArrayList<Point>();

    /**
     * Default constructor.
     */
    public MazePath(){
        this.PATH_ID = 0;
    }

    /**
     * Constructor. Instantiate a path with specific start position and direction to move.
     * @param ID Specifies the value for each position of the path that will be written into the maze.
     * @param mStartingPosition The starting point of the path.
     * @param aMazeReference Reference to the maze that contains all the paths.
     */
    public MazePath(int ID, Point mStartingPosition, int[][] aMazeReference){
        this.PATH_ID = ID;
        if(mStartingPosition != null){
            this.mPosition = mStartingPosition.copyOf();
            this.path.add(mStartingPosition.copyOf());
        }

        //Add specific first direction. Created for Entry and Exit first move.
        this.mazeReference = aMazeReference;

    }

    /**
     * Check if the positions around of the current position are empty and get available directions to empty positions.
     */
    public void getAvailableDirections(){
        directions.clear();
        //Test all points (UP, DOWN, LEFT, RIGHT) of the current position to check if position to move is available
        Point upwardPosition = mPosition.copyOf();
        upwardPosition.moveUp();
        upwardPosition.moveUp();
        if(mazeReference[upwardPosition.getHorizontalPosition()][upwardPosition.getVerticalPosition()] == GenerateMaze.EMPTY_ID ){
            directions.add(Directions.UP);
        }

        Point downwardPosition = mPosition.copyOf();
        downwardPosition.moveDown();
        downwardPosition.moveDown();
        if(mazeReference[downwardPosition.getHorizontalPosition()][downwardPosition.getVerticalPosition()] == GenerateMaze.EMPTY_ID ){
            directions.add(Directions.DOWN);
        }

        Point leftPosition = mPosition.copyOf();
        leftPosition.moveToLeft();
        leftPosition.moveToLeft();
        if(mazeReference[leftPosition.getHorizontalPosition()][leftPosition.getVerticalPosition()] == GenerateMaze.EMPTY_ID ){
            directions.add(Directions.LEFT);
        }

        Point rightPosition = mPosition.copyOf();
        rightPosition.moveToRight();
        rightPosition.moveToRight();
        if(mazeReference[rightPosition.getHorizontalPosition()][rightPosition.getVerticalPosition()] == GenerateMaze.EMPTY_ID ){
            directions.add(Directions.RIGHT);
        }
        //TODO : Change shuffle with a method that get random index of list
        Collections.shuffle(directions);
    }

    /**
     * Method that calculate the free joints from where to grow a new path.
     * The path must be ended/blocked. This class modifies the directions of the path and the current position.
     * @return True if there is available joints to grow a path.
     */
    public boolean hasFreeJoints(){
        int stepsToNextJoint = 2;
        for(int i=0; i < getPath().size(); i += stepsToNextJoint){
            mPosition = getPath().get(i);
            getAvailableDirections();
            if(hasAvailableDirection()){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if there is any available directions/ If the path is not blocked.
     * @return True is there still available positions to move to.
     */
    public Boolean hasAvailableDirection(){
        return directions.size() > 0;
    }


    /**
     * Grow the branch one unit. There must be directions available.
     * @param newDirection specific direction to move toward. If null, the direction will be random from the possible directions.
     */
    public void moveToNextPosition(Directions newDirection){
        if(newDirection == null){
            int indexFirstDirection = 0;
            if(hasAvailableDirection()){
                newDirection = directions.get(indexFirstDirection);
            }
        }

        Point intermediaryPoint = null ; //Default values to the point between old and new position.
        switch (newDirection){
            case UP:
                mPosition.moveUp();
                intermediaryPoint = mPosition.copyOf();
                mPosition.moveUp();
                break;
            case DOWN:
                mPosition.moveDown();
                intermediaryPoint = mPosition.copyOf();
                mPosition.moveDown();
                break;
            case LEFT:
                mPosition.moveToLeft();
                intermediaryPoint = mPosition.copyOf();
                mPosition.moveToLeft();
                break;
            case RIGHT:
                mPosition.moveToRight();
                intermediaryPoint = mPosition.copyOf();
                mPosition.moveToRight();
                break;
            default: break;
        }

        //Register the new positions into the path.
        this.path.add(intermediaryPoint);
        this.path.add(mPosition.copyOf());

        //Write path value to the correspondent position into maze.
        mazeReference[intermediaryPoint.getHorizontalPosition()][intermediaryPoint.getVerticalPosition()] = PATH_ID;
        mazeReference[mPosition.getHorizontalPosition()][mPosition.getVerticalPosition()] = PATH_ID;

        //Look for the possible directions of the new position
        getAvailableDirections();
    }

    /**
     * This method grow or generate the path until the head is blocked.
     */
    public void growTheBranch(){
        this.getAvailableDirections();
        while (hasAvailableDirection()){
            //System.out.println(this.getmPosition().toString());
            this.moveToNextPosition(null);
            this.getAvailableDirections();
        }

    }

    /**
     * Reverse the point of the path (The beginning of the path become the end).
     */
    public void reversePath(){
        List<Point> reversedPath = new ArrayList<>();
        for(int i = this.getPath().size() - 1; i >= 0; i--){
            reversedPath.add(this.getPath().get(i));
        }
        this.setPath(reversedPath);
    }



    public Point getmPosition() {
        return mPosition;
    }

    public void setmPosition(Point aPoint){
        this.mPosition = aPoint;
    }

    public int[][] getMazeReference() {
        return this.mazeReference;
    }

    public List<Point> getPath() {
        return this.path;
    }

    public void setPath(List<Point> path) {
        this.path = path;
    }

    public List<Directions> getDirections() {
        return directions;
    }

    public int getPATH_ID() {
        return PATH_ID;
    }
}