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
    
    private boolean play = false;
    private int pScore = 0;
    private int cScore = 0;
    
    private int gameState = 0; // 0 = waiting to start; 1 = game in progress; 2 = Player win; 3 = computer win; 4 = stalemate
    private int gameNum = 1;
    private int moveNum = 0;
    
    private GameGrid grid;
    
    public PlayGame(){
        grid = new GameGrid();
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
//        setFocusTraversalKeysEnabled(false);
    }
    
    public boolean win(int rowScore){
        if(rowScore==1){
            playerWin();
            return true;
        }
        if(rowScore==8){
            computerWin();
            return true;
        }
        return false;
    }
    
    public void playerWin(){
        pScore++;
        gameState = 2;
    }
    
    public void computerWin(){
        cScore++;
        gameState = 3;
    }
    
    public void stalemate(){
        gameState = 4;
    }
    
    public boolean checkWin(){
        int num;
        num = grid.grid[0][0].getBlockValue()*grid.grid[0][1].getBlockValue()*grid.grid[0][2].getBlockValue();
        if(win(num))return true;
        num = grid.grid[1][0].getBlockValue()*grid.grid[1][1].getBlockValue()*grid.grid[1][2].getBlockValue();
        if(win(num))return true;   
        num = grid.grid[2][0].getBlockValue()*grid.grid[2][1].getBlockValue()*grid.grid[2][2].getBlockValue();
        if(win(num))return true;
        num = grid.grid[0][0].getBlockValue()*grid.grid[1][0].getBlockValue()*grid.grid[2][0].getBlockValue();
        if(win(num))return true;   
        num = grid.grid[0][1].getBlockValue()*grid.grid[1][1].getBlockValue()*grid.grid[2][1].getBlockValue();
        if(win(num))return true;
        num = grid.grid[0][2].getBlockValue()*grid.grid[1][2].getBlockValue()*grid.grid[2][2].getBlockValue();
        if(win(num))return true; 
        num = grid.grid[0][0].getBlockValue()*grid.grid[1][1].getBlockValue()*grid.grid[2][2].getBlockValue();
        if(win(num))return true;
        num = grid.grid[0][2].getBlockValue()*grid.grid[1][1].getBlockValue()*grid.grid[2][0].getBlockValue();
        if(win(num))return true;
        if(!movesLeft()){
            stalemate();
            return true;
        }
        return false;
    }
    
    public boolean movesLeft(){
        for(int i = 0; i < grid.grid.length; i++){
            for(int j = 0; j < grid.grid[0].length; j++){
                if(grid.grid[i][j].getBlockValue()==0){
                    return true;
                }                
            }
        } 
        return false;
    }
    
    public void computerMove(){
        moveNum++;
        if(moveNum>=1){
            try{
                int[] move = findBestMove();
                grid.grid[move[0]][move[1]].setBlockValue(2);
            }catch (Exception e){
                randomMove();
            }
        }else randomMove();
        checkWin();
        repaint();
    }
        
    public void randomMove(){
        Random rand = new Random();
        int x;
        int y;
        Boolean success = false;
        while(!success){
                x = rand.nextInt(3);
                y = rand.nextInt(3);
                if(grid.grid[x][y].getBlockValue()==0){
                    grid.grid[x][y].setBlockValue(2);
                    success = true;
                }
            }
    }
    
    public int[] findBestMove()throws Exception{
        int[] pos = new int[2];
        int[] nums = new int[3];
        for(int i = 0; i < grid.grid.length; i++){
            nums = new int[3];
            for(int j = 0; j < grid.grid[0].length; j++){
                nums[j] = grid.grid[i][j].getBlockValue();
            }
            if(nums[0]+nums[1]+nums[2]==4){
                for(int k = 0; k<nums.length; k++){
                    if(nums[k]==0){
                        pos[0]=i;
                        pos[1]=k;
                        return pos;
                    }
                }
            }
            if(nums[0]+nums[1]+nums[2]==2){
                for(int k = 0; k<nums.length; k++){
                    if(nums[k]==2)break;
                }
                for(int k = 0; k<nums.length; k++){
                    if(nums[k]==0){
                        pos[0]=i;
                        pos[1]=k;
                        return pos;
                    }
                }
            }
        } 
        for(int j = 0; j < grid.grid[0].length; j++){
            nums = new int[3];
            for(int i = 0; i < grid.grid.length; i++){
                nums[i] = grid.grid[i][j].getBlockValue();
            }
            if(nums[0]+nums[1]+nums[2]==4){
                for(int k = 0; k<nums.length; k++){
                    if(nums[k]==0){
                        pos[0]=k;
                        pos[1]=j;
                        return pos;
                    }
                }
            }
            if(nums[0]+nums[1]+nums[2]==2){
                for(int k = 0; k<nums.length; k++){
                    if(nums[k]==2)break;
                }
                for(int k = 0; k<nums.length; k++){
                    if(nums[k]==0){
                        pos[0]=k;
                        pos[1]=j;
                        return pos;
                    }
                }
            }
        }
        if(grid.grid[1][1].getBlockValue()==0){
            pos[0]=pos[1]=1;
            return pos;
        }       
        throw new Exception();
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
        moveNum++;
        if(gameState==1){
            if(e.getButton() == MouseEvent.BUTTON1){
                int x = e.getX()/300;
                int y = e.getY()/300;
                grid.grid[x][y].setBlockValue(1);
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
                moveNum = 0;
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
