package Sudoku;

public class Cell {
    private int dimension;
    private int[][] puzzle4 = {
            {0,0,0,0},
            {0,2,0,1},
            {4,0,2,0},
            {0,0,0,0}
    };
    private int[][] mask4 = {
            {0,0,0,0},
            {0,1,0,1},
            {1,0,1,0},
            {0,0,0,0}
    };
    private int[][] puzzle4sets = {
            {11,12,21,22},
            {13,14,23,24},
            {31,32,41,42},
            {33,34,43,44}
    };
    private int[][] puzzle4colour = {
            {66,206,244},
            {227,247,131},
            {247,169,131},
            {131,247,158}
    };
    private int[][] puzzle6 = {
            {0,5,2,0,0,0},
            {0,0,0,0,0,0},
            {5,0,0,0,2,0},
            {0,1,0,0,0,6},
            {0,0,0,0,0,0},
            {0,0,0,4,3,0}
    };
    private int[][] mask6 = {
            {0,1,1,0,0,0},
            {0,0,0,0,0,0},
            {1,0,0,0,1,0},
            {0,1,0,0,0,1},
            {0,0,0,0,0,0},
            {0,0,0,1,1,0}
    };
    private int[][] puzzle6sets = {
            {11,12,13,23,33,43},
            {14,15,16,24,25,26},
            {21,22,31,32,41,42},
            {51,52,53,61,62,63},
            {34,44,54,64,65,66},
            {35,36,45,46,55,56}
    };
    private int[][] puzzle6colour = {
            {66,206,244},
            {227,247,131},
            {247,169,131},
            {131,247,158},
            {85,135,170},
            {164,74,191}
    };
    private int[][] puzzle8 = {
            {0,0,0,1,0,2,6,5},
            {0,0,0,6,0,0,0,0},
            {0,8,7,0,0,0,2,0},
            {6,0,2,0,0,0,0,8},
            {1,0,0,0,0,4,0,6},
            {0,2,0,0,0,5,3,0},
            {0,0,0,0,3,0,0,0},
            {3,7,1,0,8,0,0,0}
    };
    private int[][] mask8 = {
            {0,0,0,1,0,1,1,1},
            {0,0,0,1,0,0,0,0},
            {0,1,1,0,0,0,1,0},
            {1,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,1},
            {0,1,0,0,0,1,1,0},
            {0,0,0,0,1,0,0,0},
            {1,1,1,0,1,0,0,0}
    };
    private int[][] puzzle8sets = {
            {11,12,21,22,31,32,41,42},
            {13,14,23,24,33,34,43,44},
            {15,16,17,18,25,26,27,28},
            {35,36,37,38,45,46,47,48},
            {51,52,53,54,61,62,63,64},
            {71,72,73,74,81,82,83,84},
            {55,56,65,66,75,76,85,86},
            {57,58,67,68,77,78,87,88}
    };
    private int[][] puzzle8colour = {
            {66,206,244},
            {227,247,131},
            {247,169,131},
            {131,247,158},
            {85,135,170},
            {164,74,191},
            {0,144,255},
            {221,255,0}
    };

    public Cell(int dimension){
        this.dimension = dimension;
    }

    public int getValue(int row, int col){
        if(dimension == 4) return puzzle4[row][col];
        if(dimension == 6) return puzzle6[row][col];
        if(dimension == 8) return puzzle8[row][col];
        return -1;
    }
    public int getSet(int row, int col){
        row++;
        col++;
        int temp = row*10 + col;
        if(dimension == 4)
            for( int i=0; i < dimension; i++){
                for( int j=0; j < dimension; j++){
                    if(puzzle4sets[i][j]==temp) return i;
                }
            }

        if(dimension == 6)
            for( int i=0; i < dimension; i++){
                for( int j=0; j < dimension; j++){
                    if(puzzle6sets[i][j]==temp) return i;
                }
            }

        if(dimension == 8)
            for( int i=0; i < dimension; i++){
                for( int j=0; j < dimension; j++){
                    if(puzzle8sets[i][j]==temp) return i;
                }
            }

        return -1;
    }
    public int[] getColour(int set){
        if(dimension == 4) return puzzle4colour[set];
        if(dimension == 6) return puzzle6colour[set];
        if(dimension == 8) return puzzle8colour[set];
        return null;
    }

    public void setValue(int row, int col, int value) throws RuntimeException {
        if(value == -1){
            System.out.println("Resetting value on "+row+" "+col+"...");
            if(dimension==4) puzzle4[row][col] = 0;
            if(dimension==6) puzzle6[row][col] = 0;
            if(dimension==8) puzzle8[row][col] = 0;
            return;
        }
        System.out.println("Attempting to set value " + value + "...");
        int [][] table = null;
        int [][] sets = null;
        int [][] mask = null;
        if( dimension == 4 ){
            table = puzzle4;
            sets = puzzle4sets;
            mask = mask4;
        }
        if( dimension == 6 ){
            table = puzzle6;
            sets = puzzle6sets;
            mask = mask6;
        }
        if( dimension == 8 ){
            table = puzzle8;
            sets = puzzle8sets;
            mask = mask8;
        }

        int oldVal = table[row][col];
        int count1 = 0, count2 = 0, count3 = 0;
        boolean colision = false;
        String temp1 = "", temp2 = "", temp3 = "";

        //check columns
        for( int i=0; i < dimension; i++){
            if(table[row][i]==value) {
                if( mask[row][i] == 1 ) colision = true;
                count1++;
                temp1 += "" + row + "" + i;
                System.out.println("Found colision1 " + temp1);
            }
        }
        if( count1 < 1 ){
            count1 = 0;
            temp1 = "";
        }

        //check rows
        for( int i=0; i < dimension; i++){
            if(table[i][col]==value){
                if( mask[i][col] == 1 ) colision = true;
                count2++;
                temp2 += "" + i + "" + col;

                System.out.println("Found colision2 " + temp2);
            }
        }
        if( count2 < 1 ){
            count2 = 0;
            temp2 = "";
        }

        //check sets
        int set = getSet(row,col);
        for( int i=0; i < dimension; i++){
            int row1 = sets[set][i]/10-1;
            int col1 = sets[set][i]%10-1;
            if(table[row1][col1] ==  value){
                if( mask[row1][i] == 1 ) colision = true;
                count3++;
                temp3 += "" + row1 + "" + col1;
                System.out.println("Found colision3 " + temp3);
            }
        }
        if( count3 < 1 ){
            count3 = 0;
            temp3 = "";
        }
        if(colision){
            new Error();
            throw new RuntimeException("e");
        }

        if(dimension==4) puzzle4[row][col] = value;
        if(dimension==6) puzzle6[row][col] = value;
        if(dimension==8) puzzle8[row][col] = value;


        System.out.println("Value set to " + value + "...");
        if(count1+count2+count3 == 0){
            System.out.println("Without error");
            return;
        }

        System.out.println("Throwing error " + temp1+temp2+temp3);
        throw new RuntimeException(temp1+temp2+temp3);
    }
}