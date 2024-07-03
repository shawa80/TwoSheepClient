package com.siliconandsynapse.aclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.siliconandsynapse.aclient.game.TwoSheep.TwoSheepActivity;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final EditText password = (EditText)findViewById(R.id.passwordInput);
		final EditText user = (EditText)findViewById(R.id.userInput);
		
		Button loginBtn = (Button)findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener((e) -> {
				
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("user", user.getText().toString());
			intent.putExtra("pass", password.getText().toString());

			this.startActivity(intent);
        	
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
