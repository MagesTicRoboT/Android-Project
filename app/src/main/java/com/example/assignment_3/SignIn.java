package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_3.model.User;

import java.security.Permission;

public class SignIn extends AppCompatActivity {
    EditText UserNameID,PasswordID;
    Button LoginBtn;
    private int PERMISSION_CODE=101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        UserNameID = findViewById(R.id.userNameID2);
        PasswordID = findViewById(R.id.passwordID2);
        LoginBtn = findViewById(R.id.signInBtn);



        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserNameID.getText().toString().isEmpty()){
                    UserNameID.setError("Last Name is Empty");
                }
                else if (PasswordID.getText().toString().isEmpty()){
                    PasswordID.setError("Password is Empty");
                }
                else{

                    String UserName = UserNameID.getText().toString();
                    String Password = PasswordID.getText().toString();


                    User user = new User(UserName, Password);
                    DB db = new DB(SignIn.this);

                    try {
                        boolean b = db.Check_User(user);
                        if (b) {
                            Intent intent = new Intent(SignIn.this,MainActivity.class);

                            intent.putExtra("UserName", UserName);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Something Went wrong",Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }
        });


    }


}