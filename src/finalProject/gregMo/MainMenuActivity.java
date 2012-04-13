package finalProject.gregMo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import finalProject.gregKrilov.R;
import finalProject.gregMo.database.DateTable;
import finalProject.gregMo.database.NutritionContentProvider;

public class MainMenuActivity extends Activity implements OnClickListener {

	TextView textToday;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		
		registerForContextMenu(graph);
	}

    public void onClick(View v)
    {
    	Intent intent = new Intent();
    	switch (v.getId())
    	{
    	case R.id.initialInfo:
    		intent.setClass(this, InitialInformationActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.graph:
    		openContextMenu(findViewById(R.id.graph));
    		break;
    	case R.id.daily:
    		intent.setClass(this, DailyInformationActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.today:
    		intent.setClass(this, TodayActivity.class);
    		startActivity(intent);
    		break;
    	case R.id.introduction:
    		intent.setClass(this, AppInformationActivity.class);
    		startActivity(intent);
    		break;
      	}
    }

    public void setDateAndDaily()
    {
    	int lastDate;
    	textToday = (TextView) findViewById(R.id.todayDate);
    	GregorianCalendar today = new GregorianCalendar();
    	
    	// TODO: Make this a utility method somewhere
    	// TODO: Take out underscores from var names to be consistent with rest of code
		SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat date_today = new SimpleDateFormat("EEEE, dd MMMM yyyy");
		date_format.setTimeZone(TimeZone.getTimeZone("EST"));
		date_today.setTimeZone(TimeZone.getTimeZone("EST"));
		
		textToday.setText(date_today.format(today.getTime()));
		ContentValues values = new ContentValues();
		
    	Cursor cursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_DATE, null, null, null, null);
    	//If there is a date in the table
    	if ( cursor != null )
	    {
	    	if (cursor.moveToLast())
	    	{
	    		lastDate = Integer.parseInt( cursor.getString(cursor
						.getColumnIndex(DateTable.COLUMN_DATE)));
	    		int todayDate = Integer.parseInt(date_format.format(today.getTime()));
	    		//Check if todays date is later than the last one in the table
	    		if ( todayDate > lastDate)
	    		{
	        		values.put(DateTable.COLUMN_DATE, Integer.toString(todayDate));
	        		getContentResolver().insert(NutritionContentProvider.CONTENT_URI_DATE, values);
	    		}
	    		cursor.close();
	    	}
	    	//If there is nothing in the table then create a new date (today)
	    	else
	    	{
	    		values.put(DateTable.COLUMN_DATE, date_format.format(today.getTime()));
	    		getContentResolver().insert(NutritionContentProvider.CONTENT_URI_DATE, values);
	    	}
	    }
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("How far back?");
		menu.add(0, v.getId(), 0, "Just today");
		menu.add(0, v.getId(), 1, "Past week");
		menu.add(0, v.getId(), 2, "Past month");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getOrder() == 0) {
    		Intent intent = new MeasurementsBarChart().execute(this, null, null);
    		startActivity(intent);
		} else if (item.getOrder() == 1) {
			SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
			date_format.setTimeZone(TimeZone.getTimeZone("EST"));
			
			GregorianCalendar calendar = new GregorianCalendar();
			String to = date_format.format(calendar.getTime());
			calendar.add(Calendar.DATE, -7);
			String from = date_format.format(calendar.getTime());
			
    		Intent intent = new MeasurementsBarChart().execute(this, from, to);
    		startActivity(intent);
		} else if (item.getOrder() == 2) {
			SimpleDateFormat date_format = new SimpleDateFormat("yyyyMMdd");
			date_format.setTimeZone(TimeZone.getTimeZone("EST"));
			
			GregorianCalendar calendar = new GregorianCalendar();
			String to = date_format.format(calendar.getTime());
			calendar.add(Calendar.DATE, -30);
			String from = date_format.format(calendar.getTime());
			
    		Intent intent = new MeasurementsBarChart().execute(this, from, to);
    		startActivity(intent);
		}
		else {
			return false;
		}
		return true;
	}

}
