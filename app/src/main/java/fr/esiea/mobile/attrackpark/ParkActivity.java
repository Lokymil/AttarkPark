package fr.esiea.mobile.attrackpark;

import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ParkActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener, DetailPark.OnFragmentInteractionListener {

    // Here is the behavior when activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);

        // Here is the test to adapt behavior depending on the screen's width
        // If the screen is short, the test will be true
        if (findViewById(R.id.fgt_park_container) != null){
            // For a short screen, the layout is empty and is filled with a fragment
            if (savedInstanceState != null){
                return;
            }

            // The fragment to fill layout is instanciated
            SearchFragment firstFrag = new SearchFragment();
            // The Intent's parameter are given to the fragment
            firstFrag.setArguments(getIntent().getExtras());

            // The layout is fill with the fragment here
            getFragmentManager().beginTransaction()
                    .add(R.id.fgt_park_container,firstFrag)
                    .commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_park, menu);
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
    public void onFragmentInteraction(Uri uri) {

    }

    // This method is used when an item is selected in the park list
    @Override
    public void onParkSelected(Long id) {

        if (findViewById(R.id.fgt_park_container) != null) {
            // Here is the behavior for phone screen

            // The fragment used to display informations about the selected park is instanciated
            DetailPark nextFragment = new DetailPark();
            // Park's id is given to this fragment
            Bundle args = new Bundle();
            args.putLong(DetailPark.ARG_PARK_ID, id);
            nextFragment.setArguments(args);

            // The pprevious fragment is replace by the detail fragment
            getFragmentManager().beginTransaction()
                .replace(R.id.fgt_park_container, nextFragment)
                    // The fragment replaced is added to backStack
                .addToBackStack(null)
                .commit();

        } else {
            // Here is the behavior for wide screen
            DetailPark detailPark = (DetailPark) getFragmentManager().findFragmentById(R.id.fgt_detailPark);
            detailPark.refresh(id);
        }
    }

    // This part of the code is used to manage backStack with fragment
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            // In this case there no fragment in backStack
            Log.d("Fragment", "No fragment in backStack");
            super.onBackPressed();
        } else {
            // In this case, there is at least one fragment in the backStack
            // When the back button is pressed, the fragment in backStack will be displayed
            Log.d("Fragment","Fragment in backStack");
            getFragmentManager().popBackStack();
        }
    }
}
