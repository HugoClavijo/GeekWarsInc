package com.hugo.geekwars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;



public class RankingActivity extends AppCompatActivity {

    DatabaseReference mRootReference;
    Button mButtonExit;
    TextView mTop1, mTop2, mTop3;

    ProgressDialog pd;

    private static String TAG = "RankingActivity";

    static boolean isInitialized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        try{
            if(!isInitialized){
                FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                isInitialized = true;
            }else {
                Log.d(TAG,"Already Initialized");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        mRootReference = FirebaseDatabase.getInstance().getReference();
        mRootReference.keepSynced(true);


        mButtonExit = (Button)findViewById(R.id.quit);
        mTop1 = (TextView)findViewById(R.id.top1);
        mTop2 = (TextView)findViewById(R.id.top2);
        mTop3 = (TextView)findViewById(R.id.top3);

        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(RankingActivity.this, Main2Activity.class));
//                RankingActivity.this.finish();

                Intent i = new Intent(RankingActivity.this, Main2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                RankingActivity.this.finish();
            }
        });

        pd = new ProgressDialog(RankingActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        solicitarDatosFirebase();

    }

    private void solicitarDatosFirebase() {
        mRootReference.child("scores").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                int primerValor = 0;
                int segundoValor = 0;
                int tercerValor = 0;
                int quitaIndice1 = 0;
                int quitaIndice2 = 0;

                String selectedUser1 = "", selectedId1 = "";
                String selectedUser2 = "", selectedId2 = "";
                String selectedUser3 = "", selectedId3 = "";

               List<Integer> scores = new ArrayList<Integer>();
               List<String> userNames = new ArrayList<String>();
               List<String> userIds = new ArrayList<String>();


                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                            RankingLib score = snapshot.getValue(RankingLib.class);
                            String nombre = score.getUsername();
                            String id = score.getUserId();
                            int userScore = score.getScore();
                            scores.add(userScore);
                            userNames.add(nombre);
                            userIds.add(id);

//                            Log.e("NombreUsuario:",""+nombre);
                           // Log.e("scores:",""+scores.get(i).toString());
//                            Log.e("Datos:",""+scores.get(1).toString());
//                            Log.e("IdUsuario:",""+id);
//                            Log.e("ScoreUsuario:",""+userScore);
                            //Log.e("Datos:",""+snapshot.getValue());
                            i++;
                        }


                /*Buscamos dentro del arreglo el número mayor y su respectivo índice para no volverlo a tomar en cuenta */
                int	mayor = 0;
                int c = 0;
                while(c < scores.size()) {
                    if( scores.get(c) > mayor ) {
                        primerValor = scores.get(c);
                        selectedUser1 = userNames.get(c);
                        selectedId1 = userIds.get(c);
                        mayor = primerValor;
                        quitaIndice1 = c;
                    }
                    c++;
                }


                /* Buscamos el segundo valor omitiendo el índice donde encontramos el primer valor */
                c=0;
                mayor=0;
                while(c < scores.size()) {
                    if ( c != quitaIndice1 ) {
                        if( scores.get(c) > mayor ) {
                            segundoValor = scores.get(c);
                            selectedUser2 = userNames.get(c);
                            selectedId2 = userIds.get(c);
                            mayor = segundoValor;
                            quitaIndice2 = c;
                        }
                    }
                    c++;
                }



                /* Buscamos el tercer valor omitiendo el primer y segundo índice encontrado */
                c=0;
                mayor=0;
                while(c < scores.size()) {
                    if( c != quitaIndice1 && c != quitaIndice2 ) {
                        if( scores.get(c) > mayor ) {
                            tercerValor = scores.get(c);
                            selectedUser3 = userNames.get(c);
                            selectedId3 = userIds.get(c);
                            mayor = tercerValor;
                        }
                    }
                    c++;
                }

                //Log.e("scores:",""+String.valueOf(primerValor)+selectedUser1);

                if (pd.isShowing()){
                    pd.dismiss();
                }

                if(primerValor != 0){
                    mTop1.setText("1. "+selectedUser1+" "+String.valueOf(primerValor));
                    mTop1.setTextColor(Color.CYAN);
                }

                if(segundoValor != 0){
                    mTop2.setText("2. "+selectedUser2+" "+String.valueOf(segundoValor));
                }

                if(tercerValor != 0){
                    mTop3.setText("3. "+selectedUser3+" "+String.valueOf(tercerValor));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            });

    }



}
