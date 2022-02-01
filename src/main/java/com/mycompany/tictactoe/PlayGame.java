/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Charles Fagan
 */
public class PlayGame extends JPanel implements MouseListener, KeyListener{
    
    private int pScore = 0; // player score
    private int cScore = 0; // computer score
    
    private int gameState = 0; // 0 = waiting to start; 1 = game in progress; 2 = Player win; 3 = computer win; 4 = stalemate
    private int gameNum = 1;
    
    private GameGrid grid;
    
    public PlayGame(){
        grid = new GameGrid();
        listeners();
        setFocusable(true);
    }
    
    private void listeners(){
        addMouseListener(this);
        addKeyListener(this);        
    }
    
    private boolean win(int rowScore){
        if(rowScore==8){
            playerWin();
            return true;
        }
        if(rowScore==27){
            computerWin();
            return true;
        }
        return false;
    }
    
    private void playerWin(){
        pScore++;
        gameState = 2;
    }
    
    private void computerWin(){
        cScore++;
        gameState = 3;
    }
    
    private void stalemate(){
        gameState = 4;
    }
    
    private boolean checkWin(){
        int lines[][][]=grid.getLines(); // get current line array
        int line; // used to store the product of row under test
        
        // look for possible winning lines
        for(int i=0; i<8;i++){
            line=lines[i][0][0]*lines[i][1][0]*lines[i][2][0];
            if(win(line)) return true;
        }
        
        // if no moves left declare stalemate
        if(!movesLeft()){
            stalemate();
            return true;
        }
        return false;
    }
    
    private boolean movesLeft(){
        for (GridBlock[] grid1 : grid.grid) {
            for (int j = 0; j < grid.grid[0].length; j++) {
                if (grid1[j].getBlockValue() == 1) {
                    return true;
                }                
            }
        } 
        return false;
    }
    
    private void computerMove(){
        
        int[] move = findBestMove();
        grid.grid[move[0]][move[1]].setBlockValue(3);
        
        checkWin();
        repaint();
    }
        
    private int[] randomMove(){
        Random rand = new Random();
        int pos[] = new int[2];
        Boolean success = false;
        while(!success){
            pos[0] = rand.nextInt(3);
            pos[1] = rand.nextInt(3);
            if(grid.grid[pos[0]][pos[1]].getBlockValue()==1){
                success = true;
            }
        }
        return pos;
    }
    
    private int[] findBestMove(){
        int lines[][][]=grid.getLines(); // get current line array
        int line; // used to store the product of row under test
        int pos[]=new int[2]; // used to return the position of the move
        int xo = 9;// used to search for winning moves and then defensive moves
        //a line product of 9 indicates the line has two O's while 4 = two X's
        
        
        while(xo>3){  
            for(int i=0; i<8; i++){// look for possible winning/defensive moves and return position if found
                line=lines[i][0][0]*lines[i][1][0]*lines[i][2][0];
                if(line==xo){ 
                    for(int j=0;j<3;j++){// if it does, find the remaining open block and return its position
                        if(lines[i][j][0]==1){
                            pos[0]=lines[i][j][1];
                            pos[1]=lines[i][j][2];
                            return pos;
                        }
                    }
                }
            }
            xo-=5;
        }
        return randomMove();// if no good move found return random
    }    
    
    @Override
    public void paint(Graphics g){
        Font size = new Font("SansSerif", Font.BOLD, 900/24);
        if(gameState==0){
            g.setColor(Color.black);
            g.fillRect(0, 0, 900, 950);
            
            g.setFont(size);
            g.setColor(Color.red);
            g.drawString("Press 'Enter' to begin new game", 120, 150);
        }
        if(gameState==1){            
            grid.draw((Graphics2D)g,pScore,cScore);
        }
        if(gameState>1){
            grid.draw((Graphics2D)g,pScore,cScore);
            size = new Font("SansSerif", Font.BOLD, 900/12);
            g.setFont(size);
            g.setColor(Color.red);
            if(gameState==2)g.drawString("You Win!!", 250, 250);
            if(gameState==3)g.drawString("You Lose!!", 230, 250);
            if(gameState==4)g.drawString("Stalemate!!", 210, 250);
            size = new Font("SansSerif", Font.BOLD, 900/32);
            g.setFont(size);
            g.drawString("Press 'Enter' to play again", 245, 350);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gameState==1){
            if(e.getButton() == MouseEvent.BUTTON1){
                int x = e.getX()/300;
                int y = e.getY()/300;
                if(grid.grid[x][y].getBlockValue()==1){
                    grid.grid[x][y].setBlockValue(2);
                }else return;
            }
            if(checkWin()){
                repaint();
            } else{
                repaint();
                computerMove();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == KeyEvent.VK_ENTER){
            if(gameState<1){
                gameState = 1;
                repaint();
            }
            if(gameState>1){
                gameState = 1;
                gameNum += 1;
                grid = new GameGrid();
                if(gameNum%2==0){
                   computerMove(); 
                }else repaint();               
            }
        }            
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}    
    
}
