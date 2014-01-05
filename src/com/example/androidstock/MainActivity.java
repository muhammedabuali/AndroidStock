package com.example.androidstock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	boolean flag = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView webview = new WebView(this);
		//webview.setVisibility(webview.GONE);		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		setContentView(webview);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true); 
		
		MyJavaScriptInterface jInterface = new MyJavaScriptInterface(this);
		webview.addJavascriptInterface(jInterface, "HtmlViewer");
		
		webview.setWebViewClient(new WebViewClient() {
			
			@Override
			synchronized public void onPageFinished(WebView view, String url) {
				Log.d("hello",url);
			   //Load HTML
				if(!flag){
					flag = true;	
					view.loadUrl("javascript:__doPostBack('ctl00$C$S$lkMarket','')");
					try {
						wait(15000);
						view.loadUrl("javascript:window.HtmlViewer.showHTML	(document.getElementsByTagName('html')[0].innerHTML);");
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

	MyJavaScriptInterface(Context ctx) {
	    this.ctx = ctx;
	}

	@JavascriptInterface
	public void showHTML(String _html) {
	    html = _html;
	    System.out.println(html);
	    Document doc = Jsoup.parse(html);
	    Log.d("hello","ok");
	    Elements elements = doc.getElementsByTag("tr");
	    for(int i=25; i< elements.size()-1; i++)
	    	Log.d("data",elements.get(i).child(6).text());
	    Log.d("hello","");
	    Log.d("hello","14");
	}
	
	public void get_data(Document d){
		Elements elements = d.getElementsByTag("span");
		for(int i=25; i< elements.size()-1; i++)
	    	Log.d("data",elements.get(i).child(6).text());
		for(int i=35; i< elements.size(); i++)
	    	Log.d("data",elements.get(i).text());
	    
	}
}