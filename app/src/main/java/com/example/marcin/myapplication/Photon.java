package com.example.marcin.myapplication;

/**
 * Created by Marcin on 2014-11-21.
 */
public class Photon {

    private static boolean forward = false;
    private static boolean backward = false;
    private static boolean right = false;
    private static boolean left = false;

    public Photon() {
        this.forward = false;
        this.backward = false;
        this.right = false;
        this.left = false;
    }

    public static boolean isForward() {
        return forward;
    }

    public static void setForward() {
        forward = true;
        backward = false;
    }

    public static boolean isBackward() {
        return backward;
    }

    public static void setBackward() {
        backward = true;
        forward = false;
    }

    public static boolean isRight() {
        return right;
    }

    public static void setRight() {
        right = true;
        left = false;
    }

    public static boolean isLeft() {
        return left;
    }

    public static void setLeft() {
        left = true;
        right = false;
    }

    public static void setAhead(){
        right = false;
        left = false;
    }

    public static void setStop(){
        forward = false;
        backward = false;
    }

}
