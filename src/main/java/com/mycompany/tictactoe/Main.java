/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tictactoe;

import javax.swing.JFrame;

/**
 *
 * @author Charles Fagan
 */
public class Main {
    
    public static void main(String[] Args){
        JFrame obj = new JFrame();
        PlayGame newGame = new PlayGame();
        obj.setBounds(10,10,900,950);
        obj.setTitle("TicTacToe");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(newGame);
    }
}
