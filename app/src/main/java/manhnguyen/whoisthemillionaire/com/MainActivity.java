package manhnguyen.whoisthemillionaire.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DataBase dataBase;
    EditText username, password, passwordConfirm;
    Button btnSignUp;
    TextView announcement, havingAccount;
    ArrayList<Users> arrayListUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        dataBase = new DataBase(this, "mydata", null, 3);
        String sqlTable = "CREATE TABLE IF NOT EXISTS users(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(100), password VARCHAR(50), passwordconfirm VARCHAR(50))";
        dataBase.QueryData(sqlTable);
//        String sqlInsert = "INSERT INTO users VALUES(null,'TIEN','123456','123456')";
//        dataBase.QueryData(sqlInsert);
        GetData();
        // sign up
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp();
            }
        });
        // LogIn if having account
        havingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LogIn.class));
            }
        });
    }

    // set username and password to log in
    public boolean SetToLogIn(String user, String pass) {
        String sqlSelect = "SELECT * FROM users";
        Cursor data = dataBase.GetData(sqlSelect);
        while (data.moveToNext()) {
            arrayListUser.add(new Users(data.getInt(0), data.getString(1), data.getString(2), data.getString(3)));
        }
        for (int i = 0; i < arrayListUser.size(); i++) {
            if (user.equals(arrayListUser.get(i).getUsername()) && pass.equals(arrayListUser.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

    private void GetData() {
        String sqlSelect = "SELECT * FROM users";
        Cursor data = dataBase.GetData(sqlSelect);
        while (data.moveToNext()) {
            Toast.makeText(this, data.getString(1), Toast.LENGTH_SHORT).show();
        }
    }

    // signUp
    private void SignUp() {
        String usernameSignUp = username.getText().toString().trim();
        String passSignUp = password.getText().toString().trim();
        String passConfirmSignUp = passwordConfirm.getText().toString().trim();
        if (usernameSignUp.isEmpty() || passSignUp.isEmpty() || passConfirmSignUp.isEmpty()) {
            announcement.setText("All fields are required!");
        } else if (!passSignUp.equals(passConfirmSignUp)) {
            announcement.setText("Password need to equal passwordConfirm!");
        } else if (SetUserExisted(usernameSignUp)) {
            announcement.setText("This username was already existed , please try an other one!");
        } else {
            String sqlInsertNewAccount = "INSERT INTO users VALUES(null,'" + usernameSignUp + "','" + passSignUp + "','" + passConfirmSignUp + "')";
            dataBase.QueryData(sqlInsertNewAccount);
            announcement.setText("Congratulation bro! LogIn now");
        }
    }

    // set user existed
    private boolean SetUserExisted(String usernameExist) {
        String sqlSelect = "SELECT * FROM users";
        Cursor data = dataBase.GetData(sqlSelect);
        while (data.moveToNext()) {
            arrayListUser.add(new Users(data.getInt(0), data.getString(1), data.getString(2), data.getString(3)));
        }
        for (int i = 0; i < arrayListUser.size(); i++) {
            if (usernameExist.equals(arrayListUser.get(i).getUsername())) {
                return true;
            }
        }
        return false;
    }


    private void Mapping() {
        havingAccount = (TextView) findViewById(R.id.havingAccount);
        arrayListUser = new ArrayList<>();
        announcement = (TextView) findViewById(R.id.announcement);
        username = (EditText) findViewById(R.id.userSignUp);
        password = (EditText) findViewById(R.id.passSignUp);
        passwordConfirm = (EditText) findViewById(R.id.passRepeatSignUp);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }
}