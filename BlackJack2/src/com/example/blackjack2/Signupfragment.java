package com.example.blackjack2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Signupfragment extends Fragment implements OnClickListener{
	
	EditText account,password,name;
	Button ok,back;
	
	public Signupfragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View signfrag = inflater.inflate(R.layout.fragment_signup, container,
				false);
		
		account = (EditText) signfrag.findViewById(R.id.editText1);
		password = (EditText) signfrag.findViewById(R.id.editText2);
		name = (EditText) signfrag.findViewById(R.id.editText3);
		ok = (Button) signfrag.findViewById(R.id.button1);
		back = (Button) signfrag.findViewById(R.id.button2);
		ok.setOnClickListener(this);
		back.setOnClickListener(this);
		return signfrag;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v==ok){
			MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			//parent.changeFragment(2);
			
			Cursor cursor = 
				       db.query("User", // a. table
				       new String[] {"ID", "Name", "Account","Password", "Money"}, // b. column names
				       "ID=?", // c. selections 
				       new String[] {account.getText().toString()}, // d. selections args
				       null, // e. group by
				       null, // f. having
				       "ID desc", // g. order by
				       null); // h. limit
				   
			if (cursor != null && cursor.getCount() > 0){
				Toast.makeText(getActivity(), "This Account is have!", Toast.LENGTH_SHORT).show();
			}else{
				SQLiteDatabase db1 = dbHelper.getWritableDatabase();			
				ContentValues values = new ContentValues();
				values.put("Name", name.getText().toString());
				values.put("Account", account.getText().toString());
				values.put("Password", password.getText().toString());
				values.put("Money", 200);
				db1.insert("User",  null, values); 
				db1.close();
				Toast.makeText(this.getActivity(), "New Account Insert", Toast.LENGTH_SHORT).show();
				v=back;
				this.onClick(v);
			}
			db.close();
			dbHelper.close();
		}
		if(v==back){
			FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
			getActivity().getSupportFragmentManager().popBackStack();
			transaction.remove(this);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			transaction.commit();
		}
		
	}

	
}
