package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserFeedActivity extends AppCompatActivity {

    LinearLayout linLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        setTitle(username + "'s Photos");

        // Add to layout
        linLayout = findViewById(R.id.linLayout);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Image");

        query.whereEqualTo("username", username);
        // Order by parse object "createdAt"
        query.orderByDescending("createdAt");

        // Find in background from parse
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                // Check that there is data with objects.size()
                if(e == null  && objects.size() > 0){
                    for(ParseObject object : objects){
                        ParseFile file = (ParseFile) object.get("image");

                        file.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                // Check that data is okay with != null
                                if(e == null && data != null){

                                    // Image from parse
                                    Bitmap bitmap   = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    ImageView imageView = new ImageView(getApplicationContext());

                                    imageView.setLayoutParams(new ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT
                                    ));

                                    // Set parse bitmap
                                    imageView.setImageBitmap(bitmap);

                                    linLayout.addView(imageView);
                                }
                            }
                        });
                    }
                }
            }
        });




    }
}