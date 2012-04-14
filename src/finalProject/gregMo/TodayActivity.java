package finalProject.gregMo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import finalProject.gregKrilov.R;
import finalProject.gregMo.database.DateTable;
import finalProject.gregMo.database.FoodTable;
import finalProject.gregMo.database.NutritionContentProvider;

public class TodayActivity extends Activity implements OnClickListener {

	ListView food;
	Cursor cursor;
	String foodDate;
	int dailyID;
	int [] ids;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
			cursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_DATE, new String[] {DateTable.COLUMN_ID}, 
					DateTable.COLUMN_DATE + " = '" + foodDate + "'", null, null);
			
			if (cursor != null) {
				cursor.moveToFirst();
			}
		}
		//Otherwise you came here from the today button on the main menu
		//The last date in the dateTable is todays date therefore use that
		else
		{
			//Get a cursor of dates
	    	cursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_DATE, null, null, null, null);
	    	
	    	if (cursor != null) {
	    		//Put the cursor at todays date
	    		cursor.moveToLast();
	    		foodDate = cursor.getString(cursor.getColumnIndex(DateTable.COLUMN_DATE));
	    	}
		}
		
		//ID for the date
		dailyID = cursor.getInt(cursor.getColumnIndex(DateTable.COLUMN_ID)); 
		
		food = (ListView) findViewById(R.id.foodList);
		
		fillData();
	}

	private void fillData() {
		//Get a cursor for all food items which have a FK ID of the current daily ID
		cursor = getContentResolver().query(NutritionContentProvider.CONTENT_URI_FOOD, null, 
				FoodTable.COLUMN_DAILY_ID + " = '" + dailyID + "'", null, null);
		
		//If there is something in it then present it
		if ( cursor != null )
		{
			if ( cursor.moveToFirst() )
			{
				ids = new int[cursor.getCount()];
				int i = 0;
				ListAdapter foodAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String [] {FoodTable.COLUMN_NAME}, new int [] {android.R.id.text1}, 2  );
				food.setAdapter(foodAdapter);
				
				//Put the ids of todays food items in order
				while ( !cursor.isAfterLast() )
				{
					ids[i++] = cursor.getInt(cursor.getColumnIndex(FoodTable.COLUMN_ID));
					cursor.moveToNext();
				}
				
				food.setOnItemClickListener( new OnItemClickListener() {
	
					public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
						Intent intent = new Intent();
						intent.setClass(TodayActivity.this, AddFoodActivity.class);
						intent.putExtra("id", ids[position]);
						intent.putExtra("dailyID", dailyID);
						intent.putExtra("date", foodDate);
						intent.putExtra("update", true);
						startActivity(intent);
				}});
				
				registerForContextMenu(food);
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
	}

	@Override
	protected void onStop() {
		super.onStop();
		cursor.close();
	}
	
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId())
		{
		case R.id.addNew: 
			intent.setClass(this, AddFoodActivity.class);
			intent.putExtra("dailyID", dailyID);
			intent.putExtra("date", foodDate);
			intent.putExtra("update", false);
			startActivity(intent);
			break;
		case R.id.graph:
    		intent = new MeasurementsBarChart().execute(this, foodDate, foodDate);
    		startActivity(intent);
			break;
		}
		
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Are you sure you want to delete this item?");
		menu.add(0, v.getId(), 0, "Yes");
		menu.add(0, v.getId(), 1, "No");
	}
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	switch(item.getOrder()) {
    	case 0:
    		Log.d("D", foodDate);
    		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
    		Uri uri = Uri.parse(NutritionContentProvider.CONTENT_URI_FOOD + "/" + info.id);
    		getContentResolver().delete(uri, null, null);

    		fillData();
			
    		return true;
    	}
    	return super.onContextItemSelected(item);
    }
	
}
