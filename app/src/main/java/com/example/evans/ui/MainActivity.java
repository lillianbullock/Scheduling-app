    package com.example.evans.ui;


import android.app.Fragment;
import android.app.FragmentManager;;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.MainController;


public class MainActivity extends AppCompatActivity {

    // Variables
    private MainController _mainController;
    private Fragment _currentFragment;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _actionBarToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Initialize your layout and variables */
       initializeToolbarAndNavigationDrawer();


        // Initialize and launch the start page fragment
        _currentFragment = new StartPageFragment();
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, _currentFragment).commit();

    }

    private void initializeToolbarAndNavigationDrawer() {

        // Set the app toolbar programmatically
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // Initialize the navigation view (nav bar) and set a click listener for its menu items
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavDrawerItemClicked(item);
                return true;
            }
        });


        // More initialization
        _drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // This will add the hamburger icon to the left of the screen and allow for toggling
        _actionBarToggle = new ActionBarDrawerToggle(MainActivity.this,
                _drawerLayout,
                R.string.open,
                R.string.close);
        _drawerLayout.addDrawerListener(_actionBarToggle);

        // sync the state of the hamburger (menu) button depending on whether the navigation drawer is
        // open or closed
        _actionBarToggle.syncState();

        // Display the hamburger icon or the back icon depending on the state of the navigation drawer
        // (open or closed). Note that this will crash the app if we don't have a valid actionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * OnNavDrawerItemClicked: Handle when the user clicks on a menu item in the
     * navigation drawer. Simply load the fragment that matches the menu item.
     * Perform any other task as needed.
     * @param menuItem: The menu item that was clicked.
     */
    public void onNavDrawerItemClicked(MenuItem menuItem) {

        Toast.makeText(this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getFragmentManager();


        // TODO: Load a new fragment depending on the menu item click
        switch (menuItem.getItemId()) {
            case R.id.menu_item_customers:
                _drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new CustomersListFragment()).commit();
                break;
            case R.id.menu_item_goals:
                _drawerLayout.closeDrawer(GravityCompat.START);
                fragmentManager.beginTransaction().replace(R.id.content_frame, new GoalListFragment()).commit();
                break;
            case R.id.menu_item_appointments:
                fragmentManager.beginTransaction().replace(R.id.content_frame, new AppointmentsViewFragment()).commit();
                _drawerLayout.closeDrawer(GravityCompat.START);
                break;
            default:
                _drawerLayout.closeDrawer(GravityCompat.START);
        }

    }


    /**
     * This function has one job only. TO handle when the user clicks the hamburger icon. To open and
     * close the navigation drawer as neeeded
     * @param menuItem: The menu item that was clicked. In this case we're really listening for clicks
     *                on the hamburger icon
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        if (_actionBarToggle.onOptionsItemSelected(menuItem)) {
            return  true;
        }

        return super.onOptionsItemSelected(menuItem);
    }


}
