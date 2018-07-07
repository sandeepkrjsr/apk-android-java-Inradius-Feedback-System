package com.bleedcode.inradius.Main;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bleedcode.inradius.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Map;

/**
 * Created by 1505560 on 06-Jul-18.
 */

public class Function_Image {

    private static String get_poster;

    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Inradius");
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Inradius");

    public static void getImage(final Context context, final ImageView image, final String path){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                get_poster = map.get(path);
                Picasso.with(context).load(get_poster).into(image);

                //Toast.makeText(context, get_poster,Toast.LENGTH_SHORT).show();
                //if (get_poster.equals("null"))
                //    Toast.makeText(context, "Hello",Toast.LENGTH_SHORT).show();
                    //image.setImageDrawable(context.getResources().getDrawable(R.drawable.img_user));*/
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void postImage(final Context context, Uri imguri, final String path) {
        if (imguri != null) {
            StorageReference filepath = storageReference.child(imguri.getLastPathSegment());
            filepath.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloaduri = taskSnapshot.getDownloadUrl();
                    databaseReference.child(path).setValue(downloaduri.toString());
                    Toast.makeText(context, "Uploaded Successfully !", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
