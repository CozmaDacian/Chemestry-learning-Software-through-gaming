package Menu;
import static utilz.Help_Methods.loadImageFromFile;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main_game.Game;
import states.GameState;
import states.metode;
public class Dead implements metode {

	public static BufferedImage dead=loadImageFromFile(utilz.Constante.path_name+"death_screen.jpg");
    private int tick=0;
    private int speed_dead_appear=120;
    private boolean reset_possible=false;
    private Rectangle reload=new Rectangle(500,400,350,100);
    private Rectangle exit=new Rectangle(500,600,350,100);
	private Game game;
   
	public Dead(Game game) {
    	this.game=game;
    }
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		g.drawImage(dead,0,0, null);
		if(reset_possible) {
		 g.setColor(new Color(106,7,7,255));
		 g.fillRect(reload.x, reload.y,350,100);
		 g.fillRect(exit.x,exit.y,350,100);
		 g.setFont(new Font("Arial",Font.PLAIN,36));
		 
		 g.setColor(Color.WHITE);
		 g.drawString("Reload",reload.x+350/3,reload.y+50);
		 g.drawString("Exit", exit.x+350/3, exit.y+50);
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		tick++;
		if(tick>this.speed_dead_appear)
		{
			tick=0;
			reset_possible=true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(reset_possible) {
			int x=e.getX();
			int y=e.getY();
			if(reload.contains(x,y)) {
				
				Deserialize_Class classa=new Deserialize_Class();
				Load_Last_Checkpoint point=classa.deserialize(utilz.Constante.path_name+"Save/1.ser");
				point.initialize_variables(game);
				game.getCaracter().resetDirections();
				game.getCaracter().setPlayerAction(utilz.Constante.PlayerConstansts.IDLE_GUN);
				GameState.setCurrentOption(GameState.PLAYING);
			}
			if(exit.contains(x,y)) {
				System.exit(0);
			}
		}
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
