package sudokuvalidator;

import java.io.*;
import java.util.*;

public class SudokuValidator {

    //intialize the board that will be used to house file numbers
    private static int board[][] = new int[9][9]; // 9x9 array
    //set values to -1
    private static int mRowNum = -1;
    private static int mColNum = -1;
    private static int mSubGridNum = -1;
    
    private static String msgRow = " ";
    private static String msgCol = " ";
    private static String msgSubGrid = " ";
    
    //read the given file and copy data from it to the 9x9 array
    private static void readBoard(String fileName){
        try{
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            int row = 0;
        
            while((line = br.readLine()) != null){
                String[] vals = line.trim().split(","); //trim lines to correct size and get rid of commas found in .txt file
            
                for(int col = 0; col < 9; col++){
                    board[row][col] = Integer.parseInt(vals[col]);
                }
                row++;
            }
        }
        catch(IOException io){
            io.printStackTrace();
        }
        printBoard();
    }
    
    //function to board 9x9 array in a grid with the numbers from text file
    private static void printBoard(){
        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        //input file specified by user
        System.out.print("Input a file name: ");
        
        //set the scanner for the file
        Scanner sc = new Scanner(System.in);
        String fileName = sc.next();
        System.out.println();
        readBoard(fileName);
    }
    
}
