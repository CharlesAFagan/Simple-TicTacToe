/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Charles Fagan
 */

public class GameGrid {
    
    private final GridBlock grid[][];
    
    public GameGrid(){
        grid = new GridBlock[8][3];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                grid[i][j]= new GridBlock(i,j);     // columns left to right
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++){
                grid[i+3][j]=grid[j][i];            // rows top to bottom
                if(i==j)                            // diagonal 1 \
                    grid[6][i]=grid[i][j];                           
                if(i-j==2||(i==1&&j==1)||j-i==2)    // diagonal 2 /
                    grid[7][i]=grid[i][j];
            }     
    }
    
    public void draw(Graphics2D g, int ps, int cs){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                grid[i][j].drawBlock(g);
            }
        }
        Font size = new Font("SansSerif", Font.BOLD, 900/48);
        g.setFont(size);
        g.setColor(Color.red);
        g.drawString("Player Score = " + ps, 70, 30);
        g.drawString("Computer Score = " + cs, 660, 30);
    }
    
    public int getLineProduct(int line){//------> 1 = blank, 2 = X,    3 = O
        int product = 1;                       // 4 = XX,    6 = XO,   8 = XXX
        for(GridBlock current : grid[line])    // 9 = OO,   12 = XXO, 18 = OOX
            product *= current.getBlockValue();//27 = OOO
        return product;
    }
    
    public int[] findMove() throws InterruptedException{
        try{
            return winningMove();
        }catch(NoValidMoveException e){}
        try{
            return trappingMove();
        }catch(NoValidMoveException e){}
        return randomMove();
    }
    
    public boolean makeMove(int[] move, int xo){
        if(grid[move[0]][move[1]].getBlockValue()==1){
            grid[move[0]][move[1]].setBlockValue(xo);
            return true;
        }
        return false;
    }
    
    // Method to find a move that will win the game, if none are found, find a 
    // move that will prevent the player from winning.
    private int[] winningMove()throws NoValidMoveException{
        int xo = 9;         // First look for rows with only two O's
        while(xo>3){        // and return the empty block for that row
            for(int i=0; i<8; i++){
                if(getLineProduct(i)==xo)
                    for(int j=0;j<3;j++)
                        if(grid[i][j].getBlockValue()==1)
                            return grid[i][j].getBlockPosition();
            }
            xo-=5;          // Then look for rows with only two X's
        }
        throw new NoValidMoveException();
    }
    
    // Method to find a move that will give the computer two possible winning 
    // moves for the next turn, only one of which the player would be able to
    // block. If not find a move that will block the player from doing the same.
    private int[] trappingMove()throws NoValidMoveException{
        for(int xo=3; xo>1; xo--){
            GridBlock[][] rows = new GridBlock[0][3];
            int numFound = 0;       // First store all rows with only one O
            for(int i=0; i<8; i++){
                if(getLineProduct(i)==xo){
                    rows = Arrays.copyOf(rows, rows.length+1);
                    rows[numFound] = grid[i];
                    numFound ++;
                }
            }               // Then compare the stored rows to see if any 
            if(numFound>1)  // overlap at an empty block and retrun its position
                for(int row=0; row<rows.length; row++)
                    for(int nextRow=row+1; nextRow<rows.length; nextRow++)
                        for(int i=0; i<3; i++)
                            for(int j=0; j<3; j++)
                                if((rows[row][i].equals(rows[nextRow][j]))
                                    &&(rows[row][i].getBlockValue()==1))
                                        return rows[row][i].getBlockPosition();
        }                   // Repeat all steps for X's
        throw new NoValidMoveException();
    }
    
    private int[] randomMove(){
        Random rand = new Random();
        int[] position = new int[2];
        Boolean success = false;
        while(!success){
            position = new int[]{rand.nextInt(3), rand.nextInt(3)};
            if(grid[position[0]][position[1]].getBlockValue()==1)
                success = true;
        }
        return position;
    }
    
    public boolean movesLeft(){
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (grid[i][j].getBlockValue() == 1)
                    return true;
        return false;
    }
    
    private class NoValidMoveException extends Exception {}
}
