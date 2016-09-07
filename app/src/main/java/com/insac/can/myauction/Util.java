package com.insac.can.myauction;

/**
 * Created by can on 4.09.2016.
 */
public class Util {

    public static boolean validateInput(String[] inputs) {
        boolean result = true;
        for(String input : inputs) {
            if(!(input.length() > 0)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
