package com.ruffneck.mobilesafer.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ruffneck.mobilesafer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by 佛剑分说 on 2015/10/14.
 */
public class ContactsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ListView lv = (ListView) findViewById(R.id.lv);

        final ArrayList<HashMap<String,String>> list = distinct(readContact());

        lv.setAdapter(new SimpleAdapter(this,list,R.layout.contacts_list_item,
                new String[]{"phone","name"},new int[]{R.id.tv_phone,R.id.tv_name}));

        for(HashMap<String,String> map :list){
            System.out.println(map.toString());
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent data = new Intent();
                data.putExtra("phone",list.get(position).get("phone"));
                setResult(RESULT_OK,data);
                finish();

            }
        });

    }

    public ArrayList<HashMap<String, String>> readContact() {
        ArrayList<HashMap<String, String>> contacts = new ArrayList<>();

        ContentResolver resolver = getContentResolver();

        Uri rawContentUri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri dataUri = Uri.parse("content://com.android.contacts/data");

        Cursor idCursor = resolver.query(rawContentUri, new String[]{"contact_id"}, null, null, null);

        if (idCursor != null) {
            while (idCursor.moveToNext()) {
                String contactId = idCursor.getString(0);

                Cursor dataCursor = resolver.query(dataUri, new String[]{"mimetype", "data1"}, "raw_contact_id = ?", new String[]{contactId}, null);

                if (dataCursor != null) {
                    HashMap<String, String> map = new HashMap<>();
                    map.clear();
                    while (dataCursor.moveToNext()) {
                        String mimetype = dataCursor.getString(0);
                        String data = dataCursor.getString(1);

                        if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
                            map.put("phone", data);
                        } else if ("vnd.android.cursor.item/name".equals(mimetype)) {
                            map.put("name", data);
                        }

                    }
                    contacts.add(map);
                    dataCursor.close();
                }

            }
            idCursor.close();
        }

        return contacts;
    }

    public ArrayList<HashMap<String, String>> distinct(ArrayList<HashMap<String, String>> list){

        HashSet<HashMap<String,String>> set = new HashSet<>(list);

        return new ArrayList<>(set);

    }

}
