package Coliziuni;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Obj {

	 public float x,y;
	 public int width,height;
	 public Rectangle hitbox;
	 
	 public Obj(float x,float y,int width,int height) {
		 this.x=x;
		 this.y=y;
		 this.width=width;
		 this.height=height;
		 
	 }
	 private void initHitbox() {
		 hitbox=new Rectangle((int) x,(int) y, width,height);
		 
	 }
   public void drawHitbox(Graphics2D g) {
	   //debuging;
	   g.setColor(Color.pink);
       g.drawRect(hitbox.x,hitbox.y,width,height);
   }

   public void updateHitbox() {
	   hitbox.x=(int) x;
	   hitbox.y=(int) y;
   }
   public Rectangle getHitbox() {
	   return hitbox;
   }
}
