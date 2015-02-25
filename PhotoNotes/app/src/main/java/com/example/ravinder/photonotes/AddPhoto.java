package com.example.ravinder.photonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddPhoto extends ActionBarActivity {



    SQLiteDatabase db;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ArrayList<String> captions = new ArrayList<String>();

    public ArrayList<String> getArrayList(){

        return captions;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        Button saveButton = (Button)(findViewById(R.id.saveButton));
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText = (EditText)(findViewById(R.id.caption1));
                String caption = editText.getText().toString();
                insertIntoDB(caption,mCurrentPhotoPath);

                getData();

                Intent myIntent = new Intent(AddPhoto.this,ListActivity.class);
                startActivity(myIntent);
            }
        });

        Button takePhotos = (Button)(findViewById(R.id.takePhoto));
        takePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.uninstall) {
            Uri packageURI = Uri.parse("package:com.example.ravinder.photonotes");
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);
        }

        return super.onOptionsItemSelected(item);
    }





    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return image;
    }



   private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);


            }
        }
    }

    public void insertIntoDB(String caption, String path){


        // SQLiteDatabase
        db = new PhotoDbHelper(this).getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(PhotoDbHelper.CAPTION_COLUMN, caption);

        newValues.put(PhotoDbHelper.FILE_PATH_COLUMN, path);
        db.insert(PhotoDbHelper.DATABASE_TABLE, null, newValues);
    }


    public void getData(){

        String where = null;
        String whereArgs[] = null;
        String groupBy = null;
        String having = null;
        String order = null;
        String[] resultColumns = {PhotoDbHelper.ID_COLUMN, PhotoDbHelper.CAPTION_COLUMN,PhotoDbHelper.FILE_PATH_COLUMN};
        Cursor cursor = db.query(PhotoDbHelper.DATABASE_TABLE, resultColumns, where, whereArgs, groupBy, having, order);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String caption = cursor.getString(1);
            String filepath = cursor.getString(2);
          //Log.d("PhotoNotes", String.format("%s,%s,%s", id, caption,filepath));
        }

        while(cursor.moveToNext()){

            String cap = cursor.getString(1);
            captions.add(cap);
        }
 }

}






























