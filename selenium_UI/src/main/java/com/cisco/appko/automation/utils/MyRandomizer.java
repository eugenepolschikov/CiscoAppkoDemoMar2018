package com.cisco.appko.automation.utils;


import java.security.SecureRandom;
import java.util.Random;

public class MyRandomizer {


    static final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public  static String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public  static String randomSubString( String specialSymbolsString , int len){
        // create random object
        Random rand = new Random();
        int randomNum = rand.nextInt(specialSymbolsString.length() - len + 1);
        String result = specialSymbolsString.substring(randomNum, randomNum + len);

        return result;
    }
}
