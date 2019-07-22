package fr.tf_i.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.tf_i.R;
import fr.tf_i.SampleActivity;
import fr.tf_i.model.Question;
import fr.tf_i.model.QuestionBank;
import fr.tf_i.model.User;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private User mUser = new User();
    private SampleActivity sa = new SampleActivity();
    private LeaderboardActivity mLeaderboard = new LeaderboardActivity();

    private TextView mQuestionTextView;
    private Button mAnswer1Button;
    private Button mAnswer2Button;
    private Button mAnswer3Button;
    private Button mAnswer4Button;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mPercentage;
    private String mName;
    private int mNumberOfQuestions;
    private int mCurrentQuestionNumber;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_EXTRA_NUMBOFQUESTIONS = "BUNDLE_EXTRA_NUMBOFQUESTIONS";
    public static final String BUNDLE_EXTRA_PERCENTAGE = "BUNDLE_EXTRA_PERCENTAGE";

    private boolean mEnableTouchEvents;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        System.out.println("GameActivity::onCreate()");

        //Assign variable to the objects
        mQuestionTextView = (TextView) findViewById(R.id.activity_game_question_text);

        mAnswer1Button = (Button) findViewById(R.id.activity_game_answer1_btn);
        mAnswer2Button = (Button) findViewById(R.id.activity_game_answer2_btn);
        mAnswer3Button = (Button) findViewById(R.id.activity_game_answer3_btn);
        mAnswer4Button = (Button) findViewById(R.id.activity_game_answer4_btn);



        //Set the listeners
        mAnswer1Button.setOnClickListener(this);
        mAnswer2Button.setOnClickListener(this);
        mAnswer3Button.setOnClickListener(this);
        mAnswer4Button.setOnClickListener(this);

        //Tag the buttons
        mAnswer1Button.setTag(0);
        mAnswer2Button.setTag(1);
        mAnswer3Button.setTag(2);
        mAnswer4Button.setTag(3);


        mScore = 0;
        mNumberOfQuestions = getIntent().getExtras().getInt("NUMBER_OF_QUESTIONS");


        //Game

        mUser.setNumberOfQuestions(mNumberOfQuestions);
        mCurrentQuestionNumber = 0;
        mEnableTouchEvents = true;

        mQuestionBank = this.generateQuestions();
        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

    }

    @Override
    public void onClick(View v) {
        int responseIndex = (int) v.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()) {
            //GOOD ANSWER
            Toast.makeText(this, "Bonne reponse =) ", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            //WRONG ANSWER
            Toast.makeText(this,"Mauvaise reponse =( ", Toast.LENGTH_SHORT).show();
        }

        mEnableTouchEvents = false;
        //Tempo 2 sec between each questions
        //Used this way (sa.mHandler) to "avoid" memory leak
        sa.mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mEnableTouchEvents = true;
                //IF it is the last Question, End Game
                //ELSE, Display the Next Question.
                if (++mCurrentQuestionNumber == mNumberOfQuestions) {
                    //END OF THE GAME
                    endGame();
                } else {
                    //NEXT QUESTION
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        }, 2000); // LENGTH_SHORT is usually 2 second long
    }
    @Override
    public  boolean dispatchTouchEvent (MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    /*
    End of the game then quit GameActivity
     */
    private void endGame() {

        mPercentage = (mScore*100)/mNumberOfQuestions;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bravo !")
                .setMessage("Votre score est de " + mPercentage + "%")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //End of the activity
                        mLeaderboard.addScoreNameToLeaderboard(mPercentage,mName);
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_PERCENTAGE,mPercentage);
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        intent.putExtra(BUNDLE_EXTRA_NUMBOFQUESTIONS, mNumberOfQuestions);
                        mUser.setPercentage(mPercentage);
                        mUser.setScore(mScore);
                        mUser.setNumberOfQuestions(mNumberOfQuestions);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }


    /*
    Display question and responses to the screen.
    */
    private void displayQuestion(final Question question) {

        mQuestionTextView.setText(question.getQuestion());
        mAnswer1Button.setText(question.getChoiceList().get(0));
        mAnswer2Button.setText(question.getChoiceList().get(1));
        mAnswer3Button.setText(question.getChoiceList().get(2));
        mAnswer4Button.setText(question.getChoiceList().get(3));
    }


    /*
    Create Questions.
     */
    private QuestionBank generateQuestions(){

        Question question1 = new Question("Quel est le nom du président de la république Française actuel (2019) ?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("Combien d'état membre compose l'Union Européenne ?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Qui est le créateur du Système d'exploitation Android ?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("En quelle année l'homme a marché sur la lune pour la première fois ?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("Quel est la capitale de la Roumanie ?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Qui a peint Mona Lisa ?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("Dans quelle ville le compositeur Frédéric Chopin est enterré ?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("Quel est le ''Top-Level domain'' de la Belgique pour ses pages web ?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("Quel est le numéro de la maison des Simpsons ?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        Question question10 = new Question("Combien de grammes de sucre en moyenne contient une canette de Cola de 33cl ?",
                Arrays.asList("12", "18", "22" , "28"),
                3);

        Question question11 = new Question("En quelle année est sortie la PlayStation 1 en Europe ?",
                Arrays.asList("1994", "1995", "1996" , "1997"),
                1);

        Question question12 = new Question("En quelle année est sortie la première version publique de iOS ?",
                Arrays.asList("2005", "2006", "2007" , "2008"),
                2);

        Question question13 = new Question("Quel est le vrai nom de Johnny Hallyday",
                Arrays.asList("Jean-Philippe Smet", "John Hall", "Jean Leroy" , "Jerome Dupont"),
                0);

        Question question14 = new Question("Quel est le vrai nom du YouTuber Squeezie ?",
                Arrays.asList("Sébastien Hugnard", "Lucas Hauchard", "Théo Rignard" , "Tristan Machard"),
                1);

        Question question15 = new Question("Quand a été signé l'armistice de la 1ère Guerre Mondiale ?",
                Arrays.asList("21 Avril 1914", "16 Juin 1914", "17 Mars 1918" , "11 Novembre 1918"),
                3);

        Question question16 = new Question("Quel est la suite ? Pi = 3.14...",
                Arrays.asList("3.14057...", "3.14101...", "3.14159..." , "3.14224..."),
                2);

        Question question17 = new Question("Quand a été lancé ''Le Site du Zero'' ancètre de OpenClassRooms ?",
                Arrays.asList("12 Mai 1997", "10 Novembre 1999", "05 Janvier 2001" , "23 Octobre 2003"),
                1);

        Question question18 = new Question("Quel est le record du monde pour la vitesse de pointe d'un homme (Usain Bolt)",
                Arrays.asList("37,15 Km/h", "41,56 Km/h", "44,72 Km/h" , "46,25 Km/h"),
                2);




        return new QuestionBank(Arrays.asList
                (question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9,
                question10,
                question11,
                question12,
                question13,
                question14,
                question15,
                question16,
                question17,
                question18));
    }

    public void setName(String name) {
        mName = name;
    }

    @Override
    protected void onStart() {
        super.onStart();

        System.out.println("GameActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("GameActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("GameActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        System.out.println("GameActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        System.out.println("GameActivity::onDestroy()");
    }
}
