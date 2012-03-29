package finalProject.gregMo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import finalProject.gregKrilov.R;
import finalProject.gregMo.database.FoodTable;
import finalProject.gregMo.database.NutritionContentProvider;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

public class AddFood extends Activity {

	int dailyID;
	ExpandableListView foodList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_food);
		
	
		
		Bundle extras = getIntent().getExtras();
		if ( extras != null )
		{
			dailyID = extras.getInt("dailyID");
		}
		foodList = (ExpandableListView) findViewById(R.id.foodList);
		
		
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childData = new ArrayList<List<Map<String, String>>>();
        Map<String, String> group = new HashMap<String, String>();
        List<Map<String, String>> children = new ArrayList<Map<String, String>>();
        
        Cursor foodCursor = managedQuery(NutritionContentProvider.CONTENT_URI_FOOD, null, null, null, null);
        if (foodCursor.moveToFirst())
        {
            group.put("title", "Food List");
            groupData.add(group);
            
            while ( !foodCursor.isAfterLast() )
            {
            	Map<String, String> childMap = new HashMap<String, String>();
            	childMap.put("food", foodCursor.getString(
            			foodCursor.getColumnIndex(FoodTable.COLUMN_NAME)));
            	children.add(childMap);
            	foodCursor.moveToNext();
            }
            childData.add(children);
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

}
