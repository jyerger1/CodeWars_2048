package com.codegym.games.game2048;
import com.codegym.engine.cell.*;
import java.util.*;

public class Game2048 extends Game {
    
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score;



    
    @Override
    public void initialize() {
               setScreenSize(SIDE, SIDE);
               createGame();
               
               drawScene();

    }
    


    
    private void createGame () {
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }
    
    private void drawScene() {
        for (int x = 0; x < SIDE; x++) {
            for (int y = 0; y < SIDE; y++) {
                setCellColoredNumber(x,y, gameField[y][x]);
            }
        }
    }

        
    
    
    private void createNewNumber(){
    int randomCellX = getRandomNumber(SIDE);
    int randomCellY = getRandomNumber(SIDE);
    
    if( gameField[randomCellX][randomCellY] != 0){
        createNewNumber();
        
    }else{
        
       int value = getRandomNumber(10);
       if(value == 9){
           gameField[randomCellX][randomCellY] = 4;
       }else{
           gameField[randomCellX][randomCellY] = 2;
       }
    }
    if(getMaxTileValue() == 2048)
    win();
 }
       
       
     private Color getColorByValue(int value){
        //return a cell color based on value
        switch(value){
            case 0: return Color.WHITE;
            case 2: return Color.BLUE;
            case 4: return Color.RED;
            case 8: return Color.GREEN;
            case 16: return Color.CYAN;
            case 32: return Color.GRAY;
            case 64: return Color.MAGENTA;
            case 128: return Color.ORANGE;
            case 256: return Color.PINK;
            case 512: return Color.YELLOW;
            case 1024: return Color.PURPLE;
            case 2048: return Color.BROWN;
            default: return Color.WHITE;
        }
    }
       
       
     private void setCellColoredNumber(int x, int y, int value){
        if (value != 0) {
            setCellValueEx(x, y, getColorByValue(value), Integer.toString(value));
        } else {
            setCellValueEx(x, y, getColorByValue(value), "");

        }
    }
    
     private boolean compressRow(int[] row){
    // int col = -1;
        int length = row.length;
        boolean hasChanged = false;
        for(int x=0; x< length; x++){
        for (int j = 0; j< length * length; j++){
         int i = j % length;
         if(i == length -1){
             continue;
         }
         if(row[i] == 0 && row[i +1] !=0){
             row[i] = row[i+1];
             row[i+1] =0;
             hasChanged = true;
         }
     }
     }
     return hasChanged;
}

    private boolean mergeRow(int[] row) {
        
        boolean merge = false;
            for (int m = 0; m < row.length-1; m++){
                if (row[m] != 0 && row[m] == row[m + 1]) {
                    merge = true;
                    row[m] *= 2;
                    row[m + 1] = 0;
                    score += row[m];
                    setScore(score);
                }
    }
       return false;
    }




    private void moveLeft () {
        boolean compress;
        boolean merge;
        int move = 0;
        for (int i = 0; i < SIDE; i++) {
             compress = compressRow(gameField[i]);
             merge = mergeRow(gameField[i]);
             compressRow(gameField[i]);
             if (merge || compress) move++;
        } if (move > 0)
            createNewNumber();
    }

     private void moveRight () {
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }

    private void moveUp () {
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }

    private void moveDown () {
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }



    @Override
    public void onKeyPress(Key key){
        if(isGameStopped){
            
            if(key == Key.SPACE){
                isGameStopped = false;
                score = 0;
                setScore(score);
                createGame();
                drawScene();
            }
        }
        else if( canUserMove() ){
        
            if (key == Key.LEFT){
            moveLeft();
            }
            else if (key == Key.RIGHT){
            moveRight();
            }
            else if (key == Key.UP){
                moveUp();
            }
            else if (key == Key.DOWN){
            moveDown();
            }
            drawScene();
            }
        else{
            gameOver(); }
    }

private void rotateClockwise () {
        int[][] tempMat = new int[SIDE][SIDE];
        for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE; c++) {
                tempMat[c][SIDE - 1 - r] = gameField[r][c];
            }
        for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE; c++) {
                gameField[r][c] = tempMat[r][c];
            }
    }
    
    
     private int getMaxTileValue () {
        int max = 0;
        for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE; c++)
                if (gameField[r][c] > max)
                    max = gameField[r][c];

        return max;
    }
    
   private void win () {
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "You Win", Color.WHITE, 75);
    }
    
    
    private boolean canUserMove(){
         for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE; c++)
                if (gameField[r][c] == 0)
                    return true;

        for (int r = 0; r < SIDE-1; r++)
            for (int c = 0; c < SIDE; c++)
                if (gameField[r][c] == gameField[r+1][c])
                        return true;

        for (int r = 0; r < SIDE; r++)
            for (int c = 0; c < SIDE - 1; c++)
                if (gameField[r][c] == gameField[r][c + 1])
                    return true;

        return false;
    }


    private void gameOver () { 
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "Game Over", Color.RED, 75);
    }

        
    
    
    
        
    
}