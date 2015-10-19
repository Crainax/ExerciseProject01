package com.ruffneck.mobilesafer.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by 佛剑分说 on 2015/10/18.
 */
public class AddressDAO {

    public static final String PATH = "/data/data/com.ruffneck.mobilesafer/files/address.db";

    public static String getAddress(String number) {

        String address = "未知号码";

        SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
        System.out.println("db:" + db);

        String regex = "^1[3-8]\\d{9}$";

        if (number.matches(regex)) {
            Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)",
                    new String[]{number.substring(0, 7)});

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    address = cursor.getString(0);
                }
                cursor.close();
                return address;
            }
        } else if (number.matches("^\\d+$")) {
            switch (number.length()) {
                case 3:
                    address = "报警电话";
                    break;
                case 4:
                    address = "模拟器";
                    break;
                case 5:
                    address = "客服电话";
                    break;
                case 8:
                    address = "本地电话";
                    break;
                default:

                    if (number.startsWith("0") && number.length() > 10) {
                        Cursor cursor = db.rawQuery("select location from data2 where area = ?", new String[]{number.substring(1, 4)});

                        if (cursor != null) {
                            if (cursor.moveToNext()) {
                                address = cursor.getString(0).replaceAll("(电信)|(联通)|(移动)", "固话");
                            } else {
                                cursor.close();

                                cursor = db.rawQuery("select location from data2 where area = ?", new String[]{number.substring(1, 3)});

                                if (cursor != null)
                                    if (cursor.moveToNext()) {
                                        address = cursor.getString(0).replaceAll("(电信)|(联通)|(移动)", "固话");
                                    }

                            }
                            if (cursor != null) cursor.close();
                        }
                    }
                    break;
            }
        }

        db.close();
        return address;
    }


}

