/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import static java.nio.file.Files.size;

/**
 *
 * @author Charles Fagan
 */
public class GameGrid {
    
    public GridBlock grid[][];
    
    public GameGrid(){
        grid = new GridBlock[3][3];
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j]= new GridBlock(i,j);
            }
        }
    }
    
    public void draw(Graphics2D g, int ps, int cs){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                grid[i][j].drawBlock(g);
            }
        }
        Font size = new Font("SansSerif", Font.BOLD, 900/48);
        g.setFont(size);
        g.setColor(Color.red);
        g.drawString("Player Score = " + ps, 70, 30);
        g.drawString("Computer Score = " + cs, 660, 30);
    }
    
    public int[][][] getLines(){
        int lines[][][]=new int[8][3][3]; // Array to store the eight winning lines and grid positions
        // convert GameGrid block values to an array of all eight winning lines
        for(int i=0; i<3;i++){
            for(int j=0; j<3;j++){
                lines[i][j][0]=grid[i][j].getBlockValue();// rows
                lines[i][j][1]=i;
                lines[i][j][2]=j;
                lines[i+3][j][0]=grid[j][i].getBlockValue();// columns
                lines[i+3][j][1]=j;
                lines[i+3][j][2]=i;
                if(i==j){// top left to bottom right diagonal
                    lines[6][i][0]=grid[i][j].getBlockValue();
                    lines[6][i][1]=i;
                    lines[6][i][2]=j;
                }// top right to bottom left diagonal
                if(i-j==2||(i==1&&j==1)||j-i==2){
                    lines[7][i][0]=grid[i][j].getBlockValue();
                    lines[7][i][1]=i;
                    lines[7][i][2]=j;
                }
            }
        }
        return lines;
    }   
}
