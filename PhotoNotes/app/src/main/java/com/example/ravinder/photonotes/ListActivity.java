package com.example.ravinder.photonotes;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListActivity extends ActionBarActivity {


    AddPhoto addPhoto;
    ArrayList<String> myCaptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

       myCaptions = new ArrayList<String>();
       addPhoto = new AddPhoto();
        populateListView();
        registerClickCallback();

        myCaptions.add("Sample");
        myCaptions.add("Sample");
        myCaptions.add("Sample");
        myCaptions.add("Sample");
        myCaptions.add("Sample");
        myCaptions.add("Sample");
        myCaptions.add("Sample");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            //return true;
        } else if (id == R.id.goToPhotoActivity) {

            Intent myIntent = new Intent(ListActivity.this, AddPhoto.class);
            startActivity(myIntent);
    }
         return super.onOptionsItemSelected(item);
    }

    public void populateListView(){

        ArrayAdapter<String> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        //myCaptions.add("testData");
        //myCaptions.add("testData");


    }




    public void registerClickCallback(){
        final ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                String cap = myCaptions.get(position);
                Intent myIntent = new Intent(ListActivity.this,ViewPhoto.class);

                myIntent.putExtra("cap",cap);
                myIntent.putExtra("position",position);
                startActivity(myIntent);
            }
        });
    }


    public class MyListAdapter extends ArrayAdapter<String> {
        public MyListAdapter() {
            super(ListActivity.this, R.layout.item_view, myCaptions );
        }
        @Override
        public View getView(int position,View convertView, ViewGroup parent){

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }


            TextView nameText= (TextView) itemView.findViewById(R.id.item_name);
            nameText.setText(myCaptions.get(position));
            return itemView;
        }
    }




    // static final int REQUEST_IMAGE_CAPTURE = 1;

  /*  private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }*/


  }
