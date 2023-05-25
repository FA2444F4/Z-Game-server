package com.zy.util;

import java.util.ArrayList;
import java.util.Map;

public class Recommend {

    public static double getPearsonCorrelationFromArrayList(ArrayList<Map<String,Integer>> arr1,ArrayList<Map<String,Integer>> arr2){
        //arr1是用户,arr2是其他用户
        int n = arr1.size();
        if (n != arr2.size() || n == 0) {
            throw new IllegalArgumentException("输入数组长度不一致或为空");
        }

        double mean1 = getMeanFromArrayList(arr1);
        double mean2 = getMeanFromArrayList(arr2);
        double sumProduct = 0, sumSquare1 = 0, sumSquare2 = 0;

        for (int i = 0; i < n; i++) {
            double dev1 = arr1.get(i).get("rating") - mean1;
            double dev2 = arr2.get(i).get("rating") - mean2;
            sumProduct += dev1 * dev2;
            sumSquare1 += dev1 * dev1;
            sumSquare2 += dev2 * dev2;
        }

        if (sumSquare1 == 0 || sumSquare2 == 0) {
            return 0;
        }

        return sumProduct / Math.sqrt(sumSquare1 * sumSquare2);
    }

    private static double getMeanFromArrayList(ArrayList<Map<String,Integer>> arr) {
        int n = arr.size();
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr.get(i).get("rating");
        }

        return sum / n;
    }

    public static double pearsonCorrelation(int[] arr1, int[] arr2) {
        int n = arr1.length;
        if (n != arr2.length || n == 0) {
            throw new IllegalArgumentException("输入数组长度不一致或为空");
        }

        double mean1 = mean(arr1);
        double mean2 = mean(arr2);
        double sumProduct = 0, sumSquare1 = 0, sumSquare2 = 0;

        for (int i = 0; i < n; i++) {
            double dev1 = arr1[i] - mean1;
            double dev2 = arr2[i] - mean2;
            sumProduct += dev1 * dev2;
            sumSquare1 += dev1 * dev1;
            sumSquare2 += dev2 * dev2;
        }

        if (sumSquare1 == 0 || sumSquare2 == 0) {
            return 0;
        }

        return sumProduct / Math.sqrt(sumSquare1 * sumSquare2);
    }

    private static double mean(int[] arr) {
        int n = arr.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        return sum / n;
    }

    //Integer
    public static double pearsonCorrelationInteger(Integer[] arr1, Integer[] arr2) {
        int n = arr1.length;
        if (n != arr2.length || n == 0) {
            throw new IllegalArgumentException("输入数组长度不一致或为空");
        }

        double mean1 = meanInteger(arr1);
        double mean2 = meanInteger(arr2);
        double sumProduct = 0, sumSquare1 = 0, sumSquare2 = 0;

        for (int i = 0; i < n; i++) {
            double dev1 = arr1[i] - mean1;
            double dev2 = arr2[i] - mean2;
            sumProduct += dev1 * dev2;
            sumSquare1 += dev1 * dev1;
            sumSquare2 += dev2 * dev2;
        }

        if (sumSquare1 == 0 || sumSquare2 == 0) {
            return 0;
        }

        return sumProduct / Math.sqrt(sumSquare1 * sumSquare2);
    }

    private static double meanInteger(Integer[] arr) {
        int n = arr.length;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            sum += arr[i];
        }
        return sum / n;
    }

}
