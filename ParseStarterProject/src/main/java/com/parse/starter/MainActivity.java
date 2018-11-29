/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Sign up or login
    Boolean signUpModeActive = true;
    TextView loginTextView;

  @Override
  public void onClick(View v) {
    if(v.getId() == R.id.loginTextView){

        Button signUpButton = findViewById(R.id.signUpButton);

        if(signUpModeActive){
            signUpModeActive = false;
            signUpButton.setText("Login");
            loginTextView.setText("or, Sign Up");
        } else {
            signUpModeActive = true;
            signUpButton.setText("Sign Up");
            loginTextView.setText("or, Login");
        }
    }
  }

  // Respond to Sign up OnClick
  public void signUpClicked(View view){

    // Access edit text field variables
    EditText usernameEditText = findViewById(R.id.usernameEditText);
    EditText passwordEditText = findViewById(R.id.passwordEditText);

    // Check if user has entered text into both fields otherwise prompt the user
    if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")){
      // Prompt user for text
      Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
    } else {
        // Sign up mode
        if(signUpModeActive){
          // User has entered a user name and password
          ParseUser user = new ParseUser();
          user.setUsername(usernameEditText.getText().toString());
          user.setPassword(passwordEditText.getText().toString());

          user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
              if(e == null){
                //Ok
                Log.i("Signup", "Success");
              } else {
                // Log short error message to user
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    } else {
            // Login mode
            ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null){
                        Log.i("Login", "Ok!");
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    loginTextView = findViewById(R.id.loginTextView);
    loginTextView.setOnClickListener(this);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}