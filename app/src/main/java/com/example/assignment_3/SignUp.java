package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment_3.model.User;

public class SignUp extends AppCompatActivity {

    EditText FirstNameID,LastNameID,UserNameID,EmailID,
            AddressID,PasswordID,RePasswordID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirstNameID = findViewById(R.id.firstNameID);
        LastNameID = findViewById(R.id.lastNameID);
        UserNameID = findViewById(R.id.userNameID);
        EmailID = findViewById(R.id.emailID);
        AddressID = findViewById(R.id.addressID);


        PasswordID = findViewById(R.id.passwordID);
        RePasswordID = findViewById(R.id.rePasswordID);

        Button button = findViewById(R.id.noteBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (FirstNameID.getText().toString().isEmpty()){
            FirstNameID.setError("First Name is Empty");
        }
        else if (LastNameID.getText().toString().isEmpty()){
            LastNameID.setError("Last Name is Empty");
        }
        else if (UserNameID.getText().toString().isEmpty()){
            UserNameID.setError("Last Name is Empty");
        }
        else if (EmailID.getText().toString().isEmpty() || EmailID.getText().toString().matches(emailPattern)){
            EmailID.setError(" Please Enter Valid Email Address");
        }
        else if (AddressID.getText().toString().isEmpty()){
            AddressID.setError("Address is Empty");
        }
        else if (PasswordID.getText().toString().isEmpty()){
            PasswordID.setError("Password is Empty");
        }
        else if (PasswordID.length() < 5){
            PasswordID.setError("Password is too Short");
        }
        else if (RePasswordID.getText().toString().isEmpty()){
            LastNameID.setError("Re-Password is Empty");
        }
        else if (!RePasswordID.getText().toString().contains(PasswordID.getText().toString())){
            RePasswordID.setError("Password does not match");
        }
        else{

            String FirstNameString = FirstNameID.getText().toString();
            String LastNameString = LastNameID.getText().toString();
            String UserNameString = UserNameID.getText().toString();
            String EmailString = EmailID.getText().toString();
            String AddressString = AddressID.getText().toString();
            String PasswordString = PasswordID.getText().toString();
            DB db = new DB(SignUp.this);

            if (db.isUserNameExists(UserNameString)){
                Toast.makeText(getApplicationContext(),"UserName is already Exist",Toast.LENGTH_SHORT).show();
            }
            else{
                User user = new User(FirstNameString,LastNameString,UserNameString,EmailString,
                        AddressString,PasswordString);
                try {


                    boolean b = db.Insert_into_User(user);
                    if (b){
                        Toast.makeText(getApplicationContext(),"Data Inserted",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    }


                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error !"+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }


        }

    }
}