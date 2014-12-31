package com.example.cityrally.app.engine.games;


import android.content.Intent;
import android.os.Parcelable;
import android.provider.MediaStore;
import com.example.cityrally.app.engine.manager.Manager;

public class PhotoGame extends Game{
    public static final int PHOTO_CODE = 1;

    @Override
    public void onStart() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Manager.activity().getPackageManager()) != null) {
            Manager.activity().startActivityForResult(takePictureIntent, PHOTO_CODE);
        }
    }
}
