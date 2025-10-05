package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main_game.Game;
import main_game.GamePanel;
import states.GameState;

public class MouseInputs implements MouseListener,MouseMotionListener{

	
	private Game game;
	private GamePanel gamePanel;
	public MouseInputs(Game game) {
		this.game=game;
		gamePanel=game.getGamePanel();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(GameState.state) {
		case PAUSE_MENU:
		
			game.getPause().mouseMoved(e);
			break;
		}
				
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(GameState.state) {
		case MENU:
			break;
		case PLAYING:
			break;
		case QUIZ:
			game.getIntrebari().mouseClicked(e);
			break;
		case COMBAT:
			game.getCombat().mouseClicked(e);
			break;
		case INVENTAR:
			game.getInventar().mouseClicked(e);
			break;
		case REPORT:
			game.getReport().mouseClicked(e);
		case CARTE:
			game.getCarte().mouseClicked(e);
		break;
		case SKILLTREE:
			game.getTree().mouseClicked(e);
		break;
		case EDITARE:
			game.getEdit().mouseClicked(e);
			break;
		case DEAD:
			game.getDead().mouseClicked(e);
		break;
		case PAUSE_MENU:
			game.getPause().mouseClicked(e);
		break;
		case EXPLICATII:
			game.explicatii.mouseClicked(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(GameState.state) {
		case COMBAT:
			//if(game.combat.phase1==false) {
			
			//}
				default:
				break;
		}
		}
	

	@Override
	public void mouseExited(MouseEvent e) {
		switch(GameState.state) {
		case COMBAT:
			//if(game.combat.phase1==false) {
				
				default:
				break;
		}
		
	}

}
