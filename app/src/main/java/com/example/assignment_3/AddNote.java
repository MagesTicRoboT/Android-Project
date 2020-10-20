package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.model.Note;

public class AddNote extends AppCompatActivity {

    private static final int PERMISSION_CODE = 101;
    TextView locationNameID, descriptionID;
    Button addNoteBtn, saveBtn;
    ImageView locationImageID;
    private static final int pic_id = 123;
    Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        locationNameID = findViewById(R.id.locationNameID);
        descriptionID = findViewById(R.id.descriptionID);
        addNoteBtn = findViewById(R.id.addNoteBtn);
        saveBtn = findViewById(R.id.saveBtn);
        locationImageID = findViewById(R.id.locationImageID);

        locationNameID.setVisibility(View.INVISIBLE);
        descriptionID.setVisibility(View.INVISIBLE);
        locationImageID.setVisibility(View.INVISIBLE);
        saveBtn.setVisibility(View.INVISIBLE);

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CheckForPermission();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationNameID.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Location Name is Empty", Toast.LENGTH_SHORT).show();
                } else if (descriptionID.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Description is Empty", Toast.LENGTH_SHORT).show();
                } else if (locationImageID.getDrawable() == null) {
                    Toast.makeText(getApplicationContext(), "Image is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    String LocationNameString = locationNameID.getText().toString();
                    String DescriptionString = descriptionID.getText().toString();


                    byte myImage[] = DB.getBitmapAsByteArray(bitmap);

                    Note note = new Note(LocationNameString, DescriptionString, myImage);

                    try {

                        DB db = new DB(AddNote.this);
                        db.Insert_Into_Note(note);
                        Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error !" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pic_id) {
            bitmap = (Bitmap) data.getExtras().get("data");
            locationImageID.setImageBitmap(bitmap);
            locationNameID.setVisibility(View.VISIBLE);
            descriptionID.setVisibility(View.VISIBLE);
            locationImageID.setVisibility(View.VISIBLE);
            saveBtn.setVisibility(View.VISIBLE);

        }

    }

    private void CheckForPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {

                String[] permission = {Manifest.permission.CAMERA};
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                //Permission granted
                OpenCamera();
            }
        } else {
            //Build is less than the required build
        }
    }

    private void OpenCamera(){
        try{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, pic_id);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
