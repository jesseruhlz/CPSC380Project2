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
        for (int i = 0; i < 9; ++i){
            for(int j = 0; j < 9; ++j){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //a thread that will check the column of the grid-array for error
    static private class ColumnCheck extends Thread{
        private int columnError = -1;
        private HashSet<Integer> columnSet = new HashSet<>();
        
        public void run(){
            for (int i = 0; i < 9; ++i){
                for (int j = 0; j < 9; ++j){
                    //if an error is found in the column
                    if(!columnSet.add(board[i][j])){
                        columnError = i;
                        System.out.println("Column thread\t\tFound an error at column " + (i + 1));
                        Integer[] tempArray = columnSet.toArray(new Integer[columnSet.size()]);
                        Arrays.sort(tempArray);
                        
                        //using iteration to find a duplicate number in the column
                        for (int k =1; k < 10; k++){
                            if(Arrays.asList(tempArray).contains(k) == false){
                                mColNum += k + 1;
                                System.out.println("\nColum Error: On column " + (i + 1) + "\tDuplicate numer " + board[i][j] + "\tReplace the duplicate " + board[i][j] + " with a " + mColNum);
                                mColNum -= 2;
                                break;
                            }
                        }
                    }
                    if (j ==8){
                        columnSet.clear();
                    }
                }
            }
        }
        private int getColumnError(){
                return columnError;
        }
    }
    
    //second thread that will check each row in the grid-array for errors
    static private class RowCheck extends Thread{
        
        private int rowError = -1;
        private HashSet<Integer> rowSet = new HashSet<>();
        
        public void run(){
            for (int i = 0; i < 9; ++i){
                for (int j = 0; j < 9; ++j){
                    //if an error is found in the row
                    if(!rowSet.add(board[i][j])){
                        rowError = i;
                        System.out.println("Row thread\t\tFound error on row " + (i + 1));
                        Integer[] tempArray = rowSet.toArray(new Integer[rowSet.size()]);
                        Arrays.sort(tempArray);
                        
                        //iterate to find the suplicate number
                        for(int k = 0; k < 10; k++){
                            if(Arrays.asList(tempArray).contains(k) == false){
                                mRowNum += k + 1;
                                System.out.println("\nRow Error: row " + (i + 1) + "\tDuplicate " + board[i][j] + "\t Replace the duplicate " + board[i][j] + " with a " + mRowNum);
                                mRowNum -= 2;
                                break;
                            }
                        }
                    }
                    if(j == 8){
                        rowSet.clear();
                    }
                }
            }
        }
        private int GetRowError(){
            return rowError;
        }
    }
    
    //for the subgrid check will have to divide the subgrids up
    //use a rowstart, row end, column start, and column end to bound the subgrids
    //use an integer to keep track of what subgrid we are checking
    //(int startRow, endRow, startCol, endCol, subgridNum)
    //make the thread for the subgrids, then use a function to check each subgrid
    
    static private class CheckSubGrid extends threads{
        public void run(){
            //here we will make the bounds for each subgrid and keep track of each 
            sgCheck(0, 3, 0, 3, 1);
            
        }
    }
    
    private static void sgCheck(int startRow, int endRow, int startCol, int endCol, int subgridNumber){
        
    }
    
    
    public static void main(String[] args) {
        //input file specified by user
        System.out.print("Input a file name: ");
        
        //set the scanner for the file
        Scanner sc = new Scanner(System.in);
        String fileName = sc.next();
        System.out.println();
        readBoard(fileName);
        
        //using an array list to keep track of the multiple threads
        ArrayList<Thread> threads = new ArrayList<>();
        
        ColumnCheck colThread = new ColumnCheck();
        RowCheck rowThread = new RowCheck();
        
        //need to add the threads
        threads.add(new Thread(colThread));
        threads.add(new Thread(rowThread));
        
        try{
            for (Thread t : threads){
                t.start();
            }
        }
        catch(Exception e){
            System.out.println("Unable to start the threads.");
        }
        
        try{
            for(Thread t : threads){
                t.join();
            }
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        
        int colError = colThread.getColumnError();
        int rowErrors = rowThread.GetRowError();
        
        System.out.println(msgCol);
    }
    
}
