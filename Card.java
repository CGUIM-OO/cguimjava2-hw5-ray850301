
public class Card {
	enum Suit {Club, Diamond, Heart, Spade}  // 列舉花色: 梅花♣ 方塊♦ 愛心♥ 黑桃♠
	private Suit suit; //Definition: 1~4, ♣ Clubs=1, ♦ Diamonds=2, ♥ Hearts=3, ♠ Spades=4
	private int rank; //1~13	
	/**
	 * @param s suit
	 * @param r rank
	 */
	public Card(Suit s,int r){
		suit=s;
		rank=r;
	}	
	//TODO: 1. Please implement the printCard method (20 points, 10 for suit, 10 for rank)
	public void printCard(){
		// 轉換後列印
		String su="";       // 花色  String Type
		String ra="";		// 點數  String Type
		switch (suit) {
        case Spade:
        	su="♠";		// Spades 黑桃
        	break;
        case Heart:
        	su="♥";		// Hearts 愛心
        	break;
        case Diamond:
        	su="♦";		// Diamonds 方塊
        	break;
        case Club:
        	su="♣";		// Clubs 梅花
        	break;
		}
		
		switch (rank){
    	case 13:
    		ra="K";
    		break;
    	case 12:
    		ra="Q";
    		break;
    	case 11:
    		ra="J";
    		break;
    	case 1:
    		ra="Ace";
    		break;
    	default:
    		ra=getRank()+"";
    		rank =getRank();
    		break;
    		}
		System.out.println(suit+" "+ra+" ");
		}
	public Suit getSuit(){
		return suit;
	}
	public int getRank(){
		if (rank>10) {
			return 10;
			}
		return rank;
	}
}
