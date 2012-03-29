package finalProject.gregMo;

import finalProject.gregKrilov.R;
import finalProject.gregMo.database.DailyIntakeTable;
import finalProject.gregMo.database.DateTable;
import finalProject.gregMo.database.FoodTable;
import finalProject.gregMo.database.NutritionContentProvider;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Today extends Activity implements OnClickListener {

	ListView food;
	Cursor cursor;
	String foodDate;
	int dailyID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.daily_food);
	
		Bundle dateExtra = getIntent().getExtras();
		
		//If not null it means you came from the daily page by clicking on a date
		//If you are coming from the list of date page then you need to pass date string
		//of the date clicked on in the form of yyyyMMdd (that way it is stored in DB)
		if ( dateExtra != null)
		{
			foodDate = dateExtra.getString("date");
			//Get ID for the date selected
			cursor = managedQuery(NutritionContentProvider.CONTENT_URI_DATE, new String[] {DateTable.COLUMN_ID}, 
					DateTable.COLUMN_DATE + " = '" + foodDate + "'", null, null);
		}
		//Otherwise you came here from the today button on the main menu
		//The last date in the dateTable is todays date therefore use that
		else
		{
			//Get a cursor of dates
	    	cursor = managedQuery(NutritionContentProvider.CONTENT_URI_DATE, null, null, null, null);
	    	//Put the cursor at todays date
	    	cursor.moveToLast();
		}
		
		//ID for the date
		int id = cursor.getInt(cursor.getColumnIndex(DateTable.COLUMN_ID)); 
		
		// ***************************************************
		// * Get ID for the DailyTable for the date selected *
		// ***************************************************
		cursor = managedQuery(NutritionContentProvider.CONTENT_URI_DAILY, new String[] {DailyIntakeTable.COLUMN_ID}, 
				DailyIntakeTable.COLUMN_DATE_ID + " = '" + id + "'", null, null);
		
		//Each daily has a date and each date always has a daily associated with its primary key
		if ( cursor.moveToFirst() )
			dailyID = cursor.getInt(cursor.getColumnIndex(DailyIntakeTable.COLUMN_ID));
			
		
		food = (ListView) findViewById(R.id.foodList);
		
		//Get a cursor for all food items which have a FK ID of the current daily ID
		cursor = managedQuery(NutritionContentProvider.CONTENT_URI_FOOD, null, 
				FoodTable.COLUMN_DAILY_ID + " = '" + dailyID + "'", null, null);
		
		//If there is something in it then present it
		if (cursor.isFirst())
		{
			ListAdapter foodAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String [] {FoodTable.COLUMN_NAME}, new int [] {android.R.id.text1}, 2  );
			food.setAdapter(foodAdapter);
		}
		
		//Otherwise create a blank adapter
		else
		{
			food.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new String[] {"Add a food using the above button"}));
		}
			
		ImageButton addFood = (ImageButton) findViewById(R.id.addNew);
		ImageButton dateGraph = (ImageButton) findViewById(R.id.graph);
		
		addFood.setOnClickListener(this);
		dateGraph.setOnClickListener(this);
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId())
		{
		case R.id.addNew: 
			intent.setClass(this, AddFood.class);
			intent.putExtra("dailyID", dailyID);
			startActivity(intent);
			break;
		//Should not have to pass anything in here and should be able to simply
		//show a graph on the current daily table row (one where todays date is FK)
		case R.id.graph:
			//intent.setClass(this, graph_activity_here.java);
			startActivity(intent);
			break;
		}
		
	}
	
}
