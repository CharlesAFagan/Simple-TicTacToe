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
    
    private int fill; // 0=blank, 1="X", 2="O"
    private int posX;
    private int posY;
    private int blockSize;
    
    public GridBlock( int row, int col){
        fill = 0;
        posX = row;
        posY = col;
        blockSize = 300;
    }
    
    public void drawBlock(Graphics2D g){
        Font size = new Font("SansSerif", Font.BOLD, blockSize/2);
        g.setFont(size);
        g.setColor(Color.black);
        g.fillRect(posX*blockSize, posY*blockSize, blockSize, blockSize);

        g.setStroke(new BasicStroke(3));
        g.setColor(Color.white);
        g.drawRect(posX*blockSize, posY*blockSize, blockSize, blockSize);
        
        if(fill==1){
            g.setColor(Color.yellow);
            g.drawString("X", (posX*blockSize)+(blockSize/3), (posY*blockSize)+((blockSize/3)*2));
        }
        if(fill==2){
            g.setColor(Color.yellow);
            g.drawString("O", (posX*blockSize)+(blockSize/3), (posY*blockSize)+((blockSize/3)*2));
        }
    }
    
    public int getBlockValue(){
        return fill;
    }
    
    public void setBlockValue(int v){
        if(fill==0) fill = v;
    }
}
        
