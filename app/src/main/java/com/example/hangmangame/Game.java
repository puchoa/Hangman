package com.example.hangmangame;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    TextView tv1, tv2,tv3,tv5,tvp ,tvc;
    Button[] b = new Button[26];
    Button bRest;
    Button bHelp;
    int isWinner = 0;
    int[] num = new int[3];

    private static final long START_TIME_IN_MILLIS = 30000;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;



    String[] letters = {"Q","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M"};
    String dif="";
    String g= "GUESS THE WORD";
    String a= "";
    String text ="";
    int counter = 0;
    int points =0;
    int ranNum =0;
    final Random r = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        dif = intent.getStringExtra(MainActivity.EXTRA_TEXT);

        importWords(dif);

        tv1 = (TextView)findViewById(R.id.textView1);
        tv1.setText(g);

        setRandom();

        tv2 = (TextView)findViewById(R.id.textView2);
        tv2.setText(a);

        tv3 = (TextView)findViewById(R.id.textView3);

        tv5 = (TextView)findViewById(R.id.textView5);
        tv5.setText(" ");
        tvp = (TextView)findViewById(R.id.textViewPoints);
        tvp.setText(Integer.toString(points));

        tvc = (TextView)findViewById(R.id.textViewCount);

        b[0] =(Button)findViewById(R.id.button1);
        b[1] =(Button)findViewById(R.id.button2);
        b[2] =(Button)findViewById(R.id.button3);
        b[3] =(Button)findViewById(R.id.button4);
        b[4] =(Button)findViewById(R.id.button5);
        b[5] =(Button)findViewById(R.id.button6);
        b[6] =(Button)findViewById(R.id.button7);
        b[7] =(Button)findViewById(R.id.button8);
        b[8] =(Button)findViewById(R.id.button9);
        b[9] =(Button)findViewById(R.id.button10);
        b[10] =(Button)findViewById(R.id.button11);
        b[11] =(Button)findViewById(R.id.button12);
        b[12] =(Button)findViewById(R.id.button13);
        b[13] =(Button)findViewById(R.id.button14);
        b[14] =(Button)findViewById(R.id.button15);
        b[15] =(Button)findViewById(R.id.button16);
        b[16] =(Button)findViewById(R.id.button17);
        b[17] =(Button)findViewById(R.id.button18);
        b[18] =(Button)findViewById(R.id.button19);
        b[19] =(Button)findViewById(R.id.button20);
        b[20] =(Button)findViewById(R.id.button21);
        b[21] =(Button)findViewById(R.id.button22);
        b[22] =(Button)findViewById(R.id.button23);
        b[23] =(Button)findViewById(R.id.button24);
        b[24] =(Button)findViewById(R.id.button25);
        b[25] =(Button)findViewById(R.id.button26);
        bRest =(Button)findViewById(R.id.buttonRest);
        bRest.setBackgroundResource(android.R.drawable.btn_default);

        bHelp=(Button)findViewById(R.id.buttonHelp);
        bHelp.setBackgroundResource(android.R.drawable.btn_default);



        if(dif.charAt(0) =='e' || dif.charAt(0) =='m' ){

            tvc.setVisibility(View.INVISIBLE);
        }

        if(dif.charAt(0) =='m' || dif.charAt(0) =='h' ){

            bHelp.setVisibility(View.INVISIBLE);
        }

        for(int i =0; i <26;++i) {
            b[i].setBackgroundResource(android.R.drawable.btn_default);
            b[i].setText(letters[i]);
            b[i].setTextColor(Color.BLACK);
        }
        rest();
        setHash();

        if(dif.charAt(0) == 'h'){
            startTimer();
        }




        if(dif.charAt(0) =='e'){
            helpSetup();
            easyKeyboard();
        }
    }



    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;



        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvc.setText(timeLeftFormatted);

        if(seconds > 20) {
            tvc.setTextColor(Color.GREEN);
            tvc.setTextSize(30);
        }
        else if(seconds > 10) {
            tvc.setTextColor(Color.rgb(255, 153, 51));
            tvc.setTextSize(35);
        }
        else {
            tvc.setTextColor(Color.RED);
            tvc.setTypeface(null, Typeface.BOLD);


            if(tvc.getTextSize() == 122.5)
                tvc.setTextSize(40);
            else if(tvc.getTextSize() == 140)
                tvc.setTextSize(45);
            else
                tvc.setTextSize(40);

        }

        if(seconds == 0 && a.contains("#")){
            counterCheck(5);
        }
    }




    private void setRandom() {
        String fileName ="";
        int num1 =0;
        int num2=0;

        if(dif.charAt(0) =='m') {
            fileName = "medium.txt";
            num1 =8937;
            num2 =6;
        }
        else if(dif.charAt(0) =='h') {
            fileName = "hard.txt";
            num1 =24011;
            num2 =8;

        }
        else{
            fileName = "easy.txt";
            num1 =1064;
            num2 =4;
        }

        ranNum = r.nextInt(num1) * num2;
        getWord(fileName,ranNum);
    }





    // Sets up the button call - checks if the letter inputed is in the string
    public void onButton1Clicked(android.view.View test) {
        if(g.indexOf('q') == -1) {
            setEmoji(b[0], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('q');
            setEmoji(b[0], true,false);
        }
    }




    public void onButton2Clicked(android.view.View test) {
        if(g.indexOf('w') == -1) {
            setEmoji(b[1], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('w');
            setEmoji(b[1], true,false);
        }
    }




    public void onButton3Clicked(android.view.View test) {
        if(g.indexOf('e') == -1) {
            setEmoji(b[2], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('e');
            setEmoji(b[2], true,false);
        }
    }




    public void onButton4Clicked(android.view.View test) {
        if(g.indexOf('r') == -1) {
            setEmoji(b[3], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('r');
            setEmoji(b[3], true,false);
        }
    }




    public void onButton5Clicked(android.view.View test) {
        if(g.indexOf('t') == -1) {
            setEmoji(b[4], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('t');
            setEmoji(b[4], true,false);
        }
    }




    public void onButton6Clicked(android.view.View test) {
        if(g.indexOf('y') == -1) {
            setEmoji(b[5], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('y');
            setEmoji(b[5], true,false);
        }
    }




    public void onButton7Clicked(android.view.View test) {
        if(g.indexOf('u') == -1) {
            setEmoji(b[6], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('u');
            setEmoji(b[6], true,false);
        }
    }




    public void onButton8Clicked(android.view.View test) {
        if(g.indexOf('i') == -1) {
            setEmoji(b[7], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('i');
            setEmoji(b[7], true,false);
        }
    }




    public void onButton9Clicked(android.view.View test) {
        if(g.indexOf('o') == -1) {
            setEmoji(b[8], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('o');
            setEmoji(b[8], true,false);
        }
    }




    public void onButton10Clicked(android.view.View test) {
        if(g.indexOf('p') == -1) {
            setEmoji(b[9], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('p');
            setEmoji(b[9], true,false);
        }
    }




    public void onButton11Clicked(android.view.View test) {
        if(g.indexOf('a') == -1) {
            setEmoji(b[10], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('a');
            setEmoji(b[10], true,false);
        }
    }




    public void onButton12Clicked(android.view.View test) {
        if(g.indexOf('s') == -1) {
            setEmoji(b[11], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('s');
            setEmoji(b[11], true,false);
        }
    }




    public void onButton13Clicked(android.view.View test) {
        if(g.indexOf('d') == -1) {
            setEmoji(b[12], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('d');
            setEmoji(b[12], true,false);
        }
    }




    public void onButton14Clicked(android.view.View test) {
        if(g.indexOf('f') == -1) {
            setEmoji(b[13], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('f');
            setEmoji(b[13], true,false);
        }
    }




    public void onButton15Clicked(android.view.View test) {
        if(g.indexOf('g') == -1) {
            setEmoji(b[14], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('g');
            setEmoji(b[14], true,false);
        }
    }




    public void onButton16Clicked(android.view.View test) {
        if(g.indexOf('h') == -1) {
            setEmoji(b[15], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('h');
            setEmoji(b[15], true,false);
        }
    }




    public void onButton17Clicked(android.view.View test) {
        if(g.indexOf('j') == -1) {
            setEmoji(b[16], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('j');
            setEmoji(b[16], true,false);
        }
    }




    public void onButton18Clicked(android.view.View test) {
        if(g.indexOf('k') == -1) {
            setEmoji(b[17], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('k');
            setEmoji(b[17], true,false);
        }
    }




    public void onButton19Clicked(android.view.View test) {
        if(g.indexOf('l') == -1) {
            setEmoji(b[18], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('l');
            setEmoji(b[18], true,false);
        }
    }




    public void onButton20Clicked(android.view.View test) {
        if(g.indexOf('z') == -1) {
            setEmoji(b[19], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('z');
            setEmoji(b[19], true,false);
        }
    }




    public void onButton21Clicked(android.view.View test) {
        if(g.indexOf('x') == -1) {
            setEmoji(b[20], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('x');
            setEmoji(b[20], true,false);
        }
    }




    public void onButton22Clicked(android.view.View test) {
        if(g.indexOf('c') == -1) {
            setEmoji(b[21], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('c');
            setEmoji(b[21], true,false);
        }
    }




    public void onButton23Clicked(android.view.View test) {
        if(g.indexOf('v') == -1) {
            setEmoji(b[22], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('v');
            setEmoji(b[22], true,false);
        }
    }




    public void onButton24Clicked(android.view.View test) {
        if(g.indexOf('b') == -1) {
            setEmoji(b[23], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('b');
            setEmoji(b[23], true,false);
        }
    }




    public void onButton25Clicked(android.view.View test) {
        if(g.indexOf('n') == -1) {
            setEmoji(b[24], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('n');
            setEmoji(b[24], true,false);
        }
    }




    public void onButton26Clicked(android.view.View test) {
        if(g.indexOf('m') == -1) {
            setEmoji(b[25], false,false);
            ++counter;
            counterCheck(counter);
        }
        else{
            correct('m');
            setEmoji(b[25], true,false);
        }
    }







// Reset the game

    public void onButtonRestClicked(android.view.View test){
        counter =0;


        if(isWinner != 25)
            points=0;


        tv5.setText("");
        tvp.setText(Integer.toString(points));
        setHash();
        rest();
        setRandom();

        if(dif.charAt(0) =='e'){
            helpSetup();
            easyKeyboard();

        }



        tv1.setText("GUESS THE WORD");

        bHelp.setEnabled(true);
        bHelp.setText("HELP");

        if(dif.charAt(0) =='h') {
            mCountDownTimer.cancel();
            mTimerRunning = false;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            startTimer();

        }


    }



    // Reset all the values
    public void rest(){

        for(int i =0; i <26;++i) {
            b[i].setText(letters[i]);
            b[i].setTextSize(14);
            b[i].setEnabled(true);
        }

        for(int i =0; i <3;++i) {
            num[i] =-1;
        }
    }




    // Checks how many wrong guesses - 5 wrong guess and will declare loser - all skull on keyboard
    public void counterCheck(int counter){

        if(counter ==5){
            tv5.setText("YOU LOSE");
            tv1.setText(g);

            if(dif.charAt(0) == 'h'){
                mCountDownTimer.cancel();
                mTimerRunning = false;

            }
            else if(dif.charAt(0) == 'e'){
                bHelp.setEnabled(false);
            }


            for(int i =0; i <26;++i){
                setEmoji(b[i],false,true);

            }
        }
    }



    //all happy on keyboard - gives bonus points
    public void winner(){

        for(int i =0; i <26;++i)
            if (dif.charAt(0) == 'h') {
                b[i].setText(new String(Character.toChars(0x1F603)));
            } else {
                setEmoji(b[i], true, true);

            }

        points +=25;
        tvp.setText(Integer.toString(points));
        isWinner = 25;

        if(dif.charAt(0) =='e'){
            bHelp.setEnabled(false);
        }
    }




    // Sets up the guess string to # - also gives space
    public void setHash(){
        a="";

        for(int i =0; i<g.length();++i) {
            char letter =g.charAt(i);
            if(letter != ' ') {
                a+= '#';

            }
            else
                a+= " ";
        }
        tv2.setText(a);

    }





    // builds the guess string as the user gets them correct
    // also checks to see if the user guessed all the correct letters - declares winner
    public void correct(char l){

        for(int i =0; i<g.length();++i) {
            char test = g.charAt(i);
            if(test == l ) {
                a = a.substring(0, i)+test+ a.substring(i+1);
                tv2.setText(a);
            }

        }

        if(a.indexOf('#') < 0) {
            tv5.setText("YOU WIN");
            winner();
        }


    }




// sets up the emojis - takes a button, isTrue is for is the guess correct - false makes skull and true makes happy
    //end is for is it the end of the game - if false then it either adds or Subtracts points. with true is for placing all keyboard with emoji and so it doesnt mess with the points

    public void setEmoji(Button b, boolean isTrue, boolean end){
        if(!isTrue) {
            b.setText(new String(Character.toChars(0x1F480))); //Skull
            if(!end)
                points -=3;
        }
        else {

            b.setText(new String(Character.toChars(0x1F603))); //Happy
            if(!end)
                points +=10;
        }

        b.setTextSize(18);
        b.setEnabled(false);

        tvp.setText(Integer.toString(points));
    }

    public void importWords(String fileName){

        try{
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[]buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);

        }catch (IOException ex){
            ex.printStackTrace();
        }


    }

    public void getWord(String fileName, int num){

        int add =3;
        if(fileName.charAt(0) == 'm')
            add =5;

        else if(fileName.charAt(0) == 'h')
            add = 7;

        String gWord = text.substring(num, num+add);
        g = gWord;
    }


    private void helpSetup() {
        // Stores the index of the guessing word sets them to -1

        for(int s =0; s < 3; ++s) {
            num[s] = -1;
        }

        //Goes through the guessing word string with the letters array and if it finds the letters stores that number in the num
        // replaces the value that is stored as -1
        for(int i =0; i < g.length(); ++i) {
            char a = g.charAt(i);

            for(int j = 0; j < 26; ++j) {
                char b = letters[j].toLowerCase().charAt(0);

                if(a == b){
                    int counter =0;
                    for(int s =0; s < 3; ++s) {
                        if(num[s] == j)
                            ++counter;
                    }
                    if(counter ==0) {
                        for(int s =0; s < 3; ++s) {
                            if(num[s] == -1) {
                                num[s] = j;
                                break;
                            }
                        }
                    }
                }
            }
        }

    }

    private void easyKeyboard() {

        // Generates a random keyboard with only 10 keys
        int x =0;
        int counter =0;

        while(counter < 16){
            x = r.nextInt(26);

            int check =0;
            for(int i =0; i <3; ++i){
                if (num[i] != -1 && num[i] == x){
                    ++check;
                    break;
                }
            }
            if(check ==0 && b[x].isEnabled() ){
                b[x].setText(" ");
                b[x].setEnabled(false);
                ++counter;
            }
        }
    }




    public void onButtonHelpClicked(View view) throws InterruptedException {

        if(a.charAt(0) == '#') {
            b[num[0]].setText(new String(Character.toChars(0x1F61D)));
            b[num[0]].setTextSize(18);
        }
        else if(a.charAt(1) == '#'){
            b[num[1]].setText(new String(Character.toChars(0x1F61D)));
            b[num[1]].setTextSize(18);
        }
        else {
            b[num[2]].setText(new String(Character.toChars(0x1F61D)));
            b[num[2]].setTextSize(18);
        }

        bHelp.setEnabled(false);

    }
}
