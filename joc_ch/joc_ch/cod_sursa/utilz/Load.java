package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Load {
     
	
	public static final String NIVEL="/back1.png";
	public static BufferedImage GetImage(String Img) {
		BufferedImage d = null,img=null;
		 InputStream is=Load.class.getResourceAsStream(Img);
			try {
			 img= ImageIO.read(is);
			 d=img;
			 }catch(IOException e) {
				 e.printStackTrace();
		
				
	  }
			return img;
			
	}	
		
	}
	
	
		
	
	

