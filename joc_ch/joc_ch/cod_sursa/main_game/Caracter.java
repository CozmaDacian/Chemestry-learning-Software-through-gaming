package main_game;
import static utilz.Help_Methods.GetEntityYUnderRoofOrAbove;
import static utilz.Help_Methods.IsEntityOnFloor;
import static utilz.Help_Methods.GetEntityPosNextToWall;
import static utilz.Help_Methods.CanMoveHere;
import static utilz.Constante.PlayerConstansts.GetSpeed;
import static utilz.Constante.PlayerConstansts.GetSprite;
import static utilz.Constante.PlayerConstansts.IDLE;
import static utilz.Constante.path_name;
import static utilz.Constante.PlayerConstansts.IDLE_GUN;
import static utilz.Constante.PlayerConstansts.JUMP;
import static utilz.Constante.PlayerConstansts.RUN_GUN;
import static utilz.Constante.PlayerConstansts.FALL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;

import Entitati.Entity;
import Stats.Player_stats;
import states.GameState;
import utilz.Pair;

public class Caracter extends Entity implements Serializable {

	public int cul_car=0;
	public int tick=0,falltick=0;
	
	
	
	public BufferedImage PlayerAnim[][][]=new BufferedImage[3][20][20];
	public BufferedImage PlayerAnim_Combat[][][]=new BufferedImage[3][20][20];
	
	private int levelData[][]=new int[50][200];
    private int player_action=utilz.Constante.PlayerConstansts.IDLE_GUN;
    private int player_direction=-1,space_pressed=0;
    
    
    private boolean moving,dreapta=false,inair=false,stanga=false,ShiftPress=true,shoot=false;
   
    private float airSpeed=0f;
    
   
    private float gravity=0.04f*Game.SCALE_JUMP;
    private float jumpSpeed=-2.4f*Game.SCALE_JUMP;
    private float fallSpeedAfterCollision=0.5f*Game.SCALE_JUMP;
	public int animatie=1;
	private float fact_jump=0;
	public double ground_acceleration=0.6;
	
    public int yLvlOffset=768;
	public int xLvlOffset=0;
	
	private Bars bars;
	private Player_stats statistici=new Player_stats();
	
	
	private int upperBorder=(int)(0.65*Game.GAME_HEIGHT);
	private int lowerBorder=(int)(0.35*Game.GAME_HEIGHT);
	private int leftBorder=(int)(0.4*Game.GAME_WIDTH);
	private int rightBorder=(int)(0.6*Game.GAME_WIDTH);
   

	
    public Game game;
	
  public Caracter(float x,float y,Game game) {
	  super(x,y,game);
	    this.game=game;
		 
	    init_culori();
        initialize_Foloders();
        // Setam caracterul din inventar
        game.getInventar().caracter[0]=game.loadImageFromFile(utilz.Constante.path_name + "gic.png");
        game.getInventar().caracter[1]=this.set(game.getInventar().caracter[0], Culori,40);
        game.getInventar().caracter[2]=this.set(game.getInventar().caracter[1],Culori1,40);
        this.setSpriteAnimation(0,path_name+"Caracter/","gri1",PlayerAnim);
        this.setSpriteAnimation(0, path_name+"Caracter/","gri", PlayerAnim_Combat);
        this.levelData=game.getLevel1().getLevelData();
        resizeSprite(2,0.60,PlayerAnim,8,new Vector<Integer>());
        setBars(new Bars(50,40,214,18,114,10,statistici));
	}

      void initialize_Foloders()
      {
    	  Foldere.put(0,"Dead");
          Foldere.put(1, "Gun animation");
          Foldere.put(2, "Gun walk");
          Foldere.put(3, "Gun idle");
          Foldere.put(4, "Jump");
          Foldere.put(5, "Walk");
          Foldere.put(6, "Idle");
          Foldere.put(7,"Grenade animation");
          Foldere.put(8, "Fall");
      }
   
  
  

     


 




  
  public BufferedImage set(BufferedImage originalImage,Map<Color,Color>Culori,int offset) {


	  BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), originalImage.getType());

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

	            if (Culori.get(culoare1) != null && d1<originalImage.getHeight()-20 && d1<originalImage.getWidth()-offset) {
	                culoare2 = Culori.get(culoare1);
	            } else {
	                culoare2 = culoare1;
	            }

	            modifiedImage.setRGB(e, d1, culoare2.getRGB());
	        }
	    }
	    return modifiedImage;
  }



 

  
  
     
   
	private void checkUpperBorder() {
		
	
			// verificam offsetul y al caracterului
		
		
		int player=(int) y;
		int diff=player-this.yLvlOffset;
		
		 if(diff>this.upperBorder && yLvlOffset<768)
			  yLvlOffset+=diff-this.upperBorder;
		  else
			  if(diff<this.lowerBorder && yLvlOffset>0)
				 yLvlOffset+=diff-lowerBorder;
		
		 if(yLvlOffset>768)
			 yLvlOffset=768;
		 else
			 if(yLvlOffset<0)
				 yLvlOffset=0;
		 
	}
	
	
		
		
	
  
    
	private void checkCLoseToBorder() {

	  checkUpperBorder();
	  if(x>615) {
	  int player=(int) x;
	  int diff=player-this.xLvlOffset;

	  if(diff>this.rightBorder && x<game.getPlay().Levels.peek().img.getWidth()*game.TILES_SIZE-this.leftBorder)
		  xLvlOffset+=diff-this.rightBorder;
	  else
		  if(diff<this.leftBorder)
			 xLvlOffset+=diff-leftBorder;
	  }
     }
	
	 
	 private void updateXPos(float xSpeed) {
		    
		 if(CanMoveHere(hitbox.x+xSpeed,y,hitbox.width,hitbox.height,getLevelData(),hitbox)) {
			
			x+=xSpeed;
		 }
		
		// else
		  // x=(int) GetEntityPosNextToWall(hitbox,xSpeed);
	 }
	
	  public void updatePos() {

		  moving=false;
 float playerSpeed_RunX=2.0f;
 float playerSpeed_WalkX = 1.3f;
 if(!inair && GameState.state==GameState.PLAYING) {
	  if(IsEntityOnFloor(hitbox,getLevelData())==false)
		  inair=true;
	     airSpeed=0;
 }
 if(stanga==false && dreapta==false && inair==false)
	return ;		
   float xSpeed=0;
         

   
	   if(dreapta ) 
	   xSpeed+=playerSpeed_RunX;
       if(stanga)
    	xSpeed-=playerSpeed_RunX;
   
  
  
		  this.checkCLoseToBorder();
		  
		 
		 
		  if(inair) {
			  if(y+airSpeed>0)
			  if(CanMoveHere(hitbox.x,y+airSpeed,hitbox.width,hitbox.height,getLevelData(),hitbox)) {
				 
				  y+=airSpeed;
				  airSpeed+=gravity;
				 
			     updateXPos(xSpeed);
			  }
			  else {
				  
				updateXPos(xSpeed);  
				
				if(airSpeed>0)
			  {
			 setInAir(false);
		     setSpace_pressed(0);
			  }
				else
				{
					airSpeed=fallSpeedAfterCollision;
				}
					
		  }
		  }
		  else
			 updateXPos(xSpeed);
		  
 		  moving=true;
 		  
 		  
 		
	  }
	  

	   public void setDirectie(int direction) {
			this.player_direction=direction;

		}

		public void setInAir(boolean inair)
		{
		this.inair=inair;
		}
		
	 
		
		public int getPlayerAction() {
			return player_action;
		}
		
		public void setPlayerAction(int actiune) {
			this.player_action = actiune;
			animatie=1;
		}
		
		public void resetDirections() {
			stanga=false;
			dreapta=false;
			
		}

 public void updateJump() {
	 
	
	 if(space_pressed==1) {
		 airSpeed=jumpSpeed;
	 }
	 else
		if(space_pressed==2) {			
			airSpeed=(float) (jumpSpeed*2/3);		    
		}
	 
 }
 
 private void setAnimation() {
	 
	 if(player_action==utilz.Constante.PlayerConstansts.SHOOT)
		 return;
	 
	 int startAni=player_action;
	 if(moving)
		 
		
		 player_action=RUN_GUN;
	 else
		 player_action=IDLE_GUN;
	 
	 if(stanga && dreapta)
		 player_action=IDLE_GUN;
	 if(inair) {
		
		 if(airSpeed<0)
		player_action=JUMP;
		 else
	    player_action=FALL;
	 }
	 if(stanga==true)
		 movingdr=false;
	 if(dreapta==true)
		 movingdr=true;
	 if(startAni!=player_action)
		 resetAnimation();
	 
 }

  private void resetAnimation() {
	  animatie=1;
	  tick=0;
  }
 public void update() {
	  updatePos();
	  setAnimation();
	  
	  
	  width=PlayerAnim[0][getPlayerAction()][animatie].getWidth();
	  
	  
	  
	  height=PlayerAnim[0][getPlayerAction()][animatie].getHeight();
	  updateHitBox();
	  UpdateTick();
 }

	public void UpdateTick()
	{
		tick++;
		if(player_action==JUMP) {
			tick=0;
			return ;
		}
		
     if(tick>GetSpeed(player_action)) {
    	 
    	 tick=0;
    	 animatie++;
  
    	 if(animatie>GetSprite(player_action)) {
    		 
    		 animatie=1;
    		 
    		 
    	 }
    	 
     }
	


     }
    
	public BufferedImage get_current_caracter_sprite_in_combat() {
		return this.PlayerAnim_Combat[this.cul_car][this.player_action][this.animatie];
	}
	public BufferedImage get_current_caracter_sprite() {
		return this.PlayerAnim[this.cul_car][this.player_action][this.animatie];
	}




	public void draw(Graphics2D g) {


          
            getBars().draw_health_bar(g);
            getBars().draw_energy_percentage(g);
		    if(movingdr) {
			g.drawImage(PlayerAnim[cul_car][getPlayerAction()][animatie],(int)x-this.xLvlOffset,(int)y-yLvlOffset,null);



		  }
		    else
		    {
		    	   BufferedImage flippedHorizontal = flipImageVertical(PlayerAnim[cul_car][getPlayerAction()][animatie]);
		    	   g.drawImage(flippedHorizontal,(int)x-this.xLvlOffset, (int)y-yLvlOffset,null);
		    }
	}
    






    

	public boolean getDreapta() {
		return dreapta;
	}

	public void setDreapta(boolean dreapta) {
		this.dreapta = dreapta;
	}

	public boolean getStanga() {
		return stanga;
	}

	public void setStanga(boolean stanga) {
		this.stanga = stanga;
	}

	public int getSpace_pressed() {
		return space_pressed;
	}

	public void setSpace_pressed(int space_pressed) {
		this.space_pressed = space_pressed;
	}

	public boolean isShiftPress() {
		return ShiftPress;
	}

	public void setShiftPress(boolean shiftPress) {
		ShiftPress = shiftPress;
	}

	public int[][] getLevelData() {
		return levelData;
	}

	public void setLevelData(int levelData[][]) {
		this.levelData = levelData;
	}
    public int getX() {
    	return (int)x;
    }
    
    public int getY() {
    	return (int) y;
    }
    public void setX(float x) {
    	this.x=x;
    }
    public void setY(float y) {
    	this.y=y;
    }
    
    public int getyLvlOffset() {
    	return yLvlOffset;
    }
	public int getxLvlOffset() {
		// TODO Auto-generated method stub
	   return xLvlOffset;
	}

	public Rectangle getHitBoxCombat() {
		// TODO Auto-generated method stub
		int width=PlayerAnim_Combat[this.cul_car][this.player_action][this.animatie].getWidth()-80;
		int height=PlayerAnim_Combat[this.cul_car][this.player_action][this.animatie].getHeight();
		int x=(int) this.x;
	    int y=(int) this.y;
	    return new Rectangle(x,y,width,height);
	}

	public Player_stats getStatistici() {
		return statistici;
	}

	public void setStatistici(Player_stats statistici) {
		
		this.statistici = statistici;
		this.bars.setStats(statistici);
	}

	public void setyLvlOffset(int yoffset) {
		this.yLvlOffset=yoffset;
	}
	public void setxLvlOffset(int xoffset) {
		// TODO Auto-generated method stub
		this.xLvlOffset=xoffset;
	}

	public Bars getBars() {
		return bars;
	}

	public void setBars(Bars bars) {
		this.bars = bars;
	} 

	















	
}
	






