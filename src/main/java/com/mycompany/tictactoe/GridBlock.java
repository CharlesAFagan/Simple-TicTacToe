/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Charles Fagan
 */
public class GridBlock {
    
    private int blockValue; // 1=empty, 2="X", 3="O"
    private final int position[];
    private final int size;
    
    public GridBlock(int row, int col){
        blockValue = 1;
        position = new int[]{row, col};
        size = 300;
    }
    
    public void drawBlock(Graphics2D g){
        Font font = new Font("SansSerif", Font.BOLD, size/2);
        String xy = "";
        g.setFont(font);
        g.setColor(Color.black);
        g.fillRect(position[0]*size, position[1]*size, size, size);

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.white);
        g.drawRect(position[0]*size, position[1]*size, size, size);
        
        if(blockValue==2) xy = "X";
        if(blockValue==3) xy = "O";
        g.setColor(Color.yellow);
        g.drawString(xy, (position[0]*size)+(size/3), 
                                (position[1]*size)+((size/3)*2));
    }
    
    public int getBlockValue(){
        return blockValue;
    }
    
    public void setBlockValue(int v){// Only empty blocks can be set
        if(blockValue==1) blockValue = v;
    }
    
    public int[] getBlockPosition(){
        return position;
    }
}
        
