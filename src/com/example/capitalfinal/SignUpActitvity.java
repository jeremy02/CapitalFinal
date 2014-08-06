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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActitvity extends Activity {
	 
    // Progress Dialog
    private ProgressDialog pDialog;
 
    JSONParser jsonParser = new JSONParser();
    EditText etuser,etemail,etphone,etpass;
    //EditText inputPrice;
    //EditText inputDesc;
 
    // url to create new product
    private static String url_create_user = "http://10.0.2.2/red/android/capital_new_user.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_actitvity);
 
        // Edit Text
        etuser = (EditText) findViewById(R.id.regusername);
        etemail = (EditText) findViewById(R.id.regemail);
        etphone = (EditText) findViewById(R.id.regphone);
        etpass = (EditText) findViewById(R.id.regpassword);
 
        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.sign_up);
 
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
            	new CreateNewUser().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new product
     * */
    class CreateNewUser extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActitvity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            int success;
            
            String username = etuser.getText().toString();
            String email = etemail.getText().toString();
            String phone = etphone.getText().toString();
            String password = etpass.getText().toString();
            
 
            // check for success tag
            try {
                
             // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", username));
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("phone", phone));
                params.add(new BasicNameValuePair("password", password));
     
                // getting JSON Object
                // Note that create product url accepts POST method
                JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                        "POST", params);
     
                // check log cat fro response
                Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                
                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    //startActivity(i);
                    
                	Log.d("User Created!", json.toString());
                	finish();
                	return json.getString(TAG_MESSAGE);
                	// closing this screen
                    //finish();
                	
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
            	Toast.makeText(SignUpActitvity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
 
    }
}