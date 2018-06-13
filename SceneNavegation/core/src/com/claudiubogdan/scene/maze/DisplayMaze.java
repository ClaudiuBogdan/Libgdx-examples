package com.claudiubogdan.scene.maze;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class DisplayMaze {
    public static void main(String[] args){
        System.out.println("Hello, World!");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Constant dimensions of windows
                final int width = 1500;
                final int high = 900;
                //Create and set up the window.
                JFrame frame = new JFrame("HelloWorldSwing");
                frame.setTitle("Maze Rubicon");
                frame.setSize(width, high);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                boolean run = true;
                while(run){
                    run = false;
                    for(int i=0; i<1;i++){
                        int complexity = 1;
                        double dimMultiple = 10.0 * complexity;
                        int horizontalPaths = (int)(dimMultiple*16.0/9.0) ; //752;
                        int verticalPaths = (int) dimMultiple; //432;
                        int entry = (int) (verticalPaths/2.0);
                        int exit = (int) (verticalPaths/2.0);
                        int blockWidth = (int) (40.0/complexity);
                        int blockHigh = blockWidth;

                        GenerateMaze mMaze = new GenerateMaze(horizontalPaths  , verticalPaths, entry, exit );
                        //Initialize block component
                        DrawMazeBlock component = new DrawMazeBlock(mMaze, blockWidth, blockHigh);

                        //Insert Maze to render
                        frame.getContentPane().removeAll();

                        //Add component to the frame
                        frame.add(component);

                        //frame.setUndecorated(true);

                        //Display the window.
                        frame.setVisible(true);
/*            long sum = 1;
            for(int j=0; j<10000; j++){
                for(int k=0; k<10000; k++)
                sum *= sum * j * k;
            }*/
                    }
                    try {
                        //Thread.sleep(500);
                    }
                    catch (Exception ex){

                    }
                }

            }

        };
        Thread thread = new Thread(runnable);

        thread.start();


    }

    private static void createAndShowGUI() {



    }

    public static class DrawMazeBlock extends JComponent{

        private final int WIDTH_OF_BLOCK ;
        private final int HIGH_OF_BLOCK ;
        private final GenerateMaze gereratedMaze;
        private final int[][] MazeMatrix;

        public DrawMazeBlock(GenerateMaze aMaze,int width, int high){
            this.gereratedMaze = aMaze;
            this.WIDTH_OF_BLOCK = width;
            this.HIGH_OF_BLOCK = high;
            this.MazeMatrix = aMaze.getMaze();
        }

        public void paintComponent(Graphics g){
            //Constant dimensions of a block
            //Recover Graphics2D

            Graphics2D g2 = (Graphics2D) g;

            //Create geometric shape
            List<Rectangle> rectangleArray = new ArrayList<Rectangle>();
            for(int i = 0; i<MazeMatrix.length; i++){
                for(int j = 0; j < MazeMatrix[0].length; j++){
                    if( MazeMatrix[i][j] == gereratedMaze.MARGIN_ID) {
                        g2.setColor(Color.BLACK);
                    }
                    else if(MazeMatrix[i][j] == gereratedMaze.ENTRY_ID) {
                        g2.setColor(Color.RED);
                    }
                    else if(MazeMatrix[i][j] == gereratedMaze.EXIT_ID) {
                        g2.setColor(Color.CYAN);
                    }
                    else if(MazeMatrix[i][j] == gereratedMaze.ENTRY_PATH_ID) {
                        g2.setColor(Color.WHITE);
                    }
                    else if(MazeMatrix[i][j] == gereratedMaze.EXIT_PATH_ID) {
                        g2.setColor(Color.WHITE);
                    }
                    else if(MazeMatrix[i][j] == gereratedMaze.GENERIC_PATH_ID) {
                        g2.setColor(Color.WHITE);
                    }
                    else if (MazeMatrix[i][j] == gereratedMaze.EMPTY_ID){
                        g2.setColor(Color.DARK_GRAY);
                    }
                    g2.fill(new Rectangle( i * WIDTH_OF_BLOCK  , j * HIGH_OF_BLOCK , WIDTH_OF_BLOCK  , HIGH_OF_BLOCK));
                    }

                }

            }


    }
}
