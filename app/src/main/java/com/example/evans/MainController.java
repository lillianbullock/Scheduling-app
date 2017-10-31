package com.example.evans;


import android.support.constraint.solver.Goal;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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

    private List<Appointment> _appoinments       = new LinkedList<Appoinment>();
    private List<Customer> _customers         = new LinkedList<Customer>();
    private List<Goal> _goals             = new LinkedList<Goal>();
    private List<Service> _availableServices = new LinkedList<Service>();
    private List<Sale> _allSales          = new LinkedList<Sale>();
    private List<Expense> _expenses          = new LinkedList<Expense>();


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
     * @param customer: A valid customer to be added to our list
     */
    public void addAppointment(Customer customer) {

        if (customer != null) {
            _customers.add(customer);
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
     * @return
     */
    public List<Appointment> getAppointments(TimePeriod timePeriod) {

        if (timePeriod == null) {
            return null;
        }

        List<Appointment> tempAppointments = new LinkedList<Appointment>();


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

        List<Goal> tempGoals = new LinkedList<Goal>();


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
     * @return List<Customer></Customer>
     */
    public List<Customer> getCustomers() {
        return _customers;
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


}
