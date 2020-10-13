package com.example.hangman2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static int wins;
    TextView txtWordGuessed;
    String wordGuessed;
    String wordDisplayedString;
    char[] wordDisplayedCharArray;
    ArrayList<String> myList;
    EditText edtInput;
    TextView txtLettersTried;
    String lettersTried;
    final String MESSAGE_WITH_LETTERS_TRIED = "Letters tried: ";
    TextView txtTriesLeft;
    String triesLeft;
    final String WINNING_MESSAGE = "You won!";
    final String LOSING_MESSAGE = "You lost!";
    private Button scorebtn;
    int listcounter = 0;


    void revealLetterInWord(char letter) {
        int indexOfLetter = wordGuessed.indexOf(letter);

        while (indexOfLetter >= 0) {
            wordDisplayedCharArray[indexOfLetter] = wordGuessed.charAt(indexOfLetter);
            indexOfLetter = wordGuessed.indexOf(letter, indexOfLetter + 1);
        }
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);
    }

    void displayWordOnScreen () {
        StringBuilder formattedString = new StringBuilder();
        for (char character : wordDisplayedCharArray) {
            formattedString.append(character).append(" ");
        }
        txtWordGuessed.setText(formattedString.toString());
    }

    void initializeGame () {
        //shuffle list

        wordGuessed = myList.get(0);
        try {
            myList.remove(listcounter);
            listcounter++;
        }
        catch (Exception e) {
            listcounter=0;
        }


        wordDisplayedCharArray = wordGuessed.toCharArray();

        //underscore
        for(int i = 0; i < wordDisplayedCharArray.length - 1; i++) {
            wordDisplayedCharArray[i] ='_';
        }

        //reveal last letter
        revealLetterInWord(wordDisplayedCharArray[wordDisplayedCharArray.length - 1]);

        //initialize string
        wordDisplayedString = String.valueOf(wordDisplayedCharArray);

        //display word
        displayWordOnScreen();

        //clear input field
        edtInput.setText("");

        //initialize string for letters tried
        lettersTried = " ";
        txtLettersTried.setText(MESSAGE_WITH_LETTERS_TRIED);

        //tries left
        triesLeft = " X X X X X";
        txtTriesLeft.setText((triesLeft));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myList = new ArrayList<String>( );
        txtWordGuessed = findViewById(R.id.myWord);
        edtInput = (EditText) findViewById(R.id.input);
        txtLettersTried = findViewById(R.id.lettersTried);
        txtTriesLeft = findViewById(R.id.triesLeft);

        InputStream myInputStream = null;
        Scanner in = null;
        String aWord = "";

        try {
            myInputStream = getAssets().open("database_file.txt");
            in = new Scanner (myInputStream);
            while (in.hasNext()) {
                aWord = in.next();
                myList.add(aWord);

            }
        } catch (IOException e) {
            Toast.makeText(MainActivity.this,
                    e.getClass().getSimpleName() + ": " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        finally {
            //close scanner
            if(in !=null) {
                in.close();
            }

            //close input
            try {
                if(myInputStream != null) {
                    myInputStream.close();
                }
            }catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        e.getClass().getSimpleName() + ": " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
        Collections.shuffle(myList);
        initializeGame();

        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() !=0){
                    String str = s.toString();
                    checkIfLetterIsInWord(str.toLowerCase());
                    edtInput.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        scorebtn = findViewById(R.id.scoreBtn);
        scorebtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoreActivity();
            }
        }));





    }
    public void openScoreActivity() {
        Intent intent = new Intent (this, ScoreActivity.class);
        intent.putExtra("wins",wins);
        startActivity(intent);
    }

    void checkIfLetterIsInWord(String letter) {
        if(wordGuessed.indexOf(letter) >=0) {
            if (wordDisplayedString.indexOf(letter) < 0){
                char let = letter.charAt(0);
                revealLetterInWord(let);

                displayWordOnScreen();

                if (!wordDisplayedString.contains("_")) {
                    txtTriesLeft.setText(WINNING_MESSAGE);
                    wins++;

                }
            }
        }
        else {
            decreaseAndDisplayTriesLeft();

            if (triesLeft.isEmpty()) {
                txtTriesLeft.setText(LOSING_MESSAGE);
                txtWordGuessed.setText(wordGuessed);
            }
        }
        if (lettersTried.indexOf(letter) < 0) {
            lettersTried += letter +", ";
            String messageToBeDisplayed = MESSAGE_WITH_LETTERS_TRIED + lettersTried;
            txtLettersTried.setText(messageToBeDisplayed);

        }
    }

    void decreaseAndDisplayTriesLeft (){
        if (!triesLeft.isEmpty()){
            triesLeft = triesLeft.substring(0, triesLeft.length() - 2);
            txtTriesLeft.setText(triesLeft);
        }
    }

    public void resetGame(View view) {
        initializeGame();
    }

}
