package com.hugo.geekwars;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button mButton = (Button) findViewById(R.id.btExit);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(AboutActivity.this, Main2Activity.class));
//                AboutActivity.this.finish();
                Intent i = new Intent(AboutActivity.this, Main2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                AboutActivity.this.finish();
            }
        });

        Button fbutton = findViewById(R.id.btnFacebook);
        fbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToFacebookPage("geekdomEC");
            }
        });

        Button ibutton = findViewById(R.id.btnInstagram);
        ibutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                goToInstagramPage("geekdomec");
            }
        });

    }


    public void goToFacebookPage(String id){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + id));
            startActivity(intent);

        } catch (ActivityNotFoundException e){

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + id));
            startActivity(intent);

        }
    }


    public void goToInstagramPage(String id){
        try {
            // mediaLink is something like "https://instagram.com/p/6GgFE9JKzm/" or
            // "https://instagram.com/_u/sembozdemir"
            Uri uri = Uri.parse("https://www.instagram.com/" + id);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            intent.setPackage("com.instagram.android");
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //Log.e(TAG, e.getMessage());
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/" + id));
            startActivity(intent);
        }
    }

}
