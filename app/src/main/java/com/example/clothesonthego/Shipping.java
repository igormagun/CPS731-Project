package com.example.clothesonthego;

/**
 * A class representing the cost of shipping to a given city, based on database entries from Firestore
 */
public class Shipping {
    private final String id;
    private final String city;
    private float cost;

    /**
     * Initialize a Shipping object
     * @param id The ID from the database
     * @param city The city name
     * @param cost The cost of shipping
     */
    public Shipping(String id, String city, float cost) {
        this.id = id;
        this.city = city;
        this.cost = cost;
    }

    /**
     * Return the name of the city
     * @return The city name
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Return the most up-to-date cost available
     * @return The cost
     */
    public float getCost() {
        return this.cost;
    }
}
