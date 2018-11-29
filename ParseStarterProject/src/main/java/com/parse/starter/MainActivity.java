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
import android.widget.EditText;
import android.widget.Switch;
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


public class MainActivity extends AppCompatActivity {

  // Respond to Sign up OnClick
  public void signUpClicked(View view){

    // Access edit text field variables
    EditText usernameEditText = findViewById(R.id.usernameEditText);
    EditText passwordEditText = findViewById(R.id.passwordEditText);

    // Check if user has entered text into both fields otherwise prompt the user
    if (usernameEditText.getText().toString() == "" || passwordEditText.getText().toString() == ""){
      // Prompt user for text
      Toast.makeText(this, "A username and a password are required.", Toast.LENGTH_SHORT).show();
    } else {

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
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



    // SIGN UP

    /*ParseUser user = new ParseUser();

    user.setUsername("brendan");
    user.setPassword("myPass");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          Log.i("Sign up ok", "We did it!");
        }else {
          e.printStackTrace();
        }
      }
    });*/

    // LOGIN

      /*ParseUser.logInInBackground("brendan", "myPass", new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
              if (user != null){
                  Log.i("Success", "We logged in");
              } else {
                  e.printStackTrace();
              }
          }
      });*/

      // LOG OUT USER
    /*ParseUser.logOut();

      // CHECK FOR LOGGED IN USER
      // IF equal to null no-one logged in
      if(ParseUser.getCurrentUser() != null){
        Log.i("Signed In", ParseUser.getCurrentUser().getUsername());
      } else {
          Log.i("Awww Snap", "Not signed in");
      } */

    /*// Specific query
    query.whereEqualTo("score","susan");
    query.setLimit(1);

    // Call all objects
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e == null){
          if(objects.size() >0){
            for(ParseObject object : objects){
              Log.i("username", object.getString("username"));
              Log.i( "score",Integer.toString(object.getInt("score")));
            }
          }
        }
      }
    });*/

    // ** Sending to Parse **
    /*ParseObject score = new ParseObject("Score");
    // Add properties to score object
    score.put("username", "susan");
    score.put("score", 65);

    // Send object to parse
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          // Ok
          Log.i("Success", "We saved the score");
        } else {
          e.printStackTrace();
        }
      }
    });

    // ** Getting from Parse
    /*ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");

    // Parse callback
    query.getInBackground("5gu3FGiOta", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if (e == null && object != null){
          // getString/Int/

          object.put("score", 85);
          object.saveInBackground();

          Log.i( "username",object.getString("username"));
          Log.i( "score",Integer.toString(object.getInt("score")));
        }
      }
    });*/

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

}