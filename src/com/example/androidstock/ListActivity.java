package com.example.androidstock;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity {
	private CompaniesDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datasource = new CompaniesDataSource(this);
	    datasource.open();
	    setContentView(R.layout.activity_list);
	    final ListView listview = (ListView) findViewById(R.id.listview);
	    ArrayList<Company> companies = datasource.getAllCompanies();
	    Log.d("hello",companies.size()+"");
	    if(companies.size()>0){
	    final ArrayAdapter<Company> adapter = new ArrayAdapter<Company>(this,
	            android.R.layout.simple_list_item_1, companies);
	    Log.d("data",adapter.toString());
	        listview.setAdapter(adapter);
	    }
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

}
