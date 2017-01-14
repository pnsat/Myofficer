package sanghirun.yongyut.myofficer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SignUpActivity extends AppCompatActivity {

    // eplicit
    private ImageView imageView;
    private EditText nameEditText, userEditText, passwordEditText;
    private Button button;
    private String nameString, userString, passwordString;
    private Uri uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView);
        nameEditText = (EditText) findViewById(R.id.editText);
        userEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        button = (Button) findViewById(R.id.button3);

        // buttonController
        buttonController();

        //imageController
        imageController();


    }   //Main Method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            uri = data.getData();

            //show Image Choose to Imageview
            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getContentResolver()
                                .openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }




        } // if


    }

    private void imageController() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose App"), 0);


            }  //OnClick
        });

    }

    private void buttonController() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value from Edit text
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //check space
                if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
                    //have space
                    MyAlert myAlert = new MyAlert(SignUpActivity.this);
                    myAlert.errorDialog("Have Space", "Please Fill All Every Blank");

                }



            } //On Click
        });
    }

}   //Main Class
