package Menu;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PauseButton extends Button {
	
        protected  boolean inside=false;
		protected int x, y, width, height;
		protected Rectangle bounds;
        String text;
		public PauseButton(int x, int y, int width, int height,String text) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			createBounds();
			this.text=text;
			
		}
        public void draw(Graphics g) {
        	g.setFont(new Font("Arial",Font.PLAIN,16));
        	
        	if(inside==true)
        	g.setColor(Color.black);
        	else 
        	g.setColor(Color.red);
        	g.drawString(text, x+width/3, y);
        	g.setColor(Color.WHITE);
        	g.fillRect(x, y, width, height);
        }
        
        private void mouseEntered() {
           	
        }
		private void createBounds() {
			bounds = new Rectangle(x, y, width, height);
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

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public void setBounds(Rectangle bounds) {
			this.bounds = bounds;
		}

	
}