package fr.esiea.mobile.attrackpark;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Element from the main activity, instanciate in onCreate method
    private Button searchButton;
    private Button locateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Instanciate the searchButton from the main activity
        searchButton = (Button) findViewById(R.id.search_button_main);
        searchButton.setOnClickListener(this);

        // Instanciate the locateButton from the main activity
        locateButton = (Button) findViewById(R.id.location_button_main);
        locateButton.setOnClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Click sur Options", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Behavior when clicking a button in the main activity
    @Override
    public void onClick(View v) {

        if (searchButton.getId() == v.getId()){
            // Behavior when the search button is clicked
            Log.d("Button", "Click on searchButton from MainActivity");
            // Launch the ParkActivity
            Intent nextActivity = new Intent(this, ParkActivity.class);
            startActivity(nextActivity);
        } else if (locateButton.getId() == v.getId()){
            // Behavior whent the locate button is clicked
            Log.d("Button", "Click on locateButton from MainActivity");
            // Launch the MapsActivity
            Intent nextActivity = new Intent(this, MapsActivity.class);
            startActivity(nextActivity);
        } else {
            Log.d("Button", "Click on MainActivity's button " + v.getId() + " not implemented");
        }
    }
}
