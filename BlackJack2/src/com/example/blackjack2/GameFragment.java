package com.example.blackjack2;

import java.util.ArrayList;

import blackjack_java.Blackjack;
import blackjack_java.Card;
import blackjack_java.Cards;
import blackjack_java.Dealer;
import blackjack_java.GamePlayer;
import blackjack_java.Player;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameFragment extends Fragment implements OnClickListener{
	
	
	String userName;
	int ID,userMoney,bet;
	TextView nameText,dealer;
	Button hit,stay,again;
	ArrayList<ImageView> playerCards, dealerCards;
	ImageView judge;
	Blackjack game;
	
	public GameFragment(){
		
	}
	
	public void setAll(int id,String name,int m,int b){
		this.ID = id;
		this.userName = name;
		this.userMoney = m;
		this.bet = b;
	}
	
	public int getIdentifierByString(String str){
		int id = getActivity().getResources().getIdentifier(str, "id", getActivity().getPackageName());
		return id;
	}
	
	public int getCareDrawableByString(String suit,String face){
		int id = getActivity().getResources().getIdentifier(suit + "_" + face, "drawable", getActivity().getPackageName());
		return id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View gfrag = inflater.inflate(R.layout.fragment_game, container,
				false);
		//declear
		judge = (ImageView) gfrag.findViewById(R.id.imageView01);
		judge.setVisibility(View.INVISIBLE);
		hit = (Button) gfrag.findViewById(R.id.button1);
		stay = (Button) gfrag.findViewById(R.id.button2);
		again = (Button) gfrag.findViewById(R.id.button3);
		again.setVisibility(View.INVISIBLE);
		dealer = (TextView) gfrag.findViewById(R.id.textView1);
		nameText = (TextView) gfrag.findViewById(R.id.textView2);
		//nameText.setText(userName);
		playerCards = new ArrayList<ImageView>();
		dealerCards = new ArrayList<ImageView>();
		//onclick
		hit.setOnClickListener(this);
		stay.setOnClickListener(this);
		again.setOnClickListener(this);
		//123456
		for(int i = 1; i <= 10; i++){
        	int id1 = getIdentifierByString("imageView" + i);
        	int id2 = getIdentifierByString("ImageView" + i);
        	
        	ImageView v1 = (ImageView) gfrag.findViewById(id1);
        	ImageView v2 = (ImageView) gfrag.findViewById(id2);
        	
        	v1.setVisibility(View.INVISIBLE);
        	v2.setVisibility(View.INVISIBLE);
        	
        	dealerCards.add(v1);
        	playerCards.add(v2);
        }
		
		game = new Blackjack(userName);
		
		for(int i = 0; i < 2; i++){
			dealerCards.get(i).setVisibility(View.VISIBLE);
			            	
			Card card = game.player.card(i);
			ImageView cardView = playerCards.get(i);
			cardView.setVisibility(View.VISIBLE);
			cardView.setImageResource(getCareDrawableByString(card.suit(), card.face()));
		}
		
		nameText.setText(userName + ", " + game.player.point() + "Point.");
		return gfrag;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==hit){//hit button
			int i = game.player.cardCount();
        	if(i >= 10)
        		return;
        	ImageView cardView = playerCards.get(i);
        	game.hit();
        	Card card = game.player.card(i);
        	cardView.setVisibility(View.VISIBLE);
        	cardView.setImageResource(getCareDrawableByString(card.suit(), card.face()));
        	//cardView.startAnimation(animFadein);//°Êµe
        	nameText.setText(userName + ", " + game.player.point() + "Point.");
        	if(game.player.point()>=21){ //brust
        		v=stay;
        		this.onClick(v);
        	}
		}else if(v==stay){//stay button
			for(int i = 0; i < 2; i++){
            	Card card = game.dealer.card(i);
            	ImageView cardView = dealerCards.get(i);
            	cardView.setVisibility(View.VISIBLE);
            	cardView.setImageResource(getCareDrawableByString(card.suit(), card.face()));
            }
        	
        	dealer.setText("Dealer, " + game.dealer.point() + "Point");
        	for(int i = 2; i < 10; i++){
        		if(game.dealer.point() < 17){
        			
    				ImageView cardView = dealerCards.get(i);
    				game.dealer.deal(game.dealer);
    				Card card = game.dealer.card(i);
    				cardView.setVisibility(View.VISIBLE);
    				//cardView.startAnimation(animFadein);
    				cardView.setImageResource(getCareDrawableByString(card.suit(), card.face()));
        		}
        		dealer.setText("Dealer, " + game.dealer.point() + "Point");
        	}
        	
			
        	if(game.compete()==-1){//lost the game
        		Toast.makeText(getActivity(), "Lost", Toast.LENGTH_SHORT).show();
        		judge.setImageResource(R.drawable.lost);
        		judge.setVisibility(View.VISIBLE);
        		again.setVisibility(View.VISIBLE);
        		hit.setVisibility(View.INVISIBLE);
        		stay.setVisibility(View.INVISIBLE);

        		userMoney-=bet;
        		
        	}else if(game.compete()==1){//win the game
        		Toast.makeText(getActivity(), "Win", Toast.LENGTH_SHORT).show();
        		judge.setImageResource(R.drawable.win);
        		judge.setVisibility(View.VISIBLE);
        		again.setVisibility(View.VISIBLE);
        		hit.setVisibility(View.INVISIBLE);
        		stay.setVisibility(View.INVISIBLE);
        		
        		userMoney+=bet;
        		
        	}else{//tie
        		Toast.makeText(getActivity(), "Tie", Toast.LENGTH_SHORT).show();
        		judge.setImageResource(R.drawable.tie);
        		judge.setVisibility(View.VISIBLE);
        		again.setVisibility(View.VISIBLE);
        		hit.setVisibility(View.INVISIBLE);
        		stay.setVisibility(View.INVISIBLE);
        	}//compete end
        	MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues(); 
			
			//cv.put("Name", userName);
        	cv.put("Money", userMoney);
        	db.update("User", cv, "ID=1", null);
        	db.close();
		}else if(v==again){//again button
			Betsfragment newFragment = new Betsfragment();
			newFragment.setName(userName);
			newFragment.setMoney(userMoney);
			newFragment.setID(ID);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment).commit();
		}
		
	}
	
}//GmaeFragment  end