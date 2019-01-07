// BoughtDrink.aidl
package com.example.vendingmachine;

// Declare any non-default types here with import statements

interface BoughtDrink {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int getMoney();

    void setMoney(int money);

    String[] getDrinkResult();

    void initialize();

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
