package com.bwie.gejuan.util;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bwie.gejuan.frag.Frag01;

public class NetState {
    public static boolean isConn(Frag01 frag01) {
        boolean s = false;
        //网络判断
       ConnectivityManager connectivityManager = (ConnectivityManager) frag01.getActivity().
               getSystemService(frag01.getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //判断网络是否可用
        if(networkInfo!=null){
            s = connectivityManager.getActiveNetworkInfo().isAvailable();
        }
        return s;
    }
}
