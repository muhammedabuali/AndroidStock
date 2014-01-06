package com.example.androidstock;

public class Company {
	  private long id;
	  private String name;
	  private String change;
	  private int price;
	  private int amount;
	  private boolean is_favorite;
	  
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChange() {
		return change;
	}
	public void setChange(String change) {
		this.change = change;
	}
	public void setCompany(String string, String string2, int i, int j, int k) {
		name = string;
		change = string2;
		setPrice(i);
		setAmount(j);
		setFavorite(k);
	}
	
	public void setFavorite(int k){
		if(k == 0){
			this.is_favorite = false;
		}
		else{
			this.is_favorite = true;
		}
	}
	
	@Override
	public String toString(){
		return name +" "+ change;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}