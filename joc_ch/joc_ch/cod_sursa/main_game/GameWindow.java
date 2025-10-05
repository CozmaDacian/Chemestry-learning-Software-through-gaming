package main_game;

import java.awt.Component;

import javax.swing.JFrame;

public class GameWindow {
 private JFrame jframe;
 
 public GameWindow(GamePanel gamePanel) {
	 jframe=new JFrame();
	
	 
	 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 jframe.add(gamePanel);
	 jframe.setLocation(500,50);
	 
	 
	 jframe.pack();
	 jframe.setResizable(false);
	 jframe.setVisible(true);
 }
}
