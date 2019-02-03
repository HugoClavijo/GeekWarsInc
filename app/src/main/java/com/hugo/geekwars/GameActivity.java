package com.hugo.geekwars;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GameActivity extends AppCompatActivity {


    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonExit;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mScoreView = (TextView)findViewById(R.id.score);
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonExit = (Button)findViewById(R.id.quit);

        if (checkConnectivity()){
            //do something
            new JsonTask().execute("https://geekwars-d935a.firebaseio.com/quiz/.json");
        }else{
            startActivity(new Intent(GameActivity.this, Main2Activity.class));
            GameActivity.this.finish();
        }

    }

    private class JsonTask extends AsyncTask<String, String, String> {

        private QuestionLibrary mQuestionLibrary;

        private String mAnswer;
        private int mScore = 0;
        private int mQuestionNumber = 0;

        private String questionsRef [];

        private String choicesRef [][];

        private String answersRef [];

        private String mUser, mUserId;

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(GameActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();

            Bundle bundle = getIntent().getExtras();
            mUser = bundle.getString("userName");
            mUserId = bundle.getString("userId");
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();





            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }


            try
            {

                JSONArray jsonArray = new JSONArray(result);
                int length = jsonArray.length();

                questionsRef = new String[length];
                answersRef= new String[length];
                choicesRef = new String[length][3];

                for (int i = 0; i < length; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    questionsRef[i] = jsonObject.getString("question");
                    choicesRef[i][0] = jsonObject.getString("choise1");
                    choicesRef[i][1] = jsonObject.getString("choise2");
                    choicesRef[i][2] = jsonObject.getString("choise3");
                    answersRef[i] = jsonObject.getString("answer");
                }

            }
            catch (Exception e)
            {
                // this is just an example
            }


            mAnswer = answersRef[mQuestionNumber];
            mQuestionLibrary = new QuestionLibrary(questionsRef, choicesRef, answersRef);
            updateQuestion();


            //Start of Button Listener for Button1
            mButtonChoice1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //My logic for Button goes in here

                    if (mButtonChoice1.getText().equals(mAnswer) ){
                        mScore = mScore + 1;
                        updateScore(mScore);
                        updateQuestion();
                        //This line of code is optiona
                        Toast.makeText(GameActivity.this, "correcto", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(GameActivity.this, "incorrecto", Toast.LENGTH_SHORT).show();
                        //updateQuestion();
                        validateQuestions();
                    }
                }
            });

            //End of Button Listener for Button1

            //Start of Button Listener for Button2
            mButtonChoice2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //My logic for Button goes in here

                    if (mButtonChoice2.getText().equals(mAnswer)){
                        mScore = mScore + 1;
                        updateScore(mScore);
                        updateQuestion();
                        //This line of code is optiona
                        Toast.makeText(GameActivity.this, "correcto", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(GameActivity.this, "incorrecto", Toast.LENGTH_SHORT).show();
                        //updateQuestion();
                        validateQuestions();
                    }
                }
            });

            //End of Button Listener for Button2


            //Start of Button Listener for Button3
            mButtonChoice3.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    //My logic for Button goes in here

                    if (mButtonChoice3.getText().equals(mAnswer)){
                        mScore = mScore + 1;
                        updateScore(mScore);
                        validateQuestions();
                        //updateQuestion();
                        //This line of code is optiona
                        Toast.makeText(GameActivity.this, "correcto", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(GameActivity.this, "incorrecto", Toast.LENGTH_SHORT).show();
                        //updateQuestion();
                        validateQuestions();
                    }
                }
            });

            //End of Button Listener for Button3


            mButtonExit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(GameActivity.this, Main2Activity.class));
                    GameActivity.this.finish();
                }
            });


        }


        private void updateQuestion(){
            mQuestionView.setText(questionsRef[mQuestionNumber]);
            mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));

            //mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mAnswer = answersRef[mQuestionNumber];
            mQuestionNumber++;
        }


        private void updateScore(int point) {
            mScoreView.setText("" + mScore);
        }



        private void validateQuestions() {
            //perform check before you update the question
            if (mQuestionNumber == QuestionLibrary.numQuestions) {
                Intent i = new Intent(GameActivity.this, ResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userName", mUser);
                bundle.putString("userId", mUserId);
                bundle.putInt("finalScore", mScore);
                i.putExtras(bundle);
                GameActivity.this.finish();
                startActivity(i);
                //startActivity(new Intent(GameActivity.this, ResultActivity.class));
            } else {
                updateQuestion();
            }
        }


    }

    private boolean checkConnectivity() {
        boolean enabled = true;

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {
            Toast.makeText(getApplicationContext(), "Sin conexión a Internet...", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

        //return false;
    }

}
