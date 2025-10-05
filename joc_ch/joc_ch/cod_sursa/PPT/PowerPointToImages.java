package PPT;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import main_game.Game;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
public class PowerPointToImages {

    
    		 
    	    public static void create_phothos(String file_name,int nivel,int numar_carte)
    	        throws IOException
    	    {
    	 
    	        // create an empty presentation
    	        File file = new File(file_name);
    	        XMLSlideShow ppt
    	            = new XMLSlideShow(new FileInputStream(file));
    	 
    	        // get the dimension and size of the slide
    	        Dimension pgsize = ppt.getPageSize();
    	        List<XSLFSlide> slide = ppt.getSlides();
    	        BufferedImage img = null;
    	 
    	        System.out.println(slide.size());
    	 
    	        for (int i = 0; i < slide.size(); i++) {
    	            img = new BufferedImage(
    	                pgsize.width, pgsize.height,
    	                BufferedImage.TYPE_INT_RGB);
    	            Graphics2D graphics = img.createGraphics();
    	 
    	            // clear area
    	            graphics.setPaint(Color.white);
    	            graphics.fill(new Rectangle2D.Float(
    	                0, 0, pgsize.width, pgsize.height));
    	 
    	            // draw the images
    	            slide.get(i).draw(graphics);
    	            FileOutputStream out = new FileOutputStream(
    	            utilz.Constante.path_name+"/Carte/"+"Level"+nivel+"/"+numar_carte+"/"+"Pagini/"+ i + ".png");
    	            javax.imageio.ImageIO.write(img, "png", out);
    	            ppt.write(out);
    	            out.close();
    	            System.out.println(i);
    	        }
    	        System.out.println("Image successfully created");
    	    }
    	
      
}
