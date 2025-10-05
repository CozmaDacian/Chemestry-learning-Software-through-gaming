package Combat;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main_game.Bars;
import main_game.Caracter;
import main_game.Enemies;
import main_game.Game;

import states.GameState;
import states.metode;
import utilz.Rectangle_Hitbox;
import utilz.CButton;
import utilz.Constante;
import utilz.Pair;

public class Combat  extends Objects_Combat implements metode{
	public int tick=0;
    private Enemies enemy_phase1;
    public Abilitati abilitati;
	private Phase_1 phase1; 
	protected int turn=0;
	public int player_combat_turns=1;
	public Phase_Combat lupta;
	private boolean phase_1=false,phase_2,phase_3;
	
	
	private Enemies[] combat_enemy=new Enemies[4];
	public int atac=0;
	public int atacat=1;
	
	
   
	
	
	
	
	
	public Combat(Game game) {
		
		
		super(game);
		phase1=new Phase_1(this);
		lupta=new Phase_Combat(this);
		abilitati=new Abilitati(this);
		
	}
	
	
	
	
		
	
	
	
	
	
	@Override
	public void draw(Graphics2D g) {
		  
				
		    
			
		if(isPhase_1()==true) {
			getPhase1().draw(g);
		}
		else
		{
			
			
			lupta.draw_in_combat(g);
			drawButtons(g);	
		
		}
		
	}
	public void drawButtons(Graphics2D g) {
		BufferedImage image;
		for(int i=1;i<=10;i++) {
			CButton button=this.abilitati.Abilitati[i];
			int x=button.getX();
			int y=button.getY();
		
			if(button.getClicked()==true) {
		     image=button.graphics;
				
			}
			else
		    image=button.graphics1;
		
			g.drawImage(image, x, y, null);
			if(button.getCooldown()>0) {
				 
						
					 g.setFont(new Font("Arial",Font.PLAIN,18));
					 g.setColor(Color.red);
					 g.drawString( String.valueOf(button.getCooldown()), x+80, y+80);
				  }
			
		if(i==10) {
			int enr=utilz.Constante.Inventory_Constants.POTIUNE_ENERGIE;
            int number_of_potions=this.getGame().getInventar().getFrecventeItems()[enr];
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,14));
		    g.drawString(String.valueOf(number_of_potions), x+10, y+80);
		}
		if(i==9) {
			int hp=utilz.Constante.Inventory_Constants.POTIUNE_HEAL;
            int number_of_potions=this.getGame().getInventar().getFrecventeItems()[hp];
            g.setColor(Color.white);
            g.setFont(new Font("Arial",Font.PLAIN,14));
            g.drawString(String.valueOf(number_of_potions), x+10, y+80);
		}
		}
		}
	
	@Override
	public void render() {
	
		if(isPhase_1()==true) {
			getPhase1().render();
		}
		else
		{
			
			lupta.update_atac(turn);
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.print(atac);
		int x=e.getX();
		int y=e.getY();
		if(!isPhase_1()) {
			
				if(atac!=0) {
					
					for(int i=1;i<=3;i++) {
						Rectangle inamic=new Rectangle(this.combat_enemy[i].getEnemy_hitbox_combat());
						inamic.width=300;
						
						inamic.height=120;
					    if(inamic.contains(x, y) && combat_enemy[i].getEnemyAction()!=this.ENEMY_DEAD) {
					    	atacat=i;
					    	abilitati.set_enabled(false);
					    	
					    	lupta.player_choosing=false;
					    
					}
					
				}
			}
		
		
	}
   }

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}









    
	public void setTick(int tick) {
		this.tick=tick;
	}
	public int getTick() {
		// TODO Auto-generated method stub
		return tick;
		
	}










	public Enemies getEnemy_phase1() {
		return enemy_phase1;
	}










	public void setEnemy_phase1(Enemies enemy_phase1) {
		this.enemy_phase1 = enemy_phase1;
	}










	public boolean isPhase_1() {
		return phase_1;
	}










	public void setPhase_1(boolean phase_1) {
		this.phase_1 = phase_1;
	}










	public Phase_1 getPhase1() {
		return phase1;
	}
	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}









	public void setPhase1(Phase_1 phase1) {
		this.phase1 = phase1;
	}










	public Enemies getCombat_enemy(int indice) {
		return combat_enemy[indice];
	}










	public void setCombat_enemy(Enemies combat_enemy,int indice) {
		this.combat_enemy[indice]=combat_enemy;
	}
}
	