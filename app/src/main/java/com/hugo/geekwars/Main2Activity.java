package com.hugo.geekwars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button mButton = (Button) findViewById(R.id.b5);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, AboutActivity.class));
            }
        });

        Button mButton1 = (Button) findViewById(R.id.b1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, GameActivity.class));
            }
        });

        Button mButton2 = (Button) findViewById(R.id.b2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
            }
        });

        Button mButton3 = (Button) findViewById(R.id.b4);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, ProfileActivity.class));
            }
        });
    }
}
