package blackjack_java;

import java.io.*;
import java.util.*;

public class Blackjack {
  private Cards cards;
  public Player player;
  public Dealer dealer;	
  public Blackjack (String playername) {
	cards = new Cards(1);
	player = new Player(playername);
	dealer = new Dealer(cards);
	
	//Initial Round
	dealer.deal(dealer);
	dealer.deal(dealer);
	
	dealer.deal(player);
	dealer.deal(player);
	
  }
  public void hit() {
	  dealer.deal(player);
  }
  public int compete() {
		if (player.point() > 21)
			return -1;
		else if (dealer.point() > 21)
			return 1;
		else if (player.point() > dealer.point())
			return 1;
		else if (player.point() < dealer.point())
			return -1;
		else
			return 0;
  
  }
}