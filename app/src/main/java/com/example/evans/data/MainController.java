package com.example.evans.data;


import android.support.constraint.solver.Goal;

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

    private List<Appointment> _appoinments       = new LinkedList<>();
    private List<Customer>    _customers         = new LinkedList<>();
    private List<Goal>        _goals             = new LinkedList<>();
    private List<Service>     _availableServices = new LinkedList<>();
    private List<Sale>        _allSales          = new LinkedList<>();
    private List<Expense>     _expenses          = new LinkedList<>();


    /**
     * The constructor will do nothing for now. As we progress we'll change it so that it loads data
     * from the database and from the phone and populate the data for the app session
     * TODO: Implement data loading here
     */
    MainController() {

    }


    /**
     * Add a single appointment to our list of appointments. The appointment should already
     * be in a valid state before this function is called.
     * @param appointment: A valid appoinment to be added to our list
     */
    public void addAppointment(Appointment appointment) {

        if (appointment != null) {
            _appoinments.add(appointment);
        }

        // TODO Update the user interface here so that the new appointment shows up in the
        // ListView. This can probably be done by simple addding it to the arrayAdapter

        // TODO: Consider notifying the user somehow, maybe by displaying a toast


    }


    /**
     * Add an already created customer to our list of customers
     * Note that the customer class should be used to create the customer before
     * calling this method.
     * @param customer: A valid customer to be added to our list
     */
    public void addCustomer(Customer customer) {

        if (customer != null) {
            _customers.add(customer);
        }


        // TODO Update the user interface here so that the new customer shows up in the
        // ListView. This can probably be done by simple addding it to the arrayAdapter


        // TODO: Consider notifying the user somehow, maybe by displaying a toast

    }


    /**
     * AddSale: Add a single sale to our list of sales. Note that the Sale class should be
     * used to create the sale then the already created sale should be passed to this
     * method.
     * @param sale: The sale to be added to our sales list
     */
    public void addSale(Sale sale) {

        if (sale != null) {
            _allSales.add(sale);
        }

        // TODO: Add the new sale to the arrayAdapter for the sales list view so that the user
        // will be able to see the update.

        // TODO: Consider notifying the user somehow, maybe by displaying a toast

    }


    /**
     * Add a single goal to our list of goals. Again the goal at this point should be in
     * a valid state.
     * @param goal: The goal to be added
     */
    public void addNewGoal(Goal goal) {

        if (goal != null) {
            _goals.add(goal);
        }

        // TODO: Add the new sale to the arrayAdapter for the sales list view so that the user
        // will be able to see the update.

        // TODO: Consider notifying the user somehow, maybe by displaying a toast

    }


    /**
     * Add a valid expense to our list of expenses. Again the list item here should be in
     * a valid state.
     * @param expense: The expense item to be added to our list.
     */
    public void addExpense(Expense expense) {

        if (expense != null) {
            _expenses.add(expense);
        }

        // TODO: Add the new sale to the arrayAdapter for the sales list view so that the user
        // will be able to see the update.

        // TODO: Consider notifying the user somehow, maybe by displaying a toast

    }


    /**
     * GetAppointments: This method returns a list of appointments based on the time period specified in
     * the TimePeriod that is received as a parameter.
     * @param timePeriod: Used to determine what date range to return appointments for
     * @return list of appointments for that time period
     */
    public List<Appointment> getAppointments(TimePeriod timePeriod) {

        if (timePeriod == null) {
            return null;
        }

        List<Appointment> tempAppointments = new LinkedList<>();


        switch (timePeriod){
            case Day:
                for (Appointment appointment : _appoinments){
                    // TODO Compare the date on each appointment against the current day on the phone and
                    // add matching ones to our appoinment list
                }
                break;

            case Week:
                for (Appointment appointment : _appoinments){
                    // TODO Compare the date on each appointment against the current week on the phone and
                    // add matching ones to our appoinment list
                }
                break;

            case Month:
                for (Appointment appointment : _appoinments){
                    // TODO Compare the date on each appointment against the current month on the phone and
                    // add matching ones to our appoinment list
                }
                break;

            case Year:
                for (Appointment appointment : _appoinments){
                    // TODO Compare the date on each appointment against the current year on the phone and
                    // add matching ones to our appointment list
                }
                break;

        }

        return tempAppointments;

    }



    /**
     * GetGoals: This method returns a list of goals based on the time period specified in
     * the TimePeriod that is received as a parameter.
     * @param timePeriod: Used to determine what date range to return goals for
     * @return LinkedList
     */
    public List<Goal> getGoals(TimePeriod timePeriod) {

        if (timePeriod == null) {
            return null;
        }

        List<Goal> tempGoals = new LinkedList<>();


        switch (timePeriod){
            case Day:
                for (Goal goal : _goals){
                    // TODO Compare the date on each goal against the current day on the phone and
                    // add matching ones to our goal list
                }
                break;

            case Week:
                for (Goal goal : _goals){
                    // TODO Compare the date on each goal against the current week on the phone and
                    // add matching ones to our goal list
                }
                break;

            case Month:
                for (Goal goal : _goals){
                    // TODO Compare the date on each goal against the current month on the phone and
                    // add matching ones to our goal list
                }
                break;

            case Year:
                for (Goal goal : _goals){
                    // TODO Compare the date on each goal against the current year on the phone and
                    // add matching ones to our goal list
                }
                break;

        }

        return tempGoals;
    }


    /**
     * Return all the appointments for a particular customer
     * @param customer: The customer that we're looking up appointments for
     * @return: A list of appointments for the customer
     */
    public List<Customer> getAppointmentsForCustomer(Customer customer) {


        return null;

    }


    /**
     * Return a list of goals that are due in a certain month
     * @param month: The month we're looking at
     * @return: A list of goals
     */
    public List<Goal> getGoalsByDueMonth(Month month) {

        return null;
    }


    /**
     * Get a specific goal by the title of the name. The use case for this is if the
     * user searches for a specific goal.
     * @param name: name of the goal
     * @return
     */
    public Goal getGoalByName(String name){

        return null;
    }




    /**
     * GetExpenses: This method returns a list of expenses based on the time period specified in
     * the TimePeriod that is received as a parameter.
     * @param timePeriod: Used to determine what date range to return it's for
     * @param timePeriod
     * @return A LinkedList<Expenses>
     */
    public List<Expense> getExpenses(TimePeriod timePeriod) {

        List<Expense> tempExpenses = new LinkedList<Expense>();


        if (timePeriod == null) {
            return null;
        }


        switch (timePeriod){
            case Day:
                for (Expense expense : _expenses){
                    // TODO Compare the date on each expense against the current day on the phone and
                    // add matching ones to our expenses list
                }
                break;

            case Week:
                for (Expense expense : _expenses){
                    // TODO Compare the date on each expense against the current week on the phone and
                    // add matching ones to our expenses list
                }
                break;

            case Month:
                for (Expense expense : _expenses){
                    // TODO Compare the date on each expense against the current month on the phone and
                    // add matching ones to our expenses list
                }
                break;

            case Year:
                for (Expense expense : _expenses){
                    // TODO Compare the date on each expense against the current year on the phone and
                    // add matching ones to our expenses list
                }
                break;

        }


        return tempExpenses;
    }


    /**
     * GetCustommers: Simply return a list of all the customers in our customers list
     * @return List<Customer>
     */
    public List<Customer> getCustomers() {
        return _customers;
    }


    /**
     * Return a list of customers that were added in the last month. This method may be
     * changed later to accept a parameter that specifies the duration to check for
     * @return List of customers
     */
    public List<Goal> getNewCustomers() {
        return null;
    }


    /**
     * GetFrequent customers
     * Return a list of customers that have visited two or more times in the past
     * @return List of Customer
     */
    public List<Customer> getFrequentCustomers() {
        return null;
    }

    /**
     * Simply return a list of all the services in our data list. Note that this is a list of the the kind
     * of services that the company offers
     * @return LinkedList<Service>
     */
    public List<Service> getAvailableServices() {
        return _availableServices;
    }

    /**
     * GetAllSales: Simply return a list of all the sales in our data collection
     * @return LinkedList<Sale>
     */
    public List<Sale> getAllSales() {
        return _allSales;
    }


    /**
     * CreateSalesReport: Return a map of the name of the sub report and a key containing its
     * value for the specified time period. Example:
     * 'Sales' 120
     * 'Sales amount' 15000
     * 'Total customers' 20
     * 'Total expenses' 200
     * @param timePeriod
     * @return
     */
    public Map<String, Double> createSalesReport(TimePeriod timePeriod) {
        return null;
    }


}