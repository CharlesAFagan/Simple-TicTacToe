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
import javax.swing.JPanel;

/**
 *
 * @author Charles Fagan
 */
public class PlayGame extends JPanel implements MouseListener, KeyListener{
    
    private int playerScore = 0;
    private int computerScore = 0;
    
    private int gameState = 0;//-->0=waiting to start; 1=game in progress; 
    private int gameNumber = 1;//  2=Player win; 3=computer win; 4=stalemate
    
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
    
    private void computerMove(){     
        grid.makeMove(grid.findMove(), 3);        
        checkWin();
        repaint();
    }
        
    private boolean checkWin(){              
        for(int i=0; i<8;i++){// look for possible winning lines
            int line = grid.getLineProduct(i);
            if(line==8){
                playerScore++;
                gameState = 2;//player win
                return true;
            }
            if(line==27){
                computerScore++;
                gameState = 3;//computer win
                return true;
            }
        }        
        if(!grid.movesLeft()){
            gameState = 4;// stalemate
            return true;
        }
        return false;
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
            grid.draw((Graphics2D)g,playerScore,computerScore);
        }
        if(gameState>1){
            grid.draw((Graphics2D)g,playerScore,computerScore);
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
                int[] position = {e.getX()/300, e.getY()/300};
                if(!grid.makeMove(position, 2)) return;
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
                gameNumber += 1;
                grid = new GameGrid();
                if(gameNumber%2==0){
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
