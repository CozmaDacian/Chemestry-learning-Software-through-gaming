package utilz;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import main_game.Game;

public class Obiecte implements Serializable {
    
	public int aliniere;
	public int text_size,style;
	public Font font;
	public int tip,width,height;
	public Rectangle Hitbox;
	public BufferedImage obiect;
	private int x;
	private int y;
	public Obiecte(int tip,int x,int y) {
	 this.tip=tip;
	 this.setX(x);
	 this.setY(y);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	
}
