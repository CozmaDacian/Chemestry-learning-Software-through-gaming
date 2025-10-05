package Nivel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main_game.Game;
import utilz.Load;

public class Handler {

	private Game game;
	private BufferedImage[] levelSprite;
	public Handler(Game game) {
		this.game=game;
		
		
		
	}
	private void ImportBack() {
		
	levelSprite=new BufferedImage[48];	
	for(int i=0;i<4;i++) {
	for(int j=0;j<12;j++) {
		
		int index=j*12+1;
	}
		
	}
	
	}
	
	public void draw(Graphics2D g) {
		//g.drawImage(levelSprite, 0, 0, null);
		
	}
	
}
