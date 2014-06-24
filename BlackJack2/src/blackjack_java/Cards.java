package blackjack_java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Cards {
	private ArrayList<Card> cards;
	private int cardNumber;
	public Cards(int decks) {
		cardNumber = decks * 52;
		initialize();
	}
	private void initialize() {
		cards = new ArrayList<Card>();
		for (int i = 0; i < cardNumber; i++) {
			cards.add(new Card(i / (cardNumber / 4), i % 13));
		}
		shuffle();
	}
	public void print() {
		for (Card d : cards) {
			d.print();
		}
	}
	public Card draw() {
		if (cards.size() < cardNumber / 2) {
			cards = null;
			System.gc();
			initialize();
		}
		Card c = cards.remove(0);
		return c;
	}
	private void shuffle() {
		Random rand = new Random();
		int amount = cards.size();
		for (int i = 0; i < amount; i++) {
			int j = rand.nextInt(amount);
			Collections.swap(cards, i, j);
		}
	}	
}