package com.example.evans.data;

import com.google.gson.Gson;

/**
 * Helper class to serialize and deserialize classes
 */

public class GsonManager {



    public static String serialize(Object object) {
        Gson gson = new Gson();

        return gson.toJson(object);
    }

    public static Object deserialize(String json, Object object) {
        Gson gson = new Gson();

        object = gson.fromJson(json, object.getClass());

        return object;
    }


    private GsonManager() {

    }
}
