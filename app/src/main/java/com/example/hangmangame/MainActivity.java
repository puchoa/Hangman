package com.example.hangmangame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.application.example.EXTRA_TEXT";
    private Button buttonEasy, buttonMedium, buttonHard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEasy = (Button)findViewById(R.id.buttonE);
        buttonMedium= (Button)findViewById(R.id.buttonM);
        buttonHard= (Button)findViewById(R.id.buttonH);


        buttonEasy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View test){
                openGame("easy.txt");
            }
        });
        buttonEasy.setBackgroundResource(android.R.drawable.btn_default);


        buttonMedium.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View test){
                openGame("medium.txt");
            }
        });
        buttonMedium.setBackgroundResource(android.R.drawable.btn_default);


        buttonHard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View test){
                openGame("hard.txt");
            }
        });
        buttonHard.setBackgroundResource(android.R.drawable.btn_default);
    }

    public void openGame(String dif){
        Intent intent = new Intent( this, Game.class);
        intent.putExtra(EXTRA_TEXT, dif);
        startActivity(intent);
    }

}
