package com.example.assignment_3;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment_3.model.Note;

import java.io.ByteArrayInputStream;

public class EditNote extends AppCompatActivity {

    private static final int pic_id = 111;
    private static final int PERMISSION_CODE = 222;


    TextView locationNameID, descriptionID;
    Button addNoteBtn, updateBtn, deleteBtn;
    ImageView locationImageID;
    Bitmap bitmap;
    private int NoteIDInt;
    private String LocationNameString,DescriptionString;
    byte[] MyByteImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);


        locationNameID = findViewById(R.id.locationNameID3);
        descriptionID = findViewById(R.id.descriptionID3);
        addNoteBtn = findViewById(R.id.addNoteBtn3);
        updateBtn = findViewById(R.id.updateBtn3);
        deleteBtn = findViewById(R.id.deleteBtn3);
        locationImageID = findViewById(R.id.locationImageID3);

        Intent ReceiveIntent = getIntent();
        NoteIDInt = ReceiveIntent.getIntExtra("NoteID",1);
        LocationNameString = ReceiveIntent.getStringExtra("LocationName");
        DescriptionString =  ReceiveIntent.getStringExtra("Description");
        MyByteImage = ReceiveIntent.getByteArrayExtra("MyImage");

        ByteArrayInputStream imageStream = new ByteArrayInputStream(MyByteImage);
        bitmap = BitmapFactory.decodeStream(imageStream);

        DB db = new DB(EditNote.this);


        locationNameID.setText(LocationNameString);
        descriptionID.setText(DescriptionString);
        locationImageID.setImageBitmap(bitmap);

        Toast.makeText(EditNote.this,""+LocationNameString, Toast.LENGTH_SHORT).show();

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

        updateBtn.setOnClickListener(new View.OnClickListener() {
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

                    Note note = new Note(NoteIDInt,LocationNameString, DescriptionString, myImage);

                    try {

                        DB db = new DB(EditNote.this);
                        db.updateData(note);
                        Toast.makeText(getApplicationContext(), "Data Update", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error !" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(NoteIDInt);
                try {

                    DB db = new DB(EditNote.this);
                    db.deleteData(note);
                    Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(EditNote.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error !" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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