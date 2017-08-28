package com.example.financialmanagement.util;

import android.util.Log;

import com.example.financialmanagement.activity.FinanceManageActivity;

/**
 * Created by wangdy11 on 2017/7/20.
 */

public class DebugLog {

    public static final String mAPPTAG = "FinancialManagement";

    public static void d(String TAG,String log){
        Log.d(mAPPTAG," " + TAG + " " + log);
    }

    public static void e(String TAG,String log){
        Log.e(mAPPTAG," " + TAG + " " + log);
    }

    public static void i(String TAG,String log){
        Log.i(mAPPTAG," " + TAG + " " + log);
    }

    public static void v(String TAG,String log){
        Log.v(mAPPTAG," " + TAG + " " + log);
    }
}
