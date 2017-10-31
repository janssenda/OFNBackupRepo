package com.ofn.dao.impl;

public class DataManipulator {

    public static String[] varArgs(String[] args, int arrLength) {
        String[] allArgs = new String[arrLength];
        System.arraycopy(args, 0, allArgs, 0, args.length);
        for (int i = args.length; i < allArgs.length; i++) {
            allArgs[i] = null;
        }
        return allArgs;
    }

    public static String nullify(String s){
        if (s == null || s.isEmpty()) {
            s = null;
        }
        return  s;
    }

}
