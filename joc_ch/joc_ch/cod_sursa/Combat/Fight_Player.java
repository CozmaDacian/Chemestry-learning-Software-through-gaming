package Combat;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static utilz.Constante.EnemyConstants.Robot.GetEnemy;

import static utilz.Constante.PlayerConstansts.GetSpeed;
import static utilz.Help_Methods.update_hitbox_glont;
import static utilz.Help_Methods.update_enemy_poz;
import static utilz.Constante.PlayerConstansts.GetSprite;
import main_game.Caracter;
import main_game.Enemies;

public class Fight_Player {
private int speed_animatie=15;
private int indice_grenada=1;
private Rectangle Hitbox_enemy=new Rectangle();
private Rectangle Hitbox_caracter=new Rectangle();
private  Rectangle Hitbox_glont =new Rectangle();
private boolean grenada_travel=false,grenade_pusca=false,turning=false;
private boolean glont_travel=false,caracter_attacked=false,caracter_hit=false;
private int tick=0,tick_bullet=0;
private float dist_x,dist_y;
private Rectangle grenada_detonation=new Rectangle(765,305,100,400);
private void initialize_glont(int turn,int atac,int atacat,Combat combat) {
		// Initializam glontul bazat pe atacul playerului
                 
		if(turn==0) {
			
			
			if(atac==1) {
			Hitbox_glont.width=30;
			Hitbox_glont.height=30;
			Hitbox_glont.x=(int) combat.Foc[atac][atacat].getX();
			Hitbox_glont.y=(int) combat.Foc[atac][atacat].getY();
			}
			if(atac==2) {
				Hitbox_glont.width=30;	
				Hitbox_glont.height=30;
				Hitbox_glont.x=(int) combat.Foc[atac][atacat].getX();
				Hitbox_glont.y=(int) combat.Foc[atac][atacat].getY();
			}
			if(atac==3) {
				Hitbox_glont.width=40;
				Hitbox_glont.height=30;
				Hitbox_glont.x=600;
				Hitbox_glont.y=400;
			}
		}
    }

    private void update_tick(Caracter caracter,Combat combat) {
    	
    	if(caracter.getPlayerAction()==combat.GUNIDLE)
    		return;
    	int increase_anim=1;
    	if(caracter_attacked)
    		increase_anim=-1;
    
    	int atacat=combat.atacat;
        int animatie_max=0;
    	if(atacat==1)
    		animatie_max=3;
    	
    		if(atacat==2)
    			animatie_max=5;
    	   if(atacat==3)
    		   animatie_max=7;
    	// Se activeaza doar in cazul tragerii cu proiectile, unde calculeaza bazat
    	tick++;
		int speed=GetSpeed(caracter.getPlayerAction());
	    
		if(tick>speed) {
			
			caracter.animatie+=increase_anim;
			tick=0;
			
			if(caracter.animatie>animatie_max) {
				caracter.animatie=animatie_max;
				tick=0;
			    caracter_attacked=true;
			}
			
			if(caracter.animatie==0)
				caracter.setPlayerAction(combat.GUNIDLE);
			
			
		}
    	
    	
    }
    
    private Point update_speed_bullet(Combat combat) {
		int xspeed=4;
		int yspeed=1;
		tick_bullet=(tick_bullet+1)%2;
	  if(combat.atacat==1) {
		  if(tick_bullet==0)
		  xspeed=3;
		  else
		  xspeed=4;
		  if(tick_bullet==0)
		  yspeed=1;
		  else
		yspeed=2;
	  }
		if(combat.atacat==2)
			yspeed=0;
		
		if(combat.atacat==3)
			xspeed=4;
		return new Point(xspeed,yspeed);
	}
	
	private Point update_enemy_collision_max_point(Enemies enemy,Combat combat) {
		
		
		//updateaza punctul maxim de intersectie in caz ca este nevoie de el
		Rectangle hitbox=enemy.getEnemy_hitbox_combat();
		
		int x_enemy=hitbox.x;
		int y_enemy=hitbox.y;
		int width=hitbox.width;
		int height=hitbox.height;
		
		return new Point(x_enemy+width/2,y_enemy+height/2);
		
	}
	
    
	public void update_atac(Combat combat,Caracter caracter,Enemies enemy) {
		int atac=combat.atac;
		if(atac==1 || atac==2) {
			this.update_atac_gloante(combat, caracter, enemy);
		}
		if(atac==3) {
			this.update_atac_grenada(combat, caracter);
		}
	}
	public void update_dead_animation(Enemies enemy) {
		
		
	
	    
		
		enemy.UpdateTick();
	  
	   
	}
		
	
	/**
	 * Updates the bullet attack sequence for the character.
	 *
	 * @param combat The current combat instance.
	 * @param caracter The character executing the bullet attack.
	 * @param enemy The enemy being attacked.
	 */
	public void update_atac_gloante(Combat combat, Caracter caracter, Enemies enemy) {
	    // Retrieve hitboxes for character and enemy
	    Hitbox_enemy = enemy.getEnemy_hitbox_combat();
	    Hitbox_caracter = caracter.getHitBoxCombat();

	    // If the character hasn't been attacked yet, update tick
	    if (!caracter_attacked) {
	        update_tick(caracter, combat);
	    } else {
	        // If character has been attacked, initialize bullet travel
	        if (!glont_travel && !caracter_hit) {
	            glont_travel = true;
	            this.initialize_glont(combat.turn, combat.atac, combat.atacat, combat);
	        }
	    }

	    // If bullet is traveling
	    if (glont_travel) {
	        // Update tick and calculate bullet speed
	        update_tick(caracter, combat);
	        int xGlont_speed = this.update_speed_bullet(combat).x;
	        int yGlont_speed = this.update_speed_bullet(combat).y;

	        // Calculate enemy final collision point
	        int enemy_x_final = this.update_enemy_collision_max_point(enemy, combat).x;
	        int enemy_y_final = this.update_enemy_collision_max_point(enemy, combat).y;

	        // Update bullet hitbox
	        update_hitbox_glont(xGlont_speed, yGlont_speed, Hitbox_glont, enemy_x_final, enemy_y_final);

	        // Check if bullet hits the enemy
	        if (Hitbox_glont.intersects(Hitbox_enemy)) {
	            glont_travel = false;
	            caracter_hit = true;
	            enemy.animatie = 2;

	            // Calculate damage and apply to enemy health
	            int damage = caracter.getStatistici().GetAtacDamage(combat.atac);
	            int defense = enemy.getStatistici().getDefense_enemy();
	            int hp = enemy.getStatistici().getRemaining_health();

	            if (hp - damage + defense <= 0) {
	                enemy.setEnemyAction(combat.ENEMY_DEAD);
	                enemy.getStatistici().setRemaining_health(0);
	            } else
	                enemy.getStatistici().setRemaining_health(hp - damage + defense);

	            // Move enemy after being hit
	            enemy.setX(enemy.getX() + 30);
	        }
	    }

	    // If character is hit
	    if (caracter_hit) {
	        // Update tick
	        update_tick(caracter, combat);

	        // Get attacked and current turn information
	        int atacat = combat.atacat;
	        int turn = combat.getTurn();

	        // Get enemy final position
	        int xenemy_fin = combat.enemies_poz_final[atacat].x;

	        // If enemy is dead, update dead animation
	        if (enemy.getEnemyAction() == combat.ENEMY_DEAD) {
	            int animatie = enemy.animatie;
	            this.update_dead_animation(enemy);
	            if (enemy.animatie != animatie && enemy.animatie == 1) {
	                enemy.animatie = animatie;
	                enemy.move_stg = true;
	                combat.setTurn(turn + 1);
	                this.reinitialize_variable();
	            }
	        } else if (enemy.getX() - 1 == xenemy_fin) {
	            // If enemy reaches final position, update turn and reset variables
	            enemy.setX(xenemy_fin);
	            combat.setTurn(turn + 1);
	            enemy.animatie = 1;
	            this.reinitialize_variable();
	            combat.lupta.start_animatie = false;
	        } else
	            // Move enemy towards final position
	            enemy.setX(enemy.getX() - 1);
	    }
	}
	
	
    
    public void reinitialize_variable() {
	caracter_hit=false;
   this.glont_travel=false;
   this.caracter_attacked=false;
   grenada_travel=false;
   grenade_pusca=false;
   dist_x=0;
   dist_y=0;
   tick=0;
}


    /**
     * Updates the grenade attack sequence for the character.
     *
     * @param combat   The current combat instance.
     * @param caracter The character executing the grenade attack.
     */
    public void update_atac_grenada(Combat combat, Caracter caracter) {
        int animatie = caracter.animatie;

        // If the character hasn't been attacked yet
        if (!caracter_attacked) {
            caracter.UpdateTick();
            // Check if character animation has changed, indicating grenade launch
            if (animatie != caracter.animatie) {
                if (caracter.animatie == 1) {
                    // Set player action to gun idle and initialize grenade travel
                    caracter.setPlayerAction(combat.GUNIDLE);
                    caracter_attacked = true;
                    grenada_travel = true;
                    this.initialize_glont(combat.turn, combat.atac, combat.atacat, combat);
                }
            }
        }

        // If grenade is traveling
        if (grenada_travel) {
            float x = Hitbox_glont.x;
            float y = Hitbox_glont.y;
            int speedx = 4;
            // Calculate grenade trajectory
            dist_x += speedx;
            dist_y += 0.09;
            float speedy = -(dist_y * dist_y);

            // Check if grenade reaches detonation point
            if (grenada_detonation.contains(x + dist_x, x + speedy)) {
                grenada_travel = false;
                grenade_pusca = true;
                dist_y = -180;
                dist_x = dist_x - 40;
                tick = 0;
            }
        }

        // If grenade is detonating
        if (grenade_pusca) {
            this.update_tick_grenada(combat);
        }

        // If turning back
        if (turning) {
            boolean gata = true;
            // Update enemy positions and check if all enemies have reached final positions
            for (int i = 1; i <= 3; i++) {
                int x_final = combat.enemies_poz_final[i].x;
                int y_final = combat.enemies_poz_final[i].y;
                int x_speed = 1;
                int y_speed = 1;
                Enemies enemy = combat.getCombat_enemy(i);
                
                // Update enemy position
                update_enemy_poz(enemy, x_final, y_final, x_speed, y_speed);

                // Check if enemy has reached final position or is dead
                if ((int) enemy.getX() != x_final || (int) enemy.getY() != y_final)
                    gata = false;

                // If enemy is dead, update dead animation
                if (enemy.getEnemyAction() == combat.ENEMY_DEAD) {
                    animatie = enemy.animatie;
                    this.update_dead_animation(enemy);
                    if (enemy.animatie != animatie && enemy.animatie == 1) {
                        enemy.animatie = animatie;
                        enemy.move_stg = true;
                        gata = true;
                    } else
                        gata = false;
                }
            }
            
            // If all enemies have reached final positions, finish turning back
            if (gata == true) {
                turning = false;
                combat.lupta.start_animatie = false;
                int turn = combat.getTurn();
                combat.setTurn(turn + 1);
                this.reinitialize_variable();
            }
        }
    }
   
   public void draw_combat_elements(Graphics2D g,int atac,int atacat,Combat combat,Enemies enemy) {
	   BufferedImage proiectil;
	   
	   Hitbox_enemy=enemy.getEnemy_hitbox_combat();
	   if(atac==1 || atac==2) {
		   
		   if(!this.glont_travel)
		   return;
		   proiectil=combat.Bullet_Orientation[atac][atacat];
		   int x=Hitbox_glont.x;
		   int y=Hitbox_glont.y;
		   g.drawRect(Hitbox_enemy.x,Hitbox_enemy.y,Hitbox_enemy.width,Hitbox_enemy.height);
		   g.drawRect(x, y, Hitbox_glont.width,Hitbox_glont.height);
		   g.drawImage(proiectil,x,y,null);
	   }
	   if(atac==3) {
		   if(!this.grenada_travel && !this.grenade_pusca)
			   return;
		   int x=Hitbox_glont.x+(int) dist_x;
		   int y=Hitbox_glont.y+(int) dist_y;
	   proiectil=combat.greneda[this.indice_grenada];
	   g.drawImage(proiectil, x, y, null);
	   }
   }
   


/**
 * Updates the animation tick for the grenade explosion.
 *
 * @param combat The current combat instance.
 */
private void update_tick_grenada(Combat combat) {
    // Increment tick count
    tick++;

    // Check if animation tick exceeds speed limit
    if (tick > speed_animatie) {
        tick = 0;
        indice_grenada++;

        // Update enemy positions during grenade explosion animation
        for (int i = 1; i <= 3; i++) {
            if (combat.getCombat_enemy(i).getEnemyAction() != combat.ENEMY_DEAD) {
                float x = combat.getCombat_enemy(i).getX();
                float y = combat.getCombat_enemy(i).getY();

                // Adjust enemy position based on explosion direction
                if (i == 1) {
                    y = y + 3;
                }
                if (i == 2) {
                    x = x + 3;
                }
                if (i == 3) {
                    y = y - 3;
                }

                // Update enemy position
                combat.getCombat_enemy(i).setX(x);
                combat.getCombat_enemy(i).setY(y);
            }
        }

        // Check if grenade explosion animation is complete
        if (indice_grenada > 7) {
            grenade_pusca = false;
            turning = true;
            indice_grenada = 1;

            // Calculate damage and apply to enemies
            int damage = combat.getGame().getCaracter().getStatistici().GetAtacDamage(combat.atac);
            for (int i = 1; i <= 3; i++) {
                if (combat.getCombat_enemy(i).getEnemyAction() != combat.ENEMY_DEAD) {
                    int defense = combat.getCombat_enemy(i).getStatistici().getDefense_enemy();
                    int rhp = combat.getCombat_enemy(i).getStatistici().getRemaining_health();

                    // Apply damage to enemy health, considering defense
                    if (rhp - damage + defense > 0) {
                        rhp = rhp - damage + defense;
                    } else {
                        rhp = 0;
                        // Set enemy action to dead if health reaches zero
                        combat.getCombat_enemy(i).setEnemyAction(combat.ENEMY_DEAD);
                    }

                    // Update enemy health
                    combat.getCombat_enemy(i).getStatistici().setRemaining_health(rhp);
                }
            }
        }
    }
 } 
}