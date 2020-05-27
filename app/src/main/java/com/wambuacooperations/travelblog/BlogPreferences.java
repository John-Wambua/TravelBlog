package com.wambuacooperations.travelblog;


import android.content.Context;
import android.content.SharedPreferences;

public class BlogPreferences {

    private static final String KEY_LOGIN_STATE = "key_login_state";

    SharedPreferences preferences;

    BlogPreferences(Context context){

        preferences=context.getSharedPreferences("com.wambuacooperations.travelblog", Context.MODE_PRIVATE);

    }
    public boolean isLoggedIn(){
        return preferences.getBoolean("key_login_state",false);
    }
    public void setLoggedIn(boolean loggedIn){
        preferences.edit().putBoolean("key_login_state",loggedIn).apply();
    }

       ;


}
