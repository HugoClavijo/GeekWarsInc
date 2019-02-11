package com.hugo.geekwars;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Main2Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private static final int SENSOR_SENSITIVITY = 2;

    Dialog myDialog;

    private String mUser, mUserId;

    private GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Google User Data
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    setUserData(user);
                } else {
                    goLogInScreen();
                }
            }
        };
        //---------------------------------------------------------------

        Button mButton = (Button) findViewById(R.id.b5);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this, AboutActivity.class));
                //Main2Activity.this.finish();
            }
        });

        Button mButton1 = (Button) findViewById(R.id.b1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(Main2Activity.this, GameActivity.class));
                Intent i = new Intent(Main2Activity.this, GameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", mUser);
                bundle.putString("userId", mUserId);
                i.putExtras(bundle);
                startActivity(i);
                //Main2Activity.this.finish();
            }
        });

        Button mButton2 = (Button) findViewById(R.id.b2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, RankingActivity.class));
                //Main2Activity.this.finish();
            }
        });

        Button mButton3 = (Button) findViewById(R.id.b4);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Main2Activity.this, "No Service", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, ProfileActivity.class));
                //Main2Activity.this.finish();
            }
        });


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        float maxRange = mSensor.getMaximumRange();


        myDialog = new Dialog(this);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean("IS_FIRST_TIME", true)) {
            ShowPopup(this);
            //show your dialog here
            //...
            //change the value of your sharedPreferences
            sharedPreferences.edit().putBoolean("IS_FIRST_TIME", false).apply();
        }


    }


    public void ShowPopup(Main2Activity v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.btEx);
        //txtclose.setText("M");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }


    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent event) {
//        if (mSensor.getMaximumRange() == event.values[0]) {
//            //iv.setImageResource(R.drawable.near);
//            Toast.makeText(Main2Activity.this, "Free Add", Toast.LENGTH_SHORT).show();
//        } else {
//            //iv.setImageResource(R.drawable.far);
//        }

        if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
            //iv.setImageResource(R.drawable.near);
            //Toast.makeText(Main2Activity.this, "Free Add", Toast.LENGTH_SHORT).show();
            ShowPopup(this);
        } else {
            //iv.setImageResource(R.drawable.far);
        }

    }


    //Google User Data-------------------------------------
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    private void setUserData(FirebaseUser user) {
        //nameTextView.setText(user.getDisplayName());
        //emailTextView.setText(user.getEmail());
        //idTextView.setText(user.getUid());
        //Glide.with(this).load(user.getPhotoUrl()).into(photoImageView);
        mUser = user.getDisplayName();
        mUserId = user.getUid();
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }
//----------------------------------------------------------------------------

}
