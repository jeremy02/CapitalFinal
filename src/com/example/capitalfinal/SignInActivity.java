package com.example.capitalfinal;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.text.LoginFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends Activity {
	 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText loginuser,loginpass;
    //EditText inputPrice;
    //EditText inputDesc;
 
    // url to create new product
    private static String url_login_user = "http://10.0.2.2/red/android/capital_login.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
 
        // Edit Text
        loginuser = (EditText) findViewById(R.id.loginusername);
        loginpass = (EditText) findViewById(R.id.loginpassword);
 
        // Create button
        Button login = (Button) findViewById(R.id.loginbtn);
 
        // button click event
        login.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
            	new LoginUser().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new product
     * */
    class LoginUser extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignInActivity.this);
            pDialog.setMessage("Logging in to Capital FM...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            int success;
            
            String loginusername = loginuser.getText().toString();
            String loginpassword = loginpass.getText().toString();
            
 
            // check for success tag
            try {
                
             // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", loginusername));
                params.add(new BasicNameValuePair("password", loginpassword));
     
                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(url_login_user,
                        "POST", params);
     
                // check log cat for response
                Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                
                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    //startActivity(i);
                    
                	Log.d("Login Sucessful!", json.toString());
                	// closing this screen
                	finish();
                	return json.getString(TAG_MESSAGE);
                	
                } else {
                    // failed to create product
                	Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                	return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
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
            	Toast.makeText(SignInActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
 
    }
}