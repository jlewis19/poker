import java.util.ArrayList;

public class Deck extends ArrayList<Card> {
	private ArrayList<Card> cards;
	private int size;
	
	public Deck(String[] ranks, String[] suits, int[] values) {
		cards = new ArrayList<Card>();
		for (int i = 0; i < ranks.length; i++) {
			for (int j = 0; j < suits.length; j++) {
				cards.add(new Card(suits[j], ranks[i], values[i]));
			}
		}
		size = cards.size();
		shuffle();
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public int size() {
		return size;
	}

	public void shuffle() {
		int r;
		for (int k = size - 1; k > -1; k--) {
			r = (int) ((k + 1) * Math.random());
			Card temp = cards.get(k);
			cards.set(k, cards.get(r));
			cards.set(r, temp);
		}
	}
	
	public Card deal() {
		if (isEmpty())
			return null;
		
		size--;
		return cards.get(size);
	}
	
	@Override
	public String toString() {
		String rtn = "size = " + size + "\nUndealt cards: \n";

		for (int k = size - 1; k >= 0; k--) {
			rtn = rtn + cards.get(k);
			if (k != 0) {
				rtn = rtn + ", ";
			}
			if ((size - k) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn = rtn + "\n";
			}
		}

		rtn = rtn + "\nDealt cards: \n";
		for (int k = cards.size() - 1; k >= size; k--) {
			rtn = rtn + cards.get(k);
			if (k != size) {
				rtn = rtn + ", ";
			}
			if ((k - cards.size()) % 2 == 0) {
				// Insert carriage returns so entire deck is visible on console.
				rtn = rtn + "\n";
			}
		}

		rtn = rtn + "\n";
		return rtn;
	}
}