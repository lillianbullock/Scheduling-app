package com.example.evans.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the connection with Google Firebase. Handles create, update and delete operations.
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
     * @return all customers
     */
    public void getAllCustomers(final OnGetDataListener onGetDataListener) {

        if (onGetDataListener == null){
            throw new NullPointerException("Invalid OnGetDataListener");
        }


        onGetDataListener.onDataLoadStarted();
        Query allCustomersQuery = _databaseRoot.child(CUSTOMERS);

        allCustomersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Unable to load customers from the database");
            }
        });

    }


    /**
     * Return a list of customers added between two dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end the query at
     * @return List of Customers added between the dates
     */
    public void getCustomersAddedBetweenDates(LocalDate startDate, LocalDate endDate, final OnGetDataListener onGetDataListener){

        final String DATE = "dateAdded";
        onGetDataListener.onDataLoadStarted();

        Query customerAddedBetweenDatesQuery = _databaseRoot.child(CUSTOMERS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        customerAddedBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for customers cancelled");
            }
        });

    }

    /**
     * Query firebase for the first customer with the id
     * @param customerId the customer id to query
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getCustomerWithId(String customerId, final OnGetDataListener onGetDataListener) {

        String ID = "id";

        // this is a hack around Java not letting us change variables in an inner class
        final Customer[] customerArray = new Customer[1];

        Query customerWithIdQuery = _databaseRoot.child(CUSTOMERS).orderByChild(ID).equalTo(customerId);

        customerWithIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);

            }
        });

    }

    /**
     * Query firebase for the first computer with the given name in the parameter
     * @param customerName the name to query for
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getCustomerWithName(String customerName, final OnGetDataListener onGetDataListener) {

        String NAME = "name";

        // this is a hack around Java not letting us change variables in an inner class
        onGetDataListener.onDataLoadStarted();

        Query customerWithIdQuery = _databaseRoot.child(CUSTOMERS).orderByChild(NAME).equalTo(customerName);

        customerWithIdQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                 onGetDataListener.onDataLoadFailed(databaseError);
            }
        });

    }


    /**
     * Query firebase for a list of customers limited to the number specified in the parameter
     * @param numCustomers the limit of customers to pull
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getCustomersWithLimit(int numCustomers, final OnGetDataListener onGetDataListener) {


        Query customersQuery = _databaseRoot.child(CUSTOMERS).limitToFirst(numCustomers);

        customersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.i(TAG, "Query cancelled");
            }
        });

    }


    /**
     * Query firebase for all services
     * @param dataListener the listener that will be called when the data is succeeded or failed
     */
    public void getServices(final OnGetDataListener dataListener) {

        if (dataListener == null){
            throw new NullPointerException("NULL OnGetDataListener");
        }

        Query servicesQuery = _databaseRoot.child(SERVICES).orderByChild("Title");

        servicesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Unable to retrieve services from the database");
            }
        });

    }



    /**
     * Query firebase for all appointments
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAllAppointments(final OnGetDataListener onGetDataListener) {

        onGetDataListener.onDataLoadStarted();
        Query allCustomersQuery = _databaseRoot.child(APPOINTMENTS);

        allCustomersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Unable to load customers from the database");
            }
        });
    }


    /**
     * Query firebase for all appointments for the customer in the parameter
     * @param customer the customer to look up appointments for
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAppointmentsForCustomer(Customer customer, final OnGetDataListener onGetDataListener) {

        if (customer == null) {
            return;
        }

        String ID = "customerId";
        String customerId = customer.getId();

        Query appointmentsForCustomerQuery = _databaseRoot.child(APPOINTMENTS).orderByChild(ID).equalTo(customerId);
        onGetDataListener.onDataLoadStarted();

        appointmentsForCustomerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.i(TAG, "Firebase query operation cancelled");
            }
        });
    }


    /**
     * Query firebase for appointments between the specified dates - inclusive
     * @param startDate the date to start from
     * @param endDate the end date
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAppointmentsBetween(LocalDate startDate, LocalDate endDate, final OnGetDataListener onGetDataListener){

        final String DATE = "date";

        Query appointmentsBetweenDatesQuery = _databaseRoot.child(APPOINTMENTS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());
        onGetDataListener.onDataLoadStarted();

        appointmentsBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for appointments cancelled");
            }
        });
    }



    /**
     * Query firebase for a limited list of appointments
     * @param numOfAppointments the limit of appointments to pull
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAppointmentWithLimit(int numOfAppointments, final OnGetDataListener onGetDataListener){


        Query appointmentsQuery = _databaseRoot.child(APPOINTMENTS).limitToFirst(numOfAppointments);
        onGetDataListener.onDataLoadStarted();

        appointmentsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for appointments cancelled");
            }
        });

    }

    /**
     * Returns all the goals in the database
     * @return List of Goals
     */
    /**
     * Query firebase for all goals
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAllGoals(final OnGetDataListener onGetDataListener) {

        if (onGetDataListener == null){
            throw new NullPointerException("OnGetDataListener was not");
        }

        onGetDataListener.onDataLoadStarted();

        Query goalsQuery = _databaseRoot.child(GOALS);

        goalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for all goals cancelled");
            }
        });


    }


    /**
     * Query firebase for a limited number of unfinished goals
     * @param limitNum the number of goals to retrieve
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getUnFinishedLimitGoals(int limitNum, final OnGetDataListener onGetDataListener) {

        final String DONE = "done";

        Query unfinishedGoalsQuery = _databaseRoot.child(GOALS).orderByChild(DONE).limitToFirst(limitNum);
        onGetDataListener.onDataLoadStarted();

        unfinishedGoalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for unfinished goals cancelled");
            }
        });

    }

    /**
     * Query firebase for all unfinished goals
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getUnFinishedGoals(final OnGetDataListener onGetDataListener) {

        final String DONE = "done";

        Query unfinishedGoalsQuery = _databaseRoot.child(GOALS).orderByChild(DONE).equalTo(false);
        onGetDataListener.onDataLoadStarted();

        unfinishedGoalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for unfinished goals cancelled");
            }
        });

    }
    /**
     * Get goals with the start date specified in the parameter
     * @param startDate the start date that we're looking up
     * @return List of Goals
     */
    /**
     * Query firebase for goals between the start and end date specified in the parameter
     * @param startDate the start date
     * @param endDate the end date
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getGoalsWithStartDateBetween(LocalDate startDate, LocalDate endDate, final OnGetDataListener onGetDataListener) {

        final String DATE = "startDate";

        onGetDataListener.onDataLoadStarted();
        Query goalsWithStartDatesQuery = _databaseRoot.child(GOALS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());

        goalsWithStartDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for goals cancelled");
            }
        });

    }

    /**
     * Return goals with the specified end date.
     * @param endDate the end date to stop looking up
     * @param startDate the end date to start looking up
     * @return List of Goals
     */
    /**
     * Query firebase for goals with end dates between the specified parameters
     * @param startDate the start date
     * @param endDate the end date to end the query at
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getGoalsWithEndDateBetween(LocalDate startDate, LocalDate endDate, final OnGetDataListener onGetDataListener) {

        final String DATE = "dueDate";

        Query goalsBetweenDatesQuery = _databaseRoot.child(GOALS)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());
        onGetDataListener.onDataLoadStarted();

        goalsBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for goals cancelled");
            }
        });

    }



    /**
     * Query firebase for a list of goals limited to the specified number
     * @param numGoals the number of goals to retrieve
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getGoalsWithLimit(int numGoals, final OnGetDataListener onGetDataListener) {


        Query goalsQuery = _databaseRoot.child(GOALS).limitToLast(numGoals);
        onGetDataListener.onDataLoadStarted();

        goalsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.i(TAG, "Query cancelled");
            }
        });


    }


    /**
     * Query firebase for a all expenses
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAllExpenses(final OnGetDataListener onGetDataListener) {

        Query allExpensesQuery = _databaseRoot.child(EXPENSES);
        onGetDataListener.onDataLoadStarted();


        allExpensesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Unable to load expenses from the database");
            }
        });


    }


    /**
     * Query firebase for expenses between the specified dates - inclusive
     * @param startDate the date to start from
     * @param endDate the date to end
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public  void getExpensesBetweenDates(LocalDate startDate, LocalDate endDate, final OnGetDataListener onGetDataListener) {

        final String DATE = "date";

        Query expensesBetweenDatesQuery = _databaseRoot.child(EXPENSES)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());
        onGetDataListener.onDataLoadStarted();

        expensesBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for expenses cancelled");
            }
        });

    }



    /**
     * Query firebase for a limited amount of expenses
     * @param numExpenses the number of expenses to query for
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getExpensesWithLimit(int numExpenses, final OnGetDataListener onGetDataListener) {


        Query expenseQuery = _databaseRoot.child(EXPENSES).limitToFirst(numExpenses);
        onGetDataListener.onDataLoadStarted();



        expenseQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.i(TAG, "Query cancelled");
            }
        });

    }



    /**
     * Query firebase for a all sales
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getAllSales(final OnGetDataListener onGetDataListener) {


        Query allSalesQuery = _databaseRoot.child(SALES);
        onGetDataListener.onDataLoadStarted();


        allSalesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Unable to load expenses from the database");
            }
        });

    }


    /**
     * Query firebase for a sales between the dates specified in the parameter
     * @param startDate the date to start from
     * @param endDate the date to end at
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getSalesBetweenDates(LocalDate startDate, LocalDate endDate, final OnGetDataListener onGetDataListener){

        final String DATE = "date";

        Query salesBetweenDatesQuery = _databaseRoot.child(SALES)
                .orderByChild(DATE).startAt(startDate.toString())
                .endAt(endDate.toString());
        onGetDataListener.onDataLoadStarted();

        salesBetweenDatesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.w(TAG, "Query to database for sales cancelled");
            }
        });
    }


     /**
     *  Query firebase for a list of sales limited to the specified number
     *
     * @param numSales the number of sales to get
     * @param onGetDataListener the listener that will be called when the data is succeeded or failed
     */
    public void getSalesWithLimit(int numSales, final OnGetDataListener onGetDataListener) {

        final List<Sale> sales = new ArrayList<>();

        Query salesQuery = _databaseRoot.child(SALES).limitToFirst(numSales);
        onGetDataListener.onDataLoadStarted();

        salesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onGetDataListener.onDataLoadSucceed(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetDataListener.onDataLoadFailed(databaseError);
                Log.i(TAG, "Query cancelled");
            }
        });


    }

    /**
     * Returns an auto-generated key from firebase for a new customer that can be used as an id.
     * @return key
     */
    public String getKeyForNewCustomer() {
        return _databaseRoot.child(CUSTOMERS).push().getKey();
    }


    /**
     * Returns an auto-generated key from firebase for a new sale that can be used as an id.
     * @return key
     */
    public String getKeyForNewSale() {
        return _databaseRoot.child(SALES).push().getKey();
    }


    /**
     * Returns an auto-generated key from firebase for a new appointment that can be used as an id.
     * @return key
     */
    public String getKeyForNewAppointment() {
        return _databaseRoot.child(APPOINTMENTS).push().getKey();
    }

    /**
     * Returns an auto-generated key from firebase for a new goal that can be used as an id.
     * @return key
     */
    public String getKeyForNewGoal() {
        return _databaseRoot.child(GOALS).push().getKey();
    }

    /**
     * Returns an auto-generated key from firebase for a new expense that can be used as an id.
     * @return key
     */
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

        if (customer != null){
            _databaseRoot.child(CUSTOMERS).child(customer.getId()).removeValue();
        }
    }

    /**
     * Deletes a single appointment from the database
     * @param appointment the appointment to be deleted
     */
    public void deleteAppointment(Appointment appointment) {
        if (appointment != null){
            _databaseRoot.child(CUSTOMERS).child(appointment.getId()).removeValue();
        }
    }


    /**
     * Deletes a single service from the database
     * @param service the service to be deleted
     */
    public void deleteService(Service service) {
        if (service != null){
            _databaseRoot.child(SERVICES).child(service.getId()).removeValue();
        }
    }

    /**
     * Deletes a single sale from the database
     * @param sale The sale to be deleted
     */
    public void deleteSale(Sale sale){
        if (sale != null){
            _databaseRoot.child(SALES).child(sale.getId()).removeValue();
        }
    }

    /**
     * Deletes a single goal from the database
     * @param goal the goal to be deleted
     */
    public void deleteGoal(Goal goal) {
        if (goal != null){
            _databaseRoot.child(GOALS).child(goal.getId()).removeValue();
        }
    }

    /**
     * Deletes an expense from the database
     * @param expense the expense to be deleted
     */
    public void deleteExpense(Expense expense){
        if (expense != null){
            _databaseRoot.child(EXPENSES).child(expense.getId()).removeValue();
        }
    }


    /**
     * Update a customer on firebase with a new customer
     * @param oldCustomer the old customer to be overwritten
     * @param newCustomer the new customer
     */
    public void updateCustomer(Customer oldCustomer, Customer newCustomer) {

        if (oldCustomer != null && newCustomer != null){
            _databaseRoot.child(CUSTOMERS).child(oldCustomer.getId()).setValue(newCustomer);
        }
    }

    /**
     * Update an appointment on firebase with a new appointment
     * @param oldAppointment the old appointment to be overwritten
     * @param newAppointment the new appointment
     */
    public void updateAppointment(Appointment oldAppointment, Appointment newAppointment) {
        if (oldAppointment != null && newAppointment != null){
            _databaseRoot.child(APPOINTMENTS).child(oldAppointment.getId()).setValue(newAppointment);
        }
    }

    /**
     * Update an appointment on firebase with the same appointment
     *
     * This is for cases when the appointment was just slightly modified and
     * did not warrant creating a new appointment object
     * @param newAppointment the appointment to be updated
     */
    public void updateAppointment(Appointment newAppointment) {
        if (newAppointment != null){
            _databaseRoot.child(APPOINTMENTS).child(newAppointment.getId()).setValue(newAppointment);
        }
    }

    /**
     * Update a sale on firebase with a new sale
     * @param newSale the old sale to be overwritten
     * @param oldSale the new sale
     */
    public void updateSale(Sale oldSale, Sale newSale) {
        if (oldSale != null && newSale != null){
            _databaseRoot.child(SALES).child(oldSale.getId()).setValue(newSale);
        }
    }

    /**
     * Update a goal on firebase with a new goal
     * @param oldGoal the old goal to be overwritten
     * @param newGoal the new goal
     */
    public void updateGoal(Goal oldGoal, Goal newGoal) {
        if (oldGoal != null && newGoal != null){
            _databaseRoot.child(GOALS).child(oldGoal.getId()).setValue(newGoal);
        }
    }

    /**
     * Update a service on firebase with a new service
     * @param oldService the old service to be overwritten
     * @param newService the new service
     */
    public void updateService(Service oldService, Service newService) {
        if (oldService != null && newService != null){
            _databaseRoot.child(SERVICES).child(oldService.getId()).setValue(newService);
        }
    }

    /**
     * Update an expense on firebase with a new expense
     * @param oldExpense the old expenses to be overwritten
     * @param newExpense the new expense
     */
    public void updateExpense(Expense oldExpense, Expense newExpense) {
        if (oldExpense != null && newExpense != null){
            _databaseRoot.child(EXPENSES).child(oldExpense.getId()).setValue(newExpense);
        }
    }



}
