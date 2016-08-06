package com.example.cristian.everysale.BaseClasses.imagePicker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.example.cristian.everysale.BaseClasses.getFilePath.getFilePath;

/**
 * Created by Cristian on 06/08/2016.
 */
public class ImagePicker {
    //image picker
    private String imgPath=null;
    private Bitmap bitimg;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Activity activity=null;
    private String userChoosenTask;
    //image picker

    ImagePicker(Activity activityPassed){
        activity = activityPassed;
    }
    //image picker2

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Scatta foto"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Scegli dalla Galleria"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Scatta foto", "Scegli dalla Galleria",
                "Annulla" };

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Aggiungi foto!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(activity);

                if (items[item].equals("Scatta foto")) {
                    userChoosenTask = "Scatta foto";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Scegli dalla Galleria")) {
                    userChoosenTask = "Scegli dalla Galleria";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Annulla")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        activity.startActivityForResult(Intent.createChooser(intent, "Seleziona File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory()+"/"+Environment.DIRECTORY_DCIM+"/",
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bitimg=thumbnail;
        imgPath = destination.getAbsolutePath();
        //Toast.makeText(getContext(), "oncaptureimgres  " +imgPath , Toast.LENGTH_LONG).show();

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            imgPath=getFilePath(activity,data.getData());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getContext(),"ongallery  " + imgPath , Toast.LENGTH_LONG).show();

        bitimg=bm;
    }


    //end image picke2

}
