package finalProject.gregMo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import finalProject.gregKrilov.R;
import finalProject.gregMo.database.DateTable;
import finalProject.gregMo.database.FoodTable;
import finalProject.gregMo.database.NutritionContentProvider;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class AddFood extends Activity {

	int dailyID;
	int foodID;
	int [] ids;
	Button submitFood;
	ExpandableListView foodList;
	EditText name, calories, gramsFat, gramsSatFat, carbs, protein, cholesterol, sodium, fibre;
	TextView error, update;

	OnClickListener submitFoodListener = new OnClickListener() 
	{
		public void onClick(View v) {
				
				if ( name.getText().toString().isEmpty() || 
					 calories.getText().toString().isEmpty() ||
					 gramsFat.getText().toString().isEmpty() || 
					 gramsSatFat.getText().toString().isEmpty() || 
					 carbs.getText().toString().isEmpty() || 
					 protein.getText().toString().isEmpty() || 
					 cholesterol.getText().toString().isEmpty() || 
					 sodium.getText().toString().isEmpty() ||
					 fibre.getText().toString().isEmpty() )
				{
					error.setTextColor(Color.RED);
					error.setText("Please fill in all the fields");
				}
				else
				{
					ContentValues food = new ContentValues();
					food.put(FoodTable.COLUMN_NAME,  name.getText().toString().toUpperCase());
					food.put(FoodTable.COLUMN_FOOD_CALORIES,  calories.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_FATG,  gramsFat.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_SFATG,  gramsSatFat.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_CARBSG,  carbs.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_PROTEING,  protein.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_CHOLESTEROL,  cholesterol.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_SODIUM,  sodium.getText().toString());
					food.put(FoodTable.COLUMN_FOOD_FIBERG,  fibre.getText().toString());

					//Create an alert dialog
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddFood.this);
					if ( getIntent().getExtras().getBoolean("update") == false)
					{
							food.put(FoodTable.COLUMN_DAILY_ID,  dailyID);
							getContentResolver().insert(NutritionContentProvider.CONTENT_URI_FOOD, food);
							alertDialog.setTitle("Item Added");
							alertDialog.setMessage("The item has been successfully Added");
					}
					else
					{
						getContentResolver().update(Uri.parse(NutritionContentProvider.CONTENT_URI_FOOD + "/" + foodID), food, null, null);
						alertDialog.setTitle("Item updated");
						alertDialog.setMessage("The item has been successfully updated");
					}
							
					alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int arg1) {
			            	Intent intent = new Intent(AddFood.this, Today.class);
			            	intent.putExtra("date", getIntent().getExtras().getString("date"));
			            	startActivity(intent);
			       	}});
					
					alertDialog.setCancelable(false);
					alertDialog.show();
				}
		}
	};
	
	OnChildClickListener clickedFoodListener = new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			
			Cursor itemWithID = managedQuery(Uri.parse(NutritionContentProvider.CONTENT_URI_FOOD + "/" + ids[childPosition])
					, null, null, null, null);
			itemWithID.moveToFirst();
			name.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_NAME)));
			calories.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_CALORIES)));
			gramsFat.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_FATG)));
			gramsSatFat.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_SFATG)));
			sodium.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_SODIUM)));
			protein.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_PROTEING)));
			cholesterol.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_CHOLESTEROL)));
			fibre.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_FIBERG)));
			carbs.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_CARBSG)));
			
			return true;
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
		
		submitFood = (Button) findViewById(R.id.submitFood);
		name = (EditText) findViewById(R.id.name);
		calories = (EditText) findViewById(R.id.calories);
		gramsFat = (EditText) findViewById(R.id.gramsFat);
		gramsSatFat = (EditText) findViewById(R.id.gramsSatFat);
		carbs = (EditText) findViewById(R.id.carbsGram);
		protein = (EditText) findViewById(R.id.proteinGram);
		cholesterol = (EditText) findViewById(R.id.cholesterol);
		sodium = (EditText) findViewById(R.id.sodium);
		fibre = (EditText) findViewById(R.id.fibre);
		error = (TextView) findViewById(R.id.error);
		submitFood.setOnClickListener(submitFoodListener);
		
	}

	@Override
	protected void onStart() 
	{
		// TODO Auto-generated method stub
		super.onStart();
		Bundle extras = getIntent().getExtras();
		if ( extras != null )
		{
			//Do this if it is a new item
			if ( extras.getBoolean("update") == false)
			{
				update = (TextView) findViewById(R.id.updateText);
				update.setVisibility(View.INVISIBLE);
				dailyID = extras.getInt("dailyID");
				foodList = (ExpandableListView) findViewById(R.id.foodList);
				
				
		        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
		        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
		        Map<String, String> group = new HashMap<String, String>();
		        List<Map<String, String>> children = new ArrayList<Map<String, String>>();
		        
		        Cursor foodCursor = managedQuery(NutritionContentProvider.CONTENT_URI_FOOD, null, null, null, null);
		        if (foodCursor.moveToFirst())
		        {
		        	ids = new int[foodCursor.getCount()];
		        	int i = 0;
		            group.put("title", "Food List");
		            groupData.add(group);
		            
		            while ( !foodCursor.isAfterLast() )
		            {
		            	Map<String, String> childMap = new HashMap<String, String>();
		            	Map<String, String> test = new HashMap<String, String>();
		            	test.put("food", foodCursor.getString(foodCursor.getColumnIndex(FoodTable.COLUMN_NAME)));
		            	if ( !children.contains(test))
		            	{	
		            		childMap.put("food", foodCursor.getString(foodCursor.getColumnIndex(FoodTable.COLUMN_NAME)));
		            		ids[i++] = foodCursor.getInt(foodCursor.getColumnIndex(FoodTable.COLUMN_ID));
		            		children.add(childMap);
		            	}
		            	foodCursor.moveToNext();
		            }
		            childData.add(children);
		            
		            foodList.setOnChildClickListener(clickedFoodListener);
		        }
		        else
		        {
		            group.put("title", "No food available to choose from");
		            groupData.add(group);
		            Map<String, String> childMap = new HashMap<String, String>();
		            childMap.put("food", "Empty List");
		            children.add(childMap);
		            childData.add(children);
		        }
		        
			    foodList.setAdapter(new SimpleExpandableListAdapter(this, groupData, R.layout.food_list_row, new String[] {"title"}, new int [] {R.id.groupname}, 
						childData, R.layout.food_list_row_child, new String[] {"food"}, new int [] {R.id.childname}));
				
			}
			
			//Do this if an item was sent for an update
			else
			{
				foodID = extras.getInt("id");
				submitFood.setOnClickListener(submitFoodListener);
				//Set textfields to have things in them based on the passed in ID
				Cursor itemWithID = managedQuery(Uri.parse(NutritionContentProvider.CONTENT_URI_FOOD + "/" + foodID)
						, null, null, null, null);
				itemWithID.moveToFirst();
				//Now get the item and put its things in the textfields
				//itemWithID
				name.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_NAME)));
				calories.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_CALORIES)));
				gramsFat.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_FATG)));
				gramsSatFat.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_SFATG)));
				sodium.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_SODIUM)));
				protein.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_PROTEING)));
				cholesterol.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_CHOLESTEROL)));
				fibre.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_FIBERG)));
				carbs.setText(itemWithID.getString(itemWithID.getColumnIndex(FoodTable.COLUMN_FOOD_CARBSG)));
			}
		}
	}	
}
