package main_game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Entitati.Entity;
import Stats.Player_stats;
import utilz.Pair;

public class Bars {
    
	
	
	 private BufferedImage hearth,engergy_bar;
	  
	 private int y_offset_inima=15;
	 private int width_health_bar,x_offset_inima=51,height_health_bar;
	 private int height_energy_bar,width_energy_bar,x_offset_energy=22,y_offset_energy=15;
	 private Bars bars;
	 private Color color;
     private Entity entitate;
     private int offset_bari_y=45,offset_bari_x=13;
     private int x, y;
     private Player_stats stats;
     private BufferedImage health_bar,energy_bar;
     public Bars(int x,int y,int width_bar_health,int height_bar_health,int width_energy_bar,int height_energy_bar,Player_stats stats) {
    	 this.x=x;
    	 this.y=y;
    	 this.setStats(stats);
    	 this.width_health_bar=width_bar_health;
    	 this.height_health_bar=height_bar_health;
    	 this.height_energy_bar=height_energy_bar;
    	 this.width_energy_bar=width_energy_bar;
         health_bar=this.loadImageFromFile(utilz.Constante.path_name+"hp.png");	 
         energy_bar=this.loadImageFromFile(utilz.Constante.path_name+"stamina.png");
     }
	 
     
     public double Get_Health_Percentage() {
    	 int total_health=getStats().getTotal_health();
    	 int remaining_health=getStats().getRemaining_health();
    	 
    	 double percentage=remaining_health*1.0/total_health;
    	 
    	 
    	 return percentage;
    	 
     }
     public double Get_Energy_Percentage() {
    	 int total_energy=getStats().getTotal_Stamina();
    	 int remaining_energy=getStats().getRemaining_Energy();
       double percentage=remaining_energy*1.0/total_energy;
    	 
    	 
    	 return percentage;
     }
     public void draw_health_bar(Graphics g) {
    	    // Calculate health percentage
    	    double percentage = this.Get_Health_Percentage();
    	    // Calculate width of health bar to draw
    	    int x_drawing = (int) (percentage * this.width_health_bar);

    	    // Draw health bar background
    	    g.drawImage(health_bar, x, y, null);

    	    // Fill health bar based on percentage
    	    g.setColor(Color.RED);
    	    g.fillRect(x + x_offset_inima, y + y_offset_inima, x_drawing, this.height_health_bar);

    	    // Draw remaining health text
    	    g.setColor(Color.BLACK);
    	    g.setFont(new Font("Tahoma", Font.BOLD, 18));
    	    String text = getStats().getRemaining_health() + " / " + getStats().getTotal_health();
    	    g.drawString(text, x + x_offset_inima + width_health_bar / 4, y + y_offset_inima + height_health_bar - 2);
    	}

    	public void draw_energy_percentage(Graphics g) {
    	    // Calculate energy percentage
    	    double percentage = Get_Energy_Percentage();
    	    // Calculate width of energy bar to draw
    	    int x_drawing = (int)(percentage * this.width_energy_bar);

    	    // Draw energy bar background
    	    g.setColor(Color.YELLOW);
    	    g.drawImage(energy_bar, offset_bari_x + x, offset_bari_y + y, null);
    	    
    	    // Draw energy bar based on percentage
    	    int x_start = offset_bari_x + x + x_offset_energy;
    	    int y_start = y + y_offset_energy + offset_bari_y;
    	    g.fillRect(x_start, y_start, x_drawing, this.height_energy_bar); 
    	    
    	    // Draw remaining energy text
    	    g.setColor(Color.BLACK);
    	    g.setFont(new Font("Tahoma", Font.BOLD, 14));
    	    String text = getStats().getRemaining_Energy() + " / " + getStats().getTotal_Stamina();
    	    int x_text = x_start + this.width_energy_bar / 4;
    	    int y_text = y_start + this.height_energy_bar;
    	    g.drawString(text, x_text, y_text);
    	}
    	 
     
     
     
public static BufferedImage loadImageFromFile(String filePath) {
    BufferedImage img = null;
    try {
        File file = new File(filePath);
        if (file.exists()) {
            img = ImageIO.read(file); // Read the image from the file path
        } else {
            throw new IOException("File not found: " + filePath);
        }
    } catch (IOException e) {
        e.printStackTrace(); // Handle or log the exception
        // Return a default image or handle the error accordingly
        // For example:
        // img = getDefaultImage(); // Provide a default image
    }
    return img;
}


public Player_stats getStats() {
	return stats;
}


public void setStats(Player_stats stats) {
	this.stats = stats;
}
	 
}
	 
	 

  
	 
	 
	
		  
	  
	
	

