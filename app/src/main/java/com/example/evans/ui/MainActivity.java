    package com.example.evans.ui;


import android.app.Fragment;
import android.app.FragmentManager;;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.evans.data.Customer;
import com.example.evans.data.MainController;


public class MainActivity extends AppCompatActivity implements
        CustomerEditFragment.OnSubmitCustomerEdit,
        CustomersListFragment.InteractionWithCustomerFragmentListener {

    // Variables
    private MainController _mainController;
    private Fragment _currentFragment;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _actionBarToggle;

    static int LAST_ASSIGNED_CUSTOMER_ID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* Initialize your layout and variables */
       initializeToolbarAndNavigationDrawer();


        // Initialize and launch the start page fragment
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(false);
        LAST_ASSIGNED_CUSTOMER_ID = getLastAssignedCustomerId();

    }

    /**
     * Not sure yet how we want to implement this feature
     * there're a few ways but it should return the last id that was
     * assigned to the last created customer
     * @return
     */
    private int getLastAssignedCustomerId() {
        //TODO change the implementation soon!
        return 1;
    }

    /**
     * Making this static so anyone can use it. This should simply return the
     * next possible number to use as a customer id for a new customer
     * @return
     */
    static public int getNextCustomerId() {
        // We can work with this for now
        return LAST_ASSIGNED_CUSTOMER_ID + 1;
    }

    @Override
    public void onCustomerEditFinish(Customer customer) {
         // TODO Handle this case

        // Return to the main page for now
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(false);

        String name = customer.getName();
        String email = customer.getEmail();
        String phone = customer.getPhone();
        String date = customer.getDateAdded().toString();

        Toast.makeText(this, "Customer create \n"
                + "Name: " + name
                + "\nEmail: " + email
                + "\nPhone: " + phone
                + "\nDate Added : " + date,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddAppointmentClick(Customer customer) {
        // TODO Handle this case
    }

    @Override
    public void onClickCustomer(Customer customer) {
        // TODO Handle customer click
    }

    @Override
    public void onAddCustomer() {
        _currentFragment = new CustomerEditFragment();
        loadCurrentFragment(true);

    }

    /**
     * Helper method to load the current fragment. I figured we were loading frgments
     * enough that we could use a helper method to avoid code bloat.
     * Add it to the back stack if addToBackStack is true
     */
    private void loadCurrentFragment(boolean addToBackStack) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_frame, _currentFragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    /**
     * Helper method to initialize the app bar and setup the navigation drawer. I'm only
     * trying to reduce how much code we have in onCreate
     */
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


        // Close the navigation drawer and create and instance of the appropriate
        // fragment then load it
        switch (menuItem.getItemId()) {

            case R.id.menu_item_customers:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new CustomersListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_goals:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new GoalListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_appointments:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new AppointmentsListFragment();
                loadCurrentFragment(true);
                break;
            default:
                _drawerLayout.closeDrawer(GravityCompat.START);
        }

    }


    /**
     * This function has one job only. TO handle when the user clicks the hamburger icon. To open and
     * close the navigation drawer as needed
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

    /**
     * Override onBackPress so that we can go back to the previous fragment if we added it
     * to the back stack.
     */
    @Override
    public void onBackPressed() {
        // I'm pretty sure we don't want to pop off an empty backStack!
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
