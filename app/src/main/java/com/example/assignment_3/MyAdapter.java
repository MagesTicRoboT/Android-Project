package com.example.assignment_3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_3.model.Note;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.MyViewHolder>{

    List<Note> noteList = new ArrayList<Note>();
    Context context;

    public MyAdapter(Context context,List<Note> myList) {
        this.context = context;
        noteList = myList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {

        int NoteId = noteList.get(position).getNoteID();
        holder.NoteID.setText(""+NoteId);
        holder.LocationNameID.setText(noteList.get(position).getLocationName());
        holder.DescriptionID.setText(noteList.get(position).getDescription());

        byte[] outImage=noteList.get(position).getMyImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap image = BitmapFactory.decodeStream(imageStream);
        holder.LocationImage.setImageBitmap(image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option[] = new CharSequence[]{"EDIT NOTE","CANCEL"};
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Option");
                alertDialog.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(which==0){
                            int NoteID = noteList.get(position).getNoteID();
                            String LocationNameString = noteList.get(position).getLocationName();
                            String DescriptionString = noteList.get(position).getDescription();
                            byte[] outImage=noteList.get(position).getMyImage();

                            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                            Bitmap image = BitmapFactory.decodeStream(imageStream);
                            Intent intent = new Intent(context, EditNote.class);
                            intent.putExtra("NoteID",NoteID);
                            intent.putExtra("LocationName", LocationNameString);
                            intent.putExtra("Description", DescriptionString);
                            intent.putExtra("MyImage",outImage);
                            //Toast.makeText(context,""+LocationNameString, Toast.LENGTH_SHORT).show();
                            context.startActivity(intent);


                        }
                        if(which==1){

                        }

                    }
                });
                alertDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView LocationNameID,DescriptionID,NoteID;

        ImageView LocationImage;

        public MyViewHolder(View view){
            super(view);
            NoteID = view.findViewById(R.id.noteID);
            LocationNameID = view.findViewById(R.id.locationName2ID);
            DescriptionID = view.findViewById(R.id.description2ID);
            LocationImage = view.findViewById(R.id.locationImageID2);

        }

    }
}
