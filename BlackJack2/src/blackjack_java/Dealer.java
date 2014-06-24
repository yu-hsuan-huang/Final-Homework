package blackjack_java;


public class Dealer extends GamePlayer {
	Cards cards;
	public Dealer(Cards c) {
		super("");
		cards = c;
	}
	public void deal(GamePlayer player) {
		Card c = cards.draw();
		player.getCard(c);
	}
}