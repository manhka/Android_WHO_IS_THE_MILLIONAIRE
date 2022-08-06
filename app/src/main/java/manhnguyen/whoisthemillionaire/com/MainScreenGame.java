package manhnguyen.whoisthemillionaire.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainScreenGame extends AppCompatActivity {
    DataBase dataBase;
    ArrayList<Question> questionArrayList;
    Button btnA, btnB, btnC, btnD;
    TextView question, money;
    ImageView mic;
    boolean checkAnswer = false;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_game);
        Mapping();
//        CreateDataMillionaire();
//        InsertData();
        GetDataMillionaire();
        MakeGame();
        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String A = btnA.getText().toString().trim();
                if (A.equals(questionArrayList.get(i).getCorrectAnswer())) {
                    question.setText("True");


                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            i++;
                            if (i == questionArrayList.size() - 1) {
                                startActivity(new Intent(MainScreenGame.this, TheMillionaire.class));

                            }
                            MakeGame();
                        }
                    }.start();

                } else {
                    question.setText("Failed!");

                }
            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String B = btnB.getText().toString().trim();
                if (B.equals(questionArrayList.get(i).getCorrectAnswer())) {
                    question.setText("True");
                    checkAnswer = true;
                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            i++;
                            if (i == questionArrayList.size() - 1) {
                                startActivity(new Intent(MainScreenGame.this, TheMillionaire.class));
                            }
                            MakeGame();
                        }
                    }.start();

                } else {
                    question.setText("Failed!");

                }
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String C = btnC.getText().toString().trim();
                if (C.equals(questionArrayList.get(i).getCorrectAnswer())) {
                    question.setText("True");
                    checkAnswer = true;
                    new CountDownTimer(3000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            i++;
                            if (i == questionArrayList.size() - 1) {
                                startActivity(new Intent(MainScreenGame.this, TheMillionaire.class));

                            }
                            MakeGame();
                        }
                    }.start();
                } else {
                    question.setText("Failed!");

                }
            }
        });
        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String D = btnD.getText().toString().trim();
                if (D.equals(questionArrayList.get(i).getCorrectAnswer())) {
                    question.setText("True");
                    checkAnswer = true;
                    new CountDownTimer(1000, 1000) {

                        public void onTick(long millisUntilFinished) {

                        }

                        public void onFinish() {
                            i++;
                            if (i == questionArrayList.size() - 1) {
                                startActivity(new Intent(MainScreenGame.this, TheMillionaire.class));
                            }
                            MakeGame();
                        }
                    }.start();

                } else {
                    question.setText("Failed!");

                }
            }
        });

    }

    // insert data
    private void InsertData() {
        String sqlInsert = "INSERT INTO whoisthemillionaires VALUES(null,'1+2=?','A:0','B:2','C:3','D:4','C:3')";
        dataBase.QueryData(sqlInsert);
    }


    private void GetDataMillionaire() {
        String sqlSELECT = "SELECT * FROM whoisthemillionaires";
        questionArrayList.clear();
        Cursor dataMillionaire = dataBase.GetData(sqlSELECT);
        while (dataMillionaire.moveToNext()) {
            questionArrayList.add(new Question(dataMillionaire.getInt(0),
                    dataMillionaire.getString(1),
                    dataMillionaire.getString(2),
                    dataMillionaire.getString(3),
                    dataMillionaire.getString(4),
                    dataMillionaire.getString(5),
                    dataMillionaire.getString(6)));
        }
    }

    // make game
    private void MakeGame() {
        question.setText("Question "+(i+1) +": "+questionArrayList.get(i).getQuestion());
        btnA.setText(questionArrayList.get(i).getAnswerA());
        btnB.setText(questionArrayList.get(i).getAnswerB());
        btnC.setText(questionArrayList.get(i).getAnswerC());
        btnD.setText(questionArrayList.get(i).getAnswerD());

    }


    private void CreateDataMillionaire() {
        String sqlTb = "CREATE TABLE IF NOT EXISTS whoisthemillionaires(id INTEGER PRIMARY KEY AUTOINCREMENT,question VARCHAR(200)," +
                "a VARCHAR(200),b VARCHAR(200),c VARCHAR(200),d VARCHAR(200),correct VARCHAR(200))";
        dataBase.QueryData(sqlTb);
    }

    private void Mapping() {
        dataBase = new DataBase(this, "millionaire.com", null, 3);
        btnA = (Button) findViewById(R.id.answerA);
        btnB = (Button) findViewById(R.id.answerB);
        btnC = (Button) findViewById(R.id.answerC);
        btnD = (Button) findViewById(R.id.answerD);
        question = (TextView) findViewById(R.id.question);
        money = (TextView) findViewById(R.id.money);
        mic = (ImageView) findViewById(R.id.mic);
        questionArrayList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuadd) {
            DialogAddNewQuestion();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogAddNewQuestion() {
        Dialog dialog = new Dialog(this);dialog.setContentView(R.layout.dialog_add_new_question);
        dialog.setCanceledOnTouchOutside(true);
        EditText a, b, c, d,newQuestiona,answer1;
        Button addNewQuestion;
        addNewQuestion = (Button) dialog.findViewById(R.id.addQuestion);
        newQuestiona = (EditText) dialog.findViewById(R.id.newquestionxx);
        answer1 = (EditText) dialog.findViewById(R.id.correct);
        a = (EditText) dialog.findViewById(R.id.A);
        b = (EditText) dialog.findViewById(R.id.B);
        c = (EditText) dialog.findViewById(R.id.C);
        d = (EditText) dialog.findViewById(R.id.D);

       String newQt=newQuestiona.getText().toString().trim();
       String newcA=answer1.getText().toString().trim();
       String newa=a.getText().toString().trim();
       String newb=b.getText().toString().trim();
       String newc=c.getText().toString().trim();
       String newd=d.getText().toString().trim();
        addNewQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sqlInsert = "INSERT INTO whoisthemillionaires VALUES(null,'"+newQt+"','"+newa+"','"+newb+"','"+newc+"','"+newd+"','"+newcA+"')";
                dataBase.QueryData(sqlInsert);
                GetDataMillionaire();
                dialog.dismiss();

            }
        });
        dialog.show();

    }
}