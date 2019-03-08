import java.util.ArrayList;
import java.util.Scanner;

public class Poker {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Poker p = new Poker();
		
		String selection;
		System.out.print("Welcome to Virtual Poker. ");
		do {
			System.out.println("Please select what you would like to do.");
			System.out.print("1. Play\n2. Learn the rules\n3. Quit\n\t> ");
			selection = in.nextLine();
		} while (!isNumeric(selection) || (Integer.parseInt(selection) < 1 || Integer.parseInt(selection) > 3));
		
		int option = Integer.parseInt(selection);
		if (option == 3) {
			System.exit(0);
		} else if (option == 2) {
			// describe the rules
			do {
				System.out.print("Ready to play? (y/n)\n\t> ");
				selection = in.nextLine();
			} while (!selection.toLowerCase().equals("y") && !selection.toLowerCase().equals("n"));
			if (selection.toLowerCase().equals("n")) {
				System.exit(0);
			}
		}
		
		// add an initial bet
		
		String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
		String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
		int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		Deck deck = new Deck(ranks, suits, values);
		
		ArrayList<Card> hand = new ArrayList<Card>();
		int maxTrades = 3;
		for (int i = 0; i < 5; i++) {
			hand.add(deck.deal());
			if (hand.get(i).rank().equals("Ace"))
				maxTrades = 4;
		}
		
		String trades;
		System.out.print("Here is your hand. ");
		do {
			System.out.println("Please select up to " + maxTrades + " cards to trade in using a list (ex: 1,4,5).");
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + ". " + hand.get(i));
			}
			System.out.print("\t> ");
			trades = in.nextLine();
		} while (!p.validDataInput(trades, maxTrades));
	}
	
	public boolean validDataInput(String data, int max) {
		for (int i = 0; i < data.length(); i++) {
			if (!isNumeric(data.substring(i, i + 1))) {
				if (data.charAt(i) != ',') {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean isNumeric(String str) {  
		try {  
			double d = Double.parseDouble(str);  
		} catch(NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}
}