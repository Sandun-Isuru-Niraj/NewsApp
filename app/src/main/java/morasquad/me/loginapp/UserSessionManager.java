package morasquad.me.loginapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by Sandun Isuru Niraj on 2/27/2018.
 */

public class UserSessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "AndroidLoginApp";

    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_NAME = "name";



    public UserSessionManager(Context applicationContext) {

        this._context = applicationContext;
        pref = _context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String s, String name) {

        editor.putBoolean(IS_USER_LOGIN,true);

        editor.putString(KEY_NAME,name);

        editor.commit();
    }

    public boolean checkLogin() {

        if(!this.isUserLoggedIn()){

            Intent i = new Intent(_context,MainScreen.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);

            return true;

        }

        return false;
    }

    public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));


        // return user
        return user;
    }

    public void logoutUser(){

        editor.clear();
        editor.commit();


    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
