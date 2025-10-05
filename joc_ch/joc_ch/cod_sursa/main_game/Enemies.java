package main_game;

import static utilz.Constante.PlayerConstansts.GetSprite;
import static utilz.Constante.EnemyConstants.Robot.GetSpeed;
import static utilz.Constante.EnemyConstants.Robot.GetEnemy;

import static utilz.Constante.EnemyConstants.Robot.ATTACK_MIC;
import static utilz.Constante.EnemyConstants.Robot.ATTACK;
import static utilz.Constante.EnemyConstants.Robot.SHOOT;
import static utilz.Help_Methods.CanMoveHere;
import static utilz.Help_Methods.GetEntityYUnderRoofOrAbove;
import static utilz.Help_Methods.IsEntityOnFloor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

import Entitati.Entity;
import Stats.Enemy_stats;
import states.GameState;
import utilz.Constante;
import utilz.Pair;

public class Enemies extends Entity implements Serializable {
    
	public int enemy_action=utilz.Constante.EnemyConstants.Robot.RUN;
	public Map<Integer,String>Foldere=new HashMap<>();
	public int numar=-1;
	private int range_x=2*Game.TILES_SIZE;
    private int range_y=Game.TILES_SIZE;

	public int type;
	public boolean inair=false;
	public int xcaracter,ycaracter;
	public int dist=0;
	public int number_of_enemies=2;
	int vHP[]=new int[4];
	int k=0;
	public boolean move_stg=true;
	int tick=0,speed=20;
	public int animatie=1;
	float airSpeed=0;
	private int atac;
	private Rectangle hitbox_enemy;
	private int levelData[][]=new int[50][200];
	private Enemy_bars bars;
	private Enemy_stats statistici;
	public BufferedImage[][][] enemy =new BufferedImage[4][10][15];
	public Queue<Integer> Attacks;
	public Enemies(int x,int y,int type,int number_of_enemies,Game game) {
	
	super(x,y,game);
	
	
	this.type=type;
    int level=game.getCaracter().getStatistici().getPlayer_level();
	statistici=new Enemy_stats(type,level,number_of_enemies);
	this.number_of_enemies=number_of_enemies;
	bars=new Enemy_bars(this);
	
	this.initialize_animations(0,"alb");
	this.initialize_animations(1, "rosu");
	this.setScientistAnimation(2,utilz.Constante.path_name + "Robot/" ,"om", enemy);
	levelData=game.getCaracter().getLevelData();
	
	this.randomize_attacks();
	hitbox_enemy=new Rectangle(x,y,60,60);
	}
	private void initialize_animations(int type,String culoare) {
		Foldere.put(0,"Attack");
		Foldere.put(1, "Dead");
		Foldere.put(2, "Idle 2");
		Foldere.put(3, "Run"); //animatie outside combat
		Foldere.put(4, "Shoot 2");
		Foldere.put(5, "Attack1"); // animatie outside combat
		Foldere.put(6, "Runi");
		Foldere.put(7, "Idle_Play");//outsive combat
		enemy=this.setSpriteAnimation(type, utilz.Constante.path_name + "Robot/",culoare, enemy);
		Vector<Integer>not=new Vector<Integer>();
		not.add(0); not.add(1); not.add(2);not.add(4); not.add(6); not.add(7); 
		this.resizeEnemySprite(type,0.9,0.65, enemy,7, not);
		
	    enemy[type][7][1]=resizeImage(enemy[type][7][1], 68, 75);
	    
	   
	}
	
	
	
	
	public boolean check_in_range() {
	
    int xcar=game.getCaracter().getX();
    int ycar=game.getCaracter().getY();
    
    Rectangle car=new Rectangle();
    car.x=xcar-range_x;
    car.y=ycar-range_y;
    car.width=range_x*2;
    car.height=range_y*2;
    
    if(car.contains(x, y))
    {
    	xcaracter=(int)game.getCaracter().getX();
    	ycaracter=(int)game.getCaracter().getY();
        game.getIntrebari().yozn=-100;
        game.getCaracter().resetDirections();
        game.getCombat().setPhase_1(true);
        game.getCombat().setEnemy_phase1(this);
       
        game.getCaracter().setPlayerAction(utilz.Constante.PlayerConstansts.SHOOT);
        game.getCombat().getPhase1().updateHitboxes_before_combat();
         
    	game.getIntrebari().xozn=game.getCaracter().getX()-game.getCaracter().getxLvlOffset();
    	GameState.setCurrentOption(GameState.COMBAT);
    	
        
    	
    
    

    	return true;
    }
    return false;
	
	}
    public void updateHitbox() {
      
    	hitbox_enemy.x=(int) x;
    	hitbox_enemy.y=(int) y;
    	
    
    		hitbox_enemy.height=68;
            hitbox_enemy.width=60;
        
    	}
	public void UpdateXPos(float speed_inamic) {
		
		if(move_stg==true) {
			if(CanMoveHere(x+speed_inamic,y,(int)width,(int )height,levelData,hitbox_enemy)) 
			  x=(x+speed_inamic);	
			}
			else
		    if(CanMoveHere(x-speed_inamic,y,(int) width,(int)height,levelData,hitbox_enemy)) 
		    	x=(x-speed_inamic);
		    
			
	       }
	
	public void UpdatePos(float xSpeed) {
		updateHitbox();
		if(!inair) {
			  if(IsEntityOnFloor(hitbox_enemy,levelData)==false)
				  inair=true;
			     airSpeed=0;
		  }
		 
		  if(inair) {
		if(CanMoveHere(x,y+airSpeed,hitbox_enemy.width,hitbox_enemy.height,levelData,hitbox_enemy)) {
			 
			  y+=airSpeed;
			  airSpeed+=gravity;
			 
		     UpdateXPos(xSpeed);
		  }
		  else {
			  
			UpdateXPos(xSpeed);  
			
			if(airSpeed>0)
		  {
          airSpeed=0;
		 inair=false;
	    
		  }
		
				
	  }
	  }
	  else
		 UpdateXPos(xSpeed);
		
	}
	public void UpdateTick() {
		tick++;
		if(GameState.state==GameState.PLAYING) {
			enemy_action=utilz.Constante.EnemyConstants.Robot.RUN;
		}
	    
		int speed=0;
		
		
		int number_of_animations=0;
		
		if(type==1 || type==0) {
			speed=GetSpeed(enemy_action);
		    number_of_animations=GetEnemy(enemy_action);
		}
		else
		if(type==2) {
			speed=utilz.Constante.EnemyConstants.SCIENTIST.GetSpeed(enemy_action);
			number_of_animations=utilz.Constante.EnemyConstants.SCIENTIST.GetEnemy(enemy_action);
		}
		
		
		if(tick>speed) {
			tick=0;
			animatie++;
			if(animatie>number_of_animations) {
				
				
	         	
				move_stg=!move_stg;
				animatie=1;
		}
			
	}
	}
	
	
	public void randomize_attacks() {
		int last=0;
		int bflast=0;
		this.Attacks=new LinkedList<>();
		  Random random = new Random();
		for(int i=1;i<=10;i++) {
		
			int number_attack=random.nextInt(2) + 1;
			bflast=last;		
			if(last!=2 && bflast!=2) {
			
			last=number_attack;
		    }
			else
		    last=1;
		     
		   
			Attacks.add(last);
		    System.out.print(last+" ");
		
		}
		
	}
	public int get_Atac() {
	  
	   
	   return Attacks.poll();
	   
		
	}
	public void draw(Graphics2D g)
	{
		    int xOffset=game.getCaracter().getxLvlOffset();
		    int yOffset=game.getCaracter().getyLvlOffset();
	
		BufferedImage en=enemy[type][enemy_action][animatie];
		if(move_stg==true)
			en=flipImageVertical(en);
		
		g.drawImage(en, (int)x-xOffset, (int) y-yOffset, null);
	    }
	
	public BufferedImage[][][] setSpriteAnimation(int k,String nume,String culoare,BufferedImage PlayerAnim[][][]) {
		 
		BufferedImage d = null,img;
		
		int nr=0;
       String fisier=nume;
       String subfisier,imagine;
       fisier=nume+culoare+"/";
       
        for(int i=0;i<Foldere.size();i++) {
    	subfisier=fisier+Foldere.get(i)+"/";   
    	
    	for(int j=1;j<=utilz.Constante.EnemyConstants.Robot.GetEnemy(i);j++) {
    		
    	imagine=subfisier+j+" (1).png";
  	
    	 File imageFile = new File(imagine);
         try {
			BufferedImage image = ImageIO.read(imageFile);
			PlayerAnim[k][i][j]=image;
		} catch (IOException e) {
		   System.out.print(imagine+" not found");
			e.printStackTrace();
		}
    	}
        
        }
        return PlayerAnim;
	}
	
	public BufferedImage[][][] setScientistAnimation(int k,String nume,String inamic,BufferedImage PlayerAnim[][][]) {
		 
		BufferedImage d = null,img;
		
		int nr=0;
       String fisier=nume;
       String subfisier,imagine;
       fisier=nume+inamic+"/";
       
        for(int i=0;i<Foldere.size();i++) {
    	subfisier=fisier+Foldere.get(i)+"/";   
    	
    	for(int j=1;j<=utilz.Constante.EnemyConstants.SCIENTIST.GetEnemy(i);j++) {
    		
    	imagine=subfisier+j+".png";
  	
    	 File imageFile = new File(imagine);
         try {
			BufferedImage image = ImageIO.read(imageFile);
			PlayerAnim[k][i][j]=image;
		} catch (IOException e) {
		   System.out.print(imagine+" not found");
			e.printStackTrace();
		}
    	}
        
        }
        return PlayerAnim;
	}
	
	
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setX(float x) {
		this.x=x;
		
	}
	public void setY(float y) {
		this.y=y;
	}
	public Rectangle getHitboxEnemy() {
		return this.hitbox_enemy;
	}
	public int getEnemyAction() {
		return this.enemy_action;
	}
	public void setEnemyAction(int action) {
		this.enemy_action=action; 
		this.animatie=1;
	}

	public int getAtac() {
		return atac;
	}
    public BufferedImage get_current_enemy_sprite() {
    	return this.flipImageVertical(enemy[this.type][this.enemy_action][this.animatie]);
    }
    
    public Rectangle getEnemy_hitbox_combat() {
    	
    int width=enemy[this.type][this.enemy_action][this.animatie].getWidth();
    int height=enemy[this.type][this.enemy_action][this.animatie].getHeight();
    return new Rectangle((int) x,(int) y,width,height);
    }
    
	public void setAtac(int atac) {
		this.atac = atac;
	}
	public Enemy_stats getStatistici() {
		return statistici;
	}
	public void setStatistici(Enemy_stats statistici) {
		this.statistici = statistici;
	}
	public Enemy_bars getBars() {
		return bars;
	}
	public void setBars(Enemy_bars bars) {
		this.bars = bars;
	}

	

	
}
