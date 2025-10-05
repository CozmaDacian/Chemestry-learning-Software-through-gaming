package Menu;

import static utilz.Help_Methods.loadImageFromFile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main_game.Game;
import states.GameState;
import states.metode;
import utilz.Butoane_Game;

public class Start_Menu implements metode {
   public static String path_name=utilz.Constante.path_name+"MenuItems/";
   public  static BufferedImage PLAY_PRESS=loadImageFromFile(path_name+"play_press.png");
   public static BufferedImage PLAY=loadImageFromFile(path_name+"play.png");
   public static BufferedImage QUIT=loadImageFromFile(path_name+"quit.png");
   public static BufferedImage QUIT_PRESS=loadImageFromFile(path_name+"quit_press.png");
   public static BufferedImage EDIT=loadImageFromFile(path_name+"edit.png");
   public static BufferedImage EDIT_PRESS=loadImageFromFile(path_name+"edit_press.png");
   public static BufferedImage BACKGROUND=loadImageFromFile(path_name+"Start_Background.jpg");
   public static final int WIDTH_BUTOANE=400;
   public static final int HEIGHT_BUTOANE=75;
   public static final int X_BUTOANE=590,Y_PLAY=191,YQUIT=492,YEDIT=335;
   public Butoane_Game play=new Butoane_Game(PLAY_PRESS,PLAY);
   public Butoane_Game quit=new Butoane_Game(QUIT_PRESS,QUIT);
   public Butoane_Game edit=new Butoane_Game(EDIT_PRESS,EDIT);
   
   private Game game;
   public Start_Menu(Game game) {
	   this.game=game;
	   AddButtons();
   }
   public void AddButtons() {
	   
	 play.setBounds(new Rectangle(X_BUTOANE,Y_PLAY,WIDTH_BUTOANE,HEIGHT_BUTOANE));
	 
	 quit.setBounds(new Rectangle(X_BUTOANE,YQUIT,WIDTH_BUTOANE,HEIGHT_BUTOANE));
	 edit.setBounds(new Rectangle(X_BUTOANE,YEDIT,WIDTH_BUTOANE,HEIGHT_BUTOANE));
	   play.setVisible(true);
	   quit.setVisible(true);
	   edit.setVisible(true);
	   play.setInside(false);
	   edit.setInside(false);
	   quit.setInside(false);
	   quit.setEnabled(true);
	   edit.setEnabled(true);
	   play.setEnabled(true);
	   game.getGamePanel().add(edit);
	   game.getGamePanel().add(play);
	   game.getGamePanel().add(quit);
	   play.addMouseListener(new MouseAdapter() {
         
		   @Override
		   public void mouseEntered(MouseEvent e) {
			   if(play.isEnabled())
			   play.setInside(true);
		   }
		   public void mouseExited(MouseEvent e) {
			  
		       if(play.isEnabled())
			   play.setInside(false);
		      
		   }
		   public void mouseClicked(MouseEvent e) {
			   if(quit.isEnabled()) {
			   GameState.setCurrentOption(GameState.PLAYING);
			   quit.setEnabled(false);
			   edit.setEnabled(false);
			   play.setEnabled(false);
		      
			  game.getGamePanel().remove(quit);
			  game.getGamePanel().remove(edit);
			  game.getGamePanel().remove(play);
			   }
		   
		   }});
	   
	   edit.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
				
		    	if(edit.isEnabled())
				   edit.setInside(true);
			   }
			 public void mouseExited(MouseEvent e) {
				   
				   if(edit.isEnabled())
				   edit.setInside(false);
			   }
		    public void mouseClicked(MouseEvent e) {
		    	
		    	
		        if (edit.isEnabled()) {
		            int response = JOptionPane.showOptionDialog(
		                null,
		                "Do you want to introduce new files for 'intrebari' and 'explicatii'?",
		                "Choose Option",
		                JOptionPane.YES_NO_CANCEL_OPTION,
		                JOptionPane.QUESTION_MESSAGE,
		                null,
		                new String[]{"Use Default", "Select Files", "Cancel"},
		                "Use Default"
		            );
		            
		            String pathIntrebari =  utilz.Constante.path_name + "Level_Sprite/intrebari.txt";
		            String pathExplicatii = utilz.Constante.path_name + "Level_Sprite/explicatii.txt";
		          
		            
		            
		            if (response == 1) { // Select Files Option
		                JFileChooser fileChooser = new JFileChooser();
		                fileChooser.setDialogTitle("Select 'intrebari' file");
		                int returnValue = fileChooser.showOpenDialog(null);
		                if (returnValue == JFileChooser.APPROVE_OPTION) {
		                    pathIntrebari = fileChooser.getSelectedFile().getAbsolutePath();
		                }
		                
		                fileChooser.setDialogTitle("Select 'explicatii' file");
		                returnValue = fileChooser.showOpenDialog(null);
		                if (returnValue == JFileChooser.APPROVE_OPTION) {
		                    pathExplicatii = fileChooser.getSelectedFile().getAbsolutePath();
		                }
		            }
		            
		            if (response != 2) { // If not cancelled
		                String editorPath = utilz.Constante.path_name + "editor/aplicatie/ChemEditorApp.exe";
		                try {
		                    ProcessBuilder pb = new ProcessBuilder(editorPath, pathIntrebari, pathExplicatii);
		                    pb.start();
		                } catch (IOException ex) {
		                    JOptionPane.showMessageDialog(null, "Error running the editor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            }
		        }
		    }
		});

	   quit.addMouseListener(new MouseAdapter() {
	         
		   @Override
		   public void mouseEntered(MouseEvent e) {
			  if(quit.isEnabled())
			   quit.setInside(true);
		   }
		   public void mouseExited(MouseEvent e) {
			   if(quit.isEnabled())
			   quit.setInside(false);
		   }
		   public void mouseClicked(MouseEvent e) {
			  if(quit.isEnabled())
				  System.exit(0);
		   }
		   
       });

	   
   }
   
   
   
@Override
public void draw(Graphics2D g) {
	// TODO Auto-generated method stub
	
	g.drawImage(BACKGROUND, 0, 0, null);
	play.draw(g);
	quit.draw(g);
	edit.draw(g);
}
@Override
public void render() {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
   
   
   
}
