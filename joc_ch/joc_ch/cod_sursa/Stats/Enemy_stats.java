package Stats;

public class Enemy_stats {
   
	private int total_health=60;
	private int remaining_health=60;
	private int defense_enemy;
	private int damege_atac_1;
	private int damage_atac_2;
	
	private static final int ROBOT_ALB=0;
	private static final int ROBOT_ROSU=1;
	private static final int SCIENTIST=2;
	
	public Enemy_stats(int type,int level,int number_of_enemies) {
		
		initialize_abilities_based_on_type(type,level,number_of_enemies);
	}
	public void initialize_abilities_based_on_type(int type,int level,int number_of_enemies) {
		float multiplication_factor;
		
		
		if(number_of_enemies==1)
		 multiplication_factor=1.5f;
		else
		if(number_of_enemies==2)
			multiplication_factor=1.15f;
		else
			multiplication_factor=1f;
		
		
		switch(type) {
		
		
		case ROBOT_ALB:
		this.damege_atac_1=(int) (5*multiplication_factor);
		this.damage_atac_2=(int) (6*multiplication_factor);
		this.remaining_health=(int) (50*multiplication_factor);
		this.total_health=(int) (50*multiplication_factor);
		this.defense_enemy=(int) (1*multiplication_factor);
		break;
		// 7 ,9 ,30,40,2
		case ROBOT_ROSU:
			this.damege_atac_1=(int) (6*multiplication_factor);
			this.damage_atac_2=(int) (8*multiplication_factor);
			this.remaining_health=(int) (40*multiplication_factor);
			this.total_health=(int) (40*multiplication_factor);
			this.defense_enemy=(int) (2*multiplication_factor);
		break;
		
		case SCIENTIST:
		this.damege_atac_1=12;
		this.damage_atac_2=15;
		this.remaining_health=70;
		this.total_health=70;
		this.defense_enemy=3;
		
		
		}
	}
	
	
	
	
	public int getDamage_atac_2() {
		return damage_atac_2;
	}
	public void setDamage_atac_2(int damage_atac_2) {
		this.damage_atac_2 = damage_atac_2;
	}
	public int getDamege_atac_1() {
		return damege_atac_1;
	}
	public void setDamege_atac_1(int damege_atac_1) {
		this.damege_atac_1 = damege_atac_1;
	}
	public int getDefense_enemy() {
		return defense_enemy;
	}
	public void setDefense_enemy(int defense_enemy) {
		this.defense_enemy = defense_enemy;
	}
	public int getRemaining_health() {
		return remaining_health;
	}
	public void setRemaining_health(int remaining_health) {
		this.remaining_health = remaining_health;
	}
	public int getTotal_health() {
		return total_health;
	}
	public void setTotal_health(int total_health) {
		this.total_health = total_health;
	}
}
