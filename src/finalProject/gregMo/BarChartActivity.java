package finalProject.gregMo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import finalProject.gregKrilov.R;

public class BarChartActivity extends Activity {
	WebView webView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bar_chart);

		this.webView = (WebView) findViewById(R.id.webview);
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		webView.loadUrl("file:///android_asset/barchart.html");
	}

}
