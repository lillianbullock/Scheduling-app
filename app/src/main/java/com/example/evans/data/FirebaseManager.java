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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Manages our connection with Google Firebase. Handles create, update and delete operations.
 */

public class FirebaseManager {

    private DatabaseReference _databaseRoot;

    private static final String TAG = "FirebaseManager";

    private static String SERVICES = "Services";
    private static String CUSTOMERS = "Customers";
    private static String APPOINTMENTS = "Appointments";
    private static String GOALS = "Goals";
    private static String SALES = "Sales";
    private static String EXPENSES = "Expenses";



    public FirebaseManager() {

        // initialize our connection to firebase
        _databaseRoot = FirebaseDatabase.getInstance().getReference();
    }


    /* Getters start here */

    /**
     * Get a list of all customers in the database
     * @return
     */
    public List<Customer> getAllCustomers() {
        final List<Customer> customers = new ArrayList<>();

        Query allCustomersQuery = _databaseRoot.child(CUSTOMERS);

        allCustomersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Customer newCustomer = child.getValue(Customer.class);
                    customers.add(newCustomer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Unable to load customers from the database");
            }
        });

        return customers;
    }


    /**
     * Return a list of customers added between two dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end the query at
     * @return List of Customers added between the dates
     */
    public List<Customer> getCustomersAddedBetweenDates(LocalDate startDate, LocalDate endDate){
        final List<Customer> customers = new ArrayList<>();
        final String DATE = "dateAdded";

        Query customerAddedBetweenDatesQuery = _databaseRoot.child(CUSTOMERS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        customerAddedBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Customer newCustomer = child.getValue(Customer.class);
                    customers.add(newCustomer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for customers cancelled");
            }
        });

        return customers;
    }



    /**
     * Return the first customer found with the id
     * @param customerId the customer id that we're looking up
     * @return a customer with the id, null otherwise
     */
    public Customer getCustomerWithId(String customerId) {

        String ID = "id";

        // this is a hack around Java not letting us change variables in an inner class
        final Customer[] customerArray = new Customer[1];
        customerArray[0] = null;

        Query customerWithIdQuery = _databaseRoot.child(CUSTOMERS).orderByChild(ID).equalTo(customerId);

        customerWithIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                     customerArray[0] = child.getValue(Customer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return customerArray[0];
    }

    /**
     * Return the first customer found with the given name
     * @param customerName the name of the customer to look up
     * @return a customer with the name, null if no matching customer exist
     */
    public Customer getCustomerWithName(String customerName) {

        String NAME = "name";

        // this is a hack around Java not letting us change variables in an inner class
        final Customer[] customerArray = new Customer[1];
        customerArray[0] = null;

        Query customerWithIdQuery = _databaseRoot.child(CUSTOMERS).orderByChild(NAME).equalTo(customerName);

        customerWithIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    customerArray[0] = child.getValue(Customer.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return customerArray[0];
    }


    /**
     * Return a customer that is associated with a specific appointment
     * @param appointment the appointment that we're looking up the customer for
     * @return customer matching the customerId in the appointment
     */
    public Customer getCustomerForAppointment(Appointment appointment) {
        String customerId = appointment.getCustomerId();

        // easy way out hee haw!!
        return getCustomerWithId(customerId);
    }


    /**
     * Return a list of customers limited to the specified number
     * @return
     * @param numCustomers the number of customers to retrieve
     */
    public List<Customer> getCustomersWithLimit(int numCustomers) {

        final List<Customer> customers = new ArrayList<>();

        Query customersQuery = _databaseRoot.child(CUSTOMERS).limitToFirst(numCustomers);

        customersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Customer newCustomer = child.getValue(Customer.class);
                    customers.add(newCustomer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Query cancelled");
            }
        });

        return customers;

    }

    /**
     * Get all the services in the database
     * @return List of Services
     */
    public Map<String, Service> getServices() {

        final Map<String, Service> serviceMap = new TreeMap<>();


        Query servicesQuery = _databaseRoot.child(SERVICES).orderByChild("Title");

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
        final List<Appointment> appointments = new ArrayList<>();

        Query allCustomersQuery = _databaseRoot.child(CUSTOMERS);

        allCustomersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Appointment newAppointment = child.getValue(Appointment.class);
                    appointments.add(newAppointment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Unable to load customers from the database");
            }
        });

        return appointments;
    }

    /**
     * Returns a list off appointments for the specified customer
     * @param customer the customer to lookup
     * @return list of appointments. Return null if none is found
     */
    public List<Appointment> getAppointmentsForCustomer(Customer customer) {

        if (customer == null) {
            return null;
        }

        List<Appointment> customerAppointments = new ArrayList<>();
        String ID = "customerId";
        String customerId = customer.getId();

        Query appointmentsForCustomerQuery = _databaseRoot.child(APPOINTMENTS).orderByChild(ID).equalTo(customerId);

        appointmentsForCustomerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Appointment appointment = child.getValue(Appointment.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Firebase query operation cancelled");
            }
        });

        return customerAppointments;
    }

    /**
     * Return a list of appointments between the specified dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end at
     * @return List of Appointments
     */
    public List<Appointment> getAppointmentsBetween(LocalDate startDate, LocalDate endDate){

        final List<Appointment> appointments = new ArrayList<>();
        final String DATE = "date";

        Query appointmentsBetweenDatesQuery = _databaseRoot.child(APPOINTMENTS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        appointmentsBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Appointment newAppointment = child.getValue(Appointment.class);
                    appointments.add(newAppointment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for appointments cancelled");
            }
        });

        return appointments;
    }

    /**
     * Return a list of appointments limited to the specified number
     * @param numOfAppointments the number of appointments to get
     * @return List of Appointments
     */
    public List<Appointment> getAppointmentWithLimit(int numOfAppointments){
        final List<Appointment> appointments = new ArrayList<>();

        Query appointmentsQuery = _databaseRoot.child(APPOINTMENTS).limitToFirst(numOfAppointments);

        appointmentsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Appointment appointment = child.getValue(Appointment.class);
                    appointments.add(appointment);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for appointments cancelled");
            }
        });

        return appointments;
    }

    /**
     * Returns all the goals in the database
     * @return List of Goals
     */
    public List<Goal> getAllGoals() {
        final List<Goal> goals = new ArrayList<>();

        Query goalsQuery = _databaseRoot.child(GOALS);

        goalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Goal newGoal = child.getValue(Goal.class);
                    goals.add(newGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for all goals cancelled");
            }
        });

        return goals;
    }

    /**
     * Get all goals that have not been marked as finished
     * @return List of Goals
     */
    public List<Goal> getUnFinishedGoals() {

        final List<Goal> goals = new ArrayList<>();
        final String DONE = "done";

        Query unfinishedGoalsQuery = _databaseRoot.child(GOALS).orderByChild(DONE);

        unfinishedGoalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Goal newGoal = child.getValue(Goal.class);
                    goals.add(newGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for unfinished goals cancelled");
            }
        });

        return goals;
    }

    /**
     * Get goals with the start date specified in the parameter
     * @param startDate the start date that we're looking up
     * @return List of Goals
     */
    public List<Goal> getGoalsWithStartDateBetween(LocalDate startDate, LocalDate endDate) {
        final List<Goal> goals = new ArrayList<>();
        final String DATE = "startDate";

        Query goalsWithStartDatesQuery = _databaseRoot.child(GOALS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        goalsWithStartDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Goal newGoal = child.getValue(Goal.class);
                    goals.add(newGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for goals cancelled");
            }
        });

        return goals;
    }

    /**
     * Return goals with the specified end date.
     * @param endDate the end date to stop looking up
     * @param startDate the end date to start looking up
     * @return List of Goals
     */
    public List<Goal> getGoalsWithEndDateBetween(LocalDate startDate, LocalDate endDate) {
        final List<Goal> goals = new ArrayList<>();
        final String DATE = "dueDate";

        Query goalsBetweenDatesQuery = _databaseRoot.child(GOALS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        goalsBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Goal newGoal = child.getValue(Goal.class);
                    goals.add(newGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for goals cancelled");
            }
        });

        return goals;
    }


    /**
     * Return a list of goals limited to the specified number
     * @return list of Goals
     * @param numGoals the number of goals to retrieve
     */
    public List<Goal> getGoalsWithLimit(int numGoals) {

        final List<Goal> goals = new ArrayList<>();

        Query goalsQuery = _databaseRoot.child(GOALS).limitToFirst(numGoals);

        goalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Goal newGoal = child.getValue(Goal.class);
                    goals.add(newGoal);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Query cancelled");
            }
        });

        return goals;

    }


    /**
     * Return all the expenses in the database
     * @return List of Expenses
     */
    public List<Expense> getAllExpenses() {
        final List<Expense> expenses = new ArrayList<>();

        Query allExpensesQuery = _databaseRoot.child(EXPENSES);

        allExpensesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Expense newExpense = child.getValue(Expense.class);
                    expenses.add(newExpense);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Unable to load expenses from the database");
            }
        });

        return expenses;
    }

    /**
     * Return a list of expenses between the specified dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end at
     * @return List of Expenses
     */
    public  List<Expense> getExpensesBetweenDates(LocalDate startDate, LocalDate endDate) {
        final List<Expense> expenses = new ArrayList<>();
        final String DATE = "date";

        Query expensesBetweenDatesQuery = _databaseRoot.child(EXPENSES)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        expensesBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Expense newExpense = child.getValue(Expense.class);
                    expenses.add(newExpense);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for expenses cancelled");
            }
        });

        return expenses;
    }


    /**
     * Return a list of expenses limited to the specified number
     * @return List of expenses
     * @param numExpenses the number of expenses to retrieve
     */
    public List<Expense> getExpensesWithLimit(int numExpenses) {

        final List<Expense> expenses = new ArrayList<>();


        Query expenseQuery = _databaseRoot.child(EXPENSES).limitToFirst(numExpenses);



        expenseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Expense newExpense = child.getValue(Expense.class);
                    expenses.add(newExpense);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Query cancelled");
            }
        });

        return expenses;
    }


    /**
     * Return all the sales in the database
     * @return List of Sales
     */
    public List<Sale> getAllSales() {
        final List<Sale> sales = new ArrayList<>();

        Query allSalesQuery = _databaseRoot.child(SALES);

        allSalesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Sale newSale = child.getValue(Sale.class);
                    sales.add(newSale);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Unable to load expenses from the database");
            }
        });

        return sales;
    }

    /**
     * Return a list of sales between two dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end the query at
     * @return List of Sales between the dates
     */
    public List<Sale> getSalesBetweenDates(LocalDate startDate, LocalDate endDate){
        final List<Sale> sales = new ArrayList<>();
        final String DATE = "date";

        Query salesBetweenDatesQuery = _databaseRoot.child(SALES)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        salesBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()){
                    Sale newSale = child.getValue(Sale.class);
                    sales.add(newSale);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Query to database for sales cancelled");
            }
        });

        return sales;
    }


    /**
     * Return a list of sales limited to the specified number
     * @return
     * @param numSales the number of sales to retrieve
     */
    public List<Sale> getSalesWithLimit(int numSales) {

        final List<Sale> sales = new ArrayList<>();

        Query salesQuery = _databaseRoot.child(SALES).limitToFirst(numSales);

        salesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Sale newSale = child.getValue(Sale.class);
                    sales.add(newSale);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "Query cancelled");
            }
        });

        return sales;

    }

    public String getKeyForNewCustomer() {
        return _databaseRoot.child(CUSTOMERS).push().getKey();
    }

    public String getKeyForNewSale() {
        return _databaseRoot.child(SALES).push().getKey();
    }


    public String getKeyForNewAppointment() {
        return _databaseRoot.child(APPOINTMENTS).push().getKey();
    }

    public String getKeyForNewGoal() {
        return _databaseRoot.child(GOALS).push().getKey();
    }

    public String getKeyForNewExpense() {
        return _databaseRoot.child(EXPENSES).push().getKey();
    }


    /* Getters end here */

    /* setters start here */


    /**
     * Adds a single valid customer to the database
     * @param customer the customer to be added
     */
    public void addCustomer(Customer customer, String customerId) {
        if(customer == null){
            return;
        }


        _databaseRoot.child(CUSTOMERS).child(customerId).setValue(customer);
    }


    /**
     * Adds a single valid appointment to the database
     * @param appointment the appointment to be added
     */
    public void addAppointment(Appointment appointment, String id) {

        if(appointment == null){
            return;
        }

        _databaseRoot.child(APPOINTMENTS).child(id).setValue(appointment);
    }


    /**
     * Adds a single service to the database
     * @param service the service to be added
     */
    public void addService(Service service, String id) {

        if (service == null){
            return;
        }

        _databaseRoot.child(SERVICES).child(id).setValue(service);
    }

    /**
     * Adds a single valid sale to the database
     * @param sale the sale to be added to the database
     */
    public void addSale(Sale sale, String id){

        if(sale == null){
            return;
        }

        _databaseRoot.child(SALES).child(id).setValue(sale);
    }

    /**
     * Adds a single valid goal to the database
     * @param goal the goal to be added
     */
    public void addGoal(Goal goal, String id) {

        if(goal == null){
            return;
        }

        _databaseRoot.child(GOALS).child(id).setValue(goal);

    }


    /**
     * Adds a single valid expense to the database
     * @param expense the expense to be added
     */
    public void addExpense(Expense expense, String id) {

        if(expense == null){
            return;
        }

        _databaseRoot.child(EXPENSES).child(id).setValue(expense);

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
