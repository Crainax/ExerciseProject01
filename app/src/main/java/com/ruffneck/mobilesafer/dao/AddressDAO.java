package com.ruffneck.mobilesafer.dao;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

/**
 * Created by 佛剑分说 on 2015/10/18.
 */
public class AddressDAO extends AndroidTestCase{

    public static final String PATH = "/data/data/com.ruffneck.mobilesafer/files/address.db";

    public static String getAddress(String number){

        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.OPEN_READONLY);

        System.out.println(db);

        db.close();
        return null;
    }

    public void test(){
        getAddress("100");
    }

}
