package com.example.ravinder.zoo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity2 extends ActionBarActivity {

    //Display the selected animal from list view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);

        TextView heading = (TextView) findViewById(R.id.textView);
        heading.setText(MainActivity.clickedAnimal.getName());

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageResource(MainActivity.clickedAnimal.getImageId());

        TextView details = (TextView) findViewById(R.id.textView2);
        details.setText(MainActivity.clickedAnimal.getDetails());

    }
    // Same menu as for other activities
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Logic for Overflow menu in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.information) {

            Intent myIntent = new Intent(this, MainActivity3.class);
            startActivity(myIntent);
        }
        else {
            if (id == R.id.uninstall) {

                Uri packageURI = Uri.parse("package:com.example.ravinder.zoo");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

            }
        }

        return super.onOptionsItemSelected(item);
    }
}
