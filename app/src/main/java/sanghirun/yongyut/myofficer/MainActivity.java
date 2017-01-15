package sanghirun.yongyut.myofficer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private Button signInButton, signUpButton;
    private EditText userEditText, passwordEditText;
    private String userString, passwordString, truePasswordString;



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
        Log.d("15janV1", "UserPass ==> " + passwordString);

        //Check User and Password
        if (userString.equals("") || passwordString.equals("")) {
            //Have String
            MyAlert myAlert = new MyAlert(MainActivity.this);
            myAlert.errorDialog("มีช่องว่าง", "กรุณากรอกทุกช่องค่ะ");

        } else if (checkUser()) {
            //User False
            MyAlert myAlert = new MyAlert(MainActivity.this);
            myAlert.errorDialog("User False", "No This user in my database");
        } else if (!passwordString.equals(truePasswordString)) {
            MyAlert myAlert = new MyAlert(MainActivity.this);
            myAlert.errorDialog("Password False", "Please try Again");

        } else {
            // Password True
            startActivity(new Intent(MainActivity.this, ServiceActivity.class));
            finish();
        }

    } // myAuthen

    private boolean checkUser() {

        boolean result = true; // User False

        try {

            SynUser synUser = new SynUser(MainActivity.this);
            synUser.execute();
            String strJSON = synUser.get();
            Log.d("15janV1", "JSON ==>" + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {
                    truePasswordString = jsonObject.getString("Password");
                    Log.d("15janV1", "truePass ==> " + truePasswordString);
                    result = false;  // User True
                }

            } //for


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }
}   // Main Class
