    package com.example.evans;


import android.app.FragmentManager;;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


    public class MainActivity extends AppCompatActivity {
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _actionBarToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize the drawer layout
        _drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        // Initialize the toggle and give it to the navigation drawer
        _actionBarToggle = new ActionBarDrawerToggle(this, _drawerLayout, R.string.open, R.string.close);
        _drawerLayout.addDrawerListener(_actionBarToggle);

        // sync the state of the drawer indicator (open,close) with the linked
        // drawer layout
        _actionBarToggle.syncState();

        // Get the action bar and give it the hamburger and back button at the top
        // The app will crash on this method if we don't have an appbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Initialize the navigation view (nav bar) and set a click listener for its menu items
        NavigationView navigationView = (NavigationView) findViewById(R.id.main_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavDrawerItemClicked(item);
                return true;
            }
        });


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
                fragmentManager.beginTransaction().replace(R.id.content_frame, new CustomersViewFragment()).commit();
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
