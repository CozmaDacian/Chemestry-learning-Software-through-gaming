package main_game;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JTextField;
import inputs.KeyBoardInputs;
import inputs.MouseInputs;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage; 
import static main_game.Game.GAME_HEIGHT;
import static main_game.Game.GAME_WIDTH;
public class GamePanel extends JPanel {
     

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MouseInputs mouseInputs;
	public KeyBoardInputs keyBoardInputs;
	public ActionListener actionListener;
	
	public JTextField textField[];
    private Game game;
    int indImg=0;
	public GamePanel(Game game) {
		this.setGame(game);
		
		
	
		
		setLayout(null);
		mouseInputs=new MouseInputs(game);
	    keyBoardInputs = new KeyBoardInputs(game);
	    
	    addKeyListener(keyBoardInputs);
		addMouseListener(mouseInputs);
	
		setPanelSize();
	
		
		addMouseMotionListener(mouseInputs);
		
	    
		
	  
	}
	
	
	 
	 private void setPanelSize() {
		 Dimension size= new Dimension(GAME_WIDTH,GAME_HEIGHT);
		 
		 setPreferredSize(size);
		
		 
	 }
	
	 
		
	

	
   
  
   
  
  		
	
	
	
	

			   
			    
			    
		
	   

	 protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		   
	        	Graphics2D g2d = (Graphics2D) g; // Cast Graphics to Graphics2D
	        
		    getGame().draw(g2d); // Call draw() with Graphics2D
		    g2d.dispose(); // Dispose of the Graphics2D object
		}



	public Game getGame() {
		return game;
	}



	public void setGame(Game game) {
		this.game = game;
	}
		
	 
	  
	
	
	 
	 
	
	 

		
		    	
		    
	}


