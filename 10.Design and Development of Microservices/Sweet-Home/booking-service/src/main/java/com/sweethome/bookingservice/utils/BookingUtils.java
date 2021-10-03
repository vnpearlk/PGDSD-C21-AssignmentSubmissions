package com.sweethome.bookingservice.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;


/**
 * Utils class
 */
public class BookingUtils {

    /**
     * Utils stub implementation, that returns list of n-count random numbers with upperbound of 100 to
     * simulate
     *
     * @param count
     * @return ArrayList<String></String>
     */
    public static ArrayList<String> getRandomNumbers(int count) {
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String> numberList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }

        return numberList;
    }

    /**
     * Calculates number of days between two intervening from & to dates.
     * Currently there are no validations to check whether fromDate < ToDate.
     *
     * @param fromDate
     * @param toDate
     * @return int
     */
    public static int getNumOfDays(Date fromDate, Date toDate) {

        return (int) DAYS.between(fromDate.toInstant(), toDate.toInstant());
    }

    /**
     * Calculates room price, at 1000 INR rate per room per day
     *
     * @param fromDate
     * @param toDate
     * @param numOfDays
     * @return int
     */
    public static int getRoomPrice(Date fromDate, Date toDate, int numOfDays) {

        return 1000 * getNumOfDays(fromDate, toDate) * numOfDays;
    }
}
