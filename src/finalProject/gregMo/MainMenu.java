package finalProject.gregMo;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import finalProject.gregKrilov.R;
import finalProject.gregMo.database.DailyIntakeTable;
import finalProject.gregMo.database.DateTable;
import finalProject.gregMo.database.NutritionContentProvider;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainMenu extends Activity implements OnClickListener {

	TextView textToday;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		setDateAndDaily();
		
		ImageButton introduction = (ImageButton) findViewById(R.id.introduction);
		ImageButton initialInfo = (ImageButton) findViewById(R.id.initialInfo);
		ImageButton daily = (ImageButton) findViewById(R.id.daily);
		ImageButton graph = (ImageButton) findViewById(R.id.graph);
		ImageButton today = (ImageButton) findViewById(R.id.today);
		
		introduction.setOnClickListener(this);
		initialInfo.setOnClickListener(this);
		daily.setOnClickListener(this);
		graph.setOnClickListener(this);
		today.setOnClickListener(this);
		
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
    	case R.id.graph:
    		intent.setClass(this, BarChartActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.daily:
    		intent.setClass(this, DailyInformation.class);
    		startActivity(intent);
    		break;
    	case R.id.today:
    		intent.setClass(this, Today.class);
    		startActivity(intent);
    		break;
//    	case R.id.button3:
//    		stopService(intent);
//    		break; */
      	}
    }

    public void setDateAndDaily()
    {
    	int lastDate;
    	textToday = (TextView) findViewById(R.id.todayDate);
    	GregorianCalendar today = new GregorianCalendar();
    	
		SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat date_today = new SimpleDateFormat("EEEE, dd MMMM yyyy");
		date_format.setTimeZone(TimeZone.getTimeZone("EST"));
		date_today.setTimeZone(TimeZone.getTimeZone("EST"));
		
		textToday.setText(date_today.format(today.getTime()));
		ContentValues values = new ContentValues();
		Uri uri;
		
    	Cursor cursor = managedQuery(NutritionContentProvider.CONTENT_URI_DATE, null, null, null, null);
    	if ( cursor.moveToLast() )
    	{
    		lastDate = Integer.parseInt( cursor.getString(cursor
					.getColumnIndex(DateTable.COLUMN_DATE)));
    		int todayDate = Integer.parseInt(date_format.format(today.getTime()));
    		if ( todayDate > lastDate)
    		{
        		values.put(DateTable.COLUMN_DATE, Integer.toString(todayDate));
        		uri = getContentResolver().insert(NutritionContentProvider.CONTENT_URI_DATE, values);
        		values.clear();
        		values = getDaily(uri.getLastPathSegment());
        		getContentResolver().insert(NutritionContentProvider.CONTENT_URI_DAILY, values);
    		}
    	}
    	else
    	{
    		values.put(DateTable.COLUMN_DATE, date_format.format(today.getTime()));
    		uri = getContentResolver().insert(NutritionContentProvider.CONTENT_URI_DATE, values);
    		values.clear();
    		values = getDaily(uri.getLastPathSegment());
    		getContentResolver().insert(NutritionContentProvider.CONTENT_URI_DAILY, values);
    	}
    }
    
    public ContentValues getDaily(String id)
    {
    	 ContentValues values = new ContentValues();
	     values.put(DailyIntakeTable.COLUMN_TODAY_CALORIES,  "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_FATG,  "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_CARBSG,  "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_PROTEING, "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_FIBERG,  "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_SFATG,  "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_CHOLESTEROL, "0");
	     values.put(DailyIntakeTable.COLUMN_TODAY_SODIUM,  "0");
	     values.put(DailyIntakeTable.COLUMN_DATE_ID, id);
	     return values;
    }
	
}
