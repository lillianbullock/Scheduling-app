package com.example.evans.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.evans.data.Sale;
import com.example.evans.data.Service;
import com.example.evans.data.TimePeriod;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;


/**
 * This is the MainActivity, it controls all of the Functions that will
 * be in the application as a whole
 * This class will implement the fragments
 */
public class MainActivity extends AppCompatActivity implements
        CustomerEditFragment.OnSubmitCustomerEdit,
        CustomersListFragment.InteractionWithCustomerListFragmentListener,
        ServiceEditFragment.OnSubmitServiceEdit,
        ServiceListFragment.InteractionWithServiceListFragmentListener,
        GoalEditFragment.OnSubmitGoalEdit,
        GoalListFragment.InteractionWithGoalsListFragmentListener,
        AppointmentsListFragment.InteractionWithAppointmentListFragmentListener,
        AppointmentEditFragment.OnSubmitAppointment,
        DatePickerFragment.OnDateSetListener,
        CustomerViewFragment.InteractionWithCustomerViewFragmentListener,
        SalesListFragment.SalesListFragmentListener,
        AppointmentViewFragment.InteractionWithAppointmentViewFragmentListener,
        SalesEditFragment.OnSubmitSalesEdit
    {

    // Variables
    private MainController _mainController;
    private Fragment _currentFragment;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _actionBarToggle;

    private static final String DATABASE_CUSTOMER_REF = "Customers";
    private static final String TAG = "MainActivity";
    private static final String APP_PREFS = "com.example.evans";
    private static final String PREF_LAST_CUS_ID = "Last Used Customer ID";

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

        // load any saved data from the shared preference
        loadSharedPreference();

        // Initialize and launch the start page fragment
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(false);
        LAST_ASSIGNED_CUSTOMER_ID = getLastAssignedCustomerId();

    }

    /**
     * Save our last assigned customer id
     */
    @Override
    protected void onStop() {
        super.onStop();

        saveSharedPreference();
    }

    /**
     * Load LAST_ASSIGNED_CUSTOMER_ID from shared preferences. This is the only thing that we're
     * loading for now
     */
    private void loadSharedPreference() {

        SharedPreferences prefs = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);

        int loadedId = 0;


        // Make sure that it's valid before attempting to load data from prefs
        // NOTE that if the value is not found, we'll get -1 back
        if (prefs != null) {
            loadedId = prefs.getInt(PREF_LAST_CUS_ID, -1);
        }

        // Could not find the value
        if (loadedId == -1) {
            loadedId = 0;
            Log.w(TAG, "Unable to read last assigned user id from sharedPreference");
        } else {
            Log.i(TAG, "Loaded id = " + loadedId);
        }

        LAST_ASSIGNED_CUSTOMER_ID = loadedId;

    }

    /**
     * Save LAST_ASSIGNED_CUSTOMER_ID from shared preferences. This is the only thing that we're
     * saving for now
     */
    private void saveSharedPreference() {
        SharedPreferences prefs = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(PREF_LAST_CUS_ID, LAST_ASSIGNED_CUSTOMER_ID);
        editor.apply();
        Log.i(TAG, "Saved last customer id to shared preference");
    }

       /**
     * Not sure yet how we want to implement this feature
     * there're a few ways but it should return the last id that was
     * assigned to the last created customer
     * @return
     */
    private int getLastAssignedCustomerId() {
        //TODO change the implementation soon!
        return LAST_ASSIGNED_CUSTOMER_ID;
    }


    /** IMPLEMENT METHODS for all the fragments that this activity will use */

    @Override
    public void onAddCustomer() {
        _currentFragment = new CustomerEditFragment();
        loadCurrentFragment(true);

    }

    @Override
    public void onAddAppointment() {
        _currentFragment = new AppointmentEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onAddService() {
        _currentFragment = new ServiceEditFragment();
        loadCurrentFragment(true);
    }


    @Override
    public Map<String, Service> getServices () {

        return _mainController.getAvailableServices();
    }


    @Override
    public Customer getViewCustomer() {
        return null;
    }

    @Override
    public List<Customer> getCustomerList() { return _mainController.getCustomers(); }

    @Override
    public List<Appointment> getAppointmentList() { return _mainController.getAppointments(TimePeriod.Week); }


    @Override
    public List<Goal> getGoal() {
        //TODO Figure out if we need more than one function for week day or year goals
        return _mainController.getGoals(TimePeriod.Week);
    }


    /**
     * Sales implementations
     */
    @Override
    public List<Sale> getSale() { return _mainController.getAllSales(); }

    @Override
    public void onAddSale() {
        _currentFragment = new SalesEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onClickSale(Sale sale) {

    }
    @Override
    public void onSaleCancel() {
        onBackPressed();
    }

    @Override
    public void onSaleEditFinish(Sale sale) {

        if(sale != null){
            _mainController.addSale(sale);
            _currentFragment = new StartPageFragment();
            loadCurrentFragment(false);
        }

    }

    @Override
    public void onServiceEditFinish(Service service) {

        if (service != null) {
            _mainController.addService(service.getTitle(), service);

            _currentFragment = new ServiceListFragment();
            loadCurrentFragment(false);

            String title = service.getTitle();
            String description = service.getDescription();
            Double price = service.getPrice();

        } else {

            // If this ever happens then there's an error on our part. A null service should never be return here
            _currentFragment = new StartPageFragment();
            loadCurrentFragment(false);
            Snackbar.make(findViewById(R.id.content_frame),
                    "ERROR: Invalid service information enterred, cancelling operation", Snackbar.LENGTH_LONG).show();
            Log.e(TAG, "NULL service passed to MainActivity");
        }


    }


    @Override
    public void onCustomerEditFinish(Customer customer) {

        if (customer == null) {
            Snackbar.make(findViewById(R.id.content_frame),
                    "ERROR: Invalid customer. Operation aborted",
                    Snackbar.LENGTH_LONG)
                    .show();

            _currentFragment = new CustomerViewFragment();

            loadCurrentFragment(false);

            return;
        }

        // A valid customer was created so increase the value of LAST_ASSIGNED_CUSTOMER_ID
        LAST_ASSIGNED_CUSTOMER_ID += 1;


         CustomerViewFragment _frag = new CustomerViewFragment();
        _frag.setCustomer(customer);
        _currentFragment = _frag;

        loadCurrentFragment(false);

        // TODO Add the new customer to the database for now. We'll need to coordinate with Main controller to sync properly
        _database.child(DATABASE_CUSTOMER_REF).child(String.valueOf(customer.getId())).setValue(customer);

        _mainController.addCustomer(customer);

    }

    /**
     * Return the next possible number to use as a customer id for a new customer
     * @return
     */
    @Override
    public int getNextCustomerId() {
        // We can work with this for now
        return LAST_ASSIGNED_CUSTOMER_ID + 1;
    }


    @Override
    public void onAddAppointmentClickForCustomer(Customer customer) {
        // TODO Handle this case
    }


    /**
     * GOAL method Implementation
     */
    @Override
    public void onClickAddGoal() {
        _currentFragment = new GoalEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onClickCustomer(Customer customer) {
        // TODO Handle customer click
    }


    @Override
    public void onClickGoal() {
        // TODO Implement
    }

    @Override
    public void onGoalCancel() {
        onBackPressed();
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
    public void onClickAppointment(Appointment appointment) {
        //TODO Handle CLick appointment
    }

    @Override
    public Customer getCustomerForAppointment() {
        return null;
    }


    @Override
    public void onAppointmentEditFinish(Customer customer, Appointment appointment) {

        if (appointment == null || customer == null) {
            return;
        }



        // Check if the customer was created in the appointment view. The id would be set to null if it was
        if (customer.getId() == null){
            // check if we have a customer like that already if not add it
            if (_mainController.getCustomerByName(customer.getName()) == null) {
                customer.setId(String.valueOf(getNextCustomerId()));
                _mainController.addCustomer(customer);
            }
        }

        // set the appointment's customerId so we can keep track of which customer had the appointment
        appointment.setCustomerId(customer.getId());


        AppointmentViewFragment _frag = new AppointmentViewFragment();
        _frag.setRelatedCustomer(customer);
        _frag.setAppointment(appointment);
        _currentFragment = _frag;

        loadCurrentFragment(false);

        _mainController.addAppointment(appointment);
    }

    @Override
    public void onCancel() {
        onBackPressed();

    }

    @Override
    public void hideActionbar() {
        getSupportActionBar().hide();
    }

    @Override
    public void showActionbar() {
            getSupportActionBar().show();
        }

    /********************END OF OVERRIDING METHODS FOR FRAGMENTS****************************/

    /**
     * Load the current fragment.
     * I figured we were loading fragments
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
            case R.id.menu_item_sales:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new SalesListFragment();
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

    @Override
    public void onDateSet(LocalDate date) {
        Snackbar.make(findViewById(R.id.content_frame), "SET DATE CALLED IN PARENT ACTIVITY", Snackbar.LENGTH_LONG).show();

    }

        @Override
        public void setDate(LocalDate date) {

        }
    }
