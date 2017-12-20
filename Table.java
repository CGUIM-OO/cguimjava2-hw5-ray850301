import java.util.ArrayList;

public class Table {
	public static final int MAXPLAYER =4;
	
	private Deck deck;
	private Player[] player;	// 所有的玩家
	private Dealer dealer;		// 莊家
	private int[] pos_betArray = new int[MAXPLAYER];	// 每個玩家在一局下的注
	private int nDeck;			
	private ArrayList <Card> allPlayerCard = new ArrayList<Card>();
	private ArrayList <Card> dealerCard = new ArrayList<Card>();
	
	public Table(int nDeck) {
		this.nDeck =nDeck;
		this.deck=new Deck(nDeck);
		this.player=new Player[MAXPLAYER];
		allPlayerCard.clear();
		dealerCard.clear();
	}
	
	public void set_player(int pos, Player p) {
		player[pos]=p;
	}
	
	public Player[] get_player() {
		return player;
	}
	
	public void set_dealer(Dealer d) {		// dealer
		this.dealer=d;
	}
	
	public Card get_face_up_card_of_dealer() {
		return dealerCard.get(1);  // 打開 第2張牌
	}
	
	private void distribute_cards_to_dealer_and_players() {
		//發牌給玩家跟莊家，先發兩張打開的牌給玩家
		for(int i=0;i<MAXPLAYER;i++) {
			// 每人 2張牌
			allPlayerCard = new ArrayList<Card>();
			dealerCard = new ArrayList<Card>();
			allPlayerCard.add(deck.getOneCard(true));
			allPlayerCard.add(deck.getOneCard(true));
			player[i].setOneRoundCard((allPlayerCard));
			}
		//再一張蓋著的牌，以及一張打開的牌給莊家。(提示: setOneRoundCard())
		dealerCard.add(deck.getOneCard(false));
		dealerCard.add(deck.getOneCard(true));
		dealer.setOneRoundCard(dealerCard);
		
		//發牌給莊家後，在畫面上印出莊家打開的牌"Dealer's face up card is " (提示: printCard())
		System.out.print("Dealer's face up card is: ");
		get_face_up_card_of_dealer().printCard();
	}
	// 下注
	private void ask_each_player_about_bets() {
		for (int i=0;i<player.length;i++) {
			player[i].sayHello();	//請每個玩家打招呼
			player[i].makeBet();		//請每個玩家下注
			pos_betArray[i]= player[i].makeBet();	//紀錄所下的注
		}
		System.out.println();
	}
	
	private void ask_each_player_about_hits() {
		for (int i=0;i<player.length;i++) {
			if(player[i].getTotalValue()<=21) {
				//如果玩家的目前點數小於21點才有要牌的機會
				boolean hit=false;
				do{
					hit=player[i].hit_me(this); //this
					if(hit){
						player[i].getOneRoundCard().add(deck.getOneCard(true));
						System.out.print("Hit! ");
						System.out.println(player[i].getName()+"'s Cards now:");
						for(int j=0; j<player[i].getOneRoundCard().size(); j++) {
							player[i].getOneRoundCard().get(j).printCard();
						}//玩家要牌
						System.out.println();
					}
					else {
						System.out.println(player[i].getName()+", Pass hit!");
						System.out.println(player[i].getName()+", Final Card:");
						for(int j=0; j<player[i].getOneRoundCard().size(); j++) {
							player[i].getOneRoundCard().get(j).printCard();
						}	//玩家要牌
						System.out.println();
					}	//玩家不要牌
				}while(hit);
			}
			else {	//玩家點數超過21點就顯示爆掉
				System.out.println(player[i].getName()+", is over 21 point. Pass hit!");
				System.out.println(player[i].getName()+", Final Card:");
				for(int j=0; j<player[i].getOneRoundCard().size(); j++) {
					player[i].getOneRoundCard().get(j).printCard();
				}//玩家要牌
				System.out.println();
				allPlayerCard = new ArrayList<Card>();
			}
		}
	}
	private void ask_dealer_about_hits() {
		//發牌給玩家跟莊家，先發兩張打開的牌給玩家
		//再一張蓋著的牌，以及一張打開的牌給莊家。(提示: setOneRoundCard())
		//發牌給莊家後，在畫面上印出莊家打開的牌"Dealer's face up card is " (提示: printCard())
		boolean hit=false;
				if(dealer.getTotalValue()<=21) {
					do{
						hit=dealer.hit_me(this); //this
						if(hit){
							dealerCard.add(deck.getOneCard(true));
							dealer.setOneRoundCard(dealerCard);
							System.out.print("Hit! ");
							System.out.println("Dealer's Cards now:");
							for(int j=0; j<dealer.getOneRoundCard().size(); j++) {
								dealer.getOneRoundCard().get(j).printCard();
							}
						}
						else{
							System.out.println("Dealer Pass hit!");
							System.out.println("Dealer's Final Card:");
							for(int j=0; j<dealer.getOneRoundCard().size(); j++) {
								dealer.getOneRoundCard().get(j).printCard();
							}
						}
					}while(hit);
				}else {// 假如大於21點  禁止要牌
					System.out.println("Dealer is over 21 point. Pass hit!");
					System.out.println("Dealer's Final Card:");
					for(int j=0; j<dealer.getOneRoundCard().size(); j++) {
						dealer.getOneRoundCard().get(j).printCard();
					}
				}
				dealerCard = new ArrayList<Card>();
				System.out.println("Dealer's hit is Over!!!\n");
	}
	
	private void calculate_chips() {
		System.out.print("Dealer's card value is "+dealer.getTotalValue()+", Cards:\n");
		dealer.printAllCard();
		System.out.println();
		// 判斷輸贏
		for(int i=0;i<player.length;i++) {
			System.out.print(player[i].getName()+"'s card value is "+player[i].getTotalValue()+", Cards:\n");
			player[i].printAllCard();
			if(dealer.getTotalValue()>21) {
				System.out.println("Dealer is over 21 points!");
				if(player[i].getTotalValue()<=21){
					player[i].increaseChips(pos_betArray[i]);
					System.out.println(player[i].getName()+" Get "+pos_betArray[i]+" Chips, the Chips now is: "+player[i].getCurrentChips()+"\n");
				}	//	玩家小於21點且莊家爆
				else if(player[i].getTotalValue()>21) {
					System.out.println(player[i].getName()+" is over 21 points!");
					System.out.print(player[i].getName()+"'s card value is "+player[i].getTotalValue());
					System.out.println(", chips have no change! The Chips now is: "+player[i].getCurrentChips()+"\n");
				}	//	玩家大於21點且莊家爆
			}
			else if(dealer.getTotalValue()<=21) {
				if(player[i].getTotalValue()<=21){
					if(player[i].getTotalValue()>dealer.getTotalValue()) {
						player[i].increaseChips(pos_betArray[i]);
						System.out.print(player[i].getName()+"'s card value is "+player[i].getTotalValue()+".\n");
						System.out.println(player[i].getName()+" Get "+pos_betArray[i]+" Chips, the Chips now is: "+player[i].getCurrentChips()+"\n");
					}	//	玩家大於莊家 贏
					else if(player[i].getTotalValue()<dealer.getTotalValue()) {
						player[i].increaseChips(-pos_betArray[i]);
						System.out.print(player[i].getName()+"'s card value is "+player[i].getTotalValue()+".\n");
						System.out.println(player[i].getName()+" loses "+pos_betArray[i]+" Chips, the Chips now is: "+player[i].getCurrentChips()+"\n");
					}	//	玩家小於莊家 輸
					else if(player[i].getTotalValue()==dealer.getTotalValue()) {
						System.out.print(player[i].getName()+"'s card value is "+player[i].getTotalValue());
						System.out.println(", chips have no change! The Chips now is: "+player[i].getCurrentChips()+"\n");
					}	//玩家等於莊家 平手
				}
				else if(player[i].getTotalValue()>21) {
					player[i].increaseChips(-pos_betArray[i]);
					System.out.print(player[i].getName()+"'s card value is "+player[i].getTotalValue()+" which is over 21 points!\n");
					System.out.println(player[i].getName()+" loses "+pos_betArray[i]+" Chips, the Chips now is: "+player[i].getCurrentChips()+"\n");
				}	//	玩家大於21點 輸
			}
		}
	}

	public int[] get_players_bet() {
		return pos_betArray;
	}
	public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}

	
	
}
