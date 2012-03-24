package finalProject.gregMo;

import finalProject.gregKrilov.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainMenu extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		ImageButton introduction = (ImageButton) findViewById(R.id.introduction);
		ImageButton initialInfo = (ImageButton) findViewById(R.id.initialInfo);
		ImageButton daily = (ImageButton) findViewById(R.id.daily);
		ImageButton graph = (ImageButton) findViewById(R.id.graph);
		
		introduction.setOnClickListener(this);
		initialInfo.setOnClickListener(this);
		daily.setOnClickListener(this);
		graph.setOnClickListener(this);
	}

    public void onClick(View v)
    {
    	Intent intent = new Intent();
    	switch (v.getId())
    	{
    	case R.id.initialInfo:
    		intent.setClass(this, InitialInformation.class);
    		startActivity(intent);
    		break;
    	/*case R.id.button2:
    		intent.putExtra("button", 2);
    		startService(intent);
    		break;
    	case R.id.button3:
    		stopService(intent);
    		break; */
    	}
    }
	
}
