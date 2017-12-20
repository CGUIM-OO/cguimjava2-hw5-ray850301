import java.util.ArrayList;
import java.util.Random;

public class Deck {
	private ArrayList<Card> cards;		// 未用牌
	private ArrayList<Card> usedCard;	// 發過的牌
	private ArrayList openCard;			// 存放此副牌中所有打開的牌，洗牌時要重置
	public int nUsed=0;					// 計數
	
	public Deck(int nDeck){
		cards=new ArrayList<Card>();
		usedCard=new ArrayList<Card>();

		for(int n=0; n<nDeck; n++) {						// 有 N副牌
			for(Card.Suit s : Card.Suit.values()) {			// 利用       ENUM
				for(int j=1; j<=13; j++){					// 1~13
					Card card=new Card(s,j);	
					cards.add(card);
					}
				}
			}
		shuffle();
		}
	
	public void printDeck(){
		int n=1;	// 從第一副牌開始
		for(int i=0;i<cards.size();i++) {
			if(i%52==0) {	// 每發52張牌就是下一副牌
				System.out.print("\n第"+n+"副牌:\n");
				n++;
			}
			cards.get(i).printCard();
		}
		System.out.println("");				//	純粹想換行
	}
	
	public ArrayList<Card> getAllCards(){
		return cards;
	}
	//	洗牌
	public void shuffle() {
		Random rnd = new Random();
		for(int i = 0; i<cards.size(); i++){		// 重複52次           cards.size()=52
			int j = rnd.nextInt(cards.size())+1;	// j = 1~52
			
			Card temp = cards.get(i);			// 把第一張暫存起來
			cards.set(i, cards.get(j-1));		// 再把原牌堆中拉出來新增的牌刪掉，換上隨機位置的一張牌
			cards.set((j-1), temp);				// 再把暫存的那張牌去替換掉，原本隨機位置抽出的那張牌
		}
		nUsed=0;								// 發牌數歸零
		usedCard= new ArrayList<Card>();		// 洗完牌，把用過的牌清除
		openCard= new ArrayList<Card>();		// 洗完牌，把此輪打開過的牌清除
	}
	
	public Card getOneCard (boolean isOpened) {
		if(nUsed==52) {							// 當發完52張牌就要洗牌
			shuffle();
			}
		Card givecard = cards.get(nUsed);		// 從牌堆中依序發牌
		nUsed++;
		usedCard.add(givecard);					// 將發過的牌記憶到usedCard陣列
		if(isOpened) {
			openCard.add(givecard);
		}
		//cards.remove(0);
		return givecard;
	}
	
	public ArrayList getOpenedCard() {		//回傳此副牌中所有打開過的牌，意即openCard (5pt)
		return openCard;
	}
}
