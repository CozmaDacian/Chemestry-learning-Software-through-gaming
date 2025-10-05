package actiune_joc;

import static utilz.Constante.Directions.DOWN;
import static utilz.Constante.Directions.LEFT;
import static utilz.Constante.Directions.RIGHT;
import static utilz.Constante.Directions.UP;
import static utilz.Constante.PlayerConstansts.IDLE;
import static utilz.Constante.PlayerConstansts.IDLE_GUN;
import static utilz.Constante.PlayerConstansts.RUN;
import static utilz.Constante.PlayerConstansts.RUN_GUN;
import static utilz.Constante.PlayerConstansts.JUMP;
import static utilz.Constante.Directions.INAIR;
import static utilz.Constante.Directions.FALL;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import Menu.Deserialize_Class;
import Menu.Load_Last_Checkpoint;
import Nivel.Level_Manager;
import main_game.CheckPoint;
import main_game.Enemies;
import main_game.Game;
import main_game.GamePanel;
import states.GameState;
import states.metode;
import states.metode.*;
import utilz.CButton;
import utilz.Obiecte;
import states.GameState;

public class Playing implements metode {

	private BufferedImage back;
	private Game game;
	public boolean moving=false;
	private int offset=10;
	public boolean jump=false;
    public boolean idle=true;
    public int nr_nivel=1;
    public float fact_jump=1;
   
    public boolean a_release=false,d_release=false;
	public Vector<Enemies>enemies=new Vector<>();
	private Vector<Obiecte> obj=new Vector<>();
	public Vector<CheckPoint>checkpoints=new Vector<>();
	public Queue<CheckPoint> Final=new LinkedList<>();
	public Rectangle Obstacole[][]=new Rectangle[5][5];
	
	Level_Manager level1,level2,CurentLevel;
	
    public Queue<Level_Manager>Levels=new LinkedList<>();
	
	public int levelData[][]=new int[200][40];
	
	public Playing(Game game) {
		this.game=game;
		
		for (int i=1;i<=utilz.Constante.nr_nivele;i++) {
	    level1=new Level_Manager(game,i,"Data.png","texture.png");
	    Levels.add(level1);
		}
		level1 = Levels.peek();
	    levelData=level1.getLevelData();
	    setObjects(level1.Add_Objects());
	    
	    
	   level1.InterpretCheckpoint(this);
	   
        level1.InterpretEnemies(0, 0, this);
        
        
       
       
      
	    
	  
	}
	
	public void check_in_obiecte() {
		
		
		for(int i=0;i<getObjects().size();i++) {
		
	   Obiecte obiect=getObjects().get(i);
	   int x=obiect.getX();
	   int y=obiect.getY();
	   int tip=obiect.tip;
	
		Rectangle hitbox=new Rectangle(x,y,64,64);	
		if(game.getCaracter().getHitBox().intersects(hitbox)) {
			Levels.peek().statistici.obiecte_colectate++;
			game.getInventar().Add_items(tip);
			getObjects().remove(i);
			
		}
			
		}
		
	}
	public void check_at_end() {
		
		
		if(Final.peek().check_in_checkpoint(game.getCaracter().getHitBox())){
			Final.poll();
			nr_nivel++;
			enemies.removeAllElements();
			
		
			game.getReport().addInfo(Levels.peek());
			
			Levels.poll();
			CurentLevel=Levels.peek();
			
			levelData=CurentLevel.getLevelData();
		
			game.getIntrebari().reinitialize("intrebari"+nr_nivel);
			game.getCaracter().setLevelData(levelData);
			CurentLevel.InterpretEnemies(0, 0, this);
			obj.removeAllElements();
			Deserialize_Class classa=new Deserialize_Class();
			Load_Last_Checkpoint point=classa.deserialize(utilz.Constante.path_name+"Save/1.json");
			point.initialize_variables(game);
			setObjects(CurentLevel.Add_Objects());
			checkpoints.removeAllElements();
			CurentLevel.InterpretCheckpoint(this);
		    GameState.setCurrentOption(GameState.REPORT);
		}
		
	}
	public void add_check_points() {
		CheckPoint point=new CheckPoint(1000,1000,70,70);
		   
	    checkpoints.add(point);
	    point=new CheckPoint(0,0,1000,1536);
	    checkpoints.add(point);
	    point=new CheckPoint(2500,300,400,400);
	    checkpoints.add(point);
	}
  public void check_in_check_point() {
	  
	  for(int i=0;i<checkpoints.size();i++) {
		  CheckPoint point=checkpoints.get(i);
		  if(point.check_in_checkpoint(game.getCaracter().getHitBox())) {
			 checkpoints.remove(i);
			 Load_Last_Checkpoint save=new Load_Last_Checkpoint(game);
		  }
	  }
	  
  }
	
	
	 
	 
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
	    
		
	
		
		
		Levels.peek().draw(g);
		CheckPoint rect = this.Final.peek();
		
		
		
		for(int i=0;i<getObjects().size();i++) {
			  Obiecte obiect=getObjects().get(i);
			   int x=obiect.getX();
			   int y=obiect.getY();
			   int tip=obiect.tip;
			   
			   BufferedImage image=game.getInventar().linker.get(tip);
			   
			   
			   g.drawImage(image, x-game.getCaracter().xLvlOffset, y-game.getCaracter().yLvlOffset, 64, 64, null);
		}
	
			
		getGame().getCaracter().draw(g);
	    for(int i=0;i<enemies.size();i++) {
	    	Enemies enemy=enemies.elementAt(i);
            enemy.draw(g); 
	    	
	    
	    	
	    }

	    
		
		
	}

	@Override
	public void render() {
		check_in_obiecte();
		check_in_check_point();
	getGame().getCaracter().update();
	this.check_at_end();
	for(int i=0;i<enemies.size();i++) {
    	Enemies enemy=enemies.elementAt(i);
        
     
     enemy.UpdatePos(-1);
  
     enemy.UpdateTick();
     enemy.check_in_range();
    	
    
    	
    }
	 
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_A:
			getGame().getCaracter().setStanga(true);
			
			getGame().getCaracter().setMovingdr(false);
			break;
		case KeyEvent.VK_D:
			getGame().getCaracter().setDreapta(true);
			getGame().getCaracter().setMovingdr(true);
			
			
			break;
		case KeyEvent.VK_SPACE:
		    new Thread(() -> {
                try {
                   
                	getGame().getCaracter().setInAir(true);
                	getGame().getCaracter().setSpace_pressed(getGame().getCaracter().getSpace_pressed()+1);
                	
                	getGame().getCaracter().updateJump();
                    Thread.sleep(1);
                    
                    
                    
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
		    break;
		    
		    
		case KeyEvent.VK_F5:
			Deserialize_Class classa=new Deserialize_Class();
			Load_Last_Checkpoint point=classa.deserialize(utilz.Constante.path_name+"Save/1.json");
			point.initialize_variables(game);
			break;
			
		case KeyEvent.VK_SHIFT:
			getGame().getCaracter().setShiftPress(true);
        break;
        
		case KeyEvent.VK_I:
			GameState.setCurrentOption(GameState.INVENTAR);
			break;
		
		case KeyEvent.VK_T:
			GameState.setCurrentOption(GameState.SKILLTREE);
			break;
		case KeyEvent.VK_ESCAPE:
			GameState.setCurrentOption(GameState.PAUSE_MENU);
		}
    }
			
			
			
			
		
		
	



	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
switch(e.getKeyCode()) {
		
		case KeyEvent.VK_A:
			getGame().getCaracter().setStanga(false);
			
			break;
		case KeyEvent.VK_D:
			getGame().getCaracter().setDreapta(false);
			break;
		case KeyEvent.VK_SPACE:
			break;
		
}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Vector<Obiecte> getObjects() {
		return obj;
	}

	public void setObjects(Vector<Obiecte> obj) {
		this.obj = obj;
	}
}
	
