package Menu;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import utilz.Constante;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import actiune_joc.Intrebari;
import main_game.Game;
import states.GameState;
import states.metode;
import utilz.Constante;

public class Explicatie implements metode{
    public boolean corect=false;
	private Intrebari quiz;
	private String[] intrebare=new String[5];
	private String[] explicatii=new String[5];
	 public ArrayList<String>Explicatii[][];
	private BufferedImage background_corect,background_incorect;
	private Font font=new Font("Arial",Font.PLAIN,18);
	private Rectangle explanaition_rect,intrebari_rect;
	private Rectangle Continue=new Rectangle(600,620,220,86);
	private BufferedImage continua;
	public Explicatie(Game game) {
		this.quiz=game.getIntrebari();
		continua=game.loadImageFromFile(utilz.Constante.path_name+"continue.png");
		background_corect=game.loadImageFromFile(utilz.Constante.path_name+"background_corect.jpg");
		background_incorect=game.loadImageFromFile(utilz.Constante.path_name+"background_incorect.jpg");
		intrebari_rect=new Rectangle(480,150,700,200);
		explanaition_rect=new Rectangle(480,400,700,200);
		this.Initialize_question();
		
		Explicatii=new ArrayList[4][100];
		for(int i=1;i<=3;i++) {
			for(int j=0;j<=99;j++)
				Explicatii[i][j]=new ArrayList<>();
			
			
		for(int j=1;j<=utilz.Constante.nr_nivele;j++)
		this.Initialize_explicatii(1);
		this.Initialize_explicatii(2);
	}
	}
	
	private void Initialize_explicatii(int nr_nivel){

	String file_path=utilz.Constante.path_name+"Level_Sprite/Level"+nr_nivel+"/Explicatii/";
	File File=new File(file_path);
    if(File.isDirectory()) {
    File folder[]=File.listFiles();	
    	int index=0;
    	int index_stop=0;
       
        
         for(File files:folder) {
             
             
        try {
        	
			
			++index;
			File searched=new File(file_path+index+".txt");
		   Scanner my_obj=new Scanner(searched);	
			Explicatii[nr_nivel][index] = new ArrayList<>();
			while(my_obj.hasNextLine()) {
				
				Explicatii[nr_nivel][index].add(my_obj.nextLine());
				
			}
			my_obj.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print(nr_nivel);
			System.out.print(index);
			
			e.printStackTrace();
		}	 
       
        }
        	 
        	 
    	}
    }
	
	
	
	private void Initialize_question() {
         
		int nivel=quiz.panel.getGame().getPlay().nr_nivel;
		int nr_intrebare=quiz.getNrIntrebare();
		for(int i=0;i<=4;i++) {
			intrebare[i]=quiz.Intr[nivel][nr_intrebare].get(i);
		   
		}
	}
		
	
	public int FontLenght(String s,Font font,Graphics2D g) {
			FontMetrics fontMetrics=g.getFontMetrics();
			return fontMetrics.stringWidth(s);
			}
	
	private int drawStringInRectangle(Graphics2D g,int indice,Rectangle area,int y_start,Font font,String text) {
		  
		  int left_margin=20;
		  int right_margin=20;
		  int up_margin=20;
		  int line_space=font.getSize();
		  if(indice!=-1)
		  text=indice+"."+text;
		String words[]=text.split(" ");
	    g.setFont(font);
	   
	    int x=area.x;
	   
	    int y=y_start;
	    
	    int width=area.width;
	  
	    int widht=area.width-right_margin;
	    int curent_lenght=left_margin;
	    for(String word: words) {
	    	word=word+" ";
	    	int distance=FontLenght(word,font,g);
	    	if(distance+curent_lenght<=width) {
	    		g.drawString(word, x+curent_lenght, y);
	    		curent_lenght+=distance;
	    	}
	    	else
	    	{
	    		curent_lenght=left_margin;
	    		y=y+line_space;
	    		g.drawString(word, x+curent_lenght, y);
	    	   curent_lenght+=distance;
	    	}
	    }
	  
	    return y;
	}
	

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		int upper_border=15;
		int y_start_intrebari=this.intrebari_rect.y+upper_border;
		int y_curent=y_start_intrebari;
		if(corect==false)
	    g.drawImage(background_incorect,0, 0, null);
		else
			g.drawImage(background_corect,0, 0, null);
		for(int i=0;i<=4;i++) {
		String text=intrebare[i];
		int indice;
		if(i==0)
		 indice=-1;
		else
			indice=i;
		int y_temp=this.drawStringInRectangle(g,indice,intrebari_rect,y_curent,font,text);
		
		int line_space=g.getFont().getSize();
		
		
		
		
		y_curent=y_temp+line_space+5;
		}
		
		y_curent=y_curent+10;
		g.setFont(new Font("Arial",Font.PLAIN,20));
        Vector<Integer>Rasp=new Vector<>();
		int nr_intrebare=quiz.getNrIntrebare();
		int nivel=quiz.panel.getGame().getPlay().nr_nivel;
		for(int i=1;i<=4;i++) {
			if(quiz.Rasp[nivel][nr_intrebare].contains(intrebare[i])) {
				Rasp.add(i);
			}
			
		}
		String text="Raspunsurile corecte sunt:";
		for(int i=0;i<Rasp.size()-1;i++) {
			text=text+Rasp.get(i)+", ";
		}
		int x=this.explanaition_rect.x+20;
		text=text+Rasp.get(Rasp.size()-1);
		g.drawString(text,x, y_curent);
		
		y_curent=y_curent+g.getFont().getSize();
		
		g.drawString("Explicatii:", x, y_curent);
		
		y_curent=y_curent+g.getFont().getSize();
		
		int indice=1;
		
		
		System.out.println(nr_intrebare);
		for(int i=0;i<this.Explicatii[nivel][nr_intrebare].size();i++) {
		 text=this.Explicatii[nivel][nr_intrebare].get(i);
			int y_temp=this.drawStringInRectangle(g,indice,intrebari_rect,y_curent,font,text);
			
			int line_space=g.getFont().getSize();
			
			
			
			
			y_curent=y_temp+line_space+5;
		    indice++;	
		}
		
		
		g.drawImage(continua, Continue.x,Continue.y, null);
		}
		
		
		
		
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(this.Continue.contains(e.getX(),e.getY())) {
			 int nr_nivel = quiz.panel.getGame().getPlay().nr_nivel;
				quiz.setINrIntrebare(quiz.GetRandom(nr_nivel));
			   System.out.println(quiz.getNrIntrebare());
			    this.Initialize_question();
			    // curata elementele din coada 
			 
			   quiz.queue.clear();
			    // deschidem butonul de recharge
			   GameState.setCurrentOption(GameState.COMBAT);
			  quiz.panel.getGame().getCombat().abilitati.set_enabled(true);
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
	
	
	
}
