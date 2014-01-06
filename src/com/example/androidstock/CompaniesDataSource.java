package com.example.androidstock;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CompaniesDataSource {
  
  // Database fields
  private SQLiteDatabase database;
  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
      MySQLiteHelper.COLUMN_COMPANY,
      MySQLiteHelper.COLUMN_CHANGE,
      MySQLiteHelper.COLUMN_PRICE,
      MySQLiteHelper.COLUMN_AMOUNT,
      MySQLiteHelper.IS_FAVORITE};
private long start_id;

  public CompaniesDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Company createCompany(String name, String change, String string, String string2) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_COMPANY, name);
    values.put(MySQLiteHelper.COLUMN_CHANGE, change);
    values.put(MySQLiteHelper.COLUMN_PRICE, string);
    values.put(MySQLiteHelper.COLUMN_AMOUNT, string2);
    values.put(MySQLiteHelper.IS_FAVORITE, "0");
    long insertId = database.insert(MySQLiteHelper.TABLE_COMPANIES, null,
        values);
    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPANIES,
        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Company newCompany = cursorToCompany(cursor);
    cursor.close();
    return newCompany;
  }

public void deleteCompany(Company company) {
    long id = company.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(MySQLiteHelper.TABLE_COMPANIES, MySQLiteHelper.COLUMN_ID
        + " = " + id, null);
  }

  public ArrayList<Company> getAllCompanies() {
    ArrayList<Company> companies = new ArrayList<Company>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPANIES,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    start_id = cursor.getLong(0);
    while (!cursor.isAfterLast()) {
      Company company = cursorToCompany(cursor);
      companies.add(company);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return companies;
  }
  
  public Company getCompany(int id){
	  Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPANIES,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = "+ id, null, null, null, null);
	  return cursorToCompany(cursor);
  }
  
  public void setCompanyIsFavorite(long id , boolean is_favorite){
	  int x;
	  if(is_favorite){
		  x = 1;
	  }
	  else{
		  x= 0;
	  }
	  String sql = "UPDATE "+ MySQLiteHelper.TABLE_COMPANIES +" SET " + 
			  MySQLiteHelper.IS_FAVORITE + " = '"+x
			  +"' WHERE "+ MySQLiteHelper.COLUMN_ID + " = "+id;
	  database.execSQL(sql);
  }

  public void deleteAllCompanies(){
	database.delete(MySQLiteHelper.TABLE_COMPANIES, null, null);  
  }
  
  private Company cursorToCompany(Cursor cursor) {
    Company company = new Company();
    company.setId(cursor.getLong(0));
    company.setCompany(cursor.getString(1),cursor.getString(2),
    		cursor.getInt(3), cursor.getInt(4), cursor.getInt(5));
    return company;
  }
	public long getId() {
		
		return start_id;
	}
}