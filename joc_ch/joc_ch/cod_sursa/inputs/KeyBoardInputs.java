package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main_game.Caracter;
import main_game.Game;
import main_game.GamePanel;
import states.GameState;

import static utilz.Constante.Directions.*;
import static utilz.Constante.PlayerConstansts.*;

public class KeyBoardInputs implements KeyListener {
   
	
	public boolean left=false,right=false;
	
	private GamePanel gamePanel;
	private Game game;
	
	public KeyBoardInputs(Game game) {
		this.game=game;
		gamePanel=game.getGamePanel();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(GameState.state) {
		case PLAYING:
			
			game.getPlay().keyTyped(e);
		
			break;
		
		}
	 }
	@Override
	public void keyPressed(KeyEvent e) {
		switch(GameState.state) {
		case MENU:
			break;
		case PLAYING:
			
		game.getPlay().keyPressed(e);
		break;
		case SKILLTREE:
			game.getTree().keyPressed(e);
		case QUIZ:
		
			break;
		case INVENTAR:
			game.getInventar().keyPressed(e);
			break;
		case COMBAT:
			game.getCombat().keyPressed(e);
			break;
		case CARTE:
			game.getCarte().keyPressed(e);
			
			break;
		case EDITARE:
			game.getEdit().keyPressed(e);
		case PAUSE_MENU:
			game.getPause().keyPressed(e);
		case REPORT:
			game.getReport().keyPressed(e);
		default:
			break;
		
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(GameState.state) {
		case MENU:
			break;
		case PLAYING:
			game.getPlay().keyReleased(e);
		case EDITARE:
			game.getEdit().keyReleased(e);
		case QUIZ:
			break;
		default:
			break;
		
		
	}

	
	}
	
}
	


