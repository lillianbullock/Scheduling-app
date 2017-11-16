package com.example.evans.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Customer;
import com.example.evans.data.Goal;
import com.example.evans.data.MainController;
import com.example.evans.data.Service;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Map;

public class MainActivity extends AppCompatActivity implements
        CustomerEditFragment.OnSubmitCustomerEdit,
        CustomersListFragment.InteractionWithCustomerFragmentListener,
        ServiceEditFragment.OnSubmitServiceEdit,
        ServiceListFragment.InteractionWithServiceFragmentListener,
        GoalEditFragment.OnSubmitGoalEdit,
        GoalListFragment.InteractionWithGoalsListFragmentListener,
        AppointmentsListFragment.InteractionWithAppointmentFragmentListener,
        AppointmentEditFragment.OnSubmitAppointment
    {

    // Variables
    private MainController _mainController;
    private Fragment _currentFragment;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _actionBarToggle;

    private static final String DATABASE_CUSTOMER_REF = "Customers";
    private static final String TAG = "MainActivity";

    // FireBase stuff
    private DatabaseReference _database;

    static int LAST_ASSIGNED_CUSTOMER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize your toolbar and navigation drawer*/
       initializeToolbarAndNavigationDrawer();

       // Initialize class variables
        _mainController = new MainController();
        _database = FirebaseDatabase.getInstance().getReference();

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


    /** IMPLEMENT METHODS for all the fragments that this activity will use */
    @Override
    public Map<String, Service> getServices () {

        return _mainController.getAvailableServices();
    }

    @Override
    public void hideActionbar() {
        getSupportActionBar().hide();
    }

    @Override
    public void showActionbar() {
        getSupportActionBar().show();
    }

    @Override
     public void onCustomerEditFinish(Customer customer) {

        if (customer == null) {
            Snackbar.make(findViewById(R.id.content_frame),
                    "ERROR: Invalid customer. Operation aborted",
                    Snackbar.LENGTH_LONG)
                    .show();

            _currentFragment = new StartPageFragment();
            loadCurrentFragment(false);
        }


        // Return to the main page for now
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(false);

        // TODO Add the new customer to the database for now. We'll need to coordinate with Main controller to sync properly
       _database.child(DATABASE_CUSTOMER_REF).child(String.valueOf(customer.getId())).setValue(customer);


        _mainController.addCustomer(customer);

    }

    /**
     * This should simply return the
     * next possible number to use as a customer id for a new customer
     * @return
     */
    @Override
    public int getNextCustomerId() {
        // We can work with this for now
        return LAST_ASSIGNED_CUSTOMER_ID + 1;
    }

    @Override
    public void onServiceEditFinish(Service service) {
        // TODO Handle this case

        // Return to the main page for now
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(false);

        String title = service.getTitle();
        String description = service.getDescription();
        Double price = service.getPrice();

        Toast.makeText(this, "Service create \n"
                        + "Title: " + title
                        + "\nPrice: " + price
                        + "\nDescription: " + description,
                Toast.LENGTH_SHORT).show();

        _mainController.addService(title, service);
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

    @Override
    public void onClickGoal() {
        // TODO Implement
    }

    @Override
    public void onClickAddGoal() {
        Toast.makeText(this, "Recieved instruction to create a goal", Toast.LENGTH_LONG).show();
        _currentFragment = new GoalEditFragment();
        loadCurrentFragment(true);
        // TODO Implement
    }

    @Override
    public void onGoalEditFinish(Goal goal) {
        // Return to the main page for now
        // TODO Go to goal view
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(true);

       _mainController.addNewGoal(goal);
    }

    @Override
    public void onClickService(Service service) {
        // TODO Handle service click
    }

    @Override
    public void onAddService() {
        _currentFragment = new ServiceEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onClickAppointment(Appointment appointment) {
        //TODO Handle CLick appointment
    }

    @Override
    public void onAddAppointment() {
        _currentFragment = new AppointmentEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onAppointmentEditFinish(Appointment appointment) {
        //TODO get the customer id using the customer name. If not exist, prompt user to save customer
        //TODO if customer exist then add the customer Id to the appointment.
        _mainController.addAppointment(appointment);
    }

    @Override
    public void onCancelAppointmentEdit() {
        onBackPressed();

    }

        /********************END OF OVERRIDING METHODS FOR FRAGMENTS****************************/

    /**
     * Helper method to load the current fragment. I figured we were loading fragments
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
            case R.id.menu_item_service:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new ServiceListFragment();
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
            return true;
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
