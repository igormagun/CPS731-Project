package com.example.clothesonthego;

/**
 * A class representing the cost of shipping to a given city, based on database entries from Firestore
 */
public class Shipping {
    private final String city;
    private final double cost;

    /**
     * Initialize a Shipping object
     * @param city The city name
     * @param cost The cost of shipping
     */
    public Shipping(String city, double cost) {
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
    public double getCost() {
        return this.cost;
    }
}
