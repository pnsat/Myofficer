package sanghirun.yongyut.myofficer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private Button signInButton, signUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind wiget
        signInButton = (Button) findViewById(R.id.button);
        signUpButton = (Button) findViewById(R.id.button2);
        userEditText = (EditText) findViewById(R.id.editText4);
        passwordEditText = (EditText) findViewById(R.id.editText6);


        //Button Controller
        signInButton.setOnClickListener(MainActivity.this);
        signUpButton.setOnClickListener(MainActivity.this);


    }   // Main method


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button:  // for singin

                myAuthen();

                break;
            case R.id.button2:  // for singup
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                break;
        }

    }

    private void myAuthen() {

        //Get value
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        //Check User and Password
        if (userString.equals("") || passwordString.equals("")) {
            //Have String
            MyAlert myAlert = new MyAlert(MainActivity.this);
            myAlert.errorDialog("มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");

        }

    } // myAuthen
}   // Main Class
