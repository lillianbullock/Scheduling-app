package com.example.evans.data;



import com.google.firebase.database.DatabaseReference;

import org.joda.time.LocalDate;

import java.util.Calendar;
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
    private Map<String, Service>    _availableServices = new HashMap<String, Service>();
    private List<Sale>              _allSales          = new LinkedList<>();
    private List<Expense>           _expenses          = new LinkedList<>();
    private FirebaseManager         _firebaseManager   = null;


    /**
     * The constructor will do nothing for now. As we progress we'll change it so that it loads data
     * from the database and from the phone and populate the data for the app session
     */
    public MainController() {

        _firebaseManager = new FirebaseManager();
        populateServices();
    }

    /**
     * This function will get populate our map of services with some
     * previously stored services. Pull from the cloud or device memory
     */
    private void populateServices() {
        _availableServices = _firebaseManager.getServices();

    }


    /**
     * Add a single appointment to our list of appointments. The appointment should already
     * be in a valid state before this function is called.
     * @param appointment: A valid appoinment to be added to our list
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


    /**
     * Add an already created customer to our list of customers
     * Note that the customer class should be used to create the customer before
     * calling this method.
     * @param customer: A valid customer to be added to our list
     */
    public void addCustomer(Customer customer) {

        if (customer == null) {
            return;
        }

        // get the id from the database manager. It's auto-generated with
        // the server's timestamp for multi-user support
        customer.setId(_firebaseManager.getKeyForNewCustomer());

        _customers.add(customer);
        _firebaseManager.addCustomer(customer, customer.getId());

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
     * GetCustommers: Simply return a list of all the customers in our customers list
     * @return List<Customer>
     */
    public List<Customer> getCustomers() {
        return _customers;
    }


    /**
     * Return all the appointments for a particular customer
     * @param customer: The customer that we're looking up appointments for
     * @return: A list of appointments for the customer
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
     * @return: A list of goals
     */
    public List<Goal> getGoalsByDueDate(LocalDate date) {
        return _firebaseManager.getGoalsWithEndDateBetween(date, date);
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
     * Simply return a list of all the services in our data list. Note that this is a list of the the kind
     * of services that the company offers
     * @return LinkedList<Service>
     */
    public Map<String, Service> getAvailableServices() {

        // we don't need to retrieve this data from the database since it's populated on initialization
        return _availableServices;
    }


    /**
     * GetAllSales: Simply return a list of all the sales in our data collection
     * @return LinkedList<Sale>
     */
    public List<Sale> getAllSales() {
        return _firebaseManager.getAllSales();
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
        return _firebaseManager.getAppointmnentWithLimit(numAppointments);
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

    public List<Sale> getSalesBetween(LocalDate beginDate, LocalDate endDate) {
        return  _firebaseManager.getSalesBetweenDates(beginDate, endDate);
    }

    public List<Expense> getExpensesBetween(LocalDate beginDate, LocalDate endDate) {
        return  _firebaseManager.getExpensesBetweenDates(beginDate, endDate);
    }


}
