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
      MySQLiteHelper.COLUMN_CHANGE};

  public CompaniesDataSource(Context context) {
    dbHelper = new MySQLiteHelper(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Company createCompany(String name, String change) {
    ContentValues values = new ContentValues();
    values.put(MySQLiteHelper.COLUMN_COMPANY, name);
    values.put(MySQLiteHelper.COLUMN_CHANGE, change);
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

  public List<Company> getAllComments() {
    List<Company> companies = new ArrayList<Company>();

    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPANIES,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Company comment = cursorToCompany(cursor);
      companies.add(comment);
      cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return companies;
  }

  public void deleteAllCompanies(){
	database.delete(MySQLiteHelper.TABLE_COMPANIES, null, null);  
  }
  
  private Company cursorToCompany(Cursor cursor) {
    Company company = new Company();
    company.setId(cursor.getLong(0));
    company.setCompany(cursor.getString(1),cursor.getString(2));
    return company;
  }
}