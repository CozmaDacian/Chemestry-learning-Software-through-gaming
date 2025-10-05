package Menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import Nivel.Level_Manager;
import main_game.Game;
import states.GameState;
import states.metode;

public class FinalReport implements metode  {
    Game game;
    private BufferedImage background;
	private int x_back=300,y_back=60;
	Vector<Point>poziti=new Vector<>();
    Vector<String> text=new Vector<>();
	public FinalReport(Game game){
		this.game=game;
		
		background=game.loadImageFromFile(utilz.Constante.path_name+"report.png");
	 this.addPoints();
	
	}
    public void addPoints() {
    	poziti.add(new Point(255,115));
    	poziti.add(new Point(210,150));
    	poziti.add(new Point(291,187));
    	poziti.add(new Point(245,224));
    	poziti.add(new Point(309,262));
    	poziti.add(new Point(439,298));
    	poziti.add(new Point(425,335));
    }
    public void addInfo(Level_Manager nivel) {
    	
    	text.add(Integer.toString( nivel.statistici.obiecte_colectate));
    	text.add(Integer.toString(nivel.statistici.obiecte_totale));
    	text.add(Integer.toString(nivel.statistici.enemies_defeated));
    	text.add(Integer.toString(nivel.statistici.total_enemies));
    	text.add(Integer.toString(game.getIntrebari().intrebari_raspunse-1));
    	text.add(Integer.toString(game.getIntrebari().intrebari_corecte-1));
    	float procent=game.getIntrebari().intrebari_corecte/game.getIntrebari().intrebari_raspunse*100;
        
    	text.add(Integer.toString((int)procent)+"%");
    }
	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		 game.getPlay().draw(g);
		 g.setColor(Color.white);
		 g.setFont(new Font("Times New Roman",Font.BOLD,28));
		 g.drawImage(background, x_back, y_back, null);
		 for(int i=0;i<poziti.size();i++) {
			 int x=poziti.get(i).x+x_back+20;
			 int y=poziti.get(i).y+y_back+44;
			 g.drawString(text.get(i), x, y);
		 }
	}
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Point e1 = new Point(e.getX(),e.getY());
		Rectangle back = new Rectangle(x_back,y_back,200,200);
		if (back.contains(e1)){
			GameState.state = GameState.PLAYING;
			
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		GameState.state = GameState.PLAYING;
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
