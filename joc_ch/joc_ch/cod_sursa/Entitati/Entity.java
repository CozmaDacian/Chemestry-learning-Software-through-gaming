package Entitati;

import static utilz.Constante.PlayerConstansts.GetSprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;

import main_game.Game;

public class Entity implements Serializable {
	protected Map<Color,Color>Culori1=new HashMap<>();
    protected Map<Color,Color>Culori=new HashMap<>();
	protected Map<Integer,String>Foldere=new HashMap<>();
	protected Rectangle hitbox;
	protected float x;
	protected float y;
	protected float width=65;
	protected float height=50;
	public Game game;
	protected boolean movingdr=false;
	protected float gravity=0.04f*Game.SCALE_JUMP;
	public Entity(float x,float y,Game game) {
		this.x=x;
		this.y=y;
		this.game=game;
		hitbox=new Rectangle((int)x,(int)y,(int)width,(int)height);
		
	}
	
	public Rectangle getHitBox() {
		
		return hitbox;
	}
	protected void drawHitBox(Graphics2D g,int offsetX,int offsetY) {
		//debugging
		g.setColor(Color.red);
		g.drawRect(hitbox.x-offsetX,hitbox.y-offsetY,hitbox.width,hitbox.height);
		
	}
	
	public void updateHitBox () {
  
     hitbox.height=52;
     hitbox.width=40;
	
	hitbox.x=(int) x+hitbox.width/2;
	hitbox.width=20;
	
	 hitbox.y=(int)y;
	
	}
	
	
	
	
	 public static BufferedImage flipImageVertical(BufferedImage originalImage) {
	        int width = originalImage.getWidth();
	        int height = originalImage.getHeight();
	        BufferedImage flippedImage = new BufferedImage(width, height, originalImage.getType());

	        for (int y = 0; y < height; y++) {
	            for (int x = 0; x < width; x++) {
	                int rgb = originalImage.getRGB(width - x - 1, y);
	                flippedImage.setRGB(x, y, rgb);
	            }
	        }

	        return flippedImage;
	    }

	  public static BufferedImage resizeImage(BufferedImage originalImage, int newWidth, int newHeight) {
		  if(originalImage==null)
			  return null;
	      BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
	      Graphics2D g = resizedImage.createGraphics();
	      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	      g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
	      g.dispose();
	      return resizedImage;
	  }

	     public void init_culori() {
	    	   for(int i=-20;i<=20;i++) {

	    		   for(int j=-20;j<=20;j++) {

	    	  for(int e=-20;e<=20;e++) {



	    	   Culori.put(new Color(53+i,50+e,90+j,255), new Color(26,38,33,255));
	    	   Culori.put(new Color(103+i,113+e,124+j,255), new Color(52,88,45,255));
	    	   Culori.put(new Color(22+i,22+e,42+j,255), new Color(24,17,36,255));
	    	   Culori.put(new Color(42+i,50+e,56+j,255), new Color( 47,40,49,255));
	    	   Culori.put(new Color(208+i,113+e,24+j,255), new Color(132,118,135,255));
	    	   }
	    	   }
	    	}

	    	Culori1.put(new Color(26,38,33,255), new Color(12,73,186,255));
	    	Culori1.put(new Color(52,88,45,255), new Color(24,171,255,255));
	        Culori1.put(new Color(24,17,36,255), new Color(24,23,43,255));
	        Culori1.put(new Color(132,118,135,255), new Color(47,54,59,255));
	    }

	
	
	public void resizeSprite(int culori,double percentage,BufferedImage PlayerAnim[][][],int size, Vector<Integer>not) {

		  for(int k=0;k<=culori;k++) {


		  for(int i=0;i<=size;i++) {
			 if(!not.contains(i)) {
			  int animatii=utilz.Constante.PlayerConstansts.GetSprite(i);

			 for(int j=1;j<=animatii;j++) {
			int width=PlayerAnim[k][i][j].getWidth();
			int height=PlayerAnim[k][i][j].getHeight();

		    height=(int) (height*percentage);
		    width=(int)(width*percentage);
			PlayerAnim[k][i][j]=resizeImage(PlayerAnim[k][i][j],width,height);

			 }

		  }
		  }
		  }
	  }
	public void resizeEnemySprite(int culori,double percentage_width,double percentage_height,BufferedImage PlayerAnim[][][],int size, Vector<Integer>not) {

		  


		  for(int i=0;i<=size;i++) {
			 if(!not.contains(i)) {
			  int animatii=utilz.Constante.EnemyConstants.Robot.GetEnemy(i);

			 for(int j=1;j<=animatii;j++) {
			int width=PlayerAnim[culori][i][j].getWidth();
			int height=PlayerAnim[culori][i][j].getHeight();

		    height=(int) (height*percentage_height);
		    width=(int)(width*percentage_width);
			PlayerAnim[culori][i][j]=resizeImage(PlayerAnim[culori][i][j],width,height);

			 }

		  }
		  }
		  }
	  
	public BufferedImage setSpriteImage(String s) {

		BufferedImage d = null,img;
		 InputStream is=getClass().getResourceAsStream(s);
			try {
			 img= ImageIO.read(is);
			 d=img;
			 }catch(IOException e) {
				 e.printStackTrace();


	  }
			return d;

	}
	 public BufferedImage[][][] setSpriteAnimation(int k,String nume,String culoare,BufferedImage PlayerAnim[][][]) {

			BufferedImage d = null,img;
            // luam pozele din folderul nume+culuare_folder
			int nr=0;
	       String fisier=nume;
	       String subfisier,imagine;
	       fisier=nume+culoare+"/";

	        for(int i=0;i<=8;i++) {
	    	subfisier=fisier+Foldere.get(i)+"/";
              
	    	for(int j=1;j<=GetSprite(i);j++) {
            // citim imaginile
	    	imagine=subfisier+j+".png";
	     
	    	 File imageFile = new File(imagine);
	    	 try {
	    		    BufferedImage originalImage = ImageIO.read(imageFile);



	    		    BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());
                    // modificam culorile bazat pe cele din vectorul culori 
	    		    // returnam animatia de k
	    		    for (int e = 0; e < originalImage.getWidth(); e++) {
	    		        for (int d1 = 0; d1 < originalImage.getHeight(); d1++) {
	    		            int rgba = originalImage.getRGB(e, d1);
	    		            Color culoare2;

	    		            // Extract red, green, blue, and alpha components
	    		            int red = (rgba >> 16) & 0xFF;
	    		            int green = (rgba >> 8) & 0xFF;
	    		            int blue = rgba & 0xFF;
	    		            int alpha = (rgba >> 24) & 0xFF;

	    		            Color culoare1 = new Color(red, green, blue, alpha);

	    		            if (Culori.get(culoare1) != null && d1<originalImage.getHeight()-40) {
	    		                culoare2 = Culori.get(culoare1);
	    		            } else {
	    		                culoare2 = culoare1;
	    		            }

	    		            modifiedImage.setRGB(e, d1, culoare2.getRGB());
	    		        }
	    		    }
	    		    PlayerAnim[k][i][j] = originalImage;
	    		    PlayerAnim[k + 1][i][j] = modifiedImage;
                    // Cum avem doua culori o facem de 2 ori
	    		    BufferedImage mod=new BufferedImage(modifiedImage.getWidth(), modifiedImage.getHeight(), modifiedImage.getType());
	    		    for (int e = 0; e < originalImage.getWidth(); e++) {
	    		        for (int d1 = 0; d1 < originalImage.getHeight(); d1++) {
	    		            int rgba = modifiedImage.getRGB(e, d1);
	    		            Color culoare2;

	    		            // Extract red, green, blue, and alpha components
	    		            int red = (rgba >> 16) & 0xFF;
	    		            int green = (rgba >> 8) & 0xFF;
	    		            int blue = rgba & 0xFF;
	    		            int alpha = (rgba >> 24) & 0xFF;

	    		            Color culoare1 = new Color(red, green, blue, alpha);

	    		            if (Culori1.get(culoare1) != null && d1<originalImage.getHeight()-40) {
	    		                culoare2 = Culori1.get(culoare1);
	    		            } else {
	    		                culoare2 = culoare1;
	    		            }

	    		            mod.setRGB(e, d1, culoare2.getRGB());
	    		        }
	    		    }
	    		    PlayerAnim[k+2][i][j]=mod;

	    	 }catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}




	        }

	        }
	  return PlayerAnim;
	  }

	public boolean getMovingdr() {
		return movingdr;
	}

	public void setMovingdr(boolean movingdr) {
		this.movingdr = movingdr;
	}
}

	
