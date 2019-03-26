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
			System.out.println("You will be dealt five cards with the option to turn up to three in, up to four if you have an ace.");
			System.out.println("Your goal is to create the best possible hand with your five cards to get a better payout.");
			System.out.println("Your payout is calculated using the highest multiplier from the cards in your deck.");
			System.out.println("\nHands are as follows:\nPair of 10s or higher: x1 multiplier\nTwo pairs (of any values): x2 multiplier");
			System.out.println("Three of a kind: x3 multiplier\nStraight (5 cards with incrementing values): x4 multiplier");
			System.out.println("Flush (5 cards with the same suit): x6 multiplier\nFull house (a pair and a three of a kind): x9 multiplier");
			System.out.println("Four of a kind: x25 multiplier\nStraight flush (a straight where all cards have the same suit): x50 multiplier");
			System.out.println("Royal flush (a 10, Jack, Queen, King, and Ace of the same suit): x250 multiplier\n\nPlease consult our website for more in-depth rules.\n");
			do {
				System.out.print("Ready to play? (y/n)\n\t> ");
				selection = in.nextLine();
			} while (!selection.toLowerCase().equals("y") && !selection.toLowerCase().equals("n"));
			if (selection.toLowerCase().equals("n")) {
				System.exit(0);
			}
		}
		
		String bet = "0";
		
		while (!isNumeric(bet) || Integer.parseInt(bet) < 1 || Integer.parseInt(bet) > 100000) {
			System.out.print("\nHow much would you like to bet? Please enter a positive integer no greater than 100,000.\n\t> ");
			bet = in.nextLine();
		}
		
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
		
		boolean first = true;
		String tradeList;
		System.out.print("\nHere is your hand. ");
		do {
			if (!first) {
				System.out.println("");
			} else {
				first = false;
			}
			System.out.println("Please select up to " + maxTrades + " cards to trade in using a list (ex: 1,4,5).");
			for (int i = 0; i < 5; i++) {
				System.out.println((i + 1) + ". " + hand.get(i));
			}
			System.out.print("\t> ");
			tradeList = in.nextLine();
		} while (!p.validDataInput(tradeList, maxTrades));
		
		ArrayList<Integer> trades = p.createIntList(tradeList);
		int index = -1;
		for (int i = 0; i < trades.size(); i++) {
			index = trades.get(i) - 1;
			hand.set(index, deck.deal());
		}
		
		System.out.println("\nHere is your final hand.");
		for (int i = 0; i < 5; i++) {
			System.out.println((i + 1) + ". " + hand.get(i));
		}
		System.out.println("");
		
		/*ArrayList<Card> checker = new ArrayList<Card>();
		checker.add(new Card("Clubs", "Six", 12));
		checker.add(new Card("Clubs", "Two", 14));
		checker.add(new Card("Clubs", "Three", 13));
		checker.add(new Card("Clubs", "Four", 11));
		checker.add(new Card("Clubs", "Five", 10));*/
		
		String phrase = "No winning hand.";
		int multiplier = 0;
		if (p.containsPair(hand)) {
			phrase = "One pair!";
			multiplier = 1;
		}
		if (p.containsTwoPair(hand)) {
			phrase = "Two pairs!";
			multiplier = 2;
		}
		if (p.containsThreeOfAKind(hand)) {
			phrase = "Three of a kind!";
			multiplier = 3;
		}
		if (p.containsStraight(hand)) {
			phrase = "Straight!";
			multiplier = 4;
		}
		if (p.containsFlush(hand)) {
			phrase = "Flush!";
			multiplier = 6;
		}
		if (p.containsFullHouse(hand)) {
			phrase = "Full house!";
			multiplier = 9;
		}
		if (p.containsFourOfAKind(hand)) {
			phrase = "Four of a kind!";
			multiplier = 25;
		}
		if (p.containsStraightFlush(hand)) {
			phrase = "Straight flush!";
			multiplier = 50;
		}
		if (p.containsRoyalFlush(hand)) {
			phrase = "Royal flush!";
			multiplier = 250;
		}
		
		System.out.println("Result:\n" + phrase + "\nMultiplier = " + multiplier + ".\nPayout = " + (multiplier * Integer.parseInt(bet)) + ".\nThank you for playing!");
		
		in.close();
	}
	
	public boolean containsRoyalFlush(ArrayList<Card> hand) {
		if (containsStraight(hand) && containsFlush(hand)) {
			int min = hand.get(0).pointValue();
			for (int i = 0; i < 5; i++) {
				if (hand.get(i).pointValue() < min) {
					min = hand.get(i).pointValue();
				}
			}
			if (min == 10) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsStraightFlush(ArrayList<Card> hand) {
		if (containsStraight(hand) && containsFlush(hand)) {
			return true;
		}
		return false;
	}
	
	public boolean containsFourOfAKind(ArrayList<Card> hand) {
		int counter = 0;
		int value = hand.get(0).pointValue();
		for (int i = 0; i < 5; i++) {
			if (hand.get(i).pointValue() != value) {
				counter++;
			}
		}
		if (counter > 1) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean containsFullHouse(ArrayList<Card> hand) {
		if (containsThreeOfAKind(hand)) {
			int intI = 0;
			int intJ = 0;
			int intK = 0;
			for (int i = 0; i < hand.size(); i++) {
				for (int j = 0; j < hand.size(); j++) {
					if (hand.get(i).pointValue() == hand.get(j).pointValue() && j != i) {
						for (int k = 0; k < hand.size(); k++) {
							if (hand.get(i).pointValue() == hand.get(k).pointValue() && i != k && j != k) {
								intI = i;
								intJ = j;
								intK = k;
							}
						}
					}
				}
			}
			ArrayList<Card> pair = new ArrayList<Card>();
			for (int i = 0; i < 5; i++) {
				if (hand.get(i) != hand.get(intI) && hand.get(i) != hand.get(intJ) && hand.get(i) != hand.get(intK)) {
					pair.add(hand.get(i));
				}
			}
			if (pair.get(0).pointValue() == pair.get(1).pointValue()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean containsFlush(ArrayList<Card> hand) {
		String suit = hand.get(0).suit();
		for (int i = 1; i < hand.size(); i++) {
			if (!hand.get(i).suit().equals(suit)) {
				return false;
			}
		}
		return true;
	}
	
	public boolean containsStraight(ArrayList<Card> hand) {
		int min = hand.get(0).pointValue();
		for (int i = 1; i < hand.size(); i++) {
			if (hand.get(i).pointValue() < min) {
				min = hand.get(i).pointValue();
			}
		}
		boolean increment = false;
		for (int i = 1; i < 5; i++) {
			for (int j = 0; j < hand.size(); j++) {
				if (hand.get(j).pointValue() == min + i) {
					increment = true;
				}
			}
			if (increment == false)
				return false;
			else
				increment = false;
		}
		return true;
	}
	
	public boolean containsThreeOfAKind(ArrayList<Card> hand) {
		for (int i = 0; i < hand.size(); i++) {
			for (int j = 0; j < hand.size(); j++) {
				if (hand.get(i).pointValue() == hand.get(j).pointValue() && j != i) {
					for (int k = 0; k < hand.size(); k++) {
						if (hand.get(i).pointValue() == hand.get(k).pointValue() && i != k && j != k) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean containsTwoPair(ArrayList<Card> hand) {
		int count = 0;
		for (int i = 0; i < hand.size(); i++) {
			for (int j = i; j < hand.size(); j++) {
				if (hand.get(i).pointValue() == hand.get(j).pointValue() && j != i) {
					count++;
				}
			}
		}
		if (count > 1)
			return true;
		else
			return false;
	}
	
	public boolean containsPair(ArrayList<Card> hand) {
		for (int i = 0; i < hand.size(); i++) {
			for (int j = i; j < hand.size(); j++) {
				if (hand.get(i).pointValue() == hand.get(j).pointValue() && j != i && hand.get(i).pointValue() >= 10) {
					return true;
				}
			}
		}
		return false;
	}
	
	public ArrayList<Integer> createIntList(String data) {
		String entry = "";
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < data.length(); i++) {
			if (data.charAt(i) != ',') {
				entry += data.charAt(i);
			} else if (!entry.equals("")) {
				list.add(Integer.parseInt(entry));
				entry = "";
			}
			
			if (i == data.length() - 1) {
				list.add(Integer.parseInt(entry));
			}
		}
		return list;
	}
	
	public boolean validDataInput(String data, int max) {
		int count = 0;
		for (int i = 0; i < data.length(); i++) {
			if (!isNumeric(data.substring(i, i + 1))) {
				if (data.charAt(i) != ',') {
					return false;
				}
			} else if (Integer.parseInt(data.substring(i, i + 1)) > 5) {
				return false;
			} else {
				count++;
			}
		}
		if (count > max) {
			return false;
		}
		return true;
	}
	
	public static boolean isNumeric(String str) {  
		try {  
			@SuppressWarnings("unused")
			Integer i = Integer.parseInt(str);  
		} catch(NumberFormatException nfe) {  
			return false;  
		}  
		return true;  
	}
}