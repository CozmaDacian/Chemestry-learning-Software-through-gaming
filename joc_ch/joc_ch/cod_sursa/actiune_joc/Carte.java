package actiune_joc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import PPT.PowerPointToImages;
import main_game.Game;
import states.GameState;
import states.metode;
import main_game.Caracter;
import utilz.Constante.ActiuniCarte;
import utilz.Constante.ActiuniCarte.*;
public class Carte implements metode {
    
	private Rectangle NextPage=new Rectangle(), LastPage=new Rectangle();
	public BufferedImage carte[]=new BufferedImage[7];
	private BufferedImage info[][][]=new BufferedImage[15][5][50];
	public int ind_carte=0;
    private PowerPointToImages translator=new PowerPointToImages();
	private BufferedImage current;
	private Game game;
	int tick=0;
	int animatie=0;
	boolean data=false;
	int ActCarte=ActiuniCarte.STATIC;
    private  int NrPagini[][]=new int[15][5];
	public int pagina_curenta=0;
	private Boolean Stg=false,Dr=false;  
	private Map<Color,Color>H=new HashMap<>();
	public Carte(Game game) {
		this.game=game;
		
		H.put(new Color(202,167,114),Color.black);
		H.put(new Color(171,141,97),Color.black);
		H.put(new Color(164,135,93),Color.black);
		H.put(new Color(173,143,98),Color.black);
		H.put(new Color(177,146,100),Color.black);
		H.put(new Color(165,136,94),Color.black);
		H.put(new Color(191,158,108),Color.black);
		H.put(new Color(147,121,83),Color.black);
		H.put(new Color(215,177,122),Color.black);
		current=game.loadImageFromFile(utilz.Constante.path_name + "background.jpg");
		NextPage.x=1143;
		NextPage.y=581;
		NextPage.height=67;
		NextPage.width=84;
		LastPage.x=325;
		LastPage.y=581;
		LastPage.height=67;
		LastPage.width=84;
		setSprite("/Carti.png");
	    for(int i=1;i<=utilz.Constante.nr_nivele;i++)
	    	{
	    	this.InitializeInfo(i, 1);
	    	this.ImportPhoto(i,1);
	    	
	    	}
	    	
	  
	}
	public void ImportPhoto(int nr_nivel,int book_number) {
		String directory_name=utilz.Constante.path_name+"Carte/Level"+nr_nivel+"/"+book_number+"/Pagini/";
		File folder=new File(directory_name);
		File[] files=folder.listFiles();
		int index=0;
		int poza_curenta=0;
		for(File file:files) {
			String file_name=directory_name+poza_curenta+".png";
			BufferedImage Temporary;
			poza_curenta++;
			Temporary=game.loadImageFromFile(file_name);
			Temporary=game.getCaracter().resizeImage(Temporary,1000,600);
			//Pagina 2 desen 531,15,445,544;
			//Pagina 1 desen 16,15,445,544;
		
			info[nr_nivel][book_number-1][index+1]=Temporary.getSubimage(26,22,445,544);
			info[nr_nivel][book_number-1][index+2]=Temporary.getSubimage(531,22, 435, 544);
			index=index+2;
		}
		NrPagini[nr_nivel][book_number-1]=index;
	}
	public void InitializeInfo(int nr_nivel,int book_number) {
		
		
		String ppt_name=new String();
		String directory_name=utilz.Constante.path_name+"Carte/Level"+nr_nivel+"/"+book_number+"/";
		File folder=new File(directory_name);
		if(folder.exists() && folder.isDirectory()) {
			
			File[] files=folder.listFiles();
			if(files!=null) {
				
				for(File file:files) {
					
					String file_name=file.getName();
					
				    int last_index=file_name.lastIndexOf(".");
				    
				    if(last_index>0) {
				    	String extension=file_name.substring(last_index);
				    	
				    	if (extension.equals(".pptx") || extension.equals(".ppt") || extension.equals(".pps") || extension.equals(".ppsx"))  {
				    		System.out.print(ppt_name);
				    		ppt_name=file_name;
				    	break;
				    	}
				    	
				    }
				}
			}
		
			
		String path_file=directory_name+ppt_name;
		File ppt=new File(path_file);
		System.out.println(path_file);
		if(ppt.exists()) {
			
		
			try {
				
				
				translator.create_phothos(path_file, nr_nivel, book_number);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
		}
		else
			System.out.println("NUEXISTAAAAAAAAAAAAAAA");
	}
	public BufferedImage updatePhoto(BufferedImage originalImage) {
		

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

	            if (H.get(culoare1) != null && d1<originalImage.getHeight() && d1<originalImage.getWidth()) {
	                culoare2 = H.get(culoare1);
	            } else {
	                culoare2 = culoare1;
	            }

	           originalImage.setRGB(e, d1, culoare2.getRGB());
	        }
	    }
	    return originalImage;
	}
	public void setSprite(String s) {
		
		BufferedImage img=null;
        InputStream is=getClass().getResourceAsStream(s);
      	
		
		try {
			
			int l=16;
			int ct=0;
			int width=1000,height=600;
			img= ImageIO.read(is);
			for(int i=0;i<=6;i++) {
			
				
			carte[i]=img.getSubimage(width*i,0,width,height);
			
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			System.out.print("HAI");
		}
		
	}
	
	public void draw(Graphics2D g) {
	    
		int nr_nivel=game.getPlay().nr_nivel;
		int fact=20;
	
		g.drawImage(current,0, 0, null);
		g.drawImage(carte[animatie],268,84,null);
		
			
		
		if(ActCarte==ActiuniCarte.STATIC) {
			if(info[nr_nivel][ind_carte][pagina_curenta+1]!=null)
			g.drawImage(info[nr_nivel][ind_carte][pagina_curenta+1], 320-fact, 117-fact, null);
			if(info[nr_nivel][ind_carte][pagina_curenta+2]!=null)
			g.drawImage(info[nr_nivel][ind_carte][pagina_curenta+2], 808-fact, 119-fact, null);
		    }
			else
			{
				if(Stg==true && animatie>5) {
					g.drawImage(info[nr_nivel][ind_carte][pagina_curenta+1], 320-fact, 117-fact, null);
				}
				if(Stg==true && animatie>3) {
					 g.drawImage(info[nr_nivel][ind_carte][pagina_curenta+2], 808-fact, 119-fact, null);
				
				}
				if(Dr==true && animatie<2) {
					 g.drawImage(info[nr_nivel][ind_carte][pagina_curenta+2], 808-fact, 119-fact, null);
				}
			if(Dr==true && animatie<5) {
			    	g.drawImage(info[nr_nivel][ind_carte][pagina_curenta+1], 320-fact, 117-fact, null);
			    }
				
			}
	}


	
	public void Updatetick() {
	
		int nr_nivel=game.getPlay().nr_nivel;
		if(ActCarte==ActiuniCarte.MOVING) {
		
		if(tick<7)
		tick++;
		else
	    {tick=0;
	    
	    if(Stg==true)
	    animatie--;
	    else
	    animatie++;	
	    }
	
		
		
		if(animatie>6 || (animatie<=0 && Dr==false))
		{tick=0;animatie=0;ActCarte=ActiuniCarte.STATIC;
		
		
		if(Dr==true) {
			pagina_curenta+=2;
		}
		else
			pagina_curenta-=2;
		
		if(NrPagini[nr_nivel][ind_carte]<pagina_curenta) {
			pagina_curenta=0;
			GameState.setCurrentOption(GameState.INVENTAR);
		}
		if(pagina_curenta<0) {
		pagina_curenta=0;
		GameState.setCurrentOption(GameState.INVENTAR);
		}
		
		Stg=false;
		Dr=false;
			
		}
		}
		
	}
	
	
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	   int x=e.getX();
	   int y=e.getY();
	   
	   if(NextPage.contains(x, y)==true) {
		   
		 ActCarte=ActiuniCarte.MOVING;
		  Dr=true;
		  animatie=0;
	   }
	   
	   if(LastPage.contains(x, y)==true) {
		   ActCarte=ActiuniCarte.MOVING;
		   Stg=true;
		   animatie=6;
	   }
	   
		   
		   
	   
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			GameState.state=GameState.INVENTAR;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
