package com.hugo.geekwars;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView mGrade, mFinalScore;
    Button mRetryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mGrade = (TextView)findViewById(R.id.grade);
        mFinalScore = (TextView)findViewById(R.id.outOf);
        mRetryButton = (Button)findViewById(R.id.retry);

        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("finalScore");
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

        mRetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultActivity.this, Main2Activity.class));
                ResultActivity.this.finish();
            }
        });

    }
}
