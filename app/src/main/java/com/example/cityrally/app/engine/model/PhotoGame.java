package com.example.cityrally.app.engine.model;


import android.content.Intent;
import android.provider.MediaStore;
import com.example.cityrally.app.engine.controller.Controller;

public class PhotoGame extends Game{
    public static final int PHOTO_CODE = 1;

    @Override
    public void onStart() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Controller.getActivity().getPackageManager()) != null) {
            Controller.getActivity().startActivityForResult(takePictureIntent, PHOTO_CODE);
        }
    }
}
