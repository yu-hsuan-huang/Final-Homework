package com.example.blackjack2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements OnClickListener{

		Button inputNameOkButton,mapButton,signupButton;
		EditText accountEdtext,passWord;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			//declare elements 
			inputNameOkButton = (Button) rootView.findViewById(R.id.button1);
			mapButton = (Button) rootView.findViewById(R.id.button2);
			signupButton = (Button) rootView.findViewById(R.id.button3);
			accountEdtext = (EditText) rootView.findViewById(R.id.editText1);
			passWord = (EditText) rootView.findViewById(R.id.editText2);
			
			inputNameOkButton.setOnClickListener(this);
			mapButton.setOnClickListener(this);
			signupButton.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String idString = null;
			
			if(v==inputNameOkButton){
				MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				//parent.changeFragment(2);
				
				Cursor cursor = 
					       db.query("User", // a. table
					       new String[] {"ID", "Name", "Account", "Password", "Money"}, // b. column names
					       "Account=?", // c. selections 
					       new String[] {accountEdtext.getText().toString()}, // d. selections args
					       null, // e. group by
					       null, // f. having
					       "ID desc", // g. order by
					       null); // h. limit
					   
					if (cursor != null && cursor.getCount() > 0) {//login ok
						cursor.moveToFirst();
						idString = cursor.getString(0);
						
						if(compareStr(cursor.getString(2),accountEdtext.getText().toString())&&compareStr(cursor.getString(3),passWord.getText().toString())){
							Toast.makeText(this.getActivity(), "Hello " + cursor.getString(1), Toast.LENGTH_SHORT).show();
							//TODO
							Betsfragment newFragment = new Betsfragment();
							newFragment.setAll(cursor.getString(1), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(0)));
							getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment).addToBackStack(null).commit();
							//
							accountEdtext.setText("");
							passWord.setText("");
						}else{
							Toast.makeText(this.getActivity(), "Error password", Toast.LENGTH_SHORT).show();
						}
						
						db.close();
						dbHelper.close();
						return;
					}
					//db.close();
					
					if(idString == null){//Insert to database
						/*SQLiteDatabase db1 = dbHelper.getWritableDatabase();			
						ContentValues values = new ContentValues();
						values.put("Name", nameEdtext.getText().toString()); 
						values.put("Password", passWord.getText().toString());
						values.put("Money", 200);
						db1.insert("User",  null, values); 
						db1.close();*/
						Toast.makeText(this.getActivity(), "not have this account", Toast.LENGTH_SHORT).show();
					}
					//dbHelper.close();
					
			}//end click inputNameOkButton
			if(v==mapButton){
				Mapfragment newFragment = new Mapfragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment).addToBackStack(null).commit();
			}//end click mapButton
			if(v==signupButton){
				Signupfragment newFragment = new Signupfragment();
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment).addToBackStack(null).commit();
			}
			
		}//onClick//end
		
		private boolean compareStr(String a,String b){
			return a.equals(b);
		}
		
	}//fragment_main//end

	
	
	
}//MainActivity class end
