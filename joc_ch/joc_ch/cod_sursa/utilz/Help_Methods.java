package utilz;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import main_game.Enemies;
import main_game.Game;

public  class Help_Methods {
     
	
	
	
	
	private static Rectangle GetTileHitbox(int index,int x,int y) {
		
		
		// valoare =20
		
		// 64 64
		
		
		// offset_inceput_x of
		
		
		
		// x=x+offset_inceput_x;
		// y=y+offset_inceput_y;
		int yoffset=0;
		int xoffset=0;
		Rectangle Hitbox=new Rectangle();
		int height=64;
		int width=64;
		switch(index) {
		case 44:
			yoffset=20;
			xoffset=20;
			width=20;
			height=height-yoffset;
		break;
		
		case 45:
			yoffset=20;
			xoffset=20;
			width=20;
			height=30;
		break;
			
		case 46:
			yoffset=20;
			xoffset=20;
			width=20;
			height=30;
		break;
			
		case 50:
			yoffset=20;
			xoffset=20;
			width=20;
			height=30;
		break;
		
		
		
	    
		
		}
		if(yoffset>0 || xoffset>0)
		System.out.println(x+" "+xoffset);
		return new Rectangle(x+xoffset,y+yoffset,width,height);
		
	}
	
public static void update_hitbox_glont(int xspeed,int yspeed,Rectangle Hitbox_glont,int x_atacat,int y_atacat) {
	
		
		int x_glont=Hitbox_glont.x;
		int y_glont=Hitbox_glont.y;
	   

		
		
		
		
		
		if(x_glont<x_atacat) {
			
		
		if(x_glont+xspeed<x_atacat) {
		
		x_glont+=xspeed;
		}
		else
	    x_glont=x_atacat;
		}
		else
		if(x_glont>x_atacat) {
			if(x_glont-xspeed>x_atacat) {
				x_glont-=xspeed;
			}
			else
				x_glont=x_atacat;
		}
		
		// updatam pozitiile glontului 
		if(y_glont<y_atacat) {
			
			
			if(y_glont+yspeed<y_atacat) {
			
			y_glont+=yspeed;
			}
			else
		    y_glont=y_atacat;
			}
			else
			if(y_glont>y_atacat) {
				if(y_glont-yspeed>y_atacat) {
					y_glont-=yspeed;
				}
				else
					y_glont=y_atacat;
			}
		
		Hitbox_glont.setLocation(x_glont, y_glont);
	}
	
	
	public static boolean CanMoveHere(float x,float y,int width,int height,int[][] levelData,Rectangle Hitbox) {
		
		if(!IsSolid(x,y,levelData,Hitbox)) 
			if(!IsSolid(x+width,y+height,levelData,Hitbox))
				if(!IsSolid(x+width,y,levelData,Hitbox))
					if(!IsSolid(x,y+height,levelData,Hitbox))
		return true;
		
		return false;
					
			
		
		
	} 
	public static  void update_enemy_poz(Enemies enemy,int xfinal,int yfinal,int xspeed,int yspeed) {
		// Tinand cont de pozitia inamicului si de pozitia finala aducfe caracterul cat mai aproape de 
		// aceea poztitie
		
		
		int xen=(int) enemy.getX();
		int yen=(int) enemy.getY();
		if(yen>yfinal)
		{
			if(yen-yspeed>yfinal)
				yen=yen-yspeed;
			else
				yen=yfinal;
		}
		else
	    if(yen<yfinal) {
	    	if(yen+yspeed<yfinal)
	    		yen+=yspeed;
	    	else
	    		yen=yfinal;
	    }
		
		if(xen>xfinal)
		{
			if(xen-xspeed>xfinal)
				xen=xen-xspeed;
			else
				xen=xfinal;
		}
		else
	    if(xen<xfinal) {
	    	if(xen+xspeed<xfinal)
	    		xen+=xspeed;
	    	else
	    		xen=xfinal;
	    }
		// La final updatam inamicul
		enemy.setX(xen);
		enemy.setY(yen);
		
	}
	
	
	
	public static int GetScreenY(int y) {
		
	    if(y>Game.GAME_HEIGHT)
	    	return 2;
	    else
	    	return 1;
		
	}
	 
	public static boolean IsEntityOnFloor(Rectangle hitbox,int[][] leveldata) {
		
		if(!IsSolid(hitbox.x,hitbox.y+hitbox.height+1,leveldata,hitbox)) 
			if(!IsSolid(hitbox.x+hitbox.width,hitbox.y+hitbox.height+1,leveldata,hitbox))
				return false;
			
		return true;
		
	}
	
	public static int GetEntityPosNextToWall(Rectangle hitbox,float xSpeed) {
	   
		int currentTile=(int) hitbox.x/Game.TILES_SIZE;
		if(xSpeed>0) {
			//Right
			 return (int) ((hitbox.x + hitbox.width) / Game.TILES_SIZE);
		}
		else
		{
			//Left
			return currentTile*Game.TILES_SIZE;
		}
	}
	

	
	public static int GetEntityYUnderRoofOrAbove(Rectangle hitbox,float airSpeed) {
		
	
		
		
		int currentTile=(int)(hitbox.y/Game.TILES_SIZE);
		if(airSpeed>0) {
			//Falling- touching floor
			int tileYpos=currentTile*Game.TILES_SIZE;
			//int yOffset=(int)(Game.TILES_SIZE-hitbox.height);
			return tileYpos;
		}
		else
		{
			return currentTile*Game.TILES_SIZE;
		}
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
	private static boolean check_if_solid(int value) {
		        if(value>=53 || value<0)
		        	return true;
		        			
		        if(value!=10 && value!=12 && value!=8 && value!=9   && 
		           value!=19 && value!=20 && value!=21 && value!=30 &&
		           value!=31 && value!=32 && value!=41 && value!=42
		           
		        		
		        		)
     return true;
     return false;
	}
	private static boolean IsSolid(float x,float y,int[][] levelData,Rectangle hitbox_entitity) {
		
		if(x<0 || x>=Game.TOTALSIZE) {
			return true;
		}
		if(y<-Game.TOTAL_HEIGHT_SIZE/2 || y>Game.TOTAL_HEIGHT_SIZE)
			return true;
		
		
		
	
		
	    
		
		
	  
		
		float xIndex=x/Game.TILES_SIZE;
		float yIndex=y/Game.TILES_SIZE;
		
		
		
		//System.out.println("Asta e valoarea"+" "+yIndex);
		
		
		int value=levelData[(int) yIndex][(int) xIndex];
	    
		
		
		
		Rectangle hitbox=GetTileHitbox(value,(int) xIndex*Game.TILES_SIZE,(int) yIndex*Game.TILES_SIZE);
	     
	         if(value==44)
	         System.out.println(hitbox+" "+x+" "+y);
	        if(value==44 || value==45) {
			if(hitbox.contains(x,y))
				return true;
			else
				return false;
		
		}
	        else
	        	return check_if_solid(value);
	}
		
		
	}
	
	
	

