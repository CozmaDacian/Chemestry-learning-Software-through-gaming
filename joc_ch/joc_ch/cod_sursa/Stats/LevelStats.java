package Stats;

import java.io.Serializable;

public class LevelStats implements Serializable {
   public int obiecte_totale;
  public int obiecte_colectate;
   public int total_enemies;
   public int enemies_defeated;
   public int question_ans;
 public  int question_correct;
   public LevelStats(int nr_ob,int ob_col,int t_en,int en_d,int q_asw,int q_cor){
	   this.obiecte_totale=nr_ob;
	   this.obiecte_colectate=ob_col;
	   this.total_enemies=t_en;
	   this.enemies_defeated=en_d;
	   this.question_ans=q_asw;
	   this.question_correct=q_cor;
   }
   
}
