package Combat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main_game.Caracter;
import main_game.Enemies;
import states.GameState;

public class Phase_Combat {
    public boolean player_choosing=true;
	public boolean start_animatie=false;
	int atac;
	Fight_Enemy inamic_atac;
	Fight_Player player;
	int number_of_enemies;
	boolean all_dead=false;
	Combat combat;
	boolean player_dead=false;
	Phase_Combat(Combat combat){
		this.combat=combat;
		inamic_atac=new Fight_Enemy();
	    player=new Fight_Player();
	  
	}
	
	public void update_atac(int turn) {
	    // Get necessary combat, character, and player information
	    Combat combat = this.combat;
	    Caracter caracter = combat.getGame().getCaracter();
	    boolean player_choosing = this.player_choosing;
	    
	   
	    if (!player_choosing) {
	        // Update actions for enemies
	        if (turn != 0) {
	            Enemies enemy = combat.getCombat_enemy(turn);
	            if (!start_animatie) {
	                atac = enemy.get_Atac();
	                start_animatie = true;
	                
	                // Determine enemy action and update
	                if (atac == 1) {
	                    enemy.setEnemyAction(combat.ENEMY_RUN);
	                } else if (atac == 2) {
	                    enemy.setEnemyAction(combat.ENEMY_SHOOT);
	                }
	            
	            }
	            // Update enemy attack position
	            inamic_atac.update_poz_atac_enemy_robot(combat, enemy, atac, caracter);
	        }
	        // Player turn
	        if (turn == 0) {
	            int atacat = combat.atacat;
	            int atac = combat.atac;
	            Enemies enemy = combat.getCombat_enemy(atacat);
	            if (!start_animatie) {
	                // Determine player action and update
	            	
	                if (atac == 1 || atac == 2) {
	                    caracter.setPlayerAction(combat.SHOOT);
	                } else if (atac == 3) {
	                    caracter.setPlayerAction(combat.GRENADA);
	                }
	                int stamina=caracter.getStatistici().getRemaining_Energy();
	                int decrease_stamina=caracter.getStatistici().GetAtacStamina(atac);
	                caracter.getStatistici().setRemaining_Energy(stamina-decrease_stamina);
	                start_animatie = true;
	                int cooldown=caracter.getStatistici().GetCooldownAbility(atac);
	                int ability_pressed=combat.abilitati.ability_pressed;
	                combat.abilitati.Abilitati[ability_pressed].setCooldown(cooldown+1);
	            }
	            // Update player attack
	            player.update_atac(combat, caracter, enemy);
	            
	            // Check for remaining living enemies
	            if (combat.turn == 1) {
	                // Move to the next living enemy
	                if (combat.turn != 4) {
	                    while (combat.getCombat_enemy(combat.turn).getEnemyAction() == combat.ENEMY_DEAD && combat.turn < 4) {
	                        combat.turn++;
	                        if (combat.turn == 4) {
	                            break;
	                        }
	                    }
	                    // All enemies are dead, end game
	                    if (combat.turn == 4) {
	                        all_dead = true;
	                        caracter.setX(combat.getEnemy_phase1().xcaracter);
	                        caracter.setY(combat.getEnemy_phase1().ycaracter);
	                        Enemies enemy1=combat.getEnemy_phase1();
	                        combat.getGame().getPlay().enemies.remove(enemy1);
	                        this.reset_animations();
	                        GameState.setCurrentOption(GameState.PLAYING);
	                       return;
	                    } else {
	                        start_animatie = false;
	                    }
	                }
	            }
	        }
	    }
	    // Reset for next turn
	    if (combat.getTurn() != turn && combat.getTurn() == 0) {
	        combat.atac = 0;
	        combat.atacat = 1;
	        combat.abilitati.set_enabled(true);
	        
	        for(int i=1;i<=10;i++) {
	        	int cooldown=combat.abilitati.Abilitati[i].getCooldown();
	        	if(cooldown>0)
	        		combat.abilitati.Abilitati[i].setCooldown(cooldown-1);
	        }
	        int stamina=caracter.getStatistici().getRemaining_Energy();
	        int stamina_regen=caracter.getStatistici().getStamina_regen();
	        if(stamina+stamina_regen<caracter.getStatistici().getTotal_Stamina())
	        caracter.getStatistici().setRemaining_Energy(stamina_regen+stamina);
	        else
	        caracter.getStatistici().setRemaining_Energy(caracter.getStatistici().getTotal_Stamina());;
	        this.player_choosing = true;
	    }
	}
	
	public void reset_animations() {
		 combat.atac = 0;
	        combat.atacat = 1;
	        combat.turn=0;
	        for(int i=1;i<=10;i++) {
	        combat.abilitati.Abilitati[i].setCooldown(0);
	        }
	        combat.abilitati.set_enabled(false);
        
        
         this.player_choosing=true;
         start_animatie=false;
	}
	
	
	public void draw_text(Graphics2D g,String text[],Rectangle rect) {
		
			
			if(!player_choosing)
              return;			
			
		
			int x1=rect.x;
			int y1=rect.y;
	        int width=rect.width;
	        int height=rect.height;
	        
	        g.setColor(new Color(14,14,14,255));
	        g.fillRect(x1, y1, width, height);
	         x1=rect.x+5;
			 y1=rect.y+8;
			
			g.setFont(new Font("Arial",Font.PLAIN,18));
			g.setColor(new Color(224,2,226,255));
			
			g.drawString(text[0], x1, y1+10);
			g.setColor(Color.white);
			g.setFont(new Font("Arial",Font.PLAIN,16));
			g.drawString(text[1],x1,y1+30);
			g.setColor(new Color(222,222,3,255));
			g.drawString(text[2], x1, y1+70);
			g.setColor(new Color(194,194,194,255));
			g.drawString(text[3], x1, y1+90);
			
			
		}
	
	public void draw_in_combat(Graphics2D g) {
	    // Get necessary combat and character information
	    Combat combat = this.combat;
	    Caracter caracter = combat.getGame().getCaracter();
	    BufferedImage img_caracter = caracter.get_current_caracter_sprite_in_combat();
	    BufferedImage back = combat.background;
	    int turn = combat.turn;
	    int atac = combat.atac;
	    int atacat = combat.atacat;
	    boolean player_choosing = this.player_choosing;

	    // Draw background
	    g.drawImage(back, 0, 0, null);

	    
	    int x_caracter = caracter.getX();
	    int y_caracter = caracter.getY();
	    
	    // Draw circle for player
	    g.drawImage(combat.cerc_player, x_caracter - 50, y_caracter + caracter.getHitBoxCombat().height - 60, null);
	   
	   // Draw player character
	    g.drawImage(img_caracter, x_caracter, y_caracter, null);

	    // Draw circles for enemies  based on offsets
	   
	    if(number_of_enemies==1) {
	    for (int i = 2; i <= 2; i++) {
	        Enemies enemy = combat.getCombat_enemy(i);
	        int x_enemy = (int) enemy.getX() - 60;
	        int y_enemy = (int) enemy.getY() + 110;
	        g.drawImage(combat.cerc_inamic, x_enemy, y_enemy, null);
	        // Draw health bars for living enemies
	        if (enemy.getEnemyAction() != combat.ENEMY_DEAD) {
	            enemy.getBars().draw_health_bar(g);
	        } else if (enemy.animatie < 5) {
	            // Draw health bars for dead enemies for a limited time
	            enemy.getBars().draw_health_bar(g);
	        }
	    }
	    
	    }
	    else
	    {
	    	for (int i = 1; i <= number_of_enemies; i++) {
		        Enemies enemy = combat.getCombat_enemy(i);
		        int x_enemy = (int) enemy.getX() - 60;
		        int y_enemy = (int) enemy.getY() + 110;
		        g.drawImage(combat.cerc_inamic, x_enemy, y_enemy, null);
		        // Draw health bars for living enemies
		        if (enemy.getEnemyAction() != combat.ENEMY_DEAD) {
		            enemy.getBars().draw_health_bar(g);
		        } else if (enemy.animatie < 5) {
		            // Draw health bars for dead enemies for a limited time
		            enemy.getBars().draw_health_bar(g);
		        
		        }
		        
		        
		    }
	    }
	    // Draw health and energy bars for player character
	    caracter.getBars().draw_health_bar(g);
	    caracter.getBars().draw_energy_percentage(g);

	    // Determine whose turn it is and draw corresponding elements
	    if (!player_choosing) {
	        if (turn == 0) {
	            player.draw_combat_elements(g, atac, atacat, combat, combat.getCombat_enemy(combat.atacat));
	        } else {
	            inamic_atac.draw_atac2(g, combat);
	        }
	    }

	    // Draw enemies
	    if(number_of_enemies==1) {
	    	
	    
	    for (int i = 2; i <= 2; i++) {
	        Enemies enemy = combat.getCombat_enemy(i);
	        int x_enemy = (int) enemy.getX();
	        int y_enemy = (int) enemy.getY();
	        BufferedImage en = enemy.get_current_enemy_sprite();
	        g.drawImage(en, x_enemy, y_enemy, null);
	    }
	    }
	    else
	    {
	    	  for (int i = 1; i <= number_of_enemies; i++) {
	  	        Enemies enemy = combat.getCombat_enemy(i);
	  	        int x_enemy = (int) enemy.getX();
	  	        int y_enemy = (int) enemy.getY();
	  	        BufferedImage en = enemy.get_current_enemy_sprite();
	  	        g.drawImage(en, x_enemy, y_enemy, null);
	    	
	    }
	    }
	    this.draw_text(g, combat.abilitati.getText(),combat.abilitati.getText_Rectangle());
	    
	  
	}
}

	
	
	

