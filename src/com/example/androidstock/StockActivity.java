package com.example.androidstock;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StockActivity extends Activity {
	
	private int stock_id;
	private CompaniesDataSource datasource;
	private Company company;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stock);
		datasource = new CompaniesDataSource(this);
		datasource.open();
		stock_id  = ListActivity.current_id;
		company = datasource.getCompany(stock_id);
		button = (Button)findViewById(R.id.favorite_button);
		if(company.getFavorite()){
			button.setText("اضف الي المفضلة");
		}
		else{
			button.setText("احذف من المفضلة");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stock, menu);
		return true;
	}
	
	public void isFavorite(View v){
		if(company.getFavorite()){
			datasource.setCompanyIsFavorite(company.getId(), false);
			button.setText("اضف الي المفضلة");
		}
		else{
			datasource.setCompanyIsFavorite(company.getId(), true);
			button.setText("احذف من المفضلة");
		}
	}

}
