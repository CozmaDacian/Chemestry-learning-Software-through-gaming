package main_game;

import java.awt.Rectangle;

public class CheckPoint {

	public Rectangle Hitbox=new Rectangle();
	
	public CheckPoint(int x,int y,int width,int height){
		Hitbox.x=x;
		Hitbox.y=y;
		Hitbox.height=height;
		Hitbox.width=width;
	}
	
   public boolean check_in_checkpoint(Rectangle caracter) {
	   
	   if(caracter.intersects(Hitbox))
		   return true;
	   return false;
   }
	
}
