package Stats;

import java.io.Serializable;

import states.GameState;

public class Player_stats implements Serializable {
    
	 private int player_level=1;
    private int damage_all=18;
	private int damage_de_baza=0;
	 private int upgrade_damage_grenada=2;
	 private int upgrade_damage_concentrat=3;
	 
	 private int cooldown_damage_all=4;
	 private int upgrade_damage_simplu=3,upgrade_health=15,upgrade_stamina=7;
	 private int restore_energy=15, restore_health=30;
	private int damage_atac_simplu=8,damage_atac_Concentrat=16,damage_Grenada=12;
	private int player_defense=0;
	private int upgrade_defense=1;
	private  int current_defense=1;
	 private int Total_health=90;
	 private int Total_Stamina=30;
	 private int Remaining_Energy=30;
	 private int Remaining_health=Total_health;
	 private int stamina_regen=7;
	 private int learning_points=0;
	 private int cooldown_grenada=2, cooldown_atac_concentrat=1, 
			     cooldown_atac_simplu=0, 
			     cooldown_wait=0, cooldown_quiz=1;
	 private int cooldown_heal=1,cooldown_energie=1;
	 private int stamina_grenada=20, stamina_atac_concentrat=18, stamina_atac_simplu=7;

	 
	 
	 
	 
     public int GetCooldownAbility(int atac) {
    	
    			
    			
    			
    			switch(atac) {
    			
    			case 3:
    		    return this.cooldown_grenada;
    			
    			case 2:
    			return this.cooldown_atac_concentrat;
    				
    			case 1:
    				return this.cooldown_atac_simplu;
    		
    			}
    			return 0;
     }
	 public int GetAtacStamina(int atac) {
		 
		 switch(atac) {
		 case 3:
			 return stamina_grenada;
		 case 2:
			 return stamina_atac_concentrat;
		 case 1:
			 return stamina_atac_simplu;
		 
		 }
		 
		 return 0;
	 }
	 public int GetAtacDamage(int atac) {
		 switch(atac) {
		 case 3:
			 return this.damage_Grenada;
		 case 2:
			 return this.damage_atac_Concentrat;
		 case 1:
			 return this.damage_atac_simplu;
		 }
		 return 0;
	 }
	

	 public void update_damages(int atac) {
		 
		 switch(atac) {
		 case 1:
			 this.damage_Grenada+=this.upgrade_damage_grenada;
			  break;
		 case 2:
			 this.damage_atac_Concentrat+=this.upgrade_damage_concentrat;
		 case 3:
			 this.damage_atac_simplu+=this.upgrade_damage_simplu;
		 }
		 
	 }
	 public void update_all_damages() {
		 
		 for(int i=1;i<=3;i++)
			update_damages(i);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 public int getCooldown_energie() {
		return cooldown_energie;
	}
	public void setCooldown_energie(int cooldown_energie) {
		this.cooldown_energie = cooldown_energie;
	}
	public int getStamina_regen() {
		return stamina_regen;
	}
	public void setStamina_regen(int stamina_regen) {
		this.stamina_regen = stamina_regen;
	}
	public int getRemaining_health() {
		return Remaining_health;
	}
	public void setRemaining_health(int remaining_health) {
		Remaining_health = remaining_health;
	}
	public int getTotal_Stamina() {
		return Total_Stamina;
	}
	public void setTotal_Stamina(int total_Stamina) {
		Total_Stamina = total_Stamina;
	}
	public int getRemaining_Energy() {
		return Remaining_Energy;
	}
	public void setRemaining_Energy(int remaining_Energy) {
		Remaining_Energy = remaining_Energy;
	}
	public int getTotal_health() {
		return Total_health;
	}
	public void setTotal_health(int total_health) {
		Total_health = total_health;
	}
	public int getCurrent_defense() {
		return current_defense;
	}
	public void setCurrent_defense(int current_defense) {
		this.current_defense = current_defense;
	}
	public int getPlayer_defense() {
		return player_defense;
	}
	public void setPlayer_defense(int player_defense) {
		this.player_defense = player_defense;
	}
	public int getPlayer_level() {
		return player_level;
	}
	public void setPlayer_level(int player_level) {
		this.player_level = player_level;
	}
	
	public int getUpgrade_health() {
		return upgrade_health;
	}
	public void setUpgrade_health(int upgrade_health) {
		this.upgrade_health = upgrade_health;
	}
	public int getUpgrade_stamina() {
		return upgrade_stamina;
	}
	public void setUpgrade_stamina(int upgrade_stamina) {
		this.upgrade_stamina = upgrade_stamina;
	}
	public int getUpgrade_damage_simplu() {
		return upgrade_damage_simplu;
	}
	public void setUpgrade_damage_simplu(int upgrade_damage_simplu) {
		this.upgrade_damage_simplu = upgrade_damage_simplu;
	}
	public int getRestore_energy() {
		return restore_energy;
	}
	public void setRestore_energy(int restore_energy) {
		this.restore_energy = restore_energy;
	}
	public int getRestore_health() {
		return restore_health;
	}
	public void setRestore_health(int restore_health) {
		this.restore_health = restore_health;
	}
	public int getUpgrade_defense() {
		return upgrade_defense;
	}
	public void setUpgrade_defense(int upgrade_defense) {
		this.upgrade_defense = upgrade_defense;
	}
	public int getDamage_de_baza() {
		return damage_de_baza;
	}
	public void setDamage_de_baza(int damage_de_baza) {
		this.damage_de_baza = damage_de_baza;
	}
	public int getLearning_points() {
		return learning_points;
	}
	public void setLearning_points(int learning_points) {
		this.learning_points = learning_points;
	}
	 


}
