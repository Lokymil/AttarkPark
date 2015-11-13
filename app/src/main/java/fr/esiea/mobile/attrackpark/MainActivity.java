package fr.esiea.mobile.attrackpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button searchButton;
    private Button locateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.search_button_main);
        searchButton.setOnClickListener(this);

        locateButton = (Button) findViewById(R.id.location_button_main);
        locateButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (searchButton.getId() == v.getId()){
            Log.d("Button", "Click on searchButton from MainActivity");
            Intent nextActivity = new Intent(this, ParkActivity.class);
            startActivity(nextActivity);
        } else if (locateButton.getId() == v.getId()){
            Log.d("Button", "Click on locateButton from MainActivity");
        } else {
            Log.d("Button", "Click on MainActivity's button " + v.getId() + " not implemented");
        }
    }
}
