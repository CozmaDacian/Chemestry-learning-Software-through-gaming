package actiune_joc;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.AttributedString;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import main_game.Game;
import states.GameState;
import states.metode;
import utilz.Pair;


    public class Skill_tree implements metode,Serializable{

    private boolean Click_Attributes=false;
	 private Map<Rectangle,String>Texte= new HashMap<>();
	private Game game;
	private int scale=90;
	private BufferedImage back;
	private Rectangle Learned=new Rectangle();
	private Rectangle Click[]=new Rectangle[25]; 
	private Pair<Integer,Integer>pair;
     int numar=0;
	public int Skill_Points=0;
	private int Conexiuni[]=new int[40],Frecvente[]=new int[40];
	
    private Map<Integer,String>Abilitati=new HashMap<>();
	public int Freq[]=new int[25] ;
	
	public Skill_tree(Game game){
    	this.game=game;
    	Conexiuni();
    	Init_Clicks();
    	readFile();
     
       Click[0]=new Rectangle();
     back=game.loadImageFromFile(utilz.Constante.path_name + "skl_back.png");
      numar=0;
      
	}
    
	
	private void Init_Clicks() {
		
		File myObj= new File("C:\\De ce eu Project X\\Sizes_Abilitati.txt");
		try {
			
		
			Scanner myReader=new Scanner(myObj);
		    int numar=1;
		    int frecventa=0;
		    Vector<Integer>Sizes = new Vector<Integer>();
		    Sizes.setSize(12);
		    
			while(myReader.hasNextInt()) {
				  int x=myReader.nextInt();
				  int y=myReader.nextInt()-20;
				Click[numar]=new Rectangle();
				Click[numar].setBounds(x,y,65,64);
				numar++;
				frecventa=0;
				Sizes.clear();
				}
			}
	     catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
	}
	
	
	private void Conexiuni() {
		File myObj= new File("C:\\De ce eu Project X\\conexiuni.txt");
		try {
			
		
			Scanner myReader=new Scanner(myObj);
			while(myReader.hasNextInt()) {
				int first=myReader.nextInt();
				int second=myReader.nextInt();
				
				this.Conexiuni[second]=first;
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    }
		
		
	}
	   
	
 
	
	
	
	
	
	

	private void readFile() {
		int nr=0;
		File myObj= new File("C:\\De ce eu Project X\\descriere_tree.txt");
		try {
			Scanner myReader=new Scanner(myObj);
			while(myReader.hasNextLine()==true && nr<24) {
				
			   nr++;
				String s=myReader.nextLine();
				
					
				if(s.charAt(1)==' ' ) {
				numar=s.charAt(0)-'0';	
				 s=(s.substring(2));
				}
				else {
			  
				{numar=(s.charAt(0)-'0')*10+(s.charAt(1)-'0');
			   s=s.substring(3);
				}
				}
			   
			   
				
			
			Texte.put(Click[numar],s);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private int FontLenght(String s,Font font,Graphics2D g) {
		
		 

			
			FontMetrics fontMetrics=g.getFontMetrics();
			return fontMetrics.stringWidth(s);
			
		
			
			}

	private void DrawDescriere(Graphics2D g,Rectangle R) {
		
		g.setColor(Color.white);
		int width=R.width-100;
		int height=R.height;
		int ystart=R.y+45;
		int xstart=R.x;
		
		String currenttext=Texte.get(Click[numar]);
		
		String words[]=currenttext.split(" ");
		Font font1=new Font("Pacific",Font.PLAIN,20);
		int distanta=0,holder_distanta;
		g.setFont(font1);
		for(String Words:words) {
	     Words=Words+' ';
	     holder_distanta=distanta;
		 distanta+=FontLenght(Words,font1,g);
		 if(distanta+50>width) {
		 distanta=0;
		 holder_distanta=distanta;
		 distanta+=FontLenght(Words,font1,g);
		 
		
		 ystart=ystart+30;
		 
		 	 
		 }
		 g.drawString(Words, xstart+holder_distanta,ystart );
		 
		 
		 }
			
		}
		 
	
		
  
			
			
		
		

		
 
		  
		   
   private Boolean binary_tree(int Nod) {
	 
	   if(Nod==1 || Nod==2 || Nod==3)
		   if(Frecvente[Nod]==0)
	           return true;
	   
	   if(Frecvente[Nod]==0 && Frecvente[Conexiuni[Nod]]==1)
	   return true;
	 else
	   return false;
	   
	   
   }
	
    
   public void drawCaract(Graphics2D g) {
      /*
	   int x=5;
	   g.setColor(Color.black);
	   g.setFont(new Font("Arial",Font.PLAIN,18));
	  String nr=Integer.toString(game.bars.getHealth_bar());
	   g.drawString(nr,1102,116+x);
      nr=Integer.toString(game.bars.getEnergy_Bar());
      g.drawString(nr, 1102, 165+x);
      nr=Integer.toString(game.bars.getDamage_Grenada());
      g.drawString(nr, 1067, 208+x);
      nr=Integer.toString(game.bars.getDamage_Concentrat());
      g.drawString(nr, 1060, 248+x);
      nr=Integer.toString(game.bars.getDmg_basic());
      g.drawString(nr, 1060, 285+x);
      nr=Integer.toString(game.bars.getDefense()+game.bars.getDefense());
   */
     // g.drawString(nr, 1072, 328+x);  
      }
	
	public void draw(Graphics2D g) {
		
	
    g.drawImage(back,0,0, null);
    drawCaract(g);
    if(numar!=0){	
    	
	
	    
		DrawDescriere(g,new Rectangle(200+5*scale+200,390,600,300));
    }      
	
	
	

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		 
		int x=e.getX();
		int y=e.getY();
		
		  for(int i=1;i<24;i++) {
		    if(Click[i].contains(x, y)==true)
		    {
		    	System.out.print(true);
		    	numar=i; break;
		    }
		  }
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_ESCAPE:
			GameState.setCurrentOption(GameState.PLAYING);
		
		}
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
