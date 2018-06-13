package com.claudiubogdan.scene.maze;


/**
 * Class that create branches of the main path.
 */
public class MainMazePath extends MazePath {

    private Point lastPoint;
    private final int COMPLEMENTARY_ID;
    /**
     * Constructor. Create a main branch path.
     * @param ID the id of the branch
     * @param mStartingPosition starting position of the branch
     * @param mStartingDirection The preference direction to move toward the first time.
     * @param aMazeReference Reference to the maze that contains all the paths.
     */
    public MainMazePath(int ID, Point mStartingPosition, Directions mStartingDirection, int[][] aMazeReference, int aCOMPLEMENTARY_ID){
        super(ID, mStartingPosition, aMazeReference);
        this.COMPLEMENTARY_ID = aCOMPLEMENTARY_ID;
        if(mStartingDirection != null){
            moveToNextPosition(mStartingDirection); //First move toward specified direction(not random, toward interior of maze.
        }
    }

    //COMPLETED: Implement the method that cut the path to a specific position.

    /**
     * Trow all the positions from the given position (excluded) to the end of the path and clear the maze of the thrown positions.
     * @param aPositionToCut Point of the branch from where to cut the branch.
     */
    public void cutPathToPosition(Point aPositionToCut){
        int indexOfPositionToCut = 2; // The second position of the branch.
        for(int i = 2; i < getPath().size(); i++ ){
            if(aPositionToCut.equals(getPath().get(i))){
                indexOfPositionToCut = i;
            }
        }
        indexOfPositionToCut++;
//        System.out.println("Index path: " + indexOfPositionToCut);

        //Clear the maze of the values of the part of the branch that will be cut.
        for(Point position : getPath().subList(indexOfPositionToCut , getPath().size())){
            getMazeReference()[position.getHorizontalPosition()][position.getVerticalPosition()] = GenerateMaze.EMPTY_ID;
        }
        //Erase values from the path list
        getPath().subList(indexOfPositionToCut, getPath().size()).clear();

        //Set the last position of the branch to the las item of the List
        Point lastPointOfTheBranch = getPath().get(getPath().size()-1);
        setmPosition(lastPointOfTheBranch.copyOf());
        getAvailableDirections();
    }

    /**
     * Method that search the most close Point to a TargetPoint.
     * @param targetPoint The point that will be the reference to calculate the minimum distance.
     * @return The closest point tho the target point/reference.
     */
    public Point searchClosestPositionToPoint(final Point targetPoint){
        //Iterate each point of the path and keep the shortest distance (distance = (x1-x0)^2 + (y1-y0)^2
        int indexSecondPoint = 2;
        int stepsOfPathAdvance = 2;
        double minDistance = (Math.pow(getPath().get(indexSecondPoint).getHorizontalPosition() - targetPoint.getHorizontalPosition(),indexSecondPoint) +
                Math.pow(getPath().get(indexSecondPoint).getVerticalPosition() - targetPoint.getVerticalPosition(),indexSecondPoint));
        Point closestPoint = getPath().get(indexSecondPoint);
        //stepOfPathAdvance are the number of steps the branch advance each time.
        for(int i=indexSecondPoint; i<getPath().size(); i += stepsOfPathAdvance){
            Point pathPoint = getPath().get(i);
            double distance = (Math.pow(pathPoint.getHorizontalPosition() - targetPoint.getHorizontalPosition(),2) +
                    Math.pow(pathPoint.getVerticalPosition() - targetPoint.getVerticalPosition(),2));
            if(distance < minDistance){
                minDistance = distance;
                closestPoint = pathPoint;
            }
        }
        return closestPoint;
    }

    /**
     * Check if the complementary branch is one move ahead.
     * @return True if complementary branch is one move ahead.
     */
    public Boolean hasFoundComplementaryBranch(){

        //Look upward for the complementary branch
        Point upwardPosition = new Point(getmPosition().getHorizontalPosition(), getmPosition().getVerticalPosition(), GenerateMaze.PATH_BLOCK_DIMENSION);
        upwardPosition.moveUp();
        upwardPosition.moveUp();
        if(getMazeReference()[upwardPosition.getHorizontalPosition()][upwardPosition.getVerticalPosition()] == COMPLEMENTARY_ID ){
            moveToNextPosition(Directions.UP);
            lastPoint = upwardPosition;
            return Boolean.TRUE;
        }

        //Look downward for the complementary branch
        Point downwardPoint = new Point(getmPosition().getHorizontalPosition(), getmPosition().getVerticalPosition(), GenerateMaze.PATH_BLOCK_DIMENSION);
        downwardPoint.moveDown();
        downwardPoint.moveDown();
        if(getMazeReference()[downwardPoint.getHorizontalPosition()][downwardPoint.getVerticalPosition()] == COMPLEMENTARY_ID ){
            moveToNextPosition(Directions.DOWN);
            lastPoint = downwardPoint;
            return Boolean.TRUE;
        }

        //Look left for the complementary branch
        Point leftPoint = new Point(getmPosition().getHorizontalPosition(), getmPosition().getVerticalPosition(), GenerateMaze.PATH_BLOCK_DIMENSION);
        leftPoint.moveToLeft();
        leftPoint.moveToLeft();
        if(getMazeReference()[leftPoint.getHorizontalPosition()][leftPoint.getVerticalPosition()] == COMPLEMENTARY_ID ){
            moveToNextPosition(Directions.LEFT);
            lastPoint = leftPoint;
            return Boolean.TRUE;
        }

        //Look right for the complementary branch
        Point rightPoint = new Point(getmPosition().getHorizontalPosition(), getmPosition().getVerticalPosition(), GenerateMaze.PATH_BLOCK_DIMENSION);
        rightPoint.moveToRight();
        rightPoint.moveToRight();
        if(getMazeReference()[rightPoint.getHorizontalPosition()][rightPoint.getVerticalPosition()] == COMPLEMENTARY_ID ){
            moveToNextPosition(Directions.RIGHT);
            lastPoint = rightPoint;
            return Boolean.TRUE;
        }
        //If now branch found, return false
        return Boolean.FALSE;

    }

    public Point getLastPoint() {
        return lastPoint;
    }

    private void displayPath(){
        for(Point point:getPath()){
            System.out.println("ID: " + getPATH_ID() + " ;" + point.toString());
        }
    }
}
