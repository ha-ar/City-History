package com.algorepublic.cityhistory.cityhistory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by waqas on 7/25/15.
 */
public class LoginActivity extends Activity{

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
      login = (Button) findViewById(R.id.Login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LoginActivity.this, BaseActivity.class);
                startActivity(myIntent);
                finish();

            }
        });
    }


}
