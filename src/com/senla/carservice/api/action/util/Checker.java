package com.senla.carservice.api.action.util;

public class Checker {
    public static boolean isSymbolsString(String text) {
        char [] symbols = new char[]{
                '1','2','3','4','5','6','7','8','9','0','~','!','@',
                '#','$','%','^','&','*','(',')','_','+','-','=','[',
                ']','{','}','\n','\\',':','\"',';',',','.','/','|',
                '<','>','`'
        };
        for (char symbol: symbols){
            if (text.contains(String.valueOf(symbol))){
                return true;
            }
        }
        return false;
    }
    public static boolean isSymbolsStringNumber(String text) {
        char [] symbols = new char[]{
                '~','!','@','#','$','%','^','&','*','(',')',
                '_','+','=','[',']','{','}','\n','\\',
                '\"',';',',','.','/','|','<','>','`'
        };
        for (char symbol: symbols){
            if (text.contains(String.valueOf(symbol))){
                return true;
            }
        }
        return false;
    }
}