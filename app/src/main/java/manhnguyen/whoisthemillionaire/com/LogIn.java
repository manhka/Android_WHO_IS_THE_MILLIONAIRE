package manhnguyen.whoisthemillionaire.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LogIn extends MainActivity {
TextView announcementLogIn,noAccount;
Button LogIn;
EditText usernameLogIn,passLogIn;
CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        SharedPreferences sharedPreferences=getSharedPreferences("dataLogIn",MODE_PRIVATE);
        Mapping();
        usernameLogIn.setText(sharedPreferences.getString("username",""));
        passLogIn.setText(sharedPreferences.getString("password",""));
        rememberMe.setChecked(sharedPreferences.getBoolean("checked",false));
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this,MainActivity.class));
            }
        });
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username= usernameLogIn.getText().toString().trim();
                String password= passLogIn.getText().toString().trim();

                if (username.isEmpty()||password.isEmpty()){
                    announcementLogIn.setText("All fields are required!");
                }else if (SetToLogIn(username,password)==false){
                    announcementLogIn.setText("Username or Password is incorrect, Try again!");
                }else {
                    announcementLogIn.setText("LogIn Successful!");
                    if (rememberMe.isChecked()){
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("username",username);
                        editor.putString("password",password);
                        editor.putBoolean("checked",true);
                        editor.commit();
                    }else {
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.remove("username");
                        editor.remove("password");
                        editor.remove("checked");
                        editor.commit();
                    }
                    startActivity(new Intent(LogIn.this,MainScreenGame.class));
                }

            }
        });
    }


    private void Mapping() {
        rememberMe=(CheckBox)findViewById(R.id.rememberMe);
        announcementLogIn=(TextView) findViewById(R.id.announcementLogIn);
        noAccount=(TextView) findViewById(R.id.noAccount);
        LogIn=(Button) findViewById(R.id.btnLogIn);
        usernameLogIn=(EditText) findViewById(R.id.userLogIn);
        passLogIn=(EditText) findViewById(R.id.passLogIn);
    }
}