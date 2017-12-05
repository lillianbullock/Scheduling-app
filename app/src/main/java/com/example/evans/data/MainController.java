package com.example.evans.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class will hold most of the list data for the application and will contain methods to create, retrieve
 * update and delete goals, sales, services, appointments, customers and expenses. This is the main data source
 * for the user interface in the application.
 *
 * License: Private - Class work
 * version: 0.0.1
 *
 */



public class MainController {

    private List<Appointment>       _appointments      = new LinkedList<>();
    private List<Customer>          _customers         = new LinkedList<>();
    private List<Goal>              _goals             = new LinkedList<>();
    private Map<String, Service>    _availableServices = new HashMap<>();
    private List<Sale>              _allSales          = new LinkedList<>();
    private List<Expense>           _expenses          = new LinkedList<>();
    private FirebaseManager         _firebaseManager   = null;

    private static final String TAG = "MainController";


    /**
     * The constructor will do nothing for now. As we progress we'll change it so that it loads data
     * from the database and from the phone and populate the data for the app session
     */
    public MainController() {

        _firebaseManager = new FirebaseManager();
        populateServices();
        loadAllCustomers();
        _availableServices = _firebaseManager.getAllServices();
    }

    /**
     * This function will get populate our map of services with some
     * previously stored services. Pull from the cloud or device memory
     */
    private void populateServices() {
        _firebaseManager.getServices(new OnGetDataListener() {
            @Override
            public void onDataLoadStarted() { }

            @Override
            public void onDataLoadSucceed(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Service service = child.getValue(Service.class);
                    _availableServices.put(service.getTitle(), service);
                }
            }

            @Override
            public void onDataLoadFailed(DatabaseError databaseError) {
                Log.w(TAG, "unable to load services from firebase");
            }
        });

    }


    private void loadAllCustomers() {

        _firebaseManager.getAllCustomers(new OnGetDataListener() {
            @Override
            public void onDataLoadStarted() {

            }

            @Override
            public void onDataLoadSucceed(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    _customers.add(child.getValue(Customer.class));
                }
            }

            @Override
            public void onDataLoadFailed(DatabaseError databaseError) {
                Log.w(TAG, "Unable to load all customers from Firebase");
            }
        });
    }



    /**
     * Add a single appointment to our list of appointments. The appointment should already
     * be in a valid state before this function is called.
     * @param appointment: A valid appointment to be added to our list
     */
    public void addAppointment(Appointment appointment) {

        if (appointment == null) {
            return;
        }

        appointment.setId(_firebaseManager.getKeyForNewAppointment());
        _appointments.add(appointment);
        _firebaseManager.addAppointment(appointment, appointment.getId());

    }

    /**
     * Add a new service to the list of services that is offered
     */
    public void addService(String title, Service service){
        if(service == null){
            return;
        }

        // We're using the title as the key
        service.setId(service.getTitle());

        _availableServices.put(title, service);
        _firebaseManager.addService(service, service.getId());
    }

    public void getAllExpenses(OnGetDataListener onGetDataListener) {
        _firebaseManager.getAllExpenses(onGetDataListener);
    }

    public void getAllCustomers(OnGetDataListener onGetDataListener) {
        _firebaseManager.getAllCustomers(onGetDataListener);
    }

    public void getAllAppointments(OnGetDataListener onGetDataListener) {
        _firebaseManager.getAllAppointments(onGetDataListener);
    }

    public void getAllGoals(OnGetDataListener onGetDataListener){
        _firebaseManager.getAllGoals(onGetDataListener);
    }


    /**
     * Add an already created customer to our list of customers
     * Note that the customer class should be used to create the customer before
     * calling this method.
     * @param customer: A valid customer to be added to our list
     */
    public Customer addCustomer(Customer customer) {

        if (customer == null) {
            return null;
        }

        // get the id from the database manager if it wasn't already set.
        // It's auto-generated with the server's timestamp for multi-user support
        if (customer.getId().isEmpty()){
            customer.setId(_firebaseManager.getKeyForNewCustomer());
        }

        _customers.add(customer);
        _firebaseManager.addCustomer(customer, customer.getId());

        // we're returning the customer because we set the id here
        return customer;

    }


    /**
     * AddSale: Add a single sale to our list of sales. Note that the Sale class should be
     * used to create the sale then the already created sale should be passed to this
     * method.
     * @param sale: The sale to be added to our sales list
     */
    public void addSale(Sale sale) {

        if (sale == null) {
            return;
        }

        sale.setId(_firebaseManager.getKeyForNewSale());
        _allSales.add(sale);

        _firebaseManager.addSale(sale, sale.getId());

    }


    /**
     * Add a single goal to our list of goals. Again the goal at this point should be in
     * a valid state.
     * @param goal: The goal to be added
     */
    public void addNewGoal(Goal goal) {

        if (goal == null) {
            return;
        }

        goal.setId(_firebaseManager.getKeyForNewGoal());
        _goals.add(goal);

        _firebaseManager.addGoal(goal, goal.getId());

    }


    /**
     * Add a valid expense to our list of expenses. Again the list item here should be in
     * a valid state.
     * @param expense: The expense item to be added to our list.
     */
    public void addExpense(Expense expense) {

        if (expense == null) {
            return;
        }

        expense.setId(_firebaseManager.getKeyForNewExpense());
        _expenses.add(expense);
        _firebaseManager.addExpense(expense, expense.getId());
    }




    /**
     * Return all the appointments for a particular customer
     * @param customer Needed customer for appointment
     */
    public void getAppointmentsForCustomer(Customer customer, OnGetDataListener onGetDataListener) {

        _firebaseManager.getAppointmentsForCustomer(customer, onGetDataListener);
    }

    /**
     * Return the first customer that matches the id.
     * Return null if nothing was found
     */
    public void getCustomerById(String id, OnGetDataListener onGetDataListener) {
        _firebaseManager.getCustomerWithId(id, onGetDataListener);
    }


    /**
     * Return the first customer that matches the name. Return null
     * if nothing was found
     * @param name: customer name
     */
    public void getCustomerByName(String name, OnGetDataListener onGetDataListener) {
        _firebaseManager.getCustomerWithName(name, onGetDataListener);
    }


    /**
     * Return a list of goals that are due by a certain date
     * @param date: The month we're looking at
     */
    public void getGoalsByDueDate(LocalDate date, OnGetDataListener onGetDataListener) {
        _firebaseManager.getGoalsWithEndDateBetween(date, date, onGetDataListener);
    }


    /**
     * This will return a number of goals
     * @param numGoals number of goal we want
     */
    public void getGoalsWithLimit(int numGoals, OnGetDataListener onGetDataListener){
        _firebaseManager.getGoalsWithLimit(numGoals, onGetDataListener);
    }

    public Customer getCustomerWithName(String name){

        for (Customer customer: _customers){
            if (customer.getName().equals(name)){
                return customer;
            }
        }

        // didn't find any customer
        return null;
    }



    /**
     * Return a list of customers that were added in the last month. This method may be
     * changed later to accept a parameter that specifies the duration to check for
     */
    public void getNewCustomers(OnGetDataListener onGetDataListener) {
        LocalDate today = LocalDate.now();

        LocalDate lastMonth = new LocalDate(today.getYear(), today.getMonthOfYear() -1, today.getDayOfMonth());

        _firebaseManager.getCustomersAddedBetweenDates(lastMonth, today, onGetDataListener);
    }



    /**
     * Simply return a list of all the services in our data list. Note that this is a list of the the kind
     * of services that the company offers
     * @return LinkedList<Service>
     */
    public Map<String, Service> getAvailableServices() {
        return _availableServices;
    }


    /**
     * GetAllSales: Simply return a list of all the sales in our data collection
     */
    public void getAllSales(OnGetDataListener onGetDataListener) {
        _firebaseManager.getAllSales(onGetDataListener);
    }


    /**
     * Return the number of customers specified in the parameter
     * @param numOfCustomers the number of customers to be returned if available
     * @return List of customers
     */
    public void getFirstNumberCustomers(int numOfCustomers, OnGetDataListener onGetDataListener){
        _firebaseManager.getCustomersWithLimit(numOfCustomers, onGetDataListener);
    }


    /**
     * Return the number of sales specified in the parameter
     * @param numOfSales the number of sales to be returned if available
     */
    public void getFirstNumberSales(int numOfSales, OnGetDataListener onGetDataListener){
        _firebaseManager.getSalesWithLimit(numOfSales, onGetDataListener);
    }


    /**
     * Return the number of appointments specified in the parameter
     * @param numAppointments the number of appointments to be returned if available
     */
    public void getFirstNumberAppointments(int numAppointments, OnGetDataListener onGetDataListener){
        _firebaseManager.getAppointmentWithLimit(numAppointments, onGetDataListener);
    }


    /**
     * Return the number of goals specified in the parameter
     *
     * This will return the most recent goals.
     * @param numGoals the number of goals to be returned if available
     */
    public void getFirstNumberGoals(int numGoals, OnGetDataListener onGetDataListener){
        _firebaseManager.getGoalsWithLimit(numGoals, onGetDataListener);
    }


    /**
     * Return the number of expenses specified in the parameter
     *
     * This will return the most recent expenses.
     * @param numExpenses the number of expenses to be returned if available
     */
    public void getFirstNumberExpenses(int numExpenses, OnGetDataListener onGetDataListener){
        _firebaseManager.getExpensesWithLimit(numExpenses, onGetDataListener);
    }


    /*getters for financial report*/
    public void getAppointmentsBetween(LocalDate beginDate, LocalDate endDate, OnGetDataListener onGetDataListener) {

         _firebaseManager.getAppointmentsBetween(beginDate, endDate, onGetDataListener);
    }

    /*Getting Sales between a certain time*/
    public void getSalesBetween(LocalDate beginDate, LocalDate endDate, OnGetDataListener onGetDataListener) {
        _firebaseManager.getSalesBetweenDates(beginDate, endDate, onGetDataListener);
    }

    /*Getting Expenses between a certain time*/
    public void getExpensesBetween(LocalDate beginDate, LocalDate endDate, OnGetDataListener onGetDataListener) {
        _firebaseManager.getExpensesBetweenDates(beginDate, endDate, onGetDataListener);
    }

    public String getIdForNewCustomer(){
        return _firebaseManager.getKeyForNewCustomer();
    }


}
