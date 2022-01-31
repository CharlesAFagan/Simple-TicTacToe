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
}
