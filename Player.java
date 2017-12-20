import java.util.ArrayList;

public class Player extends Person{
	private String name; 				//(5pt) 玩家姓名
	private int chips; 					//(5pt) 玩家有的籌碼
	private int bet; 					//(5pt) 玩家此局下注的籌碼
	private ArrayList<Card> oneRoundCard; 	//(5pt)	此牌局的卡
	int total=0;
	
	public Player(String name, int chips) {	// (5pt) 新增Player物件時，需要姓名、擁有的籌碼等兩個參數
		this.name=name;
		this.chips=chips;
	}
	
	public String getName() {	//(5pt)
		return name;
	}
	
	public int makeBet() {		//(5pt)
		if(chips==0 || chips<bet) {
			bet=chips;
			return bet;
		}
		bet=50;						//基本為1
		return bet;
	}
	
	public int getCurrentChips() {		//(5pt) 回傳玩家現有籌碼
		return chips;
	}
	public void increaseChips (int diff) {	//(5pt) 玩家籌碼變動，setter
		chips+=diff;
	}
	
	public void sayHello() {				//(5pt) 玩家Say Hello
		System.out.println("Hello, I am " + name + ".");
		System.out.println("I have " + chips + " chips.");
	}

	@Override
	public boolean hit_me(Table tbl) {
		if(getTotalValue()<=15) {					// 不大於15點時 可以繼續要牌
			return true;
		}
		return false;
	}
}
