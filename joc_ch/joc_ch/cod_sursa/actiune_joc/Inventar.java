package actiune_joc;

import static utilz.Constante.PlayerConstansts.GetSprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyPair;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main_game.Game;
import states.GameState;
import states.metode;

public class Inventar implements metode ,Serializable {

	private boolean great_restore=false;
	public BufferedImage back,cback,use;
	public int lvlData[][]=new int[6][6];
	public Rectangle Items[][]=new Rectangle[6][6];
	private Rectangle UseButton=new Rectangle(795,485,263,132);
	
	public Map<Integer,BufferedImage> linker = new HashMap<>();
	public Map<Integer,String>textImagine=new HashMap<>();
	
	private int FrecventeItems[]=new int[21];
	private ArrayList<BufferedImage>Obiecte=new ArrayList<BufferedImage>();
	private Game game;
	private Rectangle Back=new Rectangle();
	private static final int distante=4;
	private static final int ITEMSX=270,ITEMSY=100;
	private static final int scale=80;
	private int Phase=0;
	private int  indicei=-1,indicej=0;
	private Color color,color1;
	private String CurrentText=new String();
    private BufferedImage equip;
	public BufferedImage caracter[]=new BufferedImage[3];
	public Inventar(Game game) {
		color=new Color(145,121,90);
		color1=new Color(13,63,152,255);
		this.game=game;
		Back.height=scale*7;
		Back.width=scale*11;
		Back.x=ITEMSX-20;
		Back.y=ITEMSY-20;
		use=game.loadImageFromFile(utilz.Constante.path_name + "Use_Button.png");
		int dist1=450;
	    
	   
	    cback=game.loadImageFromFile(utilz.Constante.path_name + "C_Back.png");
	    equip=game.setImage("/equip.png");
		for(int i=0;i<6;i++) {
		for(int j=0;j<6;j++) {
			Items[i][j]=new Rectangle();
		    Items[i][j].height=scale; Items[i][j].width=scale; Items[i][j].x=j*(scale+distante-2)+ITEMSX+dist1; Items[i][j].y=i*(scale+distante-2)+82;
		    lvlData[i][j]=-1;
		}
	
			
		}
	
		back=game.setImage("/background.jpg");
		ObjectsIndex(utilz.Constante.path_name + "Iteme/");
        
 		
		ObjectText();
		}
	private void ObjectsIndex(String nume ) {
       
		
		// Ia imaginile din fisireul de iteme si le pune intr-o mapa pentru acces;
		
		int nr=0;
       String fisier=nume;
       String subfisier,imagine;
       fisier=nume;
       
       for(int i=0;i<=20;i++) {
       subfisier=fisier+i+".png";   
       File imageFile = new File(subfisier);
       try {
			BufferedImage image = ImageIO.read(imageFile);
			linker.put(i, image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		
    	
      
		} 
		
				
	  }
		
	
	
	
	
	public void Add_items(int item) {
		if(getFrecventeItems()[item]==0) {
			AddObject(item);
		}
			getFrecventeItems()[item]++;
	
	}
	
	
	@Override
	public void draw(Graphics2D g) {
       
		//draws the squares on which the items will be stored
		//draws all the objcets in the inventory and if one item is clicked displays the description of the object
	 
		
		  g.drawImage(back, 0, 0, null);
	      g.setColor(color1); 
		  g.fillRect(Back.x, Back.y, Back.width, Back.height);
			for(int i=0;i<5;i++) {
		    for(int j=0;j<5;j++) {
		    int width=Items[i][j].width;
		    int height=Items[i][j].height;
		    int x=Items[i][j].x;
		    int y=Items[i][j].y;
		    
		    if(i==indicei && j==indicej)
		    g.setColor(new Color(77,84,82,255));
		    else
		    g.setColor(color);
		    g.fillRect(x, y, width,height);
		    	
		    }
				
			}
			
			DrawInventar(g);
		
		g.drawImage(use, UseButton.x, UseButton.y, null);
	     
		
		
	if(Phase==1) {
		
		
		 DrawDescriereInventar(g);
	 }
	}	
	
	
	private int FontLenght(String s,Font font,Graphics2D g) {
		
		   // Very very babuin solutie to the problem 

			
			FontMetrics fontMetrics=g.getFontMetrics();
			return fontMetrics.stringWidth(s);
			
		
			
			}

	private void DrawDescriereInventar(Graphics2D g) {
		
        
	
		
		Font font1=new Font("Pacific",Font.PLAIN,25);
	
		  
		   g.setColor(Color.LIGHT_GRAY);
		   String[]Words=CurrentText.split(" ");
		   g.setFont(font1);
		  
		  
		   int distantax=ITEMSX-10, distantay=420; 
		   int i=0;
		   
			g.fillRect(distantax-8, distantay-5, 465, 200);
		
		   for(String words:Words) {
		       words+=" ";
			   
			   i++;
			   g.setColor(Color.DARK_GRAY);
			   int distanta=FontLenght(words,font1,g);
			   if(distantax+distanta>600) 
				 {
					distantax=ITEMSX-10;
					distantay+=25;
				 }
			  
				   
			  g.drawString(words,distantax+15, distantay+30);
			  distantax+=distanta;   
			
			 
		   }
		
		 
	}
		
		
	
	private void DrawInventar(Graphics2D g) {
		
		
		// Deseneaza fiecare element din queue de obiecte in ordinea in  care au fost introduse 
		int Ind=0;
		for(int i=0;i<5;i++) {
	    
		for(int j=0;j<5;j++) {
		
		if(lvlData[i][j]!=-1) {
			
			
		    int x=Items[i][j].x;
		    int y=Items[i][j].y;
			
		    BufferedImage image=linker.get(lvlData[i][j]);
		    
		    g.drawImage(image, x, y, null);
		
		    
		    
		    if(FrecventeItems[lvlData[i][j]]>1) {
		    	g.setColor(new Color(60,60,60));
		    	g.setFont(new Font("Arial",Font.BOLD,20));
		    	int offsetx=65;
		    	int offsety=17;
		    	String text=Integer.toString(getFrecventeItems()[lvlData[i][j]]);
		    	g.drawString(text, x+offsetx, y+offsety);
		    }
		
		}
		
		
		}
		
		}
		
        g.drawImage(cback, 230+20,81, null);		
        g.drawImage(caracter[game.getCaracter().cul_car], 426-20,176-60, null);
	}
	
	public void AddObject(int tipObiect) {
		
		// Adauga obiectul pe prima pozitie nenula si il adauga in coada de obiecte;
		// imaginea pe care o vom adauga in coada de obiecte o cautam in mapa numita linker
		
		int i=0,j=0;
		
		while(lvlData[i][j]!=-1){
			
		j++;	
		if(j==5)
			{i++;j=0;}
		else
			if(i==5)
				break;
			
		}
		
		
		
		lvlData[i][j]=tipObiect;
	
			
		
		
		
		
		
	}
	
	public void removeObject(int i,int j) {
		/*
		# Scoate obiectu din lista de obiecte;
		# Permuta toate elementele circular spre stanga cu o pozitie;
		# Ultima pozitie de pe fiecare rand este initializata cu prima de pe randul urmator;
		# Ciclul contiunua pana la randul 5 care nu exista;
		 */
	
		int value=this.lvlData[i][j];
		
		
		if(value==utilz.Constante.Inventory_Constants.CARTE_1 || value==utilz.Constante.Inventory_Constants.CARTE_2 ||
		value==utilz.Constante.Inventory_Constants.CARTE_3 || value==utilz.Constante.Inventory_Constants.ARMURA_ALBASTRA ||
		value==utilz.Constante.Inventory_Constants.ARMURA_GRI || value==utilz.Constante.Inventory_Constants.DIAMONDS ||
		value==utilz.Constante.Inventory_Constants.ARMURA_VERDE)
		
			return;
		
		if(this.getFrecventeItems()[value]-1<=0) {
	    getObiecte().remove(linker.get(lvlData[i][j]));
		for(int j1=j;j1<5;j1++) {
			lvlData[i][j1]=lvlData[i][j1+1];
		}
		
	    for(int i1=i+1;i1<5;i1++) {		
		lvlData[i1-1][4]=lvlData[i1][0];
	    for(int j1=0;j1<5;j1++)
		lvlData[i1][j1]=lvlData[i1][j1+1];
		
	
		
		}
		}
		else
		{
	     getFrecventeItems()[value]--;		
		}
	    
		
		
	
		
		
		
		}

    private void ObjectText() {
	 /* # Citim fisierul cu descriptii obiecte
        # Fiecarui obiect care poate fi inserat in lvlData ii cream o cheie cu descriptia sa in textImagine
        # 
    
     */
	 File reader=new File("C:\\De ce eu Project X\\DescriptieObiecteInventar.txt");
	textImagine.put(-1,"Empty slot");
	try {
		 Scanner scanner=new Scanner(reader);
		 int i=0;
		 while(scanner.hasNextLine()) {
	     String s=scanner.nextLine();
	     
	     textImagine.put(i, s);
	     i++;
		 }
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("File Not Found");
	}
	
	
		 
		 
	 }
    	
    	
    	
	 
    
	
	
	
	public void render() {
		// TODO Auto-generated method stub
		// 842 518;
	}

   
	public void mouseClicked(MouseEvent e) {
    
		int x=e.getX();
		int y=e.getY();
		
		for(int i=0;i<5;i++){
	
		for(int j=0;j<5;j++) {
	
		if(Items[i][j].contains(x, y)) {
		Phase=1;
		indicei=i;
		indicej=j;
		CurrentText=textImagine.get(lvlData[i][j]);
		
			
		}
			
		}
			
		}
		if(this.UseButton.contains(x, y) && lvlData[indicei][indicej]!=-1 && Phase==1) {
			
			
			utilz.Constante.Inventory_Constants.Get_Action(lvlData[indicei][indicej],game);
		    this.removeObject(indicei, indicej);
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
	public boolean isGreat_restore() {
		return great_restore;
	}
	public void setGreat_restore(boolean great_restore) {
		this.great_restore = great_restore;
	}
	public int[] getFrecventeItems() {
		return FrecventeItems;
	}
	public void setFrecventa_item(int item,int number) {
		FrecventeItems[item]=number;
	}
	public void setFrecventeItems(int frecventeItems[]) {
		FrecventeItems = frecventeItems;
	}
	public ArrayList<BufferedImage> getObiecte() {
		return Obiecte;
	}
	public void setObiecte(ArrayList<BufferedImage> obiecte) {
		Obiecte = obiecte;
	}

}
