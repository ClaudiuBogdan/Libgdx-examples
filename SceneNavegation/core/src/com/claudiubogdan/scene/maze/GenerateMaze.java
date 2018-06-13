package com.claudiubogdan.scene.maze;

import java.util.*;

//Possible direction to move to
enum Directions {UP ,DOWN ,LEFT ,RIGHT}

public class GenerateMaze {

    private final int NUMBER_OF_HORIZONTAL_PATHS;    //number of rows
    private final int NUMBER_OF_VERTICAL_PATHS;    //number of columns
    private final int HORIZONTAL_DIMENSION_OF_MAZE;
    private final int VERTICAL_DIMENSION_OF_MAZE;
    private final Point ENTRY_POSITION;
    private final Point EXIT_POSITION;
    private  MainMazePath mainMazePath;

    public static final int MARGIN_OF_MAZE_DIMENSION = 1; //Dimension measured in units of matrix rows and columns
    public static final int EMPTY_BLOCK_DIMENSION = 1;
    public static final int PATH_BLOCK_DIMENSION= 1;
    public static final int EMPTY_ID = 0; //Do not change this id. Is zero by default as empty matrix is generated with zeros
    public static final int MARGIN_ID = 1; //ID of margin. Value to write in Maze margin matrix
    public static final int ENTRY_ID = 2;
    public static final int EXIT_ID = 3;
    public static final int GENERIC_PATH_ID = 4;
    public static final int ENTRY_PATH_ID = 5;
    public static final int EXIT_PATH_ID = 6;
    public static final int MAIN_PATH_ID = 7;

    private int[][] Maze; //Maze matrix that stores different values for margins, path, empty space,...



    /**
     * Constructor. Generate maze with specific number of rows and columns, entry and exit position
     * @param aNumberOfHorizontalPaths number of rows/horizontal paths. Number must be integer grater than zero
     * @param aNumberOfVerticalPaths number of columns/vertical paths. Number must be integer grater than zero
     * @param aVerticalEntryPosition the posY/vertical position of the maze's entry. Number must be between 1 and aNumberOfVerticalPaths
     * @param aVerticalExitPosition the posX/horizontal position of the maze's entry. Number must be between 1 and aNumberOfVerticalPaths
     */
    public GenerateMaze(int aNumberOfHorizontalPaths, int aNumberOfVerticalPaths, int aVerticalEntryPosition, int aVerticalExitPosition){
        NUMBER_OF_HORIZONTAL_PATHS = aNumberOfHorizontalPaths;
        NUMBER_OF_VERTICAL_PATHS = aNumberOfVerticalPaths;
        HORIZONTAL_DIMENSION_OF_MAZE = 2 * MARGIN_OF_MAZE_DIMENSION + aNumberOfHorizontalPaths *
                (EMPTY_BLOCK_DIMENSION + PATH_BLOCK_DIMENSION) + EMPTY_BLOCK_DIMENSION;
        VERTICAL_DIMENSION_OF_MAZE = 2 * MARGIN_OF_MAZE_DIMENSION + aNumberOfVerticalPaths *
                (EMPTY_BLOCK_DIMENSION + PATH_BLOCK_DIMENSION) + EMPTY_BLOCK_DIMENSION;
        ENTRY_POSITION = calculateEntryPosition(aVerticalEntryPosition);
        EXIT_POSITION = calculateExitPosition(aVerticalExitPosition);

        //Prepare a matrix of empty positions.
        generateEmptyMaze();
        if(true) {
            //Insert Entry, Exit and Path Solution to the maze matrix.
            mainMazePath = generateMainPath();

            generateSecondaryPathsSwitchDistribution();
        }
        else{
            generatePathsFromEntry();
        }

    }

    /**
     * Generate empty maze with Margins, Entry and Exit.
     * @return generated maze;
     */
    private void generateEmptyMaze() {
        Maze = new int[HORIZONTAL_DIMENSION_OF_MAZE][VERTICAL_DIMENSION_OF_MAZE];

        //Asigne empty value to all the elements of the maze
        for(int i=0; i<HORIZONTAL_DIMENSION_OF_MAZE; i++){
            for(int j=0; j<VERTICAL_DIMENSION_OF_MAZE; j++){
                Maze[i][j] = EMPTY_ID;
            }
        }

        //Fill emptyMaze the up and bottom margin values
        for(int i = 0; i<HORIZONTAL_DIMENSION_OF_MAZE; i++){
            for(int j = 0; j<MARGIN_OF_MAZE_DIMENSION; j++){
                Maze[i][j] = MARGIN_ID;
                Maze[i][VERTICAL_DIMENSION_OF_MAZE - MARGIN_OF_MAZE_DIMENSION + j] = MARGIN_ID;
            }
        }
        //Fill emptyMaze the left and right margin values
        for(int j = 0; j<VERTICAL_DIMENSION_OF_MAZE; j++){
            for(int i= 0; i<MARGIN_OF_MAZE_DIMENSION; i++){
                Maze[i][j] = MARGIN_ID;
                Maze[HORIZONTAL_DIMENSION_OF_MAZE - MARGIN_OF_MAZE_DIMENSION + i][j]= MARGIN_ID;
            }
        }
    }

    /**
     * Insert Entry, Exit and Path Solution to the maze matrix.
     */
    private MainMazePath generateMainPath(){
        //Set Entry values into proper index of Maze
        Maze[ENTRY_POSITION.getHorizontalPosition()][ENTRY_POSITION.getVerticalPosition()] = ENTRY_ID;

        //Set Exit values into proper index of Maze
        Maze[EXIT_POSITION.getHorizontalPosition()][EXIT_POSITION.getVerticalPosition()] = EXIT_ID;

        //Branch that starts from ENTRY_POSITION and connects with mainPathExit
        MainMazePath mainPathEntry = new MainMazePath(ENTRY_PATH_ID, ENTRY_POSITION, Directions.RIGHT, getMaze(), EXIT_PATH_ID);
        //Branch that starts from EXIT_POSITION and connects with mainPathExit
        MainMazePath mainPathExit = new MainMazePath(EXIT_PATH_ID, EXIT_POSITION, Directions.LEFT, getMaze(), ENTRY_PATH_ID);

        Point targetPoint = calculateRandomPosition();
        MainMazePath mainBranch = mainPathEntry;
        MainMazePath complementaryBranch = mainPathExit;
        //Grow branches until one find the other.
        while (!mainBranch.hasFoundComplementaryBranch()){
            mainBranch = mainBranch == mainPathEntry ? mainPathExit : mainPathEntry;
            complementaryBranch = mainBranch == mainPathEntry ? mainPathExit : mainPathEntry;
            //If there is available directions to move toward.
            if (mainBranch.hasAvailableDirection()) {
                mainBranch.moveToNextPosition(null);
            }
            else{
                //The branch is blocked. Cut from the most close point to the last position of the other branch.
               mainBranch.cutPathToPosition(mainBranch.searchClosestPositionToPoint(targetPoint));
            }

        }
        //Delete the complementary surplus branch
       complementaryBranch.cutPathToPosition(mainBranch.getLastPoint());
        //Merge the to branches of the main path
        MainMazePath mainPathMaze = new MainMazePath(MAIN_PATH_ID, null, null, getMaze(),0);
        List<Point> completePath = new ArrayList<>();
        //Copy all points of the entry path with the exception of the first element which is the entry.
        for(int i = 1; i < mainPathEntry.getPath().size(); i++) {
            completePath.add(mainPathEntry.getPath().get(i));
        }

        //Copy all points of the exit path with the exception of the first element (is duplicated with entry path last element)
        // and last element which is the exit point
        mainPathExit.reversePath();
        for(int i = 1; i < mainPathExit.getPath().size() - 1 ; i++){
            completePath.add(mainPathExit.getPath().get(i));
        }
        mainPathMaze.setPath(completePath);
        return mainPathMaze;
    }

    /**
     * Generate alternative paths that start to grow from the main path positions.
     */
    private void generateSecondaryPathsUniformDistribution(){
        int eachTwoElements = 2; //Start from position separated by empty block.
        List<MazePath> mazePathArray = new ArrayList<>();
        for(int i = 1; i < mainMazePath.getPath().size(); i += eachTwoElements){
            mazePathArray.add(new MazePath(GENERIC_PATH_ID, mainMazePath.getPath().get(i), getMaze()));

        }
        boolean directionsAvailable = false;
        for(MazePath mazePath : mazePathArray){
            mazePath.getAvailableDirections(); //Calculate available directions
            directionsAvailable = directionsAvailable || mazePath.hasAvailableDirection(); //Check if there is any direction available in all paths.
        }

        while (directionsAvailable){
            directionsAvailable = false;
            for(MazePath mazePath : mazePathArray){
                mazePath.getAvailableDirections(); //Recheck if the directions have been blocked with other paths.
                if(mazePath.hasAvailableDirection()){
                    mazePath.moveToNextPosition(null);
                }
//                directionsAvailable = directionsAvailable || mazePath.hasAvailableDirection(); //Check if there is any direction available in all paths.
            }
            for(MazePath mazePath : mazePathArray){
                mazePath.getAvailableDirections();
                directionsAvailable = directionsAvailable || mazePath.hasAvailableDirection(); //Check if there is any direction available in all paths.
            }

        }
    }

    /**
     * Method that generate secondary paths until all the maze is full.
     * The secondary paths that still has available directions will be stored into a list
     * New generated path that have available directions will be stored into the list.
     * The methods modifies the maze be inserting secondary paths.
     */
    private void generateSecondaryPathsSwitchDistribution(){
        /**
         * 1. Split the main path in half and create two paths ( the second part of the path must be reversed.
         * 2.0 Store the parts into two different list.
         * 2.1 Create paths by switching between the to parts of the main path.
         * 2.2 Store the new created paths into two lists (each one for the divided parts of the main path.)
         * 2.3 When the first path of each list is blocked, delete it from the list and continue with the next path of the list.
         * 2.4 Check if the path has available joints.
         *      If the list has available paths -> GO TO 2.1 ; Else -> END
         */

        //Create a copy of the main path without the first and the last element of the path (they aren't joints).
        MainMazePath auxFirstHalfMainPath = new MainMazePath(mainMazePath.getPATH_ID(), mainMazePath.getPath().get(0), null, getMaze(), 0);
        auxFirstHalfMainPath.setPath(mainMazePath.getPath().subList(1,mainMazePath.getPath().size()/2));
        Queue<MazePath> firstHalfList = new LinkedList<>(); //List containing all the paths with free joints that grow from the first half of the main path.
        firstHalfList.add(auxFirstHalfMainPath);

        MainMazePath auxSecondHalfMainPath = new MainMazePath(mainMazePath.getPATH_ID(), mainMazePath.getPath().get(0), null, getMaze(), 0);
        auxSecondHalfMainPath.setPath(mainMazePath.getPath().subList((mainMazePath.getPath().size()-1)/2 , mainMazePath.getPath().size()-1));
        auxSecondHalfMainPath.reversePath();
        Queue<MazePath> secondHalfList = new LinkedList<>();
        secondHalfList.add(auxSecondHalfMainPath);

        //Grow new paths while there are still free joints available.
        Queue<MazePath> freeJointPathsList = secondHalfList;
        while (firstHalfList.size()>0 || secondHalfList.size()>0){
            if(firstHalfList.size()>0 && secondHalfList.size()>0){
                freeJointPathsList = freeJointPathsList == firstHalfList ? secondHalfList : firstHalfList;
            }
            else if(firstHalfList.size()>0){
                freeJointPathsList = firstHalfList;
            }
            else {
                freeJointPathsList = secondHalfList;
            }
            if(freeJointPathsList.peek().hasFreeJoints()){
                //Create and grow new path white the starting position of the first available joint.
                MazePath secPath = new MazePath(GENERIC_PATH_ID, freeJointPathsList.peek().getmPosition(), getMaze());
                secPath.growTheBranch();
                //secPath.reversePath();
                freeJointPathsList.add(secPath);
            }
            else{
                freeJointPathsList.remove();
            }


        }
    }

    /**
     * This method generate the paths of the maze by starting from the entry and creating paths to fill al the space.
     * The method create paths to fill all the empty spaces and then connect the exit with the nearest pats.
     */
    private void generatePathsFromEntry(){
        MazePath path = new MazePath(GenerateMaze.GENERIC_PATH_ID, ENTRY_POSITION.copyOf(), getMaze());
        path.moveToNextPosition(Directions.RIGHT);
        path.growTheBranch();
        //Skip the two first position of the fist path to avoid out of index error.
        path.setPath(path.getPath().subList(2,path.getPath().size()));
        Queue<MazePath> listOfPaths = new LinkedList<>();
        listOfPaths.add(path);
        while (listOfPaths.size() > 0){
            if(listOfPaths.peek().hasFreeJoints()){
                //Create a path object with the starting position from the free joint.
                MazePath newPath = new MazePath(GENERIC_PATH_ID, listOfPaths.peek().getmPosition(), getMaze());
                //Grow the path until the head is blocked.
                newPath.growTheBranch();
                //Add the path to the queue.
                listOfPaths.add(newPath);

            }
            else {
                listOfPaths.remove();
            }
        }

    }

    /**
     * Calculate the coordinate (Maze index) of the ENTRY_POSITION of the Maze.
     * @param aVerticalEntryPosition vertical position of the entry between 1 and maxPathNumber
     * @return two dimension int array with coordinate (Maze index) of the ENTRY_POSITION
     */
    private Point calculateEntryPosition(final int aVerticalEntryPosition){
        final int FIRST_HORIZONTAL_POSITION = 0;
        return new Point(FIRST_HORIZONTAL_POSITION,MARGIN_OF_MAZE_DIMENSION + aVerticalEntryPosition *
                (EMPTY_BLOCK_DIMENSION + PATH_BLOCK_DIMENSION ) - PATH_BLOCK_DIMENSION, PATH_BLOCK_DIMENSION);
    }

    /**
     * Calculate the coordinate (Maze index) of the EXIT_POSITION of the Maze.
     * @param aVerticalExitPosition vertical/posY position of the EXIT_POSITION between 1 and maxPathNumber
     * @return two dimension int array with coordinate (Maze index) of the EXIT_POSITION
     */
    private Point calculateExitPosition(int aVerticalExitPosition) {
        final int LAST_HORIZONTAL_POSITION = HORIZONTAL_DIMENSION_OF_MAZE - MARGIN_OF_MAZE_DIMENSION;
        return new Point(LAST_HORIZONTAL_POSITION,  MARGIN_OF_MAZE_DIMENSION + aVerticalExitPosition *
                (EMPTY_BLOCK_DIMENSION + PATH_BLOCK_DIMENSION ) - PATH_BLOCK_DIMENSION, PATH_BLOCK_DIMENSION);
    }

    private Point calculateRandomPosition(){
        Random random = new Random();
        int horizontalPosition = random.nextInt(NUMBER_OF_HORIZONTAL_PATHS) + 1;
        int verticalPosition = random.nextInt(NUMBER_OF_VERTICAL_PATHS) + 1;
        return new Point(MARGIN_OF_MAZE_DIMENSION + horizontalPosition * (EMPTY_BLOCK_DIMENSION + PATH_BLOCK_DIMENSION) - PATH_BLOCK_DIMENSION,
                MARGIN_OF_MAZE_DIMENSION + verticalPosition * (EMPTY_BLOCK_DIMENSION + PATH_BLOCK_DIMENSION) - PATH_BLOCK_DIMENSION
                ,PATH_BLOCK_DIMENSION);
    }

    public int[][] getMaze(){
        return Maze;
    }

}
