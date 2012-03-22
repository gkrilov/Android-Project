package finalProject.gregMo;

import finalProject.gregKrilov.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;

public class TitlePage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title);	
		
		GridLayout v = (GridLayout) findViewById(R.id.gridLayout1);
		final Intent intent = new Intent(this, MainMenu.class);
		v.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
					startActivity(intent);
			}
		});
		
	}

	
	
}

