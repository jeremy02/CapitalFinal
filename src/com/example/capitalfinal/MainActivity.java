package com.example.capitalfinal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button btnSignIn;
	Button btnSignUp,btnnews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnSignIn = (Button) findViewById(R.id.btnSingIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnnews = (Button) findViewById(R.id.buttonnews);
        
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnnews.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		Intent i = null;
		switch(v.getId()){
			case R.id.btnSingIn:
				i = new Intent(this,LoginActivity.class);
				break;
			case R.id.btnSignUp:
				i = new Intent(this,SignUpActitvity.class);
				break;
			case R.id.buttonnews:
				i = new Intent(this,NewsActivity.class);
				break;
		}
		startActivity(i);
	}


    
}
