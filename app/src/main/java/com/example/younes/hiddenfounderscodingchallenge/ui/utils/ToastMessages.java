package com.example.younes.hiddenfounderscodingchallenge.ui.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by younes on 11/11/2018.
 */

public class ToastMessages {


    final private static String INTERNET_PROBLEM_MESSAGE = "Connection Problem";


    public static void showInternetErrorToast(Context context){
        Toast.makeText(context,INTERNET_PROBLEM_MESSAGE,Toast.LENGTH_SHORT).show();
    }
}
