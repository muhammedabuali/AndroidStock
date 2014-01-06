package com.example.androidstock;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity {
	private CompaniesDataSource datasource;
	private long start_id;
	public static long current_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		datasource = new CompaniesDataSource(this);
	    datasource.open();
	    setContentView(R.layout.activity_list);
	    final ListView listview = (ListView) findViewById(R.id.listview);
	    ArrayList<Company> companies = datasource.getAllCompanies();
	    start_id = datasource.getId();
	    Log.d("hello",companies.size()+"");
	    if(companies.size()>0){
	    final ArrayAdapter<Company> adapter = new ArrayAdapter<Company>(this,
	    		R.layout.aligned_right, companies);
	    
	    Log.d("data",adapter.toString());
	        listview.setAdapter(adapter);
	    }
	    listview.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int position,
	                long paramLong) {
	        	current_id = start_id + position;
	        	Intent intent = new Intent(ListActivity.this, StockActivity.class);     
	            startActivity(intent);
	        }
	    });
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.list:
	            change(1);
	            return true;
	        case R.id.update:
	            change(2);
	            return true;
	        case R.id.favourite:
	            change(3);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void change(int i) {
		if(i == 2){
			Intent intent = new Intent(ListActivity.this,MainActivity.class);
			startActivity(intent);
		}
		if(i == 3){
			Intent intent = new Intent(ListActivity.this,FavouriteActivity.class);
			startActivity(intent);
		}
	}


}
