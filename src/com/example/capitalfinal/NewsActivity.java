package com.example.capitalfinal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.capitalfinal.LoginActivity.LoginUser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NewsActivity extends Activity {

	 // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    
    //declare the variables
    // Declare Variables
    // user JSONArray
    JSONArray news = null;
    JSONObject newsobject,json;
    ListView listview;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    ArrayList<HashMap<String, String>> arraylist;
    
    String id,title,desc,image,date;
 
    // url to create new product
    private static String url_news= "http://10.0.2.2/red/android/capital_news_list.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";   
    
    
    private static final String TAG_NEWS = "news";    
    /*private static final String TAG_ID = "news_id";
    private static final String TAG_IMAGE = "news_image";
    private static final String TAG_DESC = "news_description";*/
    
    static String ID = "news_id";
    static String TITLE = "news_title";
    static String DESC = "news_description";
    static String IMAGE = "news_image_url";
    static String DATE = "created_at";
       

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from listview_main.xml		
		setContentView(R.layout.listview_main); 
        new NewsFromJson().execute();
           
    }
 
    /**
     * Background Async Task to Create new product
     * */
    class NewsFromJson extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsActivity.this);
            pDialog.setMessage("Loading News...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
        	// check for success tag
            int success;             
            
         // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            try {
                
             // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
     
                // getting JSON Object
                // Note that create product url accepts POST method
                json = jsonParser.makeHttpRequest(url_news,
                        "GET", params);
     
                // check log cat for response
                //Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                
                if (success == 1) {
                	// successfully got news
                	// Getting Array of User Details
                    news = json.getJSONArray(TAG_NEWS);
                    for (int i = 0; i < news.length(); i++) {
                    	HashMap<String, String> map = new HashMap<String, String>();
                    	newsobject = news.getJSONObject(i);
                    	// Storing each json item in variable
                		id = newsobject.getString("news_id");
                		title = newsobject.getString("news_title");
                		desc = newsobject.getString("news_description");
                		image = newsobject.getString("news_image_url");
                		date = newsobject.getString("created_at");
                		Log.d("id",id);
                		Log.i("title",title);
                		Log.i("desc",desc);
                		Log.i("image",image);
                		Log.i("date",date);
                		/*Log.d("title", newsobject.getString("news_title"));
                		Log.d("desc", newsobject.getString("news_description"));
                		Log.d("image", newsobject.getString("news_image_url"));
                		Log.d("date", newsobject.getString("created_at"));*/
                		
                		// Retrive JSON Objects
                        map.put("news_id", id);
                        map.put("news_title", title);
                        map.put("news_description", desc);
                        map.put("news_image_url", image);
                        // Set the JSON Objects into the array
                        arraylist.add(map);
                		
                    }
                	return json.getString(TAG_MESSAGE);
                	
                } else {
                    // failed to create product
                	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
            	Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if(file_url != null){
            	Toast.makeText(NewsActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
         // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(NewsActivity.this, arraylist);
            // Set the adapter to the ListView
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //listview.notifyDataSetChanged();
            listview.setOnItemClickListener(new OnItemClickListener() {

            	public void onItemClick(AdapterView<?> parent, View view, int position,long id){
            		
            		// Get the position
    				//String resultp = arraylist.get(position);
    				/*Intent intent = new Intent(context, SingleItemView.class);
    				// Pass all data rank
    				intent.putExtra("rank", resultp.get(MainActivity.RANK));
    				// Pass all data country
    				intent.putExtra("country", resultp.get(MainActivity.COUNTRY));
    				// Pass all data population
    				intent.putExtra("population",resultp.get(MainActivity.POPULATION));
    				// Pass all data flag
    				intent.putExtra("flag", resultp.get(MainActivity.FLAG));
    				// Start SingleItemView Class
    				context.startActivity(intent);*/
                    //String val=(String)(mListView.getItemAtPosition(position));
            		
            		
            		Toast.makeText(NewsActivity.this, "You Clicked at "+arraylist.get(+position).get("news_title"), Toast.LENGTH_SHORT).show();

                }
            });
        }
 
    }
}