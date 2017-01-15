package sanghirun.yongyut.myofficer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    // eplicit
    private ImageView imageView;
    private EditText nameEditText, userEditText, passwordEditText;
    private Button button;
    private String nameString, userString, passwordString,
            pathImageChooseString, nameImageChooseString;
    private Uri uri;
    private boolean aBoolean = true;



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
            aBoolean = false;


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

                } else if (aBoolean) {
                    //Non Choose Image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this);
                    myAlert.errorDialog("ยังไม่เลือกรูปภาพ", "กรุณาเลือกรูปภาพด้วยค่ะ");

                } else {
                    // Every Thing Ok
                    uploadDataToServer();

                }



            } //On Click
        });
    }

    private void uploadDataToServer() {

        //Find Path of Image Choose
        String[] strings = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings, null, null, null);
        if (cursor !=null) {
            cursor.moveToFirst();
            int i = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            pathImageChooseString = cursor.getString(i);


        } else {
            pathImageChooseString = uri.getPath();

        }
        Log.d("14janV1", "pathImage ==> " + pathImageChooseString);

        //Find name of Image Choose
        nameImageChooseString = pathImageChooseString.substring(pathImageChooseString.lastIndexOf("/"));
        Log.d("14janV1", "nameImage ==> " + nameImageChooseString);

        //upload Image
        try {

            //Connect FTP protocal
            StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                    .Builder().permitAll().build();
            StrictMode.setThreadPolicy(threadPolicy);


            //Upload Image
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21,
                    "14jan@swiftcodingthai.com", "Abc12345");

            simpleFTP.bin();
            simpleFTP.cwd("YutImage");
            simpleFTP.stor(new File(pathImageChooseString));
            simpleFTP.disconnect();

            //Upload Text
            UpdateStringToServer updateStringToServer = new UpdateStringToServer(SignUpActivity.this,
                    nameString, userString, passwordString,
                    "http://swiftcodingthai.com/14jan/YutImage/ + nameImageChooseString");
            updateStringToServer.execute();

            if (Boolean.parseBoolean(updateStringToServer.get())) {
                // Upload  Success
                finish();

            } else {
                //Un Success
                MyAlert myAlert = new MyAlert(SignUpActivity.this);
                myAlert.errorDialog("Upload False", "Please Try Again Upload False");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }



    }  // Upload



}   //Main Class
