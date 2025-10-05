package main_game;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.SystemFlavorMap;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import Combat.Combat;
import Menu.Dead;
import Menu.Explicatie;
import Menu.FinalReport;
import Menu.Pause;
import Menu.Start_Menu;
import Nivel.Handler;
import Nivel.Level_Manager;
import actiune_joc.Carte;

import actiune_joc.Intrebari;
import actiune_joc.Inventar;
import actiune_joc.Playing;
import actiune_joc.Skill_tree;
import states.GameState;


public class Game implements Runnable,Serializable{
	private static final long serialVersionUID = 1L;

	
	private Inventar inventar;
    private Playing play;
	private GameWindow gameWindow;
     private GamePanel gamePanel;
     private Thread gameThread;
     private Intrebari intrebari;
     private Combat combat;
     private Carte carte;
    private Enemies enemy;
    public Explicatie explicatii;
    private Level_Manager level1;
    private Editare_Intrebari edit;
     private Skill_tree tree;
    private Start_Menu menu;
    private Caracter caracter;
    private Pause pause;
    private Dead dead;
    private FinalReport report;
     private final int FPS_SET=60;
     private final int UPS_SET=120;
   private Handler levelHandler;
   public Bars bars;
   public  int XP=0,nivel=0;
   public final static int GROUND=630;
     public final static int TILES_DEF_SIZE=32;
     public final static float SCALE=2f;
     public final static float SCALE_JUMP=1.5f;
     public final static int TILE_WIDTH=32;
     public final static int TILES_IN_HEIGHT=12;
    public final static int TILES_IN_WIDTH=24;
    public final static int TILES_IN_SIZE=32;
     public final static int TILES_SIZE=(int) (TILES_DEF_SIZE*SCALE);
     public final static int GAME_WIDTH=TILES_SIZE*TILES_IN_WIDTH;
	 
     public final static int GAME_HEIGHT=TILES_IN_HEIGHT*TILES_SIZE;
     public final static int TOTAL_HEIGHT_SIZE=GAME_HEIGHT*2;
	 public final static int TOTALSIZE=GAME_WIDTH*10;
     
	 public Game() {
		 setGamePanel(new GamePanel(this));
		 
		  level1=new Level_Manager(this,1,"Data.png","texture.png");
		 
		 setInventar(new Inventar(this));
		 setCaracter(new Caracter(166,1000,this));
		 setPlay(new Playing(this));
    
    	
    	   
	setTree(new Skill_tree(this));
	
	//bars=new Bars(this);

	setCarte(new Carte(this));
    setDead(new Dead(this));
	menu=new Start_Menu(this);
	setPause(new Pause(this));
	gameWindow=new GameWindow(getGamePanel());
	setIntrebari(new Intrebari(getGamePanel()));
	setLevelHandler(new Handler(this));
	setCombat(new Combat(this));
	explicatii=new Explicatie(this);
	setReport(new FinalReport(this));
	setEdit(new Editare_Intrebari(this));
	
	getGamePanel().requestFocus();
	startGameLoop();
     
	}
	private void startGameLoop() {
		gameThread=new Thread(this);
		gameThread.start();
	}

	 public BufferedImage loadImageFromFile(String filePath) {
	        BufferedImage img = null;
	        try {
	            File file = new File(filePath);
	            if (file.exists()) {
	            	
	            	InputStream is = getClass().getResourceAsStream(filePath);
	                img = ImageIO.read(file); // Read the image from the file path
	               
	            } else {
	                throw new IOException("File not found: " + filePath);
	            }
	        } catch (IOException e) {
	            e.printStackTrace(); // Handle or log the exception
	            // Return a default image or handle the error accordingly
	            // For example:
	            // img = getDefaultImage(); // Provide a default image
	        }
	        return img;
	    }
	public BufferedImage setImage(String s) {
	    BufferedImage img = null;
	    InputStream is = getClass().getResourceAsStream(s);
        System.out.println(is);
	    try {
	        if (is != null) {
	            img = ImageIO.read(is);
	        } else {
	            // Handle case when the resource is not found
	            throw new IOException("Resource not found: " + s);
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); // Consider using a logger instead of printing to the console
	        // Return a default image or handle the error accordingly
	        // For example:
	        // img = getDefaultImage(); // Provide a default image
	    } finally {
	        try {
	            if (is != null) {
	                is.close(); // Close the input stream
	            }
	        } catch (IOException e) {
	            e.printStackTrace(); // Handle error in closing the input stream
	        }
	    }
	    return img;
	}
	
	public void draw(Graphics2D g) {
		switch(GameState.state) {
		case PLAYING:
			
			getPlay().draw(g);
		
		
		break;
		case EXPLICATII:
		explicatii.draw(g);
		break;
		case QUIZ:
		
		getIntrebari().draw(g);
		
		break;
		case MENU:
		menu.draw(g);   
			break;
		case INVENTAR:
			getInventar().draw(g);
			break;
		case CARTE:
		
			getCarte().draw(g);
			break;
		
		case PAUSE_MENU:
			getPause().draw(g);
			break;
			
		case COMBAT:
			getCombat().draw(g);
			
			break;
		case SKILLTREE:
			getTree().draw(g);
			break;
	
		case EDITARE:
			getEdit().draw(g);
			break;
		case DEAD:
			getDead().draw(g);
			break;
		case REPORT:
			this.getReport().draw(g);
	break;
		}
	}
	public void render() {
		
		
		switch(GameState.state) {
		case EXPLICATII:
			explicatii.render();
		case PLAYING:
			getPlay().render();
			
		break;
		
		case QUIZ:
		getIntrebari().render();
		
		
		break;
		case MENU:
		   
			break;
		case INVENTAR:
			
			break;
		case CARTE:
			getCarte().Updatetick();
			
			break;
		case COMBAT:
		
			getCombat().render();
			break;
		case SKILLTREE:
			
			break;
		
		case EDITARE:
			getEdit().render();
			break;
		case DEAD:
			getDead().render();
		default:
			break;
	}
	}
	
	
	public void run() {
	
		
		int frames = 0;
		int updates = 0;

		long lastCheck = System.currentTimeMillis();
		double timePerFrame = 1000000000 / FPS_SET;
		double timePerUpdate = 1000000000 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long now = System.nanoTime();

		while (true) {
		    now = System.nanoTime();

		    // FPS counter
		    if (now - lastFrame >= timePerFrame) {
		    	 getGamePanel().repaint();
		        lastFrame = now;
		        frames++;
		       
		    }

		    // UPS counter
		    if (now - lastUpdate >= timePerUpdate) {
		        // Update game logic here
		        // ...
		    	render();
		        lastUpdate = now;
		        updates++;
		    }

		    if (System.currentTimeMillis() - lastCheck >= 1000) {
		        lastCheck = System.currentTimeMillis();
		        System.out.println("FPS: " + frames + " | UPS: " + updates);
		        frames = 0;
		        updates = 0;
		    }
		}
}
	public Caracter getCaracter() {
		return caracter;
	}
	public Intrebari getIntrebari() {
		return intrebari;
	}
	public void setIntrebari(Intrebari intrebari) {
		this.intrebari = intrebari;
	}
	public void setCaracter(Caracter caracter) {
		this.caracter = caracter;
	}
	public Enemies getEnemy() {
		return enemy;
	}
	public void setEnemy(Enemies enemy) {
		this.enemy = enemy;
	}
	public Playing getPlay() {
		return play;
	}
	public void setPlay(Playing play) {
		this.play = play;
	}
	public Combat getCombat() {
		return combat;
	}
	public void setCombat(Combat combat) {
		this.combat = combat;
	}
	public Editare_Intrebari getEdit() {
		return edit;
	}
	public void setEdit(Editare_Intrebari edit) {
		this.edit = edit;
	}
	public Handler getLevelHandler() {
		return levelHandler;
	}
	public void setLevelHandler(Handler levelHandler) {
		this.levelHandler = levelHandler;
	}
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	public Inventar getInventar() {
		return inventar;
	}
	public void setInventar(Inventar inventar) {
		this.inventar = inventar;
	}
	public Level_Manager getLevel1() {
		return level1;
	}
	public void setLevel1(Level_Manager level1) {
		this.level1 = level1;
	}
	public Carte getCarte() {
		return carte;
	}
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	public Skill_tree getTree() {
		return tree;
	}
	public void setTree(Skill_tree tree) {
		this.tree = tree;
	}
	public Start_Menu getMenu() {
		return menu;
	}
	public void setMenu(Start_Menu menu) {
		this.menu = menu;
	}
	public Dead getDead() {
		return dead;
	}
	public void setDead(Dead dead) {
		this.dead = dead;
	}
	public Pause getPause() {
		return pause;
	}
	public void setPause(Pause pause) {
		this.pause = pause;
	}
	public FinalReport getReport() {
		return report;
	}
	public void setReport(FinalReport report) {
		this.report = report;
	}

}
