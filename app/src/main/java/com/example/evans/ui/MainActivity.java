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

import com.example.evans.R;
import com.example.evans.data.Appointment;
import com.example.evans.data.Customer;
import com.example.evans.data.Expense;
import com.example.evans.data.Goal;
import com.example.evans.data.MainController;
import com.example.evans.data.Sale;
import com.example.evans.data.Service;
import com.example.evans.ui.DialogFragements.DatePickerFragment;
import com.example.evans.ui.DialogFragements.TimePickerFragment;
import com.example.evans.ui.EditFragments.AppointmentEditFragment;
import com.example.evans.ui.EditFragments.CustomerEditFragment;
import com.example.evans.ui.EditFragments.ExpenseEditFragment;
import com.example.evans.ui.EditFragments.GoalEditFragment;
import com.example.evans.ui.EditFragments.SaleEditFragment;
import com.example.evans.ui.EditFragments.ServiceEditFragment;
import com.example.evans.ui.ListFragments.AppointmentListFragment;
import com.example.evans.ui.ListFragments.CustomerListFragment;
import com.example.evans.ui.ListFragments.ExpenseListFragment;
import com.example.evans.ui.ListFragments.GoalListFragment;
import com.example.evans.ui.ListFragments.SaleListFragment;
import com.example.evans.ui.ListFragments.ServiceListFragment;
import com.example.evans.ui.ViewFragments.AppointmentViewFragment;
import com.example.evans.ui.ViewFragments.CustomerViewFragment;
import com.example.evans.ui.ViewFragments.GoalViewFragment;
import com.example.evans.ui.ViewFragments.SalesViewFragment;
import com.example.evans.ui.ViewFragments.ServiceViewFragment;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;
import java.util.Map;


/**
 * This is the MainActivity, it controls all of the Functions that will
 * be in the application as a whole
 * This class will implement the fragments
 */
public class MainActivity extends AppCompatActivity implements
        StartPageFragment.StartPageFragmentListener,
        CustomerEditFragment.OnSubmitCustomerEdit,
        CustomerListFragment.CustomerListFragmentListener,
        CustomerViewFragment.InteractionWithCustomerViewFragmentListener,
        ServiceEditFragment.OnSubmitServiceEdit,
        ServiceListFragment.ServiceListFragmentListener,
        ServiceViewFragment.ServiceListFragmentListener,
        GoalEditFragment.OnSubmitGoalEdit,
        FinancialReportFragment.ReportFragmentListener,
        GoalListFragment.GoalsListFragmentListener,
        AppointmentListFragment.AppointmentListFragmentListener,
        AppointmentEditFragment.OnSubmitAppointment,
        DatePickerFragment.OnDateSetListener,
        SaleListFragment.SaleListFragmentListener,
        AppointmentViewFragment.InteractionWithAppointmentViewFragmentListener,
        SaleEditFragment.OnSubmitSaleEdit,
        TimePickerFragment.OnTimeSetListener,
        GoalViewFragment.InteractionWithGoalViewFragmentListener,
        ExpenseListFragment.ExpenseListFragmentListener,
        ExpenseEditFragment.InteractionWithExpenseEditFragmentListener
    {



    // Variables
    private MainController _mainController;
    private Fragment _currentFragment;
    private DrawerLayout _drawerLayout;
    private ActionBarDrawerToggle _actionBarToggle;
    private Toolbar _toolbar;


    private static final int DEFAULTLOADNUMBER = 20;
    private static final String TAG = "MainActivity";
    private static final String APP_PREFS = "com.example.evans";
    private static final String PREF_LAST_CUS_ID = "Last Used Customer ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize class variables
        _mainController = MainController.getInstance();
        _toolbar = findViewById(R.id.app_toolbar);

        /* Initialize your toolbar and navigation drawer*/
        initializeToolbarAndNavigationDrawer();


        // load any saved data from the shared preference
        loadSharedPreference();

        // Initialize and launch the start page fragment
        _currentFragment = new StartPageFragment();
        loadCurrentFragment(false);

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
     * Load saved shared preference
     */
    private void loadSharedPreference() {

    }

    /**
     * Save light data to shared preference
     */
    private void saveSharedPreference() {

    }



    /**** EXPENSE *****/
    @Override
    public void onClickExpense(Expense expense) {
        ExpenseEditFragment _frag = new ExpenseEditFragment();
        _frag.setExistingExpense(expense);
        _currentFragment = _frag;

        loadCurrentFragment(true);
    }

    @Override
    public void onExpenseCancel() { onBackPressed(); }

        @Override
    public void onAddExpense() {
        _currentFragment = new ExpenseEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onExpenseEditFinish(Expense expense) {
        if (expense != null) {
            _mainController.addExpense(expense);
            _currentFragment = new ExpenseListFragment();
            loadCurrentFragment(false);

        } else {

            // If this ever happens then there's an error on our part. A null service should never be returned here
            _currentFragment = new StartPageFragment();
            loadCurrentFragment(false);
            Snackbar.make(findViewById(R.id.content_frame),
                    "ERROR: Invalid expense information entered, cancelling operation", Snackbar.LENGTH_LONG).show();
            Log.e(TAG, "NULL expense passed to MainActivity");
        }

    }

    /*** SALE ***/
    @Override
    public void onAddSale() {
        _currentFragment = new SaleEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onClickSale(Sale sale) {
        if (sale != null){
            SaleEditFragment frag = new SaleEditFragment();
            frag.setExistingSale(sale);
            _currentFragment = frag;
            loadCurrentFragment(true);

        } else {
            Snackbar.make(findViewById(R.id.content_frame), "ERROR: Invalid sale from mainactivity", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onSaleCancel() {
            onBackPressed();
        }


    @Override
    public void onSaleEditFinish(Sale sale) {

        if (sale != null) {
            _mainController.addSale(sale);
            _currentFragment = new SaleListFragment();
            loadCurrentFragment(false);

        } else {
            // If this ever happens then there's an error on our part. A null service should never be returned here
            _currentFragment = new StartPageFragment();
            loadCurrentFragment(false);
            Snackbar.make(findViewById(R.id.content_frame),
                    "ERROR: Invalid expense information entered, cancelling operation", Snackbar.LENGTH_LONG).show();
            Log.e(TAG, "NULL expense passed to MainActivity");
        }
        /*if(sale != null){
            _mainController.addSale(sale);

            // since w're not viewing the sale, we'll just go back to the previous fragment
            onBackPressed();
        }*/
    }




    /***** SERVICE *******/
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
    public void onServiceEditFinish(Service service) {

        if (service != null) {
            _mainController.addService(service.getTitle(), service);
            _currentFragment = new ServiceViewFragment();

            ServiceViewFragment _frag = new ServiceViewFragment();
            _frag.setService(service);
            _currentFragment = _frag;

            loadCurrentFragment(false);

        } else {

            // If this ever happens then there's an error on our part. A null service should never be return here
            _currentFragment = new StartPageFragment();
            loadCurrentFragment(false);
            Snackbar.make(findViewById(R.id.content_frame),
                    "ERROR: Invalid service information entered, cancelling operation", Snackbar.LENGTH_LONG).show();
            Log.e(TAG, "NULL service passed to MainActivity");
        }
    }

    @Override
    public void onClickService(Service service) {
        ServiceViewFragment _frag = new ServiceViewFragment();
        _frag.setService(service);
        _currentFragment = _frag;
        loadCurrentFragment(true);
    }

    @Override
    public void onServiceCancel() { onBackPressed(); }



    @Override
    public void onEditService(Service service) {
        if (service != null){
            ServiceEditFragment frag = new ServiceEditFragment();
            frag.setExistingService(service);
            _currentFragment = frag;
            loadCurrentFragment(true);

        } else {
            Snackbar.make(findViewById(R.id.content_frame), "ERROR: Invalid customer from mainactivity", Snackbar.LENGTH_LONG).show();

        }

    }

    @Override
    public void viewWithService(Service service) {

    }



    /******** CUSTOMER **********/
    @Override
    public void onEditCustomer(Customer customer) {

        if (customer != null){
            CustomerEditFragment frag = new CustomerEditFragment();
            frag.setExistingCustomer(customer);
            _currentFragment = frag;
            loadCurrentFragment(true);

        } else {
            Snackbar.make(findViewById(R.id.content_frame), "ERROR: Invalid customer from mainActivity", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClickCustomer(Customer customer) {
            CustomerViewFragment _frag = new CustomerViewFragment();
            _frag.setCustomer(customer);
            _currentFragment = _frag;

            loadCurrentFragment(true);
    }

    @Override
    public void onCancelCustomerEdit() { onBackPressed(); }

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

        // addCustomer returns the same customer but with a valid id
        customer = _mainController.addCustomer(customer);

        CustomerViewFragment _frag = new CustomerViewFragment();
        _frag.setCustomer(customer);
        _currentFragment = _frag;

        loadCurrentFragment(false);

    }


    @Override
    public void onAddCustomer() {
        _currentFragment = new CustomerEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public Customer getViewCustomer() {
            return null;
        }



    /******** GOAL *******/
    @Override
    public void onClickAddGoal() {
        _currentFragment = new GoalEditFragment();
        loadCurrentFragment(true);
    }

    @Override
    public void onEditGoal(Goal goal) {
        if (goal != null){
            GoalEditFragment frag = new GoalEditFragment();
            frag.setExistingGoal(goal);
            _currentFragment = frag;
            loadCurrentFragment(true);

        } else {
            Snackbar.make(findViewById(R.id.content_frame), "ERROR: Invalid Goal from mainActivity", Snackbar.LENGTH_LONG).show();

        }
    }

    @Override
    public void onSaveGoal(Goal goal) {
        if (goal != null){
            GoalListFragment frag = new GoalListFragment();
            _currentFragment = frag;
            loadCurrentFragment(true);

        } else {
            Snackbar.make(findViewById(R.id.content_frame), "ERROR: Invalid Goal from main activity",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGoalEditCancel() {
        onBackPressed();
    }

    @Override
    public void onGoalEditFinish(Goal goal) {
        if (goal == null) {
            return;
        }

        //Connecting thee GoalViewFragment on finish
        GoalViewFragment _frag = new GoalViewFragment();
        _frag.setGoal(goal);
        _currentFragment = _frag;

        loadCurrentFragment(false);
        _mainController.addNewGoal(goal);
    }


    @Override
    public void viewWithGoal(Goal goal) {
        GoalViewFragment _frag = new GoalViewFragment();
        _frag.setGoal(goal);
        _currentFragment = _frag;

        loadCurrentFragment(true);

    }

        /******* APPOINTMENT ******/
    @Override
    public void onClickAppointment(Appointment appointment) {
        AppointmentViewFragment _frag = new AppointmentViewFragment();
        _frag.setAppointment(appointment);
        _currentFragment = _frag;
        loadCurrentFragment(false);
    }

    @Override
    public void onEditAppointment(Appointment appointment) {

        if (appointment != null){
            AppointmentEditFragment frag = new AppointmentEditFragment();
                frag.setExistingAppointment(appointment);
                _currentFragment = frag;
                loadCurrentFragment(true);

            } else {
                Snackbar.make(findViewById(R.id.content_frame), "ERROR: Invalid Appointment from mainActivity",
                        Snackbar.LENGTH_LONG).show();

            }
        }

    @Override
    public void onAppointmentEditCancel() { onBackPressed(); }

    @Override
    public void onAddAppointment() {
        _currentFragment = new AppointmentEditFragment();
        loadCurrentFragment(true);
    }


    @Override
    public void onAppointmentEditFinish(Customer customer, Appointment appointment) {

        if (appointment == null || customer == null) {
            return;
        }
        AppointmentViewFragment _frag = new AppointmentViewFragment();
        _frag.setRelatedCustomer(customer);
        _frag.setAppointment(appointment);
        _currentFragment = _frag;
        loadCurrentFragment(false);
        _mainController.addAppointment(appointment);
    }


    @Override
    public void onSetAppointmentForCustomer(Customer customer) {

        AppointmentEditFragment appointmentEditFragment = new AppointmentEditFragment();
        appointmentEditFragment.setCustomer(customer);

        _currentFragment = appointmentEditFragment;
        loadCurrentFragment(true);
    }

    @Override
    public void hideActionbar() {
        getSupportActionBar().hide();
    }

    @Override
    public void showActionbar() {
            getSupportActionBar().show();
        }


        /************************* StartPage Fragment ************************************/
    @Override
    public void onClickGoalsSeeMore() {
        GoalListFragment goalListFragment = new GoalListFragment();

        _currentFragment = goalListFragment;
        loadCurrentFragment(false);

    }

    @Override
    public void onClickAppointmentsSeeMore() {
        _currentFragment = new AppointmentListFragment();
        loadCurrentFragment(false);
    }

    @Override
    public void onClickGoal(Goal goal) {

    }

    @Override
    public void setAppbarTitle(String title){
        _toolbar.setTitle(title);
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

    void removeCurrentFragment() {
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.content_frame)).commit();
    }

    /**
     * Helper method to initialize the app bar and setup the navigation drawer. I'm only
     * trying to reduce how much code we have in onCreate
     */
    private void initializeToolbarAndNavigationDrawer() {

        // Set the app toolbar programmatically
        _toolbar.setTitle(R.string.app_name);
        _toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccentText));
        setSupportActionBar(_toolbar);

        // Initialize the navigation view (nav bar) and set a click listener for its menu items
        NavigationView navigationView = findViewById(R.id.main_nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                onNavDrawerItemClicked(item);
                return true;
            }
        });


        // More initialization
        _drawerLayout = findViewById(R.id.drawer_layout);

        // This will add the hamburger icon to the left of the screen and allow for toggling
        _actionBarToggle = new ActionBarDrawerToggle(MainActivity.this,
                _drawerLayout,
                R.string.open,
                R.string.close);

        // change the hamburger icon color to the text color
        _actionBarToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccentText));
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

            case R.id.menu_item_home:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new StartPageFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_customers:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new CustomerListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_goals:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new GoalListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_appointments:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new AppointmentListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_service:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new ServiceListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_sales:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new SaleListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_expense:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new ExpenseListFragment();
                loadCurrentFragment(true);
                break;
            case R.id.menu_item_fin_rep:
                _drawerLayout.closeDrawer(GravityCompat.START);
                _currentFragment = new FinancialReportFragment();
                loadCurrentFragment(true);
                break;
            case R.id.exit_app:
                finish();
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

        if (_drawerLayout.isDrawerOpen(GravityCompat.START)
                && getFragmentManager().getBackStackEntryCount() > 0){
            _drawerLayout.closeDrawer(GravityCompat.START);
        }

        // I'm pretty sure we don't want to pop off an empty backStack!
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            removeCurrentFragment();
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onDateSet(LocalDate date) {
        Snackbar.make(findViewById(R.id.content_frame),
                "SET DATE CALLED IN PARENT ACTIVITY", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(LocalTime time) {

    }
}
