package Combat;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main_game.Enemies;
import main_game.Game;
import static utilz.Help_Methods.GetScreenY;
import static utilz.Constante.PlayerConstansts.GetSpeed;
import static utilz.Constante.PlayerConstansts.GetSprite;

import states.metode;

public class Phase_1  {
    int tick=0;
    int animatie_ozn=1;
    boolean tracting_both=false;
    boolean tracting_one=false;
    boolean intersection=false;
   
    BufferedImage background;
    int speed_animatie_ozn=50;
  
    Rectangle Hitbox_ozn=new Rectangle(0,0,0,0);
    Rectangle Hitbox_caracter=new Rectangle(0,0,0,0);
    Rectangle Hitbox_Enemy=new Rectangle(0,0,0,0);
   
    boolean caract_in_poz=false,ozn_in_poz=false,enemy_in_poz=false;
    boolean in_loading_screen,in_combat;
	boolean phase_1=true;
	int yoz=-1;
	boolean moving_stg;
	int yLevelOffset,xLevelOffset=0;
    Combat combat;
	Phase_1(Combat combat){
		this.combat=combat;
		
	   
	    
	    }
         	
	
	
	public void draw_before_combat(Graphics2D g) {
	   
		
		// deseneaza grafica jocului, background caracterul itemele etc     
		
        combat.getGame().getPlay().draw(g);
        
       // g.drawImage(combat.getEnemy_phase1().enemy[0][enemy_action][animatie],0,0, null);
		combat.getEnemy_phase1().draw(g);
		 
                                                                                       
		
		
		g.drawImage(combat.OZN[animatie_ozn],Hitbox_ozn.x-xLevelOffset,Hitbox_ozn.y-yLevelOffset ,null);
	}
    
	public void updateHitboxes_before_combat() {
		
		
		
		// updateaza hitboxurile in caz ca ar iesi vreun caracter din background
		 xLevelOffset=combat.getGame().getCaracter().getxLvlOffset();
		 yLevelOffset=combat.getGame().getCaracter().getyLvlOffset();
		  
		 // updateaza poztia,animatia, verifica daca este in aer caracterul 
		  combat.getGame().getCaracter().update();
		  if(tracting_both || tracting_one)
			  return;
		
		  // Initializam hitboxurile caracterului si a inamicului
		  Hitbox_Enemy=combat.getEnemy_phase1().getHitboxEnemy();
		  Hitbox_caracter=combat.getGame().getCaracter().getHitBox();
		
		  
		  // Verificam directia in care caracterul ar trebui sa o ia 
		if(Hitbox_Enemy.x>Hitbox_caracter.x)
		{
			moving_stg=false;
		combat.getEnemy_phase1().move_stg=true;
		combat.getGame().getCaracter().setMovingdr(true);
		}
		else
		{
			combat.getGame().getCaracter().setMovingdr(false);
			combat.getEnemy_phase1().move_stg=false;
	    moving_stg=true;
		}
		
		  
		  int xoz=(int) combat.getGame().getCaracter().getX()-100;
		
		
		
		
		//updatam pozitia de start a onz
	    if(yoz==-1)
		{
	    	yoz=combat.getGame().getCaracter().getY()-700;
		}
	    else
	    // updatam pozitia ozn se misca cu 120 de pixeli pe secunda
		yoz=yoz+1;
	   
	    
		int heightOZ=combat.OZN[animatie_ozn].getHeight();
		int widthOZ=combat.OZN[animatie_ozn].getWidth();
	    // updatam hitobxul ozn
		Hitbox_ozn.setBounds(xoz, yoz, widthOZ, heightOZ);
	    float speed_inamic=1.6f;
	    
	   
	    
	    
	  
	    
	   
	    
	 
	  
	    
	    // Aducem inamicii unul langa altul pentru tractarea in ozn
	    if(!Hitbox_Enemy.intersects(Hitbox_caracter)) {
	    	if(Hitbox_Enemy.x>Hitbox_caracter.x)
	    	combat.getEnemy_phase1().UpdatePos(-speed_inamic);
	    	else
	        combat.getEnemy_phase1().UpdatePos(-speed_inamic);
	    	
	    }
	    
	        // verificam daca inamicii sunt in range pentru a veadea daca schimbam animatia inamicului
	        Rectangle in_range=new Rectangle();
	        in_range.x=Hitbox_Enemy.x-Hitbox_Enemy.width;
	        in_range.width=Hitbox_Enemy.width*2;
	        in_range.y=Hitbox_Enemy.y;
	        in_range.height=Hitbox_Enemy.height;
	        
	        if(intersection==false) 
	        if(in_range.intersects(Hitbox_caracter))
	    	{
	          	combat.getEnemy_phase1().setEnemyAction(combat.ENEMY_ATTACK);
	    	    intersection=true;
	    	}
	        
	        
	    // Functia verifica daca atat inamicul cat si playerul sunt tractati in caz afimrativ tractin_both=true
	    // altfel tracteaza doar playerul, ar trebui sa se apeleze foarte rar, daca vreodata, caz pus in caz de siguranta
	    if(Hitbox_caracter.intersects(Hitbox_ozn) && Hitbox_ozn.intersects(Hitbox_Enemy))
	    {
	    	tracting_both=true;
	    	 combat.getEnemy_phase1().setEnemyAction(combat.ENEMY_IDLE_PLAY);
	       combat.getGame().getCaracter().setPlayerAction(combat.GUNIDLE);
	       
	    }
	    else
	    	if(Hitbox_caracter.intersects(Hitbox_ozn)) {
	    		tracting_one=true;
	    		combat.getGame().getCaracter().setPlayerAction(combat.GUNIDLE);
	    	   combat.getEnemy_phase1().setEnemyAction(combat.ENEMY_IDLE_PLAY);
	   	     
	    	}
	    
	}
	public void update_animations_before_combat() {
		
		
		// updatam tickurie caracterului si ale ozn
		// animatiile caracterului sunt update in update de la clasa caracter
		if(tracting_one==true || tracting_both==true)
			return;
		if(in_loading_screen==true || in_combat==true)
			return; 
		combat.getEnemy_phase1().UpdateTick();
		
		update_animatie_ozn();
		
	}
	public void update_animatie_ozn() {
          tick++;    
          // Updateaza animatiile ozn daca animatia>4 animatia ramane 4
		if(tick>this.speed_animatie_ozn) {
			
			tick=0;
			animatie_ozn++;
			if(animatie_ozn>combat.animatii_ozn)
			{
				animatie_ozn=combat.animatii_ozn;
			}
			
		}
	}
	
	
	public void update_initial_combat() {
		
		// Pozitiile initiale ale hitboxurilor in momentul in care se face tranzitia intre loading screen
		// si combat first phase
		Hitbox_ozn.x=210;
		Hitbox_ozn.y=80;
		Hitbox_caracter.x=Hitbox_ozn.x+130;
		Hitbox_caracter.y=Hitbox_ozn.y+60;
		Hitbox_Enemy.x=320;
		Hitbox_Enemy.y=Hitbox_ozn.y+70;
	   animatie_ozn=3;
	}
	

	private void update_enemy_poz(Enemies enemy,int xfinal,int yfinal) {
		// Tinand cont de pozitia inamicului si de pozitia finala aducfe caracterul cat mai aproape de 
		// aceea poztitie
		
		
		int xen=(int) enemy.getX();
		int yen=(int) enemy.getY();
		int yspeed=2;
		int xspeed=2;
		if(yen>yfinal)
		{
			if(yen-yspeed>yfinal)
				yen=yen-yspeed;
			else
				yen=yfinal;
		}
		else
	    if(yen<yfinal) {
	    	if(yen+yspeed<yfinal)
	    		yen+=yspeed;
	    	else
	    		yen=yfinal;
	    }
		
		if(xen>xfinal)
		{
			if(xen-xspeed>xfinal)
				xen=xen-xspeed;
			else
				xen=xfinal;
		}
		else
	    if(xen<xfinal) {
	    	if(xen+xspeed<xfinal)
	    		xen+=xspeed;
	    	else
	    		xen=xfinal;
	    }
		// La final updatam inamicul
		enemy.setX(xen);
		enemy.setY(yen);
		
	}
	
	public void update_enemy_in_poz() {
		if(!ozn_in_poz)
			return;
		int y=combat.enemies_poz_final[2].y;
		float yspeed=1.5f;
		// Verificam daca cei trei inamici sunt in pozitia y 
		if(Hitbox_Enemy.y!=y) {
			Hitbox_Enemy.y+=yspeed;
			if(Hitbox_Enemy.y>y)
				Hitbox_Enemy.y=y;
			for(int i=1;i<=3;i++)
			combat.getCombat_enemy(i).setY(Hitbox_Enemy.y);
              
		}
		else
		{
			// Daca inamicii sunt coborati in pozitia initiala y updatam ceilalti 2 inamici 
			animatie_ozn=2;
			this.enemy_in_poz=true;
			if(Hitbox_ozn.y>-400)
			Hitbox_ozn.y-=3;
			for(int i=1;i<=3;i++) {
				int x=combat.enemies_poz_final[i].x;
				int y1=combat.enemies_poz_final[i].y;
				update_enemy_poz(combat.getCombat_enemy(i),x,y1);
				
				if(combat.getCombat_enemy(i).getEnemyAction()!=combat.ENEMY_DEAD)
				if((int)(combat.getCombat_enemy(i).getX())!=x || (int) (combat.getCombat_enemy(i).getY())!=y1) {
				enemy_in_poz=false;
				
				}
				
				
			}
			if(enemy_in_poz) {
				this.update_animatii_based_on_pozition();
			}
		}
		
		
	}
	public void update_animatii_based_on_pozition() {
		int enemy_action=combat.ENEMY_SHOOT;
		int animatie=2;
		String culoare;
		for(int i=1;i<=3;i++) {
			combat.getCombat_enemy(i).randomize_attacks();
			
		}
		//updateaza animatia robotului de tras, daca acesta este sus sau jos sau la mijloc
		int type=combat.getEnemy_phase1().type;
		if(type==1) {
			culoare="rosu";
		}
		else
		if(type==0)
		{
			culoare="alb";
		}
		else
			return;
		String path_file=utilz.Constante.path_name+"Robot/"+culoare+"/Shoot 2/";
		
		String path_name_glont=utilz.Constante.path_name+"Robot/alb/Gloante/";
		
	
		int width=combat.getCombat_enemy(3).enemy[type][enemy_action][1].getWidth();
		int height=combat.getCombat_enemy(3).enemy[type][enemy_action][1].getHeight();
		combat.getCombat_enemy(3).enemy[type][enemy_action][2]=combat.getGame().getCaracter().resizeImage( 
		combat.getGame().loadImageFromFile(path_file+"atacjos.png"),width, height); 
		
	
		combat.getCombat_enemy(1).enemy[type][enemy_action][2]=combat.getGame().getCaracter().resizeImage( 
		combat.getGame().loadImageFromFile(path_file+"atacsus.png"),width, height); 
		
		
		combat.getCombat_enemy(1).enemy[type][enemy_action][3]=combat.getCombat_enemy(1).enemy[type][enemy_action][2];
		combat.getCombat_enemy(3).enemy[type][enemy_action][3]=combat.getCombat_enemy(1).enemy[type][enemy_action][2];
		
		
		
		
		// Initializam atacurile inamicilor
		
		
		
	}
	public void update_ozn_in_poz() {
		if(ozn_in_poz || !caract_in_poz)
			return;
		int x=combat.enemies_poz_final[2].x-130;
		// verificam daca ozn este in pozitia inamicului 
		if(Hitbox_ozn.x+2<x) {
			Hitbox_ozn.x+=2;
		}
		else
		{
			// daca sunt in acea pozitie initializam inamicii
	     Hitbox_ozn.x=x;
	     Hitbox_Enemy.y=Hitbox_ozn.y+150;
	     Hitbox_Enemy.x=combat.enemies_poz_final[2].x;
	     int type=combat.getEnemy_phase1().type;
	     int number_of_enemies=combat.getEnemy_phase1().number_of_enemies;
	     System.out.println(number_of_enemies);
	     combat.lupta.number_of_enemies=combat.getEnemy_phase1().number_of_enemies;
	   
	     for(int i=1;i<=3;i++) {
	    	 Enemies enemy=new Enemies(Hitbox_Enemy.x,Hitbox_Enemy.y,type,number_of_enemies,combat.getGame());
	    	 
	    	 if(number_of_enemies==1 && i==2)
	    	 enemy.setEnemyAction(combat.ENEMY_IDLE);
	    	 else
	    	 {
	    		if(number_of_enemies==1){
	    			enemy.setEnemyAction(combat.ENEMY_DEAD);
	    		   enemy.animatie=5;
	    		}
	    	    if(number_of_enemies==2) {
	    	    	if(i<=number_of_enemies) {
	    	    		enemy.setEnemyAction(combat.ENEMY_IDLE);
	    	    		
	    	    	}
	    	    	else
	    	    		{
	    	    		enemy.setEnemyAction(combat.ENEMY_DEAD);
	    	    		enemy.animatie=5;
	    	    		}
	    	    }
	    	    if(number_of_enemies==3) {
	    	    	enemy.setEnemyAction(combat.ENEMY_IDLE);
	    	    }
	    		
	    	 }
	   
	    	 combat.setCombat_enemy(enemy, i);
	    	 
	    	
	    	 
	    	 
	     }
	     ozn_in_poz=true;
		}
	}
	
	public void update_caracter_in_combat() {
		
		// updatam caracterul pana ce este in pozitia caract x si in pozitia y 
		
		if(caract_in_poz)
			return;
		
		int x=combat.getCaract_poz_final().x;
		int y=combat.getCaract_poz_final().y;
		
		int xspeed=1;
		float yspeed=1.5f;
		if(Hitbox_caracter.x<x) {
			Hitbox_caracter.x+=xspeed;
		}
		if(Hitbox_caracter.x>x) {
			Hitbox_caracter.x-=xspeed;
		}
		
		if(Hitbox_caracter.y+yspeed<y) {
			Hitbox_caracter.y+=yspeed;
		}
		else
			Hitbox_caracter.y=y;
		
		if(Hitbox_caracter.x==x && Hitbox_caracter.y==y)
		{
		  caract_in_poz=true;	
		  
		}
		combat.getGame().getCaracter().setX(x);
		combat.getGame().getCaracter().setY(y);
		
		
	}
	
	public void reinitialize_tracting() {
		
		// resetarea tuturor animatiilor
		
		tracting_one=false;
	    tracting_both=false;
	    tick=0;
	    animatie_ozn=1;
	   in_loading_screen=false;
	   in_combat=false;
	   caract_in_poz=false;
	   enemy_in_poz=false;
	   ozn_in_poz=false;
	   this.intersection=false;
	   yoz=-1;
	}
	
	private void update_tracting_up() {
		if(tracting_one==false && tracting_both==false)
			return;
		float tract_speed=1.5f;
		int ozn_xspeed=2;
		
		//aflam directia ozn
		if(moving_stg)
		ozn_xspeed*=-1;
		
		
	
		int xenemy=(int) combat.getEnemy_phase1().getX()+70;
		int yenemy=(int) combat.getEnemy_phase1().getY();
		int ycaracter=combat.getGame().getCaracter().getY();
		int xcaracter=combat.getGame().getCaracter().getX();
		
	
		if(tracting_one) {
			// tragem caracterul pana intr-un punct anume iar apoi tractam inamicul  
			if(ycaracter>Hitbox_ozn.y+100) 
	         combat.getGame().getCaracter().setY(ycaracter-tract_speed);
			else	
		     if(Hitbox_ozn.x!=xenemy) { 
		    Hitbox_ozn.x+=ozn_xspeed;combat.getGame().getCaracter().setX(xcaracter+ozn_xspeed);
		    } 
		    else
		    	 combat.getEnemy_phase1().setY(yenemy-tract_speed);
		tracting_both=true; tracting_one=false;
		}
	    if(tracting_both) {
	    	// updatam toate pozitile pentru tractarea in sus 
	    	combat.getEnemy_phase1().setY(yenemy-tract_speed);
	    	combat.getGame().getCaracter().setY(yenemy-tract_speed);
	    	if(tick%2==0) 
	        Hitbox_ozn.y-=2;
	        else
	        Hitbox_ozn.y-=1;
	    	
	    }
		
		
	}
		
	
				
	
	public void render_ozn() {
		   
		if(in_loading_screen || in_combat) 
			return; 
		 
	
		
		    int xOznSpeed=2;
		    float tract_speed=2.5f;
		    float ycaracter=combat.getGame().getCaracter().getY();
			float yEnemy=combat.getEnemy_phase1().getY();
			float yOzn=(int) Hitbox_ozn.getY();
		    update_animations_before_combat();
		    updateHitboxes_before_combat();
			update_tracting_up();
			// verificam daca caracterul este complet in afara ecranului in caz afirmativ intram in loading screen 
		   if(ycaracter+combat.getGame().getCaracter().getHitBox().height-yLevelOffset<0) {
			  this.reinitialize_tracting();
			   in_loading_screen=true;
		       
		   }
	}
	private void render_combat() {
		
		this.update_caracter_in_combat();
		this.update_ozn_in_poz();
		this.update_enemy_in_poz();
	}
	
	public void render() {
		// TODO Auto-generated method stub
		
		if(in_loading_screen) {
			
			tick++;
			if(tick>100) {
				
				in_loading_screen=false; 
				in_combat=true;
				update_initial_combat();
				tick=0;
			}
			
		}
					
		if(!in_loading_screen && !in_combat) {
			render_ozn();
		}
		
	   if(in_combat) {
		   render_combat();
		
	   if(enemy_in_poz && ozn_in_poz && caract_in_poz) {
		   this.reinitialize_tracting();
		   combat.setPhase_1(false);
		  combat.abilitati.set_enabled(true);
		  combat.getGame().getCaracter().animatie=1;
		   
		
	   }
	   }
		
		
	}

    public void draw_in_combat(Graphics2D g) {
    	
    	// desenam inamicii caracterul si ozn 
    	
    	int caract_action=combat.getGame().getCaracter().getPlayerAction();
    	int animatie=1;
    	int enemy_action=combat.ENEMY_IDLE;
    	g.drawImage(combat.background,0,0, null);
    	g.drawImage(combat.getGame().getCaracter().PlayerAnim_Combat[combat.getGame().getCaracter().cul_car][caract_action][animatie]
    		,Hitbox_caracter.x,Hitbox_caracter.y,null);
        
    	g.drawImage(combat.OZN[animatie_ozn],Hitbox_ozn.x,Hitbox_ozn.y, null);
    	
    	if(ozn_in_poz) {
    	    if(combat.getEnemy_phase1().number_of_enemies!=1) {
    		for(int i=1;i<=combat.getEnemy_phase1().number_of_enemies;i++) {
    			int x=(int)combat.getCombat_enemy(i).getX();
    			int y=(int) combat.getCombat_enemy(i).getY();
    			int type=combat.getCombat_enemy(i).type;
    			BufferedImage en=(combat.getCombat_enemy(i).enemy[type][enemy_action][1]);
    			g.drawImage(combat.getEnemy_phase1().flipImageVertical(en), x, y, null);
    			
    		}
    		
    	}
    	    else {
    	    	
    	    	int x=(int)combat.getCombat_enemy(2).getX();
    			int y=(int) combat.getCombat_enemy(2).getY();
    			int type=combat.getCombat_enemy(2).type;
    			BufferedImage en=(combat.getCombat_enemy(2).enemy[type][enemy_action][1]);
    			g.drawImage(combat.getEnemy_phase1().flipImageVertical(en), x, y, null);
    	    	
    	    }
    	}
    	
    }

	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub
		if(!in_loading_screen && !in_combat) {
		draw_before_combat(g);
		}
		if(in_loading_screen) {
		g.drawImage(combat.load, 0, 0, null);	
		}
	   if(in_combat) {
		   
			draw_in_combat(g);
	
		}
			
	}



	

	

}
