package com.example.blackjack2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Betsfragment extends Fragment implements OnClickListener {

	String userName;
	int ID,userMoney,bets = 0;
	TextView show1,show2,showbets;
	Button increase, minus, betOk, back;
	ImageButton showhand;
	
	public Betsfragment(){
		
	}
	
	public void setAll(String un, int um, int id){
		this.userName = un;
		this.userMoney = um;
		this.ID = id;
	}


	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View bfrag = inflater.inflate(R.layout.fragment_bets, container,
				false);
		
		showhand  = (ImageButton) bfrag.findViewById(R.id.imageButton1);
		showhand.setOnClickListener(this);
		
		show1 = (TextView) bfrag.findViewById(R.id.textView1);
		show2 = (TextView) bfrag.findViewById(R.id.textView2);
		showbets = (TextView) bfrag.findViewById(R.id.textView3);
		show1.setText("Hello " + userName + "!");
		show2.setText("Your Money is " + userMoney + " dollors!");
		showbets.setText("Bets : " + bets);
		
		increase = (Button) bfrag.findViewById(R.id.button1);
		minus = (Button) bfrag.findViewById(R.id.button2);
		betOk = (Button) bfrag.findViewById(R.id.button3);
		back = (Button) bfrag.findViewById(R.id.button4);
		
		increase.setOnClickListener(this);
		minus.setOnClickListener(this);
		betOk.setOnClickListener(this);
		back.setOnClickListener(this);
		
		return bfrag;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==showhand){
			bets = userMoney;
			showbets.setText("Bets : " + bets);
			//Toast.makeText(this.getActivity(), "showhand", Toast.LENGTH_SHORT).show();
		}else if(v==increase){
			if(bets < userMoney){
				bets+=10;
				showbets.setText("Bets : " + bets);
			}
			//Toast.makeText(this.getActivity(), "i", Toast.LENGTH_SHORT).show();
		}else if(v==minus){
			if(bets > 0){
				bets-=10;
				showbets.setText("Bets : " + bets);
			}
			//Toast.makeText(this.getActivity(), "m", Toast.LENGTH_SHORT).show();
		}else if(v==betOk){
			if(bets!=0){
				GameFragment newFragment = new GameFragment();
				newFragment.setAll(ID,userName,userMoney,bets);
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment).commit();
				Toast.makeText(this.getActivity(), "you bet $" + bets, Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this.getActivity(), "bets not enter!", Toast.LENGTH_SHORT).show();
			}
			
		}else{
			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
			getActivity().getSupportFragmentManager().popBackStack();
			transaction.remove(this);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			transaction.commit();
		}
	}

}//bfragment_main//end
