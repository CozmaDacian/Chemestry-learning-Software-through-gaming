package Combat;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main_game.Caracter;
import main_game.Enemies;
import states.GameState;

import static utilz.Constante.PlayerConstansts.GetSpeed;
import static utilz.Constante.PlayerConstansts.GetSprite;
import static utilz.Help_Methods.update_enemy_poz;
import static utilz.Help_Methods.update_hitbox_glont;
public class Fight_Enemy  {
    
	int tick=0;
	public boolean robot_attacking=false;
	public boolean robot_attacked=false;
	public boolean turning=false;
	public boolean glont_travel=false;
	public boolean glont_hit=false;
	public Rectangle Hitbox_glont=new Rectangle();
	public Rectangle Hitbox_caracter;
	
	
	public void update_poz_atac_enemy_robot(Combat combat,Enemies enemy,int atac,Caracter caracter) {
		
		//Rectangle Hitbox_enemy=enemy.getHitboxEnemy();
		//Rectangle Hitbox_caracter=caracter.getHitBox();
		
		float xspeed_enemy=3;
		float yspeed_enemy=1.5f;
		int offset_hit=10;
		if(atac==1) {
		this.update_atac1(combat, enemy, atac, caracter);
	    
			
		}
		if(atac==2) {
			
		update_atac2(combat,enemy,caracter);
			
			
		    	
			
		}

		
		
		
		
		
		
		
	}
	
	private void initialize_glont(int turn,int atac,int atacat,Combat combat) {
		
		if(turn==0) {
			
			Hitbox_glont.x=(int) combat.Foc[atac][atacat].getX();
			Hitbox_glont.y=(int) combat.Foc[atac][atacat].getY();
			if(atac==1) {
			Hitbox_glont.width=combat.atac_concentrat.getWidth();	
			Hitbox_glont.height=combat.atac_concentrat.getHeight();
			}
			if(atac==2) {
				Hitbox_glont.width=combat.atac_simplu.getWidth();	
				Hitbox_glont.height=combat.atac_simplu.getHeight();
			}
			
		}
		else{
			
			Hitbox_glont.width=combat.Bullet_Enemy.getWidth();
			Hitbox_glont.height=combat.Bullet_Enemy.getHeight();
			
			
			
			Hitbox_glont.x=combat.bullets_enemy[turn].x;
		    Hitbox_glont.y=combat.bullets_enemy[turn].y;
			
			
		if(combat.getCombat_enemy(turn).type==2) {
			Hitbox_glont.y+=40;
		}
		}
		
			
		
		
	}
	
	
	
	private Point update_speeds_attack_2(Combat combat) {
		int xspeed=4;
		int yspeed=1;
		tick=(tick+1)%2;
	  if(combat.getTurn()==1) {
		  if(tick==0)
		  xspeed=3;
		  else
		  xspeed=4;
		  if(tick==0)
		  yspeed=1;
		  else
		yspeed=2;
	  }
		if(combat.getTurn()==2)
			yspeed=0;
		
		if(combat.getTurn()==3)
			xspeed=4;
		return new Point(xspeed,yspeed);
	}
	
	private Point update_caracter_collision_max_point(Caracter caracter,Combat combat) {
		
		
		//updateaza punctul maxim de intersectie in caz ca este nevoie de el
		Rectangle hitbox=caracter.getHitBoxCombat();
		
		int width=hitbox.width;
		int height=hitbox.height;
		int x_caracter=hitbox.x;
		int y_caracter=hitbox.y;
		
		int turn=combat.getTurn();
		
		if(turn==1) {
			y_caracter=hitbox.y+height/2;
		    x_caracter=(int) (hitbox.x+hitbox.width/2);
		}
		if(turn==2)
		{
			x_caracter=hitbox.x+hitbox.width/2;
		}
		if(turn==3) {
			x_caracter=hitbox.x+hitbox.width/2;
			y_caracter=hitbox.y+height/2;
		}
		return new Point(x_caracter,y_caracter);
		
	}
	
	
	/**
	 * Updates the attack sequence for the enemy.
	 *
	 * @param combat   The current combat instance.
	 * @param enemy    The enemy whose attack sequence is being updated.
	 * @param caracter The character being attacked.
	 */
	private void update_atac2(Combat combat, Enemies enemy, Caracter caracter) {
	    int animatie_enemy = enemy.animatie;
	    int animatie_shoot = 3;
	    int xspeed, x_final;
	    int yspeed, y_final;
	    Rectangle Hitbox_caracter = caracter.getHitBoxCombat();

	    // If the robot hasn't attacked yet
	    if (!robot_attacked) {
	        // Update enemy animation until it's ready to shoot
	        if (animatie_enemy != animatie_shoot) {
	            enemy.UpdateTick();
	        } else {
	            // Robot is ready to attack, initialize bullet
	            robot_attacked = true;
	            glont_travel = true;
	            this.initialize_glont(combat.getTurn(), combat.atac, combat.atacat, combat);
	        }
	    }

	    // If bullet is traveling
	    if (glont_travel) {
	        if (enemy.getEnemyAction() != combat.ENEMY_IDLE) {
	            enemy.UpdateTick();
	        }
	        if (animatie_enemy != enemy.animatie) {
	            if (enemy.animatie == 1) {
	                enemy.setEnemyAction(combat.ENEMY_IDLE);
	            }
	        }

	        // Calculate bullet speeds and final positions
	        xspeed = this.update_speeds_attack_2(combat).x;
	        yspeed = this.update_speeds_attack_2(combat).y;
	        x_final = this.update_caracter_collision_max_point(caracter, combat).x;
	        y_final = this.update_caracter_collision_max_point(caracter, combat).y;
	        update_hitbox_glont(xspeed, yspeed, Hitbox_glont, x_final, y_final);

	        // Check for collision with character
	        if (Hitbox_glont.intersects(Hitbox_caracter)) {
	            glont_travel = false;
	            glont_hit = true;
	            int xcaracter = combat.getGame().getCaracter().getX();
	            combat.getGame().getCaracter().setX(xcaracter - 30);
	            this.check_if_dead(caracter, enemy, combat);
	        }
	    }

	    // If bullet hit character
	    if (glont_hit) {
	        if (enemy.getEnemyAction() != combat.ENEMY_IDLE) {
	            enemy.UpdateTick();
	        }
	        if (animatie_enemy != enemy.animatie) {
	            if (enemy.animatie == 1) {
	                enemy.setEnemyAction(combat.ENEMY_IDLE);
	            }
	        }
	        int turn = combat.getTurn();
	        int xcaracter = combat.getGame().getCaracter().getX();

	        // Move character and check for enemy turn
	        if (combat.lupta.player_dead == false) {
	            if (xcaracter + 1 == combat.getCaract_poz_final().x) {
	                combat.getGame().getCaracter().setX(xcaracter + 1);
	                turn++;

	                // Move to next living enemy if any
	                if (turn != 4) {
	                    while (combat.getCombat_enemy(turn).getEnemyAction() == combat.ENEMY_DEAD && turn < 4) {
	                        turn++;
	                        if (turn == 4)
	                            break;
	                    }
	                }

	                // Check for end of combat
	                if (turn == 4)
	                    combat.setTurn(0);
	                else
	                    combat.setTurn(turn);
	                reset_variables(combat);
	            } else
	                combat.getGame().getCaracter().setX(xcaracter + 1);
	        } else
	            this.update_death_animation(caracter,combat);
	    }
	}
	
	
	public void reset_variables(Combat combat) {
		combat.lupta.start_animatie=false;
		this.robot_attacked=false;
		this.robot_attacking=false;
		this.glont_hit=false;
		this.glont_travel=false;
		this.turning=false;
		tick=0;
	}
	
	public void draw_atac2(Graphics2D g,Combat combat) {
		
		if(!glont_travel)
		return;
		int x=this.Hitbox_glont.x;
		int y=this.Hitbox_glont.y;
		g.drawRect(combat.getGame().getCaracter().getX(), combat.getGame().getCaracter().getX(),100,100);
		g.drawRect(x, y, 30, 30);
		BufferedImage img=combat.Bullet_Enemy;
		g.drawImage(img, x, y, null);
	}
	
	public void check_if_dead(Caracter caracter,Enemies enemy,Combat combat) {
		
		int damage=0;
		if(combat.lupta.atac==1)
		 damage=enemy.getStatistici().getDamege_atac_1();
		else
		if(combat.lupta.atac==2)
			damage=enemy.getStatistici().getDamage_atac_2();
		
		
	
		int defense=caracter.getStatistici().getCurrent_defense()+caracter.getStatistici().getPlayer_defense();
		
		if(defense-damage>=0)
			damage=1;
		else
		damage=damage-defense;
		
		int remaining_hp=caracter.getStatistici().getRemaining_health();
		remaining_hp-=damage;
		
		
		
		
		turning=true;
		if(remaining_hp<=0)
		{caracter.setPlayerAction(combat.PLAYER_DEAD);
		remaining_hp=0;
		combat.lupta.player_dead=true;
		}
		else
	    
		caracter.setPlayerAction(combat.GUNIDLE);
		caracter.getStatistici().setRemaining_health(remaining_hp);
	}
	
	/**
	 * Updates the attack sequence for the enemy.
	 *
	 * @param combat  The current combat instance.
	 * @param enemy   The enemy whose attack sequence is being updated.
	 * @param atac    The type of attack being executed.
	 * @param caracter The character being attacked.
	 */
	
	
	
	public void update_atac1(Combat combat, Enemies enemy, int atac, Caracter caracter) {
	    Rectangle Hitbox_enemy = enemy.getEnemy_hitbox_combat();
	    Rectangle Hitbox_caracter = caracter.getHitBoxCombat();

	    int xspeed_enemy = 3;
	    int yspeed_enemy = 1;
	    int offset_hit = 8;

	    // Adjust enemy speed if it's in the middle position
	    if (combat.getTurn() == 2)
	        yspeed_enemy = 0;

	    // If the enemy hasn't attacked and there's no collision, update enemy position
	    if (!robot_attacked && !Hitbox_enemy.intersects(Hitbox_caracter) && !robot_attacking) {
	        enemy.UpdateTick();
	        update_enemy_poz(enemy, Hitbox_caracter.x, Hitbox_caracter.y + Hitbox_caracter.height / 4, xspeed_enemy, yspeed_enemy);
	    } else if (Hitbox_enemy.intersects(Hitbox_caracter) && !robot_attacking && !robot_attacked) {
	        // If there's collision and enemy isn't attacking, set to attack
	        enemy.setEnemyAction(combat.ENEMY_ATTACK_COMBAT);
	        robot_attacking = true;
	    }

	    // If enemy is attacking
	    if (robot_attacking) {
	        int animatie = enemy.animatie;
	        enemy.UpdateTick();
	        // Update player action and position during attack animation
	        if (enemy.animatie != animatie) {
	        	if(combat.getCombat_enemy(combat.getTurn()).type!=2) {
	            caracter.setPlayerAction(combat.JUMP);
	            
	            caracter.setX(Hitbox_caracter.x - offset_hit * animatie);
	        }
	        }
	        // Check if enemy attack animation is complete
	        if (enemy.animatie == 1 && enemy.animatie != animatie) {
	        	if(combat.getCombat_enemy(combat.getTurn()).type==2)
	        		caracter.setX(Hitbox_caracter.x-offset_hit*10);
	        	
	            check_if_dead(caracter, enemy, combat);
	            enemy.setEnemyAction(combat.ENEMY_RUN);
	            robot_attacking = false;
	            robot_attacked = true;
	        }
	    }

	    // If the enemy is turning back
	    if (turning) {
	        if (combat.lupta.player_dead) {
	            // Update death animation if player is dead
	            this.update_death_animation(caracter,combat);
	        }
	        enemy.UpdateTick();
	        // Turn enemy back towards player
	        this.turn_back(combat, enemy, caracter, xspeed_enemy, yspeed_enemy);
	    }
	}

	private void update_death_animation(Caracter caracter,Combat combat) {
		
	  
	  tick++;
	  if(tick>GetSpeed(caracter.getPlayerAction())) {
		  
		  caracter.animatie++;
		  if(caracter.animatie>GetSprite(caracter.getPlayerAction())) {
			  GameState.setCurrentOption(GameState.DEAD);  
			combat.lupta.reset_animations();
			this.reset_variables(combat);
			
		  }
		  
		  tick=0;
		  
	  }
		
	}
	/*
	 * 
	 * Turns the enemy and character back to their initial positions.
	 *
	 * @param combat  The current combat instance.
	 * @param enemy   The enemy being turned back.
	 * @param caracter The character being turned back.
	 * @param xspeed  The horizontal speed of the enemy.
	 * @param yspeed  The vertical speed of the enemy.
	 */
	public void turn_back(Combat combat, Enemies enemy, Caracter caracter, int xspeed, int yspeed) {
	    // Retrieve combat and character position information
	    int turn = combat.getTurn();
	    if(turn==4)
	    	return;
	    int xcaract_final = combat.getCaract_poz_final().x;
	    int y_enemy_final = combat.enemies_poz_final[turn].y;
	    int x_enemy_final = combat.enemies_poz_final[turn].x;

	    // Update enemy position to turn back
	    update_enemy_poz(enemy, x_enemy_final, y_enemy_final, xspeed, yspeed);

	    // If the character is not dead, update character position
	    if (caracter.getPlayerAction() != combat.PLAYER_DEAD) {
	        if (xcaract_final > 1 + caracter.getX()) {
	            caracter.setX(1 + caracter.getX());
	        } else
	            caracter.setX(xcaract_final);
	    }

	    // Check if both enemy and character have reached their initial positions
	    if ((int) enemy.getX() == x_enemy_final && (int) caracter.getX() == xcaract_final && (int) enemy.getY() == y_enemy_final) {
	        // Set enemy action to idle
	        enemy.setEnemyAction(combat.ENEMY_IDLE);

	        // Move to the next living enemy if any
	        turn++;
	        if (turn != 4) {
	            while (combat.getCombat_enemy(turn).getEnemyAction() == combat.ENEMY_DEAD && turn < 4) {
	                turn++;
	                if (turn == 4)
	                    break;
	            }
	        }

	        // Check for end of combat
	        if (turn == 4)
	            combat.setTurn(0);
	        else
	            combat.setTurn(turn);
	        // Reset variables for next turn
	        this.reset_variables(combat);
	    }
	}
}