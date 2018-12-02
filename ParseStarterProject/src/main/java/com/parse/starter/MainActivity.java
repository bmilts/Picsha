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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener  {

    // Sign up or login
    Boolean signUpModeActive = true;
    TextView loginTextView;

    EditText usernameEditText;
    EditText passwordEditText;

    // Acess activity_user_list.xml
    public void showUserList(){
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            signUpClicked(v);
        }

        return false;
    }

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
        // USER FRIENDLY KEYBOARD EDITION
        // User clicks outside password or user id field (Logo or Layout)
    } else if (v.getId() == R.id.logoImageView || v.getId() == R.id.backgroundLayout){
        // Cast method return
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
  }

  // Respond to Sign up OnClick
  public void signUpClicked(View view){

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
                showUserList();
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
                        showUserList();
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

      // Access edit text field variables
      usernameEditText = findViewById(R.id.usernameEditText);
      passwordEditText = findViewById(R.id.passwordEditText);

      passwordEditText.setOnKeyListener(this);
      ImageView logoImageView = findViewById(R.id.logoImageView);
      RelativeLayout backgroundLayout =findViewById(R.id.backgroundLayout);

      // Allow clicking whitespace and logo to move keyboard
      logoImageView.setOnClickListener(this);
      backgroundLayout.setOnClickListener(this);

      // If user logged in show user list
      if(ParseUser.getCurrentUser() != null){
          showUserList();
      }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}