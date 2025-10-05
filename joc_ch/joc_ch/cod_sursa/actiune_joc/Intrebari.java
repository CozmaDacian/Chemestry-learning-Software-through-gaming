package actiune_joc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import inputs.KeyBoardInputs;
import main_game.Bars;
import main_game.Caracter;
import main_game.GamePanel;
import states.GameState;
import states.metode;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.AttributedString;

public class Intrebari implements metode{
       
	    public boolean second_chance=false;
	    public int damage_inamic_raspuns_corect=0;
	    public boolean full_recharge_energy=false;
	    public boolean reroll=false;
	    private int noroc=0;
	    public boolean sendPr=false;
	    public boolean sendPr1=false;
        public ArrayList<String>Intr[][];
	    public ArrayList<String>Rasp[][];
	    public int intrebari_raspunse=1;
	    public int intrebari_corecte=1;
	    private String nume_fisier;
	    private Rectangle[]Raspunsuri=new Rectangle[4]; 
	    public BufferedImage OZNS[]=new BufferedImage[6];
	    public String[] s=new String[10];
        private Rectangle send=new Rectangle();
        public Queue<Integer> queue = new LinkedList<>();
        public BufferedImage back_quiz;
	   private Caracter caracter;
        private int Nrintreb=0;
        private int intrebare=1;
	    public GamePanel panel;
	    private int[] nr_intreb;
	    public BufferedImage quiz;
	    private int odata=0;
	    public Font font=new Font("Tahota",Font.PLAIN,20);
	    public int xquiz=145,oznanim=0;
	    public float tick=0, yozn=750,xozn=450,yquiz=1000;
	    private BufferedImage OZN;
	    private HashSet<Integer>asked=new HashSet<>();
	    private int ct=1;
	    private JTextArea textarea[]=new JTextArea[5];
	    
	    
	    private int restore_energy_based_on_luck() {
	    	int stamina_regen=0;
	    	if(noroc==0)
	    		return stamina_regen;
	    	Random random = new Random();
	    	
	    	if(noroc==1) {
	    	int x=random.nextInt(9);
	        if(x==5)
	         
	        stamina_regen=panel.getGame().getCaracter().getStatistici().getStamina_regen()/2;
	        
	    	}
	    	if(noroc==2) {
	    		int x=random.nextInt(3);
	    		if(x==1)
	    	    stamina_regen=panel.getGame().getCaracter().getStatistici().getStamina_regen()/2;
	    	}
	    	
	    	return stamina_regen;
	    }
	    public int GetRandom(int nr_nivel) {
			
	    	int max_size = this.nr_intreb[nr_nivel];
	    	Random rand=new Random();
	    	int random=rand.nextInt(max_size);
	    	System.out.println(random);
	    	if(asked.contains(random)==false) {
	    	    ct++;
	    		asked.add(random);
	    		return random;
	    		}
	    		else
	    	 if(ct<Nrintreb)
	    	return GetRandom(nr_nivel);
	    	
	    	return 5;
	    	}
	    	
	    	
      
	    
	    
	    
	    public Intrebari(GamePanel panel) {
	        nume_fisier="intrebari1";
	    	this.panel=panel;
	    
	    	
	    
	            
	    back_quiz=panel.getGame().setImage("/back_quiz.png"); 
        quiz=panel.getGame().getCaracter().setSpriteImage("/quiz.jpg");    
	    OZN=panel.getGame().getCaracter().setSpriteImage("/ozn.png");
	    System.out.println(OZN.getHeight());
	    System.out.println(OZN.getWidth());
	    nr_intreb = new int[utilz.Constante.nr_nivele+1];
        Initializare();
        for(int i=1;i<=utilz.Constante.nr_nivele;i++) {
	    ReadFile(i);
	    
	    Raspunsuri(i);
        nr_intreb[i] = this.Nrintreb; 
        }
	    
	    asked.add(1);
		asked.add(0);
    	OznANiM();
	    	
	    }
	    public void reinitialize(String filename) {
	        asked.clear();
	 		asked.add(0);
	    }
	    
	    
	    public void reset_ozn_pozitions() {
	    	    sendPr1=false;
			    tick=0; 
			    yozn=750;
			    xozn=450;
			    yquiz=1000;
			    this.oznanim=0;
	    }
	    
	    public void OznANiM(){
		    
	    	OZNS[0]=panel.getGame().getCaracter().setSpriteImage("/ozn.png");
	    	OZNS[1]=panel.getGame().getCaracter().setSpriteImage("/image.png");
	      
	    }
	    	
	 public void Reset() {
		 
		 tick=0; yozn=672; 
		 xozn=1230;
		 yquiz=-400;
	 }
	    
    private void Initializare() {
    	Intr=new ArrayList[4][100];
	    Rasp=new ArrayList[4][100];  
	   
        for(int j=1;j<=2;j++)
	    for(int i=0;i<100;i++)
          {
	       
           Intr[j][i]=new ArrayList<String>();	
           Rasp[j][i]=new ArrayList<String>();
          }
        
        for(int i=0;i<4;i++)
        {
        	
        	Raspunsuri[i]=new Rectangle();
       
        }
        
        int xstart=429;
        int ystart=519;
        int offsetx=36;
        int offsety=20;
        int width=320;
        int height=60;
        Raspunsuri[0].setBounds(xstart,ystart,width,height);
    	Raspunsuri[1].setBounds(xstart+offsetx+width,ystart,width,height);
        Raspunsuri[2].setBounds(xstart,ystart+height+offsety,width,height);
        Raspunsuri[3].setBounds(xstart+offsetx+width,ystart+height+offsety,width,height);
        send.setBounds(1187,434,140,160);
        
    }
    private String Search_extension(ArrayList<String> extension,String directory_name) {
        // Extensiile se afla in array extensions
    	// Cautam fiecare in folderul directory toate fisierele cu extensiile cautate si daca gasim vreunul il returnam
    	String file_name=new String();
    	
    	File folder=new File(directory_name);
    	
    	File[] files=folder.listFiles();
		int index=0;
		for(File file:files) {
			file_name=directory_name+file.getName();
		   
			index=file_name.lastIndexOf(".");
		    if(index>0) {
			String ext=file_name.substring(index);
		    
			if(extension.contains(ext))
				return file_name;
		    }
			
		}
    	
    	
    	return null;
    	
    	
    }
	public void ReadFile(int nr_nivel){
	try {
		String file_name=utilz.Constante.path_name+"Level_Sprite/Level"+nr_nivel;
		ArrayList<String> ext=new ArrayList<>();
		ext.add(".txt");
		String intrebari=this.Search_extension(ext, file_name+"/Intrebari/");
	
		Nrintreb =0;
		
		
		File myObj= new File(intrebari);
		Scanner myReader=new Scanner(myObj);
		int i=0;
		int j=1;
		while(myReader.hasNextLine()) {
	    s[i]=myReader.nextLine();
	    Intr[nr_nivel][j].add(s[i]);
	    if(i==4){
	    	i=0;
	    	j++;
	    	Nrintreb++;}
	    else
	    i++;
	    
		}
		myReader.close();
		for(i=1;i<=Nrintreb;i++) {
			 String filePath = file_name+"/Intrebari/Individual/"+i+".txt";
			 save_photo(filePath,nr_nivel,i);
		
	
	}
	}
	catch(FileNotFoundException e) {
		
		e.printStackTrace();
		 System.out.println("File not found.");
	}
	
    }
    private void save_photo(String filepath,int nr_nivel,int intrebare) {
    	 try {
	            // Create FileWriter object
	
	            FileWriter writer = new FileWriter(filepath);
	            PrintWriter print=new PrintWriter(writer);
	            for(int  j=0;j<=4;j++) {
	            print.println(Intr[nr_nivel][intrebare].get(j));
	            }
	            writer.close();
	  }
	
	  catch (IOException e) {
         System.err.println("Error writing to file: " + e.getMessage());
     }
	}
    
	private void Raspunsuri(int nr_nivel) {
    
    	for(int j=0;j<Nrintreb;j++) {
        for(int i=1;i<Intr[nr_nivel][j].size();i++) {
       String temp=Intr[nr_nivel][j].get(i);
      
       if(temp.contains("*")==true) {
    	  
       temp=temp.replace("*","");
        Intr[nr_nivel][j].set(i,temp);
    	Rasp[nr_nivel][j].add(temp);	
       
        }
        }
    	
    	}
    	
    
    	
    }
	private Boolean VerificareRaspunsuri(int nr_nivel) {
		// o coada care verifica daca elementele selectate corespund cu Raspunsurile
		// se apeleaza doar dupa ce send este activat 
		// 
		if(queue.size()!=Rasp[nr_nivel][getNrIntrebare()].size())
			return false;
		else
		if(queue.isEmpty()==true)
			return false;
		else
		while(queue.isEmpty()==false) {
		int x=queue.peek();
		
		if(Rasp[nr_nivel][getNrIntrebare()].contains(Intr[nr_nivel][getNrIntrebare()].get(x+1))==false){
			
		System.out.println("False");
		return false;
		}
		queue.poll();
			
		}
		System.out.println("True");
		return true;
	}
   
    
	public int FontLenght(String s,Font font,Graphics2D g) {
		
		  

			
			FontMetrics fontMetrics=g.getFontMetrics();
			return fontMetrics.stringWidth(s);
			
		
			
			}
    
	public void setWordWrap(Rectangle r, String temp, Graphics2D g, Font font) {
	    // Initialize variables and arrays
		g.setFont(font);
		
		//imparte cuvantul in functie de space
	    String[] words = temp.split(" ");
	   
	    // calculeaza spatul ramas pe linia [i]
	    int[] spaceLeft = new int[5];
	    // calculeaza numarul de numere de pe linie
	    // cand desenam pe ecran stringul si numarul de cuvinte este depasit marim y
	    int[] wordsPerLine = new int[5];
	   
	    /// Seteaza dimensiunile in functie de dreptunghi
	    int height = r.height;
	    int width = r.width;
	    int x = r.x;
	    int y = 0;
	    int distance = 0;
	    int lineCount = 1;
	    int wordCount = 0;
        ///
	    // Itereaza prin fiecare cuvant
	 
	    for (String word : words) {
	        wordCount++;
	        word=word+" ";
	       
	        int wordLength = FontLenght(word, font, g);
	        
	        // verifica daca cuvantul depaseste lungimea data
	        if (distance + wordLength > width) {
	            // calculeaza spatiul ramas si il divide cu 2 
	            spaceLeft[lineCount] = (width - distance) / 2;
	            distance = 0;
	            lineCount++;
	          
	            wordsPerLine[lineCount - 1] = wordCount - 1;
	            
	            distance += wordLength;
	        } else {
	             /// distanta este marita cu lungimea cuvantului  
	            distance += wordLength;
	        }
	    }
	    // calculeaza distanta ramasa pe ultima linie
	    spaceLeft[lineCount] = (width - distance) / 2;
	    wordsPerLine[lineCount]=wordCount;
	    
	    
	    
	    int ystart=0;
	    // Adjust y-coordinate to vertically center the text within the rectangle
	    if (lineCount == 1) {
	       ystart = height / 2;
	    } else if (lineCount == 2) {
	        ystart = height / 3;
	    } else if (lineCount == 3) {
	       ystart = 15;
	    }
	   
	    // Reset variables for drawing
	    wordCount = 0;
	    lineCount = 1;
	    distance = 0;
        y=0;
	    // Draw each word with calculated spacing for central alignment within each line
	    for (String word : words) {
	        int startDistance = spaceLeft[lineCount] + r.x;
	        wordCount++;
	        word=word+" ";
	        g.drawString(word, startDistance + distance, r.y + y+ystart);
	        
	        if (wordCount < wordsPerLine[lineCount]) {
	            distance += FontLenght(word, font, g);
	        } else {
	            distance = 0;
	            
	            lineCount++;
	            y += font.getSize();
	        }
	    }
	    
	}
	        
	
	
	public void draw(Graphics2D g) {
	     
		int nr_nivel=panel.getGame().getPlay().nr_nivel;
	
		if(sendPr1==false)
		{if(tick<255)
		{
	//// daca tick<255 deseneaza animatia ozn pe ecran
		panel.getGame().getCombat().draw(g);
		g.drawImage(quiz,145,(int) yquiz,null);
		g.drawImage(OZNS[oznanim], (int) xozn,(int) yozn, null);	
	
		}
		else
		{
		    // deseneaza elementele ce alcatuiesc backgroundul
		    g.drawImage(back_quiz,0,0,null);
		    g.drawImage(quiz,145,30,null);
		for(int i=0;i<=3;i++) {
			   int height=Raspunsuri[i].height;
			   int width=Raspunsuri[i].width;
			   int x=Raspunsuri[i].x;
			   int y=Raspunsuri[i].y;
			 
			   Rectangle r=Raspunsuri[i].getBounds();
			  
			 
			 
			   // daca raspunsul este cuprins in queueu il deseneaza cu roz altfel cu alb 
			   if(queue.contains(i))
			   g.setColor(new Color(211,141,224));
			   else
			   g.setColor(Color.white);
			   String Temp=Intr[nr_nivel][getNrIntrebare()].get(i+1);
			  
			   
			   // seteaza wraping la centru 
			   setWordWrap(r, Temp, g,new Font("Arial",Font.BOLD,16));
			   System.out.println();
			   
			 
		   }
		  g.setColor(Color.yellow);
		 
		  String Temp=Intr[nr_nivel][getNrIntrebare()].get(0);
		  Font font1=new Font("Arial",Font.PLAIN,28);
		setWordWrap(new Rectangle(460,400,600,112), Temp, g, font1);
		    
		int x=this.send.x;
		int y=this.send.y;
		int width=this.send.width;
		int height=this.send.height;
		g.drawRect(x, y, width, height);
	    }
		}

		
		
			
	}

	@Override
	public void render() {
		
		
		if(sendPr1==true) {
			
			
			
			
			
		{	
			   
			  
			}
		}		
		else
			{
			
			// animatia ozn daca ozn nu a aparut inca pe ecran 
			if(tick<255) {
			
			tick++;
			yozn=yozn-4;
			if(yquiz>4)
			yquiz=yquiz-4;
			
			// adauga si scade xozn pt a da senzatia unei rotatii 
			if(tick%17==0 && oznanim==0)
				xozn=xozn+30;
			else
				if(tick%17==0 && oznanim==1)
					xozn-=30;
			if(tick%35==0)
				oznanim++;
			if(oznanim==2)
			oznanim=0;
			if(oznanim==0) {
				xozn=(float) (xozn+0.5);
			}
			else
				xozn=(float) (xozn-0.5);
			
			}
		else
			yozn=-270;
	}
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
		
		int x=e.getX();
		int y=e.getY();
		for(int i=0;i<=3;i++) {
		if(Raspunsuri[i].contains(new Point(x,y))) {
		if(queue.contains(i)==false) {
			// cum coada in java este un linked list putem adauga/elimina foarte usor elementele respective
			queue.add(i);
		}
		else
		
			queue.remove(i);
			
		
		}
		
		
		 // reinitializeza variabilele pentru animatie
	      
        
	    // alege intrebarea din database random
	   
			
		}
		if(send.contains(x, y)==true) {
			int nr_nivel=panel.getGame().getPlay().nr_nivel;
			// daca send este apasat verifica daca raspunsul este corect si ii adauga stamina acestuia daca este cazul
			 // reinitializeza variabilele pentru animatie
		      
	        
		   
			 sendPr=VerificareRaspunsuri(nr_nivel);
			 if(sendPr==true) {
				 this.intrebari_corecte++;
				 
			 }
			 this.intrebari_raspunse++;
			panel.getGame().explicatii.corect=sendPr;
			GameState.setCurrentOption(GameState.EXPLICATII);
		     sendPr1=true;
		     tick=0;
		 
			 }
		}
		
	
	

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}






	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}






	public int getNoroc() {
		return noroc;
	}






	public void setNoroc(int noroc) {
		this.noroc = noroc;
	}
	public int getNrIntrebare() {
		return intrebare;
	}
	public void setINrIntrebare(int intrebare) {
		this.intrebare = intrebare;
	}
}
	
	
	

