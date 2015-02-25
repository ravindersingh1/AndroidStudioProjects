package com.example.ravinder.zoo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity{

    // Arraylist for storing Animal objects
    List<Animal> animals = new ArrayList<>();
    static Animal clickedAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create animals
        populateAnimals();
        populateListView();
        registerClickCallback();
        }
    // Description for all ten animals, hardcoded.

    String alligator ="Alligators do not truely hibernate, but they do undergo periods of " +
            "dormancy in cold weather.  They are known to search out caves in the banks of " +
            "waterways and use them as dens.Females usually have a small range, while males " +
            "occupy areas of greater than two miles. ";
    String cheetah = "Cheetah comes from a Hindi word meaning \"spotted one.\"  Two groups" +
            " exist in wild populations: the family group and males.  Males, often siblings," +
            " form a group of 2 or 3; rarely 1 will live alone.  This group will live and hunt" +
            " together for life and claims a range which may overlap several female territories" +
            ".  Male territories may be as large as 300 square miles.";
    String elephant="Social unit: gregarious and roam about in herds (of 15-20) led by an " +
            "old female; Defense: tusks are used as a tool for feeding and as a weapon; " +
            "Reproduction: gestation lasts 22 months and an adult female gives birth about " +
            "every 4 years starting at about age 13.";
    String giraffe ="Giraffes tend to travel in herds that vary from as few as 3 to as many" +
            " as 15 in number.  The females tend to stick together in family groups while " +
            "they lose some of their young males to all-bachelor groups.  These herds may" +
            " separate to graze for the day, but they always stay within hearing and vision" +
            " range of each other.  A large male may consume up to 75 pounds of food " +
            "every 24 hours.  ";
    String kangaroo= "The Red Kangaroo (Macropus rufus), is the largest living marsupial. " +
            "These animals are mostly found in the dry inland Australia, including desert, " +
            "grassland, mallee, and mulga country. It is able to go with out drinking as" +
            " long as green grass is available and it adapts well to drought. Despite its " +
            "name, the Red Kangaroo is sometimes a blue-grey color, particularly the female." +
            " Even though these animals look cuddly, they are to be approached with caution";
    String koala= "Koalas are said to be very lazy, but when it comes to getting food, they" +
            " can climb 150 feet to the top of an Eucalyptus tree and leap from one to " +
            "another. These \"pouched animals\" are very quick tempered and very muscular. " +
            "The Aboriginal meaning of Koala is \"no water.\" Koalas have the ability to " +
            "drink, but they seldom do. They obtain their water through the eucalyptus " +
            "leaves. Koalas are excellent swimmers. ";
    String lizard = "Habitat: frilled lizard lives in the subhumid to semi-arid grassy " +
            "woodlands and dry sclerophyll forests. They usually eat insects and most " +
            "small invertebrates, but sometimes they do eat small mammals pieces of meat.";
    String tortoise ="Pancake tortoises are small and flat with a thin, flexible shell. " +
            "The shell is normally 6 to 7 inches long and an inch or so high. On the legs," +
            " they have bigger scales with points that project downward and outward. Usually" +
            " the shell has radiating dark lines on the carapace (upper part of the shell).";
    String wallaby = "The Yellow-Footed Rock Wallaby breeds all year long when there is " +
            "enough food.  When drought arrives and food sources are low and the female " +
            "cannot produce milk for her young, she abandons her joey (Joey means baby or" +
            " young).  If the female wallaby becomes pregnant while a joey is already in " +
            "her womb, the embryo will not develop until the joey is out of the pouch. " +
            " The periods of drought also determine the development of the new embryo.";
    String wolf = " White-tailed deer and raccoon but will eat  any small animal " +
            "available.Fast once a week, bone or muscle meat once a week.  2/3 Science" +
            " Diet Canine Maintenance, 1/3 frozen feline diet the remainder of the week";

     // creating instances of animal and adding them to arraylist
    private void populateAnimals() {
        animals.add(new Animal(R.drawable.alligator,"The American Alligator",alligator));
        animals.add(new Animal(R.drawable.cheetah,"Cheetah",cheetah));
        animals.add(new Animal(R.drawable.elephant,"Asian Elephant",elephant));
        animals.add(new Animal(R.drawable.giraffe,"Giraffe",giraffe));
        animals.add(new Animal(R.drawable.kangaroo,"Red Kangaroo",kangaroo));
        animals.add(new Animal(R.drawable.koala,"Koala",koala));
        animals.add(new Animal(R.drawable.lizard,"Frilled Lizard",lizard));
        animals.add(new Animal(R.drawable.tortoise,"Pancake Tortoise",tortoise));
        animals.add(new Animal(R.drawable.wallaby,"Yellow-footed rock Wallaby",wallaby));
        animals.add(new Animal(R.drawable.wolf, "Red Wolf",wolf));
    }
    //adaptor to the list
    private void populateListView() {
        ArrayAdapter<Animal> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
     }
    // inflating menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);

     }
    //action, after user has clicked an item on list view
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


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

    public void registerClickCallback(){
        final ListView list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

              //Implementing Alert Dialog box for last animal in list
              if(position ==9){
                   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                   builder.setMessage("This animal is very scary. Do you want to proceed");
                   builder.setCancelable(false);
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                   clickedAnimal = animals.get(9);
                   Intent myIntent = new Intent(MainActivity.this,MainActivity2.class);
                   startActivity(myIntent);

                    }
                                       });
                   builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                   dialog.cancel();
                          }
                        });
                   AlertDialog alert = builder.create();
                   alert.show();
                       }

                   else {
                        clickedAnimal = animals.get(position);
                        Intent myIntent = new Intent(MainActivity.this,MainActivity2.class);
                        myIntent.putExtra(clickedAnimal.getName(),position);
                        startActivity(myIntent);
                   }
                }
            });
    }

    private class MyListAdapter extends ArrayAdapter<Animal> {
        public MyListAdapter() {
            super(MainActivity.this, R.layout.item_view, animals);
        }

        @Override
        public View getView(int position,View convertView, ViewGroup parent){

            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            Animal currentAnimal = animals.get(position);

            // Fill the view
            ImageView imageView =(ImageView) itemView.findViewById(R.id.item_photo);
            imageView.setImageResource(currentAnimal.getImageId());

            TextView nameText= (TextView) itemView.findViewById(R.id.item_name);
            nameText.setText("" + currentAnimal.getName());
            return itemView;
        }
    }
}
