package com.example.androidstock;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
		//webview.setVisibility(View.GONE);		
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		
		setContentView(webview);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBuiltInZoomControls(true); 
		
		MyJavaScriptInterface jInterface = new MyJavaScriptInterface(this);
		webview.addJavascriptInterface(jInterface, "HtmlViewer");
		
		webview.setWebViewClient(new WebViewClient() {
			
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d("hello",url);
			   //Load HTML
				view.loadUrl("javascript:window.HtmlViewer.showHTML	(document.getElementsByTagName('html')[0].innerHTML);");
				if(!flag){
					view.loadUrl("javascript:__doPostBack('ctl00$C$S$lkMarket','')");
					view.loadUrl("javascript:window.HtmlViewer.showHTML	(document.getElementsByTagName('html')[0].innerHTML);");
				}
			   flag = true;
			   
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
	}
}