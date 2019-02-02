package com.hugo.geekwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    DatabaseReference mRootReference;
    TextView mGrade, mFinalScore;
    Button mRetryButton;
    private String mUser, mUserId;
    int whenIsCreated;
    int score = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mRootReference = FirebaseDatabase.getInstance().getReference();

        mGrade = (TextView)findViewById(R.id.grade);
        mFinalScore = (TextView)findViewById(R.id.outOf);
        mRetryButton = (Button)findViewById(R.id.retry);

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("finalScore");
        mUser = bundle.getString("userName");
        mUserId = bundle.getString("userId");
        int minScore = QuestionLibrary.numQuestions - 1;
        int regularScore = QuestionLibrary.numQuestions - 2;


        mFinalScore.setText("Acertaste " + score + " de " + QuestionLibrary.numQuestions);

        if (score == QuestionLibrary.numQuestions){
            mGrade.setText("Excepcional");
        }else if (score == minScore){
            mGrade.setText("Buen Trabajo");
        }else if (score == regularScore) {
            mGrade.setText("Buen Esfuerzo");
        }else {
            mGrade.setText("Esfuérzate más");
        }

        solicitarDatosFirebase();

        //Toast.makeText(ResultActivity.this, userIds.get(0), Toast.LENGTH_SHORT).show();

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(ResultActivity.this, Main2Activity.class));
//                ResultActivity.this.finish();
//                String name = mUser;
//                String id = mUserId;
//                int mScore = score;

                if(whenIsCreated == 0){
                    cargarDatosFirebase(mUser, mUserId, score);
                }

                Intent i = new Intent(ResultActivity.this, RankingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("userScore", score);
                i.putExtras(bundle);
                ResultActivity.this.finish();
                startActivity(i);
            }
        });

    }


    private void solicitarDatosFirebase() {
        mRootReference.child("scores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    mRootReference.child("scores").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            RankingLib score = snapshot.getValue(RankingLib.class);
                            String nombre = score.getUsername();
                            String id = score.getUserId();
                            int userScore = score.getScore();

                            if(id.equals(mUserId) || id.equals("") || id.equals(null)){
                                whenIsCreated = 1;
                            }else{
                                whenIsCreated = 0;
                            }

                            Log.e("NombreUsuario:",""+nombre);
                            Log.e("IdUsuario:",""+id);
                            Log.e("ScoreUsuario:",""+userScore);
                            Log.e("Datos:",""+snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void cargarDatosFirebase(String name, String UserId, int userScore) {

        Map<String, Object> datosScore = new HashMap<>();
        datosScore.put("userName", name);
        datosScore.put("userId", UserId);
        datosScore.put("score", userScore);

        mRootReference.child("scores").push().setValue(datosScore);
    }


}
