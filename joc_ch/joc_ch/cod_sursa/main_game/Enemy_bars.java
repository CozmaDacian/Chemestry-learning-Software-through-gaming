package main_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Enemy_bars {

	BufferedImage health;
	Enemies enemy;
	
	int height=13;
	int x_offset=44;
	int y_offset=15;
	int width=126;
	public Enemy_bars(Enemies enemy){
		
		this.enemy=enemy;
	    health=enemy.game.loadImageFromFile(utilz.Constante.path_name+"hpEnemy.png");
	    health=enemy.game.getCaracter().resizeImage(health, health.getWidth()-30, health.getHeight()-5);
	}
	
	public void draw_health_bar(Graphics g) {
		
		
		int x_start=(int) enemy.getX()-60;
		int y_start=(int) enemy.getY()-40;
		
		g.drawImage(health, x_start, y_start, null);
		g.setColor(Color.RED);
		
		int x_draw=(int) (this.Get_Health_Percentage()*this.width);
		
		
		g.fillRect(x_start+x_offset, y_start+y_offset, x_draw,height);
		 g.setColor(Color.BLACK);
 	    g.setFont(new Font("Tahoma", Font.BOLD, 18));
 	    String text =enemy.getStatistici().getRemaining_health() + " / " +enemy.getStatistici().getTotal_health();
 	    g.drawString(text, x_start + x_offset + width / 4, y_start + y_offset + height);
		
	}
	
	
	  public double Get_Health_Percentage() {
	    	 int total_health=enemy.getStatistici().getTotal_health();
	    	 int remaining_health=enemy.getStatistici().getRemaining_health();
	    	 
	    	 double percentage=remaining_health*1.0/total_health;
	    	 
	    	 
	    	 return percentage;
	    	 
	     }
	     
	
	
	
	
	
}
