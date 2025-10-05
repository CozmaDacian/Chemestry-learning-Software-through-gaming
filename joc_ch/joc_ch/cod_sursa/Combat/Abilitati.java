package Combat;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import main_game.Game;
import states.GameState;
import states.metode;
import utilz.CButton;


	

	public class Abilitati {
	    public CButton[] Abilitati = new CButton[11];
	    public BufferedImage[][] imagini = new BufferedImage[2][11];
	    private Combat combat;
	    public int ability_pressed;
	    private boolean inside_component;
	    private Rectangle Text_Abilitati = new Rectangle(0, Game.GAME_HEIGHT, 100, 100);
	    private String text[] = new String[5];

	    /**
	     * Constructor that initializes the Abilitati class.
	     * @param combat A reference to the combat system in the game.
	     */
	    Abilitati(Combat combat) {
	        this.combat = combat;
	        this.InitializeAttackButtons();
	        load_photos();
	        this.initialize_clicked();
	        set_enabled(false);
	    }

	    /**
	     * Sets the text display area for abilities.
	     * @param r The rectangle defining the area where text will be shown.
	     */
	    private void setText_Abilitati(Rectangle r) {
	        this.setText_Rectangle(r);
	    }

	    /**
	     * Executes logic based on the ability pressed.
	     * @param ability_pressed The index of the pressed ability.
	     * @param button The CButton representing the pressed ability.
	     */
	    private void based_on_ability_pressed(int ability_pressed, CButton button) {
	        String name = Abilitati[ability_pressed].getName();
	        int rstamina, st_atac;

	        switch (name) {
	            case "Grenada":
	                if (button.getCooldown() == 0) {
	                    rstamina = combat.getGame().getCaracter().getStatistici().getRemaining_Energy();
	                    // 3 is the index of the grenada atac
	                    st_atac = combat.getGame().getCaracter().getStatistici().GetAtacStamina(3);
	                    if (rstamina - st_atac > 0)
	                        combat.getGame().getCombat().atac = 3;
	                }
	                break;

	            case "Concentrat":
	                if (button.getCooldown() == 0) {
	                	// 2 is the index of the concentrat atac
	                    rstamina = combat.getGame().getCaracter().getStatistici().getRemaining_Energy();
	                    st_atac = combat.getGame().getCaracter().getStatistici().GetAtacStamina(2);
	                    if (rstamina - st_atac > 0)
	                        combat.getGame().getCombat().atac = 2;
	                }
	                break;

	            case "Simplu":
	                if (button.getCooldown() == 0) {
	                    rstamina = combat.getGame().getCaracter().getStatistici().getRemaining_Energy();
	                    st_atac = combat.getGame().getCaracter().getStatistici().GetAtacStamina(1);
	                    if (rstamina - st_atac > 0)
	                        combat.getGame().getCombat().atac = 1;
	                }
	                break;

	            case "Wait":
	                int turn = 1;
	                // Check what enemy is still alive
	                while (combat.getCombat_enemy(turn).getEnemyAction() == combat.ENEMY_DEAD) {
	                    turn++;
	                }
	                combat.getGame().getCombat().setTurn(turn);
	                combat.lupta.player_choosing = false;
	                Abilitati[ability_pressed].setClicked(false);
	                set_enabled(false);
	                break;

	            case "Recharge":
	                if (button.getCooldown() == 0) {
	                    button.setCooldown(1);
	                    combat.getGame().getIntrebari().reset_ozn_pozitions();
	                    Abilitati[ability_pressed].setClicked(false);
	                    this.set_enabled(false);
	                    Abilitati[ability_pressed].setEnabled(false);
	                    GameState.state = GameState.QUIZ;
	                    break;
	                }

	            case "Heal":
	                if (button.getCooldown() == 0) {
	                    int hp = utilz.Constante.Inventory_Constants.POTIUNE_HEAL;
	                    int number_of_potions = combat.getGame().getInventar().getFrecventeItems()[hp];
	                    int current_health = combat.getGame().getCaracter().getStatistici().getRemaining_health();
	                    int total_health = combat.getGame().getCaracter().getStatistici().getTotal_health();

	                    int health_restore = combat.getGame().getCaracter().getStatistici().getRestore_health();
	                    if (number_of_potions != 0) {
	                        if (current_health + health_restore > total_health) {
	                            combat.getGame().getCaracter().getStatistici().setRemaining_health(total_health);
	                        } else {
	                            combat.getGame().getCaracter().getStatistici().setRemaining_health(health_restore + current_health);
	                        }
	                        combat.getGame().getInventar().setFrecventa_item(hp, number_of_potions - 1);
	                        button.setCooldown(1);
	                    }
	                }
	                break;

	            case "Energie":
	                if (button.getCooldown() == 0) {
	                    Game game = combat.getGame();
	                    int current_energy = game.getCaracter().getStatistici().getRemaining_Energy();
	                    int total_energy = game.getCaracter().getStatistici().getTotal_Stamina();
	                    int energy_restore = game.getCaracter().getStatistici().getRestore_energy();

	                    int enr = utilz.Constante.Inventory_Constants.POTIUNE_ENERGIE;
	                    int number_of_potions = combat.getGame().getInventar().getFrecventeItems()[enr];
	                    if (number_of_potions != 0) {
	                        if (current_energy + energy_restore > total_energy) {
	                            game.getCaracter().getStatistici().setRemaining_Energy(total_energy);
	                        } else {
	                            game.getCaracter().getStatistici().setRemaining_Energy(current_energy + energy_restore);
	                        }
	                        combat.getGame().getInventar().setFrecventa_item(enr, number_of_potions - 1);
	                        button.setCooldown(1);
	                    }
	                }
	        }
	    }

	    /**
	     * Enables or disables the ability buttons.
	     * @param enabled Boolean flag to enable or disable buttons.
	     */
	    public void set_enabled(boolean enabled) {
	        for (int i = 1; i <= 10; i++) {
	            String name = Abilitati[i].getName();
	            if (!name.equals("nu")) {
	                Abilitati[i].setEnabled(enabled);
	                Abilitati[i].setClicked(false);
	            }
	        }
	    }

	    /**
	     * Loads the button images from files.
	     */
	    private void load_photos() {
	        String file1 = utilz.Constante.path_name;
	        String file = utilz.Constante.path_name;
	        file1 = file1 + "Butoane/nclick/";
	        file = file + "Butoane/click/";
	        Abilitati[0] = new CButton();

	        for (int i = 1; i <= 10; i++) {
	            BufferedImage img1 = combat.getGame().loadImageFromFile(file + i + ".png");
	            BufferedImage img = combat.getGame().loadImageFromFile(file1 + i + ".png");
	            imagini[0][i] = img;
	            imagini[1][i] = img1;

	            Abilitati[i].graphics = img1;
	            Abilitati[i].graphics1 = img;

	            combat.getGame().getGamePanel().add(Abilitati[i]);
	            Abilitati[i].setClicked(false);
	        }
	    }

	    /**
	     * Initializes the attack buttons by setting up their names and default text.
	     */
	    private void InitializeAttackButtons() {
	        for (int i = 0; i <= 4; i++) {
	            this.text[i] = new String();
	        }
	        String name[] = new String[11];

	        name[1] = "Grenada";
	        name[2] = "Concentrat";
	        name[3] = "Simplu";
	        name[4] = "nu";
	        name[5] = "nu";
	        name[6] = "Wait";
	        name[7] = "Recharge";
	        name[8] = "nu";
	        name[9] = "Heal";
	        name[10] = "Energie";
	    
		
		int widthButton=90;
		int heightButton=90;
		int x=288;
		int y=combat.getGame().GAME_HEIGHT-90;
		
		for(int i=1;i<=10;i++) {
			Abilitati[i]=new CButton();
			Abilitati[i].setName(name[i]);
			if(name[i]=="nu")
				Abilitati[i].setEnabled(false);
			Abilitati[i].setBounds(x+i*(widthButton+1), y, widthButton, heightButton);
		    
			Abilitati[i].setVisible(true);
			
			
			
		}
		
		
		
		
	}
	

	private int GetAbility(String value) {
		
		switch(value) {
			
		case "nu":
			return 0;
		case "Grenada":
			 return 1;
		case "Concentrat":
			return 2;
		case "Simplu":
			return 3;
		case "Wait":
			return 6;
		case "Recharge":
			return 7;
		case "Heal":
			 return 9;
		case "Energie":
			return 10;
		}
		return 0;
		
	}
	
	private void setAbility_pressed(int ability_pressed) {
		Abilitati[this.ability_pressed].setClicked(false);
		if(Abilitati[ability_pressed].getClicked()==false)
		this.ability_pressed=ability_pressed;
		else
		this.ability_pressed=0;
	}
	
	private boolean check_if_possible(int ability_pressed) {
	
		 if(Abilitati[ability_pressed].getCooldown()>0)
			 return false;
		 
		 int stamina=combat.getGame().getCaracter().getStatistici().getRemaining_Energy();
		 return true;
	}
	
	private int getAbility_pressed() {
		return this.ability_pressed;
	}
	
	private void setButtonText(String value) {
		switch(value) {
		
		case "nu":
			
		case "Grenada":
			  getText()[0]="Grenada";
			  getText()[1]="Un atac care loveste toti adversarii";
			  getText()[2]="Cost: 20 energie";
			  getText()[3]="Cooldown: 3 ture";
		break;
		case "Concentrat":
			getText()[0]="Atac Concentrat";
			getText()[1]="Un atac care patrunde prin armura inamicilor";
			getText()[2]="Cost: 18 energie";
			getText()[3]="Cooldown: 2 ture";
		break;
		case "Simplu":
			getText()[0]="Atac Simplu";
			getText()[1]="Un atac care provoaca daune minore";
			getText()[2]="Cost: 7 energie";
			getText()[3]="Cooldown: 0 ture";
		break;
		case "Wait":
			getText()[0]="Asteptare";
			getText()[1]="Asteapta o tura pentru a iti reface energia";
			getText()[2]="Cost: 0 energie";
			getText()[3]="Cooldown: 0 ture";
		break;
		case "Recharge":
			  getText()[0]="Recharge";
			  getText()[1]="Raspunde la o intrebare si refa-ti energia";
			  getText()[2]="Cost: 0 energie";
			  getText()[3]="Cooldown: 1 tura";
		break;
		case "Heal":
			 getText()[0]="Heal";
			 getText()[1]="Potiune care iti vindeca hp cu 30 de puncte";
			 getText()[2]="Cost: 0 energie";
			 getText()[3]="Cooldown 1 tura";
			 break;
		case "Energie":
			getText()[0]="Energie";
			 getText()[1]="Potiune care iti reface energia cu 10 puncte";
			 getText()[2]="Cost: 0 energie";
			 getText()[3]="Cooldown 1 tura";
			 break;
		}
	}
	
	public void set_visible() {
		
		for(int i=1;i<=10;i++)
	    Abilitati[i].setVisible(true);
		
	}

	private void initialize_clicked() {
		
		for(int i=1;i<=10;i++) {
			Abilitati[i].setFocusable(true);
			Abilitati[i].requestFocus();
			Abilitati[i].addMouseListener(new MouseAdapter() {
			    public void mouseClicked(MouseEvent e) {
			        new Thread(() -> {
			            CButton button = (CButton) e.getSource();
			           
			            String abilityName = button.getName();
			            int ability_pressed = GetAbility(abilityName);
			            boolean clicked = Abilitati[ability_pressed].getClicked();
			             
			             if(button.isEnabled() && button.getCooldown()==0) { 
			             setAbility_pressed(ability_pressed);
			             based_on_ability_pressed(ability_pressed,button);
			             combat.getGame().getGamePanel().requestFocus();
			            
			            Abilitati[ability_pressed].setClicked(!clicked);
			             }
			        }).start();
			    }
			    @Override
			    public void mouseExited(MouseEvent e) {
			    	CButton button = (CButton) e.getSource();
			       
		            String abilityName = button.getName();
		            if(button.isEnabled()) {
		            	setText_Abilitati(new Rectangle(-200,-200,1,1));
		            }
			    }
			    @Override
			    public void mouseEntered(MouseEvent e) {
				   
				    	CButton button = (CButton) e.getSource();
				           
			            String abilityName = button.getName();
			            if(button.isEnabled()) {
			            	setButtonText(abilityName);
			            	setText_Abilitati(new Rectangle(button.getX()+10,button.getY()-100,315,100));
			            }
				    }
			});
			
	}
	}
		
			 
		    
		

	            
	
	
		

	public String[] getText() {
		return text;
	}
	public void setText(String text[]) {
		this.text = text;
	}
	public Rectangle getText_Rectangle() {
		return Text_Abilitati;
	}
	public void setText_Rectangle(Rectangle text_Abilitati) {
		Text_Abilitati = text_Abilitati;
	}
	public boolean isInside_component() {
		return inside_component;
	}
	public void setInside_component(boolean inside_component) {
		this.inside_component = inside_component;
	}

	
}
