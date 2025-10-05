package utilz;

import java.util.HashMap;
import java.util.Map;

import actiune_joc.Skill_tree;
import main_game.Game;
import states.GameState;

public class Constante {
    //    120
	//  90
	public static String path_name="resources/";
	public static double fr=1;
    public static int nr_nivele = 2;
	public static class SkillTreeConstant{
		
		public static final int BOOST_MIC_VIATA=1;
		public static final int BOOST_ENERGIE=2;
		public static final int BOOST_DAUNE_ARME_MIC=3;
		public static final int BOOST_MIC_ARMURA=4;
		public static final int BOOST_ENERGIE_RECHARGE=5;
		public static final int BOOST_NOROC_INTREBARE=6;
		public static final int BOOST_ATAC_SIMPLU=7;
		public static final int BOOST_POTIUNE_HEAL=8;
		public static final int BOOST_POTIUNE_ENERGIE=9;
		public static final int BOOST_DAMAGE_ARME=10;
		public static final int BOOST_DAMAGE_GRENADA=11;
		public static final int BOOST_MEDIU_VIATA=12;
		public static final int BOOST_MEDIU_ARMURA=13;
		public static final int BOOST_MEDIU_ENERGIE=14;
		public static final int BOOST_NOROC_INTREBARE_II=15;
		public static final int BOOST_DAMAGE_ATAC_ALL=16;
		public static final int BOOST_TWO_TURNS=17;
		public static final int GREAT_RESTORE=18;
		public static final int A_DOUA_SANSA=19;
		public static final int GENIUL_IN_BATALIE=20;
		public static final int RECHARGE_FULLY=21;
		public static final int REROLL=22;
		public static final int BOOST_ENERGIE_WAIT=23;
		public static final int BOOST_DAMAGE_RASPUNS_CORECT=24;
		
		
		
		public static void GetAbilitate(int abilitate,Game game) {
			int total_health=game.getCaracter().getStatistici().getTotal_health();
			int remaining_health=game.getCaracter().getStatistici().getRemaining_health();
            int health_upgrade,energy_upgrade;
            int total_energy=game.getCaracter().getStatistici().getTotal_Stamina();
			int remaining_energy=game.getCaracter().getStatistici().getRemaining_Energy();
			
			if(game.getTree().Freq[abilitate]==1)
                     return ;
			switch(abilitate) {
			case BOOST_MIC_VIATA:
			
				 health_upgrade=25;
				game.getCaracter().getStatistici().setRemaining_health(remaining_health+health_upgrade);
				game.getCaracter().getStatistici().setTotal_health(total_health+health_upgrade);
				
				break;
			case BOOST_ENERGIE:
				 energy_upgrade=8;
				game.getCaracter().getStatistici().setRemaining_Energy(remaining_energy+energy_upgrade);
				game.getCaracter().getStatistici().setTotal_Stamina(energy_upgrade+total_energy);
			break;
			
			case BOOST_DAUNE_ARME_MIC:
				int damage_de_baza=game.getCaracter().getStatistici().getDamage_de_baza();
				game.getCaracter().getStatistici().setDamage_de_baza(damage_de_baza+2);
			    break;
			
			case BOOST_MIC_ARMURA:
				int defense=game.getCaracter().getStatistici().getPlayer_defense();
				game.getCaracter().getStatistici().setCurrent_defense(defense+1);
			break;
				
			case BOOST_ENERGIE_RECHARGE:
				 int stamina_regen=game.getCaracter().getStatistici().getStamina_regen();
				 int boost_stamina=2;
				 game.getCaracter().getStatistici().setStamina_regen(stamina_regen+boost_stamina);
			break;
			case BOOST_NOROC_INTREBARE:
				game.getIntrebari().setNoroc(1);
				break;
			case BOOST_ATAC_SIMPLU:
				game.getCaracter().getStatistici().update_damages(3);
			break;
			case BOOST_POTIUNE_HEAL:
				int heal=game.getCaracter().getStatistici().getRestore_health();
			    int upgrade_heal=10;
			    game.getCaracter().getStatistici().setRestore_health(heal+upgrade_heal);
			break;
			case BOOST_POTIUNE_ENERGIE:
				int energie=game.getCaracter().getStatistici().getRestore_energy();
				int upgrade_energie=3;
				game.getCaracter().getStatistici().setRestore_energy(energie+upgrade_energie);
			break;
			case BOOST_DAMAGE_ARME:
				int damageBaza=game.getCaracter().getStatistici().getDamage_de_baza();
				int damage_upgrade=1;
				game.getCaracter().getStatistici().setDamage_de_baza(damageBaza+damage_upgrade);
		   break;
			case BOOST_DAMAGE_GRENADA:
				game.getCaracter().getStatistici().update_damages(1);
			break;
			case BOOST_MEDIU_VIATA:
				health_upgrade=50;
				game.getCaracter().getStatistici().setRemaining_health(remaining_health+health_upgrade);
				game.getCaracter().getStatistici().setTotal_health(total_health+remaining_health);
				break;
			case BOOST_MEDIU_ARMURA:
				int cdefense=game.getCaracter().getStatistici().getPlayer_defense();
				game.getCaracter().getStatistici().setPlayer_defense(cdefense+2);
				break;
			case BOOST_MEDIU_ENERGIE:
				energy_upgrade=15;
				game.getCaracter().getStatistici().setRemaining_Energy(remaining_energy+energy_upgrade);
				game.getCaracter().getStatistici().setTotal_Stamina(total_energy+energy_upgrade);
			   break;
			case BOOST_NOROC_INTREBARE_II:
				game.getIntrebari().setNoroc(2);
				break;
			case BOOST_DAMAGE_ATAC_ALL:
				
				break;
			case BOOST_TWO_TURNS:
				game.getCombat().player_combat_turns=2;
				break;
			case GREAT_RESTORE:
				game.getInventar().setGreat_restore(true);
			break;
			case A_DOUA_SANSA:
				game.getIntrebari().second_chance=true;
			break;
			case GENIUL_IN_BATALIE:
				game.getIntrebari().damage_inamic_raspuns_corect=20;
			break;
			case RECHARGE_FULLY:
				game.getIntrebari().full_recharge_energy=true;
			break;
			case REROLL:
				game.getIntrebari().reroll=true;
			break;
			}
	    game.getTree().Freq[abilitate]=1;
		return ;
	}
	
	}
 	public static class EnemyConstants{
		
		public static class Robot{
			public static final int ATTACK=0;
			public static final int DEAD=1;
			public static final int IDLE=2;
			public static final int RUN=3;
			public static final int SHOOT=4;
			public static final int ATTACK_MIC=5;
			public static final int RUN2=6;
			public static final int IDLE_PLAY=7;
			public static int GetEnemy(int enemy_action) {
				
				
				switch(enemy_action) {
				case ATTACK:
					return 4;
					
				case DEAD:
					return 5;
					
				case IDLE:
					return 2;
				
				case RUN2:
					return 7;
				case RUN:
					return 7;
					
				case SHOOT:
					return 4;
				case ATTACK_MIC:
					return 4;
				case IDLE_PLAY:
					return 1;
					
				}
					return 0;
				}
				
				
				public static int GetSpeed(int enemy_action) {
					
					
					switch(enemy_action) {
					case ATTACK:
						return (int) (30/fr);
						
					case DEAD:
						return (int)(15/fr);
						
					case IDLE:
						return (int) (100/fr);
				   
					case RUN2:
						return (int) (15/fr);
					case RUN:
						return (int) (25/fr);
						
					case SHOOT:
						return (int) (50/fr);
					case ATTACK_MIC:
						return (int) (30/fr);
					case IDLE_PLAY:
						return (int)(100/fr);
					}
					return 0;
		 			}
				
				
	 			}
			public static class SCIENTIST{
				public static final int ATTACK=0;
				public static final int DEAD=1;
				public static final int IDLE=2;
				public static final int RUN=3;
				public static final int SHOOT=4;
				public static final int ATTACK_MIC=5;
				public static final int RUN2=6;
				public static final int IDLE_PLAY=7;
				public static int GetEnemy(int enemy_action) {
					
					
					switch(enemy_action) {
					case ATTACK:
						return 9;
						
					case DEAD:
						return 5;
						
					case IDLE:
						return 2;
					
					case RUN2:
						return 11;
					case RUN:
						return 11;
						
					case SHOOT:
						return 4;
					case ATTACK_MIC:
						return 9;
					case IDLE_PLAY:
						return 1;
					}
					return 0;
		 			}
			
			
			
public static int GetSpeed(int enemy_action) {
				
				
				switch(enemy_action) {
				case ATTACK:
					return (int) (10/fr);
					
				case DEAD:
					return (int)(25/fr);
					
				case IDLE:
					return (int) (100/fr);
			   
				case RUN2:
					return (int) (8/fr);
				case RUN:
					return (int) (10/fr);
					
				case SHOOT:
					return (int) (30/fr);
				case ATTACK_MIC:
					return (int) (10/fr);
				case IDLE_PLAY:
					return (int)(100/fr);
				}
				return 0;
	 			}
			
		}
	}
	
	
	
	public static class PlayerConstansts{
		
		public static final int SHOOT=1;
	public static final int DEAD=0;
	
	public static final int IDLE_GUN=3;
	
	public static final int RUN_GUN=2;
	public static final int IDLE=6;
	public static final int JUMP=4;
	public static final int RUN=5;
	public static final int EXPLOSION=7;
    public static final int FALL=8;
	
	public static int GetSprite(int player_action) {
		
		
		switch(player_action) {
		
		
		case SHOOT:
			return 8;
			
		case DEAD:
			return 8+1;
	         
		   
		case IDLE_GUN:
			return 3;
		
			
		case RUN_GUN:
			return 6;
		case IDLE:
			return 2;
		case JUMP:
			return 2;
		case FALL:
			return 1;
		case RUN:
			return 6;
		case EXPLOSION:
			return 6;
		
		
		}
		return 0;
		
		
		
		}
	public static int GetSpeed(int player_action) {
		switch(player_action) {
		case SHOOT:
		
		return (int) (15/fr);

	case DEAD:
		return (int) (25/fr);
         
	   
	case IDLE_GUN:
		return 50;
	
		
	case RUN_GUN:
		return (int) (18/fr);
	case IDLE:
		return (int) (35/fr);
	case JUMP:
		return (int) (25/fr);
	case FALL:
		return (int) (25/fr);
	case RUN:
		return (int) (15/fr);
	  
	case EXPLOSION:
		return (int)(20/fr);
	
	

	
		
		
	}
		return 0;
	}
	}
	
	
	
	public static class Directions{
		public static final int LEFT=0;
		public static final int UP=1;
		public static final int RIGHT=2;
		public static final int DOWN=3;
		public static final int INAIR=4;
		public static final int FALL=5;
		
	}
	
	public static class ActiuniCarte{
		public static final int STATIC=0;
		public static final int MOVING=1;
	}
	
	
	
	public static class Inventory_Constants{
		
		    public static final int CARTE_1 = 0;
		    public static final int CARTE_2 = 1;
		    public static final int CARTE_3 = 2;
		    public static final int POTIUNE_HEAL = 3;
		    public static final int POTIUNE_ENERGIE = 4;
		    public static final int FLACOANE = 5;
		    public static final int SUBSTANTE_CHIMICE = 6;
		    public static final int ARMURA_GRI = 7;
		    public static final int ARMURA_VERDE = 8;
		    public static final int DIAMONDS = 9;
		    public static final int POTIUNE_DAMAGE = 10;
		    public static final int POTIUNE_ARMURA = 11;
		    public static final int POTIUNE_PERMANENTA_HEAL = 12;
		    public static final int POTIUNE_PERMANENTA_ENERGIE = 13;
		    public static final int NOROCU = 14;
		    public static final int FORMULA_CHIMICA_POTIUNE_HEAL = 15;
		    public static final int FORMULA_CHIMICA_POTIUNE_ENERGIE = 16;
		    public static final int ZAHAR_ENERGIE = 17;
		    public static final int ARMA_BOOST = 18;
		    public static final int APA=19;
		   public static final int ARMURA_ALBASTRA=20;
		   public static  int Get_Action(int used_item, Game game) {
		        switch (used_item) {
		            case CARTE_1:
		            case CARTE_2:
		            case CARTE_3:
		                int ind_carte = used_item - CARTE_1; // Assuming CARTE_1 starts from 0
		                game.getCarte().ind_carte = ind_carte;
		                game.getCarte().pagina_curenta=0;
		                GameState.setCurrentOption(GameState.CARTE);
		                return 1;
		            case POTIUNE_HEAL:
		                int current_health=game.getCaracter().getStatistici().getRemaining_health();
		                int total_health=game.getCaracter().getStatistici().getTotal_health();
		                
		                int health_restore=game.getCaracter().getStatistici().getRestore_health();
		                
		                if(current_health+health_restore>total_health) {
		                	game.getCaracter().getStatistici().setRemaining_health(total_health);
		                }
		                else
		                	game.getCaracter().getStatistici().setRemaining_health(health_restore+current_health);    
		                return 1;
      
		            case POTIUNE_ENERGIE:
		                int current_energy=game.getCaracter().getStatistici().getRemaining_Energy();
		                int total_energy=game.getCaracter().getStatistici().getTotal_Stamina();
		                int energy_restore=game.getCaracter().getStatistici().getRestore_energy();
		                if (current_energy+energy_restore>total_energy) {
		                	game.getCaracter().getStatistici().setRemaining_Energy(total_energy);
		                }
		                else
		                	game.getCaracter().getStatistici().setRemaining_Energy(current_energy+energy_restore);
		                return 1;
		                 
		            case ARMURA_GRI:
		            
		            	game.getCaracter().cul_car=0;
		            	return 1;
		            case ARMURA_VERDE:
		            
		            	game.getCaracter().cul_car=1;
		            	
		            	return 1;
		            case ARMURA_ALBASTRA:
		            	game.getCaracter().cul_car=2;
		            	
		            	return 1;
		            case POTIUNE_ARMURA:
		                  int defense=game.getCaracter().getStatistici().getPlayer_defense();
		                  int upgrade=game.getCaracter().getStatistici().getUpgrade_defense();
		                  game.getCaracter().getStatistici().setPlayer_defense(defense+upgrade);
		            	return 1;
		            case POTIUNE_PERMANENTA_HEAL:
		                 current_health=game.getCaracter().getStatistici().getRemaining_health();
		                 total_health=game.getCaracter().getStatistici().getTotal_health();
		                 int health_upgrade=game.getCaracter().getStatistici().getUpgrade_health();
		                 
		                 game.getCaracter().getStatistici().setRemaining_health(health_upgrade+current_health);
		                 game.getCaracter().getStatistici().setTotal_health(total_health+health_upgrade);
		                 
		                return 1;
		            case POTIUNE_DAMAGE:
		            	int damage=game.getCaracter().getStatistici().getDamage_de_baza();
		               game.getCaracter().getStatistici().setDamage_de_baza(damage+1);
		                return 1;
		            case POTIUNE_PERMANENTA_ENERGIE:
		            	  current_energy=game.getCaracter().getStatistici().getRemaining_Energy();
			              total_energy=game.getCaracter().getStatistici().getTotal_Stamina();
			              int upgrade_energy=game.getCaracter().getStatistici().getUpgrade_stamina();
			              game.getCaracter().getStatistici().setRemaining_Energy(upgrade_energy+current_energy);
			              game.getCaracter().getStatistici().setTotal_Stamina(upgrade_energy+total_energy);
			              return 1;
		            default:
		                System.out.print("Can't be used");
		                return -1;
		        }
		        // Or any other suitable default value indicating unknown action
		    }
		
		
		
		
	}
	
	
	
	}
	

