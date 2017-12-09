package com.example.evans.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This singleton class will hold most of the list data for the application and will
 * contain methods to create, retrieve update and delete goals, sales, services,
 * appointments, customers and expenses. This is the main data source for the user
 * interface in the application.
 *
 * License: Private - Class work
 * version: 0.0.1
 */



public class MainController {

    private volatile static MainController instance;

    private List<Appointment>       _appointments      = new LinkedList<>();
    private List<Customer>          _customers         = new LinkedList<>();
    private List<Goal>              _goals             = new LinkedList<>();
    private List<Service>           _availableServices = new LinkedList<>();
    private List<Sale>              _allSales          = new LinkedList<>();
    private List<Expense>           _expenses          = new LinkedList<>();
    private FirebaseManager         _firebaseManager   = null;

    private static final String TAG = "MainController";



    /**
     * Return an instance of MainController
     * @return an instance on MainController
     */
    public static MainController getInstance() {

        if (instance == null){

            // thread safety
            synchronized (MainController.class) {
                if (instance == null){
                    instance = new MainController();
                }
            }
        }

        return instance;
    }


    /**
     * Default constructor loads data from firebase
     */
    private MainController() {

        _firebaseManager = new FirebaseManager();
        populateServices();
        loadAllCustomers();
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
                    _availableServices.add(child.getValue(Service.class));
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
     * Add a customer to the list of customers
     * This method will also set the customer's id, and
     * return the edited customer
     * No validation is carried out in this function
     * @param customer: valid customer to be added to the list
     * @return : passed customer with set id value
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
     * AddSale: Add a sale to the list of sales.
     * No validation is carried out in this function
     * @param sale: The sale to be added to the sales list
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
     * Add a single goal to the list of goals.
     * No validation is carried out in this function
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
     * Add a valid expense to the list of expenses. Again the list item here should be in
     * a valid state.
     * @param expense: expense to be added to the list.
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
     * Add an appointment to the list of appointments.
     * No validation is carried out in this function
     * @param appointment: valid appointment to be added to the list
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
        if (service == null){
            return;
        }

        // We're using the title as the key
        service.setId(service.getTitle());

        _availableServices.add(service);
        _firebaseManager.addService(service, service.getId());
    }



    public Customer updateCustomer(Customer oldCustomer, Customer newCustomer) {
        int oldCustomerIndex = -1;//  = _customers.indexOf(oldCustomer);

        for (Customer customer: _customers){
            if (customer.getId().equals(oldCustomer.getId())){
                oldCustomerIndex = _customers.indexOf(customer);
            }
        }

        if (oldCustomerIndex < 0) {
            Log.e(TAG, "updateCustomer: couldn't find old customer");
            return null;
        }

        _customers.set(oldCustomerIndex, newCustomer);

        _firebaseManager.updateCustomer(oldCustomer, newCustomer);

        return newCustomer;
    }

    public Appointment updateAppointment(Appointment oldAppointment, Appointment newAppointment) {

        int oldAppointmentIndex = _appointments.indexOf(oldAppointment);

        if (oldAppointmentIndex < 0 || newAppointment == null) {
            return  null;
        }

        _appointments.set(oldAppointmentIndex, newAppointment);
        _firebaseManager.updateAppointment(oldAppointment, newAppointment);

        return newAppointment;

    }

    public Sale updateSale(Sale oldSale, Sale newSale) {

        int oldSaleIndex = _allSales.indexOf(oldSale);

        if (oldSaleIndex < 0 || newSale == null) {
            return  null;
        }

        _allSales.set(oldSaleIndex, newSale);
        _firebaseManager.updateSale(oldSale, newSale);

        return newSale;

    }

    public Goal updateGoal(Goal oldGoal, Goal newGoal) {
        int oldGoalIndex = _goals.indexOf(oldGoal);

        if (oldGoalIndex < 0 || newGoal == null) {
            return null;
        }

        _goals.set(oldGoalIndex, newGoal);
        _firebaseManager.updateGoal(oldGoal, newGoal);

        return newGoal;
    }

    public Service updateService(Service oldService, Service newService) {

        int oldServiceIndex = _availableServices.indexOf(oldService);

        if (oldServiceIndex < 0 || newService == null) {
            return null;
        }

        _availableServices.set(oldServiceIndex, newService);
        _firebaseManager.updateService(oldService, newService);

        return newService;
    }

    public Expense updateExpense(Expense oldExpense, Expense newExpense) {

        int oldExpenseIndex = _expenses.indexOf(oldExpense);

        if (oldExpenseIndex < 0 || newExpense == null) {
            return null;
        }

        _expenses.set(oldExpenseIndex, newExpense);
        _firebaseManager.updateExpense(oldExpense, newExpense);

        return newExpense;
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


    public Customer getCustomerById(String id) {
        for (Customer customer: _customers){
            if (customer.getId().equals(id)){
                return customer;
            }
        }

        // nothing found
        return null;
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
     * Return a map of all the services in the data list.
     *
     * This is a compatibility method that we may get rid of in a future release
     * @return Map of services
     */
    public Map<String, Service> getAvailableServices() {

        Map<String, Service> serviceMap = new TreeMap<>();

        for (Service service: _availableServices){
            serviceMap.put(service.getTitle(), service);
        }

        return serviceMap;
    }

    public List<Service> getAvailableServicesList() {
        return _availableServices;
    }


    /**
     * Return a list of all the sales in the data collection.
     * @return LinkedList<Sale>
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
