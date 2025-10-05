package Combat;

import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main_game.Game;
import utilz.CButton;
import utilz.Constante;
import utilz.Constante.EnemyConstants;
import utilz.Constante.PlayerConstansts;
import utilz.Constante.EnemyConstants.Robot;

public class Objects_Combat {
	protected BufferedImage load;
	protected BufferedImage background;
	public BufferedImage[] greneda=new BufferedImage[15];
   public BufferedImage cerc_player,cerc_inamic;
	public Point Foc[][]=new Point[3][5];
    
	private Game game;
	protected BufferedImage Bullet_Enemy;
	public int animatii_ozn=4;
	private Point caract_poz_final;
	public Point[] enemies_poz_final=new Point[4];
	
	public Point[] bullets_enemy=new Point[4];
	public BufferedImage[]OZN=new BufferedImage[15]; 
	
	
	public BufferedImage Bullet_Orientation[][]=new BufferedImage[3][10];
	
    public BufferedImage PlayerAnim[][][]=new BufferedImage[3][15][20];
    public BufferedImage atac_concentrat,atac_simplu;
    
    
    
	protected int SHOOT=utilz.Constante.PlayerConstansts.SHOOT,
			      GUNIDLE=utilz.Constante.PlayerConstansts.IDLE_GUN,
	              GRENADA=utilz.Constante.PlayerConstansts.EXPLOSION,
	              PLAYER_DEAD=utilz.Constante.PlayerConstansts.DEAD,
	              ENEMY_ATTACK=utilz.Constante.EnemyConstants.Robot.ATTACK_MIC,
	              ENEMY_ATTACK_COMBAT=utilz.Constante.EnemyConstants.Robot.ATTACK,
	              ENEMY_RUN=utilz.Constante.EnemyConstants.Robot.RUN2,
	              ENEMY_DEAD=utilz.Constante.EnemyConstants.Robot.DEAD,
	              ENEMY_IDLE_PLAY=utilz.Constante.EnemyConstants.Robot.IDLE_PLAY,
	              ENEMY_IDLE=utilz.Constante.EnemyConstants.Robot.IDLE,
	              ENEMY_SHOOT=utilz.Constante.EnemyConstants.Robot.SHOOT,
	              
	              JUMP=utilz.Constante.PlayerConstansts.JUMP;
	
	
	protected int GRENADE=1,
                  ATAC_CONCENTRAT=2,      
	              ATAC_SIMPLU=3,
	              WAIT=6,
	              RECHARGE=7,
	              HEAL=9,
	              ENERGIE=10;
	
	
	      
	              
	public Objects_Combat(Game game) {
		
		this.setGame(game);
		InitializeAnimations();
	   
	    this.InitializeFinalPozitions();
	}
	
	private void InitializeFinalPozitions() {
		    
		    //Initialize the poztion of the bullets based on the attack and based on the robot that is attacked
		    // Grenade will always be trown in the middle so that it is easier to calculate
		    
		
		    // Punctele de la care pornesc atacurile in momentul tragerii bazat pe animatiile pe care le avem in momentul asta
		    // Nu pot fi calculate cu o formula din pacate deoarece nu avem o functie de identificare a armei 
		    //care sa identifice exact pozitia respectiva
		   
		    Foc[2][1]=new Point(517,440);
	        Foc[2][2]=new Point(520,400);
	        Foc[2][3]=new Point(516,387);
	        Foc[1][1]=new Point(517,427);
	        Foc[1][2]=new Point(520,400);
	        Foc[1][3]=new Point(516,387);
		   
	        this.bullets_enemy[1]=new Point(813,543);
	        this.bullets_enemy[2]=new Point(972,390);
	        this.bullets_enemy[3]=new Point(813,314);
	        setCaract_poz_final(new Point(340,355));
	        
	        int x=990;
	        int y=343;
	        int offx=160;
	        int offy=120;
	        int off2=20;
	        enemies_poz_final[0]=new Point(x-offx,y+offy+off2);
	        enemies_poz_final[1]=new Point(x-offx,y+offy+off2);
	        enemies_poz_final[2]=new Point(x,y);
	        enemies_poz_final[3]=new Point(x-offx,y-offy);
	}
	
	
	  private void InitializeAnimations() {
		  
		  
		  
		  
	       String filePath=utilz.Constante.path_name;
		  
	       cerc_inamic=getGame().loadImageFromFile(filePath+"cerc_rosu.png");
	       cerc_player=getGame().loadImageFromFile(filePath+"cerc_player.png");
	       load=getGame().loadImageFromFile(filePath+"loading.jpg");
	   	   background=getGame().loadImageFromFile(filePath+"arena.png");
	      this.Bullet_Enemy=getGame().loadImageFromFile(filePath+"Robot/alb/Shoot 2/jelly splash 1.png");
	      Bullet_Enemy=getGame().getCaracter().resizeImage(Bullet_Enemy,60, 60);
	   	   
	   	   
	   	   greneda[1]=getGame().loadImageFromFile(filePath+"grenada.png");
	       
	       atac_concentrat=getGame().loadImageFromFile(filePath+"Caracter/gri/Gun animation/foc1.png");
	       atac_simplu=getGame().loadImageFromFile(filePath+"Caracter/gri/Gun animation/foc2.png");
	       
	     
	       
	       for(int i=1;i<=3;i++) {
	    	   Bullet_Orientation[1][i]=game.loadImageFromFile(utilz.Constante.path_name + "Caracter/Foc_arme1/"+ String.valueOf(i) + ".png"); 	 
	    	   Bullet_Orientation[2][i]=game.loadImageFromFile(utilz.Constante.path_name + "Caracter/Foc_arme/"+ String.valueOf(i) + ".png"); 	 
	       }
	       
	       //Animatiile grenadei;
	       for(int i=2;i<=9;i++) 
	       greneda[i]=getGame().loadImageFromFile(filePath+"Caracter/gri/Grenade animation/"+ (i+6) +".png");
	       
	       PlayerAnim=getGame().getCaracter().setSpriteAnimation(0,filePath+"Caracter/","gri",PlayerAnim);
		   
	       
	       // Animatile OZN
	       for(int i=1;i<=4;i++)
	       OZN[i]=getGame().loadImageFromFile(utilz.Constante.path_name + "nava"+String.valueOf(i)+".png"); 
	       
		}
		
		
		
		
					
				 
					
				
			
			
		
	  
	
	
	
	
       

		public Game getGame() {
			return game;
		}

		public void setGame(Game game) {
			this.game = game;
		}

		public BufferedImage[] getOZN() {
			return OZN;
		}

		public void setOZN(BufferedImage[] oZN) {
			OZN = oZN;
		}

		

		public Point getCaract_poz_final() {
			return caract_poz_final;
		}

		public void setCaract_poz_final(Point caract_poz_final) {
			this.caract_poz_final = caract_poz_final;
		}
}
