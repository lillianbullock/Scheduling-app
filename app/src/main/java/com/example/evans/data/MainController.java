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
 */
public class MainController implements OnGetDataListener{

    private List<Appointment>       _appointments      = new LinkedList<>();
    private List<Customer>          _customers         = new LinkedList<>();
    private List<Goal>              _goals             = new LinkedList<>();
    private Map<String, Service>    _availableServices = new HashMap<>();
    private List<Sale>              _allSales          = new LinkedList<>();
    private List<Expense>           _expenses          = new LinkedList<>();
    private FirebaseManager         _firebaseManager   = null;

    private static final String TAG = "MainController";


    /**
     * Default constructor loads data from firebase
     */
    public MainController() {

        _firebaseManager = new FirebaseManager();
        populateData();
    }

    /**
     * Populate lists and maps with previously stored data
     * Pull from the cloud or device memory
     */
    private void populateData() {
        _availableServices = _firebaseManager.getServices(new OnGetDataListener() {
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
        /*_goals = _firebaseManager.getAllGoals();
        _expenses = _firebaseManager.getAllExpenses();
        _allSales = _firebaseManager.getAllSales();
        _appointments = _firebaseManager.getAllAppointments();
        _customers = _firebaseManager.getAllCustomers();*/

    }


    @Override
    public void onDataLoadStarted() {

    }

    @Override
    public void onDataLoadSucceed(DataSnapshot data) {

    }

    @Override
    public void onDataLoadFailed(DatabaseError databaseError) {

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

        _availableServices.put(title, service);
        _firebaseManager.addService(service, service.getId());
    }

    public List<Expense> getAllExpenses() {
        return _expenses;
    }

    public List<Customer> getAllCustomers() {
        return _customers;
    }

    public List<Appointment> getAllAppointments() {
        return _appointments;
    }

    public List<Goal> getAllGoals(){
        return _goals;
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

        // get the id from the database manager. It's auto-generated with
        // the server's timestamp for multi-user support
        customer.setId(_firebaseManager.getKeyForNewCustomer());

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
     * Return a list of all the customers in the customers list
     * @return List<Customer>
     */
    public List<Customer> getCustomers() {
        return _customers;
    }


    /**
     * Return all the appointments for a particular customer
     * @param customer: The customer that we're looking up appointments for
     * @return A list of appointments for the customer
     */
    public List<Appointment> getAppointmentsForCustomer(Customer customer) {

        return _firebaseManager.getAppointmentsForCustomer(customer);
    }

    /**
     * Return the first customer that matches the id.
     * Return null if nothing was found
     * @return Customer
     */
    public Customer getCustomerById(String id) {
        return _firebaseManager.getCustomerWithId(id);
    }

    /**
     * Return the first customer that matches the name. Return null
     * if nothing was found
     * @param name: customer name
     * @return Customer
     */
    public Customer getCustomerByName(String name) {
        return _firebaseManager.getCustomerWithName(name);
    }


    /**
     * Return a list of goals that are due by a certain date
     * @param date: The month we're looking at
     * @return A list of goals
     */
    public List<Goal> getGoalsByDueDate(LocalDate date) {
        return _firebaseManager.getGoalsWithEndDateBetween(date, date);
    }


    /**
     * This will return a number of goals
     * @param numGoals number of goal we want
     * @return Goals are returns with a numLimit
     */
    public List getGoalsWithLimit(int numGoals){
        return  _firebaseManager.getGoalsWithLimit(numGoals);
    }



    /**
     * Return a list of customers that were added in the last month. This method may be
     * changed later to accept a parameter that specifies the duration to check for
     * @return List of customers
     */
    public List<Customer> getNewCustomers() {
        LocalDate today = LocalDate.now();

        LocalDate lastMonth = new LocalDate(today.getYear(), today.getMonthOfYear() -1, today.getDayOfMonth());

        return _firebaseManager.getCustomersAddedBetweenDates(lastMonth, today);
    }

    /**
     * Return a list of all the services in the data list.
     * This is a list of the the kind of services that the company offers
     * @return LinkedList<Service>
     */
    public Map<String, Service> getAvailableServices() {
        return _availableServices;
    }


    /**
     * Return a list of all the sales in the data collection.
     * @return LinkedList<Sale>
     */
    public List<Sale> getAllSales() {
        return _allSales;
    }


    /**
     * Return the number of customers specified in the parameter
     * @param numOfCustomers the number of customers to be returned if available
     * @return List of customers
     */
    public List<Customer> getFirstNumberCustomers(int numOfCustomers){
        return _firebaseManager.getCustomersWithLimit(numOfCustomers);
    }


    /**
     * Return the number of sales specified in the parameter
     * @param numOfSales the number of sales to be returned if available
     * @return List of sales
     */
    public List<Sale> getFirstNumberSales(int numOfSales){
        return _firebaseManager.getSalesWithLimit(numOfSales);
    }

    /**
     * Return the number of appointments specified in the parameter
     * @param numAppointments the number of appointments to be returned if available
     * @return List of appointments
     */
    public List<Appointment> getFirstNumberAppointments(int numAppointments){
        return _firebaseManager.getAppointmentWithLimit(numAppointments);
    }


    /**
     * Return the number of goals specified in the parameter
     *
     * This will return the most recent goals.
     * @param numGoals the number of goals to be returned if available
     * @return List of goals
     */
    public List<Goal> getFirstNumberGoals(int numGoals){
        return _firebaseManager.getGoalsWithLimit(numGoals);
    }


    /**
     * Return the number of expenses specified in the parameter
     *
     * This will return the most recent expenses.
     * @param numExpenses the number of expenses to be returned if available
     * @return List of expenses
     */
    public List<Expense> getFirstNumberExpenses(int numExpenses){
        return _firebaseManager.getExpensesWithLimit(numExpenses);
    }


    /*getters for financial report*/
    public List<Appointment> getAppointmentsBetween(LocalDate beginDate, LocalDate endDate) {
         return  _firebaseManager.getAppointmentsBetween(beginDate, endDate);
    }

    /*Getting Sales between a certain time*/
    public List<Sale> getSalesBetween(LocalDate beginDate, LocalDate endDate) {
        return  _firebaseManager.getSalesBetweenDates(beginDate, endDate);
    }

    /*Getting Expenses between a certain time*/
    public List<Expense> getExpensesBetween(LocalDate beginDate, LocalDate endDate) {
        return  _firebaseManager.getExpensesBetweenDates(beginDate, endDate);
    }


}
