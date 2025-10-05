package states;

public enum GameState {

	PLAYING,QUIZ,MENU,INVENTAR,CARTE,COMBAT,SKILLTREE,DEAD,LEVEL,EDITARE,PAUSE_MENU,EXPLICATII,REPORT;
	public static GameState state=MENU;
	
	public static void setCurrentOption(GameState newOption)
	{
	state=newOption;
    }
}
