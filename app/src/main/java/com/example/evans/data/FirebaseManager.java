package com.example.evans.data;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Manages our connection with Google Firebase. Handles create, update and delete operations.
 */

public class FirebaseManager {

    private DatabaseReference _dababaseRoot;

    private static final String TAG = "FirebaseManager";

    private static String SERVICES = "Services";
    private static String CUSTOMERS = "Customers";
    private static String APPOINTMENTS = "Appointments";
    private static String GOALS = "Goals";
    private static String SALES = "Sales";
    private static String EXPENSES = "Expenses";


    public FirebaseManager() {

        // initialize our connection to firebase
        _dababaseRoot = FirebaseDatabase.getInstance().getReference();
    }


    /* Getters start here */

    /**
     * Get a list of all customers in the database
     * @return
     */
    public List<Customer> getAllCustomers() {

        return null;
    }

    /**
     * Retrun the first customer found with the id
     * @param customerId the customer id that we're looking up
     * @return a customer with the id, null otherwise
     */
    public Customer getCustomerWithId(String customerId) {
        return null;
    }

    /**
     * Return the first customer found with the given name
     * @param customerName the name of the customer to look up
     * @return a customer with the name, null if no matching customer exist
     */
    public Customer getCustomerWithName(String customerName) {
        return null;
    }


    /**
     * Return a customer that is associated with a specific appointment
     * @param appointment the appointment that we're looking up the customer for
     * @return customer matching the customerId in the appoinment
     */
    public Customer getCustomerForAppointment(Appointment appointment) {
        return null;
    }


    /**
     * Return a list of customers limited to the specified number
     * @return
     * @param numCustomers the number of customers to retrieve
     */
    public List<Customer> getFirstWithLimit(int numCustomers) {

        return null;

    }

    /**
     * Get all the services in the database
     * @return List of Services
     */
    public Map<String, Service> getServices() {

        final Map<String, Service> serviceMap = new TreeMap<>();

        Query servicesQuery = _dababaseRoot.child(SERVICES).orderByChild("Title");

        servicesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // loop through anf get each service
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Service service = child.getValue(Service.class);
                    serviceMap.put(service.getTitle(), service);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Unable to retrieve services from the database");
            }
        });


        return serviceMap;
    }

    /**
     * Get all the appointments in the database
     * @return list of appointments
     */
    public List<Appointment> getAllAppointments() {
        return null;
    }

    /**
     * Returns a list off appointments for the customer with the specified customer name
     * @param customerName the customer name to lookup
     * @return list of appointments. Return null if none is found
     */
    public List<Appointment> getAppointmentsForCustomer(String customerName) {
        return null;
    }

    /**
     * Return a list of appointments between the specified dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end at
     * @return List of Appointments
     */
    public List<Appointment> getAppointmentsBetween(LocalDate startDate, LocalDate endDate){
        return null;
    }

    /**
     * Return a list of appointments limited to the specified number
     * @param numOfAppointments the number of appointments to get
     * @return List of Appointments
     */
    public List<Appointment> getAppointmnentWithLimit(int numOfAppointments){
        return null;
    }

    /**
     * Returns all the goals in the database
     * @return List of Goals
     */
    public List<Goal> getAllGoals() {
        return null;
    }

    /**
     * Get all goals that have not been marked as finished
     * @return List of Goals
     */
    public List<Goal> getUnFinishedGoals() {
        return null;
    }

    /**
     * Get goals with the start date specified in the parameter
     * @param startDate the start date that we're looking up
     * @return List of Goals
     */
    public List<Goal> getGoalsWithStartDate(LocalDate startDate) {
        return null;
    }

    /**
     * Retrun goals with the specified end date.
     * @param endDate the end date to look up
     * @return List of Goals
     */
    public List<Goal> getGoalsWithEndDate(LocalDate endDate) {
        return null;
    }

    /**
     * Returns all goals with start dates between the specified dates
     * @param beginStartDate the begining start date
     * @param endStartDate the end start date
     * @return List of Goals
     */
    public List<Goal> getGoalsWithStartDatesBetween(LocalDate beginStartDate, LocalDate endStartDate) {
        return null;
    }

    /**
     * Return all the expenses in the database
     * @return List of Expenses
     */
    public List<Expense> getAllExpenses() {
        return null;
    }

    /**
     * Return a list of expenses between the specified dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end at
     * @return List of Expences
     */
    public  List<Expense> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    /**
     * Return all the sales in the database
     * @return List of Sales
     */
    public List<Sale> getAllSales() {
        return null;
    }

    /**
     * Return a list of sales between two dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end the query at
     * @return List of Sales between the dates
     */
    public List<Sale> getSalesBetweenDates(LocalDate startDate, LocalDate endDate){
        return  null;
    }

    /* Getters end here */

    /* setters start here */


    /**
     * Adds a single valid customer to the database
     * @param customer the customer to be added
     */
    public void addCustomer(Customer customer) {

    }


    /**
     * Adds a signle valid appointment to the database
     * @param appointment the appointment to be added
     */
    public void addAppointment(Appointment appointment) {

    }


    /**
     * Adds a single service to the database
     * @param service the service to be added
     */
    public void addService(Service service) {
        String key = _dababaseRoot.child(SERVICES).push().getKey();

        _dababaseRoot.child(SERVICES).child(key).setValue(service);
    }

    /**
     * Adds a single valid sale to the database
     * @param sale the sale to be added to the database
     */
    public void addSale(Sale sale){

    }

    /**
     * Adds a single valid goal to the database
     * @param goal the goal to be added
     */
    public void addGoal(Goal goal) {

    }


    /* Delete operations */


    /**
     * Deletes a single customer from the database
     * @param customer the customer to be deleted
     */
    public void deleteCustomer(Customer customer) {

    }

    /**
     * Deletes a single appointment from the database
     * @param appointment the appointment to be deleted
     */
    public void deleteAppointment(Appointment appointment) {

    }


    /**
     * Deletes a single service from the database
     * @param service the service to be deleted
     */
    public void deleteService(Service service) {

    }

    /**
     * Delete a single sale from the database
     * @param sale
     */
    public void deleteSale(Sale sale){

    }

    /**
     * Deletes a single goal from the database
     * @param goal the goal to be deleted
     */
    public void deleteGoal(Goal goal) {

    }



}
