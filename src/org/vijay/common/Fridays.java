package org.vijay.common;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Fridays {
    public static void main(String[] args) {
        int year = 2014;

        // put the month you want
        int month = Calendar.JANUARY;
        System.out.println(month);
        Calendar cal = new GregorianCalendar(year, month, 1);
        do {
            int day = cal.get(Calendar.DAY_OF_WEEK);
            if (day == Calendar.FRIDAY) {
                System.out.println(cal.get(Calendar.DAY_OF_MONTH));
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }  while (cal.get(Calendar.MONTH) == month);
    }
}