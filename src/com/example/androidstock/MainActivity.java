package com.example.androidstock;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
	boolean flag = false;
	ArrayAdapter<String> arrayAdapter;
	MyJavaScriptInterface jInterface;
	Context con;
	private CompaniesDataSource datasource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		datasource = new CompaniesDataSource(this);
	    datasource.open();
		WebView webview = new WebView(this);
		//webview.setVisibility(webview.GONE);		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true); 
		
		jInterface = new MyJavaScriptInterface(this,datasource);
		webview.addJavascriptInterface(jInterface, "HtmlViewer");
		
		webview.setWebViewClient(new WebViewClient() {
			
			@Override
			synchronized public void onPageFinished(WebView view, String url) {
				Log.d("hello",url);
			   //Load HTML
				if(!flag){
					flag = true;
					setContentView(view);
					view.loadUrl("javascript:__doPostBack('ctl00$C$S$lkMarket','')");
					//setContentView(view2);
					view.setVisibility(view.GONE);
					try {
						wait(30000);
						view.loadUrl("javascript:window.HtmlViewer.showHTML	(document.getElementsByTagName('html')[0].innerHTML);");
						/*do{
							wait(500);
						}while(!jInterface.status);*/
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					Log.d("hello","here you go");
					//view.loadUrl("javascript:window.HtmlViewer.showHTML	(document.getElementsByTagName('html')[0].innerHTML);");
				}
			   
			}
			
		});
		webview.loadUrl("http://www.egx.com.eg/Arabic/prices.aspx");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

class MyJavaScriptInterface {
	private Context ctx;
	public String html;
	public boolean status;
	private CompaniesDataSource datasource;
	
	MyJavaScriptInterface(Context ctx, CompaniesDataSource datasource) {
	    this.ctx = ctx;
	    this.datasource = datasource;
	    this.status = false;
	}

	@JavascriptInterface
	public void showHTML(String _html) {
	    html = _html;
	    System.out.println(html);
	    Document doc = Jsoup.parse(html);
	    Log.d("hello","ok");
	    Elements elements = doc.getElementsByTag("span");
	    ArrayList<String> names = new ArrayList<String>();
	    for(int i=35; i< elements.size(); i++)
	    	names.add(elements.get(i).text());
	    elements = doc.getElementsByTag("tr");
	    ArrayList<String> changes = new ArrayList<String>();
	    for(int i=25; i< elements.size(); i++){
	    	if(elements.get(i).children().size()>7)
	    	changes.add(elements.get(i).child(6).text());
	    }
	    ArrayList<String> prices = new ArrayList<String>();
	    for(int i=25; i< elements.size(); i++){
	    	if(elements.get(i).children().size()>7)
	    	prices.add(elements.get(i).child(10).text());
	    }
	    ArrayList<String> amounts = new ArrayList<String>();
	    for(int i=25; i< elements.size(); i++){
	    	if(elements.get(i).children().size()>7)
	    	amounts.add(elements.get(i).child(11).text());
	    }
	    if(changes.size()>names.size()){
	    	int x = changes.size()- names.size();
	    	for(int i=0; i<x;i++){
	    		changes.remove(changes.size()-1);
	    		amounts.remove(changes.size()-1);
	    		prices.remove(changes.size()-1);
	    	}
	    }else if (changes.size()<names.size()){
	    	int x = elements.size()- changes.size();
	    	for(int i=0; i<x;i++){
	    		names.remove(elements.size()-1);
	    	}
	    }
	    Log.d("data","elements ="+names.size());
	    Log.d("data","changes ="+changes.size());
	    Log.d("data","prices ="+prices.size());
	    Log.d("data","amounts ="+amounts.size());
	    //delete elements from database and insert new ones
	    datasource.deleteAllCompanies();
	    for(int i =0; i< names.size(); i++){
	    	datasource.createCompany(names.get(i), changes.get(i),
	    			prices.get(i), amounts.get(i));
	    }
	    Intent myIntent = new Intent(ctx, ListActivity.class);
	    ctx.startActivity(myIntent); 
	    Log.d("hello","14");
	}
	
	public void get_data(Document d){
		Elements elements = d.getElementsByTag("span");
		for(int i=25; i< elements.size()-1; i++){
			if(elements.get(i).children().size()>7)
				Log.d("data",elements.get(i).child(6).text());	
		}
		for(int i=35; i< elements.size(); i++)
			Log.d("data",elements.get(i).text());
	}
}