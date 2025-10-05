package main_game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

import states.metode;
import utilz.Pair;

public class Editare_Intrebari implements metode{
    
    
	int nrintrebari=0;
	boolean next=false,previous=false;
	boolean finish=false,savable=false;
	int tick=0;
	public Game game;
	BufferedImage back;
	private Queue<Integer>ValidateRaspuns;
	private JTextArea File=new JTextArea();
	private Rectangle RectSave=new Rectangle(1260,680, 100, 70),
	Next=new Rectangle(1145,680,90,70),
	Last=new Rectangle(1030,680,90,70),
	Edit=new Rectangle(915,680,90,70);
	private String Intrebari[][]=new String[200][6];
	private JTextArea Intrebare=new JTextArea();
	public JTextArea Raspuns[]=new JTextArea[5];
	public Rectangle InsideRasp[]=new Rectangle[5];
	private String Current[]=new String[5];
	private ArrayList<Pair<String, Color>> Patrate = new ArrayList<>(5);
	private BufferedImage image;
	private int x, y;
	private BufferedImage image1;
	private Map<Color,Color>Culori=new HashMap<>();
	private boolean validate=false,marked=false;
	public Editare_Intrebari(Game game) 
	{
	    double percentage=0.90;
		
		this.game = game;

		initializare();
		
	}
     
	
	private void initializare() {
		back=game.getIntrebari().quiz;
	  
		Patrate.add(new Pair<String,Color>("Next",Color.LIGHT_GRAY));
		Patrate.add(new Pair<String,Color>("Previous",Color.LIGHT_GRAY));
		Patrate.add(new Pair<String,Color>("Save",Color.LIGHT_GRAY));
		Patrate.add(new Pair<String,Color>("Validate",Color.LIGHT_GRAY));
		for (int i = 0; i < 4; i++) {	
			InsideRasp[i]=new Rectangle();
	        Raspuns[i] = new JTextArea();
	        Raspuns[i].setFont(new Font("Arial",Font.BOLD,16));
	        Raspuns[i].setRows(3);
	        Current[i]=new String();
	        Raspuns[i].setDefaultLocale(null);
	        Raspuns[i].setColumns(1);
	        Raspuns[i].setSize(320, 70);
	    }
	    // Adjusted loop condition
	    for (int i = 0; i < 200; i++) {
	        for (int j = 0; j < 5; j++) {
	            Intrebari[i][j] = new String();
	        }
	    }

	    int xquiz = game.getIntrebari().xquiz;
	    Raspuns[0].setLocation(289 + 145, 478 + 40);
	    Raspuns[1].setLocation(631 + 145, 478 + 40);
	    Raspuns[2].setLocation(289 + 145, 560 + 40);
	    Raspuns[3].setLocation(631 + 145, 560 + 40);
	          
	    for(int i=0;i<4;i=i+2) {
	    	InsideRasp[i].setBounds(Raspuns[i].getBounds());
	    	InsideRasp[i].setSize(30, 30);
	    	InsideRasp[i].setLocation(InsideRasp[i].x-50,InsideRasp[i].y);

	    }
	    for(int i=1;i<4;i=i+2) {
	    	
	    	InsideRasp[i].setLocation(Raspuns[i].getX()+Raspuns[i].getWidth()+15,Raspuns[i].getY());
	    	InsideRasp[i].setSize(30, 30);
	    }
	    	
	   
    	
       for(int i=0;i<4;i++)
       {
    	   Raspuns[i].setVisible(false);
    	   
    	 
    	   Raspuns[i].setColumns(1);
    	   Raspuns[i].setRows(1);
    	   Raspuns[i].setWrapStyleWord(true);
    	   Raspuns[i].setLineWrap(true);
    	   this.game.getGamePanel().add(Raspuns[i]);
       }
       Intrebare.setFont(new Font("Arial",Font.PLAIN,28));
       Intrebare.setBounds(460,400,600,112);
       Intrebare.setLineWrap(true);
       Intrebare.setWrapStyleWord(true);
       Intrebare.setVisible(false);
       this.game.getGamePanel().add(Intrebare);
		
	}
			
	 
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {

		case KeyEvent.VK_W:
			
			y=y-15;
			break;
		case KeyEvent.VK_S:
			y=y+15;
			break;
		case KeyEvent.VK_A:
			x=x-15;
			break;
		case KeyEvent.VK_D:
			x=x+15;
			break;
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




	@Override
	


   
	
	

	public void draw(Graphics2D g) {
		BufferedImage back_quiz=game.getIntrebari().back_quiz;
		BufferedImage quiz=game.getIntrebari().quiz;
		g.drawImage(back_quiz, 0, 0, null);
		g.drawImage(quiz,game.getIntrebari().xquiz,30,null);
	   
		
	    
	    g.drawImage(image, 0, 0, null);
	    String path=utilz.Constante.path_name+"Robot/"+"rosu/";
	   
	    
	    /*
	    for(int j=0;j<=6;j++)
	    for(int ct=1;ct<=utilz.Constante.EnemyConstants.Robot.GetEnemy(j);ct++) {
	    if(j==4)
	    	j++;
	    image = game.loadImageFromFile(path+game.enemy.Foldere.get(j)+"/"+ct+" (1).png");
	     g.drawImage(image, 180*ct, j*100 ,null);   
	    }
	    */
	    }
	
	
	void modified() {
		for(int i=0;i<4;i++) {
			if(Current[i]!=(Raspuns[i].getText())) {
				Current[i]=Raspuns[i].getText();
				
			    Raspuns[0].setLocation(286 + 145, 478 + 40);
			    Raspuns[1].setLocation(634 + 145, 478 + 40);
			    Raspuns[2].setLocation(286 + 145, 561 + 40);
			    Raspuns[3].setLocation(634 + 145, 561 + 40);
			   
			}
			Intrebare.setLocation(460,400);
		}
	
	}
	public void render() {
		modified();
		if(savable==false && finish==true) {
			tick++;
			if(tick>240)
			{
				tick=0;
				savable=false;
				finish=false;
			}
			
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x=e.getX();
		int y=e.getY();
	      
		
		if(validate==true) {
	    	for(int i=0;i<=3;i++) {
	    		
	    		if(InsideRasp[i].contains(x,y)) {
	    	     System.out.println(true);
	    	    if(Raspuns[i].getForeground()==Color.black)
	    		
	    	    Raspuns[i].setForeground(Color.RED);
	    	    else
	    	    Raspuns[i].setForeground(Color.black);
	    		
	    		}
	    	}
	    }
		
		if(RectSave.contains(x, y)) {
			savable=true;
			for(int i=0;i<=3;i++) {
				if(Raspuns[i].getText().equals(null)) {
	               		savable=false;		
				
				}
				
				finish=true;
				
			}
			
		}
		
		if(Intrebare.getText().equals(null)) {
			savable=false;
		}
		
		if(savable==true){
			Intrebari[nrintrebari][0]=Intrebare.getText();
			for(int i=0;i<=3;i++) {
				Intrebari[nrintrebari][i+1]=Raspuns[i].getText();
			}
			
		 }
		if(Edit.contains(x,y)) {
			System.out.print(true);
			for(int i=0;i<=3;i++) {
				
				Raspuns[i].setEditable(validate);
			}
			validate=!validate;
			 
		}
		if(Next.contains(x,y)) {
		  if(savable==true ) {
			  System.out.print(false);
			  
			  marked=false;
			  for(int i=0;i<=3;i++) {
				  if(Raspuns[i].getForeground()==Color.pink) {
					  marked=true;
				  }
			  }
			  if(marked==false)
				  return;
			  nrintrebari++;
			  for(int i=0;i<=3;i++) {
				  Raspuns[i].setForeground(Color.black);
				  Raspuns[i].setText(null);
				  
				  
			  }
			  Intrebare.setText(null);
		  }
		}
		if(this.Last.contains(x,y)) {
			if(nrintrebari>=1) {
				nrintrebari--;
				Intrebare.setText(Intrebari[nrintrebari][0]);
				for(int i=0;i<=3;i++) {
					
					if(Intrebari[nrintrebari][i].contains("*")) {
					Raspuns[i].setForeground(Color.red);
					
					Raspuns[i].setText(Intrebari[nrintrebari][i].substring(1));
				
					}
					else
					{
						Raspuns[i].setText(Intrebari[nrintrebari][i]);
					}
			}
		}
		
	}
	}
}
	