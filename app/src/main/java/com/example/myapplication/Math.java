package com.example.myapplication;

/**
https://de.wikipedia.org/wiki/Quersumme
*/

public class Math {
    public static int calculate(String number) {
        int[] digits = convertStringToIntArrayAndReverseOrder(number);
        int alternatingSum = calculateAlternatingSum(digits);
        return alternatingSum;
    }

    public static String getResult(int alternatingSum) {
        if (alternatingSum % 2 == 0) {
            return "Die alternierende Quersumme ist gerade.";
        } else {
            return "Die alternierende Quersumme ist ungerade.";
        }
    }

    private static int[] convertStringToIntArrayAndReverseOrder(String number) {
        int[] digits = new int[number.length()];
        for (int i = 0; i < number.length(); i++) {
            //laut wikipedia beginnt man von rechts nach links durchzugehen
            //damit ich mir danach leichter tue, und per % überprüfe, ob ich ein gerades oder
            //ungerades erwischt habe drehe ich den kompletten string um
            //bitte an dieser stelle nicht verwirrt sein
            digits[number.length() - 1 - i] = Character.getNumericValue(number.charAt(i));
        }
        return digits;
    }

    private static int calculateAlternatingSum(int[] digits) {

        int sum = 0;
        for (int i = 0; i < digits.length; i++) {
            if (i % 2 == 0) {
                sum += digits[i];
            } else {
                sum -= digits[i];
            }
        }
        return sum;
    }
}
