package utilz;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CButton extends JLabel{
    
	
	public Color color_grph=new Color(32,41,38,255),color_grph1=new Color(77,84,82,255);
	public BufferedImage graphics,graphics1;
	private boolean clicked=false;
	
   
    private int cooldown=0;
    
    // Folosit pt a retine textul pe care il vom afisa cand mouse intra in componenta respectiva
    private String text[]=new String[6];
    
    
   
	@Override
	public void paint(Graphics g) {
		
		
	   super.paint(g);
		if(clicked==true)
			g.drawImage(graphics,0, getY(), null);
			else
		    g.drawImage(graphics1, 0,getY(),null);
		 
		
		
		 
		
			
		
	}
	
	
	 
	




	public boolean getClicked() {
		return clicked;
		
	}




	public void setClicked(boolean clicked) {
		this.clicked = clicked;
		
		if(clicked==false) {
			this.setBackground(color_grph1);
		}
		else
			this.setBackground(color_grph);
		repaint();
	}
	public void set_mouse_enterd() {
		repaint();
	}




	public int getCooldown() {
		return cooldown;
	}




	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
		System.out.print(cooldown);
		if(cooldown<=0)
		cooldown=0;
		repaint();
	}
}
