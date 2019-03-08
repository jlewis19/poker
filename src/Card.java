public class Card implements Comparable {
	private String suit;
	private String rank;
	private int pointValue;
	
	public Card(String suit, String rank, int pointValue) {
		this.suit = suit;
		this.rank = rank;
		this.pointValue = pointValue;
	}
	
	public String suit() {
		return suit;
	}
	
	public String rank() {
		return rank;
	}
	
	public int pointValue() {
		return pointValue;
	}
	
	public boolean matches(Card otherCard) {
		return otherCard.suit().equals(this.suit())
			&& otherCard.rank().equals(this.rank())
			&& otherCard.pointValue() == this.pointValue;
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}