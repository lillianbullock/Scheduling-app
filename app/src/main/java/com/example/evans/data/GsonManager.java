package com.example.evans.data;

import com.google.gson.Gson;

/**
 * Helper class to serialize and deserialize classes
 */

public class GsonManager {

    public static final String CUSTOMER = "Customer";
    public static final String APPOINTMENT = "Appointment";
    public static final String SERVICE = "Service";
    public static final String GOAL = "Goal";
    public static final String Sale = "Sale";
    public static final String EXPENSE = "Expense";


    private static Gson gson = new Gson();



    public static String serializeCustomer(Customer customer) {
        return gson.toJson(customer);
    }

    public static Customer deserializeCustomer(String json) {
        return gson.fromJson(json, Customer.class);
    }


    public static String serializeCustomer(Appointment appointment) {
        return gson.toJson(appointment);
    }

    public static Appointment deserializeAppointment(String json) {
        return gson.fromJson(json, Appointment.class);
    }


    public static String serializeService(Service service) {
        return gson.toJson(service);
    }

    public static Service deserializeService(String json) {
        return gson.fromJson(json, Service.class);
    }


    public static String serializeGoal(Goal goal) {
        return gson.toJson(goal);
    }

    public static Goal deserializeGoal(String json) {
        return gson.fromJson(json, Goal.class);
    }

    public static String serializeSale(Sale sale) {
        return gson.toJson(sale);
    }

    public static Sale deserializeSale(String json) {
        return gson.fromJson(json, Sale.class);
    }

    public static String serializeExpense(Expense expense) {
        return gson.toJson(expense);
    }

    public static Expense deserializeExpense(String json) {
        return gson.fromJson(json, Expense.class);
    }





    private GsonManager() {

    }
}
